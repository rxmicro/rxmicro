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

package io.rxmicro.annotation.processor.data.sql.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.data.model.Var;
import io.rxmicro.data.Pageable;
import io.rxmicro.data.RepeatParameter;
import io.rxmicro.data.sql.operation.CustomSelect;

import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.types;
import static io.rxmicro.annotation.processor.common.util.Elements.asEnumElement;
import static io.rxmicro.annotation.processor.common.util.Elements.isNotStandardEnum;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.data.sql.model.TransactionType.SUPPORTED_TRANSACTION_TYPES;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class MethodParamResolver {

    private static final Set<String> IGNORED_BY_TYPE = SUPPORTED_TRANSACTION_TYPES;

    private static final Set<Class<? extends Annotation>> IGNORED_BY_ANNOTATION_PRESENT = Set.of(
            CustomSelect.class
    );

    @Inject
    private SupportedTypesProvider supportedTypesProvider;

    public List<Var> getMethodParams(final List<? extends VariableElement> parameters) {
        final List<Var> vars = new ArrayList<>(parameters.size() + 1);
        final StandardParameterHolder standardParameterHolder = new StandardParameterHolder();
        for (final VariableElement parameter : parameters) {
            if (isParameterShouldBeIgnored(parameter)) {
                continue;
            }
            if (supportedTypesProvider.standardMethodParameters().contains(parameter.asType())) {
                addStandardMethodParameters(vars, standardParameterHolder, parameter);
            } else {
                final int repeatCount = Optional.ofNullable(parameter.getAnnotation(RepeatParameter.class))
                        .map(RepeatParameter::value)
                        .orElse(1);
                for (int i = 0; i < repeatCount; i++) {
                    asEnumElement(parameter.asType()).ifPresentOrElse(
                            e -> {
                                if (isNotStandardEnum(parameter.asType())) {
                                    vars.add(new Var(parameter, format("?.sql()", parameter.getSimpleName())));
                                } else {
                                    vars.add(new Var(parameter, format("?.name()", parameter.getSimpleName())));
                                }
                            },
                            () -> vars.add(new Var(parameter))
                    );
                }
            }
        }
        return vars;
    }

    @SuppressWarnings("RedundantIfStatement")
    private boolean isParameterShouldBeIgnored(final VariableElement parameter) {
        if (IGNORED_BY_TYPE.contains(types().erasure(parameter.asType()).toString())) {
            return true;
        }
        if (IGNORED_BY_ANNOTATION_PRESENT.stream().anyMatch(a -> parameter.getAnnotation(a) != null)) {
            return true;
        }
        return false;
    }

    private void addStandardMethodParameters(final List<Var> bindParams,
                                             final StandardParameterHolder standardParameterHolder,
                                             final VariableElement parameter) {
        if (Pageable.class.getName().equals(parameter.asType().toString())) {
            addPageableParameter(bindParams, standardParameterHolder, parameter);
        } else {
            throw new InternalErrorException(
                    "BindParameterResolver: standard method parameter not supported: ?",
                    parameter.asType()
            );
        }
    }

    private void addPageableParameter(final List<Var> bindParams,
                                      final StandardParameterHolder standardParameterHolder,
                                      final VariableElement parameter) {
        if (standardParameterHolder.page != null) {
            throw new InterruptProcessingException(parameter,
                    "Only one '?' parameter allowed per method", Pageable.class.getName());
        } else {
            standardParameterHolder.page = parameter;
            final String name = parameter.getSimpleName().toString();
            bindParams.add(new Var(parameter, format("?.getLimit()", name)));
            bindParams.add(new Var(parameter, format("?.getOffset()", name)));
        }
    }

    /**
     * @author nedis
     * @link http://rxmicro.io
     * @since 0.1
     */
    private static final class StandardParameterHolder {

        private VariableElement page;
    }
}
