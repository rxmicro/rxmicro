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
import io.rxmicro.validation.validator.LatinAlphabetOnlyConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * The annotated element must be a string with latin alphabet letters only.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @see LatinAlphabetOnlyConstraintValidator
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, METHOD, PARAMETER})
@ConstraintRule(
        supportedTypes = {
                String.class
        },
        validatorClass = {
                LatinAlphabetOnlyConstraintValidator.class
        }
)
@SelfDocumented
public @interface LatinAlphabetOnly {

    /**
     * Allows disabling the validation rule if this rule is inherited from super class. <p>
     * By default, disable is off
     *
     * @return  {@code true} if the validation must be disabled,
     */
    boolean off() default false;

    /**
     * Returns {@code true} if the annotated element can contain uppercase letters
     *
     * @return {@code true} if the annotated element can contain uppercase letters
     */
    boolean allowsUppercase() default true;

    /**
     * Returns {@code true} if the annotated element can contain lowercase letters
     *
     * @return {@code true} if the annotated element can contain lowercase letters
     */
    boolean allowsLowercase() default true;

    /**
     * Returns {@code true} if the annotated element can contain digits
     *
     * @return {@code true} if the annotated element can contain digits
     */
    boolean allowsDigits() default true;

    /**
     * Returns all supported punctuations
     *
     * @return all supported punctuations
     */
    String punctuations() default "~!@#$%^&*()_+=-[]{},.;:<>?/\\\"' \t|\r\n";
}
