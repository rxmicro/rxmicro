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

package io.rxmicro.annotation.processor.common.component.impl;

import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import javax.lang.model.element.Element;
import java.util.List;

import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractExpressionParser extends AbstractProcessorComponent {

    protected final void extractTemplateAndVariables(final Element owner,
                                                     final StringBuilder templateBuilder,
                                                     final List<String> variables,
                                                     final String expression,
                                                     final boolean safeVariableSyntax) {
        final StringBuilder variableBuilder = new StringBuilder();
        final boolean variableFound = extract(
                owner,
                templateBuilder,
                variables,
                expression,
                variableBuilder,
                safeVariableSyntax
        );
        if (variableFound) {
            throw new InterruptProcessingException(owner,
                    "Invalid expression: '?' -> Missing '}'", expression);
        }
    }

    private boolean extract(final Element owner,
                            final StringBuilder templateBuilder,
                            final List<String> variables,
                            final String expression,
                            final StringBuilder variableBuilder,
                            final boolean safeVariableSyntax) {
        boolean variableFound = false;
        int index = 0;
        while (index < expression.length()) {
            final char ch = expression.charAt(index);
            if (variableFound) {
                validateNotOpenExpression(owner, ch, index, expression);
                if (ch == '}') {
                    variableFound = false;
                    variables.add(safeVariableSyntax ?
                            format("${?}", variableBuilder.toString()) :
                            variableBuilder.toString()
                    );
                    variableBuilder.delete(0, variableBuilder.length());
                } else {
                    variableBuilder.append(ch);
                }
            } else {
                if (ch == '$') {
                    if (index < expression.length() - 1) {
                        final char next = expression.charAt(index + 1);
                        if (next == '{') {
                            templateBuilder.append('?');
                            index++;
                            variableFound = true;
                        } else {
                            validateNotOpenExpression(owner, ch, index, expression);
                        }
                    } else {
                        validateNotOpenExpression(owner, ch, index, expression);
                    }
                } else {
                    validateNotOpenExpression(owner, ch, index, expression);
                    templateBuilder.append(ch);
                }
            }
            index++;
        }
        return variableFound;
    }

    private void validateNotOpenExpression(final Element owner,
                                           final char ch,
                                           final int index,
                                           final String expression) {
        if (ch == '{') {
            throw new InterruptProcessingException(owner,
                    "Invalid expression: '?' -> Missing '$' before '{'", expression);
        } else if (ch == '$') {
            if (index < expression.length() - 1) {
                final char next = expression.charAt(index + 1);
                if (next == '{') {
                    throw new InterruptProcessingException(owner,
                            "Invalid expression: '?' -> Nested expression not allowed", expression);
                } else {
                    throw new InterruptProcessingException(owner,
                            "Invalid expression: '?' -> Missing '{' after '$'", expression);
                }
            } else {
                throw new InterruptProcessingException(owner,
                        "Invalid expression: '?' -> Missing '{' after '$'", expression);
            }
        }
    }
}
