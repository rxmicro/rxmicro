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

package io.rxmicro.annotation.processor.rest.client.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.client.component.RestClientMethodSignatureBuilder;
import io.rxmicro.annotation.processor.rest.client.model.RestClientMethodSignature;
import io.rxmicro.annotation.processor.rest.component.HttpMethodMappingBuilder;
import io.rxmicro.annotation.processor.rest.component.RestRequestModelBuilder;
import io.rxmicro.annotation.processor.rest.component.RestResponseModelBuilder;
import io.rxmicro.annotation.processor.rest.component.impl.AbstractRestMethodSignatureBuilder;
import io.rxmicro.annotation.processor.rest.model.ParentUrl;
import io.rxmicro.annotation.processor.rest.model.RestResponseModel;
import io.rxmicro.rest.AddHeader;
import io.rxmicro.rest.AddQueryParameter;
import io.rxmicro.rest.HeaderMappingStrategy;
import io.rxmicro.rest.ParameterMappingStrategy;
import io.rxmicro.rest.SetHeader;
import io.rxmicro.rest.SetQueryParameter;
import io.rxmicro.rest.method.DELETE;
import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.method.HEAD;
import io.rxmicro.rest.method.OPTIONS;
import io.rxmicro.rest.method.PATCH;
import io.rxmicro.rest.method.POST;
import io.rxmicro.rest.method.PUT;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.AnnotationValidators.validateNoRxMicroAnnotationsPerElement;
import static io.rxmicro.annotation.processor.common.util.AnnotationValidators.validateOnlyOneAnnotationPerElement;
import static io.rxmicro.annotation.processor.common.util.AnnotationValidators.validateRedundantAnnotationsPerElement;
import static io.rxmicro.annotation.processor.common.util.Elements.allImplementableMethods;
import static io.rxmicro.annotation.processor.common.util.Elements.methodSignatureEquals;
import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedSet;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class RestClientMethodSignatureBuilderImpl extends AbstractRestMethodSignatureBuilder implements RestClientMethodSignatureBuilder {

    @Inject
    private RestRequestModelBuilder restRequestModelBuilder;

    @Inject
    private RestResponseModelBuilder restResponseModelBuilder;

    @Inject
    private HttpMethodMappingBuilder httpMethodMappingBuilder;

    @Override
    public List<RestClientMethodSignature> build(final ModuleElement restControllerModule,
                                                 final TypeElement restClientInterface,
                                                 final ParentUrl parentUrl,
                                                 final Map.Entry<TypeElement, List<ExecutableElement>> overriddenMethodCandidates) {
        final List<ExecutableElement> methods = allImplementableMethods(restClientInterface);
        validateMethods(methods, overriddenMethodCandidates.getValue());
        return methods.stream()
                .filter(e -> notContainIn(e, overriddenMethodCandidates.getValue()))
                .map(method -> {
                    final RestResponseModel responseModel = restResponseModelBuilder.build(restControllerModule, method, true);
                    validateReturnType(method, responseModel);
                    return new RestClientMethodSignature(
                            method,
                            restRequestModelBuilder.build(restControllerModule, method, false),
                            responseModel,
                            httpMethodMappingBuilder.buildSingle(parentUrl, method)
                    );
                })
                .collect(toList());
    }

    private void validateMethods(final List<ExecutableElement> methods,
                                 final List<ExecutableElement> overriddenMethods) {
        for (final ExecutableElement method : methods) {
            final List<? extends AnnotationMirror> annotations = method.getAnnotationMirrors();
            validateOnlyOneAnnotationPerElement(method, annotations, getHttpMethodAnnotations());
            validateRedundantAnnotationsPerElement(
                    method,
                    annotations,
                    getSupportedAnnotations(),
                    "Rest client method"
            );
            annotations.stream()
                    .filter(annotation -> getHttpMethodAnnotations().isAnnotationSupported(annotation.getAnnotationType()))
                    .findFirst()
                    .ifPresent(annotation -> validateAnnotatedInterfaceMethodModifiers(method, annotation));
            if (overriddenMethods.stream().anyMatch(otherMethod -> methodSignatureEquals(method, otherMethod))) {
                validateNoRxMicroAnnotationsPerElement(method, "current method is already implemented");
            }
        }
    }

    private void validateReturnType(final ExecutableElement method,
                                    final RestResponseModel responseModel) {
        if (responseModel.isVoidReturn()) {
            throw new InterruptProcessingException(method,
                    "'void' return type not allowed: Use CompletableFuture<Void>, Mono<Void>, etc instead");
        }
        if (responseModel.isOptional()) {
            final TypeMirror typeMirror = responseModel.getReactiveType().orElseThrow();
            throw new InterruptProcessingException(method,
                    "Optional not allowed here. So replace ?<Optional<TYPE>> by ?<TYPE>",
                    typeMirror, typeMirror
            );
        }
    }

    @Override
    protected Set<Class<? extends Annotation>> getSupportedAnnotationsPerMethod() {
        return unmodifiableOrderedSet(asList(
                // Http methods
                GET.class,
                POST.class,
                PUT.class,
                DELETE.class,
                PATCH.class,
                OPTIONS.class,
                HEAD.class,
                // Virtual request mapping strategy
                HeaderMappingStrategy.class,
                ParameterMappingStrategy.class,
                // Rest client meta data
                AddHeader.class,
                SetHeader.class,
                AddQueryParameter.class,
                SetQueryParameter.class
        ));
    }
}
