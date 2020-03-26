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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.component.HttpMethodMappingBuilder;
import io.rxmicro.annotation.processor.rest.component.RestRequestModelBuilder;
import io.rxmicro.annotation.processor.rest.component.RestResponseModelBuilder;
import io.rxmicro.annotation.processor.rest.component.impl.AbstractRestMethodSignatureBuilder;
import io.rxmicro.annotation.processor.rest.model.ParentUrl;
import io.rxmicro.annotation.processor.rest.model.RestRequestModel;
import io.rxmicro.annotation.processor.rest.server.component.RestControllerMethodSignatureBuilder;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethodSignature;
import io.rxmicro.documentation.Description;
import io.rxmicro.documentation.IncludeDescription;
import io.rxmicro.documentation.ModelExceptionErrorResponse;
import io.rxmicro.documentation.ResourceDefinition;
import io.rxmicro.documentation.SimpleErrorResponse;
import io.rxmicro.documentation.Title;
import io.rxmicro.rest.AddHeader;
import io.rxmicro.rest.HeaderMappingStrategy;
import io.rxmicro.rest.ParameterMappingStrategy;
import io.rxmicro.rest.SetHeader;
import io.rxmicro.rest.method.DELETE;
import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.method.HEAD;
import io.rxmicro.rest.method.OPTIONS;
import io.rxmicro.rest.method.PATCH;
import io.rxmicro.rest.method.POST;
import io.rxmicro.rest.method.PUT;
import io.rxmicro.rest.server.NotFoundMessage;
import io.rxmicro.rest.server.SetStatusCode;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.rxmicro.annotation.processor.common.util.Elements.allMethods;
import static io.rxmicro.annotation.processor.common.util.validators.AnnotationValidators.validateNoAnnotationPerElement;
import static io.rxmicro.annotation.processor.common.util.validators.AnnotationValidators.validateRedundantAnnotationsPerElement;
import static io.rxmicro.cdi.PostConstruct.DEFAULT_POST_CONSTRUCT_METHOD_NAME;
import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedSet;
import static java.util.Arrays.asList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class RestControllerMethodSignatureBuilderImpl extends AbstractRestMethodSignatureBuilder implements RestControllerMethodSignatureBuilder {

    @Inject
    private HttpMethodMappingBuilder httpMethodMappingBuilder;

    @Inject
    private RestRequestModelBuilder restRequestModelBuilder;

    @Inject
    private RestResponseModelBuilder restResponseModelBuilder;

    @Override
    public List<RestControllerMethodSignature> build(final ModuleElement restControllerModule,
                                                     final TypeElement restControllerClass,
                                                     final ParentUrl parentUrl) {
        final List<ExecutableElement> methods = allMethods(restControllerClass, el ->
                el.getAnnotationMirrors().stream().anyMatch(a -> getSupportedAnnotations().isAnnotationSupported(a.getAnnotationType())));
        validateMethods(methods);
        return methods.stream()
                .map(method -> getRestControllerMethodSignature(restControllerModule, parentUrl, method))
                .collect(Collectors.toList());
    }

    private RestControllerMethodSignature getRestControllerMethodSignature(final ModuleElement restControllerModule,
                                                                           final ParentUrl parentUrl,
                                                                           final ExecutableElement method) {
        final RestRequestModel requestModel = restRequestModelBuilder.build(restControllerModule, method, true);
        if (!requestModel.isVirtual()) {
            validateNoAnnotationPerElement(method, HeaderMappingStrategy.class);
            validateNoAnnotationPerElement(method, ParameterMappingStrategy.class);
        }
        return new RestControllerMethodSignature(
                parentUrl,
                method,
                requestModel,
                restResponseModelBuilder.build(restControllerModule, method, false),
                httpMethodMappingBuilder.buildList(parentUrl, method)
        );
    }

    private void validateMethods(final List<ExecutableElement> methods) {
        for (final ExecutableElement method : methods) {
            validateMethodName(method);
            final List<? extends AnnotationMirror> annotations = method.getAnnotationMirrors();
            validateRedundantAnnotationsPerElement(
                    method,
                    annotations,
                    getSupportedAnnotations(),
                    "Rest controller method"
            );
            validateAnnotatedClassMethodModifiers(
                    method,
                    annotations.stream()
                            .filter(a -> getHttpMethodAnnotations().isAnnotationSupported(a.getAnnotationType()))
                            .collect(Collectors.toList())
            );
        }
    }

    private void validateMethodName(final ExecutableElement method) {
        if (DEFAULT_POST_CONSTRUCT_METHOD_NAME.equals(method.getSimpleName().toString())) {
            throw new InterruptProcessingException(
                    method,
                    "Invalid rest controller method name. The name '?' is reserved for CDI module. Rename the method!",
                    DEFAULT_POST_CONSTRUCT_METHOD_NAME);
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
                // Response meta data
                SetStatusCode.class,
                NotFoundMessage.class,
                // Documentation meta data
                IncludeDescription.class,
                ModelExceptionErrorResponse.class,
                SimpleErrorResponse.class,
                Title.class,
                Description.class,
                ResourceDefinition.class,
                // static headers
                SetHeader.class,
                AddHeader.class
        ));
    }
}
