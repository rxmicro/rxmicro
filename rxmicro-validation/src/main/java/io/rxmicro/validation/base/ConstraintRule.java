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

package io.rxmicro.validation.base;

import io.rxmicro.validation.ConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Adds meta information to a constraint annotation that is annotated by this annotation. This meta information is pairs of java types
 * ({@link #supportedTypes()}) and correspond validator classes ({@link #validatorClasses()}) that contain a validation logic for
 * the constraint annotation.
 * <p>This constraint annotation can be applied to any java type that is listed in {@link #supportedTypes()} array. If this constraint
 * annotation is enabled for a type from this list, then a validator that has the same index in the {@link #validatorClasses()} array
 * will be used for validation of this type for this constraint annotation.
 *
 * @author nedis
 * @since 0.1
 */
@Documented
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface ConstraintRule {

    /**
     * A name of mandatory parameter for any constraint annotation.
     * <p>This parameter allows disabling the validation rule if this rule is inherited from super class.
     */
    String OFF = "off";

    /**
     * Returns an array of supported types, which can be validated by the constraint annotation.
     *
     * @return not-empty array of the supported types.
     */
    Class<?>[] supportedTypes();

    /**
     * Returns an array of {@link ConstraintValidator} classes for all supported types, i.e.
     * {@code supportedTypes.length} must be equal to {@code validatorClass.length}, otherwise an exception will be thrown.
     *
     * @return not-empty array of {@link ConstraintValidator} classes for all supported types.
     */
    @SuppressWarnings("rawtypes")
    Class<? extends ConstraintValidator>[] validatorClasses();
}
