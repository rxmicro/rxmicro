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
 * Utility class for converting Java types to JSON types and vice versa
 * <p>
 * Type conversion:
 * <p>
 * 1. From Java types to JSON types:
 * <p>
 * <table>
 *     <tr>
 *         <th>Java type</th>
 *         <th>JSON type</th>
 *     </tr>
 *     <tr>
 *         <td>{@link Map}{@code <String,Object>}</td>
 *         <td>{@code object}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link List}{@code <Object>}</td>
 *         <td>{@code array}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link Boolean}</td>
 *         <td>{@code boolean}</td>
 *     </tr>
 *     <tr>
 *         <td>{@code null}</td>
 *         <td>{@code null}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link String}</td>
 *         <td>{@code string}</td>
 *     </tr>
 *     <tr>
 *         <td>{@code ? extends }{@link Number}</td>
 *         <td>{@code number}</td>
 *     </tr>
 *     <tr>
 *         <td>{@code <any java class>}</td>
 *         <td>{@code string}</td>
 *     </tr>
 * </table>
 * <br>
 * 2. From Json types to Java types:
 * <p>
 * <table>
 *     <tr>
 *         <th>JSON type</th>
 *         <th>Java type</th>
 *     </tr>
 *     <tr>
 *         <td>{@code object}</td>
 *         <td>{@link Map}{@code <String,Object>}</td>
 *     </tr>
 *     <tr>
 *         <td>{@code array}</td>
 *         <td>{@link List}{@code <Object>}</td>
 *     </tr>
 *     <tr>
 *         <td>{@code boolean}</td>
 *         <td>{@link Boolean}</td>
 *     </tr>
 *     <tr>
 *         <td>{@code null}</td>
 *         <td>{@code null}</td>
 *     </tr>
 *     <tr>
 *         <td>{@code string}</td>
 *         <td>{@link String}</td>
 *     </tr>
 *     <tr>
 *         <td>{@code number}</td>
 *         <td>{@link JsonNumber}</td>
 *     </tr>
 * </table>
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class JsonHelper {

    /**
     * Default supported recursion depth
     */
    private static final int DEFAULT_RECURSION_DEPTH = 20;

    /**
     * Default value for human readability option.
     */
    private static final boolean DEFAULT_HUMAN_READABLE_OUTPUT = false;

    /**
     * Converts the java {@link Map}{@code <String,Object>} instance to the string representation of json object
     *
     * @param jsonObject    the java {@link Map}{@code <String,Object>} instance with data. See type conversion for more information
     * @param humanReadable the human readable or not output
     * @return the string representation of json object
     */
    public static String toJsonString(final Map<String, Object> jsonObject,
                                      final boolean humanReadable) {
        return JsonWriter.toJsonString(jsonObject, humanReadable);
    }

    /**
     * Converts the java {@link Map}{@code <String,Object>} instance to string representation of json object
     *
     * @param jsonObject the java {@link Map}{@code <String,Object>} instance with data. See type conversion for more information
     * @return the string representation of json object
     */
    public static String toJsonString(final Map<String, Object> jsonObject) {
        return toJsonString(jsonObject, DEFAULT_HUMAN_READABLE_OUTPUT);
    }

    /**
     * Converts the java {@link List}{@code <Object>} instance to the string representation of json array
     *
     * @param jsonArray     the java {@link List}{@code <Object>} instance with data. See type conversion for more information
     * @param humanReadable the human readable or not output
     * @return the string representation of json array
     */
    public static String toJsonString(final List<Object> jsonArray,
                                      final boolean humanReadable) {
        return JsonWriter.toJsonString(jsonArray, humanReadable);
    }

    /**
     * Converts the java {@link List}{@code <Object>} instance to string representation of json array
     *
     * @param jsonArray the java {@link List}{@code <Object>} instance with data. See type conversion for more information
     * @return the string representation of json array
     */
    public static String toJsonString(final List<Object> jsonArray) {
        return toJsonString(jsonArray, DEFAULT_HUMAN_READABLE_OUTPUT);
    }

    /**
     * Converts the java instance to string representation of json
     *
     * @param value         the java object. See type conversion for more information
     * @param humanReadable the human readable or not output
     * @return the string representation of json
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
     * Converts the java instance to string representation of json
     *
     * @param value the java object. See type conversion for more information
     * @return the string representation of json
     */
    public static String toJsonString(final Object value) {
        return toJsonString(value, DEFAULT_HUMAN_READABLE_OUTPUT);
    }

    /**
     * Convert the string representation of json object to the java {@link Map}{@code <String,Object>} instance
     *
     * @param jsonObject     the string representation of json object. See type conversion for more information
     * @param recursionDepth the recursion depth
     * @return the java {@link Map}{@code <String,Object>} instance with data
     * @throws JsonException         if json syntax error found or if stack overflow
     * @throws NumberFormatException if json number has invalid format
     */
    public static Map<String, Object> readJsonObject(final String jsonObject,
                                                     final int recursionDepth) {
        return JsonReader.readJsonObject(jsonObject, recursionDepth);
    }

    /**
     * Convert the string representation of json object to the java {@link Map}{@code <String,Object>} instance
     *
     * @param jsonObject the string representation of json object. See type conversion for more information
     * @return the java {@link Map}{@code <String,Object>} instance with data
     * @throws JsonException         if json syntax error found or if stack overflow
     * @throws NumberFormatException if json number has invalid format
     */
    public static Map<String, Object> readJsonObject(final String jsonObject) {
        return readJsonObject(jsonObject, DEFAULT_RECURSION_DEPTH);
    }

    /**
     * Convert the string representation of json array to the java {@link List}{@code <Object>} instance
     *
     * @param jsonArray      the string representation of json array. See type conversion for more information
     * @param recursionDepth the recursion depth
     * @return the java {@link List}{@code <Object>} instance with data
     * @throws JsonException         if json syntax error found or if stack overflow
     * @throws NumberFormatException if json number has invalid format
     */
    public static List<Object> readJsonArray(final String jsonArray,
                                             final int recursionDepth) {
        return JsonReader.readJsonArray(jsonArray, recursionDepth);
    }

    /**
     * Convert the string representation of json array to java {@link List}{@code <Object>} instance
     *
     * @param jsonArray the string representation of json array. See type conversion for more information
     * @return the java {@link List}{@code <Object>} instance with data
     * @throws JsonException         if json syntax error found or if stack overflow
     * @throws NumberFormatException if json number has invalid format
     */
    public static List<Object> readJsonArray(final String jsonArray) {
        return readJsonArray(jsonArray, DEFAULT_RECURSION_DEPTH);
    }

    /**
     * Convert a string representation of json to the java object
     *
     * @param json           the string representation of json
     * @param recursionDepth the recursion depth
     * @return the java instance with data. See type conversion for more information.
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
     * Convert the string representation of json to the java object
     *
     * @param json the string representation of json data
     * @return the java instance with data. See type conversion for more information.
     * @throws JsonException         if json syntax error found or if stack overflow
     * @throws NumberFormatException if json number has invalid format
     */
    public static Object readJson(final String json) {
        return readJson(json, DEFAULT_RECURSION_DEPTH);
    }

    private JsonHelper() {
    }
}
