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
 * The utility class that can be used for convenient creation of the expected JSON object.
 * <p>
 * This class arranges JSON properties and automatically converts all {@code ? extends }{@link Number} types into
 * the {@link io.rxmicro.json.JsonNumber} type.
 *
 * @author nedis
 * @see io.rxmicro.json.JsonHelper
 * @see io.rxmicro.json.JsonNumber
 * @see io.rxmicro.json.JsonTypes
 * @see JsonObjectBuilder
 * @see io.rxmicro.json.JsonArrayBuilder
 * @since 0.1
 */
public final class JsonFactory {

    /**
     * Returns the short-lived unmodified ordered {@link Map} that represents a JSON object with
     * the specified error message according to standard error JSON model structure.
     * <p>
     * The RxMicro framework defines a standard JSON model which is returned in case of any error:
     * <pre>
     * {
     *    "message": "Not Found"
     * }
     * </pre>
     * <p>
     * (Read more: {@link io.rxmicro.http.error.HttpErrorException})
     * <p>
     * <i>(FYI: This method uses {@link io.rxmicro.common.util.Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param message the error message template
     * @param args the error message template arguments
     * @return the short-lived unmodified ordered {@link Map} that represents a JSON object with
     *          the specified error message according to standard error JSON model structure.
     * @throws NullPointerException if the error message template is {@code null}
     * @throws IllegalArgumentException if detected a redundant placeholder or missing argument
     * @see io.rxmicro.http.error.HttpErrorException
     */
    public static Map<String, Object> jsonErrorObject(final String message,
                                                      final Object... args) {
        return new JsonObjectBuilder()
                .put(MESSAGE, format(message, args))
                .build();
    }

    /**
     * Returns the short-lived unmodified ordered {@link Map} that represents the empty JSON object
     *
     * @return the short-lived unmodified ordered {@link Map} that represents the empty JSON object
     */
    public static Map<String, Object> jsonObject() {
        return Map.of();
    }

    /**
     * Returns the short-lived unmodified ordered {@link Map} that represents the JSON object with one property
     *
     * @param name the property name
     * @param value the property value
     * @return the short-lived unmodified ordered {@link Map} containing the provided JSON properties.
     */
    public static Map<String, Object> jsonObject(final String name, final Object value) {
        return jsonObjectInternal(name, value);
    }

    /**
     * Returns the short-lived unmodified ordered {@link Map} that represents the JSON object with two properties
     *
     * @param name1 the first property name
     * @param value1 the first property value
     * @param name2 the second property name
     * @param value2 the second property value
     * @return the short-lived unmodified ordered {@link Map} containing the provided JSON properties.
     * @throws IllegalArgumentException if any property value contains invalid data
     */
    public static Map<String, Object> jsonObject(final String name1, final Object value1,
                                                 final String name2, final Object value2) {
        return jsonObjectInternal(
                name1, value1, name2, value2
        );
    }

    /**
     * Returns the short-lived unmodified ordered {@link Map} that represents the JSON object with three properties
     *
     * @param name1 the first property name
     * @param value1 the first property value
     * @param name2 the second property name
     * @param value2 the second property value
     * @param name3 the third property name
     * @param value3 the third property value
     * @return the short-lived unmodified ordered {@link Map} containing the provided JSON properties.
     * @throws IllegalArgumentException if any property value contains invalid data
     */
    public static Map<String, Object> jsonObject(final String name1, final Object value1,
                                                 final String name2, final Object value2,
                                                 final String name3, final Object value3) {
        return jsonObjectInternal(
                name1, value1, name2, value2, name3, value3
        );
    }

    /**
     * Returns the short-lived unmodified ordered {@link Map} that represents the JSON object with four properties
     *
     * @param name1 the first property name
     * @param value1 the first property value
     * @param name2 the second property name
     * @param value2 the second property value
     * @param name3 the third property name
     * @param value3 the third property value
     * @param name4 the fourth property name
     * @param value4 the fourth property value
     * @return the short-lived unmodified ordered {@link Map} containing the provided JSON properties.
     * @throws IllegalArgumentException if any property value contains invalid data
     */
    public static Map<String, Object> jsonObject(final String name1, final Object value1,
                                                 final String name2, final Object value2,
                                                 final String name3, final Object value3,
                                                 final String name4, final Object value4) {
        return jsonObjectInternal(
                name1, value1, name2, value2, name3, value3, name4, value4
        );
    }

    /**
     * Returns the short-lived unmodified ordered {@link Map} that represents the JSON object with five properties
     *
     * @param name1 the first property name
     * @param value1 the first property value
     * @param name2 the second property name
     * @param value2 the second property value
     * @param name3 the third property name
     * @param value3 the third property value
     * @param name4 the fourth property name
     * @param value4 the fourth property value
     * @param name5 the fifth property name
     * @param value5 the fifth property value
     * @return the short-lived unmodified ordered {@link Map} containing the provided JSON properties.
     * @throws IllegalArgumentException if any property value contains invalid data
     */
    public static Map<String, Object> jsonObject(final String name1, final Object value1,
                                                 final String name2, final Object value2,
                                                 final String name3, final Object value3,
                                                 final String name4, final Object value4,
                                                 final String name5, final Object value5) {
        return jsonObjectInternal(
                name1, value1, name2, value2, name3, value3, name4, value4, name5, value5
        );
    }

    /**
     * Returns the short-lived unmodified ordered {@link Map} that represents the JSON object with six properties
     *
     * @param name1 the first property name
     * @param value1 the first property value
     * @param name2 the second property name
     * @param value2 the second property value
     * @param name3 the third property name
     * @param value3 the third property value
     * @param name4 the fourth property name
     * @param value4 the fourth property value
     * @param name5 the fifth property name
     * @param value5 the fifth property value
     * @param name6 the sixth property name
     * @param value6 the sixth property value
     * @return the short-lived unmodified ordered {@link Map} containing the provided JSON properties.
     * @throws IllegalArgumentException if any property value contains invalid data
     */
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

    /**
     * Returns the short-lived unmodified ordered {@link Map} that represents the JSON object with seven properties
     *
     * @param name1 the first property name
     * @param value1 the first property value
     * @param name2 the second property name
     * @param value2 the second property value
     * @param name3 the third property name
     * @param value3 the third property value
     * @param name4 the fourth property name
     * @param value4 the fourth property value
     * @param name5 the fifth property name
     * @param value5 the fifth property value
     * @param name6 the sixth property name
     * @param value6 the sixth property value
     * @param name7 the seventh property name
     * @param value7 the seventh property value
     * @return the short-lived unmodified ordered {@link Map} containing the provided JSON properties.
     * @throws IllegalArgumentException if any property value contains invalid data
     */
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

    /**
     * Returns the short-lived unmodified ordered {@link Map} that represents the JSON object with eigth properties
     *
     * @param name1 the first property name
     * @param value1 the first property value
     * @param name2 the second property name
     * @param value2 the second property value
     * @param name3 the third property name
     * @param value3 the third property value
     * @param name4 the fourth property name
     * @param value4 the fourth property value
     * @param name5 the fifth property name
     * @param value5 the fifth property value
     * @param name6 the sixth property name
     * @param value6 the sixth property value
     * @param name7 the seventh property name
     * @param value7 the seventh property value
     * @param name8 the eighth property name
     * @param value8 the eighth property value
     * @return the short-lived unmodified ordered {@link Map} containing the provided JSON properties.
     * @throws IllegalArgumentException if any property value contains invalid data
     */
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

    /**
     * Returns the short-lived unmodified ordered {@link Map} that represents the JSON object with nine properties
     *
     * @param name1 the first property name
     * @param value1 the first property value
     * @param name2 the second property name
     * @param value2 the second property value
     * @param name3 the third property name
     * @param value3 the third property value
     * @param name4 the fourth property name
     * @param value4 the fourth property value
     * @param name5 the fifth property name
     * @param value5 the fifth property value
     * @param name6 the sixth property name
     * @param value6 the sixth property value
     * @param name7 the seventh property name
     * @param value7 the seventh property value
     * @param name8 the eighth property name
     * @param value8 the eighth property value
     * @param name9 the ninth property name
     * @param value9 the ninth property value
     * @return the short-lived unmodified ordered {@link Map} containing the provided JSON properties.
     * @throws IllegalArgumentException if any property value contains invalid data
     */
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

    /**
     * Returns the short-lived unmodified ordered {@link Map} that represents the JSON object with ten properties
     *
     * @param name1 the first property name
     * @param value1 the first property value
     * @param name2 the second property name
     * @param value2 the second property value
     * @param name3 the third property name
     * @param value3 the third property value
     * @param name4 the fourth property name
     * @param value4 the fourth property value
     * @param name5 the fifth property name
     * @param value5 the fifth property value
     * @param name6 the sixth property name
     * @param value6 the sixth property value
     * @param name7 the seventh property name
     * @param value7 the seventh property value
     * @param name8 the eighth property name
     * @param value8 the eighth property value
     * @param name9 the ninth property name
     * @param value9 the ninth property value
     * @param name10 the tenth property name
     * @param value10 the tenth property value
     * @return the short-lived unmodified ordered {@link Map} containing the provided JSON properties.
     * @throws IllegalArgumentException if any property value contains invalid data
     */
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

    /**
     * Returns the short-lived unmodified ordered {@link Map} that represents the JSON object
     * with properties extracted from the given entries.
     *
     * @param entries the given entries
     * @return the short-lived unmodified ordered {@link Map} containing the provided JSON properties.
     * @throws IllegalArgumentException if any property value contains invalid data
     */
    @SafeVarargs
    public static Map<String, Object> jsonObject(final Map.Entry<String, Object>... entries) {
        final JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder();
        for (final Map.Entry<String, Object> entry : entries) {
            jsonObjectBuilder.put(entry.getKey(), convertIfNecessary(entry.getValue()));
        }
        return jsonObjectBuilder.build();
    }

    /**
     * Merges the specified json objects into single json object
     *
     * @param jsonObjects the {@link Map} array that represents the JSON objects to merge
     * @return the short-lived unmodified ordered {@link Map} that represents the merged JSON object
     * @throws IllegalArgumentException if any property value contains invalid data or detected a duplicate of property name
     */
    @SafeVarargs
    public static Map<String, Object> jsonObject(final Map<String, Object>... jsonObjects) {
        final JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder(true);
        for (final Map<String, Object> jsonObject : jsonObjects) {
            for (final Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                jsonObjectBuilder.put(entry.getKey(), convertIfNecessary(entry.getValue()));
            }
        }
        return jsonObjectBuilder.build();
    }

    /**
     * Returns the short-lived unmodified {@link List} that represents the JSON array with the provided items
     *
     * @param objects JSON array items
     * @return the short-lived unmodified {@link List} that represents the JSON array with the provided items
     */
    public static List<Object> jsonArray(final Object... objects) {
        return jsonArray(Arrays.asList(objects));
    }

    /**
     * Returns the short-lived unmodified {@link List} that represents the JSON array with the provided items
     *
     * @param items JSON array items
     * @return the short-lived unmodified {@link List} that represents the JSON array with the provided items
     */
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
