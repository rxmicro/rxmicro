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
import io.rxmicro.validation.validator.MinBigDecimalNumberConstraintValidator;
import io.rxmicro.validation.validator.MinBigIntegerNumberConstraintValidator;
import io.rxmicro.validation.validator.MinByteConstraintValidator;
import io.rxmicro.validation.validator.MinDoubleConstraintValidator;
import io.rxmicro.validation.validator.MinFloatConstraintValidator;
import io.rxmicro.validation.validator.MinIntConstraintValidator;
import io.rxmicro.validation.validator.MinLongConstraintValidator;
import io.rxmicro.validation.validator.MinShortConstraintValidator;

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
 * The annotated element must be a number whose value must be higher or
 * equal to the specified minimum.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @see MinBigDecimalNumberConstraintValidator
 * @see MinBigIntegerNumberConstraintValidator
 * @see MinByteConstraintValidator
 * @see MinShortConstraintValidator
 * @see MinIntConstraintValidator
 * @see MinLongConstraintValidator
 * @see MinFloatConstraintValidator
 * @see MinDoubleConstraintValidator
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
                MinBigDecimalNumberConstraintValidator.class,
                MinBigIntegerNumberConstraintValidator.class,
                MinByteConstraintValidator.class,
                MinShortConstraintValidator.class,
                MinIntConstraintValidator.class,
                MinLongConstraintValidator.class,
                MinFloatConstraintValidator.class,
                MinDoubleConstraintValidator.class
        }
)
public @interface MinNumber {

    /**
     * Allows disabling the validation rule if this rule is inherited from super class. <p>
     * By default, disable is off
     *
     * @return  {@code true} if the validation must be disabled,
     */
    boolean off() default false;

    /**
     * The {@link String} representation of the min value according to the
     * {@link BigDecimal} string representation.
     *
     * @return the value the element must be higher or equal to
     */
    String value();

    /**
     * Specifies whether the specified minimum is inclusive or exclusive.
     * By default, it is inclusive.
     *
     * @return  {@code true} if the value must be lower or equal to the specified maximum,
     *          {@code false} if the value must be lower
     */
    boolean inclusive() default true;
}
