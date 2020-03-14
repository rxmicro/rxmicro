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

import java.util.Map;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class ClassStructure implements Comparable<ClassStructure> {

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

    @Override
    public final int hashCode() {
        return getTargetFullClassName().hashCode();
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ClassStructure that = (ClassStructure) o;
        return getTargetFullClassName().equals(that.getTargetFullClassName());
    }

    @Override
    public final String toString() {
        return getClass().getSimpleName() + "#" + getTargetFullClassName();
    }

    @Override
    public final int compareTo(final ClassStructure o) {
        return getTargetFullClassName().compareTo(o.getTargetFullClassName());
    }
}
