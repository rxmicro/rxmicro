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

package io.rxmicro.annotation.processor.data.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.data.component.DataMethodParamsResolver;
import io.rxmicro.annotation.processor.data.model.DataMethodParams;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.data.Pageable;
import io.rxmicro.data.RepeatParameter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import static io.rxmicro.annotation.processor.common.util.Elements.asEnumElement;
import static io.rxmicro.annotation.processor.common.util.Elements.isNotStandardEnum;
import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.7
 */
@Singleton
public final class DataMethodParamsResolverImpl implements DataMethodParamsResolver {

    @Override
    public DataMethodParams resolve(final ExecutableElement method,
                                    final Map<String, Predicate<VariableElement>> groupRules,
                                    final boolean allowShareVariableForGroups) {
        final Map<String, List<Variable>> params = new LinkedHashMap<>();
        final List<Variable> otherParams = new ArrayList<>();
        for (final VariableElement parameter : method.getParameters()) {
            final Set<String> rules = new HashSet<>();
            for (final Map.Entry<String, Predicate<VariableElement>> entry : groupRules.entrySet()) {
                if (entry.getValue().test(parameter)) {
                    final String rule = entry.getKey();
                    final List<Variable> variables = params.computeIfAbsent(rule, v -> new ArrayList<>());
                    addVariables(variables, parameter);
                    rules.add(rule);
                }
            }
            if (rules.size() > 1) {
                if (!allowShareVariableForGroups) {
                    throw new InterruptProcessingException(parameter, "Detected shared parameter! Contact with the RxMicro support team!");
                }
            } else if (rules.isEmpty()) {
                addVariables(otherParams, parameter);
            }
        }
        return new DataMethodParams(params, otherParams);
    }

    private void addVariables(final List<Variable> variables,
                              final VariableElement parameter) {
        final int repeatCount = Optional.ofNullable(parameter.getAnnotation(RepeatParameter.class))
                .map(RepeatParameter::value)
                .orElse(1);
        for (int i = 0; i < repeatCount; i++) {
            addVariable(variables, parameter, repeatCount);
        }
    }

    private void addVariable(final List<Variable> variables,
                             final VariableElement parameter,
                             final int repeatCount) {
        final Variable.Builder builder = new Variable.Builder()
                .setRepeatCount(repeatCount)
                .setVariableElement(parameter);
        if (asEnumElement(parameter.asType()).isPresent()) {
            if (isNotStandardEnum(parameter.asType())) {
                variables.add(builder
                        .setGetter(format("?.sql()", parameter.getSimpleName()))
                        .build());
            } else {
                variables.add(builder.build());
            }
        } else if (Pageable.class.getName().equals(parameter.asType().toString())) {
            final String name = parameter.getSimpleName().toString();
            variables.add(builder
                    .setName(name)
                    .setGetter(format("?.getLimit()", name))
                    .build()
            );
            variables.add(builder
                    .setName(name)
                    .setGetter(format("?.getOffset()", name))
                    .build()
            );
        } else {
            variables.add(builder.build());
        }
    }
}
