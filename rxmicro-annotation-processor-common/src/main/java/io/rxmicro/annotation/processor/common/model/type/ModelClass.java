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

package io.rxmicro.annotation.processor.common.model.type;

import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.LoggableClassName;
import io.rxmicro.annotation.processor.common.model.ModelField;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;

import java.util.Objects;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class ModelClass implements Comparable<ModelClass>, LoggableClassName {

    public abstract String getJavaSimpleClassName();

    public abstract String getJavaFullClassName();

    @Override
    public String getLoggableFullClassName() {
        return getJavaFullClassName();
    }

    @UsedByFreemarker
    public final boolean isPrimitive() {
        return this instanceof PrimitiveModelClass;
    }

    public final PrimitiveModelClass asPrimitive() {
        return (PrimitiveModelClass) this;
    }

    @UsedByFreemarker
    public final boolean isEnum() {
        return this instanceof EnumModelClass;
    }

    public final EnumModelClass asEnum() {
        return (EnumModelClass) this;
    }

    @UsedByFreemarker
    public final boolean isObject() {
        return this instanceof ObjectModelClass;
    }

    @SuppressWarnings("unchecked")
    public final <F extends ModelField, T extends ObjectModelClass<F>> T asObject() {
        return (T) this;
    }

    @SuppressWarnings({"unchecked", "unused"})
    public final <F extends ModelField, T extends ObjectModelClass<F>> T asObject(final Class<T> className) {
        return (T) this;
    }

    @UsedByFreemarker
    public final boolean isIterable() {
        return this instanceof IterableModelClass;
    }

    public final IterableModelClass asIterable() {
        return (IterableModelClass) this;
    }

    @Override
    public final int hashCode() {
        return getJavaFullClassName().hashCode();
    }

    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final ModelClass that = (ModelClass) other;
        return Objects.equals(getJavaFullClassName(), that.getJavaFullClassName());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "#" + getJavaFullClassName();
    }

    @Override
    public final int compareTo(final ModelClass other) {
        return getJavaFullClassName().compareTo(other.getJavaFullClassName());
    }
}
