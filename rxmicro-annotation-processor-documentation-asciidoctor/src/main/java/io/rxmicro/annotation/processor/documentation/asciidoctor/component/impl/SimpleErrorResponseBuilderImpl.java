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
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.SimpleErrorResponseBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.DocumentedModelField;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.Response;
import io.rxmicro.annotation.processor.documentation.component.DescriptionReader;
import io.rxmicro.annotation.processor.documentation.model.AnnotationValueProvider;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.annotation.processor.documentation.model.ReadMoreModel;
import io.rxmicro.annotation.processor.documentation.model.StandardHttpErrorStorage;
import io.rxmicro.annotation.processor.documentation.model.provider.SimpleErrorResponseHeaderNamesAnnotationValueProvider;
import io.rxmicro.annotation.processor.documentation.model.provider.SimpleErrorResponseHeaderValueExamplesAnnotationValueProvider;
import io.rxmicro.annotation.processor.documentation.model.provider.SimpleErrorResponseParamNamesAnnotationValueProvider;
import io.rxmicro.annotation.processor.documentation.model.provider.SimpleErrorResponseParamValueExamplesAnnotationValueProvider;
import io.rxmicro.documentation.ResourceDefinition;
import io.rxmicro.documentation.SimpleErrorResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.Element;

import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.CharacteristicsReader.OPTIONAL_RESTRICTION;
import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.CharacteristicsReader.REQUIRED_RESTRICTION;
import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.DocumentedModelFieldBuilder.buildRequestIdHeaderDocumentedModelField;
import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedMap;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static io.rxmicro.json.JsonTypes.BOOLEAN;
import static io.rxmicro.json.JsonTypes.NUMBER;
import static io.rxmicro.json.JsonTypes.STRING;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.7
 */
@Singleton
public final class SimpleErrorResponseBuilderImpl extends AbstractErrorResponseBuilder implements SimpleErrorResponseBuilder {

    @Inject
    private DescriptionReader descriptionReader;

    @Inject
    private StandardHttpErrorStorage standardHttpErrorStorage;

    @Override
    public Response buildResponse(final Element owner,
                                  final ProjectMetaData projectMetaData,
                                  final ResourceDefinition resourceDefinition,
                                  final SimpleErrorResponse simpleErrorResponse,
                                  final List<ReadMoreModel> showErrorCauseReadMoreLinks) {
        validate(owner, simpleErrorResponse);
        final int status = simpleErrorResponse.status();
        final Response.Builder responseBuilder = new Response.Builder()
                .setCode(status);
        descriptionReader.readDescription(owner, projectMetaData.getProjectDirectory(), simpleErrorResponse)
                .ifPresentOrElse(
                        responseBuilder::setDescription,
                        () -> standardHttpErrorStorage.get(status).ifPresent(e -> responseBuilder.setDescription(e.getDescription()))
                );
        final Map<String, String> errorHeaders = getErrorHeaders(owner, simpleErrorResponse);
        final Map<String, String> errorParams = getErrorParams(owner, simpleErrorResponse);
        if (resourceDefinition.withExamples()) {
            responseBuilder.setExample(
                    httpResponseExampleBuilder.buildErrorExample(resourceDefinition, status, errorHeaders, errorParams)
            );
        }
        if (resourceDefinition.withHeadersDescriptionTable()) {
            setHeadersIfFound(responseBuilder, simpleErrorResponse, errorHeaders, resourceDefinition.withRequestIdResponseHeader());
        }
        if (resourceDefinition.withBodyParametersDescriptionTable()) {
            setBodyParametersIfFound(responseBuilder, simpleErrorResponse, errorParams);
        }
        return responseBuilder.build();
    }

    private void validate(final Element owner,
                          final SimpleErrorResponse simpleErrorResponse) {
        if (simpleErrorResponse.paramNames().length != simpleErrorResponse.paramValueExamples().length) {
            throw new InterruptProcessingException(
                    owner,
                    "Invalid parameters for '@?' annotation: paramNames().length must be equal to paramValueExamples().length!",
                    SimpleErrorResponse.class.getSimpleName()
            );
        }
        if (simpleErrorResponse.headerNames().length != simpleErrorResponse.headerValueExamples().length) {
            throw new InterruptProcessingException(
                    owner,
                    "Invalid parameters for '@?' annotation: headerNames().length must be equal to headerValueExamples().length!",
                    SimpleErrorResponse.class.getSimpleName()
            );
        }
        // Value can be blank, so it is necessary to verify names only:
        Map.of("paramNames", simpleErrorResponse.paramNames(), "headerNames", simpleErrorResponse.headerNames())
                .forEach((name, array) -> {
                    for (final String value : array) {
                        if (value.isBlank()) {
                            throw new InterruptProcessingException(
                                    owner,
                                    "The blank value is not allowed for '@?.?' parameter! Provide correct value!",
                                    SimpleErrorResponse.class.getSimpleName(), name
                            );
                        }
                    }
                });
    }

    private Map<String, String> getErrorHeaders(final Element owner,
                                                final SimpleErrorResponse simpleErrorResponse) {
        return getValueMap(
                owner,
                simpleErrorResponse.headerNames().length,
                new SimpleErrorResponseHeaderNamesAnnotationValueProvider(simpleErrorResponse),
                new SimpleErrorResponseHeaderValueExamplesAnnotationValueProvider(simpleErrorResponse)
        );
    }

    private Map<String, String> getErrorParams(final Element owner,
                                               final SimpleErrorResponse simpleErrorResponse) {
        return getValueMap(
                owner,
                simpleErrorResponse.paramNames().length,
                new SimpleErrorResponseParamNamesAnnotationValueProvider(simpleErrorResponse),
                new SimpleErrorResponseParamValueExamplesAnnotationValueProvider(simpleErrorResponse)
        );
    }

    private Map<String, String> getValueMap(final Element owner,
                                            final int count,
                                            final AnnotationValueProvider namesProvider,
                                            final AnnotationValueProvider valueExamplesProvider) {
        if (count > 0) {
            final Map<String, String> map = new LinkedHashMap<>();
            for (int i = 0; i < count; i++) {
                map.put(
                        resolveString(owner, namesProvider, false),
                        resolveString(owner, valueExamplesProvider, false)
                );
            }
            return unmodifiableOrderedMap(map);
        } else {
            return Map.of();
        }
    }

    private void setHeadersIfFound(final Response.Builder responseBuilder,
                                   final SimpleErrorResponse annotation,
                                   final Map<String, String> errorHeaders,
                                   final boolean withRequestIdHeader) {
        final List<DocumentedModelField> documentedModelFields = new ArrayList<>();
        if (withRequestIdHeader && errorHeaders.keySet().stream().noneMatch(h -> h.equalsIgnoreCase(REQUEST_ID))) {
            documentedModelFields.add(buildRequestIdHeaderDocumentedModelField(true));
        }
        int index = 0;
        for (final Map.Entry<String, String> entry : errorHeaders.entrySet()) {
            final boolean required = index >= annotation.headersRequired().length || annotation.headersRequired()[index];
            final boolean withDesc = index < annotation.headerDescriptions().length && !annotation.headerDescriptions()[index].isBlank();
            documentedModelFields.add(new DocumentedModelField(
                    entry.getKey(),
                    resolveValueType(entry.getValue()),
                    List.of(required ? REQUIRED_RESTRICTION : OPTIONAL_RESTRICTION),
                    withDesc ? annotation.headerDescriptions()[index] : format("'?' header", entry.getKey()),
                    List.of()
            ));
            index++;
        }
        if (!documentedModelFields.isEmpty()) {
            responseBuilder.setHeaders(documentedModelFields);
        }
    }

    private void setBodyParametersIfFound(final Response.Builder responseBuilder,
                                          final SimpleErrorResponse annotation,
                                          final Map<String, String> errorParams) {
        if (!errorParams.isEmpty()) {
            final List<DocumentedModelField> documentedModelFields = new ArrayList<>(errorParams.size());
            int index = 0;
            for (final Map.Entry<String, String> entry : errorParams.entrySet()) {
                final boolean required = index >= annotation.paramsRequired().length || annotation.paramsRequired()[index];
                final boolean withDesc = index < annotation.paramDescriptions().length && !annotation.paramDescriptions()[index].isBlank();
                documentedModelFields.add(new DocumentedModelField(
                        entry.getKey(),
                        resolveValueType(entry.getValue()),
                        List.of(required ? REQUIRED_RESTRICTION : OPTIONAL_RESTRICTION),
                        withDesc ? annotation.paramDescriptions()[index] : format("'?' parameter", entry.getKey()),
                        List.of()
                ));
                index++;
            }
            responseBuilder.setParameters(List.of(entry("Body", documentedModelFields)));
        }
    }

    private String resolveValueType(final String value) {
        if ("true".equals(value) || "false".equals(value)) {
            return BOOLEAN;
        } else {
            try {
                new BigDecimal(value);
                return NUMBER;
            } catch (final NumberFormatException ignored) {
                return STRING;
            }
        }
    }
}
