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
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.constraint.Numeric;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static io.rxmicro.rest.model.HttpModelType.PARAMETER;
import static io.rxmicro.validation.constraint.Numeric.ValidationType.EXACT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class NumericConstraintValidatorTest extends AbstractConstraintValidatorTest<BigDecimal> {

    @Override
    ConstraintValidator<BigDecimal> instantiate() {
        return new NumericConstraintValidator(-1, 2, EXACT);
    }

    @Test
    @Order(11)
    void Should_process_parameter_as_a_valid_one() {
        assertDoesNotThrow(() -> validator.validate(new BigDecimal("0.23"), PARAMETER, "value"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0",
            "0.0",
            "0.000000000",
            "1234567890",
            "1234567890.0",
            "1234567890.1234567890"
    })
    @Order(12)
    void Should_ignore_any_value(final String value) {
        final NumericConstraintValidator validator = new NumericConstraintValidator(-1, -1, EXACT);
        assertDoesNotThrow(() -> validator.validate(new BigDecimal(value), PARAMETER, "value"));
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "-1;    2;   0.12;      EXACT",
            "-1;    2;   5.23;      EXACT",
            "-1;    2;   0.01;      EXACT",
            "-1;    2;   12.34;     EXACT",

            "-1;    2;   0.12;      MIN_SUPPORTED",
            "-1;    2;   5.233;     MIN_SUPPORTED",
            "-1;    2;   0.0002;    MIN_SUPPORTED",
            "-1;    2;   12.346;    MIN_SUPPORTED",

            "-1;    2;   100.11;    MAX_SUPPORTED",
            "-1;    2;   100.1;     MAX_SUPPORTED",
            "-1;    2;   100.00;    MAX_SUPPORTED",
            "-1;    2;   100.0;     MAX_SUPPORTED",
            "-1;    2;   100;       MAX_SUPPORTED",
            "-1;    2;   0;         MAX_SUPPORTED",
            "-1;    2;   1000;      MAX_SUPPORTED",
            "-1;    2;   10000;     MAX_SUPPORTED",
            "-1;    2;   100000;    MAX_SUPPORTED"
    })
    @Order(13)
    void Should_not_throw_ValidationException(final int expectedPrecision,
                                              final int expectedScale,
                                              final String value,
                                              final Numeric.ValidationType validationType) {
        final NumericConstraintValidator validator = new NumericConstraintValidator(expectedPrecision, expectedScale, validationType);
        assertDoesNotThrow(() -> validator.validate(new BigDecimal(value), PARAMETER, "value"));
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "-1;    2;   0;         Expected scale = 2, but actual is 0!;       EXACT",
            "-1;    2;   0.;        Expected scale = 2, but actual is 0!;       EXACT",
            "-1;    2;   0.1;       Expected scale = 2, but actual is 1!;       EXACT",
            "-1;    2;   0.123;     Expected scale = 2, but actual is 3!;       EXACT",

            "2;    -1;   0;         Expected precision = 2, but actual is 1!;   EXACT",
            "2;    -1;   0.;        Expected precision = 2, but actual is 1!;   EXACT",
            "2;    -1;   9.12;      Expected precision = 2, but actual is 3!;   EXACT",
            "2;    -1;   123;       Expected precision = 2, but actual is 3!;   EXACT",
            "2;    -1;   123.;      Expected precision = 2, but actual is 3!;   EXACT",
            "2;    -1;   123.4;     Expected precision = 2, but actual is 4!;   EXACT",

            "4;     2;   123.4;     Expected scale = 2, but actual is 1!;       EXACT",
            "4;     2;   1.234;     Expected scale = 2, but actual is 3!;       EXACT",
            "4;     2;   12345;     Expected scale = 2, but actual is 0!;       EXACT",
            "4;     2;   123.45;    Expected precision = 4, but actual is 5!;   EXACT",

            "-1;    2;   0;         Min supported scale = 2, but actual is 0!;  MIN_SUPPORTED",
            "-1;    2;   0.;        Min supported scale = 2, but actual is 0!;  MIN_SUPPORTED",
            "-1;    2;   0.1;       Min supported scale = 2, but actual is 1!;  MIN_SUPPORTED",
            "-1;    2;   0.2;       Min supported scale = 2, but actual is 1!;  MIN_SUPPORTED",

            "-1;    2;   0.123;     Max supported scale = 2, but actual is 3!;  MAX_SUPPORTED",
            "-1;    2;   0.1234;    Max supported scale = 2, but actual is 4!;  MAX_SUPPORTED",
            "-1;    2;   0.12345;   Max supported scale = 2, but actual is 5!;  MAX_SUPPORTED",
            "-1;    2;   0.123456;  Max supported scale = 2, but actual is 6!;  MAX_SUPPORTED",

            "3;    -1;   0;         Min supported precision = 3, but actual is 1!;   MIN_SUPPORTED",
            "3;    -1;   0.;        Min supported precision = 3, but actual is 1!;   MIN_SUPPORTED",
            "3;    -1;   9.1;       Min supported precision = 3, but actual is 2!;   MIN_SUPPORTED",
            "3;    -1;   12;        Min supported precision = 3, but actual is 2!;   MIN_SUPPORTED",

            "3;    -1;   1.001;     Max supported precision = 3, but actual is 4!;   MAX_SUPPORTED",
            "3;    -1;   1.0001;    Max supported precision = 3, but actual is 5!;   MAX_SUPPORTED",
            "3;    -1;   1234;      Max supported precision = 3, but actual is 4!;   MAX_SUPPORTED",
            "3;    -1;   12345;     Max supported precision = 3, but actual is 5!;   MAX_SUPPORTED"
    })
    @Order(14)
    void Should_throw_ValidationException(final int expectedPrecision,
                                          final int expectedScale,
                                          final String value,
                                          final String details,
                                          final Numeric.ValidationType validationType) {
        final NumericConstraintValidator validator = new NumericConstraintValidator(expectedPrecision, expectedScale, validationType);
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> validator.validate(new BigDecimal(value), PARAMETER, "value"));
        assertEquals(
                "Invalid parameter \"value\": " + details,
                exception.getMessage()
        );
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "-1;    2",
            "2;    -1",
            "2;     2"
    })
    @Order(15)
    void Should_throw_UnsupportedOperationException(final int expectedPrecision,
                                                    final int expectedScale) {
        final NumericConstraintValidator validator = new NumericConstraintValidator(expectedPrecision, expectedScale, null);
        final UnsupportedOperationException exception =
                assertThrows(UnsupportedOperationException.class, () -> validator.validate(BigDecimal.ZERO, PARAMETER, "value"));
        assertEquals("Validation type is unsupported: null", exception.getMessage());
    }
}
