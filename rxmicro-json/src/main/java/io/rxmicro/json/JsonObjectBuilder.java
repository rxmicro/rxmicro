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
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class JsonObjectBuilder extends MapBuilder<String, Object> {

    @Override
    public JsonObjectBuilder put(final String name,
                                 final Object value) {
        if (value != null) {
            super.put(name, value);
        }
        return this;
    }

    public JsonObjectBuilder put(final String name,
                                 final List<?> value) {
        if (value != null && !value.isEmpty()) {
            super.put(name, value);
        }
        return this;
    }
}
