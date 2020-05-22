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

import io.rxmicro.common.model.MapBuilder;

import java.util.List;

/**
 * JSON object builder
 *
 * @author nedis
 * @since 0.1
 * @see JsonTypes
 * @see JsonHelper
 * @see JsonNumber
 * @see JsonException
 */
public final class JsonObjectBuilder extends MapBuilder<String, Object> {

    /**
     * Creates a {@link JsonObjectBuilder} instance
     *
     * @param withoutDuplicates {@code true} if {@link JsonObjectBuilder} must throw
     *                          {@link IllegalArgumentException} if duplicate of map key detected
     */
    public JsonObjectBuilder(final boolean withoutDuplicates) {
        super(withoutDuplicates);
    }

    /**
     * Creates a {@link JsonObjectBuilder} instance without duplicate detection
     */
    public JsonObjectBuilder() {
        super(false);
    }

    /**
     * Puts the specified name and value to the building JSON object if the specified value is not {@code null}
     *
     * @param name the specified name
     * @param value the specified value
     * @return the reference to this {@link JsonObjectBuilder} instance
     * @throws IllegalArgumentException if {@code withoutDuplicates} is {@code true} and detected a duplicate of property name
     */
    @Override
    public JsonObjectBuilder put(final String name,
                                 final Object value) {
        if (value != null) {
            super.put(name, value);
        }
        return this;
    }

    /**
     * Puts the specified name and value to the building JSON object  if the specified value is not {@code null} or not empty
     *
     * @param name the specified name
     * @param value the specified value
     * @return the reference to this {@link JsonObjectBuilder} instance
     * @throws IllegalArgumentException if {@code withoutDuplicates} is {@code true} and detected a duplicate of property name
     */
    public JsonObjectBuilder put(final String name,
                                 final List<?> value) {
        if (value != null && !value.isEmpty()) {
            super.put(name, value);
        }
        return this;
    }
}
