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

package io.rxmicro.json;

import java.util.List;
import java.util.Map;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("unchecked")
public final class JsonTypes {

    public static final String STRING = "string";

    public static final String NUMBER = "number";

    public static final String BOOLEAN = "boolean";

    public static final String OBJECT = "object";

    public static final String ARRAY = "array";

    public static boolean isJsonObject(final Object o) {
        return o instanceof Map;
    }

    public static Map<String, Object> asJsonObject(final Object o) {
        try {
            return (Map<String, Object>) o;
        } catch (final ClassCastException e) {
            throw new JsonException("Not a json object: " + o);
        }
    }

    public static boolean isJsonArray(final Object o) {
        return o instanceof List;
    }

    public static List<Object> asJsonArray(final Object o) {
        try {
            return (List<Object>) o;
        } catch (final ClassCastException e) {
            throw new JsonException("Not a json array: " + o);
        }
    }

    public static boolean isJsonString(final Object o) {
        return o instanceof String;
    }

    public static String asJsonString(final Object o) {
        try {
            return (String) o;
        } catch (final ClassCastException e) {
            throw new JsonException("Not a json string: " + o);
        }
    }

    public static boolean isJsonNumber(final Object o) {
        return o instanceof JsonNumber;
    }

    public static JsonNumber asJsonNumber(final Object o) {
        try {
            return (JsonNumber) o;
        } catch (final ClassCastException e) {
            throw new JsonException("Not a json number: " + o);
        }
    }

    public static boolean isJsonBoolean(final Object o) {
        return o instanceof Boolean;
    }

    public static Boolean asJsonBoolean(final Object o) {
        try {
            return (Boolean) o;
        } catch (final ClassCastException e) {
            throw new JsonException("Not a json boolean: " + o);
        }
    }

    private JsonTypes() {
    }
}
