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

import io.rxmicro.common.model.StringIterator;
import io.rxmicro.json.JsonException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nedis
 * @since 0.5
 */
public final class JsonDelimiters {

    static final char END_OF_JSON = StringIterator.NO_MORE_CHARACTERS_PRESENT;

    static final int EXPECT_COMMA = 0x0001;

    static final int EXPECT_ARRAY_END = 0x0002;

    static final int EXPECT_ARRAY_ITEM = 0x0004;

    static final int EXPECT_OBJECT_END = 0x0008;

    static final int EXPECT_PROPERTY_NAME = 0x0010;

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

    static void expectThatExpectedValuesContainSpecifiedToken(final StringIterator iterator,
                                                              final int expectedValues,
                                                              final int requiredToken) {
        if (!contains(expectedValues, requiredToken)) {
            throw createUnExpectedTokenError(iterator, expectedValues);
        }
    }

    static JsonException createUnExpectedTokenError(final StringIterator iterator,
                                                    final int expectedValues) {
        final List<String> messages = new ArrayList<>();
        if (contains(expectedValues, EXPECT_ARRAY_END)) {
            messages.add("']'");
        }
        if (contains(expectedValues, EXPECT_COMMA)) {
            messages.add("','");
        }
        if (contains(expectedValues, EXPECT_ARRAY_ITEM)) {
            messages.add("an array item");
        }
        if (contains(expectedValues, EXPECT_OBJECT_END)) {
            messages.add("'}'");
        }
        if (contains(expectedValues, EXPECT_PROPERTY_NAME)) {
            messages.add("a property name");
        }
        return new JsonException(
                "Expected ?, but actual is '?'! Index=?",
                String.join(" or ", messages),
                getCurrentCharacter(iterator),
                iterator.getIndex()
        );
    }

    private static String getCurrentCharacter(final StringIterator iterator) {
        final char current = iterator.getCurrent();
        return current == END_OF_JSON ? "END_OF_JSON" : String.valueOf(current);
    }

    private static boolean contains(final int expectedValues,
                                    final int token) {
        return (expectedValues & token) != 0;
    }

    private JsonDelimiters() {
    }
}
