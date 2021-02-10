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

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.json.JsonException;
import io.rxmicro.json.JsonNumber;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.json.JsonTypes.asJsonArray;
import static io.rxmicro.json.JsonTypes.asJsonBoolean;
import static io.rxmicro.json.JsonTypes.asJsonNumber;
import static io.rxmicro.json.JsonTypes.asJsonObject;
import static io.rxmicro.json.JsonTypes.asJsonString;
import static io.rxmicro.json.internal.util.Assertions.requirePropertyValue;
import static io.rxmicro.json.internal.util.UpdatableJsonUtils.asUpdatableJsonObject;
import static java.util.Collections.unmodifiableMap;

/**
 * Json object wrapper class that simplifies the work with Json format manually.
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
public final class JsonObject implements Iterable<Map.Entry<String, Object>> {

    private static final String PROPERTY_NOT_DEFINED_MESSAGE_TEMPLATE = "'?' property not defined!";

    private final Map<String, Object> map;

    /**
     * Creates a new instance of {@link JsonObject} class using the provided internal view of JSON object.
     *
     * @param internalViewOfJsonObject the provided internal view of JSON object.
     * @throws NullPointerException if the provided internal view of JSON object is {@code null}.
     */
    public JsonObject(final Map<String, Object> internalViewOfJsonObject) {
        this.map = require(internalViewOfJsonObject);
    }

    /**
     * Creates a new instance of {@link JsonObject} class with empty state.
     */
    public JsonObject() {
        this.map = new LinkedHashMap<>();
    }

    /**
     * Returns {@code true} if the current JSON object contains the specified property.
     *
     * @param propertyName the specified property name.
     * @return {@code true} if the current JSON object contains the specified property.
     */
    public boolean hasProperty(final String propertyName) {
        return map.containsKey(propertyName);
    }

    /**
     * Returns {@code true} if the current JSON object contains the specified property and the value of this property is {@code null}.
     *
     * @param propertyName the specified property name.
     * @return {@code true} if the current JSON object contains the specified property and the value of this property is {@code null}.
     */
    public boolean isNull(final String propertyName) {
        return map.get(propertyName) == null && map.containsKey(propertyName);
    }

    /**
     * Returns the string value for the specified property of the current JSON object.
     *
     * @param propertyName the specified property name.
     * @return the string value for the specified property of the current JSON object.
     * @throws IllegalArgumentException if the current JSON object does not contain the specified property.
     * @throws JsonException if value for the specified property of the current JSON object is not a string type.
     */
    public String getString(final String propertyName) {
        return requirePropertyValue(asJsonString(map.get(propertyName)), PROPERTY_NOT_DEFINED_MESSAGE_TEMPLATE, propertyName);
    }

    /**
     * Returns the string value for the specified property of the current JSON object or {@link Optional#empty()}.
     *
     * @param propertyName the specified property name.
     * @return the string value for the specified property of the current JSON object or {@link Optional#empty()}
     * @throws JsonException if value for the specified property of the current JSON object is not a string type.
     */
    public Optional<String> getOptionalString(final String propertyName) {
        return Optional.ofNullable(asJsonString(map.get(propertyName)));
    }

    /**
     * Returns the boolean value for the specified property of the current JSON object.
     *
     * @param propertyName the specified property name.
     * @return the boolean value for the specified property of the current JSON object.
     * @throws IllegalArgumentException if the current JSON object does not contain the specified property.
     * @throws JsonException if value for the specified property of the current JSON object is not a boolean type.
     */
    public Boolean getBoolean(final String propertyName) {
        return requirePropertyValue(asJsonBoolean(map.get(propertyName)), PROPERTY_NOT_DEFINED_MESSAGE_TEMPLATE, propertyName);
    }

    /**
     * Returns the boolean value for the specified property of the current JSON object or {@link Optional#empty()}.
     *
     * @param propertyName the specified property name.
     * @return the boolean value for the specified property of the current JSON object or {@link Optional#empty()}.
     * @throws JsonException if value for the specified property of the current JSON object is not a boolean type.
     */
    public Optional<Boolean> getOptionalBoolean(final String propertyName) {
        return Optional.ofNullable(asJsonBoolean(map.get(propertyName)));
    }

    /**
     * Returns the number value using {@link JsonNumber} class for the specified property of the current JSON object.
     *
     * @param propertyName the specified property name.
     * @return the number value using {@link JsonNumber} class for the specified property of the current JSON object.
     * @throws IllegalArgumentException if the current JSON object does not contain the specified property.
     * @throws JsonException if value for the specified property of the current JSON object is not a number type.
     */
    public JsonNumber getNumber(final String propertyName) {
        return requirePropertyValue(asJsonNumber(map.get(propertyName)), PROPERTY_NOT_DEFINED_MESSAGE_TEMPLATE, propertyName);
    }

    /**
     * Returns the number value using {@link JsonNumber} class for the specified property of the current JSON object
     * or {@link Optional#empty()}.
     *
     * @param propertyName the specified property name.
     * @return the number value using {@link JsonNumber} class for the specified property of the current JSON object or
     *         {@link Optional#empty()}.
     * @throws JsonException if value for the specified property of the current JSON object is not a number type.
     */
    public Optional<JsonNumber> getOptionalNumber(final String propertyName) {
        return Optional.ofNullable(asJsonNumber(map.get(propertyName)));
    }

    /**
     * Returns the {@link JsonObject} value for the specified property of the current JSON object.
     *
     * @param propertyName the specified property name.
     * @return the {@link JsonObject} value for the specified property of the current JSON object.
     * @throws IllegalArgumentException if the current JSON object does not contain the specified property.
     * @throws JsonException if value for the specified property of the current JSON object is not a json object type.
     */
    public JsonObject getJsonObject(final String propertyName) {
        return new JsonObject(
                requirePropertyValue(asJsonObject(map.get(propertyName)), PROPERTY_NOT_DEFINED_MESSAGE_TEMPLATE, propertyName)
        );
    }

    /**
     * Returns the {@link JsonObject} value for the specified property of the current JSON object or {@link Optional#empty()}.
     *
     * @param propertyName the specified property name.
     * @return the {@link JsonObject} value for the specified property of the current JSON object or {@link Optional#empty()}.
     * @throws JsonException if value for the specified property of the current JSON object is not a json object type.
     */
    public Optional<JsonObject> getOptionalJsonObject(final String propertyName) {
        return Optional.ofNullable(asJsonObject(map.get(propertyName))).map(JsonObject::new);
    }

    /**
     * Returns the {@link JsonArray} value for the specified property of the current JSON object.
     *
     * @param propertyName the specified property name.
     * @return the {@link JsonArray} value for the specified property of the current JSON object.
     * @throws IllegalArgumentException if the current JSON object does not contain the specified property.
     * @throws JsonException if value for the specified property of the current JSON object is not a json array type.
     */
    public JsonArray getJsonArray(final String propertyName) {
        return new JsonArray(
                requirePropertyValue(asJsonArray(map.get(propertyName)), PROPERTY_NOT_DEFINED_MESSAGE_TEMPLATE, propertyName)
        );
    }

    /**
     * Returns the {@link JsonArray} value for the specified property of the current JSON object or {@link Optional#empty()}.
     *
     * @param propertyName the specified property name.
     * @return the {@link JsonArray} value for the specified property of the current JSON object or {@link Optional#empty()}.
     * @throws JsonException if value for the specified property of the current JSON object is not a json array type.
     */
    public Optional<JsonArray> getOptionalJsonArray(final String propertyName) {
        return Optional.ofNullable(asJsonArray(map.get(propertyName))).map(JsonArray::new);
    }

    /**
     * Sets the new property value for the specified property name of the current JSON object.
     *
     * @param propertyName the specified property name
     * @param propertyValue the new property value
     * @return the reference to this {@link JsonObject} instance.
     * @throws NullPointerException if the specified property name is {@code null}.
     * @throws UnsupportedOperationException if the current JSON object is not modifiable.
     */
    @BuilderMethod
    public JsonObject set(final String propertyName,
                          final String propertyValue) {
        map.put(require(propertyName), propertyValue);
        return this;
    }

    /**
     * Sets the new property value for the specified property name of the current JSON object.
     *
     * @param propertyName the specified property name
     * @param propertyValue the new property value
     * @return the reference to this {@link JsonObject} instance.
     * @throws NullPointerException if the specified property name is {@code null}.
     * @throws UnsupportedOperationException if the current JSON object is not modifiable.
     */
    @BuilderMethod
    public JsonObject set(final String propertyName,
                          final Boolean propertyValue) {
        map.put(require(propertyName), propertyValue);
        return this;
    }

    /**
     * Sets the new property value for the specified property name of the current JSON object.
     *
     * @param propertyName the specified property name
     * @param propertyValue the new property value
     * @return the reference to this {@link JsonObject} instance.
     * @throws NullPointerException if the specified property name is {@code null}.
     * @throws UnsupportedOperationException if the current JSON object is not modifiable.
     */
    @BuilderMethod
    public JsonObject set(final String propertyName,
                          final JsonNumber propertyValue) {
        map.put(require(propertyName), propertyValue);
        return this;
    }

    /**
     * Sets the new property value for the specified property name of the current JSON object.
     *
     * @param propertyName the specified property name
     * @param propertyValue the new property value
     * @return the reference to this {@link JsonObject} instance.
     * @throws NullPointerException if the specified property name is {@code null}.
     * @throws UnsupportedOperationException if the current JSON object is not modifiable.
     */
    @BuilderMethod
    public JsonObject set(final String propertyName,
                          final BigDecimal propertyValue) {
        map.put(require(propertyName), new JsonNumber(propertyValue.toPlainString()));
        return this;
    }

    /**
     * Sets the new property value for the specified property name of the current JSON object.
     *
     * @param propertyName the specified property name
     * @param propertyValue the new property value
     * @return the reference to this {@link JsonObject} instance.
     * @throws NullPointerException if the specified property name is {@code null}.
     * @throws UnsupportedOperationException if the current JSON object is not modifiable.
     */
    @BuilderMethod
    public JsonObject set(final String propertyName,
                          final Number propertyValue) {
        map.put(require(propertyName), new JsonNumber(propertyValue.toString()));
        return this;
    }

    /**
     * Sets the new property value for the specified property name of the current JSON object.
     *
     * @param propertyName the specified property name
     * @param propertyValue the new property value
     * @return the reference to this {@link JsonObject} instance.
     * @throws NullPointerException if the specified property name is {@code null}.
     * @throws UnsupportedOperationException if the current JSON object is not modifiable.
     */
    @BuilderMethod
    public JsonObject set(final String propertyName,
                          final JsonObject propertyValue) {
        map.put(require(propertyName), propertyValue.getIntervalView());
        return this;
    }

    /**
     * Sets the new property value for the specified property name of the current JSON object.
     *
     * @param propertyName the specified property name
     * @param propertyValue the new property value
     * @return the reference to this {@link JsonObject} instance.
     * @throws NullPointerException if the specified property name is {@code null}.
     * @throws UnsupportedOperationException if the current JSON object is not modifiable.
     */
    @BuilderMethod
    public JsonObject set(final String propertyName,
                          final JsonArray propertyValue) {
        map.put(require(propertyName), propertyValue.getIntervalView());
        return this;
    }

    /**
     * Sets the {@code null} value for the specified property name of the current JSON object.
     *
     * @param propertyName the specified property name
     * @return the reference to this {@link JsonObject} instance.
     * @throws NullPointerException if the specified property name is {@code null}.
     * @throws UnsupportedOperationException if the current JSON object is not modifiable.
     */
    @BuilderMethod
    public JsonObject setNull(final String propertyName) {
        map.put(require(propertyName), null);
        return this;
    }

    /**
     * Returns the internal view of the JSON object.
     *
     * <p>
     * Since {@code rxmicro.json} module is used automatically, it is optimized for machine operations.
     * Therefore, this module doesn’t provide separate classes for JSON types. Instead, standard Java classes are used.
     * The RxMicro framework uses the {@link java.util.Map}{@code <}{@link String}{@code ,}{@link Object}{@code >} interface
     * to store JSON object.
     *
     * @return the internal view of the current JSON object.
     */
    public Map<String, Object> getIntervalView() {
        return unmodifiableMap(map);
    }

    /**
     * Returns a new updatable copy of the current {@link JsonObject} instance.
     *
     * <p>
     * The {@link io.rxmicro.json.JsonHelper#readJson(String)} method returns an unmodifiable json object.
     * If it is necessary to extend or change json object that was read, use {@link #asUpdatable()} method!
     *
     * @return a new updatable copy of the current {@link JsonObject} instance.
     */
    public JsonObject asUpdatable() {
        return asUpdatableJsonObject(map);
    }

    @Override
    public Iterator<Map.Entry<String, Object>> iterator() {
        return map.entrySet().iterator();
    }

    /**
     * Removes the property using the specified property name from the current JSON object.
     *
     * @param propertyName the specified property name
     * @return {@code true} if property was removed from the current JSON object.
     * @throws UnsupportedOperationException if the current JSON object is not modifiable.
     */
    public boolean remove(final String propertyName) {
        return map.remove(propertyName) != null;
    }

    /**
     * Removes all properties from the current JSON object.
     *
     * @return the reference to this {@link JsonObject} instance.
     * @throws UnsupportedOperationException if the current JSON object is not modifiable.
     */
    @BuilderMethod
    public JsonObject clear() {
        map.clear();
        return this;
    }

    /**
     * Returns the property count for the current JSON object.
     *
     * @return the property count for the current JSON object.
     */
    public int size() {
        return map.size();
    }

    /**
     * Returns {@code true} if the current JSON object does not contain any properties.
     *
     * @return {@code true} if the current JSON object does not contain any properties.
     */
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final JsonObject entries = (JsonObject) other;
        return map.equals(entries.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }

    @Override
    public String toString() {
        return "JsonObject{" +
                "map=" + map +
                '}';
    }
}
