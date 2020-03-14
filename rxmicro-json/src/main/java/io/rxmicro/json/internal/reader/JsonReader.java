/*
 * Copyright (c) 2020 http://rxmicro.io
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

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class JsonReader {

    public static Map<String, Object> readJsonObject(final String jsonObject,
                                                     final int recursionDepth) throws JsonException {
        final StringIterator iterator = new StringIterator(jsonObject);
        final char ch = getNextSignificantCharacter(iterator);
        if (ch == '{') {
            return readJsonObject(iterator, recursionDepth);
        } else {
            throw new JsonException("Empty content");
        }
    }

    public static List<Object> readJsonArray(final String jsonObject,
                                             final int recursionDepth) throws JsonException {
        final StringIterator iterator = new StringIterator(jsonObject);
        final char ch = getNextSignificantCharacter(iterator);
        if (ch == '[') {
            return readJsonArray(iterator, recursionDepth);
        } else {
            throw new JsonException("Empty content");
        }
    }

    public static Object readJsonPrimitive(final String jsonObject) {
        final StringIterator iterator = new StringIterator(jsonObject);
        final String value = readPropertyValue(iterator);
        if (iterator.next()) {
            throw new JsonException("Redundant content after primitive value");
        } else {
            return resolveValue(value);
        }
    }

    private static Map<String, Object> readJsonObject(final StringIterator iterator,
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
                throw new JsonException("Expected '\"'. Index=" + iterator.getIndex());
            }
            final String propertyName = readString(iterator, false);
            gotoStartValueToken(propertyName, iterator);
            ch = getNextSignificantCharacter(iterator);
            if (ch == '{') {
                final Map<String, Object> jsonObject = readJsonObject(iterator, recursionDepth - 1);
                result.put(propertyName, jsonObject);
            } else if (ch == '[') {
                final List<Object> jsonArray = readJsonArray(iterator, recursionDepth - 1);
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
                throw new JsonException("Expected ','. Index=" + iterator.getIndex());
            }
        }
    }

    private static List<Object> readJsonArray(final StringIterator iterator,
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
                list.add(readJsonObject(iterator, recursionDepth - 1));
            } else if (ch == '[') {
                list.add(readJsonArray(iterator, recursionDepth - 1));
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
                throw new JsonException("Expected ','. Index=" + iterator.getIndex());
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

    private static String readPropertyValue(final StringIterator iterator) {
        final StringBuilder sb = new StringBuilder();
        while (iterator.next()) {
            final char ch = iterator.getCurrent();
            if (ch == '"') {
                return readString(iterator, true);
            }
            if (isDelimiter(ch) ||
                    ch == ',' || ch == ':' || ch == '{' || ch == '}' || ch == '[' || ch == ']') {
                iterator.previous();
                break;
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    private static boolean isDelimiter(final char ch) {
        return ch <= ' ' || ch == '\u00A0';
    }

    private static char getNextSignificantCharacter(final StringIterator iterator) {
        while (iterator.next()) {
            final char ch = iterator.getCurrent();
            if (!isDelimiter(ch)) {
                return ch;
            }
        }
        return 0;
    }

    private static void gotoStartValueToken(final String name,
                                            final StringIterator iterator) {
        char ch = getNextSignificantCharacter(iterator);
        if (ch != ':') {
            throw new JsonException("Expected ':'. Index=" + iterator.getIndex());
        }
        ch = getNextSignificantCharacter(iterator);
        if (ch != 0) {
            iterator.previous();
        } else {
            throw new JsonException("Expected value for property: " + name);
        }
    }

    private static String readString(final StringIterator iterator,
                                     final boolean withQuote) {
        final StringBuilder sb = new StringBuilder();
        if (withQuote) {
            sb.append('"');
        }
        while (iterator.next()) {
            final char ch = iterator.getCurrent();
            if (ch == '"') {
                if (iterator.getPrevious() != '\\') {
                    if (withQuote) {
                        sb.append('"');
                    }
                    return sb.toString();
                } else {
                    sb.append(ch);
                }
            } else if (ch == '\\') {
                if (iterator.getPrevious() == '\\') {
                    sb.append('\\');
                }
            } else if (ch == 'b') {
                escapeChar(iterator, sb, ch, '\b');
            } else if (ch == 't') {
                escapeChar(iterator, sb, ch, '\t');
            } else if (ch == 'n') {
                escapeChar(iterator, sb, ch, '\n');
            } else if (ch == 'f') {
                escapeChar(iterator, sb, ch, '\f');
            } else if (ch == 'r') {
                escapeChar(iterator, sb, ch, '\r');
            } else if (ch == 'u') {
                if (iterator.getPrevious() == '\\') {
                    sb.append(readUnicodeCharacter(iterator));
                } else {
                    sb.append(ch);
                }

            } else {
                sb.append(ch);
            }
        }
        throw new JsonException("Expected '\"' at the end of string");
    }

    private static void escapeChar(final StringIterator iterator,
                                   final StringBuilder sb,
                                   final char ch,
                                   final char replacement) {
        if (iterator.getPrevious() == '\\') {
            sb.append(replacement);
        } else {
            sb.append(ch);
        }
    }

    private static char readUnicodeCharacter(final StringIterator iterator) {
        final StringBuilder sb = new StringBuilder();
        while (iterator.next()) {
            sb.append(iterator.getCurrent());
            if (sb.length() == 4) {
                break;
            }
        }
        if (sb.length() == 4) {
            return (char) Integer.parseInt(sb.toString(), 16);
        } else {
            throw new JsonException("Expected valid Unicode character at the end of string");
        }
    }

    private JsonReader() {
    }
}
