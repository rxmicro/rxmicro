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
import io.rxmicro.validation.constraint.Numeric;

import java.math.BigDecimal;

/**
 * Validator for the {@link io.rxmicro.validation.constraint.Numeric} constraint.
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.Numeric
 * @since 0.1
 */
public class NumericConstraintValidator implements ConstraintValidator<BigDecimal> {

    private final int expectedPrecision;

    private final int expectedScale;

    private final Numeric.ValidationType validationType;

    /**
     * Creates the default instance of {@link NumericConstraintValidator} with the specified precision and scale.
     *
     * @param expectedPrecision the specified precision
     * @param expectedScale the specified scale
     * @param validationType the specified validation type
     */
    public NumericConstraintValidator(final int expectedPrecision,
                                      final int expectedScale,
                                      final Numeric.ValidationType validationType) {
        this.expectedPrecision = expectedPrecision;
        this.expectedScale = expectedScale;
        this.validationType = validationType;
    }

    @Override
    public void validateNonNull(final BigDecimal actual,
                                final HttpModelType httpModelType,
                                final String modelName) {
        if (expectedScale != -1) {
            validateScale(actual, httpModelType, modelName);
        }
        if (expectedPrecision != -1) {
            validatePrecision(actual, httpModelType, modelName);
        }
    }

    private void validateScale(final BigDecimal actual,
                               final HttpModelType httpModelType,
                               final String modelName) {
        if (validationType == Numeric.ValidationType.EXACT) {
            if (expectedScale != actual.scale()) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected scale = ?, but actual is ?!",
                        httpModelType, modelName, expectedScale, actual.scale()
                );
            }
        } else if (validationType == Numeric.ValidationType.MIN_SUPPORTED) {
            if (expectedScale > actual.scale()) {
                throw new ValidationException(
                        "Invalid ? \"?\": Min supported scale = ?, but actual is ?!",
                        httpModelType, modelName, expectedScale, actual.scale()
                );
            }
        } else if (validationType == Numeric.ValidationType.MAX_SUPPORTED) {
            if (expectedScale < actual.scale()) {
                throw new ValidationException(
                        "Invalid ? \"?\": Max supported scale = ?, but actual is ?!",
                        httpModelType, modelName, expectedScale, actual.scale()
                );
            }
        } else {
            throw new UnsupportedOperationException("Validation type is unsupported: " + validationType);
        }
    }

    private void validatePrecision(final BigDecimal actual,
                                   final HttpModelType httpModelType,
                                   final String modelName) {
        if (validationType == Numeric.ValidationType.EXACT) {
            if (expectedPrecision != actual.precision()) {
                throw new ValidationException(
                        "Invalid ? \"?\": Expected precision = ?, but actual is ?!",
                        httpModelType, modelName, expectedPrecision, actual.precision()
                );
            }
        } else if (validationType == Numeric.ValidationType.MIN_SUPPORTED) {
            if (expectedPrecision > actual.precision()) {
                throw new ValidationException(
                        "Invalid ? \"?\": Min supported precision = ?, but actual is ?!",
                        httpModelType, modelName, expectedPrecision, actual.precision()
                );
            }
        } else if (validationType == Numeric.ValidationType.MAX_SUPPORTED) {
            if (expectedPrecision < actual.precision()) {
                throw new ValidationException(
                        "Invalid ? \"?\": Max supported precision = ?, but actual is ?!",
                        httpModelType, modelName, expectedPrecision, actual.precision()
                );
            }
        } else {
            throw new UnsupportedOperationException("Validation type is unsupported: " + validationType);
        }
    }
}
