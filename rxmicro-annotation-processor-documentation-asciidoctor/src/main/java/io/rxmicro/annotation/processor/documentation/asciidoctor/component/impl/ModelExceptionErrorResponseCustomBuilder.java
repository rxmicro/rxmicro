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
import io.rxmicro.annotation.processor.common.component.ModelFieldBuilder;
import io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.ModelFieldBuilderOptions;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.DocumentedModelFieldBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.DocumentedModelField;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.Response;
import io.rxmicro.annotation.processor.documentation.component.HttpResponseExampleBuilder;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.documentation.ResourceDefinition;
import io.rxmicro.rest.model.HttpModelType;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_STRICT_MODE;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_STRICT_MODE_DEFAULT_VALUE;
import static io.rxmicro.annotation.processor.common.model.ModelFieldType.REST_SERVER_RESPONSE;
import static io.rxmicro.annotation.processor.common.util.Elements.allMethodsFromType;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getProcessingEnvironment;
import static io.rxmicro.common.util.Formats.format;
import static java.util.Map.entry;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.7
 */
@Singleton
public final class ModelExceptionErrorResponseCustomBuilder extends AbstractProcessorComponent {

    @Inject
    private ModelFieldBuilder<RestModelField, RestObjectModelClass> modelFieldBuilder;

    @Inject
    private HttpResponseExampleBuilder httpResponseExampleBuilder;

    @Inject
    private DocumentedModelFieldBuilder documentedModelFieldBuilder;

    void setCustomErrorResponse(final EnvironmentContext environmentContext,
                                final ResourceDefinition resourceDefinition,
                                final ProjectMetaData projectMetaData,
                                final TypeElement exceptionTypeElement,
                                final int status,
                                final Response.Builder responseBuilder) {
        final ModuleElement currentModule = environmentContext.getCurrentModule();
        final ModelFieldBuilderOptions options = new ModelFieldBuilderOptions()
                .setWithFieldsFromParentClasses(false)
                .setAccessViaReflectionMustBeDetected(false);
        final RestObjectModelClass modelClass =
                modelFieldBuilder.build(REST_SERVER_RESPONSE, currentModule, Set.of(exceptionTypeElement), options)
                        .get(exceptionTypeElement);
        validateCustomExceptionType(exceptionTypeElement, modelClass);
        if (resourceDefinition.withExamples()) {
            responseBuilder.setExample(httpResponseExampleBuilder.build(resourceDefinition, status, modelClass));
        }
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

    private void validateCustomExceptionType(final TypeElement exceptionTypeElement,
                                             final RestObjectModelClass modelClass) {
        final List<ExecutableElement> methods = allMethodsFromType(
                exceptionTypeElement, e -> "getResponseData".equals(e.getSimpleName().toString()) && e.getParameters().isEmpty()
        );
        if (methods.isEmpty()) {
            throw new InterruptProcessingException(
                    exceptionTypeElement,
                    "Exception type with custom fields must override 'getResponseData' method!"
            );
        }
        if (getBooleanOption(RX_MICRO_STRICT_MODE, RX_MICRO_STRICT_MODE_DEFAULT_VALUE)) {
            validateGetResponseDataMethodBody(methods.get(0), modelClass);
        }
    }

    private void validateGetResponseDataMethodBody(final ExecutableElement getResponseDataMethod,
                                                   final RestObjectModelClass modelClass) {
        final Trees trees = Trees.instance(getProcessingEnvironment());
        final MethodTree methodTree = trees.getTree(getResponseDataMethod);
        final List<String> expectedBodyCandidates = generateExpectedBodyCandidates(modelClass);
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

    private List<String> generateExpectedBodyCandidates(final RestObjectModelClass modelClass) {
        final String mapParams = modelClass.getParamEntries().stream()
                .map(e -> e.getKey().getModelName())
                .map(n -> format("\"?\", ?", n, n))
                .collect(joining(","));
        return List.of(
                format("return Optional.of(Map.of(?));", mapParams),
                format("return Optional.of(orderedMap(?));", mapParams),
                format("return Optional.of(ExCollections.orderedMap(?));", mapParams)
        );
    }
}
