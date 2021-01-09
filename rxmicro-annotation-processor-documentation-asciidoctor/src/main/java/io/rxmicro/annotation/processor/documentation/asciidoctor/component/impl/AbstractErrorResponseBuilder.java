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
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.DocumentedModelField;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.Response;
import io.rxmicro.annotation.processor.documentation.component.HttpResponseExampleBuilder;
import io.rxmicro.annotation.processor.documentation.component.impl.BaseDocumentationReader;
import io.rxmicro.annotation.processor.documentation.model.ReadMoreModel;
import io.rxmicro.documentation.ResourceDefinition;

import java.util.List;

import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.CharacteristicsReader.REQUIRED_RESTRICTION;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.7
 */
public abstract class AbstractErrorResponseBuilder extends BaseDocumentationReader {

    @Inject
    private HttpResponseExampleBuilder httpResponseExampleBuilder;

    protected final void setResponseExample(final ResourceDefinition resourceDefinition,
                                            final String exampleErrorMessage,
                                            final int status,
                                            final Response.Builder responseBuilder) {
        if (exampleErrorMessage.isEmpty()) {
            responseBuilder.setExample(
                    httpResponseExampleBuilder.buildErrorExample(resourceDefinition, status)
            );
        } else {
            responseBuilder.setExample(
                    httpResponseExampleBuilder.buildErrorExample(resourceDefinition, status, exampleErrorMessage)
            );
        }
    }

    protected final void setBodyParameter(final String messageDescription,
                                          final Response.Builder responseBuilder,
                                          final List<ReadMoreModel> showErrorCauseReadMoreLinks) {
        final String description = messageDescription.isEmpty() ?
                "The detailed cause of the arisen error." :
                messageDescription;
        responseBuilder.setParameters(List.of(
                entry("Body", List.of(
                        new DocumentedModelField(
                                "message",
                                "string",
                                List.of(REQUIRED_RESTRICTION),
                                description,
                                showErrorCauseReadMoreLinks
                        )
                ))
        ));
    }
}
