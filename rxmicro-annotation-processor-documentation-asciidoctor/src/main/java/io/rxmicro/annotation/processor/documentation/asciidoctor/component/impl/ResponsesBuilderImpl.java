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
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.ModelExceptionErrorResponseBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.ResponsesBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.SimpleErrorResponseBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.DocumentedModelField;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.Response;
import io.rxmicro.annotation.processor.documentation.component.DescriptionReader;
import io.rxmicro.annotation.processor.documentation.component.HttpResponseExampleBuilder;
import io.rxmicro.annotation.processor.documentation.component.JsonSchemaBuilder;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.annotation.processor.documentation.model.ReadMoreModel;
import io.rxmicro.annotation.processor.documentation.model.StandardHttpError;
import io.rxmicro.annotation.processor.documentation.model.StandardHttpErrorStorage;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.server.model.AbstractRestControllerModelClassStructure;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructure;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructureStorage;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethod;
import io.rxmicro.documentation.DocumentationDefinition;
import io.rxmicro.documentation.ModelExceptionErrorResponse;
import io.rxmicro.documentation.ResourceDefinition;
import io.rxmicro.documentation.SimpleErrorResponse;
import io.rxmicro.http.error.InternalHttpErrorException;
import io.rxmicro.http.error.ValidationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.Errors.createInternalErrorSupplier;
import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.CharacteristicsReader.REQUIRED_RESTRICTION;
import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.DocumentedModelFieldBuilder.buildRequestIdHeaderDocumentedModelField;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_VALIDATION_MODULE;
import static io.rxmicro.documentation.DocumentationDefinition.GenerationOutput.RESOURCES_SECTION;
import static io.rxmicro.documentation.IntroductionDefinition.Section.ERROR_MODEL;
import static io.rxmicro.exchange.json.JsonExchangeConstants.MESSAGE;
import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static io.rxmicro.json.JsonHelper.toJsonString;
import static io.rxmicro.json.JsonTypes.STRING;
import static io.rxmicro.rest.model.HttpModelType.HEADER;
import static io.rxmicro.rest.model.HttpModelType.PARAMETER;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class ResponsesBuilderImpl implements ResponsesBuilder {

    @Inject
    private DocumentedModelFieldBuilder documentedModelFieldBuilder;

    @Inject
    private JsonSchemaBuilder jsonSchemaBuilder;

    @Inject
    private HttpResponseExampleBuilder httpResponseExampleBuilder;

    @Inject
    private DescriptionReader descriptionReader;

    @Inject
    private SimpleErrorResponseBuilder simpleErrorResponseBuilder;

    @Inject
    private ModelExceptionErrorResponseBuilder modelExceptionErrorResponseBuilder;

    @Inject
    private StandardHttpErrorStorage standardHttpErrorStorage;

    @Override
    public Response buildSuccessResponse(final ResourceDefinition resourceDefinition,
                                         final ProjectMetaData projectMetaData,
                                         final EnvironmentContext environmentContext,
                                         final RestControllerMethod method,
                                         final RestControllerClassStructureStorage restControllerClassStructureStorage) {
        final int code = method.getSuccessStatusCode();
        final Response.Builder responseBuilder = new Response.Builder()
                .setCode(code);
        if (resourceDefinition.withExamples()) {
            responseBuilder.setExample(
                    httpResponseExampleBuilder.build(resourceDefinition, restControllerClassStructureStorage, method)
            );
        }
        final Optional<RestObjectModelClass> responseModelClass = getResponseModelClass(method, restControllerClassStructureStorage);
        return responseModelClass.map(cl -> {
            if (resourceDefinition.withJsonSchema()) {
                responseBuilder.setSchema(toJsonString(
                        jsonSchemaBuilder.getJsonObjectSchema(
                                environmentContext, projectMetaData.getProjectDirectory(), cl), true
                ));
            }
            descriptionReader.readDescription(cl.getModelTypeElement(), projectMetaData.getProjectDirectory())
                    .ifPresent(responseBuilder::setDescription);
            final boolean withReadMore = resourceDefinition.withReadMore();
            return responseBuilder
                    .setHeaders(resourceDefinition.withHeadersDescriptionTable() ?
                            buildResponseHeaders(environmentContext, resourceDefinition, projectMetaData, cl, withReadMore) :
                            List.of()
                    )
                    .setParameters(resourceDefinition.withBodyParametersDescriptionTable() ?
                            documentedModelFieldBuilder.buildComplex(
                                    environmentContext,
                                    resourceDefinition.withStandardDescriptions(),
                                    projectMetaData.getProjectDirectory(),
                                    cl, PARAMETER, withReadMore
                            ) :
                            List.of()
                    )
                    .build();
        }).orElseGet(responseBuilder::build);
    }

    @Override
    public Set<Response> buildErrorResponses(final EnvironmentContext environmentContext,
                                             final ProjectMetaData projectMetaData,
                                             final DocumentationDefinition documentationDefinition,
                                             final ResourceDefinition resourceDefinition,
                                             final RestControllerClassStructure classStructure,
                                             final RestControllerMethod method) {
        final List<ReadMoreModel> showErrorCauseReadMoreLinks =
                getShowErrorCauseReadMoreLinks(documentationDefinition, resourceDefinition);
        final Set<Response> responses = new TreeSet<>();
        final TypeElement ownerClass = classStructure.getOwnerClass();
        Arrays.stream(method.getMethod().getAnnotationsByType(ModelExceptionErrorResponse.class))
                .forEach(e -> responses.add(modelExceptionErrorResponseBuilder.buildResponse(
                        environmentContext, method.getMethod(), projectMetaData, resourceDefinition, e, showErrorCauseReadMoreLinks
                )));
        Arrays.stream(method.getMethod().getAnnotationsByType(SimpleErrorResponse.class))
                .forEach(e -> responses.add(simpleErrorResponseBuilder.buildResponse(
                        method.getMethod(), projectMetaData, resourceDefinition, e, showErrorCauseReadMoreLinks
                )));
        Arrays.stream(ownerClass.getAnnotationsByType(ModelExceptionErrorResponse.class))
                .forEach(e -> responses.add(modelExceptionErrorResponseBuilder.buildResponse(
                        environmentContext, ownerClass, projectMetaData, resourceDefinition, e, showErrorCauseReadMoreLinks
                )));
        Arrays.stream(ownerClass.getAnnotationsByType(SimpleErrorResponse.class))
                .forEach(e -> responses.add(simpleErrorResponseBuilder.buildResponse(
                        ownerClass, projectMetaData, resourceDefinition, e, showErrorCauseReadMoreLinks
                )));
        if (environmentContext.isRxMicroModuleEnabled(RX_MICRO_VALIDATION_MODULE) &&
                resourceDefinition.withValidationResponse() &&
                method.getFromHttpDataType().isPresent()) {
            responses.add(addValidationErrorResponse(resourceDefinition));
        }
        if (resourceDefinition.withInternalErrorResponse()) {
            responses.add(addInternalErrorResponse(resourceDefinition, showErrorCauseReadMoreLinks));
        }
        return responses;
    }

    private List<DocumentedModelField> buildResponseHeaders(final EnvironmentContext environmentContext,
                                                            final ResourceDefinition resourceDefinition,
                                                            final ProjectMetaData projectMetaData,
                                                            final RestObjectModelClass cl,
                                                            final boolean withReadMore) {
        if (resourceDefinition.withRequestIdResponseHeader()) {
            final List<DocumentedModelField> headers = documentedModelFieldBuilder.buildSimple(
                    environmentContext,
                    resourceDefinition.withStandardDescriptions(),
                    projectMetaData.getProjectDirectory(),
                    cl, HEADER, withReadMore
            );
            // Add Request-Id header if not defined!
            if (headers.stream().noneMatch(f -> f.getName().equalsIgnoreCase(REQUEST_ID))) {
                headers.add(0, buildRequestIdHeaderDocumentedModelField(true));
            }
            return headers;
        } else {
            return documentedModelFieldBuilder.buildSimple(
                    environmentContext,
                    resourceDefinition.withStandardDescriptions(),
                    projectMetaData.getProjectDirectory(),
                    cl, HEADER, withReadMore
            );
        }
    }

    private Optional<RestObjectModelClass> getResponseModelClass(final RestControllerMethod method,
                                                                 final RestControllerClassStructureStorage storage) {
        return method.getToHttpDataType()
                .flatMap(t -> storage.getModelWriterClassStructure(t.asType().toString()))
                .map(AbstractRestControllerModelClassStructure::getModelClass);
    }

    private List<ReadMoreModel> getShowErrorCauseReadMoreLinks(final DocumentationDefinition documentationDefinition,
                                                               final ResourceDefinition resourceDefinition) {
        if (resourceDefinition.withReadMore() &&
                Arrays.stream(documentationDefinition.introduction().sectionOrder()).anyMatch(s -> s == ERROR_MODEL) &&
                Arrays.stream(documentationDefinition.output()).noneMatch(o -> RESOURCES_SECTION == o)) {
            return List.of(new ReadMoreModel(
                    "_(How to activate the displaying of the detailed error message?)_",
                    "internal-error-message-read-more",
                    true
            ));
        } else {
            return List.of();
        }
    }

    private Response addValidationErrorResponse(final ResourceDefinition resourceDefinition) {
        return addErrorResponse(
                resourceDefinition,
                standardHttpErrorStorage.get(ValidationException.STATUS_CODE)
                        .orElseThrow(createInternalErrorSupplier("Standard http error not defined for 400 status code")),
                List.of()
        );
    }

    private Response addInternalErrorResponse(final ResourceDefinition resourceDefinition,
                                              final List<ReadMoreModel> showErrorCauseReadMoreLinks) {
        return addErrorResponse(
                resourceDefinition,
                standardHttpErrorStorage.get(InternalHttpErrorException.STATUS_CODE)
                        .orElseThrow(createInternalErrorSupplier("Standard http error not defined for 500 status code")),
                showErrorCauseReadMoreLinks
        );
    }

    private Response addErrorResponse(final ResourceDefinition resourceDefinition,
                                      final StandardHttpError standardHttpError,
                                      final List<ReadMoreModel> readMoreLinks) {
        final int status = standardHttpError.getStatus();
        final Response.Builder responseBuilder = new Response.Builder()
                .setCode(status)
                .setDescription(standardHttpError.getDescription());
        if (resourceDefinition.withExamples()) {
            responseBuilder.setExample(
                    httpResponseExampleBuilder.buildErrorExample(
                            resourceDefinition, status, standardHttpError.getExampleErrorMessage()
                    )
            );
        }
        if (resourceDefinition.withHeadersDescriptionTable() &&
                resourceDefinition.withRequestIdResponseHeader()) {
            responseBuilder.setHeaders(List.of(buildRequestIdHeaderDocumentedModelField(true)));
        }
        if (resourceDefinition.withBodyParametersDescriptionTable()) {
            responseBuilder.setParameters(List.of(
                    entry("Body", List.of(
                            new DocumentedModelField(
                                    MESSAGE,
                                    STRING,
                                    List.of(REQUIRED_RESTRICTION),
                                    standardHttpError.getMessageDescription(),
                                    readMoreLinks
                            )
                    ))
            ));
        }
        return responseBuilder.build();
    }
}
