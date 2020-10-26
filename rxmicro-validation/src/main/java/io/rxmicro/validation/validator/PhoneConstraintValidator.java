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

/**
 * Validator for the {@link io.rxmicro.validation.constraint.Phone} constraint.
 *
 * <p>
 * Read more:
 * <a href="https://en.wikipedia.org/wiki/National_conventions_for_writing_telephone_numbers">
 *     https://en.wikipedia.org/wiki/National_conventions_for_writing_telephone_numbers
 * </a>
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.Phone
 * @since 0.1
 */
public class PhoneConstraintValidator implements ConstraintValidator<String> {

    private final boolean withoutPlus;

    private final boolean allowsSpaces;

    /**
     * Creates the default instance of {@link PhoneConstraintValidator} with the specified parameters.
     *
     * @param withoutPlus value must be without plus or not
     * @param allowsSpaces allows whitespaces or not
     */
    public PhoneConstraintValidator(final boolean withoutPlus,
                                    final boolean allowsSpaces) {
        this.withoutPlus = withoutPlus;
        this.allowsSpaces = allowsSpaces;
    }

    @Override
    public void validate(final String actual,
                         final HttpModelType httpModelType,
                         final String modelName) {
        if (actual != null && actual.length() > 0) {
            final String validValue = allowsSpaces ? actual.trim() : actual;
            validatePlusChar(validValue, httpModelType, modelName, withoutPlus);
            validateDigitsOnly(validValue, httpModelType, modelName);
        }
    }

    private void validatePlusChar(final CharSequence phone,
                                  final HttpModelType httpModelType,
                                  final String fieldName,
                                  final boolean withoutPlus) {
        if (withoutPlus) {
            if (phone.charAt(0) == '+') {
                throw new ValidationException("Invalid ? \"?\": Expected a digit as a first character, but actual is '+'!",
                        httpModelType, fieldName
                );
            }
        } else {
            if (phone.charAt(0) != '+') {
                throw new ValidationException("Invalid ? \"?\": Expected a plus as a first character, but actual is '?'!",
                        httpModelType, fieldName, phone.charAt(0)
                );
            }
        }
    }

    private void validateDigitsOnly(final CharSequence phone,
                                    final HttpModelType httpModelType,
                                    final String fieldName) {
        for (int i = withoutPlus ? 0 : 1; i < phone.length(); i++) {
            final char ch = phone.charAt(i);
            if (!Character.isDigit(ch)) {
                if (allowsSpaces && Character.isWhitespace(ch)) {
                    continue;
                }
                throw new ValidationException("Invalid ? \"?\": " +
                        "Expected digits only, but actual is '?' and contains invalid character: '?' (0x?)!",
                        httpModelType, fieldName, phone, ch, Integer.toHexString(ch)
                );
            }
        }
    }
}
