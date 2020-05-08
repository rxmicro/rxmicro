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

package io.rxmicro.annotation.processor.common.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.ExpressionParser;
import io.rxmicro.annotation.processor.common.model.Expression;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.rxmicro.annotation.processor.common.util.Elements.allModelFields;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class ExpressionParserImpl extends AbstractExpressionParser
        implements ExpressionParser {

    @Override
    public Expression parse(final Element owner,
                            final TypeElement modelClass,
                            final String expression) {
        final StringBuilder templateBuilder = new StringBuilder();
        final List<String> variables = new ArrayList<>();
        extractTemplateAndVariables(owner, templateBuilder, variables, expression, false);
        final List<VariableElement> allFields = allModelFields(modelClass);
        final List<VariableElement> configFields = variables.stream()
                .map(v -> allFields.stream()
                        .filter(el -> el.getSimpleName().toString().equals(v))
                        .findFirst().orElseThrow(() -> {
                            throw new InterruptProcessingException(owner,
                                    "Invalid expression: '?' -> Field '?' not found at '?' class",
                                    expression,
                                    v,
                                    modelClass.getQualifiedName());
                        }))
                .collect(Collectors.toList());
        return new Expression(templateBuilder.toString(), configFields);
    }
}
