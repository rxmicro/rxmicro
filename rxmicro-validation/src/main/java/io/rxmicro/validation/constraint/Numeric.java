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
import static java.lang.annotation.RetentionPolicy.SOURCE;

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
@Retention(SOURCE)
@Target({FIELD, METHOD, PARAMETER})
@ConstraintRule(
        supportedTypes = BigDecimal.class,
        validatorClass = NumericConstraintValidator.class
)
@ConstraintParametersOrder({
        "precision",
        "scale"
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
}
