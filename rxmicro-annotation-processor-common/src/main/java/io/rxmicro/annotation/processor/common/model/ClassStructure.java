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

import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class ClassStructure implements Comparable<ClassStructure> {

    @UsedByFreemarker({
            "$$RestClientFactoryImplTemplate.javaftl",
            "$$RepositoryFactoryImplTemplate.javaftl"
    })
    public final String getTargetSimpleClassName() {
        return getSimpleName(getTargetFullClassName());
    }

    /**
     * Returns the full class name for model class that is used to generate the current class structure if exists,
     * otherwise {@link Optional#empty()}
     *
     * <p>
     * For example for {@code ModelReader<T>} the {@code T} will be returned.
     *
     * @return the full class name for model class that is used to generate the current class structure if exists,
     *          otherwise {@link Optional#empty()}
     */
    public Optional<String> getModelClassFullClassName(){
        return Optional.empty();
    }

    public abstract String getTargetFullClassName();

    public abstract String getTemplateName();

    public abstract Map<String, Object> getTemplateVariables();

    public abstract ClassHeader getClassHeader();

    public boolean isRequiredReflectionGetter() {
        return false;
    }

    public boolean isRequiredReflectionSetter() {
        return false;
    }

    public boolean isRequiredReflectionInvoke() {
        return false;
    }

    public List<Map.Entry<String, DefaultConfigProxyValue>> getDefaultConfigProxyValues() {
        return List.of();
    }

    @Override
    public final int hashCode() {
        return getTargetFullClassName().hashCode();
    }

    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final ClassStructure that = (ClassStructure) other;
        return getTargetFullClassName().equals(that.getTargetFullClassName());
    }

    @Override
    public final String toString() {
        return getClass().getSimpleName() + "#" + getTargetFullClassName();
    }

    @Override
    public final int compareTo(final ClassStructure other) {
        return getTargetFullClassName().compareTo(other.getTargetFullClassName());
    }
}
