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
import io.rxmicro.json.JsonNumber;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;
import static io.rxmicro.common.util.ExCollections.unmodifiableMap;
import static io.rxmicro.json.internal.reader.JsonDelimiters.isIgnoredDelimiter;
import static io.rxmicro.json.internal.reader.JsonPropertyReader.readPropertyName;
import static io.rxmicro.json.internal.reader.JsonPropertyReader.readPropertyValue;

/**
 * @author nedis
 * @since 0.1
 */
public final class JsonReader {

    public static Map<String, Object> readJsonObject(final String jsonObject,
                                                     final int recursionDepth) {
        final StringIterator iterator = new StringIterator(jsonObject);
        final char ch = getNextSignificantCharacter(iterator);
        if (ch == '{') {
            return readObject(iterator, recursionDepth);
        } else {
            throw new JsonException("Provided string is not JSON object: ?", jsonObject);
        }
    }

    public static List<Object> readJsonArray(final String jsonArray,
                                             final int recursionDepth) {
        final StringIterator iterator = new StringIterator(jsonArray);
        final char ch = getNextSignificantCharacter(iterator);
        if (ch == '[') {
            return readArray(iterator, recursionDepth);
        } else {
            throw new JsonException("Provided string is not JSON array: ?", jsonArray);
        }
    }

    public static Object readJsonPrimitive(final String jsonPrimitive) {
        final StringIterator iterator = new StringIterator(jsonPrimitive);
        final String value = readPropertyValue(iterator);
        if (iterator.next()) {
            throw new JsonException("Provided string is not JSON primitive: ?", jsonPrimitive);
        } else {
            return resolveValue(value);
        }
    }

    private static Map<String, Object> readObject(final StringIterator iterator,
                                                  final int recursionDepth) {
        if (recursionDepth <= 0) {
            throw new JsonException("Stack overflow");
        }
        final Map<String, Object> result = new LinkedHashMap<>();
        while (true) {
            char ch = getNextSignificantCharacter(iterator);
            if (ch == '}') {
                return unmodifiableMap(result);
            } else if (ch != '"') {
                throw new JsonException("Expected '\"'. Index=?", iterator.getIndex());
            }
            final String propertyName = readPropertyName(iterator);
            gotoStartValueToken(propertyName, iterator);
            ch = getNextSignificantCharacter(iterator);
            if (ch == '{') {
                final Map<String, Object> jsonObject = readObject(iterator, recursionDepth - 1);
                result.put(propertyName, jsonObject);
            } else if (ch == '[') {
                final List<Object> jsonArray = readArray(iterator, recursionDepth - 1);
                result.put(propertyName, jsonArray);
            } else {
                iterator.previous();
                final String value = readPropertyValue(iterator);
                result.put(propertyName, resolveValue(value));
            }
            // Expected ',' or '}':
            ch = getNextSignificantCharacter(iterator);
            if (ch == '}') {
                return unmodifiableMap(result);
            } else if (ch != ',') {
                throw new JsonException("Expected ','. Index=?", iterator.getIndex());
            }
        }
    }

    private static List<Object> readArray(final StringIterator iterator,
                                          final int recursionDepth) {
        if (recursionDepth <= 0) {
            throw new JsonException("Stack overflow");
        }
        final List<Object> list = new ArrayList<>();
        while (true) {
            char ch = getNextSignificantCharacter(iterator);
            if (ch == ']') {
                return unmodifiableList(list);
            } else if (ch == '{') {
                list.add(readObject(iterator, recursionDepth - 1));
            } else if (ch == '[') {
                list.add(readArray(iterator, recursionDepth - 1));
            } else {
                iterator.previous();
                final String value = readPropertyValue(iterator);
                list.add(resolveValue(value));
            }
            // Expected ',' or ']':
            ch = getNextSignificantCharacter(iterator);
            if (ch == ']') {
                return unmodifiableList(list);
            } else if (ch != ',') {
                throw new JsonException("Expected ','. Index=?", iterator.getIndex());
            }
        }
    }

    private static Object resolveValue(final String value) {
        if (value.startsWith("\"")) {
            return value.substring(1, value.length() - 1);
        } else if ("true".equals(value)) {
            return Boolean.TRUE;
        } else if ("false".equals(value)) {
            return Boolean.FALSE;
        } else if ("null".equals(value)) {
            return null;
        } else {
            return new JsonNumber(value);
        }
    }

    private static char getNextSignificantCharacter(final StringIterator iterator) {
        while (iterator.next()) {
            final char ch = iterator.getCurrent();
            if (!isIgnoredDelimiter(ch)) {
                return ch;
            }
        }
        return 0;
    }

    private static void gotoStartValueToken(final String name,
                                            final StringIterator iterator) {
        char ch = getNextSignificantCharacter(iterator);
        if (ch != ':') {
            throw new JsonException("Expected ':'. Index=?", iterator.getIndex());
        }
        ch = getNextSignificantCharacter(iterator);
        if (ch != 0) {
            iterator.previous();
        } else {
            throw new JsonException("Expected value for property: ?", name);
        }
    }

    private JsonReader() {
    }
}
