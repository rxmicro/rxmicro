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

import static io.rxmicro.validation.ConstraintViolations.reportViolation;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.DigitsOnly} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.DigitsOnly
 * @since 0.1
 */
public class DigitsOnlyConstraintValidator implements ConstraintValidator<String> {

    @Override
    public void validateNonNull(final String actual,
                                final ModelType modelType,
                                final String modelName) {
        for (int i = 0; i < actual.length(); i++) {
            final char ch = actual.charAt(i);
            if (ch < '0' || ch > '9') {
                reportViolation("Invalid ? \"?\": " +
                                "Expected a string with digits only, " +
                                "but actual value contains invalid character: '?' (0x?)!",
                        modelType, modelName, ch, Integer.toHexString(ch));
            }
        }
    }
}
