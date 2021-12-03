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

import io.rxmicro.validation.base.ConstraintParametersOrder;
import io.rxmicro.validation.base.ConstraintRule;
import io.rxmicro.validation.validator.NumericConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.math.BigDecimal;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * The annotated element must be a decimal within accepted range (scale and precision).
 *
 * @author nedis
 * @see MinNumber
 * @see MaxNumber
 * @see NumericConstraintValidator
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target({FIELD, METHOD, PARAMETER})
@ConstraintRule(
        supportedTypes = BigDecimal.class,
        validatorClass = NumericConstraintValidator.class
)
@ConstraintParametersOrder({
        "precision",
        "scale",
        "validationType"
})
public @interface Numeric {

    /**
     * Allows disabling the validation rule if this rule is inherited from super class.
     *
     * <p>
     * By default, disable is off.
     *
     * @return  {@code true} if the validation must be disabled.
     */
    boolean off() default false;

    /**
     * Returns the expected precision.
     *
     * @return the expected precision of fractional digits accepted for this number or
     *         {@code -1} if validation of precision must be ignored
     */
    int precision() default -1;

    /**
     * Returns the expected scale.
     *
     * @return the expected scale of fractional digits accepted for this number or
     *          {@code -1} if validation of scale must be ignored
     */
    int scale();


    /**
     * Returns the validation type that must be activated for the given scale or precision value(s).
     *
     * @return the validation type that must be activated for the given scale or precision value(s).
     * @see ValidationType#MIN_SUPPORTED
     * @see ValidationType#MAX_SUPPORTED
     * @see ValidationType#EXACT
     */
    ValidationType validationType() default ValidationType.EXACT;

    /**
     * Specifies the validation type for scale and(or) precision value(s).
     *
     * @author nedis
     * @since 0.10
     */
    enum ValidationType {

        /**
         * Min supported value for numeric data.
         *
         * <p>
         * For example: <code>@Numeric(scale = 2, validationType = MIN_SUPPORTED)</code>
         * means that all the following values are valid:
         * <code>0.01, 0.001, 0.0001, 0.00001, 0.000001, etc.</code>
         *
         * <p>
         * For example: <code>@Numeric(scale = -1, precision = 2, validationType = MIN_SUPPORTED)</code>
         * means that all the following values are valid:
         * <code>10, 100, 1000, 10000, 100000 etc. or 0.01, 0.001, 0.0001, 0.00001, 0.000001, etc.</code>
         */
        MIN_SUPPORTED,

        /**
         * Max supported value for numeric data.
         *
         * <p>
         * For example: <code>@Numeric(scale = 2, validationType = MAX_SUPPORTED)</code>
         * means that all the following values are valid:
         * <code>0.01, 0.1, 1</code>
         *
         * <p>
         * For example: <code>@Numeric(scale = -1, precision = 2, validationType = MAX_SUPPORTED)</code>
         * means that all the following values are valid:
         * <code>0, 1, 10, 0.01, 0.1, 1</code>
         */
        MAX_SUPPORTED,

        /**
         * Exact value for numeric data.
         *
         * <p>
         * For example: <code>@Numeric(scale = 2, validationType = EXACT)</code>
         * means that the following value is valid only:
         * <code>0.01</code>
         *
         * <p>
         * For example: <code>@Numeric(scale = -1, precision = 2, validationType = EXACT)</code>
         * means that the following value is valid only:
         * <code>10, 0.01, 1.1</code>
         */
        EXACT
    }
}
