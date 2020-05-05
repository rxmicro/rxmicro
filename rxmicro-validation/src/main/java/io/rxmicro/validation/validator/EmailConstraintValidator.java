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
 * Valid email format: ${username}@${domain}, so example of valid email with min length is: a@b
 *
 * @author nedis
 * @link https://rxmicro.io
 * @see io.rxmicro.validation.constraint.Email
 * @since 0.1
 */
public final class EmailConstraintValidator implements ConstraintValidator<String> {

    @Override
    public void validate(final String actual,
                         final HttpModelType httpModelType,
                         final String modelName) {
        if (actual != null) {
            // Min valid length is 3: a@b
            final boolean notValidEmail = actual.length() < 3 ||
                    // Email invalid if it starts with '@' (without username)
                    actual.charAt(0) == '@' ||
                    // Email invalid if it ends with '@' (without domain)
                    actual.charAt(actual.length() - 1) == '@' ||
                    // Email invalid if it does not contain '@' separator
                    actual.indexOf('@') == -1;
            if (notValidEmail) {
                throw new ValidationException("Invalid ? \"?\": Expected a valid email format!", httpModelType, modelName);
            }
        }
    }
}
