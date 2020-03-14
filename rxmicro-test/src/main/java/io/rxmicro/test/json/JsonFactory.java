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

package io.rxmicro.test.json;

import io.rxmicro.json.JsonObjectBuilder;
import io.rxmicro.test.json.internal.JsonConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.rest.server.exchange.json.local.Constants.MESSAGE;
import static io.rxmicro.test.json.internal.JsonConverter.convertIfNecessary;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class JsonFactory {

    public static Map<String, Object> jsonErrorObject(final String message,
                                                      final Object... args) {
        return new JsonObjectBuilder()
                .put(MESSAGE, format(message, args))
                .build();
    }

    public static Map<String, Object> jsonObject() {
        return Map.of();
    }

    public static Map<String, Object> jsonObject(final String name, final Object value) {
        return jsonObjectInternal(name, value);
    }

    public static Map<String, Object> jsonObject(final String name1, final Object value1,
                                                 final String name2, final Object value2) {
        return jsonObjectInternal(
                name1, value1, name2, value2
        );
    }

    public static Map<String, Object> jsonObject(final String name1, final Object value1,
                                                 final String name2, final Object value2,
                                                 final String name3, final Object value3) {
        return jsonObjectInternal(
                name1, value1, name2, value2, name3, value3
        );
    }

    public static Map<String, Object> jsonObject(final String name1, final Object value1,
                                                 final String name2, final Object value2,
                                                 final String name3, final Object value3,
                                                 final String name4, final Object value4) {
        return jsonObjectInternal(
                name1, value1, name2, value2, name3, value3, name4, value4
        );
    }

    public static Map<String, Object> jsonObject(final String name1, final Object value1,
                                                 final String name2, final Object value2,
                                                 final String name3, final Object value3,
                                                 final String name4, final Object value4,
                                                 final String name5, final Object value5) {
        return jsonObjectInternal(
                name1, value1, name2, value2, name3, value3, name4, value4, name5, value5
        );
    }

    public static Map<String, Object> jsonObject(final String name1, final Object value1,
                                                 final String name2, final Object value2,
                                                 final String name3, final Object value3,
                                                 final String name4, final Object value4,
                                                 final String name5, final Object value5,
                                                 final String name6, final Object value6) {
        return jsonObjectInternal(
                name1, value1, name2, value2, name3, value3,
                name4, value4, name5, value5, name6, value6
        );
    }

    public static Map<String, Object> jsonObject(final String name1, final Object value1,
                                                 final String name2, final Object value2,
                                                 final String name3, final Object value3,
                                                 final String name4, final Object value4,
                                                 final String name5, final Object value5,
                                                 final String name6, final Object value6,
                                                 final String name7, final Object value7) {
        return jsonObjectInternal(
                name1, value1, name2, value2, name3, value3, name4, value4,
                name5, value5, name6, value6, name7, value7
        );
    }

    public static Map<String, Object> jsonObject(final String name1, final Object value1,
                                                 final String name2, final Object value2,
                                                 final String name3, final Object value3,
                                                 final String name4, final Object value4,
                                                 final String name5, final Object value5,
                                                 final String name6, final Object value6,
                                                 final String name7, final Object value7,
                                                 final String name8, final Object value8) {
        return jsonObjectInternal(
                name1, value1, name2, value2, name3, value3, name4, value4,
                name5, value5, name6, value6, name7, value7, name8, value8
        );
    }

    public static Map<String, Object> jsonObject(final String name1, final Object value1,
                                                 final String name2, final Object value2,
                                                 final String name3, final Object value3,
                                                 final String name4, final Object value4,
                                                 final String name5, final Object value5,
                                                 final String name6, final Object value6,
                                                 final String name7, final Object value7,
                                                 final String name8, final Object value8,
                                                 final String name9, final Object value9) {
        return jsonObjectInternal(
                name1, value1, name2, value2, name3, value3, name4, value4, name5, value5,
                name6, value6, name7, value7, name8, value8, name9, value9
        );
    }

    public static Map<String, Object> jsonObject(final String name1, final Object value1,
                                                 final String name2, final Object value2,
                                                 final String name3, final Object value3,
                                                 final String name4, final Object value4,
                                                 final String name5, final Object value5,
                                                 final String name6, final Object value6,
                                                 final String name7, final Object value7,
                                                 final String name8, final Object value8,
                                                 final String name9, final Object value9,
                                                 final String name10, final Object value10) {
        return jsonObjectInternal(
                name1, value1, name2, value2, name3, value3, name4, value4, name5, value5,
                name6, value6, name7, value7, name8, value8, name9, value9, name10, value10
        );
    }

    @SafeVarargs
    public static Map<String, Object> jsonObject(final Map.Entry<String, Object>... entries) {
        final JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder();
        for (final Map.Entry<String, Object> entry : entries) {
            jsonObjectBuilder.put(entry.getKey(), convertIfNecessary(entry.getValue()));
        }
        return jsonObjectBuilder.build();
    }

    @SafeVarargs
    public static Map<String, Object> jsonObject(final Map<String, Object>... jsonObjects) {
        final JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder();
        for (final Map<String, Object> jsonObject : jsonObjects) {
            for (final Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                jsonObjectBuilder.put(entry.getKey(), convertIfNecessary(entry.getValue()));
            }
        }
        return jsonObjectBuilder.build();
    }

    public static List<Object> jsonArray(final Object... objects) {
        return jsonArray(Arrays.asList(objects));
    }

    public static List<Object> jsonArray(final List<Object> items) {
        return unmodifiableList(
                items.stream()
                        .map(JsonConverter::convertIfNecessary)
                        .collect(toList())
        );
    }

    private static Map<String, Object> jsonObjectInternal(final Object... args) {
        final JsonObjectBuilder builder = new JsonObjectBuilder();
        for (int i = 0; i < args.length; i += 2) {
            builder.put((String) args[i], convertIfNecessary(args[i + 1]));
        }
        return builder.build();
    }

    private JsonFactory() {
    }
}
