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

package io.rxmicro.annotation.processor.common.model;

import javax.lang.model.element.TypeElement;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.3
 */
public final class DefaultConfigProxyValue {

    private final Object value;

    public DefaultConfigProxyValue(final Object value) {
        this.value = require(value);
    }

    @Override
    public String toString() {
        if (value instanceof String) {
            return format("\"?\"", value);
        } else if (value instanceof TypeElement) {
            return format("?.class", ((TypeElement) value).getQualifiedName());
        } else {
            throw new IllegalStateException(format("Unsupported value type: ? (?)", value, value.getClass()));
        }
    }
}
