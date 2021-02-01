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
import io.rxmicro.annotation.processor.common.component.impl.BaseProcessorComponent;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.DocumentedModelFieldBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.DocumentedModelField;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.Response;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.documentation.ResourceDefinition;
import io.rxmicro.rest.model.HttpModelType;

import java.util.List;

import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.DocumentedModelFieldBuilder.buildRequestIdHeaderDocumentedModelField;
import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static java.util.Map.entry;

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
                         final RestObjectModelClass modelClass,
                         final Response.Builder responseBuilder) {
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
}
