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

package io.rxmicro.validation.constraint;

import io.rxmicro.validation.base.ConstraintParameters;
import io.rxmicro.validation.base.ConstraintRule;
import io.rxmicro.validation.validator.MinDoubleConstraintValidator;
import io.rxmicro.validation.validator.MinFloatConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be a double whose value must be higher or
 * equal to the specified minimum.
 *
 * @author nedis
 * @see MinInt
 * @see Min
 * @see MinFloatConstraintValidator
 * @see MinDoubleConstraintValidator
 * @since 0.1
 */
@Documented
@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER})
@ConstraintRule(
        supportedTypes = {
                Float.class,
                Double.class
        },
        validatorClasses = {
                MinFloatConstraintValidator.class,
                MinDoubleConstraintValidator.class
        }
)
@ConstraintParameters("value")
public @interface MinDouble {

    /**
     * Allows disabling the validation rule if this rule is inherited from super class.
     *
     * <p>
     * By default, disable is off.
     *
     * @return {@code true} if the validation must be disabled.
     */
    boolean off() default false;

    /**
     * Returns the value the element must be higher or equal to.
     *
     * @return the value the element must be higher or equal to
     */
    double value();
}
