/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.annotation.processor.rest.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.component.PathVariableValidator;
import io.rxmicro.annotation.processor.rest.model.HttpMethodMapping;
import io.rxmicro.rest.PathVariable;
import io.rxmicro.rest.model.UrlSegments;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Elements.allModelFields;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class PathVariableValidatorImpl implements PathVariableValidator {

    @Override
    public void validateThatPathVariablesNotMissingOrRedundant(final Element owner,
                                                               final HttpMethodMapping httpMethodMapping,
                                                               final UrlSegments urlSegments,
                                                               final TypeElement typeElement) {
        final Set<String> pathVariables = getEditablePathVariables(typeElement);
        for (final String variable : new HashSet<>(urlSegments.getVariables())) {
            if (!pathVariables.remove(variable)) {
                throw new InterruptProcessingException(owner,
                        "Redundant path variable declaration(s): ${?} in the '@?(\"?\")', " +
                                "because '?' does not contain field annotated by '@?(\"?\")' annotation",
                        variable,
                        httpMethodMapping.getMethod(),
                        httpMethodMapping.getExactOrTemplateUri(),
                        typeElement.getQualifiedName(),
                        PathVariable.class.getSimpleName(),
                        variable);
            }
        }
        if (!pathVariables.isEmpty()) {
            throw new InterruptProcessingException(owner,
                    "Missing path variable declaration(s): ? in the '@?(\"?\")'.",
                    pathVariables, httpMethodMapping.getMethod(), httpMethodMapping.getExactOrTemplateUri());
        }
    }

    @Override
    public void validateThatPathVariablesNotFound(final Element owner,
                                                  final HttpMethodMapping httpMethodMapping,
                                                  final TypeElement typeElement) {
        final Set<String> pathVariables = getEditablePathVariables(typeElement);
        if (!pathVariables.isEmpty()) {
            throw new InterruptProcessingException(owner,
                    "Missing path variable declaration(s): ? in the '@?(\"?\")'.",
                    pathVariables, httpMethodMapping.getMethod(), httpMethodMapping.getExactOrTemplateUri());
        }
    }

    private Set<String> getEditablePathVariables(final TypeElement typeElement) {
        final Set<String> result = new LinkedHashSet<>();
        for (final VariableElement field : allModelFields(typeElement)) {
            final PathVariable pathVariable = field.getAnnotation(PathVariable.class);
            if (pathVariable != null) {
                result.add(!pathVariable.value().isEmpty() ?
                        pathVariable.value() :
                        field.getSimpleName().toString()
                );
            }
        }
        return result;
    }
}
