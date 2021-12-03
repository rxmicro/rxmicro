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

import io.rxmicro.common.ImpossibleException;
import io.rxmicro.common.meta.ReadMore;
import io.rxmicro.validation.base.ConstraintRule;
import io.rxmicro.validation.internal.SelfDocumented;
import io.rxmicro.validation.validator.Base64URLEncodedConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * The annotated element must be a valid Base64 string.
 *
 * @author nedis
 * @see URLEncoded
 * @see URI
 * @see Base64URLEncodedConstraintValidator
 * @see java.util.Base64
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target({FIELD, METHOD, PARAMETER})
@ConstraintRule(
        supportedTypes = String.class,
        validatorClass = Base64URLEncodedConstraintValidator.class
)
@SelfDocumented
@ReadMore(
        caption = "What is Base64 Encoding?",
        link = "https://tools.ietf.org/html/rfc4648#section-4"
)
public @interface Base64URLEncoded {

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
     * Returns the validation {@link Alphabet}.
     *
     * @return the validation {@link Alphabet}.
     */
    Alphabet alphabet() default Alphabet.URL;

    /**
     * Validation alphabet.
     *
     * @author nedis
     * @since 0.1
     */
    enum Alphabet {

        /**
         * This array is a lookup table that translates 6-bit positive integer
         * index values into their "Base64 Alphabet" equivalents as specified
         * in "Table 1: The Base64 Alphabet" of RFC 2045 (and RFC 4648).
         */
        @ReadMore(caption = "What is Base64 Encoding?", link = "https://tools.ietf.org/html/rfc4648#section-4")
        BASE,

        /**
         * It's the lookup table for "URL and Filename safe Base64" as specified
         * in Table 2 of the RFC 4648, with the '+' and '/' changed to '-' and
         * '_'. This table is used when BASE64_URL is specified.
         */
        @ReadMore(caption = "What is Base64 Url Encoding?", link = "https://tools.ietf.org/html/rfc4648#section-5")
        URL;

        /**
         * Returns the read more link for the current alphabet.
         *
         * @return the read more link for the current alphabet.
         * @throws ImpossibleException if @{@link ReadMore} annotation is missing. This exception should be never thrown!
         */
        public ReadMore getReadMore() {
            try {
                return getClass().getDeclaredField(name()).getAnnotation(ReadMore.class);
            } catch (final NoSuchFieldException ex) {
                throw new ImpossibleException(ex, "ReadMore must be present");
            }
        }
    }
}
