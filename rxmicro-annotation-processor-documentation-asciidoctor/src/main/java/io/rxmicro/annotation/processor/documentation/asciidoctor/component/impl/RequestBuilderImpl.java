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
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.DocumentedModelFieldBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.RequestBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.DocumentedModelField;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.Request;
import io.rxmicro.annotation.processor.documentation.component.HttpRequestExampleBuilder;
import io.rxmicro.annotation.processor.documentation.component.JsonSchemaBuilder;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.annotation.processor.rest.model.HttpMethodMapping;
import io.rxmicro.annotation.processor.rest.model.ParentUrl;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.server.model.AbstractRestControllerModelClassStructure;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructure;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructureStorage;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethod;
import io.rxmicro.documentation.ResourceDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.CharacteristicsReader.REQUIRED_RESTRICTION;
import static io.rxmicro.common.util.ExCollectors.toOrderedSet;
import static io.rxmicro.json.JsonHelper.toJsonString;
import static io.rxmicro.json.JsonTypes.STRING;
import static io.rxmicro.rest.model.HttpModelType.HEADER;
import static io.rxmicro.rest.model.HttpModelType.PARAMETER;
import static io.rxmicro.rest.model.HttpModelType.PATH;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class RequestBuilderImpl implements RequestBuilder {

    @Inject
    private DocumentedModelFieldBuilder documentedModelFieldBuilder;

    @Inject
    private JsonSchemaBuilder jsonSchemaBuilder;

    @Inject
    private HttpRequestExampleBuilder httpRequestExampleBuilder;

    @Override
    public Request buildRequest(final EnvironmentContext environmentContext,
                                final ProjectMetaData projectMetaData,
                                final ResourceDefinition resourceDefinition,
                                final RestControllerClassStructure classStructure,
                                final HttpMethodMapping httpMethodMapping,
                                final RestControllerMethod method,
                                final RestControllerClassStructureStorage restControllerClassStructureStorage) {
        final Request.Builder builder = new Request.Builder();
        if (resourceDefinition.withExamples()) {
            builder.setExample(
                    httpRequestExampleBuilder.build(
                            projectMetaData, classStructure.getParentUrl(), httpMethodMapping, restControllerClassStructureStorage, method
                    )
            );
        }
        final Optional<RestObjectModelClass> requestModelClass = getRequestModelClass(method, restControllerClassStructureStorage);
        return requestModelClass
                .map(cl -> {
                    setHeadersAndPathVariables(environmentContext, projectMetaData, resourceDefinition, classStructure, cl, builder);
                    setParameters(environmentContext, projectMetaData, resourceDefinition, httpMethodMapping, cl, builder);
                    return builder.build();
                })
                .orElseGet(() -> builder
                        .setHeaders(
                                classStructure.getParentUrl().isHeaderVersionStrategy() ?
                                        List.of(getApiVersionDocumentedModelField(classStructure.getParentUrl())) :
                                        List.of())
                        .build()
                );
    }

    private void setHeadersAndPathVariables(final EnvironmentContext environmentContext,
                                            final ProjectMetaData projectMetaData,
                                            final ResourceDefinition resourceDefinition,
                                            final RestControllerClassStructure classStructure,
                                            final RestObjectModelClass restObjectModelClass,
                                            final Request.Builder builder) {
        final boolean withReadMore = resourceDefinition.withReadMore();
        builder.setHeaders(getHeaders(
                environmentContext, resourceDefinition, projectMetaData, classStructure, restObjectModelClass, withReadMore
        ));
        if (resourceDefinition.withPathVariablesDescriptionTable()) {
            builder.setPathVariables(
                    documentedModelFieldBuilder.buildSimple(
                            environmentContext,
                            resourceDefinition.withStandardDescriptions(),
                            projectMetaData.getProjectDirectory(),
                            restObjectModelClass, PATH, withReadMore
                    )
            );
        }
    }

    private void setParameters(final EnvironmentContext environmentContext,
                               final ProjectMetaData projectMetaData,
                               final ResourceDefinition resourceDefinition,
                               final HttpMethodMapping httpMethodMapping,
                               final RestObjectModelClass restObjectModelClass,
                               final Request.Builder builder) {
        final boolean withReadMore = resourceDefinition.withReadMore();
        if (httpMethodMapping.isHttpBody()) {
            if (resourceDefinition.withBodyParametersDescriptionTable()) {
                builder.setBodyParameters(
                        documentedModelFieldBuilder.buildComplex(
                                environmentContext,
                                resourceDefinition.withStandardDescriptions(),
                                projectMetaData.getProjectDirectory(),
                                restObjectModelClass, PARAMETER, withReadMore
                        )
                );
            }
            if (resourceDefinition.withJsonSchema()) {
                builder.setSchema(
                        toJsonString(jsonSchemaBuilder.getJsonObjectSchema(
                                environmentContext, projectMetaData.getProjectDirectory(), restObjectModelClass), true
                        )
                );
            }
        } else {
            if (resourceDefinition.withQueryParametersDescriptionTable()) {
                builder.setQueryParameters(
                        documentedModelFieldBuilder.buildSimple(
                                environmentContext,
                                resourceDefinition.withStandardDescriptions(),
                                projectMetaData.getProjectDirectory(),
                                restObjectModelClass, PARAMETER, withReadMore
                        )
                );
            }
        }
    }

    private List<DocumentedModelField> getHeaders(final EnvironmentContext environmentContext,
                                                  final ResourceDefinition resourceDefinition,
                                                  final ProjectMetaData projectMetaData,
                                                  final RestControllerClassStructure classStructure,
                                                  final RestObjectModelClass restObjectModelClass,
                                                  final boolean withReadMore) {
        return new ArrayList<>(
                Stream.concat(
                        classStructure.getParentUrl().isHeaderVersionStrategy() ?
                                Stream.of(getApiVersionDocumentedModelField(classStructure.getParentUrl())) :
                                Stream.of(),
                        resourceDefinition.withHeadersDescriptionTable() ?
                                documentedModelFieldBuilder.buildSimple(
                                        environmentContext,
                                        resourceDefinition.withStandardDescriptions(),
                                        projectMetaData.getProjectDirectory(),
                                        restObjectModelClass, HEADER, withReadMore
                                ).stream() :
                                Stream.of()
                ).collect(toOrderedSet())
        );
    }

    private DocumentedModelField getApiVersionDocumentedModelField(final ParentUrl parentUrl) {
        final List<String> restrictions = List.of(
                REQUIRED_RESTRICTION,
                "expected: " + parentUrl.getVersionValue()
        );
        final String description = "Api version required header, value of which is used " +
                "by the RxMicro framework framework to define appropriate request handler.";
        return new DocumentedModelField(parentUrl.getVersionHeaderName(), STRING, restrictions, description, List.of());
    }

    private Optional<RestObjectModelClass> getRequestModelClass(final RestControllerMethod method,
                                                                final RestControllerClassStructureStorage storage) {
        return method.getFromHttpDataType()
                .flatMap(t -> storage.getModelReaderClassStructure(t.asType().toString()))
                .map(AbstractRestControllerModelClassStructure::getModelClass);
    }
}
