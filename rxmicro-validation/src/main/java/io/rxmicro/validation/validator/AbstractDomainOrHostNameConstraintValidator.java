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
import io.rxmicro.validation.ConstraintViolations;

import java.util.Set;
import java.util.stream.Stream;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Strings.capitalize;
import static io.rxmicro.validation.internal.ValidatorHelper.getLatinLettersAndDigits;
import static java.util.stream.Collectors.toUnmodifiableSet;

/**
 * Base validator for the {@link io.rxmicro.validation.constraint.DomainName} and the {@link io.rxmicro.validation.constraint.HostName}
 * constraints.
 *
 * @author nedis
 * @since 0.4
 */
public abstract class AbstractDomainOrHostNameConstraintValidator implements ConstraintValidator<String> {

    private static final Set<Character> ALLOWED_CHARACTERS =
            Stream.concat(
                    getLatinLettersAndDigits().stream(),
                    // underscores, periods, and dashes
                    Stream.of('-', '.', '_')
            ).collect(toUnmodifiableSet());

    private final boolean errorWithDetails;

    /**
     * Creates the default instance of {@link AbstractDomainOrHostNameConstraintValidator} type.
     *
     * @param errorWithDetails validation error with detail message or not
     */
    protected AbstractDomainOrHostNameConstraintValidator(final boolean errorWithDetails) {
        this.errorWithDetails = errorWithDetails;
    }

    /**
     * Returns the rule.
     *
     * @return the rule
     */
    protected abstract String getRule();

    /**
     * Returns the name.
     *
     * @return the name
     */
    protected abstract String getName();

    @Override
    public void validateNonNull(final String actual,
                                final ModelType modelType,
                                final String modelName) {
        validateActual(actual, modelType, modelName, actual.length() - 1);
    }

    private void validateActual(final String actual,
                                final ModelType modelType,
                                final String modelName,
                                final int lastIndex) {
        int lastPeriodIndex = -1;
        for (int i = 0; i <= lastIndex; i++) {
            final char ch = actual.charAt(i);
            if (ALLOWED_CHARACTERS.contains(ch)) {
                if (ch == '.') {
                    lastPeriodIndex = i;
                    validateDelimiterCharacters(actual, modelType, modelName, lastIndex, i, ch);
                } else if (ch == '-' || ch == '_') {
                    validateDelimiterCharacters(actual, modelType, modelName, lastIndex, i, ch);
                }
            } else {
                final String details = format("Unsupported ? character: '?'. ?", getName(), ch, getRule());
                reportViolation(modelType, modelName, details);
            }
        }
        if (lastPeriodIndex == -1) {
            final String message = format("? must contain at least two levels!", getName());
            reportViolation(modelType, modelName, message);
        } else if (lastIndex - lastPeriodIndex < 2) {
            final String message = format("The last portion of the ? must be at least two characters!", getName());
            reportViolation(modelType, modelName, message);
        }
    }

    private void validateDelimiterCharacters(final String actual,
                                             final ModelType modelType,
                                             final String modelName,
                                             final int lastIndex,
                                             final int index,
                                             final char ch) {
        if (index == 0) {
            reportViolation(modelType, modelName, format("? can't start with '?'!", getName(), ch));
        } else if (index == lastIndex) {
            reportViolation(modelType, modelName, format("? can't end with '?'!", getName(), ch));
        } else {
            final char prev = actual.charAt(index - 1);
            if (prev == '.' || prev == '_' || prev == '-') {
                reportViolation(modelType, modelName, format("? contains redundant character: '?'!", getName(), ch));
            }
        }
    }

    private void reportViolation(final ModelType modelType,
                                 final String modelName,
                                 final String details) {
        final String errorMessage;
        if (errorWithDetails) {
            errorMessage = format("Invalid ? \"?\": ?", modelType, modelName, capitalize(details));
        } else {
            errorMessage = format("Invalid ? \"?\": Expected a valid ?!", modelType, modelName, getName());
        }
        ConstraintViolations.reportViolation(errorMessage);
    }
}
