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
import io.rxmicro.validation.base.ConstraintParametersOrder;
import io.rxmicro.validation.base.ConstraintRule;
import io.rxmicro.validation.internal.SelfDocumented;
import io.rxmicro.validation.validator.PhoneConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * The annotated element must be a valid phone number.
 *
 * @author nedis
 * @see Viber
 * @see Telegram
 * @see WhatsApp
 * @see PhoneConstraintValidator
 * @since 0.1
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, METHOD, PARAMETER})
@ConstraintRule(
        supportedTypes = String.class,
        validatorClass = PhoneConstraintValidator.class
)
@SelfDocumented
@ReadMore(
        caption = "What is phone number format?",
        link = "https://en.wikipedia.org/wiki/National_conventions_for_writing_telephone_numbers"
)
@ConstraintParametersOrder({
        "withoutPlus",
        "allowsSpaces"
})
public @interface Phone {

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
     * Returns {@code true} if phone number must not start with plus character.
     *
     * @return {@code true} if phone number must not start with plus character
     */
    boolean withoutPlus() default true;

    /**
     * Returns {@code true} if phone number can contain space characters.
     *
     * @return {@code true} if phone number can contain space characters
     */
    boolean allowsSpaces() default false;
}
