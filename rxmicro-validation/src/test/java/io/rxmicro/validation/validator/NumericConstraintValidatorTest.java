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
        return new NumericConstraintValidator(-1, 2);
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
        final NumericConstraintValidator validator = new NumericConstraintValidator(-1, -1);
        assertDoesNotThrow(() -> validator.validate(new BigDecimal(value), PARAMETER, "value"));
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "-1;    2;   0;         Expected scale = 2, but actual is 0!",
            "-1;    2;   0.;        Expected scale = 2, but actual is 0!",
            "-1;    2;   0.1;       Expected scale = 2, but actual is 1!",
            "-1;    2;   0.123;     Expected scale = 2, but actual is 3!",

            "2;    -1;   0;         Expected precision = 2, but actual is 1!",
            "2;    -1;   0.;        Expected precision = 2, but actual is 1!",
            "2;    -1;   9.12;      Expected precision = 2, but actual is 3!",
            "2;    -1;   123;       Expected precision = 2, but actual is 3!",
            "2;    -1;   123.;      Expected precision = 2, but actual is 3!",
            "2;    -1;   123.4;     Expected precision = 2, but actual is 4!",

            "4;     2;   123.4;     Expected scale = 2, but actual is 1!",
            "4;     2;   1.234;     Expected scale = 2, but actual is 3!",
            "4;     2;   12345;     Expected scale = 2, but actual is 0!",
            "4;     2;   123.45;    Expected precision = 4, but actual is 5!"
    })
    @Order(13)
    void Should_throw_ValidationException(final int expectedPrecision,
                                          final int expectedScale,
                                          final String value,
                                          final String details) {
        final NumericConstraintValidator validator = new NumericConstraintValidator(expectedPrecision, expectedScale);
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> validator.validate(new BigDecimal(value), PARAMETER, "value"));
        assertEquals(
                "Invalid parameter \"value\": " + details,
                exception.getMessage()
        );
    }
}