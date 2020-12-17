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

package io.rxmicro.config.internal.model;

import io.rxmicro.config.ConfigException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static io.rxmicro.common.util.ExCollections.unmodifiableMap;
import static io.rxmicro.reflection.Reflections.instantiate;

/**
 * @author nedis
 * @since 0.3
 */
public final class DefaultConfigValueStorage {

    private final Map<String, String> defaultStringValuesStorage;

    private final Map<String, Supplier<?>> defaultSupplierValuesStorage;

    private DefaultConfigValueStorage(final Map<String, String> defaultStringValuesStorage,
                                      final Map<String, Supplier<?>> defaultSupplierValuesStorage) {
        this.defaultStringValuesStorage = unmodifiableMap(defaultStringValuesStorage);
        this.defaultSupplierValuesStorage = unmodifiableMap(defaultSupplierValuesStorage);
    }

    public boolean hasDefaultStringValuesStorage() {
        return !defaultStringValuesStorage.isEmpty();
    }

    public Map<String, String> getDefaultStringValuesStorage() {
        return defaultStringValuesStorage;
    }

    public boolean hasDefaultSupplierValuesStorage() {
        return !defaultSupplierValuesStorage.isEmpty();
    }

    public Map<String, Supplier<?>> getDefaultSupplierValuesStorage() {
        return defaultSupplierValuesStorage;
    }

    /**
     * @author nedis
     * @since 0.3
     */
    public static final class Builder {

        private final Map<String, String> defaultStringValuesStorage = new HashMap<>();

        private final Map<String, Supplier<?>> defaultSupplierValuesStorage = new HashMap<>();

        public void addDefaultStringValues(final String name,
                                           final String value) {
            validateDuplicates(name, value, defaultStringValuesStorage.put(name, value));
        }

        public void addDefaultSupplierValues(final String name,
                                             final Class<? extends Supplier<?>> supplierClass) {
            validateDuplicates(name, supplierClass, defaultSupplierValuesStorage.put(name, instantiate(supplierClass)));
        }

        private void validateDuplicates(final String name,
                                        final Object newValue,
                                        final Object oldValue) {
            if (oldValue != null) {
                throw new ConfigException(
                        "Detected a duplicate of default config value: key=?, value1=?, value2=?",
                        name, newValue, oldValue
                );
            }
        }

        public DefaultConfigValueStorage build() {
            return new DefaultConfigValueStorage(defaultStringValuesStorage, defaultSupplierValuesStorage);
        }
    }
}
