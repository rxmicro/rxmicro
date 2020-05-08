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

package io.rxmicro.annotation.processor.common.model;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class TypeSignature implements Comparable<TypeSignature> {

    protected abstract String getTypeFullName();

    @Override
    public final int hashCode() {
        return getTypeFullName().hashCode();
    }

    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final TypeSignature that = (TypeSignature) other;
        return getTypeFullName().equals(that.getTypeFullName());
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public final int compareTo(final TypeSignature other) {
        return other == null ? 1 : getTypeFullName().compareTo(other.getTypeFullName());
    }
}
