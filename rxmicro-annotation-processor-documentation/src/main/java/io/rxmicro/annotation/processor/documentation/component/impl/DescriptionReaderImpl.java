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

package io.rxmicro.annotation.processor.documentation.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.PathVariablesResolver;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.documentation.component.DescriptionReader;
import io.rxmicro.annotation.processor.documentation.component.ExternalResourceReader;
import io.rxmicro.annotation.processor.documentation.component.IncludeReferenceSyntaxProvider;
import io.rxmicro.documentation.Description;
import io.rxmicro.documentation.IncludeDescription;
import io.rxmicro.documentation.IncludeMode;
import io.rxmicro.documentation.SimpleErrorResponse;

import javax.lang.model.element.Element;
import java.util.List;
import java.util.Optional;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class DescriptionReaderImpl implements DescriptionReader {

    @Inject
    private PathVariablesResolver pathVariablesResolver;

    @Inject
    private ExternalResourceReader externalResourceReader;

    @Inject
    private IncludeReferenceSyntaxProvider includeReferenceSyntaxProvider;

    @Override
    public Optional<String> readDescription(final Element element,
                                            final String projectDirectory) {
        final Description description = element.getAnnotation(Description.class);
        final IncludeDescription includeDescription = element.getAnnotation(IncludeDescription.class);
        validate(element, description, includeDescription);
        if (description != null) {
            return Optional.of(description.value());
        }
        if (includeDescription != null) {
            return Optional.of(readIncludedDescription(
                    element, projectDirectory, includeDescription.resource(), includeDescription.includeMode())
            );
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> readDescription(final Element element,
                                            final String projectDirectory,
                                            final SimpleErrorResponse simpleErrorResponse) {
        final String description = simpleErrorResponse.description();
        final IncludeDescription includeDescription = simpleErrorResponse.includeDescription();
        validate(element, description, includeDescription);
        if (!description.isEmpty()) {
            return Optional.of(description);
        }
        if (!includeDescription.resource().isEmpty()) {
            return Optional.of(readIncludedDescription(
                    element, projectDirectory, includeDescription.resource(), includeDescription.includeMode())
            );
        }
        return Optional.empty();
    }

    private void validate(final Element element,
                          final Description description,
                          final IncludeDescription includeDescription) {
        if (description != null && includeDescription != null) {
            throw new InterruptProcessingException(element,
                    "Only one annotation from ? list can be applied to this element",
                    List.of(Description.class.getName(), IncludeDescription.class.getName())
            );
        }
    }

    private void validate(final Element element,
                          final String description,
                          final IncludeDescription includeDescription) {
        if (!description.isEmpty() && !includeDescription.resource().isEmpty()) {
            throw new InterruptProcessingException(element,
                    "Both 'description' and 'includeDescription' couldn't be specified for the error response"
            );
        }
    }

    private String readIncludedDescription(final Element element,
                                           final String projectDirectory,
                                           final String resource,
                                           final IncludeMode includeMode) {
        final String resourcePath = pathVariablesResolver.resolvePathVariables(element, projectDirectory, resource);
        if (includeMode == IncludeMode.INCLUDE_REFERENCE) {
            return includeReferenceSyntaxProvider.include(resourcePath);
        } else {
            return externalResourceReader.read(element, resourcePath);
        }
    }
}
