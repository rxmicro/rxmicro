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
import io.rxmicro.validation.base.SelfDocumented;
import io.rxmicro.validation.validator.CountryCodeConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * The annotated element must be a valid country code.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @see CountryCodeConstraintValidator
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
                CountryCodeConstraintValidator.class
        }
)
@SelfDocumented
public @interface CountryCode {

    /**
     * Allows to disable validation rule if validation inherited from super class.
     * By default, disable is off
     *
     * @return {@code true} if the validation must be disabled,
     * {@code false} otherwise
     */
    boolean off() default false;

    Format format() default Format.ISO_3166_1_ALPHA_2;

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    enum Format {

        @ReadMore(
                caption = "What is ISO 3166-1 alpha2?",
                link = "https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2"
        )
        ISO_3166_1_ALPHA_2("Two-letter country code according to ISO 3166-1 alpha2 standard."),

        @ReadMore(
                caption = "What is ISO 3166-1 alpha3?",
                link = "https://en.wikipedia.org/wiki/ISO_3166-1_alpha-3"
        )
        ISO_3166_1_ALPHA_3("Three-letter country code according to ISO 3166-1 alpha3 standard."),

        @ReadMore(
                caption = "What is ISO 3166-1 numeric?",
                link = "https://en.wikipedia.org/wiki/ISO_3166-1_numeric"
        )
        ISO_3166_1_NUMERIC("Three-digit country code according to ISO 3166-1 numeric standard.");

        private final String description;

        Format(final String description) {
            this.description = description;
        }

        public String getType() {
            return name().substring(11);
        }

        public String getDescription() {
            return description;
        }

        public ReadMore getReadMore() {
            try {
                return getClass().getDeclaredField(name()).getAnnotation(ReadMore.class);
            } catch (NoSuchFieldException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
