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

import static io.rxmicro.json.internal.reader.JsonDelimiters.isIgnoredDelimiter;
import static io.rxmicro.json.internal.reader.JsonDelimiters.isJsonArrayDelimiter;
import static io.rxmicro.json.internal.reader.JsonDelimiters.isJsonObjectDelimiter;

/**
 * @author nedis
 * @since 0.5
 */
final class JsonPropertyReader {

    private static final int HEX_RADIX = 16;

    private static final int UNICODE_CHARACTER_LENGTH = 4;

    static String readPropertyName(final StringIterator iterator) {
        return readString(iterator, false);
    }

    static String readPropertyValue(final StringIterator iterator) {
        final StringBuilder sb = new StringBuilder();
        while (iterator.next()) {
            final char ch = iterator.getCurrent();
            if (ch == '"') {
                return readString(iterator, true);
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
            sb.append(iterator.getCurrent());
            if (sb.length() == UNICODE_CHARACTER_LENGTH) {
                break;
            }
        }
        if (sb.length() == UNICODE_CHARACTER_LENGTH) {
            return (char) Integer.parseInt(sb.toString(), HEX_RADIX);
        } else {
            throw new JsonException("Expected valid Unicode character at the end of string");
        }
    }

    private JsonPropertyReader() {
    }
}
