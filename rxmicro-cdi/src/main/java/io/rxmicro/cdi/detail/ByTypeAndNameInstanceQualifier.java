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

package io.rxmicro.cdi.detail;

import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;
import io.rxmicro.runtime.detail.InstanceQualifier;

import java.util.Objects;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.common.util.Strings.startsWith;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public class ByTypeAndNameInstanceQualifier<T> extends ByTypeInstanceQualifier<T> {

    private final String name;

    public ByTypeAndNameInstanceQualifier(final Class<T> type,
                                          final String name) {
        super(type);
        this.name = require(name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        final ByTypeAndNameInstanceQualifier<?> that = (ByTypeAndNameInstanceQualifier<?>) other;
        return name.equals(that.name);
    }

    @Override
    public String toString() {
        if (startsWith(name, '@')) {
            return format("@ByTypeAndAnnotated(?, ?)", getType().getName(), name);
        } else {
            return format("@ByTypeAndName(?, '?')", getType().getName(), name);
        }
    }

    @Override
    public int compareTo(final InstanceQualifier<T> other) {
        if (other instanceof ByTypeInstanceQualifier) {
            final int compareResult = super.compareTo(other);
            if (other instanceof ByTypeAndNameInstanceQualifier && compareResult == 0) {
                return name.compareTo(((ByTypeAndNameInstanceQualifier<?>) other).name);
            }
            return compareResult;
        }
        return 1;
    }
}
