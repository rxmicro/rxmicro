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

package io.rxmicro.http.local;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedMap;
import static io.rxmicro.common.util.Formats.format;
import static java.util.Collections.unmodifiableList;
import static java.util.Map.entry;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.1
 */
@SuppressWarnings("unchecked")
public abstract class AbstractRepeatableValues<T extends RepeatableValues<T>> {

    private final Map<String, Object> valuesMap;

    protected AbstractRepeatableValues() {
        valuesMap = new LinkedHashMap<>();
    }

    protected AbstractRepeatableValues(final T other) {
        this();
        setOrAddAll(other);
    }

    public final boolean contains(final String name) {
        return valuesMap.containsKey(name);
    }

    public final boolean isEmpty() {
        return valuesMap.isEmpty();
    }

    public final boolean isNotEmpty() {
        return !valuesMap.isEmpty();
    }

    public final int size() {
        return valuesMap.size();
    }

    public final Set<String> names() {
        return valuesMap.keySet();
    }

    public final boolean remove(final String name) {
        return valuesMap.remove(name) != null;
    }

    public final void clear() {
        valuesMap.clear();
    }

    public final String getValue(final String name) {
        final Object value = valuesMap.get(name);
        if (value == null) {
            return null;
        } else if (isSetValue(value)) {
            return (String) value;
        } else {
            return ((List<String>) value).get(0);
        }
    }

    public final List<String> getValues(final String name) {
        final Object value = valuesMap.get(name);
        if (value == null) {
            return List.of();
        } else if (isAddedValue(value)) {
            return (List<String>) value;
        } else {
            return Collections.singletonList((String) value);
        }
    }

    public final void add(final String name,
                          final String value) {
        final List<String> values = new ListImpl(value);
        final Object oldValue = valuesMap.put(name, values);
        if (oldValue != null) {
            if (isSetValue(oldValue)) {
                values.add(0, (String) oldValue);
            } else {
                ((List<String>) oldValue).add(value);
                valuesMap.put(name, oldValue);
            }
        }
    }

    public final void set(final String name,
                          final String value) {
        valuesMap.put(name, value);
    }

    public final void setOrAdd(final String name,
                               final String value) {
        final Object oldValue = valuesMap.put(name, value);
        if (oldValue != null) {
            if (isSetValue(oldValue)) {
                valuesMap.put(name, new ListImpl((String) oldValue, value));
            } else {
                ((List<String>) oldValue).add(value);
                valuesMap.put(name, oldValue);
            }
        }
    }

    public final void setOrAddAll(final T other) {
        for (final Map.Entry<String, Object> entry : other.getValuesMap().entrySet()) {
            if (!isSetValue(entry.getValue()) && !isAddedValue(entry.getValue())) {
                throw new IllegalArgumentException("Unsupported type: " + entry.getValue().getClass());
            }
            if (this.valuesMap.containsKey(entry.getKey())) {
                final Object oldValue = this.valuesMap.get(entry.getKey());
                final Object newValue = entry.getValue();
                if (isAddedValue(oldValue)) {
                    if (isAddedValue(newValue)) {
                        ((List<String>) oldValue).addAll((List<String>) newValue);
                    } else {
                        this.valuesMap.put(entry.getKey(), entry.getValue());
                    }
                } else {
                    if (isAddedValue(newValue)) {
                        final List<String> mergeValues = new ListImpl((String) oldValue);
                        mergeValues.addAll((List<String>) newValue);
                        this.valuesMap.put(entry.getKey(), mergeValues);
                    } else {
                        this.valuesMap.put(entry.getKey(), entry.getValue());
                    }
                }
            } else {
                this.valuesMap.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public final List<Map.Entry<String, String>> getEntries() {
        final List<Map.Entry<String, String>> result = new ArrayList<>(valuesMap.size());
        for (final Map.Entry<String, Object> entry : valuesMap.entrySet()) {
            if (isAddedValue(entry.getValue())) {
                for (final String value : (List<String>) entry.getValue()) {
                    result.add(entry(entry.getKey(), value));
                }
            } else {
                result.add(entry(entry.getKey(), (String) entry.getValue()));
            }
        }
        return unmodifiableList(result);
    }

    public final Map<String, Object> getValuesMap() {
        return unmodifiableOrderedMap(valuesMap);
    }

    public final boolean isAddedValue(final Object value) {
        return value instanceof List;
    }

    public final boolean isSetValue(final Object value) {
        return value instanceof String;
    }

    @Override
    public String toString() {
        return getEntries().stream()
                .map(e -> format("?=?", e.getKey(), e.getValue()))
                .collect(joining(";"));
    }

    /**
     * @author nedis
     * @since 0.1
     */
    private static final class ListImpl extends ArrayList<String> {

        private ListImpl(final String value) {
            super(1);
            add(value);
        }

        private ListImpl(final String value1,
                         final String value2) {
            super(2);
            add(value1);
            add(value2);
        }
    }
}
