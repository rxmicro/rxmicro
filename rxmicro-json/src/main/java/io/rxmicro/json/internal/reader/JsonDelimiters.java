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

package io.rxmicro.json.internal.reader;

/**
 * @author nedis
 * @since 0.5
 */
public final class JsonDelimiters {

    private static final int VERTICAL_TAB = 0x0B;

    static boolean isIgnoredDelimiter(final char ch) {
        return ch == ' ' || ch == '\u00A0' || ch == '\t' || ch == '\n' || ch == '\r' || ch == VERTICAL_TAB;
    }

    static boolean isJsonObjectDelimiter(final char ch) {
        return ch == '{' || ch == '}';
    }

    static boolean isJsonArrayDelimiter(final char ch) {
        return ch == '[' || ch == ']';
    }

    private JsonDelimiters() {
    }
}
