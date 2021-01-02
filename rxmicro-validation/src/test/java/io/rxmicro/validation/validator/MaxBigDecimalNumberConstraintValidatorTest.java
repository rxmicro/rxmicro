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

import static io.rxmicro.common.util.Formats.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 *
 * @since 0.1
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class MaxBigDecimalNumberConstraintValidatorTest extends AbstractConstraintValidatorTest<BigDecimal> {

    @Override
    ConstraintValidator<BigDecimal> instantiate() {
        return new MaxBigDecimalNumberConstraintValidator("10", true);
    }

    @ParameterizedTest
    @Order(11)
    @CsvSource({
            "5.1,    10,    true",
            "10,     10,    true",
            "10.00,  10,    true",
            "5.1,    10,    false"
    })
    void Should_process_parameter_as_a_valid_one(final String actual,
                                                 final String maxValue,
                                                 final boolean inclusive) {
        assertDoesNotThrow(() -> new MaxBigDecimalNumberConstraintValidator(maxValue, inclusive)
                .validate(new BigDecimal(actual), type, fieldName));
    }

    @ParameterizedTest
    @Order(12)
    @ValueSource(strings = {
            "10",
            "10.0",
            "10.00",
            "10.000"
    })
    void Should_throw_ValidationException_when_numbers_equal_and_inclusive_is_false(final String actual) {
        final ValidationException exception = assertThrows(ValidationException.class, () ->
                new MaxBigDecimalNumberConstraintValidator("10", false)
                        .validate(new BigDecimal(actual), type, fieldName)
        );
        assertEquals(
                format("Invalid parameter \"fieldName\": Expected that value < 10, but actual is ?!", actual),
                exception.getMessage()
        );
    }

    @Test
    @Order(13)
    void Should_throw_ValidationException_when_numbers_not_equal_inclusive_is_true() {
        final ValidationException exception = assertThrows(ValidationException.class, () ->
                new MaxBigDecimalNumberConstraintValidator("10", true)
                        .validate(new BigDecimal("20"), type, fieldName)
        );
        assertEquals(
                "Invalid parameter \"fieldName\": Expected that value <= 10, but actual is 20!",
                exception.getMessage()
        );
    }

    @Test
    @Order(14)
    void Should_throw_ValidationException_when_numbers_not_equal_inclusive_is_false() {
        final ValidationException exception = assertThrows(ValidationException.class, () ->
                new MaxBigDecimalNumberConstraintValidator("10", false)
                        .validate(new BigDecimal("20"), type, fieldName)
        );
        assertEquals(
                "Invalid parameter \"fieldName\": Expected that value < 10, but actual is 20!",
                exception.getMessage()
        );
    }
}
