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

import java.util.LinkedHashMap;
import java.util.Map;

import static io.rxmicro.common.util.ExCollections.unmodifiableMap;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public class MapBuilder<K, V> {

    private Map<K, V> map = new LinkedHashMap<>();

    public MapBuilder<K, V> put(final K name,
                                final V value) {
        map.put(name, value);
        return this;
    }

    public Map<K, V> build() {
        try {
            return unmodifiableMap(map);
        } finally {
            // After built current instance of builder must not be used.
            map = null;
        }
    }
}
