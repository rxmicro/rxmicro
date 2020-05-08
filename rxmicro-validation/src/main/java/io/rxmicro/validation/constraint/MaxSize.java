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
import io.rxmicro.validation.validator.MaxSizeConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * The annotated element must have a list size whose value must be lower or
 * equal to the specified maximum.
 *
 * @author nedis
 * @since 0.1
 * @see Size
 * @see MinSize
 * @see UniqueItems
 * @see MaxSizeConstraintValidator
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, METHOD, PARAMETER})
@ConstraintRule(
        supportedTypes = {
                List.class
        },
        validatorClass = {
                MaxSizeConstraintValidator.class
        }
)
public @interface MaxSize {

    /**
     * Allows disabling the validation rule if this rule is inherited from super class. <p>
     * By default, disable is off
     *
     * @return  {@code true} if the validation must be disabled,
     */
    boolean off() default false;

    /**
     * Returns the value the list size must be lower or equal to
     *
     * @return the value the list size must be lower or equal to
     */
    int value();

    /**
     * Specifies whether the specified maximum is inclusive or exclusive.
     * By default, it is inclusive.
     *
     * @return  {@code true} if the value must be lower or equal to the specified maximum,
     *          {@code false} if the value must be lower
     */
    boolean inclusive() default true;
}
