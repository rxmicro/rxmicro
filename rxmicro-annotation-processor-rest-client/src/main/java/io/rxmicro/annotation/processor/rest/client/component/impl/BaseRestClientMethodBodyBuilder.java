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
import io.rxmicro.annotation.processor.common.component.MethodBodyGenerator;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.rest.client.component.RestClientMethodBodyBuilder;
import io.rxmicro.annotation.processor.rest.client.model.RestClientClassStructureStorage;
import io.rxmicro.annotation.processor.rest.client.model.RestClientMethodSignature;
import io.rxmicro.annotation.processor.rest.client.model.RestClientModuleGeneratorConfig;
import io.rxmicro.annotation.processor.rest.client.model.RestClientSimpleObjectModelClass;
import io.rxmicro.annotation.processor.rest.component.PathVariableValidator;
import io.rxmicro.annotation.processor.rest.model.RestResponseModel;
import io.rxmicro.annotation.processor.rest.model.StaticHeaders;
import io.rxmicro.annotation.processor.rest.model.StaticQueryParameters;
import io.rxmicro.http.error.ValidationException;
import io.rxmicro.rest.client.RestClientGeneratorConfig;
import io.rxmicro.rest.client.detail.ModelReader;
import io.rxmicro.rest.client.detail.PathBuilder;
import io.rxmicro.rest.model.UrlSegments;
import io.rxmicro.tool.common.OverrideGeneratorConfig;
import io.rxmicro.validation.detail.ResponseValidators;

import java.util.HashMap;
import java.util.Map;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.Annotations.getRequiredAnnotationClassParameter;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerInstanceName;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class BaseRestClientMethodBodyBuilder implements RestClientMethodBodyBuilder {

    @Inject
    protected MethodBodyGenerator methodBodyGenerator;

    @Inject
    private PathVariableValidator pathVariableValidator;

    protected final Map<String, Object> createTemplateArguments(final StaticHeaders staticHeaders,
                                                                final StaticQueryParameters staticQueryParameters,
                                                                final RestClientMethodSignature methodSignature) {
        final RestResponseModel responseModel = methodSignature.getResponseModel();
        final Map<String, Object> templateArguments = new HashMap<>();
        templateArguments.put("RETURN", responseModel.getMethodResult().orElseThrow());
        templateArguments.put("HEADERS", staticHeaders.getEntries());
        templateArguments.put("QUERY_PARAMS", staticQueryParameters.getEntries());
        templateArguments.put("HTTP_METHOD", methodSignature.getHttpMethodMapping().getMethod());
        templateArguments.put("PATH", methodSignature.getHttpMethodMapping().getUri());
        if (!responseModel.isReactiveVoid()) {
            final TypeElement resultType = responseModel.getResultType().orElseThrow();
            templateArguments.put("RESPONSE_READER", getModelTransformerInstanceName(
                    getSimpleName(resultType),
                    ModelReader.class)
            );
            templateArguments.put("RESPONSE_MODEL_CLASS", new RestClientSimpleObjectModelClass(resultType));
        }
        return templateArguments;
    }

    protected final void addPathBuilder(final RestClientMethodSignature methodSignature,
                                        final Map<String, Object> templateArguments) {
        final TypeElement type = methodSignature.getRequestModel().getRequiredRequestType();
        if (methodSignature.getHttpMethodMapping().isUrlSegmentsPresent()) {
            final UrlSegments urlSegments = methodSignature.getHttpMethodMapping().getUrlSegments();
            pathVariableValidator.validateThatPathVariablesNotMissingOrRedundant(
                    methodSignature.getMethod(),
                    methodSignature.getHttpMethodMapping(),
                    urlSegments,
                    type
            );
            templateArguments.put("URL_TEMPLATE", urlSegments.getUrlTemplate());
            templateArguments.put("URL_TEMPLATE_KEY", urlSegments.getOriginalUrl());
            templateArguments.put("PATH_BUILDER", getModelTransformerInstanceName(type, PathBuilder.class));
        } else {
            pathVariableValidator.validateThatPathVariablesNotFound(
                    methodSignature.getMethod(),
                    methodSignature.getHttpMethodMapping(),
                    type
            );
        }
    }

    protected final void addValidators(final EnvironmentContext environmentContext,
                                       final RestClientClassStructureStorage storage,
                                       final ClassHeader.Builder classHeaderBuilder,
                                       final TypeElement parameterType,
                                       final RestResponseModel methodResult,
                                       final Map<String, Object> templateArguments) {
        final RestClientModuleGeneratorConfig config = environmentContext.get(RestClientModuleGeneratorConfig.class);
        final boolean generateRequestValidators = config.isGenerateRequestValidators() &&
                storage.isRequestValidatorPresent(parameterType.asType().toString());
        templateArguments.put("GENERATE_REQUEST_VALIDATORS", generateRequestValidators);
        if (generateRequestValidators) {
            templateArguments.put("REQUEST_MODEL_CLASS", new RestClientSimpleObjectModelClass(parameterType));
            classHeaderBuilder.addImports(ValidationException.class);
        }
        final boolean generateResponseValidators =
                config.isGenerateResponseValidators() &&
                        !methodResult.isReactiveVoid() &&
                        methodResult.getResultType().isPresent() &&
                        storage.isResponseValidatorPresent(
                                methodResult.getResultType().orElseThrow().asType().toString());
        if (generateResponseValidators) {
            if (methodResult.isFuture()) {
                classHeaderBuilder.addStaticImport(ResponseValidators.class, "validateIfResponseExists");
            } else {
                classHeaderBuilder.addStaticImport(ResponseValidators.class, "validateResponse");
            }
        }
        templateArguments.put("GENERATE_RESPONSE_VALIDATORS", generateResponseValidators);
    }

    protected final RestClientGeneratorConfig.RequestValidationMode getRequestValidationMode(
            final RestClientMethodSignature methodSignature,
            final EnvironmentContext environmentContext) {

        final OverrideGeneratorConfig[] overrideGeneratorConfigs =
                methodSignature.getRestClientClassSignature().getRestClientInterface().getAnnotationsByType(OverrideGeneratorConfig.class);
        for (final OverrideGeneratorConfig overrideGeneratorConfig : overrideGeneratorConfigs) {
            if (RestClientGeneratorConfig.class.getName().equals(
                    getRequiredAnnotationClassParameter(overrideGeneratorConfig::annotationConfigClass).getQualifiedName().toString()) &&
                    "requestValidationMode".equals(overrideGeneratorConfig.parameterName())) {
                return RestClientGeneratorConfig.RequestValidationMode.valueOf(overrideGeneratorConfig.overriddenValue());
            }
        }
        return environmentContext.get(RestClientModuleGeneratorConfig.class).getRequestValidationMode();
    }
}
