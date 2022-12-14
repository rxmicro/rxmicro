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

package io.rxmicro.test.json.internal;

import io.rxmicro.json.JsonNumber;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;
import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedMap;
import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.1
 */
public final class JsonConverter {

    @SuppressWarnings("unchecked")
    public static Object convertIfNecessary(final Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Map) {
            return convertJsonObject((Map<String, ?>) value);
        } else if (value instanceof Iterable) {
            return convertJsonArray((Iterable<?>) value);
        } else if (value.getClass().isArray()) {
            return convertJsonArray(value);
        } else {
            return convertJsonPrimitive(value);
        }
    }

    private static Map<String, Object> convertJsonObject(final Map<String, ?> map) {
        final Map<String, Object> result = new LinkedHashMap<>();
        for (final Map.Entry<String, ?> entry : map.entrySet()) {
            result.put(entry.getKey(), convertIfNecessary(entry.getValue()));
        }
        return unmodifiableOrderedMap(result);
    }

    private static List<Object> convertJsonArray(final Object array) {
        final List<Object> result = new ArrayList<>();
        final int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            result.add(convertIfNecessary(Array.get(array, i)));
        }
        return unmodifiableList(result);
    }

    private static List<Object> convertJsonArray(final Iterable<?> iterable) {
        final List<Object> result = new ArrayList<>();
        for (final Object item : iterable) {
            result.add(convertIfNecessary(item));
        }
        return unmodifiableList(result);
    }

    private static Object convertJsonPrimitive(final Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Enum<?> || value instanceof CharSequence || value instanceof Temporal) {
            return value.toString();
        } else if (value instanceof Boolean || value instanceof JsonNumber) {
            return value;
        } else if (value instanceof Number) {
            return convertJavaNumber((Number) value);
        } else {
            throw new IllegalArgumentException(
                    format("Can't convert '?' value of '?' class to json primitive!", value, value.getClass().getName())
            );
        }
    }

    private static Object convertJavaNumber(final Number value) {
        if (value instanceof BigDecimal) {
            return new JsonNumber(((BigDecimal) value).toPlainString());
        } else {
            return new JsonNumber(value.toString());
        }
    }

    private JsonConverter() {
    }
}
