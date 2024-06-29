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

package io.rxmicro.annotation.processor.rest.component.impl.builder;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.impl.BaseProcessorComponent;
import io.rxmicro.annotation.processor.common.model.AnnotatedModelElement;
import io.rxmicro.annotation.processor.common.model.ModelFieldType;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.rest.component.RestModelFieldBuilder;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.rest.PathVariable;
import io.rxmicro.rest.model.HttpModelType;

import java.util.Set;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * @author nedis
 * @since 0.5
 */
@Singleton
public final class PathVariableRestModelFieldBuilder extends BaseProcessorComponent implements RestModelFieldBuilder<PathVariable> {

    @Inject
    private SupportedTypesProvider supportedTypesProvider;

    @Override
    public RestModelField build(final ModelFieldType modelFieldType,
                                final TypeElement typeElement,
                                final AnnotatedModelElement annotated,
                                final PathVariable pathVariable,
                                final Set<String> modelNames,
                                final int nestedLevel) {
        final VariableElement field = annotated.getField();
        if (nestedLevel > 1) {
            error(
                    annotated.getElementAnnotatedBy(PathVariable.class).orElse(field),
                    "Annotation @? not allowed here. Path variable can be defined in root class only",
                    PathVariable.class.getSimpleName()
            );
        }
        final String modelName = pathVariable.value().isEmpty() ? field.getSimpleName().toString() : pathVariable.value();
        if (!modelNames.add(modelName)) {
            error(
                    annotated.getElementAnnotatedBy(PathVariable.class).orElse(field),
                    "Detected duplicate of path variable name: ?",
                    modelName
            );
        }
        if (!supportedTypesProvider.isModelPrimitive(field.asType())) {
            error(
                    annotated.getElementAnnotatedBy(PathVariable.class).orElse(field),
                    "Invalid path variable type. Allowed types are: ?",
                    supportedTypesProvider.getPrimitives()
            );
        }
        return new RestModelField(annotated, HttpModelType.PATH, modelName);
    }
}
