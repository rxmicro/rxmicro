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

package io.rxmicro.rest.local;

import io.rxmicro.http.error.ValidationException;
import io.rxmicro.rest.model.HttpModelType;

/**
 * @author nedis
 * @since 0.7
 */
public abstract class AbstractValidatedConverter {

    protected final ValidationException createExpectedIntegerValidationException(final String value,
                                                                                 final HttpModelType httpModelType,
                                                                                 final String modelName,
                                                                                 final Object minValue,
                                                                                 final Object maxValue) {
        if (value.isEmpty()) {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected an integer value, but actual is '?'!",
                    httpModelType, modelName, value
            );
        } else {
            final boolean isNegative = value.charAt(0) == '-';
            for (int i = isNegative || value.charAt(0) == '+' ? 1 : 0; i < value.length(); i++) {
                if (!Character.isDigit(value.charAt(i))) {
                    throwValidationExceptionIfValueContainsNotDigitsOnly(value, httpModelType, modelName, minValue, maxValue);
                }
            }
            final String sign = isNegative ? ">=" : "<=";
            final Object criticalValue = isNegative ? minValue : maxValue;
            throw new ValidationException(
                    "Invalid ? \"?\": Expected an integer value that ? '?', but actual is '?'!",
                    httpModelType, modelName, sign, criticalValue, value
            );
        }
    }

    private void throwValidationExceptionIfValueContainsNotDigitsOnly(final String value,
                                                                      final HttpModelType httpModelType,
                                                                      final String modelName,
                                                                      final Object minValue,
                                                                      final Object maxValue) {
        if ("NaN".equals(value) || "+NaN".equals(value) || "-NaN".equals(value)) {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected an integer value, but actual is 'NaN'!",
                    httpModelType, modelName
            );
        } else if ("Infinity".equals(value) || "+Infinity".equals(value)) {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected an integer value that <= '?', but actual is '?'!",
                    httpModelType, modelName, maxValue, value
            );
        } else if ("-Infinity".equals(value)) {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected an integer value that >= '?', but actual is '?'!",
                    httpModelType, modelName, minValue, value
            );
        } else {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected an integer value, but actual is '?'!",
                    httpModelType, modelName, value
            );
        }
    }

    protected final float floatIfValid(final String value,
                                       final float result,
                                       final HttpModelType httpModelType,
                                       final String modelName) {
        if (result == Float.POSITIVE_INFINITY) {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a decimal value that <= '?', but actual is '?'!",
                    httpModelType, modelName, Float.MAX_VALUE, value
            );
        } else if (result == Float.NEGATIVE_INFINITY) {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a decimal value that >= '-?', but actual is '?'!",
                    httpModelType, modelName, Float.MAX_VALUE, value
            );
        } else if (Float.isNaN(result)) {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a decimal value, but actual is 'NaN'!",
                    httpModelType, modelName
            );
        } else {
            return result;
        }
    }

    protected final double doubleIfValid(final String value,
                                         final double result,
                                         final HttpModelType httpModelType,
                                         final String modelName) {
        if (result == Double.POSITIVE_INFINITY) {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a decimal value that <= '?', but actual is '?'!",
                    httpModelType, modelName, Double.MAX_VALUE, value
            );
        } else if (result == Double.NEGATIVE_INFINITY) {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a decimal value that >= '-?', but actual is '?'!",
                    httpModelType, modelName, Double.MAX_VALUE, value
            );
        } else if (Double.isNaN(result)) {
            throw new ValidationException(
                    "Invalid ? \"?\": Expected a decimal value, but actual is 'NaN'!",
                    httpModelType, modelName
            );
        } else {
            return result;
        }
    }
}
