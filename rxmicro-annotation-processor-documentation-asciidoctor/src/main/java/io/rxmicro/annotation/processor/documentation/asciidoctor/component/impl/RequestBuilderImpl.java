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

import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.RestrictionReader.REQUIRED_RESTRICTION;
import static io.rxmicro.common.util.ExCollectors.toOrderedSet;
import static io.rxmicro.json.JsonHelper.toJsonString;
import static io.rxmicro.json.JsonTypes.STRING;
import static io.rxmicro.rest.model.HttpModelType.HEADER;
import static io.rxmicro.rest.model.HttpModelType.PARAMETER;
import static io.rxmicro.rest.model.HttpModelType.PATH;

/**
 * @author nedis
 * @link https://rxmicro.io
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
        final String example = resourceDefinition.withExamples() ?
                httpRequestExampleBuilder.build(
                        projectMetaData,
                        classStructure.getParentUrl(),
                        httpMethodMapping,
                        restControllerClassStructureStorage,
                        method
                ) :
                null;
        final Optional<RestObjectModelClass> requestModelClass = getRequestModelClass(method, restControllerClassStructureStorage);
        return requestModelClass
                .map(cl -> createRequest(
                        environmentContext,
                        projectMetaData,
                        resourceDefinition,
                        classStructure,
                        httpMethodMapping,
                        example,
                        cl))
                .orElseGet(() -> new Request(
                        example,
                        classStructure.getParentUrl().isHeaderVersionStrategy() ?
                                List.of(getApiVersionDocumentedModelField(classStructure.getParentUrl())) :
                                List.of()
                ));
    }

    private Request createRequest(final EnvironmentContext environmentContext,
                                  final ProjectMetaData projectMetaData,
                                  final ResourceDefinition resourceDefinition,
                                  final RestControllerClassStructure classStructure,
                                  final HttpMethodMapping httpMethodMapping,
                                  final String example,
                                  final RestObjectModelClass restObjectModelClass) {
        final boolean withReadMore = resourceDefinition.withReadMore();
        final List<DocumentedModelField> pathVariables = resourceDefinition.withPathVariablesDescriptionTable() ?
                documentedModelFieldBuilder.buildSimple(
                        environmentContext,
                        resourceDefinition.withStandardDescriptions(),
                        projectMetaData.getProjectDirectory(),
                        restObjectModelClass, PATH, withReadMore
                ) :
                List.of();
        final List<DocumentedModelField> headers = getHeaders(
                environmentContext, resourceDefinition, projectMetaData, classStructure, restObjectModelClass, withReadMore
        );
        final String schema = resourceDefinition.withJsonSchema() && httpMethodMapping.isHttpBody() ?
                toJsonString(jsonSchemaBuilder.getJsonObjectSchema(
                        environmentContext, projectMetaData.getProjectDirectory(), restObjectModelClass), true
                ) :
                null;
        if (!httpMethodMapping.isHttpBody()) {
            return new Request(
                    example,
                    pathVariables,
                    headers,
                    resourceDefinition.withQueryParametersDescriptionTable() ?
                            documentedModelFieldBuilder.buildSimple(
                                    environmentContext,
                                    resourceDefinition.withStandardDescriptions(),
                                    projectMetaData.getProjectDirectory(),
                                    restObjectModelClass, PARAMETER, withReadMore
                            ) :
                            List.of(),
                    List.of(),
                    null);
        } else {
            return new Request(
                    example,
                    pathVariables,
                    headers,
                    List.of(),
                    resourceDefinition.withBodyParametersDescriptionTable() ?
                            documentedModelFieldBuilder.buildComplex(
                                    environmentContext,
                                    resourceDefinition.withStandardDescriptions(),
                                    projectMetaData.getProjectDirectory(),
                                    restObjectModelClass, PARAMETER, withReadMore
                            ) :
                            List.of(),
                    schema);
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
