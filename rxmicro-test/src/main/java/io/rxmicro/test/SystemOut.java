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

package io.rxmicro.test;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * The additional interface for easy console access during testing
 *
 * @author nedis
 * @since 0.1
 * @see System#out
 */
public interface SystemOut {

    /**
     * Returns the array of bytes sent to {@link System#out} during testing
     *
     * @return the array of bytes sent to {@link System#out} during testing
     */
    byte[] asBytes();

    /**
     * Converts the array of bytes sent to {@link System#out} during testing to the {@code UTF-8} trimmed {@link String}
     *
     * @return the {@code UTF-8} trimmed {@link String}
     */
    default String asString() {
        return asString(UTF_8, true);
    }

    /**
     * Converts the array of bytes sent to {@link System#out} during testing to the {@code UTF-8} {@link String}
     *
     * @param trim should the returns string be trimmed
     * @return the {@code UTF-8} {@link String}
     */
    default String asString(final boolean trim) {
        return asString(UTF_8, trim);
    }

    /**
     * Converts the array of bytes sent to {@link System#out} during testing to the trimmed {@link String} in the specified charset
     *
     * @param charset the specified charset
     * @return the trimmed {@link String} in the specified charset
     * @see java.nio.charset.StandardCharsets
     */
    default String asString(final Charset charset) {
        return asString(charset, true);
    }

    /**
     * Converts the array of bytes sent to {@link System#out} during testing to the {@link String} in the specified charset
     *
     * @param charset the specified charset
     * @param trim should the returns string be trimmed
     * @return the {@link String} in the specified charset
     * @see java.nio.charset.StandardCharsets
     */
    default String asString(final Charset charset,
                            final boolean trim) {
        final String string = new String(asBytes(), charset);
        return trim ? string.trim() : string;
    }

    /**
     * Converts the array of bytes sent to {@link System#out} during testing to the {@link List} of
     *          the {@code UTF-8} trimmed {@link String}s
     *
     * @return the {@link List} of the {@code UTF-8} trimmed {@link String}s
     */
    default List<String> asStrings() {
        return asStrings(UTF_8, true);
    }

    /**
     * Converts the array of bytes sent to {@link System#out} during testing to the {@link List} of the {@code UTF-8} {@link String}s
     *
     * @param trim should the each string in the returned {@link List} be trimmed
     * @return the {@link List} of the {@code UTF-8} {@link String}s
     */
    default List<String> asStrings(final boolean trim) {
        return asStrings(UTF_8, trim);
    }

    /**
     * Converts the array of bytes sent to {@link System#out} during testing to the {@link List} of the trimmed {@link String}s
     * in the specified charset
     *
     * @param charset the specified charset
     * @return the {@link List} of the {@link String}s in the specified charset
     * @see java.nio.charset.StandardCharsets
     */
    default List<String> asStrings(final Charset charset) {
        return asStrings(charset, true);
    }

    /**
     * Converts the array of bytes sent to {@link System#out} during testing to the {@link List} of the
     * {@link String}s in the specified charset
     *
     * @param charset the specified charset
     * @param trim should the each string in the returned {@link List} be trimmed
     * @return the {@link List} of the {@link String}s in the specified charset
     * @see java.nio.charset.StandardCharsets
     */
    default List<String> asStrings(final Charset charset,
                                   final boolean trim) {
        return Arrays.stream(asString(charset, trim).split(lineSeparator())).map(String::trim).collect(Collectors.toList());
    }

    /**
     * Returns {@code true} if the array of bytes sent to {@link System#out} during testing is empty
     *
     * @return {@code true} if the array of bytes sent to {@link System#out} during testing is empty
     */
    default boolean isEmpty() {
        return asBytes().length == 0;
    }
}
