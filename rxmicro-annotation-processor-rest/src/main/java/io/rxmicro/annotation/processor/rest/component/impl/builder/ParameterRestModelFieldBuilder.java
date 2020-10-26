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
import io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent;
import io.rxmicro.annotation.processor.common.model.AnnotatedModelElement;
import io.rxmicro.annotation.processor.common.model.ModelFieldType;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.component.RestModelFieldBuilder;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.rest.Parameter;
import io.rxmicro.rest.ParameterMappingStrategy;
import io.rxmicro.rest.RepeatQueryParameter;
import io.rxmicro.rest.model.HttpModelType;

import java.util.Set;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import static io.rxmicro.annotation.processor.common.model.ModelFieldType.REST_CLIENT_RESPONSE;
import static io.rxmicro.annotation.processor.common.util.Errors.IMPOSSIBLE_ERROR_ANNOTATION_NOT_FOUND_SUPPLIER;
import static io.rxmicro.http.local.HttpValidators.validateParameterName;

/**
 * @author nedis
 * @since 0.5
 */
@Singleton
public final class ParameterRestModelFieldBuilder extends AbstractProcessorComponent implements RestModelFieldBuilder<Parameter> {

    @Inject
    private SupportedTypesProvider supportedTypesProvider;

    @Override
    @SuppressWarnings("Convert2MethodRef")
    public RestModelField build(final ModelFieldType modelFieldType,
                                final TypeElement typeElement,
                                final AnnotatedModelElement annotated,
                                final Parameter parameter,
                                final Set<String> modelNames,
                                final int nestedLevel) {
        final VariableElement field = annotated.getField();
        final ParameterMappingStrategy strategy = typeElement.getAnnotation(ParameterMappingStrategy.class);
        final String customParameterName = parameter != null ? parameter.value() : "";
        final String modelName = annotated.getModelName(customParameterName, strategy, () -> strategy.value());
        if (!modelNames.add(modelName)) {
            error(
                    annotated.getElementAnnotatedBy(Parameter.class).orElse(field),
                    "Detected duplicate of HTTP parameter name: ?", modelName
            );
        }
        try {
            validateParameterName(modelName);
        } catch (final IllegalArgumentException ex) {
            error(annotated.getElementAnnotatedBy(Parameter.class).orElse(field), ex.getMessage());
        }
        final boolean repeat = annotated.isAnnotationPresent(RepeatQueryParameter.class);
        if (repeat) {
            validateRepeatQueryParameter(modelFieldType, annotated);
        }
        return new RestModelField(annotated, HttpModelType.PARAMETER, modelName, repeat);
    }

    private void validateRepeatQueryParameter(final ModelFieldType modelFieldType,
                                              final AnnotatedModelElement annotated) {
        if (modelFieldType == REST_CLIENT_RESPONSE) {
            throw new InterruptProcessingException(
                    annotated.getElementAnnotatedBy(RepeatQueryParameter.class).orElseThrow(IMPOSSIBLE_ERROR_ANNOTATION_NOT_FOUND_SUPPLIER),
                    "'@?' annotation can be applied for client HTTP request model only! Remove the redundant annotation!",
                    RepeatQueryParameter.class
            );
        }
        if (!supportedTypesProvider.isModelPrimitiveList(annotated.getField().asType())) {
            throw new InterruptProcessingException(
                    annotated.getElementAnnotatedBy(RepeatQueryParameter.class).orElseThrow(IMPOSSIBLE_ERROR_ANNOTATION_NOT_FOUND_SUPPLIER),
                    "'@?' annotation can be applied for array type only! Remove the redundant annotation!",
                    RepeatQueryParameter.class
            );
        }
    }
}