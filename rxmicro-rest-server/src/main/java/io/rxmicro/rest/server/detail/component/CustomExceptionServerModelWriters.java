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

package io.rxmicro.rest.server.detail.component;

import io.rxmicro.common.InvalidStateException;
import io.rxmicro.http.error.HttpErrorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author nedis
 * @since 0.9
 */
public final class CustomExceptionServerModelWriters {

    private static final Map<Class<?>, CustomExceptionServerModelWriter<?>> REGISTERED_CUSTOM_EXCEPTION_WRITERS = new HashMap<>();

    public static <T extends HttpErrorException> void registerCustomExceptionServerModelWriter(
            final Class<T> customExceptionClass,
            final CustomExceptionServerModelWriter<T> customExceptionServerModelWriter) {
        final CustomExceptionServerModelWriter<?> oldValue =
                REGISTERED_CUSTOM_EXCEPTION_WRITERS.put(customExceptionClass, customExceptionServerModelWriter);
        if (oldValue != null) {
            throw new InvalidStateException(
                    "Expected only one custom exception writer per exception class: '?', but actual is two ones: '?' and '?'!",
                    customExceptionClass.getName(), oldValue.getClass().getName(), customExceptionServerModelWriter.getClass().getName()
            );
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends HttpErrorException> Optional<CustomExceptionServerModelWriter<T>> getCustomExceptionWriter(
            final Class<T> customExceptionClass) {
        return Optional.ofNullable((CustomExceptionServerModelWriter<T>) REGISTERED_CUSTOM_EXCEPTION_WRITERS.get(customExceptionClass));
    }

    private CustomExceptionServerModelWriters() {
    }
}
