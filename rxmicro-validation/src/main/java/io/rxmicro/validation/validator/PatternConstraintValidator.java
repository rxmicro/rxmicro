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

import io.rxmicro.http.error.ValidationException;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.constraint.Pattern;

import java.util.Collection;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @see io.rxmicro.validation.constraint.Pattern
 * @since 0.1
 */
public class PatternConstraintValidator implements ConstraintValidator<String> {

    private final java.util.regex.Pattern pattern;

    private final String regexp;

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
    public void validate(final String actual,
                         final HttpModelType httpModelType,
                         final String modelName) throws ValidationException {
        if (actual != null) {
            if (!pattern.matcher(actual).matches()) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected that 'value' matches the pattern: /?/, where 'value' is '?'!",
                        httpModelType, modelName, regexp, actual);
            }
        }
    }
}
