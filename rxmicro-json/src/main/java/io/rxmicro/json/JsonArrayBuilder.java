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

import io.rxmicro.common.model.ListBuilder;

/**
 * JSON array builder
 *
 * @author nedis
 * @since 0.1
 * @see JsonTypes
 * @see JsonHelper
 * @see JsonNumber
 * @see JsonException
 */
public final class JsonArrayBuilder extends ListBuilder<Object> {

    /**
     * Adds the specified item to the building JSON array
     *
     * @param item the specified item
     * @return the reference to this {@link JsonArrayBuilder} instance
     */
    @Override
    public JsonArrayBuilder add(final Object item) {
        return (JsonArrayBuilder) super.add(item);
    }
}
