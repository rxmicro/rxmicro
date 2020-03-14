/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.test.json.internal;

import io.rxmicro.json.JsonNumber;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.rxmicro.common.util.ExCollections.isUnmodifiableList;
import static io.rxmicro.common.util.ExCollections.isUnmodifiableMap;
import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class JsonConverter {

    public static Object convertIfNecessary(final Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            if (value instanceof JsonNumber) {
                return value;
            } else if (value instanceof BigDecimal) {
                return new JsonNumber(((BigDecimal) value).toPlainString());
            } else {
                return new JsonNumber(value.toString());
            }
        } else if (value instanceof Boolean) {
            return value;
        } else if (value instanceof Map) {
            if (isUnmodifiableMap(value)) {
                validateJsonObject((Map<?, ?>) value);
                return value;
            } else {
                throw new IllegalArgumentException(
                        "Map is not valid json object value! Use jsonObject() factory method instead!"
                );
            }
        } else if (value instanceof Iterable) {
            if (isUnmodifiableList(value)) {
                validateJsonArray((List<?>) value);
                return value;
            } else {
                throw new IllegalArgumentException(
                        "Iterable is not valid json object value! Use jsonArray() factory method instead!"
                );
            }
        } else if (value.getClass().isArray()) {
            throw new IllegalArgumentException(
                    "Java array is not valid json object value! Use jsonArray() factory method instead!"
            );
        } else if (value instanceof Enum<?>) {
            return ((Enum<?>) value).name();
        } else {
            return value.toString();
        }
    }

    private static void validateJsonArray(final List<?> value) {
        for (final Object o : value) {
            validateJsonItem(o);
        }
    }

    private static void validateJsonObject(final Map<?, ?> map) {
        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            validateJsonItem(entry.getValue());
        }
    }

    private static void validateJsonItem(final Object o) {
        if (o instanceof List) {
            validateJsonArray((List<?>) o);
        } else if (o instanceof Map) {
            validateJsonObject((Map<?, ?>) o);
        } else {
            validatePrimitive(o);
        }
    }

    private static void validatePrimitive(final Object o) {
        if (o instanceof JsonNumber || o instanceof Boolean || o instanceof String || o == null) {
            return;
        }
        throw new IllegalArgumentException(format(
                "'?' class is not valid JSON primitive. Use jsonObject() or jsonArray() factory methods instead!",
                o.getClass().getName()));
    }

    private JsonConverter() {
    }
}
