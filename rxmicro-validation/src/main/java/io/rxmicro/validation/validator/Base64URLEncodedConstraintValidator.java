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
import io.rxmicro.validation.constraint.Base64URLEncoded;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.rxmicro.validation.ConstraintViolations.reportViolation;
import static io.rxmicro.validation.internal.ValidatorHelper.getLatinLettersAndDigits;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.Base64URLEncoded} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.Base64URLEncoded
 * @since 0.1
 */
public class Base64URLEncodedConstraintValidator implements ConstraintValidator<String> {

    /**
     * This array is a lookup table that translates {@code 6}-bit positive integer index values into their {@code "Base64 Alphabet"}
     * equivalents as specified in {@code "Table 1: The Base64 Alphabet"} of {@code RFC 2045} (and {@code RFC 4648}).
     */
    private static final Set<Character> ALPHABET_BASE =
            Stream.concat(
                    getLatinLettersAndDigits().stream(),
                    Stream.of('+', '/')
            ).collect(Collectors.toUnmodifiableSet());

    private static final String ALPHABET_BASE_SORTED_CHARACTERS = ALPHABET_BASE.stream()
            .sorted()
            .map(String::valueOf)
            .collect(Collectors.joining(", "));

    /**
     * It's the lookup table for {@code "URL and Filename safe Base64"} as specified in Table 2 of the {@code RFC 4648}, with the
     * {@code '+'} and {@code '/'} changed to {@code '-'} and {@code '_'}. This table is used when {@code BASE64_URL} is specified.
     */
    private static final Set<Character> ALPHABET_URL =
            Stream.concat(
                    getLatinLettersAndDigits().stream(),
                    Stream.of('-', '_')
            ).collect(Collectors.toUnmodifiableSet());

    private static final String ALPHABET_URL_SORTED_CHARACTERS = ALPHABET_URL.stream()
            .sorted()
            .map(String::valueOf)
            .collect(Collectors.joining(", "));

    private final Base64URLEncoded.Alphabet alphabet;

    /**
     * Creates the default instance of {@link Base64URLEncodedConstraintValidator} with the specified alphabet.
     *
     * @param alphabet the specified alphabet
     */
    public Base64URLEncodedConstraintValidator(final Base64URLEncoded.Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public void validateNonNull(final String actual,
                                final ModelType modelType,
                                final String modelName) {
        if (alphabet == Base64URLEncoded.Alphabet.BASE) {
            validate(actual, modelType, modelName, ALPHABET_BASE, ALPHABET_BASE_SORTED_CHARACTERS);
        } else {
            validate(actual, modelType, modelName, ALPHABET_URL, ALPHABET_URL_SORTED_CHARACTERS);
        }
    }

    private void validate(final CharSequence value,
                          final ModelType modelType,
                          final String modelName,
                          final Set<Character> alphabet,
                          final String allowedChars) {
        for (int i = 0; i < value.length(); i++) {
            final char ch = value.charAt(i);
            if (!alphabet.contains(ch)) {
                reportViolation("Invalid ? \"?\": Expected a valid Base64 string, i.e. " +
                                "string which contains the following characters only [?], " +
                                "but actual value contains invalid character: '?' (0x?)!",
                        modelType, modelName, allowedChars, ch, Integer.toHexString(ch)
                );
            }
        }
    }
}
