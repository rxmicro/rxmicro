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
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.Response;
import io.rxmicro.annotation.processor.documentation.model.ReadMoreModel;
import io.rxmicro.annotation.processor.documentation.model.StandardHttpError;
import io.rxmicro.annotation.processor.documentation.model.StandardHttpErrorStorage;
import io.rxmicro.annotation.processor.documentation.model.provider.DescriptionAnnotationValueProvider;
import io.rxmicro.annotation.processor.documentation.model.provider.ExampleAnnotationValueProvider;
import io.rxmicro.documentation.Description;
import io.rxmicro.documentation.Example;
import io.rxmicro.documentation.ResourceDefinition;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import static io.rxmicro.annotation.processor.common.util.Elements.allConstructors;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.7
 */
@Singleton
public final class ModelExceptionErrorResponseStandardBuilder extends AbstractErrorResponseBuilder {

    @Inject
    private StandardHttpErrorStorage standardHttpErrorStorage;

    void setStandardErrorResponse(final Element owner,
                                  final ResourceDefinition resourceDefinition,
                                  final List<ReadMoreModel> showErrorCauseReadMoreLinks,
                                  final TypeElement exceptionTypeElement,
                                  final int status,
                                  final Response.Builder responseBuilder) {
        final Map.Entry<String, String> entry = getExampleErrorMessageWithDescription(owner, exceptionTypeElement, status);
        final String exampleErrorMessage = entry.getKey();
        if (resourceDefinition.withExamples()) {
            setResponseExample(resourceDefinition, exampleErrorMessage, status, responseBuilder);
        }
        if (resourceDefinition.withBodyParametersDescriptionTable() && !exampleErrorMessage.isEmpty()) {
            final boolean showReadMoreLinks = standardHttpErrorStorage.get(status)
                    .map(StandardHttpError::isWithShowErrorCauseReadMoreLink)
                    .orElse(false);
            final String messageDescription = entry.getValue();
            setBodyParameter(messageDescription, responseBuilder, showReadMoreLinks ? showErrorCauseReadMoreLinks : List.of());
        }
    }

    private Map.Entry<String, String> getExampleErrorMessageWithDescription(final Element owner,
                                                                            final TypeElement exceptionTypeElement,
                                                                            final int status) {
        final List<ExecutableElement> constructors = allConstructors(exceptionTypeElement);
        if (constructors.size() != 1) {
            throw new InterruptProcessingException(
                    owner, "'?' model exception class must declare only one constructor", exceptionTypeElement.asType().toString()
            );
        }
        final Optional<VariableElement> messageParameterOptional = getMessageParameter(constructors.get(0));
        if (messageParameterOptional.isPresent()) {
            final String exampleErrorMessage = Optional.ofNullable(messageParameterOptional.get().getAnnotation(Example.class))
                    .map(a -> resolveString(owner, new ExampleAnnotationValueProvider(a), false))
                    .orElseGet(() -> standardHttpErrorStorage.get(status)
                            .map(StandardHttpError::getExampleErrorMessage)
                            .orElse(""));
            final String messageDescription = Optional.ofNullable(messageParameterOptional.get().getAnnotation(Description.class))
                    .map(a -> resolveString(owner, new DescriptionAnnotationValueProvider(a), false))
                    .orElseGet(() -> standardHttpErrorStorage.get(status)
                            .map(StandardHttpError::getMessageDescription)
                            .orElse(""));
            return entry(exampleErrorMessage, messageDescription);
        } else {
            return entry("", "");
        }
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
}
