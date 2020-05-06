/*
 * Copyright 2019 https://rxmicro.io
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

import io.rxmicro.common.InvalidStateException;
import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;
import io.rxmicro.runtime.detail.InstanceQualifier;
import io.rxmicro.runtime.local.provider.EagerInstanceProvider;
import io.rxmicro.runtime.local.provider.LazyInstanceProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.runtime.detail.Runtimes.ENTRY_POINT_PACKAGE;
import static io.rxmicro.runtime.internal.RuntimeVersion.setRxMicroVersion;
import static io.rxmicro.runtime.local.InstanceContainer.getSingleton;
import static io.rxmicro.runtime.local.InstanceContainer.overrideSingleton;
import static io.rxmicro.runtime.local.InstanceContainer.registerSingleton;
import static io.rxmicro.runtime.local.Instances.instantiate;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractFactory {

    private static final Map<String, AbstractFactory> FACTORIES = new HashMap<>();

    static {
        setRxMicroVersion();
    }

    public static void clearFactories() {
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

    protected static AbstractFactory get(final String implClassName) {
        return FACTORIES.computeIfAbsent(implClassName, cl ->
                instantiate(format("?.?", ENTRY_POINT_PACKAGE, cl)));
    }

    protected static void registerFactory(final String implClassName,
                                          final AbstractFactory factory) {
        if (FACTORIES.put(implClassName, factory) != null) {
            throw new InvalidStateException("Factory already registered: implClassName=?", implClassName);
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
