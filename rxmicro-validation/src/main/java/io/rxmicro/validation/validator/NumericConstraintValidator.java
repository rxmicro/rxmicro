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

import java.math.BigDecimal;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.Numeric} constraint
 *
 * @author nedis
 * @since 0.1
 * @see io.rxmicro.validation.constraint.Numeric
 */
public class NumericConstraintValidator implements ConstraintValidator<BigDecimal> {

    private final int expectedPrecision;

    private final int expectedScale;

    public NumericConstraintValidator(final int expectedPrecision,
                                      final int expectedScale) {
        this.expectedPrecision = expectedPrecision;
        this.expectedScale = expectedScale;
    }

    @Override
    public void validate(final BigDecimal actual,
                         final HttpModelType httpModelType,
                         final String modelName) {
        if (actual != null) {
            if (expectedScale != -1 && expectedScale != actual.scale()) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected scale = ?, but actual is ?!",
                        httpModelType, modelName, expectedScale, actual.scale());
            }
            if (expectedPrecision != -1 && expectedPrecision != actual.precision()) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected precision = ?, but actual is ?!",
                        httpModelType, modelName, expectedScale, actual.scale());
            }
        }
    }
}
