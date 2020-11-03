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
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.SimpleErrorResponseBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.Response;
import io.rxmicro.annotation.processor.documentation.component.DescriptionReader;
import io.rxmicro.annotation.processor.documentation.component.HttpResponseExampleBuilder;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.annotation.processor.documentation.model.ReadMoreModel;
import io.rxmicro.annotation.processor.documentation.model.StandardHttpError;
import io.rxmicro.annotation.processor.documentation.model.StandardHttpErrorStorage;
import io.rxmicro.documentation.ResourceDefinition;
import io.rxmicro.documentation.SimpleErrorResponse;

import java.util.List;
import java.util.Optional;
import javax.lang.model.element.Element;

import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.DocumentedModelFieldBuilder.buildApiVersionHeaderDocumentedModelField;

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
        final int status = simpleErrorResponse.status();
        final Response.Builder responseBuilder = new Response.Builder()
                .setCode(status);
        descriptionReader.readDescription(owner, projectMetaData.getProjectDirectory(), simpleErrorResponse)
                .ifPresentOrElse(
                        responseBuilder::setDescription,
                        () -> standardHttpErrorStorage.get(status).ifPresent(e -> responseBuilder.setDescription(e.getDescription()))
                );
        if (resourceDefinition.withExamples()) {
            setResponseExample(
                    resourceDefinition,
                    Optional.of(simpleErrorResponse.exampleErrorMessage())
                            .filter(v -> !v.isEmpty())
                            .orElseGet(() ->
                                    standardHttpErrorStorage.get(status).map(StandardHttpError::getExampleErrorMessage).orElse("")),
                    status,
                    responseBuilder
            );
        }
        if (resourceDefinition.withHeadersDescriptionTable() && resourceDefinition.withRequestIdResponseHeader()) {
            responseBuilder.setHeaders(List.of(buildApiVersionHeaderDocumentedModelField(true)));
        }
        if (resourceDefinition.withBodyParametersDescriptionTable()) {
            final String messageDescription = Optional.of(simpleErrorResponse.exampleErrorMessage())
                    .filter(v -> !v.isEmpty())
                    .orElseGet(() ->
                            standardHttpErrorStorage.get(status).map(StandardHttpError::getMessageDescription).orElse(""));
            if (!messageDescription.isEmpty()) {
                final boolean showReadMoreLinks = standardHttpErrorStorage.get(status)
                        .map(StandardHttpError::isWithShowErrorCauseReadMoreLink)
                        .orElse(false);
                setBodyParameter(messageDescription, responseBuilder, showReadMoreLinks ? showErrorCauseReadMoreLinks : List.of());
            }
        }
        return responseBuilder.build();
    }
}
