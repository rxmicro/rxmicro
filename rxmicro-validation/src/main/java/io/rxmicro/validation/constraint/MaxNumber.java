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

import io.rxmicro.validation.base.ConstraintRule;
import io.rxmicro.validation.validator.MaxBigDecimalNumberConstraintValidator;
import io.rxmicro.validation.validator.MaxBigIntegerNumberConstraintValidator;
import io.rxmicro.validation.validator.MaxByteConstraintValidator;
import io.rxmicro.validation.validator.MaxDoubleConstraintValidator;
import io.rxmicro.validation.validator.MaxFloatConstraintValidator;
import io.rxmicro.validation.validator.MaxIntConstraintValidator;
import io.rxmicro.validation.validator.MaxLongConstraintValidator;
import io.rxmicro.validation.validator.MaxShortConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.math.BigInteger;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * The annotated element must be a number whose value must be lower or
 * equal to the specified maximum.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @see MaxBigDecimalNumberConstraintValidator
 * @see MaxBigIntegerNumberConstraintValidator
 * @see MaxByteConstraintValidator
 * @see MaxShortConstraintValidator
 * @see MaxIntConstraintValidator
 * @see MaxLongConstraintValidator
 * @see MaxFloatConstraintValidator
 * @see MaxDoubleConstraintValidator
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, METHOD, PARAMETER})
@ConstraintRule(
        supportedTypes = {
                BigDecimal.class,
                BigInteger.class,
                Byte.class,
                Short.class,
                Integer.class,
                Long.class,
                Float.class,
                Double.class
        },
        validatorClass = {
                MaxBigDecimalNumberConstraintValidator.class,
                MaxBigIntegerNumberConstraintValidator.class,
                MaxByteConstraintValidator.class,
                MaxShortConstraintValidator.class,
                MaxIntConstraintValidator.class,
                MaxLongConstraintValidator.class,
                MaxFloatConstraintValidator.class,
                MaxDoubleConstraintValidator.class
        }
)
public @interface MaxNumber {

    /**
     * Allows to disable validation rule if validation inherited from super class.
     * By default, disable is off
     *
     * @return {@code true} if the validation must be disabled,
     * {@code false} otherwise
     */
    boolean off() default false;

    /**
     * The {@code String} representation of the max value according to the
     * {@code BigDecimal} string representation.
     *
     * @return value the element must be lower or equal to
     */
    String value();

    /**
     * Specifies whether the specified maximum is inclusive or exclusive.
     * By default, it is inclusive.
     *
     * @return {@code true} if the value must be lower or equal to the specified maximum,
     * {@code false} if the value must be lower
     */
    boolean inclusive() default true;
}
