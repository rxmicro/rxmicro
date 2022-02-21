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

import java.util.Set;
import java.util.stream.Stream;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Strings.capitalize;
import static io.rxmicro.validation.base.ConstraintUtils.getLatinLettersAndDigits;
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
    public final void validateNonNull(final String actual,
                                      final HttpModelType httpModelType,
                                      final String modelName) {
        final int lastIndex = actual.length() - 1;
        if (lastIndex >= 0) {
            // If actual is not empty string
            validateActual(actual, httpModelType, modelName, lastIndex);
        }
    }

    private void validateActual(final String actual,
                                final HttpModelType httpModelType,
                                final String modelName,
                                final int lastIndex) {
        int lastPeriodIndex = -1;
        for (int i = 0; i <= lastIndex; i++) {
            final char ch = actual.charAt(i);
            if (!ALLOWED_CHARACTERS.contains(ch)) {
                final String details = format("Unsupported ? character: '?'. ?", getName(), ch, getRule());
                throwException(httpModelType, modelName, details);
            } else if (ch == '.') {
                lastPeriodIndex = i;
                validateDelimiterCharacters(actual, httpModelType, modelName, lastIndex, i, ch);
            } else if (ch == '-' || ch == '_') {
                validateDelimiterCharacters(actual, httpModelType, modelName, lastIndex, i, ch);
            }
        }
        if (lastPeriodIndex == -1) {
            final String message = format("? must contain at least two levels!", getName());
            throwException(httpModelType, modelName, message);
        } else if (lastIndex - lastPeriodIndex < 2) {
            final String message = format("The last portion of the ? must be at least two characters!", getName());
            throwException(httpModelType, modelName, message);
        }
    }

    private void validateDelimiterCharacters(final String actual,
                                             final HttpModelType httpModelType,
                                             final String modelName,
                                             final int lastIndex,
                                             final int index,
                                             final char ch) {
        if (index == 0) {
            throwException(httpModelType, modelName, format("? can't start with '?'!", getName(), ch));
        } else if (index == lastIndex) {
            throwException(httpModelType, modelName, format("? can't end with '?'!", getName(), ch));
        } else {
            final char prev = actual.charAt(index - 1);
            if (prev == '.' || prev == '_' || prev == '-') {
                throwException(httpModelType, modelName, format("? contains redundant character: '?'!", getName(), ch));
            }
        }
    }

    private void throwException(final HttpModelType httpModelType,
                                final String modelName,
                                final String details) {
        final String errorMessage;
        if (errorWithDetails) {
            errorMessage = format("Invalid ? \"?\": ?", httpModelType, modelName, capitalize(details));
        } else {
            errorMessage = format("Invalid ? \"?\": Expected a valid ?!", httpModelType, modelName, getName());
        }
        throw new ValidationException(errorMessage);
    }
}
