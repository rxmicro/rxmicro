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

import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public interface SystemOut {

    byte[] asBytes();

    default String asString() {
        return asString(UTF_8, true);
    }

    default String asString(final boolean trim) {
        return asString(UTF_8, trim);
    }

    default String asString(final Charset charset) {
        return asString(charset, true);
    }

    default String asString(final Charset charset,
                            final boolean trim) {
        final String string = new String(asBytes(), charset);
        return trim ? string.trim() : string;
    }

    default List<String> asStrings() {
        return asStrings(UTF_8, true);
    }

    default List<String> asStrings(final boolean trim) {
        return asStrings(UTF_8, trim);
    }

    default List<String> asStrings(final Charset charset) {
        return asStrings(charset, true);
    }

    default List<String> asStrings(final Charset charset,
                                   final boolean trim) {
        return Arrays.asList(asString(charset, trim).split(lineSeparator()));
    }

    default boolean isEmpty() {
        return asBytes().length == 0;
    }
}
