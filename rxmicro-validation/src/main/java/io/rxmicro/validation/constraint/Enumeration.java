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
import io.rxmicro.validation.internal.SelfDocumented;
import io.rxmicro.validation.validator.EnumerationCharacterConstraintValidator;
import io.rxmicro.validation.validator.EnumerationStringConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * The annotated element must be an element of the predefined enumeration.
 *
 * <p>
 * This validation rule is useful when java enum type is not applicable.
 *
 * <p>
 * For example if enum value equals to java keyword.
 * The following enum couldn't be compiled, because {@code 'new'} is java keyword
 * <pre><code>
 * enum OrderType {
 *      new,    // compile error
 *      old
 * }
 * </code></pre>
 *
 * <p>
 * To solve this issue use @{@link Enumeration} annotation, otherwise use Java enum.
 *
 * @author nedis
 * @see Enum
 * @see SubEnum
 * @see EnumerationStringConstraintValidator
 * @see EnumerationCharacterConstraintValidator
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, METHOD, PARAMETER})
@ConstraintRule(
        supportedTypes = {
                String.class,
                Character.class
        },
        validatorClass = {
                EnumerationStringConstraintValidator.class,
                EnumerationCharacterConstraintValidator.class
        }
)
@SelfDocumented
public @interface Enumeration {

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
     * Returns the enum string values.
     *
     * @return the enum string values
     */
    String[] value();
}
