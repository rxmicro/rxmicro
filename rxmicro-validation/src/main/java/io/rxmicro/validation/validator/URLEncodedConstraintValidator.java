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

/**
 * @author nedis
 * @link http://rxmicro.io
 * @see io.rxmicro.validation.constraint.URLEncoded
 * @since 0.1
 */
public class URLEncodedConstraintValidator implements ConstraintValidator<String> {

    @Override
    public void validate(final String actual,
                         final HttpModelType httpModelType,
                         final String modelName) throws ValidationException {
        if (actual != null) {
            if (isNotAllASCII(actual)) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected a URL encoded string, " +
                                "where all characters are between 0x00 and 0x7F (ASCII), but actual is '?'!",
                        httpModelType, modelName, actual);
            }
        }
    }

    private boolean isNotAllASCII(final CharSequence input) {
        for (int i = 0; i < input.length(); i++) {
            final int c = input.charAt(i);
            if (c > 0x7F) {
                return true;
            }
        }
        return false;
    }
}
