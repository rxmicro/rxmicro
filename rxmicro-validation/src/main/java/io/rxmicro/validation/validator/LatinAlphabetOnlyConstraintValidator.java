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
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class LatinAlphabetOnlyConstraintValidator implements ConstraintValidator<String> {

    private final String alphabet;

    public LatinAlphabetOnlyConstraintValidator(final boolean allowsUppercase,
                                                final boolean allowsLowercase,
                                                final boolean allowsDigits,
                                                final String punctuations) {
        this.alphabet = LatinAlphabetHelper.buildLatinAlphabet(
                allowsUppercase, allowsLowercase, allowsDigits, punctuations
        );
    }

    @Override
    public void validate(final String actual,
                         final HttpModelType httpModelType,
                         final String modelName) {
        if (actual != null) {
            for (int i = 0; i < actual.length(); i++) {
                final char ch = actual.charAt(i);
                if (alphabet.indexOf(ch) == -1) {
                    throw new ValidationException("Invalid ? \"?\": " +
                            "Expected a string which contains the following characters only [?], " +
                            "but actual value contains invalid character: '?'!",
                            httpModelType, modelName, alphabet, ch);
                }
            }
        }
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    private static final class LatinAlphabetHelper {

        private static String buildLatinAlphabet(final boolean allowsUppercase,
                                                 final boolean allowsLowercase,
                                                 final boolean allowsDigits,
                                                 final String punctuations) {
            final StringBuilder builder = new StringBuilder(69);
            if (allowsUppercase) {
                builder.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            }
            if (allowsLowercase) {
                builder.append("abcdefghijklmnopqrstuvwxyz");
            }
            if (allowsDigits) {
                builder.append("1234567890");
            }
            for (int i = 0; i < punctuations.length(); i++) {
                final char ch = punctuations.charAt(i);
                if (builder.indexOf(String.valueOf(ch)) == -1) {
                    builder.append(ch);
                }
            }
            return builder.toString();
        }
    }
}
