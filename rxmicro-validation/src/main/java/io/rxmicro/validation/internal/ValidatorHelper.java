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

package io.rxmicro.validation.internal;

import io.rxmicro.model.ModelType;

import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.Set;

import static io.rxmicro.validation.ConstraintViolations.reportViolation;

/**
 * @author nedis
 * @since 0.7
 */
public final class ValidatorHelper {

    public static <T extends Comparable<T>> void validateMaxValue(final T maxValue,
                                                                  final boolean inclusive,
                                                                  final T actual,
                                                                  final ModelType modelType,
                                                                  final String modelName,
                                                                  final String inclusiveErrorMessageTemplate,
                                                                  final String exclusiveErrorMessageTemplate) {
        if (inclusive) {
            if (actual.compareTo(maxValue) > 0) {
                reportViolation(
                        inclusiveErrorMessageTemplate,
                        modelType, modelName, maxValue, actual
                );
            }
        } else {
            if (actual.compareTo(maxValue) >= 0) {
                reportViolation(
                        exclusiveErrorMessageTemplate,
                        modelType, modelName, maxValue, actual
                );
            }
        }
    }

    public static <T extends Comparable<T>> void validateMinValue(final T minValue,
                                                                  final boolean inclusive,
                                                                  final T actual,
                                                                  final ModelType modelType,
                                                                  final String modelName,
                                                                  final String inclusiveErrorMessageTemplate,
                                                                  final String exclusiveErrorMessageTemplate) {
        if (inclusive) {
            if (actual.compareTo(minValue) < 0) {
                reportViolation(
                        inclusiveErrorMessageTemplate,
                        modelType, modelName, minValue, actual
                );
            }
        } else {
            if (actual.compareTo(minValue) <= 0) {
                reportViolation(
                        exclusiveErrorMessageTemplate,
                        modelType, modelName, minValue, actual
                );
            }
        }
    }

    /**
     * Returns the unmodifiable set of latin letters [a-z] and [A-Z] and digits [0-9].
     *
     * @return the unmodifiable set of latin letters [a-z] and [A-Z] and digits [0-9]
     */
    public static Set<Character> getLatinLettersAndDigits() {
        return Set.of(
                // [a-z]
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                // [A-Z]
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                // [0-9]
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
        );
    }

    public static Duration parseDuration(final String value) {
        try {
            return Duration.parse(value);
        } catch (final DateTimeParseException dateTimeParseException) {
            try {
                return Duration.ofSeconds(Long.parseLong(value));
            } catch (final NumberFormatException ignore) {
                throw dateTimeParseException;
            }
        }
    }

    private ValidatorHelper() {
    }
}
