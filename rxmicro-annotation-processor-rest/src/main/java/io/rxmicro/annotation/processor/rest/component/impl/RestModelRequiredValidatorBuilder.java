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

import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.common.model.type.IterableModelClass;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.validator.ModelValidatorClassStructure;
import io.rxmicro.annotation.processor.rest.model.validator.ModelValidatorCreatorDescriptor;
import io.rxmicro.validation.ConstraintValidator;
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
import io.rxmicro.validation.validator.RequiredMapConstraintValidator;
import io.rxmicro.validation.validator.RequiredSetConstraintValidator;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author nedis
 * @since 0.7
 */
public final class RestModelRequiredValidatorBuilder {

    private final Map<String, Class<? extends ConstraintValidator<?>>> iterableRequiredValidators = Map.of(
            List.class.getSimpleName(), RequiredListConstraintValidator.class,
            Set.class.getSimpleName(), RequiredSetConstraintValidator.class,
            Map.class.getSimpleName(), RequiredMapConstraintValidator.class
    );

    public void addRequiredValidator(final ModelValidatorClassStructure.Builder builder,
                                     final RestModelField restModelField,
                                     final ModelClass modelFieldType) {
        if (modelFieldType.isIterable()) {
            final Nullable nullable = restModelField.getAnnotation(Nullable.class);
            if (nullable == null || nullable.off()) {
                final RequiredModelValidatorCreatorDescriptor descriptor = new RequiredModelValidatorCreatorDescriptor(
                        Nullable.class, getIterableRequiredValidator(modelFieldType.asIterable())
                );
                builder.add(restModelField, modelFieldType, descriptor, null, false);
            }
            final NullableArrayItem nullableArrayItem = restModelField.getAnnotation(NullableArrayItem.class);
            final boolean isArrayItemNotNull = nullableArrayItem == null || nullableArrayItem.off();
            if (modelFieldType.asIterable().isPrimitiveIterable() &&
                    String.class.getName().equals(modelFieldType.asIterable().getElementModelClass().getJavaFullClassName())) {
                // isString primitive
                addRequiredStringValidator(
                        isArrayItemNotNull, NullableArrayItem.class, builder, restModelField, modelFieldType, true
                );
            } else if (isArrayItemNotNull) {
                final RequiredModelValidatorCreatorDescriptor descriptor = new RequiredModelValidatorCreatorDescriptor(
                        NullableArrayItem.class, RequiredConstraintValidator.class
                );
                builder.add(restModelField, modelFieldType, descriptor, null, true);
            }
        } else if (modelFieldType.isPrimitive() &&
                String.class.getName().equals(modelFieldType.asPrimitive().getJavaFullClassName())) {
            // isString primitive
            final Nullable nullable = restModelField.getAnnotation(Nullable.class);
            addRequiredStringValidator(
                    nullable == null || nullable.off(), Nullable.class, builder, restModelField, modelFieldType, false
            );
        } else {
            final Nullable nullable = restModelField.getAnnotation(Nullable.class);
            if (nullable == null || nullable.off()) {
                final RequiredModelValidatorCreatorDescriptor descriptor = new RequiredModelValidatorCreatorDescriptor(
                        Nullable.class, RequiredConstraintValidator.class
                );
                builder.add(restModelField, modelFieldType, descriptor, null, false);
            }
        }
    }

    private Class<? extends ConstraintValidator<?>> getIterableRequiredValidator(final IterableModelClass iterableModelClass) {
        return Optional.ofNullable(iterableRequiredValidators.get(iterableModelClass.getContainerType())).orElseThrow(
                () -> {
                    throw new InternalErrorException(
                            "Required iterable validator not defined for type: ?!",
                            iterableModelClass.getContainerType()
                    );
                }
        );
    }

    private void addRequiredStringValidator(final boolean isNullable,
                                            final Class<? extends Annotation> constraintAnnotationClass,
                                            final ModelValidatorClassStructure.Builder builder,
                                            final RestModelField restModelField,
                                            final ModelClass modelFieldType,
                                            final boolean validateIterable) {
        RequiredModelValidatorCreatorDescriptor descriptor = null;
        if (isNullable) {
            if (shouldNotEmptyValidatorBeAdded(restModelField)) {
                descriptor = new RequiredModelValidatorCreatorDescriptor(
                        constraintAnnotationClass, RequiredAndNotEmptyStringConstraintValidator.class
                );
            } else {
                descriptor = new RequiredModelValidatorCreatorDescriptor(
                        constraintAnnotationClass, RequiredConstraintValidator.class
                );
            }
        } else {
            if (shouldNotEmptyValidatorBeAdded(restModelField)) {
                descriptor = new RequiredModelValidatorCreatorDescriptor(
                        constraintAnnotationClass, NotEmptyStringConstraintValidator.class
                );
            }
        }
        if (descriptor != null) {
            builder.add(restModelField, modelFieldType, descriptor, null, validateIterable);
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

    /**
     * @author nedis
     * @since 0.9
     */
    private static final class RequiredModelValidatorCreatorDescriptor implements ModelValidatorCreatorDescriptor {

        private final Class<? extends Annotation> annotationClass;

        private final Class<? extends ConstraintValidator<?>> validatorClass;

        private RequiredModelValidatorCreatorDescriptor(final Class<? extends Annotation> annotationClass,
                                                        final Class<? extends ConstraintValidator<?>> validatorClass) {
            this.annotationClass = annotationClass;
            this.validatorClass = validatorClass;
        }

        @Override
        public String getConstraintAnnotationFullName() {
            return annotationClass.getName();
        }

        @Override
        public String getValidatorFullClassName() {
            return validatorClass.getName();
        }

        @Override
        public boolean isParametrizedConstraintValidator() {
            return false;
        }
    }
}
