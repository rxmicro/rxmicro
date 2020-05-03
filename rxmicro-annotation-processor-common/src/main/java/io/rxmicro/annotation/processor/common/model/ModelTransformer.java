/*
 * Copyright 2019 https://rxmicro.io
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

import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerFullClassName;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerInstanceName;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerSimpleClassName;
import static io.rxmicro.annotation.processor.common.util.Names.getPackageName;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class ModelTransformer implements Comparable<ModelTransformer> {

    private final String packageName;

    private final String modelSimpleName;

    private final Class<?> baseTransformerClass;

    public ModelTransformer(final TypeMirror modelFullClassName,
                            final Class<?> baseTransformerClass) {
        this.packageName = getPackageName(require(modelFullClassName));
        this.modelSimpleName = getSimpleName(modelFullClassName);
        this.baseTransformerClass = baseTransformerClass;
    }

    public String getJavaFullClassName() {
        return getModelTransformerFullClassName(packageName, modelSimpleName, baseTransformerClass);
    }

    @UsedByFreemarker
    public String getJavaSimpleClassName() {
        return getModelTransformerSimpleClassName(modelSimpleName, baseTransformerClass);
    }

    @UsedByFreemarker
    public String getInstanceName() {
        return getModelTransformerInstanceName(modelSimpleName, baseTransformerClass);
    }

    @Override
    public int hashCode() {
        return getJavaFullClassName().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final ModelTransformer that = (ModelTransformer) other;
        return getJavaFullClassName().equals(that.getJavaFullClassName());
    }

    @Override
    public int compareTo(final ModelTransformer other) {
        return getJavaFullClassName().compareTo(other.getJavaFullClassName());
    }
}
