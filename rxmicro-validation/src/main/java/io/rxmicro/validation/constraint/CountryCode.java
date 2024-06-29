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
import io.rxmicro.validation.validator.CountryCodeConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Locale;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be a valid country code.
 *
 * @author nedis
 * @see CountryCodeConstraintValidator
 * @since 0.1
 */
@Documented
@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@ConstraintRule(
        supportedTypes = String.class,
        validatorClass = CountryCodeConstraintValidator.class
)
@SelfDocumented
public @interface CountryCode {

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
     * Return the country code {@link Format}.
     *
     * @return the country code {@link Format}
     */
    Format format() default Format.ISO_3166_1_ALPHA_2;

    /**
     * The country code format.
     *
     * @author nedis
     * @since 0.1
     */
    enum Format {

        /**
         * ISO 3166-1 alpha2 format.
         */
        @ReadMore(
                caption = "What is ISO 3166-1 alpha2?",
                link = "https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2"
        )
        ISO_3166_1_ALPHA_2("Two-letter country code according to ISO 3166-1 alpha2 standard."),

        /**
         * ISO 3166-1 alpha3 format.
         */
        @ReadMore(
                caption = "What is ISO 3166-1 alpha3?",
                link = "https://en.wikipedia.org/wiki/ISO_3166-1_alpha-3"
        )
        ISO_3166_1_ALPHA_3("Three-letter country code according to ISO 3166-1 alpha3 standard."),

        /**
         * ISO 3166-1 numeric format.
         */
        @ReadMore(
                caption = "What is ISO 3166-1 numeric?",
                link = "https://en.wikipedia.org/wiki/ISO_3166-1_numeric"
        )
        ISO_3166_1_NUMERIC("Three-digit country code according to ISO 3166-1 numeric standard.");

        private static final int ISO_3166_1_PREFIX_LENGTH = 11;

        private final String description;

        Format(final String description) {
            this.description = description;
        }

        /**
         * Returns the current country code type.
         *
         * @return the current country code type.
         */
        public String getType() {
            return name().substring(ISO_3166_1_PREFIX_LENGTH).toLowerCase(Locale.ENGLISH).replace("_", "-");
        }

        /**
         * Returns the current country code description.
         *
         * @return the current country code description.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Returns the read more link for the current country code value.
         *
         * @return the read more link for the current country code value.
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
