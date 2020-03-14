/*
 * Copyright (c) 2020. http://rxmicro.io
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
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.CustomErrorResponsesBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.DocumentedModelField;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.Response;
import io.rxmicro.annotation.processor.documentation.component.DescriptionReader;
import io.rxmicro.annotation.processor.documentation.component.HttpResponseExampleBuilder;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.annotation.processor.documentation.model.ReadMoreModel;
import io.rxmicro.annotation.processor.documentation.model.StandardHttpError;
import io.rxmicro.annotation.processor.documentation.model.StandardHttpErrorStorage;
import io.rxmicro.documentation.Description;
import io.rxmicro.documentation.Example;
import io.rxmicro.documentation.ModelExceptionErrorResponse;
import io.rxmicro.documentation.ResourceDefinition;
import io.rxmicro.documentation.SimpleErrorResponse;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static io.rxmicro.annotation.processor.common.util.Annotations.getRequiredAnnotationClassParameter;
import static io.rxmicro.annotation.processor.common.util.Elements.allConstructors;
import static io.rxmicro.annotation.processor.common.util.Elements.allFields;
import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.DocumentedModelFieldBuilder.buildApiVersionHeaderDocumentedModelField;
import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.RestrictionReader.REQUIRED_RESTRICTION;
import static java.util.Map.entry;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class CustomErrorResponsesBuilderImpl implements CustomErrorResponsesBuilder {

    @Inject
    private HttpResponseExampleBuilder httpResponseExampleBuilder;

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
                        () -> standardHttpErrorStorage.get(status)
                                .ifPresent(e -> responseBuilder.setDescription(e.getDescription()))
                );
        if (resourceDefinition.withExamples()) {
            setResponseExample(
                    resourceDefinition,
                    Optional.of(simpleErrorResponse.exampleErrorMessage())
                            .filter(v -> !v.isEmpty())
                            .orElseGet(() ->
                                    standardHttpErrorStorage.get(status).map(StandardHttpError::getExampleErrorMessage)
                                            .orElse("")),
                    status,
                    responseBuilder
            );
        }
        if (resourceDefinition.withHeadersDescriptionTable() &&
                resourceDefinition.withRequestIdResponseHeader()) {
            responseBuilder.setHeaders(List.of(buildApiVersionHeaderDocumentedModelField(true)));
        }
        if (resourceDefinition.withBodyParametersDescriptionTable()) {
            final String messageDescription = Optional.of(simpleErrorResponse.exampleErrorMessage())
                    .filter(v -> !v.isEmpty())
                    .orElseGet(() ->
                            standardHttpErrorStorage.get(status).map(StandardHttpError::getMessageDescription)
                                    .orElse(""));
            if (!messageDescription.isEmpty()) {
                final boolean showReadMoreLinks = standardHttpErrorStorage.get(status)
                        .map(StandardHttpError::isWithShowErrorCauseReadMoreLink)
                        .orElse(false);
                setBodyParameter(messageDescription, responseBuilder, showReadMoreLinks ? showErrorCauseReadMoreLinks : List.of());
            }
        }
        return responseBuilder.build();
    }

    @Override
    public Response buildResponse(final Element owner,
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
                        () -> standardHttpErrorStorage.get(status)
                                .ifPresent(e -> responseBuilder.setDescription(e.getDescription()))
                );
        final List<ExecutableElement> constructors = allConstructors(exceptionTypeElement);
        if (constructors.size() != 1) {
            throw new InterruptProcessingException(owner,
                    "'?' model exception class must declare only one constructor",
                    exceptionTypeElement.asType().toString());
        }
        final String exampleErrorMessage;
        final String messageDescription;
        final Optional<VariableElement> messageParameterOptional = getMessageParameter(constructors.get(0));
        if (messageParameterOptional.isPresent()) {
            exampleErrorMessage = Optional.ofNullable(messageParameterOptional.get().getAnnotation(Example.class))
                    .map(Example::value)
                    .orElseGet(() -> standardHttpErrorStorage.get(status)
                            .map(StandardHttpError::getExampleErrorMessage)
                            .orElse(""));
            messageDescription = Optional.ofNullable(messageParameterOptional.get().getAnnotation(Description.class))
                    .map(Description::value)
                    .orElseGet(() -> standardHttpErrorStorage.get(status)
                            .map(StandardHttpError::getMessageDescription)
                            .orElse(""));
        } else {
            exampleErrorMessage = "";
            messageDescription = "";
        }
        if (resourceDefinition.withExamples()) {
            setResponseExample(resourceDefinition, exampleErrorMessage, status, responseBuilder);
        }
        if (resourceDefinition.withHeadersDescriptionTable() &&
                resourceDefinition.withRequestIdResponseHeader()) {
            responseBuilder.setHeaders(List.of(buildApiVersionHeaderDocumentedModelField(true)));
        }
        if (resourceDefinition.withBodyParametersDescriptionTable() &&
                !exampleErrorMessage.isEmpty()) {
            final boolean showReadMoreLinks = standardHttpErrorStorage.get(status)
                    .map(StandardHttpError::isWithShowErrorCauseReadMoreLink)
                    .orElse(false);
            setBodyParameter(messageDescription, responseBuilder, showReadMoreLinks ? showErrorCauseReadMoreLinks : List.of());
        }
        return responseBuilder.build();
    }

    private Optional<VariableElement> getMessageParameter(final ExecutableElement executableElement) {
        final List<? extends VariableElement> parameters = executableElement.getParameters();
        if (parameters.size() == 1 &&
                String.class.getName().equals(parameters.get(0).asType().toString())) {
            return Optional.of(parameters.get(0));
        } else if (parameters.size() == 2 &&
                String.class.getName().equals(parameters.get(0).asType().toString()) &&
                executableElement.isVarArgs()) {
            return Optional.of(parameters.get(0));
        }
        return Optional.empty();
    }

    private int extractStatusCode(final Element owner,
                                  final TypeElement exceptionTypeElement) {
        final Predicate<VariableElement> variableElementPredicate = el ->
                el.getModifiers().containsAll(Set.of(Modifier.STATIC, Modifier.FINAL)) &&
                        "STATUS_CODE".equals(el.getSimpleName().toString()) &&
                        el.asType().getKind() == TypeKind.INT;

        return (int) allFields(exceptionTypeElement, variableElementPredicate).stream()
                .findFirst()
                .orElseThrow(() -> {
                    throw new InterruptProcessingException(owner,
                            "Required static final int field: '?.STATUS_CODE' not defined",
                            exceptionTypeElement.asType().toString());
                }).getConstantValue();
    }

    private void setResponseExample(final ResourceDefinition resourceDefinition,
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

    private void setBodyParameter(final String messageDescription,
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
