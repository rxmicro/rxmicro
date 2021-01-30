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

package io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.source.tree.MethodTree;
import com.sun.source.util.Trees;
import io.rxmicro.annotation.processor.common.component.impl.BaseProcessorComponent;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.DocumentedModelFieldBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.DocumentedModelField;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.Response;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.documentation.ResourceDefinition;
import io.rxmicro.http.error.HttpErrorException;
import io.rxmicro.rest.model.HttpModelType;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.Elements.allMethodsFromType;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getProcessingEnvironment;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_STRICT_MODE;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_STRICT_MODE_DEFAULT_VALUE;
import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.DocumentedModelFieldBuilder.buildRequestIdHeaderDocumentedModelField;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static java.util.Map.entry;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.7
 */
@Singleton
public final class ModelExceptionErrorResponseCustomBuilder extends BaseProcessorComponent {

    @Inject
    private DocumentedModelFieldBuilder documentedModelFieldBuilder;

    void setResponseHeaders(final EnvironmentContext environmentContext,
                            final ResourceDefinition resourceDefinition,
                            final ProjectMetaData projectMetaData,
                            final TypeElement exceptionTypeElement,
                            final RestObjectModelClass modelClass,
                            final Response.Builder responseBuilder) {
        if (resourceDefinition.withHeadersDescriptionTable()) {
            final List<DocumentedModelField> headers = documentedModelFieldBuilder.buildSimple(
                    environmentContext,
                    true,
                    projectMetaData.getProjectDirectory(),
                    modelClass,
                    HttpModelType.HEADER,
                    true
            );
            if (!headers.isEmpty()) {
                validateGetResponseHeadersMethod(exceptionTypeElement, modelClass);
            }
            // Add Request-Id header if not defined!
            if (resourceDefinition.withRequestIdResponseHeader() &&
                    headers.stream().noneMatch(f -> f.getName().equalsIgnoreCase(REQUEST_ID))) {
                headers.add(0, buildRequestIdHeaderDocumentedModelField(true));
            }
            responseBuilder.setHeaders(headers);
        }
    }

    void setResponseBody(final EnvironmentContext environmentContext,
                         final ResourceDefinition resourceDefinition,
                         final ProjectMetaData projectMetaData,
                         final TypeElement exceptionTypeElement,
                         final RestObjectModelClass modelClass,
                         final Response.Builder responseBuilder) {
        validateGetResponseBodyMethod(exceptionTypeElement, modelClass);
        if (resourceDefinition.withBodyParametersDescriptionTable()) {
            final List<DocumentedModelField> documentedModelFields = documentedModelFieldBuilder.buildSimple(
                    environmentContext,
                    true,
                    projectMetaData.getProjectDirectory(),
                    modelClass,
                    HttpModelType.PARAMETER,
                    true
            );
            responseBuilder.setParameters(List.of(entry("Body", documentedModelFields)));
        }
    }

    /**
     * @see HttpErrorException#getResponseHeaders()
     */
    private void validateGetResponseHeadersMethod(final TypeElement exceptionTypeElement,
                                                  final RestObjectModelClass modelClass) {
        final ExecutableElement method = getRequiredMethod(exceptionTypeElement, "getResponseHeaders");
        if (getBooleanOption(RX_MICRO_STRICT_MODE, RX_MICRO_STRICT_MODE_DEFAULT_VALUE)) {
            validateMethodBody(method, modelClass.getHeaderEntries());
        }
    }

    /**
     * @see HttpErrorException#getResponseBody()
     */
    private void validateGetResponseBodyMethod(final TypeElement exceptionTypeElement,
                                               final RestObjectModelClass modelClass) {
        final ExecutableElement method = getRequiredMethod(exceptionTypeElement, "getResponseBody");
        if (getBooleanOption(RX_MICRO_STRICT_MODE, RX_MICRO_STRICT_MODE_DEFAULT_VALUE)) {
            validateMethodBody(method, modelClass.getParamEntries());
        }
    }

    private ExecutableElement getRequiredMethod(final TypeElement exceptionTypeElement,
                                                final String methodName) {
        final List<ExecutableElement> methods = allMethodsFromType(
                exceptionTypeElement, e -> methodName.equals(e.getSimpleName().toString()) && e.getParameters().isEmpty()
        );
        if (methods.isEmpty()) {
            throw new InterruptProcessingException(
                    exceptionTypeElement,
                    "Exception type with custom fields must override '?' method!",
                    methodName
            );
        }
        return methods.get(0);
    }

    private void validateMethodBody(final ExecutableElement getResponseDataMethod,
                                    final Set<Map.Entry<RestModelField, ModelClass>> entrySet) {
        final Trees trees = Trees.instance(getProcessingEnvironment());
        final MethodTree methodTree = trees.getTree(getResponseDataMethod);
        final List<String> expectedBodyCandidates = generateExpectedBodyCandidates(entrySet);
        final String actualBody = methodTree.getBody().getStatements().stream().map(Objects::toString).collect(joining("")).trim();
        for (final String expectedBodyCandidate : expectedBodyCandidates) {
            if (actualBody.equals(expectedBodyCandidate)) {
                return;
            }
        }
        throw new InterruptProcessingException(
                getResponseDataMethod,
                "Invalid method body: expected one of the following: {?}, but actual is {?}",
                expectedBodyCandidates,
                actualBody
        );
    }

    private List<String> generateExpectedBodyCandidates(final Set<Map.Entry<RestModelField, ModelClass>> entrySet) {
        final String mapParams = entrySet.stream()
                .map(e -> format("\"?\", ?", e.getKey().getModelName(), e.getKey().getFieldName()))
                .collect(joining(","));
        return List.of(
                format("return orderedMapWithoutNulls(?);", mapParams),
                format("return Map.of(?);", mapParams)
        );
    }
}
