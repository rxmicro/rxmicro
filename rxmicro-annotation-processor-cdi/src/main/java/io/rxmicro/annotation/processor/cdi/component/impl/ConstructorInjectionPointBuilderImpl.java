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

package io.rxmicro.annotation.processor.cdi.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.cdi.component.ConstructorInjectionPointBuilder;
import io.rxmicro.annotation.processor.cdi.model.InjectionPoint;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import static io.rxmicro.annotation.processor.common.util.Elements.allConstructors;
import static io.rxmicro.annotation.processor.common.util.Elements.allFields;
import static io.rxmicro.annotation.processor.common.util.Elements.allMethods;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateAccessibleConstructor;
import static io.rxmicro.cdi.local.Annotations.INJECT_ANNOTATIONS;
import static io.rxmicro.common.util.Formats.format;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class ConstructorInjectionPointBuilderImpl extends AbstractInjectionPointBuilder implements ConstructorInjectionPointBuilder {

    private static final String COMMA_SEPARATED_DELIMITER = ", ";

    @Override
    public boolean isConstructorInjection(final TypeElement beanTypeElement) {
        for (final ExecutableElement constructor : allConstructors(beanTypeElement)) {
            if (INJECT_ANNOTATIONS.stream().anyMatch(a -> constructor.getAnnotation(a) != null)) {
                return true;
            }
            for (final VariableElement parameter : constructor.getParameters()) {
                if (INJECT_ANNOTATIONS.stream().anyMatch(a -> parameter.getAnnotation(a) != null)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<InjectionPoint> build(final TypeElement beanTypeElement) {
        final List<ExecutableElement> constructors = allConstructors(beanTypeElement);
        final List<VariableElement> fields = allFields(beanTypeElement, f ->
                INJECT_ANNOTATIONS.stream().anyMatch(a -> f.getAnnotation(a) != null));
        final List<ExecutableElement> methods = allMethods(beanTypeElement, f ->
                INJECT_ANNOTATIONS.stream().anyMatch(a -> f.getAnnotation(a) != null));
        validate(constructors, fields, methods);
        return constructors.get(0).getParameters().stream()
                .map(p -> build(beanTypeElement, p))
                .collect(toList());
    }

    @Override
    protected InjectionPoint build(final VariableElement field, final InjectionPoint.Builder injectionPointBuilder) {
        return injectionPointBuilder
                .setConstructorInjection(true)
                .build();
    }

    @Override
    protected boolean isRequired(final Element element) {
        if (isInjectionAnnotationPresent(element)) {
            return super.isRequired(element);
        } else {
            // Check constructor annotations instead of parameter ones
            return super.isRequired(element.getEnclosingElement());
        }
    }

    private void validate(final List<ExecutableElement> constructors,
                          final List<VariableElement> fields,
                          final List<ExecutableElement> methods) {
        if (constructors.size() > 1) {
            throw new InterruptProcessingException(
                    constructors.get(constructors.size() - 1),
                    "Class must declare only one constructor. Remove redundant constructor(s)!"
            );
        }
        if (!fields.isEmpty()) {
            throw new InterruptProcessingException(
                    fields.get(0),
                    "Field injection is not supported if class already uses constructor injection. " +
                            "Remove injection annotations from field(s): ?",
                    fields.stream().map(e -> e.getSimpleName().toString()).collect(joining(COMMA_SEPARATED_DELIMITER))
            );
        }
        if (!methods.isEmpty()) {
            throw new InterruptProcessingException(
                    methods.get(0),
                    "Method injection is not supported if class already uses constructor injection. " +
                            "Remove injection annotations from method(s): ?",
                    methods.stream()
                            .map(e -> format("?(?)", e.getSimpleName(), e.getParameters().stream()
                                    .map(p -> p.asType().toString())
                                    .collect(joining(COMMA_SEPARATED_DELIMITER))))
                            .collect(joining(COMMA_SEPARATED_DELIMITER))
            );
        }
        validateConstructor(constructors.get(0));
    }

    private void validateConstructor(final ExecutableElement constructor) {
        if (constructor.getParameters().isEmpty()) {
            throw new InterruptProcessingException(
                    constructor,
                    "Constructor must declare at least one parameter. Add missing parameter(s) or remove '@?' annotation",
                    constructor.getAnnotationMirrors().stream()
                            .filter(a -> INJECT_ANNOTATIONS.stream().anyMatch(cl -> a.getAnnotationType().toString().equals(cl.getName())))
                            .map(a -> a.getAnnotationType().toString())
                            .collect(joining(COMMA_SEPARATED_DELIMITER))
            );
        }
        validateAccessibleConstructor(constructor);
        if (INJECT_ANNOTATIONS.stream().noneMatch(a -> constructor.getAnnotation(a) != null)) {
            for (final VariableElement parameter : constructor.getParameters()) {
                if (INJECT_ANNOTATIONS.stream().noneMatch(a -> parameter.getAnnotation(a) != null)) {
                    throw new InterruptProcessingException(
                            parameter,
                            "Missing inject annotation per constructor parameter: '?'",
                            parameter
                    );
                }
            }
        }
    }
}
