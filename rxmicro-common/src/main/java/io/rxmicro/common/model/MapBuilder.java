/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.common.model;

import io.rxmicro.common.InvalidStateException;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedMap;
import static io.rxmicro.common.util.Formats.format;

/**
 * The builder that builds a short-lived unmodified ordered {@link Map} instance.
 *
 * @author nedis
 * @since 0.1
 * @see Map
 * @see LinkedHashMap
 * @see io.rxmicro.common.util.ExCollections#unmodifiableOrderedMap(Map)
 */
public class MapBuilder<K, V> {

    private final Map<K, V> map = new LinkedHashMap<>();

    private final boolean withoutDuplicates;

    private boolean built;

    /**
     * Creates a {@link MapBuilder} instance
     *
     * @param withoutDuplicates {@code true} if {@link MapBuilder} must throw
     *                          {@link IllegalArgumentException} if duplicate of map key detected
     */
    public MapBuilder(final boolean withoutDuplicates) {
        this.withoutDuplicates = withoutDuplicates;
    }

    /**
     * Creates a {@link MapBuilder} instance without duplicate detection
     */
    public MapBuilder() {
        this(false);
    }

    /**
     * Puts the specified name and value to the end of the building ordered {@link Map} instance
     *
     * @param name the specified name
     * @param value the specified value
     * @return the reference to this {@link MapBuilder} instance
     * @throws InvalidStateException if the {@link Map} instance already built
     * @throws IllegalArgumentException if {@code withoutDuplicates} is {@code true} and detected a duplicate of property name
     */
    public MapBuilder<K, V> put(final K name,
                                final V value) {
        if (built) {
            throw new InvalidStateException("This builder can't be used, because the map instance already built! Create a new builder!");
        }
        final V oldValue = map.put(name, value);
        if (withoutDuplicates && oldValue != null) {
            throw new IllegalArgumentException(
                    format("Duplicate detected: name=?, value1=?, value2=?", name, value, oldValue)
            );
        }
        return this;
    }

    /**
     * Builds the short-lived unmodified ordered {@link Map} instance
     *
     * @return the short-lived unmodified ordered {@link Map} instance
     */
    public Map<K, V> build() {
        built = true;
        return unmodifiableOrderedMap(map);
    }
}
