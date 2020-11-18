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
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.ModelExceptionErrorResponseBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.Response;
import io.rxmicro.annotation.processor.documentation.component.DescriptionReader;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.annotation.processor.documentation.model.ReadMoreModel;
import io.rxmicro.annotation.processor.documentation.model.StandardHttpErrorStorage;
import io.rxmicro.documentation.ModelExceptionErrorResponse;
import io.rxmicro.documentation.ResourceDefinition;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;

import static io.rxmicro.annotation.processor.common.util.Annotations.getRequiredAnnotationClassParameter;
import static io.rxmicro.annotation.processor.common.util.Elements.allFields;
import static io.rxmicro.annotation.processor.common.util.Elements.allModelFields;
import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.DocumentedModelFieldBuilder.buildApiVersionHeaderDocumentedModelField;
import static io.rxmicro.documentation.asciidoctor.AsciidoctorDocumentationConstants.STATUS_CODE_STATIC_FIELD_NAME;

/**
 * @author nedis
 * @since 0.7
 */
@Singleton
public final class ModelExceptionErrorResponseBuilderImpl implements ModelExceptionErrorResponseBuilder {

    @Inject
    private DescriptionReader descriptionReader;

    @Inject
    private StandardHttpErrorStorage standardHttpErrorStorage;

    @Inject
    private ModelExceptionErrorResponseStandardBuilder modelExceptionErrorResponseStandardBuilder;

    @Inject
    private ModelExceptionErrorResponseCustomBuilder modelExceptionErrorResponseCustomBuilder;

    @Override
    public Response buildResponse(final EnvironmentContext environmentContext,
                                  final Element owner,
                                  final ProjectMetaData projectMetaData,
                                  final ResourceDefinition resourceDefinition,
                                  final ModelExceptionErrorResponse modelExceptionErrorResponse,
                                  final List<ReadMoreModel> showErrorCauseReadMoreLinks) {
        final TypeElement exceptionTypeElement = getRequiredAnnotationClassParameter(modelExceptionErrorResponse::value);
        final int status = extractStatusCode(owner, exceptionTypeElement);
        final Response.Builder responseBuilder = new Response.Builder()
                .setCode(status);
        descriptionReader.readDescription(exceptionTypeElement, projectMetaData.getProjectDirectory())
                .ifPresentOrElse(
                        responseBuilder::setDescription,
                        () -> standardHttpErrorStorage.get(status).ifPresent(e -> responseBuilder.setDescription(e.getDescription()))
                );
        if (resourceDefinition.withHeadersDescriptionTable() && resourceDefinition.withRequestIdResponseHeader()) {
            responseBuilder.setHeaders(List.of(buildApiVersionHeaderDocumentedModelField(true)));
        }
        final List<VariableElement> customExceptionFields = allModelFields(exceptionTypeElement, false);
        if (customExceptionFields.isEmpty()) {
            modelExceptionErrorResponseStandardBuilder.setStandardErrorResponse(
                    owner, resourceDefinition, showErrorCauseReadMoreLinks, exceptionTypeElement, status, responseBuilder
            );
        } else {
            modelExceptionErrorResponseCustomBuilder.setCustomErrorResponse(
                    environmentContext, resourceDefinition, projectMetaData, exceptionTypeElement, status, responseBuilder
            );
        }
        return responseBuilder.build();
    }

    private int extractStatusCode(final Element owner,
                                  final TypeElement exceptionTypeElement) {
        final Predicate<VariableElement> variableElementPredicate = el ->
                el.getModifiers().containsAll(Set.of(Modifier.STATIC, Modifier.FINAL)) &&
                        STATUS_CODE_STATIC_FIELD_NAME.equals(el.getSimpleName().toString()) &&
                        el.asType().getKind() == TypeKind.INT;

        return (int) allFields(exceptionTypeElement, variableElementPredicate).stream()
                .findFirst()
                .orElseThrow(() -> {
                    throw new InterruptProcessingException(owner,
                            "Required static final int field: '?.STATUS_CODE' not defined",
                            exceptionTypeElement.asType().toString());
                }).getConstantValue();
    }
}
