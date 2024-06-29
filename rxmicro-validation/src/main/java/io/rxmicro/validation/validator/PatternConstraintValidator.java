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

package io.rxmicro.validation.validator;

import io.rxmicro.model.ModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.constraint.Pattern;

import java.util.Collection;

import static io.rxmicro.validation.ConstraintViolations.reportViolation;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.Pattern} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.Pattern
 * @since 0.1
 */
public class PatternConstraintValidator implements ConstraintValidator<String> {

    private final java.util.regex.Pattern pattern;

    private final String regexp;

    /**
     * Creates the default instance of {@link PatternConstraintValidator} with the specified parameters.
     *
     * @param regex the specified regular expression
     * @param flags the match flags
     * @see java.util.regex.Pattern#UNIX_LINES
     * @see java.util.regex.Pattern#CASE_INSENSITIVE
     * @see java.util.regex.Pattern#COMMENTS
     * @see java.util.regex.Pattern#MULTILINE
     * @see java.util.regex.Pattern#DOTALL
     * @see java.util.regex.Pattern#UNICODE_CASE
     * @see java.util.regex.Pattern#CANON_EQ
     */
    @SuppressWarnings("MagicConstant")
    public PatternConstraintValidator(final String regex,
                                      final Collection<Pattern.Flag> flags) {
        this.pattern = java.util.regex.Pattern.compile(
                regex,
                flags.stream().map(Pattern.Flag::getValue).reduce((f1, f2) -> f1 | f2).orElse(0)
        );
        this.regexp = regex;
    }

    @Override
    public void validateNonNull(final String actual,
                                final ModelType modelType,
                                final String modelName) {
        if (!pattern.matcher(actual).matches()) {
            reportViolation(
                    "Invalid ? \"?\": Expected that 'value' matches the pattern: /?/, where 'value' is '?'!",
                    modelType, modelName, regexp, actual
            );
        }
    }
}
