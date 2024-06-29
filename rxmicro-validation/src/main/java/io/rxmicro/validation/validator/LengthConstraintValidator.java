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
 * Validator for the {@link io.rxmicro.validation.constraint.Length} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.Length
 * @since 0.1
 */
public class LengthConstraintValidator implements ConstraintValidator<String> {

    private final int expectedLength;

    /**
     * Creates the default instance of {@link LengthConstraintValidator} with the specified string length.
     *
     * @param expectedLength the specified string length
     */
    public LengthConstraintValidator(final int expectedLength) {
        this.expectedLength = expectedLength;
    }

    @Override
    public void validateNonNull(final String value,
                                final ModelType modelType,
                                final String modelName) {
        final int actual = value.length();
        if (expectedLength != actual) {
            reportViolation(
                    "Invalid ? \"?\": Expected ? characters, but actual is ?!",
                    modelType, modelName, expectedLength, actual
            );
        }
    }
}
