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

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.function.Function;

import static io.rxmicro.runtime.internal.RuntimeVersion.setRxMicroVersion;

/**
 * @author nedis
 * @since 0.1
 */
public final class Implementations {

    static {
        setRxMicroVersion();
    }

    public static <T> T getImplementation(final Class<T> serviceInterface,
                                          final boolean required,
                                          final Function<Class<T>, ServiceLoader<T>> serviceLoader) {
        final ServiceLoader<T> loader = serviceLoader.apply(serviceInterface);
        final Iterator<T> iterator = loader.iterator();
        T impl = null;

        while (iterator.hasNext()) {
            final T item = iterator.next();
            if (impl != null) {
                throw new ImplementationLoadFailedException(
                        "Detected a few implementations of component ?: ? and ?",
                        serviceInterface.getName(),
                        impl.getClass().getName(),
                        item.getClass().getName()
                );
            }
            impl = item;
        }

        if (impl == null && required) {
            throw new ImplementationLoadFailedException(
                    "Missing an implementation of component ?", serviceInterface.getName()
            );
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
