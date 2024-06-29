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

import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class MaxLengthConstraintValidatorTest extends AbstractNullableConstraintValidatorTest<String> {

    @Override
    ConstraintValidator<String> instantiate() {
        return new MaxLengthConstraintValidator(10, true);
    }

    @Test
    @Order(11)
    void Should_process_parameter_as_a_valid_one() {
        assertDoesNotThrow(() -> validator.validate("us", PARAMETER, FIELD_NAME));
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "01234567891;           true;   Expected that 'string length' <= 10, where 'string length' is '11'!",
            "0123456789;            false;  Expected that 'string length' < 10, where 'string length' is '10'!",
            "abcdefghijklmnop;      true;   Expected that 'string length' <= 10, where 'string length' is '16'!"
    })
    @Order(12)
    void Should_throw_ConstraintViolationException(final String value,
                                                   final boolean inclusive,
                                                   final String details) {
        final MaxLengthConstraintValidator validator = new MaxLengthConstraintValidator(10, inclusive);
        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> validator.validate(value, PARAMETER, FIELD_NAME));
        assertEquals(
                "Invalid parameter \"fieldName\": " + details,
                exception.getMessage()
        );
    }
}
