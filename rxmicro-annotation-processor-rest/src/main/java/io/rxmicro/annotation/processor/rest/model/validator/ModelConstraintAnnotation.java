/*
 * Copyright 2019 http://rxmicro.io
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

import io.rxmicro.validation.constraint.MaxSize;
import io.rxmicro.validation.constraint.MinSize;
import io.rxmicro.validation.constraint.Size;
import io.rxmicro.validation.constraint.UniqueItems;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class ModelConstraintAnnotation {

    private static final Set<String> LIST_VALIDATOR_CONSTRAINT_CLASS_NAMES = Set.of(
            Size.class.getSimpleName(),
            MinSize.class.getSimpleName(),
            MaxSize.class.getSimpleName(),
            UniqueItems.class.getSimpleName()
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

    public String getAnnotationSimpleName() {
        return getSimpleName(annotationMirror.getAnnotationType().toString());
    }

    public String getAnnotationFullName() {
        return annotationMirror.getAnnotationType().toString();
    }

    public boolean isListConstraint() {
        return LIST_VALIDATOR_CONSTRAINT_CLASS_NAMES.contains(getAnnotationSimpleName());
    }

    public String getJavaFullName() {
        return validatorType.asType().toString();
    }

}
