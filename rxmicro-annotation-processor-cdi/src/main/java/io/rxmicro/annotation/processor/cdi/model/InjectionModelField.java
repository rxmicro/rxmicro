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

package io.rxmicro.annotation.processor.cdi.model;

import io.rxmicro.annotation.processor.common.model.AnnotatedModelElement;
import io.rxmicro.annotation.processor.common.model.ModelField;
import io.rxmicro.annotation.processor.common.util.Names;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;

import javax.lang.model.type.DeclaredType;

import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.types;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class InjectionModelField extends ModelField {

    public InjectionModelField(final AnnotatedModelElement annotatedModelElement,
                               final String modelName) {
        super(annotatedModelElement, modelName);
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public String getGenericListItemSimpleClassName() {
        return Names.getSimpleName(
                types().erasure(((DeclaredType) getFieldClass()).getTypeArguments().get(0)).toString()
        );
    }
}
