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
import io.rxmicro.validation.constraint.Base64URLEncoded;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @see io.rxmicro.validation.constraint.Base64URLEncoded
 * @since 0.1
 */
public class Base64URLEncodedConstraintValidator implements ConstraintValidator<String> {

    private static final String BASIC_ALPHABET = new String(new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    });

    /**
     * This array is a lookup table that translates 6-bit positive integer
     * index values into their "Base64 Alphabet" equivalents as specified
     * in "Table 1: The Base64 Alphabet" of RFC 2045 (and RFC 4648).
     */
    private static final String ALPHABET_BASE = BASIC_ALPHABET + new String(new char[]{'+', '/'});

    /**
     * It's the lookup table for "URL and Filename safe Base64" as specified
     * in Table 2 of the RFC 4648, with the '+' and '/' changed to '-' and
     * '_'. This table is used when BASE64_URL is specified.
     */
    private static final String ALPHABET_URL = BASIC_ALPHABET + new String(new char[]{'-', '_'});

    private final Base64URLEncoded.Alphabet alphabet;

    public Base64URLEncodedConstraintValidator(final Base64URLEncoded.Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public void validate(final String actual,
                         final HttpModelType httpModelType,
                         final String modelName) {
        if (actual != null) {
            if (alphabet == Base64URLEncoded.Alphabet.BASE) {
                validate(actual, httpModelType, modelName, ALPHABET_BASE);
            } else {
                validate(actual, httpModelType, modelName, ALPHABET_URL);
            }
        }
    }

    private void validate(final CharSequence value,
                          final HttpModelType httpModelType,
                          final String fieldName,
                          final String alphabet) {
        for (int i = 0; i < value.length(); i++) {
            final char ch = value.charAt(i);
            if (alphabet.indexOf(ch) == -1) {
                throw new ValidationException("Invalid ? \"?\": Expected a valid Base64 string, i.e. " +
                        "string which contains the following characters only [?], " +
                        "but actual value contains invalid character: '?'!",
                        httpModelType, fieldName, alphabet, ch);
            }
        }
    }
}
