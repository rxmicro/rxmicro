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

import io.rxmicro.json.JsonException;
import io.rxmicro.json.JsonNumber;

/**
 * @author nedis
 * @since 0.10
 */
final class JsonValueConverters {

    static Object convertJsonValue(final String value,
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

    private JsonValueConverters() {
    }
}
