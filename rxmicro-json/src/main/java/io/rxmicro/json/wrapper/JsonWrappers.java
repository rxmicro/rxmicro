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

package io.rxmicro.json.wrapper;

import io.rxmicro.json.JsonException;

import static io.rxmicro.json.JsonHelper.readJson;
import static io.rxmicro.json.JsonTypes.asJsonArray;
import static io.rxmicro.json.JsonTypes.asJsonObject;

/**
 * Json wrapper util class that simplifies the work with Json format manually.
 *
 * <p>
 * The RxMicro framework uses classes from the {@code rxmicro.json} module when automatically converting Java models
 * to JSON format and vice versa.
 *
 * <p>
 * Therefore, a developer should not explicitly use this module!
 *
 * <p>
 * But sometimes it is necessary to work with JSON format manually: for test writing, geo coding, etc...
 * For such cases the RxMicro framework introduces wrapper classes.
 *
 * @author nedis
 * @since 0.9
 */
public final class JsonWrappers {

    /**
     * Returns the {@link JsonObject} wrapper instance for the provided string representation of JSON object.
     *
     * @param jsonObject the provided string representation of JSON object.
     * @return the {@link JsonObject} wrapper instance for the provided string representation of JSON object.
     * @throws JsonException if the provided string if not a string representation of JSON object.
     */
    public static JsonObject readJsonObject(final String jsonObject) {
        return toJsonObject(readJson(jsonObject));
    }

    /**
     * Returns the {@link JsonObject} wrapper instance for the provided internal view of JSON object.
     *
     * <p>
     * Since {@code rxmicro.json} module is used automatically, it is optimized for machine operations.
     * Therefore, this module doesn’t provide separate classes for JSON types. Instead, standard Java classes are used.
     * The RxMicro framework uses the {@link java.util.Map}{@code <}{@link String}{@code ,}{@link Object}{@code >} interface
     * to store JSON object.
     *
     * <p>
     * Thus the {@code internalViewOfJsonObject} must be
     * a {@link java.util.Map}{@code <}{@link String}{@code ,}{@link Object}{@code >} instance for correct work of this method,
     * otherwise a {@link JsonException} will be thrown.
     *
     * @param internalViewOfJsonObject the provided internal view of JSON object.
     * @return the {@link JsonObject} wrapper instance for the provided internal view of JSON object.
     * @throws JsonException if the provided string if not a string representation of JSON object.
     */
    public static JsonObject toJsonObject(final Object internalViewOfJsonObject) {
        return new JsonObject(asJsonObject(internalViewOfJsonObject));
    }

    /**
     * Returns the {@link JsonArray} wrapper instance for the provided string representation of JSON array.
     *
     * @param jsonArray the provided string representation of JSON array.
     * @return the {@link JsonArray} wrapper instance for the provided string representation of JSON array.
     * @throws JsonException if the provided string if not a string representation of JSON array.
     */
    public static JsonArray readJsonArray(final String jsonArray) {
        return toJsonArray(readJson(jsonArray));
    }

    /**
     * Returns the {@link JsonArray} wrapper instance for the provided internal view of JSON array.
     *
     * <p>
     * Since {@code rxmicro.json} module is used automatically, it is optimized for machine operations.
     * Therefore, this module doesn’t provide separate classes for JSON types. Instead, standard Java classes are used.
     * The RxMicro framework uses the {@link java.util.List}{@code <}{@link Object}{@code >} interface to store JSON array.
     *
     * <p>
     * Thus the {@code internalViewOfJsonArray} must be
     * a {@link java.util.List}{@code <}{@link Object}{@code >} instance for correct work of this method,
     * otherwise a {@link JsonException} will be thrown.
     *
     * @param internalViewOfJsonArray the provided internal view of JSON array.
     * @return the {@link JsonArray} wrapper instance for the provided internal view of JSON array.
     * @throws JsonException if the provided string if not a string representation of JSON array.
     */
    public static JsonArray toJsonArray(final Object internalViewOfJsonArray) {
        return new JsonArray(asJsonArray(internalViewOfJsonArray));
    }

    private JsonWrappers() {
    }
}
