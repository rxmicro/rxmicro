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
import io.rxmicro.validation.base.ConstraintRule;
import io.rxmicro.validation.internal.SelfDocumented;
import io.rxmicro.validation.validator.URIConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * The annotated element must be a valid {@link java.net.URI} address.
 *
 * @author nedis
 * @since 0.1
 * @see URLEncoded
 * @see Base64URLEncoded
 * @see java.net.URI
 * @see URIConstraintValidator
 *
 *
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, METHOD, PARAMETER})
@ConstraintRule(
        supportedTypes = {
                String.class
        },
        validatorClass = {
                URIConstraintValidator.class
        }
)
@SelfDocumented
@ReadMore(
        caption = "What is URI?",
        link = "https://en.wikipedia.org/wiki/Uniform_Resource_Identifier"
)
public @interface URI {

    /**
     * Allows disabling the validation rule if this rule is inherited from super class. <p>
     * By default, disable is off
     *
     * @return  {@code true} if the validation must be disabled,
     */
    boolean off() default false;
}
