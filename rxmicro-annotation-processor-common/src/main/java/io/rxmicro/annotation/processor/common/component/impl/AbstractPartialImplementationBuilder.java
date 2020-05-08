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

import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static io.rxmicro.annotation.processor.common.util.Annotations.getAnnotationValue;
import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;
import static io.rxmicro.annotation.processor.common.util.Elements.superClassIsObject;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateExpectedElementKind;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateNotNestedClass;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateNotSuperInterfaces;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateThatElementIsPublic;
import static java.util.Map.entry;
import static javax.lang.model.element.ElementKind.INTERFACE;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractPartialImplementationBuilder extends AbstractProcessorComponent {

    protected final void validateInterfaceType(final Element element,
                                               final Class<? extends Annotation> markerAnnotationType,
                                               final String name) {
        validateExpectedElementKind(element, INTERFACE, "'@?' annotation is applied to interface only.", markerAnnotationType.getName());
        validateNotNestedClass((TypeElement) element, "? interface couldn't be a nested interface.", name);
        validateNotSuperInterfaces((TypeElement) element, "? interface couldn't extend any interfaces", name);
        validateThatElementIsPublic(element, "? interface must be a public.", name);
    }

    protected final void validatePartialImplementation(final TypeElement interfaceType,
                                                       final TypeElement abstractClassType,
                                                       final Class<? extends Annotation> partialImplementationAnnotation) {
        validateThatAbstractClassImplementsInterface(interfaceType, abstractClassType);
        validateThatPartialImplementationClassIsAbstract(interfaceType, abstractClassType);
        validateThatCorrectVersionOfPartialImplementationIsUsed(interfaceType, partialImplementationAnnotation);
    }

    private void validateThatAbstractClassImplementsInterface(final TypeElement interfaceType,
                                                              final TypeElement abstractClassType) {
        TypeElement currentTypeElement = abstractClassType;
        while (true) {
            for (final TypeMirror anInterface : currentTypeElement.getInterfaces()) {
                if (anInterface.toString().equals(interfaceType.asType().toString())) {
                    return;
                }
            }
            final TypeMirror superClass = currentTypeElement.getSuperclass();
            if (superClassIsObject(superClass)) {
                throw new InterruptProcessingException(
                        interfaceType,
                        "Partial implementation class '?' must implement '?' interface",
                        abstractClassType.asType().toString(),
                        interfaceType.asType().toString()
                );
            }
            currentTypeElement = asTypeElement(superClass).orElseThrow();
        }
    }

    private void validateThatPartialImplementationClassIsAbstract(final TypeElement interfaceType,
                                                                  final TypeElement abstractClassType) {
        if (!abstractClassType.getModifiers().contains(Modifier.ABSTRACT)) {
            throw new InterruptProcessingException(
                    interfaceType,
                    "Partial implementation class '?' must be an abstract class",
                    abstractClassType.asType().toString()
            );
        }
    }

    private void validateThatCorrectVersionOfPartialImplementationIsUsed(final TypeElement interfaceType,
                                                                         final Class<? extends Annotation> partialImplementationAnnotation) {
        for (final AnnotationMirror annotationMirror : interfaceType.getAnnotationMirrors()) {
            final DeclaredType annotationType = annotationMirror.getAnnotationType();
            if (!annotationType.toString().equals(partialImplementationAnnotation.getName()) &&
                    "PartialImplementation".equals(getSimpleName(annotationType))) {
                throw new InterruptProcessingException(
                        interfaceType,
                        "Annotation '@?' is not valid! Use '@?' instead",
                        annotationType.toString(),
                        partialImplementationAnnotation.getName()
                );
            }
        }
    }

    protected final Map.Entry<TypeElement, List<ExecutableElement>> getOverriddenMethodCandidates(final TypeElement restClientInterface) {
        final Class<? extends Annotation> partialImplementationAnnotationClass = getPartialImplementationAnnotationClass();
        final TypeElement defaultAbstractClass = getElements().getTypeElement(getDefaultAbstractClass().getName());

        return restClientInterface.getAnnotationMirrors().stream()
                .filter(a -> a.getAnnotationType().toString().equals(partialImplementationAnnotationClass.getName()))
                .findFirst()
                .flatMap(a -> asTypeElement((TypeMirror) getAnnotationValue(a.getElementValues())).map(t -> {
                    validatePartialImplementation(restClientInterface, t, partialImplementationAnnotationClass);
                    return entry(t, getAllOverrideMethodCandidatesExceptBaseMethods(defaultAbstractClass, t));
                }))
                .orElse(entry(defaultAbstractClass, List.of()));
    }

    protected abstract Class<? extends Annotation> getPartialImplementationAnnotationClass();

    protected abstract Class<?> getDefaultAbstractClass();

    private List<ExecutableElement> getAllOverrideMethodCandidatesExceptBaseMethods(final TypeElement baseParentClass,
                                                                                    final TypeElement abstractClassType) {
        final List<ExecutableElement> methods = new ArrayList<>();
        TypeElement currentTypeElement = abstractClassType;
        while (true) {
            methods.addAll(0, currentTypeElement.getEnclosedElements().stream()
                    .filter(el -> el.getKind() == ElementKind.METHOD)
                    .map(el -> (ExecutableElement) el)
                    .filter(el -> Set.of(PRIVATE, STATIC).stream().noneMatch(m -> el.getModifiers().contains(m)))
                    .collect(Collectors.toList()));
            final TypeMirror superClass = currentTypeElement.getSuperclass();
            if (superClassIsObject(superClass)) {
                throw new InternalErrorException("Missing validatePartialImplementation call before");
            } else if (superClass.toString().equals(baseParentClass.asType().toString())) {
                break;
            } else {
                currentTypeElement = asTypeElement(superClass).orElseThrow();
            }
        }
        return methods;
    }
}
