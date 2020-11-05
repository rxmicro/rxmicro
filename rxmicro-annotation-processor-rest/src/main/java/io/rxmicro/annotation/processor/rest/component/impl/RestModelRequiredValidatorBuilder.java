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

import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.validator.ModelValidatorClassStructure;
import io.rxmicro.validation.constraint.AllowEmptyString;
import io.rxmicro.validation.constraint.Enumeration;
import io.rxmicro.validation.constraint.Length;
import io.rxmicro.validation.constraint.MinLength;
import io.rxmicro.validation.constraint.Nullable;
import io.rxmicro.validation.constraint.NullableArrayItem;
import io.rxmicro.validation.validator.NotEmptyStringConstraintValidator;
import io.rxmicro.validation.validator.RequiredAndNotEmptyStringConstraintValidator;
import io.rxmicro.validation.validator.RequiredConstraintValidator;
import io.rxmicro.validation.validator.RequiredListConstraintValidator;

import java.lang.annotation.Annotation;

/**
 * @author nedis
 * @since 0.7
 */
public final class RestModelRequiredValidatorBuilder {

    public void addRequiredValidator(final ModelValidatorClassStructure.Builder builder,
                                     final RestModelField restModelField,
                                     final ModelClass modelFieldType) {
        if (modelFieldType.isList()) {
            final Nullable nullable = restModelField.getAnnotation(Nullable.class);
            if (nullable == null || nullable.off()) {
                builder.add(restModelField, Nullable.class.getSimpleName(),
                        RequiredListConstraintValidator.class.getName(), null, false);
            }
            final NullableArrayItem nullableArrayItem = restModelField.getAnnotation(NullableArrayItem.class);
            final boolean isNullable = nullableArrayItem == null || nullableArrayItem.off();
            if (modelFieldType.asList().isPrimitiveList() &&
                    String.class.getName().equals(modelFieldType.asList().getElementModelClass().getJavaFullClassName())) {
                // isString primitive
                addRequiredStringValidator(isNullable, NullableArrayItem.class, builder, restModelField, true);
            } else if (isNullable) {
                builder.add(restModelField, NullableArrayItem.class.getSimpleName(),
                        RequiredConstraintValidator.class.getName(), null, true);
            }
        } else if (modelFieldType.isPrimitive() &&
                String.class.getName().equals(modelFieldType.asPrimitive().getJavaFullClassName())) {
            // isString primitive
            final Nullable nullable = restModelField.getAnnotation(Nullable.class);
            addRequiredStringValidator(nullable == null || nullable.off(), Nullable.class, builder, restModelField, false);
        } else {
            final Nullable nullable = restModelField.getAnnotation(Nullable.class);
            if (nullable == null || nullable.off()) {
                builder.add(restModelField, Nullable.class.getSimpleName(),
                        RequiredConstraintValidator.class.getName(), null, false);
            }
        }
    }

    private void addRequiredStringValidator(final boolean isNullable,
                                            final Class<? extends Annotation> validatorAnnotationClass,
                                            final ModelValidatorClassStructure.Builder builder,
                                            final RestModelField restModelField,
                                            final boolean validateList) {
        if (isNullable) {
            if (shouldNotEmptyValidatorBeAdded(restModelField)) {
                builder.add(restModelField, validatorAnnotationClass.getSimpleName(),
                        RequiredAndNotEmptyStringConstraintValidator.class.getName(), null, validateList);
            } else {
                builder.add(restModelField, validatorAnnotationClass.getSimpleName(),
                        RequiredConstraintValidator.class.getName(), null, validateList);
            }
        } else {
            if (shouldNotEmptyValidatorBeAdded(restModelField)) {
                builder.add(restModelField, validatorAnnotationClass.getSimpleName(),
                        NotEmptyStringConstraintValidator.class.getName(), null, validateList);
            }
        }
    }

    private boolean shouldNotEmptyValidatorBeAdded(final RestModelField restModelField) {
        final AllowEmptyString allowEmptyString = restModelField.getAnnotation(AllowEmptyString.class);
        if (allowEmptyString != null && !allowEmptyString.off()) {
            return false;
        }
        final MinLength minLength = restModelField.getAnnotation(MinLength.class);
        if (minLength != null && !minLength.off()) {
            return false;
        }
        final Length length = restModelField.getAnnotation(Length.class);
        if (length != null && !length.off()) {
            return false;
        }
        final Enumeration enumeration = restModelField.getAnnotation(Enumeration.class);
        return enumeration == null || enumeration.off();
    }
}
