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

import static io.rxmicro.json.internal.reader.JsonDelimiters.isIgnoredDelimiter;
import static io.rxmicro.json.internal.reader.JsonDelimiters.isJsonArrayDelimiter;
import static io.rxmicro.json.internal.reader.JsonDelimiters.isJsonObjectDelimiter;

/**
 * @author nedis
 * @since 0.5
 */
final class JsonValueReader {

    private static final int HEX_RADIX = 16;

    private static final int UNICODE_CHARACTER_LENGTH = 4;

    static String readPropertyName(final char ch,
                                   final StringIterator iterator) {
        if (ch != '"') {
            throw new JsonException("Expected '\"'. Index=?", iterator.getIndex());
        }
        return readString(iterator, false);
    }

    static Object readPrimitiveValue(final StringIterator iterator) {
        return resolveValue(readValue(iterator), iterator.getIndex());
    }

    private static String readValue(final StringIterator iterator) {
        final StringBuilder sb = new StringBuilder();
        while (iterator.next()) {
            final char ch = iterator.getCurrent();
            if (ch == '"') {
                if (sb.length() == 0) {
                    return readString(iterator, true);
                } else {
                    return sb.toString();
                }
            }
            if (isIgnoredDelimiter(ch) || ch == ',' || ch == ':' || isJsonObjectDelimiter(ch) || isJsonArrayDelimiter(ch)) {
                iterator.previous();
                break;
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    private static Object resolveValue(final String value,
                                       final int index) {
        if (value.startsWith("\"")) {
            return value.substring(1, value.length() - 1);
        } else if ("true".equals(value)) {
            return Boolean.TRUE;
        } else if ("false".equals(value)) {
            return Boolean.FALSE;
        } else if ("null".equals(value)) {
            return null;
        } else {
            try {
                return new JsonNumber(value);
            } catch (final NumberFormatException exception) {
                throw new JsonException(exception, "? is not a json number. Index=?", value, index);
            }
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
            } else {
                appendCharacter(iterator, sb, ch);
            }
        }
        throw new JsonException("Expected '\"' at the end of string");
    }

    private static void appendCharacter(final StringIterator iterator,
                                        final StringBuilder sb,
                                        final char ch) {
        if (ch == 'b') {
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
            final char current = iterator.getCurrent();
            if (isAsciiDigit(current) || isLatinLowercaseLetter(current) || isLaticUppercaseLetter(current)) {
                sb.append(current);
            } else {
                break;
            }
            if (sb.length() == UNICODE_CHARACTER_LENGTH) {
                break;
            }
        }
        if (sb.length() == UNICODE_CHARACTER_LENGTH) {
            return (char) Integer.parseInt(sb.toString(), HEX_RADIX);
        } else {
            throw new JsonException("Expected a valid Unicode character, but actual is '\\u?'. Index=?", sb, iterator.getIndex());
        }
    }

    private static boolean isAsciiDigit(final char current) {
        return current >= '0' && current <= '9';
    }

    private static boolean isLatinLowercaseLetter(final char current) {
        return current >= 'a' && current <= 'f';
    }

    private static boolean isLaticUppercaseLetter(final char current) {
        return current >= 'A' && current <= 'F';
    }

    private JsonValueReader() {
    }
}
