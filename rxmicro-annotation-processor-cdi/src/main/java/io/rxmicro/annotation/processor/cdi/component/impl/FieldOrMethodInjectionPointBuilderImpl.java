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
import io.rxmicro.annotation.processor.cdi.component.FieldOrMethodInjectionPointBuilder;
import io.rxmicro.annotation.processor.cdi.model.InjectionPoint;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import java.util.List;
import java.util.stream.Stream;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import static io.rxmicro.annotation.processor.common.util.Elements.allFields;
import static io.rxmicro.annotation.processor.common.util.Elements.allMethods;
import static io.rxmicro.annotation.processor.common.util.validators.MethodValidators.validateNotAbstractMethod;
import static io.rxmicro.annotation.processor.common.util.validators.MethodValidators.validateNotPrivateMethod;
import static io.rxmicro.annotation.processor.common.util.validators.MethodValidators.validateNotStaticMethod;
import static io.rxmicro.cdi.local.Annotations.INJECT_ANNOTATIONS;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class FieldOrMethodInjectionPointBuilderImpl extends AbstractInjectionPointBuilder
        implements FieldOrMethodInjectionPointBuilder {

    @Override
    public List<InjectionPoint> build(final TypeElement beanTypeElement) {
        final List<VariableElement> fields = allFields(beanTypeElement, f ->
                INJECT_ANNOTATIONS.stream().anyMatch(a -> f.getAnnotation(a) != null));
        final List<ExecutableElement> methods = allMethods(beanTypeElement, f ->
                INJECT_ANNOTATIONS.stream().anyMatch(a -> f.getAnnotation(a) != null) ||
                        f.getParameters().stream().flatMap(p -> p.getAnnotationMirrors().stream())
                                .anyMatch(m -> INJECT_ANNOTATIONS.stream().anyMatch(a ->
                                        a.getName().equals(m.getAnnotationType().toString()))
                                )
        );
        return Stream.concat(
                fields.stream()
                        .map(this::build),
                methods.stream()
                        .peek(this::validateInjectionMethod)
                        .flatMap(m -> m.getParameters().stream())
                        .map(this::build)
        ).collect(toList());
    }

    @Override
    protected InjectionPoint build(final VariableElement field,
                                   final InjectionPoint.Builder injectionPointBuilder) {
        if (field.getEnclosingElement() instanceof ExecutableElement) {
            injectionPointBuilder.setInjectionMethod((ExecutableElement) field.getEnclosingElement());
        }
        return injectionPointBuilder.build();
    }

    @Override
    protected boolean isRequired(final Element element) {
        if (element.getEnclosingElement() instanceof ExecutableElement) {
            if (isInjectionAnnotationPresent(element)) {
                return super.isRequired(element);
            } else {
                // Check method annotations instead of parameter ones
                return super.isRequired(element.getEnclosingElement());
            }
        } else {
            return super.isRequired(element);
        }
    }

    private void validateInjectionMethod(final ExecutableElement method) {
        if (method.getParameters().isEmpty()) {
            throw new InterruptProcessingException(
                    method,
                    "Injection method must contain a parameter. This parameter is injectable component!"
            );
        }
        if (method.getParameters().size() > 1) {
            throw new InterruptProcessingException(
                    method,
                    "Injection method must contain only one parameter. This parameter is injectable component!"
            );
        }
        validateNotPrivateMethod(method, "Injection method couldn't be a private. Use public or protected od <default> modifier instead!");
        validateNotStaticMethod(method, "Injection method couldn't be a static. Remove the 'static' modifier!");
        validateNotAbstractMethod(method, "Injection method couldn't be an abstract. Remove the 'abstract' modifier!");
    }
}
