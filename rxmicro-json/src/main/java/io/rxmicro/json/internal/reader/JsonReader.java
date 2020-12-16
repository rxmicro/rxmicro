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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.rxmicro.json.internal.reader.JsonDelimiters.END_OF_JSON;
import static io.rxmicro.json.internal.reader.JsonDelimiters.EXPECT_ARRAY_END;
import static io.rxmicro.json.internal.reader.JsonDelimiters.EXPECT_ARRAY_ITEM;
import static io.rxmicro.json.internal.reader.JsonDelimiters.EXPECT_COMMA;
import static io.rxmicro.json.internal.reader.JsonDelimiters.EXPECT_OBJECT_END;
import static io.rxmicro.json.internal.reader.JsonDelimiters.EXPECT_PROPERTY_NAME;
import static io.rxmicro.json.internal.reader.JsonDelimiters.createUnExpectedTokenError;
import static io.rxmicro.json.internal.reader.JsonDelimiters.expectThatExpectedValuesContainSpecifiedToken;
import static io.rxmicro.json.internal.reader.JsonDelimiters.isIgnoredDelimiter;
import static io.rxmicro.json.internal.reader.JsonValueReader.readPrimitiveValue;
import static io.rxmicro.json.internal.reader.JsonValueReader.readPropertyName;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;

/**
 * @author nedis
 * @since 0.1
 */
public final class JsonReader {

    public static Map<String, Object> readJsonObject(final String jsonObject,
                                                     final int recursionDepth) {
        final StringIterator iterator = new StringIterator(jsonObject);
        final char ch = getNextSignificantCharacter(iterator);
        expectNotBlankString(ch);
        if (ch == '{') {
            final Map<String, Object> result = readObject(iterator, recursionDepth);
            expectEndOfFile(iterator);
            return result;
        } else {
            throw new JsonException("The provided string is not JSON object: ?!", jsonObject);
        }
    }

    public static List<Object> readJsonArray(final String jsonArray,
                                             final int recursionDepth) {
        final StringIterator iterator = new StringIterator(jsonArray);
        final char ch = getNextSignificantCharacter(iterator);
        expectNotBlankString(ch);
        if (ch == '[') {
            final List<Object> result = readArray(iterator, recursionDepth);
            expectEndOfFile(iterator);
            return result;
        } else {
            throw new JsonException("The provided string is not JSON array: ?!", jsonArray);
        }
    }

    public static Object readJsonPrimitive(final String jsonPrimitive) {
        final StringIterator iterator = new StringIterator(jsonPrimitive);
        final char ch = getNextSignificantCharacter(iterator);
        expectNotBlankString(ch);
        if (ch == '{' || ch == '[') {
            throw new JsonException("The provided string is not JSON primitive: ?!", jsonPrimitive);
        } else {
            iterator.previous();
            final Object value = readPrimitiveValue(iterator);
            expectEndOfFile(iterator);
            return value;
        }
    }

    private static Map<String, Object> readObject(final StringIterator iterator,
                                                  final int recursionDepth) {
        expectPositiveRecursionDepth(recursionDepth);
        final Map<String, Object> result = new LinkedHashMap<>();
        int expectedValues = EXPECT_PROPERTY_NAME | EXPECT_OBJECT_END;
        while (true) {
            final char ch = getNextSignificantCharacter(iterator);
            if (ch == END_OF_JSON) {
                throw createUnExpectedTokenError(iterator, expectedValues);
            } else if (ch == '}') {
                expectThatExpectedValuesContainSpecifiedToken(iterator, expectedValues, EXPECT_OBJECT_END);
                return unmodifiableMap(result);
            } else if (ch == ',') {
                expectThatExpectedValuesContainSpecifiedToken(iterator, expectedValues, EXPECT_COMMA);
                expectedValues = EXPECT_PROPERTY_NAME;
            } else {
                expectThatExpectedValuesContainSpecifiedToken(iterator, expectedValues, EXPECT_PROPERTY_NAME);
                final String propertyName = readPropertyName(ch, iterator);
                readPropertyDelimiter(iterator);
                final Object propertyValue = readPropertyValue(iterator, recursionDepth, propertyName);
                result.put(propertyName, propertyValue);
                expectedValues = EXPECT_OBJECT_END | EXPECT_COMMA;
            }
        }
    }

    private static void readPropertyDelimiter(final StringIterator iterator) {
        final char ch = getNextSignificantCharacter(iterator);
        if (ch != ':') {
            throw new JsonException("Expected ':'. Index=?", iterator.getIndex());
        }
    }

    private static Object readPropertyValue(final StringIterator iterator,
                                            final int recursionDepth,
                                            final String propertyName) {
        final char ch = getNextSignificantCharacter(iterator);
        if (ch == '{') {
            return readObject(iterator, recursionDepth - 1);
        } else if (ch == '[') {
            return readArray(iterator, recursionDepth - 1);
        } else {
            expectPropertyValue(iterator, ch, propertyName);
            iterator.previous();
            return readPrimitiveValue(iterator);
        }
    }

    private static List<Object> readArray(final StringIterator iterator,
                                          final int recursionDepth) {
        expectPositiveRecursionDepth(recursionDepth);
        final List<Object> list = new ArrayList<>();
        int expectedValues = EXPECT_ARRAY_ITEM | EXPECT_ARRAY_END;
        while (true) {
            final char ch = getNextSignificantCharacter(iterator);
            if (ch == END_OF_JSON) {
                throw createUnExpectedTokenError(iterator, expectedValues);
            } else if (ch == ']') {
                expectThatExpectedValuesContainSpecifiedToken(iterator, expectedValues, EXPECT_ARRAY_END);
                return unmodifiableList(list);
            } else if (ch == '{') {
                expectThatExpectedValuesContainSpecifiedToken(iterator, expectedValues, EXPECT_ARRAY_ITEM);
                list.add(readObject(iterator, recursionDepth - 1));
                expectedValues = EXPECT_COMMA | EXPECT_ARRAY_END;
            } else if (ch == '[') {
                expectThatExpectedValuesContainSpecifiedToken(iterator, expectedValues, EXPECT_ARRAY_ITEM);
                list.add(readArray(iterator, recursionDepth - 1));
                expectedValues = EXPECT_COMMA | EXPECT_ARRAY_END;
            } else {
                if (ch == ',') {
                    expectThatExpectedValuesContainSpecifiedToken(iterator, expectedValues, EXPECT_COMMA);
                    expectedValues = EXPECT_ARRAY_ITEM;
                } else {
                    expectThatExpectedValuesContainSpecifiedToken(iterator, expectedValues, EXPECT_ARRAY_ITEM);
                    iterator.previous();
                    list.add(readPrimitiveValue(iterator));
                    expectedValues = EXPECT_COMMA | EXPECT_ARRAY_END;
                }
            }
        }
    }

    private static char getNextSignificantCharacter(final StringIterator iterator) {
        while (iterator.next()) {
            final char ch = iterator.getCurrent();
            if (!isIgnoredDelimiter(ch)) {
                return ch;
            }
        }
        return END_OF_JSON;
    }

    private static void expectNotBlankString(final char ch) {
        if (ch == END_OF_JSON) {
            throw new JsonException("Blank string is not valid json!");
        }
    }

    private static void expectEndOfFile(final StringIterator iterator) {
        final char ch = getNextSignificantCharacter(iterator);
        if (ch != END_OF_JSON) {
            throw new JsonException("Expected END_OF_JSON but actual is '?'. Index=?", ch, iterator.getIndex());
        }
    }

    private static void expectPositiveRecursionDepth(final int recursionDepth) {
        if (recursionDepth <= 0) {
            throw new JsonException(
                    "The provided JSON contains a large number of nested items! Increase the recursionDepth parameter!"
            );
        }
    }

    private static void expectPropertyValue(final StringIterator iterator,
                                            final char ch,
                                            final String propertyName) {
        if (ch == '}' || ch == ',' || ch == ':' || ch == ']') {
            throw new JsonException(
                    "Expected a value for the '?' property, but actual is '?'! Index=?",
                    propertyName, ch, iterator.getIndex()
            );
        }
    }

    private JsonReader() {
    }
}
