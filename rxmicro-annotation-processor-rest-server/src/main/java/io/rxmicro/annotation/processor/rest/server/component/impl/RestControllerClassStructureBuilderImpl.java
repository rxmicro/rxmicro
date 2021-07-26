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
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.method.MethodBody;
import io.rxmicro.annotation.processor.common.model.method.MethodName;
import io.rxmicro.annotation.processor.common.model.type.IterableModelClass;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.rest.component.PathVariableValidator;
import io.rxmicro.annotation.processor.rest.model.HttpMethodMapping;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.StaticHeaders;
import io.rxmicro.annotation.processor.rest.server.component.RestControllerClassStructureBuilder;
import io.rxmicro.annotation.processor.rest.server.component.RestControllerMethodBodyBuilder;
import io.rxmicro.annotation.processor.rest.server.component.ServerCommonOptionBuilder;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassSignature;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructure;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructureStorage;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethod;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethodSignature;
import io.rxmicro.annotation.processor.rest.server.model.RestServerModuleGeneratorConfig;
import io.rxmicro.rest.model.UrlSegments;
import io.rxmicro.rest.server.NotFoundMessage;
import io.rxmicro.rest.server.SetStatusCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.Names.getPackageName;
import static io.rxmicro.annotation.processor.common.util.Reactives.isFuture;
import static io.rxmicro.annotation.processor.common.util.Reactives.isMaybe;
import static io.rxmicro.annotation.processor.common.util.Reactives.isMono;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class RestControllerClassStructureBuilderImpl implements RestControllerClassStructureBuilder {

    @Inject
    private Set<RestControllerMethodBodyBuilder> restControllerMethodBodyBuilders;

    @Inject
    private PathVariableValidator pathVariableValidator;

    @Inject
    private ServerCommonOptionBuilder serverCommonOptionBuilder;

    @Override
    public Set<RestControllerClassStructure> build(final EnvironmentContext environmentContext,
                                                   final RestControllerClassStructureStorage restControllerClassStructureStorage,
                                                   final Set<RestControllerClassSignature> classSignatures) {
        return classSignatures.stream()
                .map(signature -> build(environmentContext, restControllerClassStructureStorage, signature))
                .collect(toSet());
    }

    private RestControllerClassStructure build(final EnvironmentContext environmentContext,
                                               final RestControllerClassStructureStorage restControllerClassStructureStorage,
                                               final RestControllerClassSignature signature) {
        final TypeElement ownerClass = signature.getTypeElement();
        final List<RestControllerMethodSignature> methods = signature.getMethodSignatures();
        final Set<String> overloadedMethodNames = getOverloadedMethodNames(methods);
        final ClassHeader.Builder classHeaderBuilder = ClassHeader.newClassHeaderBuilder(getPackageName(ownerClass));
        final StaticHeaders staticHeaders =
                serverCommonOptionBuilder.getStaticHeaders(signature.getTypeElement(), signature.getParentUrl());
        final RestServerModuleGeneratorConfig restServerModuleGeneratorConfig =
                environmentContext.get(RestServerModuleGeneratorConfig.class);
        return new RestControllerClassStructure(
                restServerModuleGeneratorConfig,
                signature.getParentUrl(),
                classHeaderBuilder,
                ownerClass,
                methods.stream()
                        .map(method -> buildRestControllerMethod(
                                restServerModuleGeneratorConfig,
                                classHeaderBuilder,
                                method,
                                staticHeaders,
                                overloadedMethodNames.contains(method.getSimpleName()),
                                restControllerClassStructureStorage
                        ))
                        .collect(toList()),
                restControllerClassStructureStorage
        );
    }

    private Set<String> getOverloadedMethodNames(final List<RestControllerMethodSignature> methods) {
        final Map<String, List<RestControllerMethodSignature>> map = new HashMap<>();
        for (final RestControllerMethodSignature method : methods) {
            final List<RestControllerMethodSignature> list =
                    map.computeIfAbsent(method.getSimpleName(), n -> new ArrayList<>());
            list.add(method);
        }
        return map.entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .collect(toSet());
    }

    private RestControllerMethod buildRestControllerMethod(final RestServerModuleGeneratorConfig restServerModuleGeneratorConfig,
                                                           final ClassHeader.Builder classHeaderBuilder,
                                                           final RestControllerMethodSignature methodSignature,
                                                           final StaticHeaders staticHeadersPerType,
                                                           final boolean isOverloaded,
                                                           final RestControllerClassStructureStorage restControllerClassStructureStorage) {
        final List<HttpMethodMapping> httpMethodMappings = methodSignature.getHttpMethodMappings();
        validatePathVariables(methodSignature, httpMethodMappings);
        validateNestedModel(methodSignature, httpMethodMappings, restControllerClassStructureStorage);
        final MethodName methodName = new MethodName(
                methodSignature.getSimpleName(),
                methodSignature.getExecutableElement().getParameters().stream().map(Element::asType).collect(toList()),
                isOverloaded);
        final int successStatusCode = Optional.ofNullable(methodSignature.getExecutableElement().getAnnotation(SetStatusCode.class))
                .map(SetStatusCode::value)
                .orElse(200);
        final StaticHeaders staticHeaders = new StaticHeaders(staticHeadersPerType);
        staticHeaders.setOrAddAll(
                serverCommonOptionBuilder.getStaticHeaders(methodSignature.getExecutableElement(), methodSignature.getParentUrl())
        );
        final MethodBody methodBody = buildMethodBody(
                restServerModuleGeneratorConfig,
                classHeaderBuilder,
                methodSignature,
                staticHeaders,
                methodName,
                successStatusCode,
                restControllerClassStructureStorage
        );
        final boolean notFoundPossible = isNotFoundPossible(methodSignature);
        validateNotFoundPossibleState(methodSignature, notFoundPossible);
        return new RestControllerMethod(
                httpMethodMappings,
                methodSignature.getExecutableElement(),
                methodName,
                methodSignature.getRequestModel(),
                methodBody,
                methodSignature.getResponseModel(),
                successStatusCode, notFoundPossible);
    }

    private void validatePathVariables(final RestControllerMethodSignature methodSignature,
                                       final List<HttpMethodMapping> httpMethodMappings) {
        if (!methodSignature.getRequestModel().requestModelNotExists()) {
            final TypeElement type = methodSignature.getRequestModel().getRequiredRequestType();
            for (final HttpMethodMapping httpMethodMapping : httpMethodMappings) {
                if (httpMethodMapping.isUrlSegmentsPresent()) {
                    final UrlSegments urlSegments = httpMethodMapping.getUrlSegments();
                    pathVariableValidator.validateThatPathVariablesNotMissingOrRedundant(
                            methodSignature.getExecutableElement(),
                            httpMethodMapping,
                            urlSegments,
                            type
                    );
                } else {
                    pathVariableValidator.validateThatPathVariablesNotFound(
                            methodSignature.getExecutableElement(),
                            httpMethodMapping,
                            type
                    );
                }
            }
        }
    }

    private void validateNestedModel(final RestControllerMethodSignature methodSignature,
                                     final List<HttpMethodMapping> httpMethodMappings,
                                     final RestControllerClassStructureStorage restControllerClassStructureStorage) {
        if (!methodSignature.getRequestModel().requestModelNotExists()) {
            for (final HttpMethodMapping httpMethodMapping : httpMethodMappings) {
                if (!httpMethodMapping.isHttpBody()) {
                    final RestObjectModelClass restObjectModelClass =
                            restControllerClassStructureStorage.getModelReaderClassStructure(
                                    methodSignature.getRequestModel().getRequiredRequestType().asType().toString()
                            ).orElseThrow().getModelClass();
                    if (!restObjectModelClass.getAllChildrenObjectModelClasses().isEmpty()) {
                        throw new InterruptProcessingException(methodSignature.getExecutableElement(),
                                "Nested model classes not allowed for @?(\"?\")",
                                httpMethodMapping.getMethod(), httpMethodMapping.getExactOrTemplateUri());
                    }
                    if (restObjectModelClass.getParamEntries().stream()
                            .map(Map.Entry::getValue)
                            .filter(ModelClass::isIterable)
                            .map(ModelClass::asIterable)
                            .anyMatch(IterableModelClass::isMap)) {
                        throw new InterruptProcessingException(methodSignature.getExecutableElement(),
                                "java.util.Map<String, ?> model item container not allowed for @?(\"?\")",
                                "?", httpMethodMapping.getMethod(), httpMethodMapping.getExactOrTemplateUri());
                    }
                }
            }
        }
    }

    private MethodBody buildMethodBody(final RestServerModuleGeneratorConfig restServerModuleGeneratorConfig,
                                       final ClassHeader.Builder classHeaderBuilder,
                                       final RestControllerMethodSignature methodSignature,
                                       final StaticHeaders staticHeaders,
                                       final MethodName methodName,
                                       final int successStatusCode,
                                       final RestControllerClassStructureStorage restControllerClassStructureStorage) {
        for (final RestControllerMethodBodyBuilder restControllerMethodBodyBuilder : restControllerMethodBodyBuilders) {
            if (restControllerMethodBodyBuilder.isSupport(methodSignature)) {
                return restControllerMethodBodyBuilder.build(
                        restServerModuleGeneratorConfig,
                        classHeaderBuilder,
                        methodName,
                        successStatusCode,
                        staticHeaders,
                        methodSignature.getRequestModel(),
                        methodSignature.getResponseModel(),
                        restControllerClassStructureStorage);
            }
        }
        throw new InterruptProcessingException(
                methodSignature.getExecutableElement(),
                "The RxMicro framework does not know how to generate a body of this method"
        );
    }

    private boolean isNotFoundPossible(final RestControllerMethodSignature methodSignature) {
        return methodSignature.getResponseModel().getReactiveType()
                .map(t -> isMono(t) || isMaybe(t) || isFuture(t) && methodSignature.getResponseModel().isOptional())
                .orElse(false);
    }

    private void validateNotFoundPossibleState(final RestControllerMethodSignature methodSignature,
                                               final boolean notFoundPossible) {
        final NotFoundMessage notFoundMessage = methodSignature.getExecutableElement().getAnnotation(NotFoundMessage.class);
        if (notFoundMessage != null && !notFoundPossible) {
            throw new InterruptProcessingException(
                    methodSignature.getExecutableElement(),
                    "This rest controller method does not support optional result. " +
                            "Thus, the '@?' annotation is redundant. " +
                            "Remove this annotation or change return type!",
                    NotFoundMessage.class.getName()
            );
        }
    }
}
