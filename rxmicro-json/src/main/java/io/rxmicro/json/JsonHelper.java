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

import io.rxmicro.json.internal.reader.JsonReader;
import io.rxmicro.json.internal.writer.JsonWriter;

import java.util.List;
import java.util.Map;

import static io.rxmicro.common.util.Strings.startsWith;

/**
 * Type conversion:
 * <p>
 * 1. From Java types to JSON types:
 * <p>
 * Java                              JSON
 * -----------------------------------------------
 * {@code Map<String, Object>}          ->   object
 * {@code List<Object>}                 ->   array
 * {@code java.lang.Boolean}            ->   true / false
 * {@code null}                         ->   null
 * {@code java.lang.String}             ->   string
 * {@code ? extends java.lang.Number}   ->   number
 * {@code <any java class>}             ->   string
 * <p>
 * 2. From Json types to Java types:
 * <p>
 * JSON             Java
 * -----------------------------------------------
 * object   ->      Map<String, Object>
 * array    ->      List<Object>
 * boolean  ->      java.lang.Boolean
 * null     ->      null
 * string   ->      java.lang.String
 * number   ->      io.rxmicro.json.JsonNumber
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class JsonHelper {

    /**
     *
     */
    private static final int DEFAULT_RECURSION_DEPTH = 20;

    /**
     *
     */
    private static final boolean DEFAULT_HUMAN_READABLE_OUTPUT = false;

    /**
     * Converts java map instance to string representation of json object
     *
     * @param jsonObject    java map instance with data. See type conversion for more information
     * @param humanReadable human readable or not output
     * @return string representation of json object
     */
    public static String toJsonString(final Map<String, Object> jsonObject,
                                      final boolean humanReadable) {
        return JsonWriter.toJsonString(jsonObject, humanReadable);
    }

    /**
     * Converts java map instance to string representation of json object
     *
     * @param jsonObject java map instance with data. See type conversion for more information
     * @return string representation of json object
     */
    public static String toJsonString(final Map<String, Object> jsonObject) {
        return toJsonString(jsonObject, DEFAULT_HUMAN_READABLE_OUTPUT);
    }

    /**
     * Converts java list instance to string representation of json array
     *
     * @param jsonArray     java list instance with data. See type conversion for more information
     * @param humanReadable human readable or not output
     * @return string representation of json array
     */
    public static String toJsonString(final List<Object> jsonArray,
                                      final boolean humanReadable) {
        return JsonWriter.toJsonString(jsonArray, humanReadable);
    }

    /**
     * Converts java list instance to string representation of json array
     *
     * @param jsonArray java list instance with data. See type conversion for more information
     * @return string representation of json array
     */
    public static String toJsonString(final List<Object> jsonArray) {
        return toJsonString(jsonArray, DEFAULT_HUMAN_READABLE_OUTPUT);
    }

    /**
     * Converts java instance to string representation of json
     *
     * @param value         java object. See type conversion for more information
     * @param humanReadable human readable or not output
     * @return string representation of json
     */
    @SuppressWarnings("unchecked")
    public static String toJsonString(final Object value,
                                      final boolean humanReadable) {
        if (value instanceof Map) {
            return toJsonString((Map<String, Object>) value, humanReadable);
        } else if (value instanceof List) {
            return toJsonString((List<Object>) value, humanReadable);
        } else {
            return JsonWriter.toJsonString(value, humanReadable);
        }
    }

    /**
     * Converts java instance to string representation of json
     *
     * @param value java object. See type conversion for more information
     * @return string representation of json
     */
    public static String toJsonString(final Object value) {
        return toJsonString(value, DEFAULT_HUMAN_READABLE_OUTPUT);
    }

    /**
     * Convert a string representation of json object to java map instance
     *
     * @param jsonObject     string representation of json object. See type conversion for more information
     * @param recursionDepth - max recursion depth
     * @return java map instance with data
     * @throws JsonException         if json syntax error found or if stack overflow
     * @throws NumberFormatException if json number has invalid format
     */
    public static Map<String, Object> readJsonObject(final String jsonObject,
                                                     final int recursionDepth) {
        return JsonReader.readJsonObject(jsonObject, recursionDepth);
    }

    /**
     * Convert a string representation of json object to java map instance
     *
     * @param jsonObject string representation of json object. See type conversion for more information
     * @return java map instance with data
     * @throws JsonException         if json syntax error found or if stack overflow
     * @throws NumberFormatException if json number has invalid format
     */
    public static Map<String, Object> readJsonObject(final String jsonObject) {
        return readJsonObject(jsonObject, DEFAULT_RECURSION_DEPTH);
    }

    /**
     * Convert a string representation of json array to java list instance
     *
     * @param jsonArray      string representation of json array. See type conversion for more information
     * @param recursionDepth - max recursion depth
     * @return java list instance with data
     * @throws JsonException         if json syntax error found or if stack overflow
     * @throws NumberFormatException if json number has invalid format
     */
    public static List<Object> readJsonArray(final String jsonArray,
                                             final int recursionDepth) {
        return JsonReader.readJsonArray(jsonArray, recursionDepth);
    }

    /**
     * Convert a string representation of json array to java list instance
     *
     * @param jsonArray string representation of json array. See type conversion for more information
     * @return java list instance with data
     * @throws JsonException         if json syntax error found or if stack overflow
     * @throws NumberFormatException if json number has invalid format
     */
    public static List<Object> readJsonArray(final String jsonArray) {
        return readJsonArray(jsonArray, DEFAULT_RECURSION_DEPTH);
    }

    /**
     * Convert a string representation of json to java object
     *
     * @param json           string representation of json
     * @param recursionDepth - max recursion depth
     * @return java instance with data. See type conversion for more information.
     * @throws JsonException         if json syntax error found or if stack overflow
     * @throws NumberFormatException if json number has invalid format
     */
    public static Object readJson(final String json,
                                  final int recursionDepth) {
        final String trimJson = json.trim();
        if (trimJson.isEmpty()) {
            throw new JsonException("empty string");
        }
        if (startsWith(trimJson, '{')) {
            return JsonReader.readJsonObject(json, recursionDepth);
        } else if (startsWith(trimJson, '[')) {
            return JsonReader.readJsonArray(json, recursionDepth);
        } else {
            return JsonReader.readJsonPrimitive(json);
        }
    }

    /**
     * Convert a string representation of json to java object
     *
     * @param json string representation of json data
     * @return java instance with data. See type conversion for more information.
     * @throws JsonException         if json syntax error found or if stack overflow
     * @throws NumberFormatException if json number has invalid format
     */
    public static Object readJson(final String json) {
        return readJson(json, DEFAULT_RECURSION_DEPTH);
    }

    private JsonHelper() {
    }
}
