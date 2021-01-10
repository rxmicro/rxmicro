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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.json.JsonTypes.asJsonArray;
import static io.rxmicro.json.JsonTypes.asJsonBoolean;
import static io.rxmicro.json.JsonTypes.asJsonNumber;
import static io.rxmicro.json.JsonTypes.asJsonObject;
import static io.rxmicro.json.JsonTypes.asJsonString;
import static java.util.Collections.unmodifiableList;

/**
 * Json array wrapper class that simplifies the work with Json format manually.
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
public final class JsonArray implements Iterable<Object> {

    private final List<Object> list;

    /**
     * Creates a new instance of {@link JsonArray} class using the provided internal view of JSON array.
     *
     * @param internalViewOfJsonArray the provided internal view of JSON array.
     * @throws NullPointerException if the provided internal view of JSON array is {@code null}.
     */
    public JsonArray(final List<Object> internalViewOfJsonArray) {
        this.list = require(internalViewOfJsonArray);
    }

    /**
     * Creates a new instance of {@link JsonArray} class with empty state.
     */
    public JsonArray() {
        this.list = new ArrayList<>(0);
    }

    /**
     * Returns the string item for the specified index of the current JSON array.
     *
     * @param index the specified index
     * @return the string item for the specified index of the current JSON array.
     * @throws JsonException if the string item for the specified index of the current JSON array is not a string type.
     */
    public String getString(final int index) {
        return asJsonString(list.get(index));
    }

    /**
     * Returns the boolean item for the specified index of the current JSON array.
     *
     * @param index the specified index
     * @return the boolean item for the specified index of the current JSON array.
     * @throws JsonException if the boolean item for the specified index of the current JSON array is not a boolean type.
     */
    public Boolean getBoolean(final int index) {
        return asJsonBoolean(list.get(index));
    }

    /**
     * Returns the number item using {@link JsonNumber} class for the specified index of the current JSON array.
     *
     * @param index the specified index
     * @return the number item using {@link JsonNumber} class for the specified index of the current JSON array.
     * @throws JsonException if the number item for the specified index of the current JSON array is not a number type.
     */
    public JsonNumber getNumber(final int index) {
        return asJsonNumber(list.get(index));
    }

    /**
     * Returns the {@link JsonObject} item for the specified index of the current JSON array.
     *
     * @param index the specified index
     * @return the {@link JsonObject} item for the specified index of the current JSON array.
     * @throws JsonException if the {@link JsonObject} item for the specified index of the current JSON array is not a JSON object type.
     */
    public JsonObject getJsonObject(final int index) {
        return new JsonObject(asJsonObject(list.get(index)));
    }

    /**
     * Returns the {@link JsonArray} item for the specified index of the current JSON array.
     *
     * @param index the specified index
     * @return the {@link JsonArray} item for the specified index of the current JSON array.
     * @throws JsonException if the {@link JsonArray} item for the specified index of the current JSON array is not a JSON array type.
     */
    public JsonArray getJsonArray(final int index) {
        return new JsonArray(asJsonArray(list.get(index)));
    }

    /**
     * Adds the {@code null} value to the current JSON array.
     *
     * @return the reference to this {@link JsonObject} instance.
     * @throws UnsupportedOperationException if the current JSON array is not modifiable.
     */
    @BuilderMethod
    public JsonArray addNull() {
        list.add(null);
        return this;
    }

    /**
     * Adds the new value to the current JSON array.
     *
     * @param item the new value
     * @return the reference to this {@link JsonObject} instance.
     * @throws NullPointerException if the new value is {@code null}.
     * @throws UnsupportedOperationException if the current JSON array is not modifiable.
     */
    @BuilderMethod
    public JsonArray add(final String item) {
        list.add(require(item));
        return this;
    }

    /**
     * Adds the new value to the current JSON array.
     *
     * @param item the new value
     * @return the reference to this {@link JsonObject} instance.
     * @throws NullPointerException if the new value is {@code null}.
     * @throws UnsupportedOperationException if the current JSON array is not modifiable.
     */
    @BuilderMethod
    public JsonArray add(final Boolean item) {
        list.add(require(item));
        return this;
    }

    /**
     * Adds the new value to the current JSON array.
     *
     * @param item the new value
     * @return the reference to this {@link JsonObject} instance.
     * @throws NullPointerException if the new value is {@code null}.
     * @throws UnsupportedOperationException if the current JSON array is not modifiable.
     */
    @BuilderMethod
    public JsonArray add(final JsonNumber item) {
        list.add(require(item));
        return this;
    }

    /**
     * Adds the new value to the current JSON array.
     *
     * @param item the new value
     * @return the reference to this {@link JsonObject} instance.
     * @throws NullPointerException if the new value is {@code null}.
     * @throws UnsupportedOperationException if the current JSON array is not modifiable.
     */
    @BuilderMethod
    public JsonArray add(final BigDecimal item) {
        list.add(new JsonNumber(item.toPlainString()));
        return this;
    }

    /**
     * Adds the new value to the current JSON array.
     *
     * @param item the new value
     * @return the reference to this {@link JsonObject} instance.
     * @throws NullPointerException if the new value is {@code null}.
     * @throws UnsupportedOperationException if the current JSON array is not modifiable.
     */
    @BuilderMethod
    public JsonArray add(final Number item) {
        list.add(new JsonNumber(item.toString()));
        return this;
    }

    /**
     * Adds the new value to the current JSON array.
     *
     * @param item the new value
     * @return the reference to this {@link JsonObject} instance.
     * @throws NullPointerException if the new value is {@code null}.
     * @throws UnsupportedOperationException if the current JSON array is not modifiable.
     */
    @BuilderMethod
    public JsonArray add(final JsonObject item) {
        list.add(require(item).getIntervalView());
        return this;
    }

    /**
     * Adds the new value to the current JSON array.
     *
     * @param item the new value
     * @return the reference to this {@link JsonObject} instance.
     * @throws NullPointerException if the new value is {@code null}.
     * @throws UnsupportedOperationException if the current JSON array is not modifiable.
     */
    @BuilderMethod
    public JsonArray add(final JsonArray item) {
        list.add(require(item).list);
        return this;
    }

    /**
     * Adds the new value to the current JSON array.
     *
     * @param item the new value
     * @return the reference to this {@link JsonObject} instance.
     * @throws NullPointerException if the new value is {@code null}.
     * @throws UnsupportedOperationException if the current JSON array is not modifiable.
     */
    @BuilderMethod
    public JsonArray addAll(final JsonArray item) {
        list.addAll(require(item).list);
        return this;
    }

    /**
     * Returns the internal view of the JSON array.
     *
     * <p>
     * Since {@code rxmicro.json} module is used automatically, it is optimized for machine operations.
     * Therefore, this module doesn’t provide separate classes for JSON types. Instead, standard Java classes are used.
     * The RxMicro framework uses the {@link java.util.List}{@code <}{@link Object}{@code >} interface
     * to store JSON array.
     *
     * @return the internal view of the current JSON array.
     */
    public List<Object> getIntervalView() {
        return unmodifiableList(list);
    }

    @Override
    public Iterator<Object> iterator() {
        return list.iterator();
    }

    /**
     * Removes the item using the specified index from the current JSON array.
     *
     * @param index the specified index
     * @return {@code true} if item was removed from the current JSON array.
     * @throws UnsupportedOperationException if the current JSON array is not modifiable.
     */
    public boolean remove(final int index) {
        return list.remove(index) != null;
    }

    /**
     * Removes all items from the current JSON array.
     *
     * @return the reference to this {@link JsonArray} instance.
     * @throws UnsupportedOperationException if the current JSON array is not modifiable.
     */
    @BuilderMethod
    public JsonArray clear() {
        list.clear();
        return this;
    }

    /**
     * Returns the item count for the current JSON array.
     *
     * @return the item count for the current JSON array.
     */
    public int size() {
        return list.size();
    }

    /**
     * Returns {@code true} if the current JSON array does not contain any items.
     *
     * @return {@code true} if the current JSON array does not contain any items.
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        return "JsonArray{" +
                "list=" + list +
                '}';
    }
}
