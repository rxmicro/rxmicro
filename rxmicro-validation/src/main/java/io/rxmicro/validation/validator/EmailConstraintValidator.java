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
import static io.rxmicro.validation.base.ConstraintUtils.getLatinLettersAndDigits;
import static io.rxmicro.validation.validator.DomainNameConstraintValidator.DOMAIN_RULE;
import static java.util.stream.Collectors.toUnmodifiableSet;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.Email} constraint
 *
 * @author nedis
 * @link https://rxmicro.io
 * @see io.rxmicro.validation.constraint.Email
 * @since 0.1
 */
public final class EmailConstraintValidator implements ConstraintValidator<String> {

    public static final String EMAIL_PREFIX_RULE =
            "Prefix must contains letters [a-z] or [A-Z], digits [0-9], underscores, periods, dashes, apostrophe and plus only!";

    public static final String EMAIL_DOMAIN_RULE = DOMAIN_RULE;

    private static final Set<Character> ALLOWED_PREFIX_CHARACTERS =
            Stream.concat(
                    getLatinLettersAndDigits().stream(),
                    Stream.of(
                            // underscores, periods, and dashes
                            '-', '.', '_',
                            // apostrophe and plus
                            '\'', '+'
                    )
            ).collect(toUnmodifiableSet());

    private final boolean errorWithDetails;

    private final DomainNameConstraintValidator domainNameConstraintValidator;

    public EmailConstraintValidator(final boolean errorWithDetails) {
        this.errorWithDetails = errorWithDetails;
        this.domainNameConstraintValidator = new DomainNameConstraintValidator(errorWithDetails);
    }

    @Override
    public void validate(final String actual,
                         final HttpModelType httpModelType,
                         final String modelName) {
        if (actual != null) {
            validateActual(actual, httpModelType, modelName);
        }
    }

    private void validateActual(final String actual,
                                final HttpModelType httpModelType,
                                final String modelName) {
        boolean atSignDelimiterFound = false;
        final int lastIndex = actual.length() - 1;
        for (int i = 0; i <= lastIndex; i++) {
            final char ch = actual.charAt(i);
            if (ch == '@') {
                validatePrefixAndDomain(actual, httpModelType, modelName, lastIndex, i);
                atSignDelimiterFound = true;
                break;
            } else if (!ALLOWED_PREFIX_CHARACTERS.contains(ch)) {
                final String details = format("Unsupported prefix character: '?'. ?", ch, EMAIL_PREFIX_RULE);
                throwException(httpModelType, modelName, details);
            } else if (ch == '.' || ch == '-' || ch == '_' || ch == '\'' || ch == '+') {
                validateDelimiters(actual, httpModelType, modelName, i, ch);
            }
        }
        if (!atSignDelimiterFound) {
            throwException(httpModelType, modelName, "Missing '@'!");
        }
    }

    private void validatePrefixAndDomain(final String actual,
                                         final HttpModelType httpModelType,
                                         final String modelName,
                                         final int lastIndex,
                                         final int index) {
        if (index == 0) {
            throwException(httpModelType, modelName, "Missing prefix!");
        } else if (index == lastIndex) {
            throwException(httpModelType, modelName, "Missing domain!");
        } else {
            final char prev = actual.charAt(index - 1);
            if (prev == '.' || prev == '_' || prev == '-' || prev == '\'' || prev == '+') {
                throwException(httpModelType, modelName, format("Prefix can't end with '?'!", prev));
            } else {
                domainNameConstraintValidator.validate(actual.substring(index + 1), httpModelType, modelName);
            }
        }
    }

    private void validateDelimiters(final String actual,
                                    final HttpModelType httpModelType,
                                    final String modelName,
                                    final int index,
                                    final char ch) {
        if (index == 0) {
            throwException(httpModelType, modelName, format("Prefix can't start with '?'!", ch));
        } else {
            final char prev = actual.charAt(index - 1);
            if (prev == '.' || prev == '-' || prev == '_' || prev == '\'' || prev == '+') {
                throwException(httpModelType, modelName, format("Prefix contains redundant character: '?'!", ch));
            }
        }
    }

    private void throwException(final HttpModelType httpModelType,
                                final String modelName,
                                final String details) {
        final String errorMessage;
        if (errorWithDetails) {
            errorMessage = format("Invalid ? \"?\": ?", httpModelType, modelName, details);
        } else {
            errorMessage = format("Invalid ? \"?\": Expected a valid email format!", httpModelType, modelName);
        }
        throw new ValidationException(errorMessage);
    }
}
