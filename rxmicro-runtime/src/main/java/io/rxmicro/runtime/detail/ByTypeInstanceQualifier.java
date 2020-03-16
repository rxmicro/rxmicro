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

package io.rxmicro.runtime.detail;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public class ByTypeInstanceQualifier<T> implements InstanceQualifier<T> {

    private final Class<T> type;

    public ByTypeInstanceQualifier(final Class<T> type) {
        this.type = require(type);
    }

    @Override
    public final Class<T> getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ByTypeInstanceQualifier<?> that = (ByTypeInstanceQualifier<?>) o;
        return type.equals(that.type);
    }

    @Override
    public String toString() {
        return format("@ByType(?)", type.getName());
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(final InstanceQualifier<T> o) {
        if (o instanceof ByTypeInstanceQualifier) {
            return type.getName().compareTo(((ByTypeInstanceQualifier<?>) o).type.getName());
        }
        return 1;
    }
}
