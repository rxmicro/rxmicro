/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.data.sql.local.impl;

import io.rxmicro.data.sql.model.EntityFieldMap;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.unmodifiableMap;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class EntityFieldMapImpl extends AbstractMap<String, Object> implements EntityFieldMap {

    private final Map<String, Object> map;

    public EntityFieldMapImpl(final Map<String, Object> map) {
        this.map = unmodifiableMap(map);
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return map.entrySet();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Map)) {
            return false;
        }
        if (other instanceof EntityFieldMapImpl) {
            return map.equals(((EntityFieldMapImpl) other).map);
        }
        return entrySet().equals(((Map<?, ?>) other).entrySet());
    }

    @Override
    public int hashCode() {
        return entrySet().hashCode();
    }
}
