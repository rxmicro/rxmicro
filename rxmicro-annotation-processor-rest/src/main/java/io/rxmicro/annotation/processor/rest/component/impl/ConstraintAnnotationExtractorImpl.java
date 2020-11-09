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

package io.rxmicro.annotation.processor.rest.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.rest.component.ConstraintAnnotationExtractor;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.validator.ModelConstraintAnnotation;
import io.rxmicro.validation.base.ConstraintRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.Annotations.getAnnotationValue;
import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getTypes;
import static io.rxmicro.annotation.processor.common.util.validators.AnnotationValidators.validateCustomAnnotation;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_VALIDATION_MODULE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class ConstraintAnnotationExtractorImpl extends AbstractProcessorComponent
        implements ConstraintAnnotationExtractor {

    @Override
    public List<ModelConstraintAnnotation> extract(final RestModelField field,
                                                   final ModelClass modelFieldType) {
        final List<ModelConstraintAnnotation> result = new ArrayList<>();
        final List<? extends AnnotationMirror> annotationMirrors = field.getAnnotationMirrors();
        for (final AnnotationMirror annotationMirror : annotationMirrors) {
            final TypeElement annotationElement = asTypeElement(annotationMirror.getAnnotationType()).orElseThrow();
            getAnnotationMirror(annotationElement, ConstraintRule.class.getName())
                    .ifPresent(customConstraint -> {
                        try {
                            validateConstraintAnnotation(annotationElement);
                            buildModelConstraintAnnotation(field, annotationMirror, customConstraint, modelFieldType)
                                    .ifPresent(result::add);
                        } catch (final InterruptProcessingException ex) {
                            error(ex);
                        }
                    });
        }
        return result;
    }

    private void validateConstraintAnnotation(final TypeElement annotationElement) {
        if (Optional.ofNullable(getElements().getModuleOf(annotationElement))
                .map(me -> !me.getQualifiedName().toString().equals(RX_MICRO_VALIDATION_MODULE.getName()))
                .orElse(true)) {
            validateCustomAnnotation(annotationElement, Set.of(FIELD, METHOD, PARAMETER));
        }
    }

    private Optional<ModelConstraintAnnotation> buildModelConstraintAnnotation(final RestModelField field,
                                                                               final AnnotationMirror annotationMirror,
                                                                               final AnnotationMirror customConstraint,
                                                                               final ModelClass modelFieldType) {
        final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues =
                getElements().getElementValuesWithDefaults(annotationMirror);
        if (isValidationDisabled(annotationMirror, elementValues)) {
            return Optional.empty();
        }
        final TypeElement validatorType = getValidatorType(field, annotationMirror, customConstraint, modelFieldType);
        return Optional.of(new ModelConstraintAnnotation(elementValues, annotationMirror, validatorType));
    }

    private Optional<? extends AnnotationMirror> getAnnotationMirror(final Element element,
                                                                     final String annotationClass) {
        return getElements().getAllAnnotationMirrors(element)
                .stream()
                .filter(a -> asTypeElement(a.getAnnotationType())
                        .orElseThrow()
                        .getQualifiedName()
                        .toString()
                        .equals(annotationClass))
                .findFirst();
    }

    private boolean isValidationDisabled(final AnnotationMirror annotationMirror,
                                         final Map<? extends ExecutableElement,
                                                 ? extends AnnotationValue> elementValues) {
        try {
            return (boolean) getAnnotationValue(elementValues, "off");
        } catch (final InternalErrorException ignore) {
            throw new InterruptProcessingException(
                    annotationMirror.getAnnotationType().asElement(),
                    "Add the required annotation parameter: \"boolean off() default false;\"." +
                            "More info is here: https://docs.rxmicro.io/latest/user-guide/validation.html#creating_custom_constraints");
        }
    }

    private TypeElement getValidatorType(final RestModelField field,
                                         final AnnotationMirror annotationMirror,
                                         final AnnotationMirror customConstraint,
                                         final ModelClass modelFieldType) {
        final List<?> supportedTypes = (List<?>) getAnnotationValue(
                customConstraint.getElementValues(), "supportedTypes");
        final List<?> validatorClasses = (List<?>) getAnnotationValue(
                customConstraint.getElementValues(), "validatorClass");
        if (validatorClasses.size() != supportedTypes.size()) {
            throw new InterruptProcessingException(
                    annotationMirror.getAnnotationType().asElement(),
                    "Expected that supportedTypes.length = validatorClass.length");
        }

        int index = indexOfSupportedClass(field.getFieldClass(), supportedTypes);
        if (index == -1) {
            if (modelFieldType.isIterable()) {
                final ModelClass elementModelClass = modelFieldType.asIterable().getElementModelClass();
                index = indexOfSupportedClass(field, elementModelClass, annotationMirror, supportedTypes);
                if (index == -1) {
                    throw new InterruptProcessingException(field.getElementAnnotatedBy(annotationMirror),
                            "'?' or '?' types couldn't be validated by @?. Change field type to one of the following: [?]",
                            getTypes().erasure(field.getFieldClass()),
                            elementModelClass.getJavaFullClassName(),
                            asTypeElement(annotationMirror.getAnnotationType()).orElseThrow().getQualifiedName(),
                            supportedTypes);
                }
            } else {
                throw new InterruptProcessingException(field.getElementAnnotatedBy(annotationMirror),
                        "'?' type couldn't be validated by @?. Change field type to one of the following: [?]",
                        field.getFieldClass(),
                        asTypeElement(annotationMirror.getAnnotationType()).orElseThrow().getQualifiedName(),
                        supportedTypes);
            }

        }
        return asTypeElement((TypeMirror) ((AnnotationValue) validatorClasses.get(index)).getValue()).orElseThrow();
    }

    private int indexOfSupportedClass(final RestModelField field,
                                      final ModelClass elementModelClass,
                                      final AnnotationMirror annotationMirror,
                                      final List<?> supportedTypes) {
        if (elementModelClass.isPrimitive()) {
            return indexOfSupportedClass(elementModelClass.asPrimitive().getTypeMirror(), supportedTypes);
        } else if (elementModelClass.isEnum()) {
            return indexOfSupportedClass(elementModelClass.asEnum().getTypeMirror(), supportedTypes);
        } else {
            throw new InterruptProcessingException(field.getElementAnnotatedBy(annotationMirror),
                    "Constraint validation @? can be applied to primitive model classes only. Please remove this annotation",
                    asTypeElement(annotationMirror.getAnnotationType()).orElseThrow().getQualifiedName());
        }
    }

    private int indexOfSupportedClass(final TypeMirror fieldType,
                                      final List<?> supportedTypes) {
        for (int i = 0; i < supportedTypes.size(); i++) {
            final TypeMirror supportedType = (TypeMirror) ((AnnotationValue) supportedTypes.get(i)).getValue();
            if (getTypes().isSameType(fieldType, supportedType) || getTypes().isSubtype(fieldType, supportedType)) {
                return i;
            }
        }
        return -1;
    }
}
