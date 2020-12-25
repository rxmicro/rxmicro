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

import io.rxmicro.common.RxMicroException;
import io.rxmicro.common.RxMicroModule;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Function;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.runtime.internal.RuntimeVersion.setRxMicroVersion;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.1
 */
public final class Implementations {

    static {
        setRxMicroVersion();
    }

    public static <T> T getRequiredRuntimeImplementation(final Class<T> serviceInterface,
                                                         final Function<Class<T>, ServiceLoader<T>> serviceLoader,
                                                         final RxMicroModule... possibleImplementationModules) {
        return getRequiredImplementation(serviceInterface, serviceLoader, "runtime module path", possibleImplementationModules);
    }

    public static <T> T getRequiredTestImplementation(final Class<T> serviceInterface,
                                                      final Function<Class<T>, ServiceLoader<T>> serviceLoader,
                                                      final RxMicroModule... possibleImplementationModules) {
        return getRequiredImplementation(serviceInterface, serviceLoader, "test class path", possibleImplementationModules);
    }

    public static <T> Optional<T> getOptionalImplementation(final Class<T> serviceInterface,
                                                            final Function<Class<T>, ServiceLoader<T>> serviceLoader) {
        return Optional.ofNullable(getImplementation(serviceInterface, serviceLoader));
    }

    private static <T> T getRequiredImplementation(final Class<T> serviceInterface,
                                                   final Function<Class<T>, ServiceLoader<T>> serviceLoader,
                                                   final String type,
                                                   final RxMicroModule... possibleImplementationModules) {
        final T implementation = getImplementation(serviceInterface, serviceLoader);
        if (implementation == null) {
            if (possibleImplementationModules.length == 0) {
                throw new ImplementationLoadFailedException(
                        "Missing an implementation of '?' component! Add module with implementation to ?!",
                        serviceInterface.getName(), type
                );
            } else {
                final boolean single = possibleImplementationModules.length == 1;
                final String messageTemplate;
                if (single) {
                    messageTemplate = "Missing an implementation of '?' component! Add '?' module to ?!";
                } else {
                    messageTemplate = "Missing an implementation of '?' component! Add one of the following ? modules to ?!";
                }
                throw new ImplementationLoadFailedException(
                        messageTemplate,
                        serviceInterface.getName(),
                        single ?
                                possibleImplementationModules[0].getName() :
                                Arrays.stream(possibleImplementationModules)
                                        .map(m -> format("'?'", m.getName()))
                                        .collect(joining(", ")),
                        type
                );
            }
        }
        return implementation;
    }

    private static <T> T getImplementation(final Class<T> serviceInterface,
                                           final Function<Class<T>, ServiceLoader<T>> serviceLoader) {
        final ServiceLoader<T> loader = serviceLoader.apply(serviceInterface);
        final Iterator<T> iterator = loader.iterator();
        T impl = null;

        while (iterator.hasNext()) {
            final T item = iterator.next();
            if (impl != null) {
                throw new ImplementationLoadFailedException(
                        "Detected a few implementations of '?' component: '?' and '?'! " +
                                "The RxMicro framework requires only one implementation per runtime! " +
                                "Remove '?' or '?' module from module path!",
                        serviceInterface.getName(),
                        impl.getClass().getName(),
                        item.getClass().getName(),
                        impl.getClass().getModule().getName(),
                        item.getClass().getModule().getName()
                );
            }
            impl = item;
        }
        return impl;
    }

    private Implementations() {
    }

    /**
     * @author nedis
     * @since 0.1
     */
    public static final class ImplementationLoadFailedException extends RxMicroException {

        private ImplementationLoadFailedException(final String message,
                                                  final Object... args) {
            super(message, args);
        }
    }
}
