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

package io.rxmicro.annotation.processor.rest.client.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.AnnotatedModelElement;
import io.rxmicro.annotation.processor.common.model.ModelFieldType;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.client.model.RestClientObjectModelClass;
import io.rxmicro.annotation.processor.rest.component.AbstractRestModelFieldBuilder;
import io.rxmicro.annotation.processor.rest.model.InternalType;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.rest.Header;
import io.rxmicro.rest.Parameter;
import io.rxmicro.rest.PathVariable;
import io.rxmicro.rest.RequestId;
import io.rxmicro.rest.ResponseBody;
import io.rxmicro.rest.ResponseStatusCode;

import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;
import static io.rxmicro.annotation.processor.common.util.validators.AnnotationValidators.validateSupportedTypes;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class RestClientModelFieldBuilderImpl extends AbstractRestModelFieldBuilder {

    @Override
    protected RestClientObjectModelClass createObjectModelClass(final ModuleElement currentModule,
                                                                final ModelFieldType modelFieldType,
                                                                final TypeMirror type,
                                                                final TypeElement typeElement,
                                                                final int nestedLevel,
                                                                final boolean requireDefConstructor) {
        return new RestClientObjectModelClass(
                type,
                typeElement,
                getFieldMap(currentModule, modelFieldType, asTypeElement(type).orElseThrow(), nestedLevel, requireDefConstructor));
    }

    @Override
    protected Optional<RestModelField> buildInternal(final ModelFieldType modelFieldType,
                                                     final AnnotatedModelElement annotated) {
        if (HttpVersion.class.getName().equals(annotated.getField().asType().toString())) {
            validateInternalByType(modelFieldType, annotated);
            return Optional.of(new RestModelField(annotated, InternalType.HTTP_VERSION));
        } else if (HttpHeaders.class.getName().equals(annotated.getField().asType().toString())) {
            validateInternalByType(modelFieldType, annotated);
            return Optional.of(new RestModelField(annotated, InternalType.HTTP_HEADERS));
        } else {
            final ResponseStatusCode responseStatusCode = annotated.getAnnotation(ResponseStatusCode.class);
            if (responseStatusCode != null) {
                validateInternalByAnnotation(modelFieldType, annotated, ResponseStatusCode.class);
                validateSupportedTypes(annotated.getElement(), responseStatusCode.annotationType());
                return Optional.of(new RestModelField(annotated, InternalType.RESPONSE_STATUS));
            }
            final ResponseBody responseBody = annotated.getAnnotation(ResponseBody.class);
            if (responseBody != null) {
                validateInternalByAnnotation(modelFieldType, annotated, ResponseBody.class);
                validateSupportedTypes(annotated.getElement(), responseBody.annotationType());
                return Optional.of(new RestModelField(annotated, InternalType.RESPONSE_BODY));
            }
        }
        return Optional.empty();
    }

    @Override
    protected Set<Class<? extends Annotation>> supportedRequestAnnotations() {
        return Set.of(
                Header.class,
                Parameter.class,
                PathVariable.class,
                RequestId.class
        );
    }

    @Override
    protected Set<Class<? extends Annotation>> supportedResponseAnnotations() {
        return Set.of(
                Header.class,
                Parameter.class,
                ResponseBody.class,
                ResponseStatusCode.class
        );
    }

    private void validateInternalByType(final ModelFieldType modelFieldType,
                                        final AnnotatedModelElement annotated) {
        validateNoAnnotations(annotated);
        if (modelFieldType != ModelFieldType.REST_CLIENT_RESPONSE) {
            throw new InterruptProcessingException(
                    annotated.getElement(),
                    "'?' type can be used for response model only",
                    annotated.getField().asType().toString()
            );
        }
    }

    private void validateInternalByAnnotation(final ModelFieldType modelFieldType,
                                              final AnnotatedModelElement annotated,
                                              final Class<? extends Annotation> annotationClass) {
        if (modelFieldType != ModelFieldType.REST_CLIENT_RESPONSE) {
            throw new InterruptProcessingException(
                    annotated.getElement(),
                    "'?' annotation can be used for response model only",
                    annotationClass.getName()
            );
        }
    }
}
