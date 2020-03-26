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
import io.rxmicro.annotation.processor.cdi.component.UserDefinedNameBuilder;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.elements;
import static io.rxmicro.annotation.processor.common.util.Annotations.getAnnotationElement;
import static io.rxmicro.annotation.processor.common.util.Annotations.getAnnotationValue;
import static io.rxmicro.annotation.processor.common.util.validators.AnnotationValidators.validateCustomAnnotation;
import static io.rxmicro.cdi.local.AnnotationsSupport.QUALIFIER_ANNOTATIONS;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class UserDefinedNameBuilderImpl implements UserDefinedNameBuilder {

    @Override
    public Optional<String> getName(final Element element) {
        final List<Map.Entry<TypeMirror, String>> qualifiedNames = getAllQualifiedNamesDefinedByUser(element);
        validate(element, qualifiedNames, () -> qualifiedNames.stream().map(Map.Entry::getKey).collect(toList()));
        return qualifiedNames.stream().map(Map.Entry::getValue).findFirst();
    }

    private List<Map.Entry<TypeMirror, String>> getAllQualifiedNamesDefinedByUser(final Element element) {
        final List<Map.Entry<TypeMirror, String>> result = new ArrayList<>();
        for (final AnnotationMirror annotation : element.getAnnotationMirrors()) {
            if (QUALIFIER_ANNOTATIONS.stream().anyMatch(cl -> cl.getName().equals(annotation.getAnnotationType().toString()))) {
                final String annotationValue = (String) getAnnotationValue(elements().getElementValuesWithDefaults(annotation));
                validateNotEmpty(element, annotationValue);
                validateStringQualifier(element, annotationValue);
                result.add(entry(
                        annotation.getAnnotationType(),
                        annotationValue
                ));
            } else {
                getCustomQualifierAnnotationValue(annotation)
                        .ifPresent(value -> result.add(entry(annotation.getAnnotationType(), value)));
            }
        }
        return result;
    }

    private Optional<String> getCustomQualifierAnnotationValue(final AnnotationMirror annotation) {
        final TypeElement annotationElement = getAnnotationElement(annotation);
        final List<? extends AnnotationMirror> mirrors = annotationElement.getAnnotationMirrors().stream()
                .filter(a -> QUALIFIER_ANNOTATIONS.stream().anyMatch(cl -> cl.getName().equals(a.getAnnotationType().toString())))
                .collect(toList());
        validate(annotationElement, mirrors, () -> QUALIFIER_ANNOTATIONS.stream().map(Class::getName).collect(toList()));
        validateCustomAnnotation(annotationElement, Set.of(FIELD, METHOD, TYPE, PARAMETER, CONSTRUCTOR));
        return mirrors.stream().findFirst()
                .map(a -> (String) getAnnotationValue(elements().getElementValuesWithDefaults(a)))
                .map(value -> Optional.of(value)
                        .filter(v -> !v.isEmpty())
                        .map(v -> {
                            validateStringQualifier(annotationElement, v);
                            validateNoAnnotationParameters(annotationElement);
                            return v;
                        })
                        .orElseGet(() -> buildAnnotationTypeQualifierName(annotation))
                );
    }

    private void validate(final Element owner,
                          final Collection<?> qualifiedNames,
                          final Supplier<List<?>> allowedAnnotationsSupplier) {
        if (qualifiedNames.size() > 1) {
            throw new InterruptProcessingException(owner,
                    "Redundant qualifier annotation per element. Use only one annotation from the list: ?!",
                    allowedAnnotationsSupplier.get()
            );
        }
    }

    private void validateStringQualifier(final Element element,
                                         final String annotationValue) {
        if (annotationValue.startsWith("@")) {
            throw new InterruptProcessingException(
                    element,
                    "String qualifier couldn't start with '@' character. Remote invalid character!"
            );
        }
    }

    private void validateNotEmpty(final Element element,
                                  final String annotationValue) {
        if (annotationValue.trim().isEmpty()) {
            throw new InterruptProcessingException(
                    element,
                    "String qualifier couldn't be empty. Add qualifier name!"
            );
        }
    }

    private void validateNoAnnotationParameters(final TypeElement annotationElement) {
        if (!annotationElement.getEnclosedElements().isEmpty()) {
            throw new InterruptProcessingException(
                    annotationElement,
                    "Custom qualifier annotation with specified string qualifier name must not contain any parameters. " +
                            "Remove all annotation parameters!"
            );
        }
    }

    private String buildAnnotationTypeQualifierName(final AnnotationMirror annotation) {
        final StringBuilder builder = new StringBuilder("@");
        builder.append(annotation.getAnnotationType());
        final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues =
                elements().getElementValuesWithDefaults(annotation);
        if (!elementValues.isEmpty()) {
            builder.append('(');
            boolean entryAdded = false;
            for (final Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {
                if (entryAdded) {
                    builder.append(',');
                }
                builder.append(entry.getKey().getSimpleName()).append('=').append(entry.getValue().getValue());
                entryAdded = true;
            }
            builder.append(')');
        }
        return builder.toString();
    }
}
