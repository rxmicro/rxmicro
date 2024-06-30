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

import io.rxmicro.common.meta.ReadMore;
import io.rxmicro.validation.base.ConstraintParameters;
import io.rxmicro.validation.base.ConstraintRule;
import io.rxmicro.validation.base.LocationAccuracy;
import io.rxmicro.validation.internal.SelfDocumented;
import io.rxmicro.validation.validator.LatConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.math.BigDecimal;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be a valid latitude coordinate.
 *
 * <p>
 * Read more:
 * <a href="https://en.wikipedia.org/wiki/Geographic_coordinate_system">
 * https://en.wikipedia.org/wiki/Geographic_coordinate_system
 * </a>
 *
 * @author nedis
 * @see Lng
 * @see LatConstraintValidator
 * @since 0.1
 */
@Documented
@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@ConstraintRule(
        supportedTypes = BigDecimal.class,
        validatorClasses = LatConstraintValidator.class
)
@ConstraintParameters("value")
@SelfDocumented
@ReadMore(
        caption = "What is latitude?",
        link = "https://en.wikipedia.org/wiki/Latitude"
)
public @interface Lat {

    /**
     * Min allowed value (inclusive).
     */
    BigDecimal MIN_LAT_VALUE = new BigDecimal("-90");

    /**
     * Max allowed value (inclusive).
     */
    BigDecimal MAX_LAT_VALUE = new BigDecimal("90");

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
     * Returns the latitude {@link LocationAccuracy}
     *
     * <p>
     * By default, accuracy equals to 1 meter.
     *
     * @return the latitude {@link LocationAccuracy}
     */
    LocationAccuracy value() default LocationAccuracy.ACCURACY_1_METER;
}
