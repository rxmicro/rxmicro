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
import io.rxmicro.validation.validator.PatternConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * The annotated {@code String} must match the specified regular expression.
 * The regular expression follows the Java regular expression conventions
 * see {@link java.util.regex.Pattern}.
 *
 * @author nedis
 * @see java.util.regex.Pattern
 * @see PatternConstraintValidator
 * @since 0.1
 */
@Documented
@Retention(CLASS)
@Target({FIELD, METHOD, PARAMETER})
@ConstraintRule(
        supportedTypes = String.class,
        validatorClass = PatternConstraintValidator.class
)
@SelfDocumented
@ReadMore(
        caption = "What is regular expressions?",
        link = "https://www.regular-expressions.info/"
)
@ConstraintParametersOrder({
        "regexp",
        "flags"
})
public @interface Pattern {

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
     * Returns the regular expression to match.
     *
     * @return the regular expression to match
     */
    String regexp();

    /**
     * Returns the array of {@code Flag}s considered when resolving the regular expression.
     *
     * @return the array of {@code Flag}s considered when resolving the regular expression
     */
    Flag[] flags() default {};

    /**
     * Possible Regexp flags.
     */
    enum Flag {

        /**
         * Enables Unix lines mode.
         *
         * @see java.util.regex.Pattern#UNIX_LINES
         */
        UNIX_LINES(java.util.regex.Pattern.UNIX_LINES),

        /**
         * Enables case-insensitive matching.
         *
         * @see java.util.regex.Pattern#CASE_INSENSITIVE
         */
        CASE_INSENSITIVE(java.util.regex.Pattern.CASE_INSENSITIVE),

        /**
         * Permits whitespace and comments in pattern.
         *
         * @see java.util.regex.Pattern#COMMENTS
         */
        COMMENTS(java.util.regex.Pattern.COMMENTS),

        /**
         * Enables multiline mode.
         *
         * @see java.util.regex.Pattern#MULTILINE
         */
        MULTILINE(java.util.regex.Pattern.MULTILINE),

        /**
         * Enables dotall mode.
         *
         * @see java.util.regex.Pattern#DOTALL
         */
        DOTALL(java.util.regex.Pattern.DOTALL),

        /**
         * Enables Unicode-aware case folding.
         *
         * @see java.util.regex.Pattern#UNICODE_CASE
         */
        UNICODE_CASE(java.util.regex.Pattern.UNICODE_CASE),

        /**
         * Enables canonical equivalence.
         *
         * @see java.util.regex.Pattern#CANON_EQ
         */
        CANON_EQ(java.util.regex.Pattern.CANON_EQ);

        private final int value;

        Flag(final int value) {
            this.value = value;
        }

        /**
         * Returns the flag value as defined in {@link java.util.regex.Pattern}.
         *
         * @return the flag value as defined in {@link java.util.regex.Pattern}
         */
        public int getValue() {
            return value;
        }
    }
}
