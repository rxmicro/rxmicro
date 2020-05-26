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
import io.rxmicro.rest.Header;
import io.rxmicro.rest.HeaderMappingStrategy;
import io.rxmicro.rest.RepeatHeader;
import io.rxmicro.rest.model.HttpModelType;

import java.util.Set;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.model.ModelFieldType.REST_CLIENT_RESPONSE;
import static io.rxmicro.annotation.processor.common.model.ModelFieldType.REST_SERVER_REQUEST;
import static io.rxmicro.annotation.processor.common.util.Errors.IMPOSSIBLE_ERROR_ANNOTATION_NOT_FOUND_SUPPLIER;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.http.local.HttpValidators.validateHeaderName;

/**
 * @author nedis
 * @since 0.5
 */
@Singleton
public final class HeaderRestModelFieldBuilder extends AbstractProcessorComponent implements RestModelFieldBuilder<Header> {

    @Inject
    private SupportedTypesProvider supportedTypesProvider;

    @Override
    @SuppressWarnings("Convert2MethodRef")
    public RestModelField build(final ModelFieldType modelFieldType,
                                final TypeElement typeElement,
                                final AnnotatedModelElement annotated,
                                final Header header,
                                final Set<String> modelNames,
                                final int nestedLevel) {
        final VariableElement field = annotated.getField();
        if (nestedLevel > 1) {
            error(
                    annotated.getElementAnnotatedBy(Header.class).orElse(field),
                    "Annotation @? not allowed here. Header can be defined in root class only",
                    Header.class.getSimpleName()
            );
        }
        final TypeMirror fieldType = field.asType();
        if (!supportedTypesProvider.isModelPrimitive(fieldType) && !supportedTypesProvider.isModelPrimitiveList(fieldType)) {
            error(
                    annotated.getElementAnnotatedBy(Header.class).orElse(field),
                    "Invalid header type. Allowed types are: ?",
                    supportedTypesProvider.getPrimitives()
            );
        }
        final HeaderMappingStrategy strategy = typeElement.getAnnotation(HeaderMappingStrategy.class);
        final String modelName = annotated.getModelName(header.value(), strategy, () -> strategy.value());
        if (!modelNames.add(modelName)) {
            error(
                    annotated.getElementAnnotatedBy(Header.class).orElse(field),
                    "Detected duplicate of HTTP header name: ?. For multi value header use List<?> type and '@?' annotation",
                    modelName, getSimpleName(field.asType()), RepeatHeader.class
            );
        }
        final boolean repeat = annotated.isAnnotationPresent(RepeatHeader.class);
        validateHeader(modelFieldType, annotated, modelName, repeat);
        return new RestModelField(annotated, HttpModelType.HEADER, modelName, repeat);
    }

    private void validateHeader(final ModelFieldType modelFieldType,
                                final AnnotatedModelElement annotated,
                                final String modelName,
                                final boolean repeat) {
        try {
            validateHeaderName(modelName);
        } catch (final IllegalArgumentException ex) {
            error(
                    annotated.getElementAnnotatedBy(Header.class).orElse(annotated.getField()),
                    ex.getMessage()
            );
        }
        if (repeat) {
            validateRepeatHeader(modelFieldType, annotated);
        }
    }

    private void validateRepeatHeader(final ModelFieldType modelFieldType,
                                      final AnnotatedModelElement annotated) {
        if (modelFieldType == REST_SERVER_REQUEST) {
            throw new InterruptProcessingException(
                    annotated.getElementAnnotatedBy(RepeatHeader.class).orElseThrow(IMPOSSIBLE_ERROR_ANNOTATION_NOT_FOUND_SUPPLIER),
                    "'@?' annotation can be applied for server HTTP response model only! Remove the redundant annotation!",
                    RepeatHeader.class
            );
        }
        if (modelFieldType == REST_CLIENT_RESPONSE) {
            throw new InterruptProcessingException(
                    annotated.getElementAnnotatedBy(RepeatHeader.class).orElseThrow(IMPOSSIBLE_ERROR_ANNOTATION_NOT_FOUND_SUPPLIER),
                    "'@?' annotation can be applied for client HTTP request model only! Remove the redundant annotation!",
                    RepeatHeader.class
            );
        }
        if (!supportedTypesProvider.isModelPrimitiveList(annotated.getField().asType())) {
            throw new InterruptProcessingException(
                    annotated.getElementAnnotatedBy(RepeatHeader.class).orElseThrow(IMPOSSIBLE_ERROR_ANNOTATION_NOT_FOUND_SUPPLIER),
                    "'@?' annotation can be applied for array type only! Remove the redundant annotation!",
                    RepeatHeader.class
            );
        }
    }
}
