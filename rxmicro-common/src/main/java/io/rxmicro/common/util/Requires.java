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

package io.rxmicro.common.util;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

import static io.rxmicro.common.util.Formats.format;

/**
 * Custom require utils.
 *
 * @author nedis
 * @since 0.1
 */
public final class Requires {

    /**
     * Checks that the specified object reference is not {@code null}.
     *
     * <p>
     * The main advantage of this method comparing with {@link Objects#requireNonNull(Object)} is the ability to replace
     * {@code if} statement by the {@code assert} one.
     * This replacement can increase the performance of the RxMicro framework in future.
     *
     * @param instance the specified object
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if the specified object is {@code null}
     * @see Objects#requireNonNull(Object)
     */
    public static <T> T require(final T instance) {
        //assert instance != null;
        return Objects.requireNonNull(instance);
    }

    /**
     * Checks that the specified object reference is not {@code null} and throws a exception with custom message if it is.
     *
     * <p>
     * The main advantage of this method comparing with {@link Objects#requireNonNull(Object, String)} is the ability to replace
     * {@code if} statement by the {@code assert} one.
     * This replacement can increase the performance of the RxMicro framework in future.
     *
     * @param instance the specified object
     * @param message the message template
     * @param args the message template arguments
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if the message template is {@code null} or the specified object is {@code null}
     * @throws IllegalArgumentException if detected a redundant placeholder or missing argument
     * @see Objects#requireNonNull(Object, String)
     */
    public static <T> T require(final T instance,
                                final String message,
                                final Object... args) {
        //assert instance != null : format(message, args);
        return Objects.requireNonNull(instance, format(message, args));
    }

    /**
     * Checks that the specified object reference is not {@code null} and throws a exception with custom message if it is.
     *
     * <p>
     * The main advantage of this method comparing with {@link Objects#requireNonNull(Object, String)} is the ability to replace
     * {@code if} statement by the {@code assert} one.
     * This replacement can increase the performance of the RxMicro framework.
     *
     * @param instance the specified object
     * @param message the message template
     * @param suppliers the message template argument suppliers
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if the message template is {@code null} or the specified object is {@code null}
     * @throws IllegalArgumentException if detected a redundant placeholder or missing argument
     * @see Objects#requireNonNull(Object, String)
     */
    @SafeVarargs
    public static <T> T require(final T instance,
                                final String message,
                                final Supplier<Object>... suppliers) {
        //assert instance != null : format(message, Arrays.stream(suppliers).map(Supplier::get).toArray());
        return Objects.requireNonNull(instance, format(message, Arrays.stream(suppliers).map(Supplier::get).toArray()));
    }

    private Requires() {
    }
}
