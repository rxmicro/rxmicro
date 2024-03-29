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

package io.rxmicro.runtime.local;

import io.rxmicro.common.CheckedWrapperException;
import io.rxmicro.common.InvalidStateException;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;
import io.rxmicro.runtime.detail.InstanceQualifier;
import io.rxmicro.runtime.local.provider.EagerInstanceProvider;
import io.rxmicro.runtime.local.provider.LazyInstanceProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static io.rxmicro.reflection.Reflections.instantiate;
import static io.rxmicro.runtime.detail.RxMicroRuntime.getEntryPointFullClassName;
import static io.rxmicro.runtime.internal.RuntimeVersion.setRxMicroVersion;
import static io.rxmicro.runtime.local.InstanceContainer.getSingleton;
import static io.rxmicro.runtime.local.InstanceContainer.overrideSingleton;
import static io.rxmicro.runtime.local.InstanceContainer.registerSingleton;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFactory.class);

    private static final Map<String, AbstractFactory> FACTORIES = new HashMap<>();

    static {
        setRxMicroVersion();
    }

    public static void clearFactories() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("The following factories were removed: ? ", FACTORIES);
        } else {
            LOGGER.debug("? factories were removed successfully", FACTORIES.size());
        }
        FACTORIES.clear();
    }

    protected static Supplier<InvalidStateException> implNotFoundError(final InstanceQualifier<?> instanceQualifier) {
        return () -> {
            throw new InvalidStateException("Implementation not found: ?", instanceQualifier);
        };
    }

    protected static Supplier<InvalidStateException> implNotFoundError(final Class<?> interfaceName) {
        return () -> {
            throw new InvalidStateException("Implementation not found: ?", interfaceName.getName());
        };
    }

    protected static AbstractFactory getRequiredFactory(final Module module,
                                                        final String implSimpleClassName) {
        return getAbstractFactory(module, implSimpleClassName, true);
    }

    protected static Optional<AbstractFactory> getOptionalFactory(final Module module,
                                                                  final String implSimpleClassName) {
        return Optional.ofNullable(getAbstractFactory(module, implSimpleClassName, false));
    }

    private static AbstractFactory getAbstractFactory(final Module module,
                                                      final String implSimpleClassName,
                                                      final boolean throwExceptionIfCreationOfFactoryFailed) {
        final String fullClassName = getEntryPointFullClassName(module, implSimpleClassName);
        AbstractFactory abstractFactory = FACTORIES.get(fullClassName);
        if (abstractFactory == null) {
            try {
                abstractFactory = instantiate(fullClassName);
            } catch (final CheckedWrapperException | IllegalArgumentException exception) {
                if (throwExceptionIfCreationOfFactoryFailed) {
                    throw exception;
                } else {
                    return null;
                }
            }
            FACTORIES.put(fullClassName, abstractFactory);
        }
        return abstractFactory;
    }

    protected static void registerFactory(final Module module,
                                          final String implSimpleClassName,
                                          final AbstractFactory factory) {
        final String fullClassName = getEntryPointFullClassName(module, implSimpleClassName);
        if (FACTORIES.put(fullClassName, factory) != null) {
            throw new InvalidStateException("Factory already registered: fullClassName=?", fullClassName);
        }
    }

    public final <T> Optional<T> getImpl(final InstanceQualifier<? super T> instanceQualifier) {
        return getSingleton(instanceQualifier);
    }

    protected final <T> void register(final Class<T> interfaceType,
                                      final Supplier<T> instanceSupplier) {
        register(interfaceType, instanceSupplier, new ByTypeInstanceQualifier<>(interfaceType));
    }

    @SafeVarargs
    protected final <T> void register(final Class<T> instanceType,
                                      final Supplier<T> instanceSupplier,
                                      final InstanceQualifier<? super T>... instanceQualifiers) {
        registerSingleton(
                new LazyInstanceProvider<>(instanceType, instanceSupplier),
                instanceQualifiers
        );
    }

    @SafeVarargs
    protected final <T> void register(final T instance,
                                      final InstanceQualifier<? super T>... instanceQualifiers) {
        registerSingleton(
                new EagerInstanceProvider<>(instance),
                instanceQualifiers
        );
    }

    @SafeVarargs
    protected final <T> void override(final T instance,
                                      final InstanceQualifier<? super T>... instanceQualifiers) {
        overrideSingleton(
                new EagerInstanceProvider<>(instance),
                instanceQualifiers
        );
    }
}
