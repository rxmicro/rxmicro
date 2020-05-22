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

package io.rxmicro.annotation.processor.rest.server.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.AnnotatedModelElement;
import io.rxmicro.annotation.processor.common.model.ModelFieldType;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.component.AbstractRestModelFieldBuilder;
import io.rxmicro.annotation.processor.rest.model.InternalType;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.server.model.RestServerObjectModelClass;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.rest.Header;
import io.rxmicro.rest.Parameter;
import io.rxmicro.rest.PathVariable;
import io.rxmicro.rest.RemoteAddress;
import io.rxmicro.rest.RequestBody;
import io.rxmicro.rest.RequestId;
import io.rxmicro.rest.RequestMethod;
import io.rxmicro.rest.RequestUrlPath;
import io.rxmicro.rest.ResponseBody;
import io.rxmicro.rest.ResponseStatusCode;
import io.rxmicro.rest.server.detail.model.HttpRequest;

import java.lang.annotation.Annotation;
import java.net.SocketAddress;
import java.util.Optional;
import java.util.Set;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.model.ModelFieldType.REST_SERVER_REQUEST;
import static io.rxmicro.annotation.processor.common.model.ModelFieldType.REST_SERVER_RESPONSE;
import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;
import static io.rxmicro.annotation.processor.common.util.validators.AnnotationValidators.validateSupportedTypes;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class RestServerModelFieldBuilderImpl extends AbstractRestModelFieldBuilder {

    private final Set<Class<?>> requestInternalTypes =
            Set.of(HttpRequest.class, HttpVersion.class, HttpHeaders.class, SocketAddress.class);

    private final Set<Class<?>> responseInternalTypes =
            Set.of(HttpVersion.class, HttpHeaders.class);

    @Override
    protected RestObjectModelClass createObjectModelClass(final ModuleElement currentModule,
                                                          final ModelFieldType modelFieldType,
                                                          final TypeMirror type,
                                                          final TypeElement typeElement,
                                                          final int nestedLevel,
                                                          final boolean requireDefConstructor) {
        return new RestServerObjectModelClass(
                type,
                typeElement,
                getFieldMap(currentModule, modelFieldType, asTypeElement(type).orElseThrow(), nestedLevel, requireDefConstructor));
    }

    @Override
    protected Optional<RestModelField> buildInternal(final ModelFieldType modelFieldType,
                                                     final AnnotatedModelElement annotated) {
        validateInternalTypes(annotated);
        if (HttpRequest.class.getName().equals(annotated.getField().asType().toString())) {
            validateInternalByType(modelFieldType, annotated, HttpRequest.class);
            return Optional.of(new RestModelField(annotated, InternalType.HTTP_REQUEST));
        } else if (HttpVersion.class.getName().equals(annotated.getField().asType().toString())) {
            validateInternalByType(modelFieldType, annotated, HttpVersion.class);
            return Optional.of(new RestModelField(annotated, InternalType.HTTP_VERSION));
        } else if (HttpHeaders.class.getName().equals(annotated.getField().asType().toString())) {
            validateInternalByType(modelFieldType, annotated, HttpHeaders.class);
            return Optional.of(new RestModelField(annotated, InternalType.HTTP_HEADERS));
        } else if (SocketAddress.class.getName().equals(annotated.getField().asType().toString())) {
            if (annotated.getAnnotation(RemoteAddress.class) != null) {
                validateInternalByAnnotation(modelFieldType, annotated, RemoteAddress.class);
            } else {
                validateInternalByType(modelFieldType, annotated, SocketAddress.class);
            }
            return Optional.of(new RestModelField(annotated, InternalType.REMOTE_ADDRESS));
        } else {
            return buildRequestInternal(modelFieldType, annotated).or(() -> buildResponseInternal(modelFieldType, annotated));
        }
    }

    private Optional<RestModelField> buildRequestInternal(final ModelFieldType modelFieldType,
                                                          final AnnotatedModelElement annotated) {
        final RequestUrlPath requestUrlPath = annotated.getAnnotation(RequestUrlPath.class);
        if (requestUrlPath != null) {
            validateInternalByAnnotation(modelFieldType, annotated, RequestUrlPath.class);
            validateSupportedTypes(annotated.getField(), requestUrlPath.annotationType());
            return Optional.of(new RestModelField(annotated, InternalType.REQUEST_URL));
        }
        final RequestMethod requestMethod = annotated.getAnnotation(RequestMethod.class);
        if (requestMethod != null) {
            validateInternalByAnnotation(modelFieldType, annotated, RequestMethod.class);
            validateSupportedTypes(annotated.getField(), requestMethod.annotationType());
            return Optional.of(new RestModelField(annotated, InternalType.REQUEST_METHOD));
        }
        final RemoteAddress remoteAddress = annotated.getAnnotation(RemoteAddress.class);
        if (remoteAddress != null) {
            validateInternalByAnnotation(modelFieldType, annotated, RemoteAddress.class);
            validateSupportedTypes(annotated.getField(), remoteAddress.annotationType());
            return Optional.of(new RestModelField(annotated, InternalType.REMOTE_ADDRESS));
        }
        final RequestBody requestBody = annotated.getAnnotation(RequestBody.class);
        if (requestBody != null) {
            validateInternalByAnnotation(modelFieldType, annotated, RequestBody.class);
            validateSupportedTypes(annotated.getField(), requestBody.annotationType());
            return Optional.of(new RestModelField(annotated, InternalType.REQUEST_BODY));
        }
        return Optional.empty();
    }

    private Optional<RestModelField> buildResponseInternal(final ModelFieldType modelFieldType,
                                                           final AnnotatedModelElement annotated) {
        final ResponseStatusCode responseStatusCode = annotated.getAnnotation(ResponseStatusCode.class);
        if (responseStatusCode != null) {
            validateInternalByAnnotation(modelFieldType, annotated, ResponseStatusCode.class);
            validateSupportedTypes(annotated.getField(), responseStatusCode.annotationType());
            return Optional.of(new RestModelField(annotated, InternalType.RESPONSE_STATUS));
        }
        final ResponseBody responseBody = annotated.getAnnotation(ResponseBody.class);
        if (responseBody != null) {
            validateInternalByAnnotation(modelFieldType, annotated, ResponseBody.class);
            validateSupportedTypes(annotated.getField(), responseBody.annotationType());
            return Optional.of(new RestModelField(annotated, InternalType.RESPONSE_BODY));
        }
        return Optional.empty();
    }

    @Override
    protected Set<Class<? extends Annotation>> supportedRequestAnnotations() {
        return Set.of(
                Header.class,
                Parameter.class,
                PathVariable.class,
                RequestMethod.class,
                RequestUrlPath.class,
                RequestId.class,
                RemoteAddress.class,
                RequestBody.class
        );
    }

    @Override
    protected Set<Class<? extends Annotation>> supportedResponseAnnotations() {
        return Set.of(
                Header.class,
                Parameter.class,
                ResponseStatusCode.class,
                ResponseBody.class
        );
    }

    private void validateInternalTypes(final AnnotatedModelElement annotated) {
        if ("io.netty.handler.codec.http.HttpRequest".equals(annotated.getField().asType().toString())) {
            throw new InterruptProcessingException(
                    annotated.getField(),
                    "Use '?' type instead of 'io.netty.handler.codec.http.HttpRequest' type!",
                    HttpRequest.class.getName()
            );
        }
        if ("io.netty.handler.codec.http.HttpVersion".equals(annotated.getField().asType().toString())) {
            throw new InterruptProcessingException(
                    annotated.getField(),
                    "Use '?' type instead of 'io.netty.handler.codec.http.HttpVersion' type!",
                    HttpVersion.class.getName()
            );
        }
        if ("io.netty.handler.codec.http.HttpHeaders".equals(annotated.getField().asType().toString())) {
            throw new InterruptProcessingException(
                    annotated.getField(),
                    "Use '?' type instead of 'io.netty.handler.codec.http.HttpHeaders' type!",
                    HttpHeaders.class.getName()
            );
        }
    }

    private void validateInternalByType(final ModelFieldType modelFieldType,
                                        final AnnotatedModelElement annotated,
                                        final Class<?> type) {
        validateNoAnnotations(annotated);
        if (!requestInternalTypes.contains(type) && modelFieldType == REST_SERVER_REQUEST) {
            throw new InterruptProcessingException(
                    annotated.getField(),
                    "'?' type is not supported for request",
                    annotated.getField().asType().toString()
            );
        }
        if (!responseInternalTypes.contains(type) && modelFieldType == REST_SERVER_RESPONSE) {
            throw new InterruptProcessingException(
                    annotated.getField(),
                    "'?' type is not supported for response",
                    annotated.getField().asType().toString()
            );
        }
    }

    private void validateInternalByAnnotation(final ModelFieldType modelFieldType,
                                              final AnnotatedModelElement annotated,
                                              final Class<? extends Annotation> annotationClass) {
        if (!supportedRequestAnnotations().contains(annotationClass) && modelFieldType == REST_SERVER_REQUEST) {
            throw new InterruptProcessingException(
                    annotated.getField(),
                    "'?' annotation is not supported for request",
                    annotationClass.getName()
            );
        }
        if (!supportedResponseAnnotations().contains(annotationClass) && modelFieldType == REST_SERVER_RESPONSE) {
            throw new InterruptProcessingException(
                    annotated.getField(),
                    "'?' annotation is not supported for response",
                    annotationClass.getName()
            );
        }
    }
}
