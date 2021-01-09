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

import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.documentation.model.AnnotationValueProvider;

import java.util.List;
import javax.lang.model.element.Element;

import static io.rxmicro.annotation.processor.common.util.validators.VariableDefinitionValidators.validateThatVariablesContainEvenItemCount;
import static io.rxmicro.annotation.processor.common.util.validators.VariableDefinitionValidators.validateVariableNameFormat;

/**
 * @author nedis
 * @since 0.9
 */
public class BaseDocumentationReader {

    public final String resolveString(final Element owner,
                                      final AnnotationValueProvider annotationValueProvider,
                                      final boolean expectOtherVariableProcessor) {
        validateThatVariablesContainEvenItemCount(
                owner, annotationValueProvider.getAnnotationClass(), "variables", annotationValueProvider.getVariables()
        );
        final List<String> variables = List.of(annotationValueProvider.getVariables());
        String result = annotationValueProvider.getValue();
        for (int i = 0; i < variables.size(); i += 2) {
            final String variableName = variables.get(i);
            validateVariableNameFormat(owner, annotationValueProvider.getAnnotationClass(), "variables", variableName);
            validateThatRedundantVariableNotDefined(owner, annotationValueProvider, variableName);
            final String variableValue = variables.get(i + 1);
            result = result.replace(variableName, variableValue);
        }
        if (!expectOtherVariableProcessor) {
            throwErrorIfUnresolvedVariablesExist(owner, annotationValueProvider, result);
        }
        return result;
    }

    private void validateThatRedundantVariableNotDefined(final Element element,
                                                         final AnnotationValueProvider annotationValueProvider,
                                                         final String variableName) {
        boolean found = false;
        for (final String value : annotationValueProvider.getAllValues()) {
            if (value.contains(variableName)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new InterruptProcessingException(
                    element,
                    "'@?.variables()' parameter contains redundant variable: '?'! Remove this variable definition!",
                    annotationValueProvider.getAnnotationClass().getSimpleName(),
                    variableName
            );
        }
    }

    private void throwErrorIfUnresolvedVariablesExist(final Element element,
                                                      final AnnotationValueProvider annotationValueProvider,
                                                      final String result) {
        final int startIndex = result.indexOf("${");
        if (startIndex != -1) {
            final int endIndex = result.indexOf('}', startIndex + 1);
            throw new InterruptProcessingException(
                    element,
                    "'@?.?()' contains undefined variable: '?'! Add variable definition using '@?.variables()' parameter!",
                    annotationValueProvider.getAnnotationClass().getSimpleName(),
                    annotationValueProvider.getAnnotationValueParameterName(),
                    result.substring(startIndex, endIndex == -1 ? result.length() : endIndex + 1),
                    annotationValueProvider.getAnnotationClass().getSimpleName()
            );
        }
    }
}
