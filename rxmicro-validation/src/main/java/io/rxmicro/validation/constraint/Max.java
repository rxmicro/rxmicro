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
import io.rxmicro.validation.validator.MaxBigDecimalConstraintValidator;
import io.rxmicro.validation.validator.MaxBigIntegerConstraintValidator;
import io.rxmicro.validation.validator.MaxByteConstraintValidator;
import io.rxmicro.validation.validator.MaxDoubleConstraintValidator;
import io.rxmicro.validation.validator.MaxDurationConstraintValidator;
import io.rxmicro.validation.validator.MaxFloatConstraintValidator;
import io.rxmicro.validation.validator.MaxIntConstraintValidator;
import io.rxmicro.validation.validator.MaxLongConstraintValidator;
import io.rxmicro.validation.validator.MaxShortConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be a number whose value must be lower or
 * equal to the specified maximum.
 *
 * @author nedis
 * @see MaxInt
 * @see MaxDouble
 * @see Numeric
 * @see MaxBigDecimalConstraintValidator
 * @see MaxBigIntegerConstraintValidator
 * @see MaxByteConstraintValidator
 * @see MaxShortConstraintValidator
 * @see MaxIntConstraintValidator
 * @see MaxLongConstraintValidator
 * @see MaxFloatConstraintValidator
 * @see MaxDoubleConstraintValidator
 * @see MaxDurationConstraintValidator
 * @since 0.1
 */
@Documented
@Retention(RUNTIME)
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
                Double.class,
                Duration.class
        },
        validatorClasses = {
                MaxBigDecimalConstraintValidator.class,
                MaxBigIntegerConstraintValidator.class,
                MaxByteConstraintValidator.class,
                MaxShortConstraintValidator.class,
                MaxIntConstraintValidator.class,
                MaxLongConstraintValidator.class,
                MaxFloatConstraintValidator.class,
                MaxDoubleConstraintValidator.class,
                MaxDurationConstraintValidator.class
        }
)
@ConstraintParameters({
        "value",
        "inclusive"
})
public @interface Max {

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
     * The {@link String} representation of the max value according to the
     * {@link BigDecimal} string representation.
     *
     * @return the value the element must be lower or equal to
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
