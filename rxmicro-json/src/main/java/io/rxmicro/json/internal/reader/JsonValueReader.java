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
import static io.rxmicro.json.internal.reader.JsonStringValueReader.readString;
import static io.rxmicro.json.internal.reader.JsonValueConverters.convertJsonValue;

/**
 * @author nedis
 * @since 0.5
 */
final class JsonValueReader {

    static String readPropertyName(final char ch,
                                   final StringIterator iterator) {
        if (ch != '"') {
            throw new JsonException("Expected '\"'. Index=?", iterator.getIndex());
        }
        return readString(iterator, false);
    }

    static Object readPrimitiveValue(final StringIterator iterator) {
        return convertJsonValue(readValue(iterator), iterator.getIndex());
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

    private JsonValueReader() {
    }
}
