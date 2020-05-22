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
import io.rxmicro.runtime.detail.InstanceQualifier;
import io.rxmicro.runtime.local.AbstractFactory;
import io.rxmicro.runtime.local.error.InstanceNotFoundException;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static io.rxmicro.runtime.local.InstanceContainer.getSingletonsByType;

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

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T> Optional<T> getOptionalBean(final Object instance,
                                                  final String injectionPoint,
                                                  final InstanceQualifier<? super T>... instanceQualifiers) {
        final AbstractFactory factory = get(BEAN_FACTORY_IMPL_CLASS_NAME);
        CIRCULAR_DEPENDENCIES_RESOLVER.push(instance, injectionPoint);
        try {
            return (Optional<T>) Arrays.stream(instanceQualifiers)
                    .flatMap(q -> factory.getImpl(q).stream())
                    .findFirst();
        } finally {
            CIRCULAR_DEPENDENCIES_RESOLVER.pop();
        }
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
