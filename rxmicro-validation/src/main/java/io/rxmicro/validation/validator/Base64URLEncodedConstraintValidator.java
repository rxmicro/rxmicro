/*
 * Copyright (c) 2020. http://rxmicro.io
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
 * @link http://rxmicro.io
 * @see io.rxmicro.validation.constraint.Base64URLEncoded
 * @since 0.1
 */
public class Base64URLEncodedConstraintValidator implements ConstraintValidator<String> {

    private static final String ALPHABET_BASE = new String(new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    });

    private static final String ALPHABET_URL = new String(new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
    });

    private final Base64URLEncoded.Alphabet alphabet;

    public Base64URLEncodedConstraintValidator(final Base64URLEncoded.Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public void validate(final String actual,
                         final HttpModelType httpModelType,
                         final String modelName) throws ValidationException {
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
