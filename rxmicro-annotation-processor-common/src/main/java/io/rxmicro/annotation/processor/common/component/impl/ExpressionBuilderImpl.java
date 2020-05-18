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

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.ExpressionBuilder;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.Expression;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.common.util.Formats;

import java.util.List;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import static io.rxmicro.annotation.processor.common.util.Elements.findGetters;
import static io.rxmicro.common.util.Formats.format;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class ExpressionBuilderImpl implements ExpressionBuilder {

    @Override
    public String build(final ClassHeader.Builder classHeaderBuilder,
                        final Expression expression,
                        final TypeElement configClass,
                        final String modelVariableName) {
        if ("?".equals(expression.getTemplate())) {
            final VariableElement configField = expression.getConfigFields().get(0);
            return format("?.?()", modelVariableName, getGetterName(configClass, configField));
        } else {
            classHeaderBuilder.addStaticImport(Formats.class, "format");
            return format("format(\"?\", ?)",
                    expression.getTemplate(),
                    buildArguments(expression, configClass, modelVariableName));
        }
    }

    private Object buildArguments(final Expression expression,
                                  final TypeElement configClass,
                                  final String modelVariableName) {
        return expression.getConfigFields().stream()
                .map(v -> format("?.?()", modelVariableName, getGetterName(configClass, v)))
                .collect(joining(", "));
    }

    private String getGetterName(final TypeElement configClass,
                                 final VariableElement var) {
        final List<ExecutableElement> getters = findGetters(configClass, var);
        if (getters.isEmpty()) {
            throw new InterruptProcessingException(configClass,
                    "Missing a getter. Add a getter for '?.?' field",
                    configClass.getQualifiedName(), var.getSimpleName());
        }
        return getters.get(0).getSimpleName().toString();
    }
}
