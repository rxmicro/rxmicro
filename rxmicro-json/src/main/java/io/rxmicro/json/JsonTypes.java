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

package io.rxmicro.json;

import java.util.List;
import java.util.Map;

/**
 * Utility class for all supported JSON types.
 *
 * @author nedis
 * @see JsonHelper
 * @see JsonNumber
 * @see JsonException
 * @since 0.1
 */
@SuppressWarnings({"unchecked", "JavaDoc"})
public final class JsonTypes {

    /**
     * The {@value #STRING} JSON type.
     */
    public static final String STRING = "string";

    /**
     * The {@value #NUMBER} JSON type.
     */
    public static final String NUMBER = "number";

    /**
     * The {@value #BOOLEAN} JSON type.
     */
    public static final String BOOLEAN = "boolean";

    /**
     * The {@value #OBJECT} JSON type.
     */
    public static final String OBJECT = "object";

    /**
     * The {@value #ARRAY} JSON type.
     */
    public static final String ARRAY = "array";

    /**
     * Returns {@code true} if the given object is a JSON {@code object}.
     *
     * @param object the tested object
     * @return {@code true} if the given object is a JSON {@code object}
     */
    public static boolean isJsonObject(final Object object) {
        return object instanceof Map;
    }

    /**
     * Casts the specified object to the {@link Map}{@code <String,Object>}.
     *
     * @param object the specified object
     * @return the {@link Map}{@code <String,Object>} casted instance
     * @throws JsonException if the specified object is not the JSON {@code object}
     */
    public static Map<String, Object> asJsonObject(final Object object) {
        try {
            return (Map<String, Object>) object;
        } catch (final ClassCastException ignore) {
            throw new JsonException("Not a json object: ?", object);
        }
    }

    /**
     * Returns {@code true} if the given object is a JSON {@code array}.
     *
     * @param object the tested object
     * @return {@code true} if the given object is a JSON {@code array}
     */
    public static boolean isJsonArray(final Object object) {
        return object instanceof List;
    }

    /**
     * Casts the specified object to the {@link List}{@code <Object>}.
     *
     * @param object the specified object
     * @return the {@link List}{@code <Object>} casted instance
     * @throws JsonException if the specified object is not the JSON {@code array}
     */
    public static List<Object> asJsonArray(final Object object) {
        try {
            return (List<Object>) object;
        } catch (final ClassCastException ignore) {
            throw new JsonException("Not a json array: ?", object);
        }
    }

    /**
     * Returns {@code true} if the given object is a JSON {@code string}.
     *
     * @param object the tested object
     * @return {@code true} if the given object is a JSON {@code string}
     */
    public static boolean isJsonString(final Object object) {
        return object instanceof String;
    }

    /**
     * Casts the specified object to the {@link String}.
     *
     * @param object the specified object
     * @return the {@link String} casted instance
     * @throws JsonException if the specified object is not the JSON {@code string}
     */
    public static String asJsonString(final Object object) {
        try {
            return (String) object;
        } catch (final ClassCastException ignore) {
            throw new JsonException("Not a json string: ?", object);
        }
    }

    /**
     * Returns {@code true} if the given object is a JSON {@code number}.
     *
     * @param object the tested object
     * @return {@code true} if the given object is a JSON {@code number}
     */
    public static boolean isJsonNumber(final Object object) {
        return object instanceof JsonNumber;
    }

    /**
     * Casts the specified object to the {@link JsonNumber}.
     *
     * @param object the specified object
     * @return the {@link JsonNumber} casted instance
     * @throws JsonException if the specified object is not the JSON {@code number}
     */
    public static JsonNumber asJsonNumber(final Object object) {
        try {
            return (JsonNumber) object;
        } catch (final ClassCastException ignore) {
            throw new JsonException("Not a json number: ?", object);
        }
    }

    /**
     * Returns {@code true} if the given object is a JSON {@code boolean}.
     *
     * @param object the tested object
     * @return {@code true} if the given object is a JSON {@code boolean}
     */
    public static boolean isJsonBoolean(final Object object) {
        return object instanceof Boolean;
    }

    /**
     * Casts the specified object to the {@link Boolean}.
     *
     * @param object the specified object
     * @return the {@link Boolean} casted instance
     * @throws JsonException if the specified object is not the JSON {@code boolean}
     */
    public static Boolean asJsonBoolean(final Object object) {
        try {
            return (Boolean) object;
        } catch (final ClassCastException ignore) {
            throw new JsonException("Not a json boolean: ?", object);
        }
    }

    private JsonTypes() {
    }
}
