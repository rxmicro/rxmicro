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

package io.rxmicro.annotation.processor.rest.model.validator;

import io.rxmicro.validation.base.ConstraintParametersOrder;
import io.rxmicro.validation.base.ParametrizedConstraintValidator;
import io.rxmicro.validation.constraint.MaxSize;
import io.rxmicro.validation.constraint.MinSize;
import io.rxmicro.validation.constraint.Size;
import io.rxmicro.validation.constraint.UniqueItems;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class ModelConstraintAnnotation implements ModelValidatorCreatorDescriptor {

    private static final Set<String> ITERABLE_VALIDATOR_CONSTRAINT_CLASS_NAMES = Set.of(
            Size.class.getName(),
            MinSize.class.getName(),
            MaxSize.class.getName(),
            UniqueItems.class.getName()
    );

    private final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues;

    private final AnnotationMirror annotationMirror;

    private final TypeElement validatorType;

    public ModelConstraintAnnotation(final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues,
                                     final AnnotationMirror annotationMirror,
                                     final TypeElement validatorType) {
        this.elementValues = require(elementValues);
        this.annotationMirror = require(annotationMirror);
        this.validatorType = require(validatorType);
    }

    public Map<? extends ExecutableElement, ? extends AnnotationValue> getElementValues() {
        return elementValues;
    }

    public List<String> getParameterOrder() {
        return Optional
                .ofNullable(annotationMirror.getAnnotationType().asElement().getAnnotation(ConstraintParametersOrder.class))
                .map(ConstraintParametersOrder::value)
                .map(List::of)
                .orElse(List.of());
    }

    @Override
    public String getConstraintAnnotationFullName() {
        return annotationMirror.getAnnotationType().toString();
    }

    public boolean isIterableConstraint() {
        return ITERABLE_VALIDATOR_CONSTRAINT_CLASS_NAMES.contains(getConstraintAnnotationFullName());
    }

    @Override
    public String getValidatorFullClassName() {
        return validatorType.asType().toString();
    }

    @Override
    public boolean isParametrizedConstraintValidator() {
        return validatorType.getAnnotation(ParametrizedConstraintValidator.class) != null;
    }
}
