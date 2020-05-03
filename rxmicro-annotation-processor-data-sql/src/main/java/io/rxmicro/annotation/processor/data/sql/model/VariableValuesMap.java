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

package io.rxmicro.annotation.processor.data.sql.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class VariableValuesMap extends HashMap<String, Object> {

    public VariableValuesMap() {
        // Default constructor
    }

    public VariableValuesMap(final Map<String, Object> map) {
        super(map);
    }

    public boolean isStringValue(final String key) {
        return isType(key, String.class);
    }

    public boolean isSqlVariableValue(final String key) {
        return isType(key, SQLVariableValue.class);
    }

    private boolean isType(final String key,
                           final Class<?> type) {
        return Optional.ofNullable(get(key))
                .map(o -> type == o.getClass())
                .orElse(false);
    }

    public String getString(final String key) {
        return (String) get(key);
    }

    public SQLVariableValue getSqlVariableValue(final String key) {
        return (SQLVariableValue) get(key);
    }
}
