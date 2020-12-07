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

package io.rxmicro.config;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedMap;

/**
 * The parent class for all config classes that use {@link Map} as value storage.
 *
 * @author nedis
 * @since 0.7
 */
public class AsMapConfig extends Config implements Map<String, Object> {

    private Map<String, Object> map;

    /**
     * This is basic class designed for extension only.
     */
    protected AsMapConfig() {
        // This is basic class designed for extension only.
    }

    /**
     * Returns {@code true} if the current config should support {@link Map}{@code <String, String>} values.
     *
     * <p>
     * Child class can override this method to enable/disable map supporting.
     *
     * @return {@code true} if the current config should support {@link Map}{@code <String, String>} values.
     * @see #getMap(Object)
     */
    public boolean supportsMap() {
        return true;
    }

    /**
     * Returns {@code true} if the current config should support {@link List}{@code <String>} values.
     *
     * <p>
     * Child class can override this method to enable/disable list supporting.
     *
     * @return {@code true} if the current config should support {@link List}{@code <String>} values.
     * @see #getList(Object)
     */
    public boolean supportsList() {
        return true;
    }

    /**
     * Sets the map instance.
     *
     * @param map the map instance
     */
    public void setMap(final Map<String, Object> map) {
        this.map = unmodifiableOrderedMap(map);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(final Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {
        return map.containsValue(value);
    }

    @Override
    public Object get(final Object key) {
        return map.get(key);
    }

    @Override
    public Object put(final String key,
                      final Object value) {
        return map.put(key, value);
    }

    @Override
    public Object remove(final Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(final Map<? extends String, ? extends Object> sourceMap) {
        map.putAll(sourceMap);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<Object> values() {
        return map.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return map.entrySet();
    }

    /**
     * Returns the {@link String} value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the {@link String} value to which the specified key is mapped, or
     *        {@code null} if this map contains no mapping for the key
     * @exception ClassCastException if the value is of an inappropriate type for this map.
     */
    public String getString(final Object key) {
        return (String) get(key);
    }

    /**
     * Returns the {@link Boolean} value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the {@link Boolean} value to which the specified key is mapped, or
     *        {@code null} if this map contains no mapping for the key
     * @exception ClassCastException if the value is of an inappropriate type for this map.
     */
    public boolean getBoolean(final Object key) {
        return (Boolean) get(key);
    }

    /**
     * Returns the {@link BigInteger} value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the {@link BigInteger} value to which the specified key is mapped, or
     *        {@code null} if this map contains no mapping for the key
     * @exception ClassCastException if the value is of an inappropriate type for this map.
     */
    public BigInteger getBigInteger(final Object key) {
        return (BigInteger) get(key);
    }

    /**
     * Returns the {@link Long} value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the {@link Long} value to which the specified key is mapped, or
     *        {@code null} if this map contains no mapping for the key
     * @exception ClassCastException if the value is of an inappropriate type for this map.
     */
    public Long getLong(final Object key) {
        return (Long) get(key);
    }

    /**
     * Returns the {@link Integer} value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the {@link Integer} value to which the specified key is mapped, or
     *        {@code null} if this map contains no mapping for the key
     * @exception ClassCastException if the value is of an inappropriate type for this map.
     */
    public Integer getInteger(final Object key) {
        final Number value = (Number) get(key);
        return value != null ? value.intValue() : null;
    }

    /**
     * Returns the {@link Short} value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the {@link Short} value to which the specified key is mapped, or
     *        {@code null} if this map contains no mapping for the key
     * @exception ClassCastException if the value is of an inappropriate type for this map.
     */
    public Short getShort(final Object key) {
        final Number value = (Number) get(key);
        return value != null ? value.shortValue() : null;
    }

    /**
     * Returns the {@link BigDecimal} value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the {@link BigDecimal} value to which the specified key is mapped, or
     *        {@code null} if this map contains no mapping for the key
     * @exception ClassCastException if the value is of an inappropriate type for this map.
     */
    public BigDecimal getBigDecimal(final Object key) {
        return (BigDecimal) get(key);
    }

    /**
     * Returns the {@link Double} value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the {@link Double} value to which the specified key is mapped, or
     *        {@code null} if this map contains no mapping for the key
     * @exception ClassCastException if the value is of an inappropriate type for this map.
     */
    public Double getDouble(final Object key) {
        final Number value = (Number) get(key);
        return value != null ? value.doubleValue() : null;
    }

    /**
     * Returns the {@link Float} value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the {@link Float} value to which the specified key is mapped, or
     *        {@code null} if this map contains no mapping for the key
     * @exception ClassCastException if the value is of an inappropriate type for this map.
     */
    public Float getFloat(final Object key) {
        final Number value = (Number) get(key);
        return value != null ? value.floatValue() : null;
    }

    /**
     * Returns the {@link Map}{@code <String, String>} value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the {@link Map}{@code <String, String>}  value to which the specified key is mapped, or
     *        {@code null} if this map contains no mapping for the key
     * @exception ClassCastException if the value is of an inappropriate type for this map.
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getMap(final Object key) {
        return (Map<String, String>) get(key);
    }

    /**
     * Returns the {@link List}{@code <String>} value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the {@link List}{@code <String>}  value to which the specified key is mapped, or
     *        {@code null} if this map contains no mapping for the key
     * @exception ClassCastException if the value is of an inappropriate type for this map.
     */
    @SuppressWarnings("unchecked")
    public List<String> getList(final Object key) {
        return (List<String>) get(key);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "map=" + map +
                '}';
    }
}
