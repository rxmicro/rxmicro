/*
 * Copyright (c) 2020. https://rxmicro.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.rxmicro.cdi.detail;

import io.rxmicro.cdi.BeanFactory;
import io.rxmicro.cdi.internal.CircularDependenciesResolver;
import io.rxmicro.common.CheckedWrapperException;
import io.rxmicro.runtime.detail.InstanceQualifier;
import io.rxmicro.runtime.local.AbstractFactory;
import io.rxmicro.runtime.local.error.InstanceNotFoundException;
import io.rxmicro.runtime.local.provider.EagerInstanceProvider;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static io.rxmicro.common.local.InternalModules.isInternalModule;
import static io.rxmicro.common.util.ExCollectors.toOrderedSet;
import static io.rxmicro.reflection.Reflections.instantiate;
import static io.rxmicro.runtime.local.InstanceContainer.getSingletonsByType;
import static io.rxmicro.runtime.local.InstanceContainer.registerSingleton;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public abstract class InternalBeanFactory extends BeanFactory {

    private static final CircularDependenciesResolver CIRCULAR_DEPENDENCIES_RESOLVER =
            new CircularDependenciesResolver();

    @SafeVarargs
    public static <T> T getRequiredBean(final Object instance,
                                        final String injectionPoint,
                                        final InstanceQualifier<? super T>... instanceQualifiers) {
        return getOptionalBean(instance, injectionPoint, instanceQualifiers)
                .orElseThrow(() -> {
                    throw new InstanceNotFoundException(
                            "Can't inject bean qualified by '?' into '?.?': Bean not found.",
                            Arrays.toString(instanceQualifiers),
                            instance.getClass().getName(),
                            injectionPoint
                    );
                });
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <T> Optional<T> getOptionalBean(final Object instance,
                                                  final String injectionPoint,
                                                  final InstanceQualifier<? super T>... instanceQualifiers) {
        CIRCULAR_DEPENDENCIES_RESOLVER.push(instance, injectionPoint);
        try {
            final Set<Class<T>> classes = new HashSet<>();
            for (final InstanceQualifier<? super T> instanceQualifier : instanceQualifiers) {
                classes.add((Class<T>) instanceQualifier.getType());
                final Set<Module> modules = getAllNotInternalModules(
                        instanceQualifier.getType().getModule(),
                        instance.getClass().getModule()
                );
                for (final Module module : modules) {
                    final Optional<AbstractFactory> optionalFactory = getOptionalFactory(module, BEAN_FACTORY_IMPL_CLASS_NAME);
                    if (optionalFactory.isPresent()) {
                        final AbstractFactory factory = optionalFactory.get();
                        final Optional<T> impl = factory.getImpl(instanceQualifier);
                        if (impl.isPresent()) {
                            return impl;
                        }
                    }
                }
            }
            // If bean is a pojo, then the InternalBeanFactory must try to create instance via reflection!
            return tryCreateBeanViaReflection(classes, instanceQualifiers);
        } finally {
            CIRCULAR_DEPENDENCIES_RESOLVER.pop();
        }
    }

    private static Set<Module> getAllNotInternalModules(final Module firstPriorityModule,
                                                        final Module secondPriorityModule) {
        return Stream.concat(
                Stream.of(firstPriorityModule, secondPriorityModule),
                ModuleLayer.boot().modules().stream().filter(m -> !m.isNamed() || !isInternalModule(m.getName()))
        ).collect(toOrderedSet());
    }

    @SafeVarargs
    private static <T> Optional<T> tryCreateBeanViaReflection(final Set<Class<T>> classes,
                                                              final InstanceQualifier<? super T>... instanceQualifiers) {
        for (final Class<T> type : classes) {
            if (!type.isInterface() && !type.isEnum() && !type.isArray() && !type.isAnnotation()) {
                final int modifiers = type.getModifiers();
                if (Modifier.isPublic(modifiers) && !Modifier.isAbstract(modifiers)) {
                    try {
                        final T object = instantiate(type);
                        registerSingleton(new EagerInstanceProvider<>(object), instanceQualifiers);
                        return Optional.of(object);
                    } catch (final CheckedWrapperException | IllegalArgumentException ignored) {
                        // do nothing
                    }
                }
            }
        }
        return Optional.empty();
    }

    public static <T> Set<T> getBeansByType(final Object instance,
                                            final String injectionPoint,
                                            final Class<T> interfaceType) {
        CIRCULAR_DEPENDENCIES_RESOLVER.push(instance, injectionPoint);
        try {
            return getSingletonsByType(interfaceType);
        } finally {
            CIRCULAR_DEPENDENCIES_RESOLVER.pop();
        }
    }
}
