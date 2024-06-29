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
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static io.rxmicro.common.util.Formats.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.12
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class MaxDurationConstraintValidatorTest extends AbstractNullableConstraintValidatorTest<Duration> {

    @Override
    ConstraintValidator<Duration> instantiate() {
        return new MaxDurationConstraintValidator("PT10S", true);
    }

    @ParameterizedTest
    @Order(11)
    @CsvSource({
            "PT5.1S,    PT10S,    true",
            "PT10S,     PT10S,    true",
            "PT10.00S,  PT10S,    true",
            "PT5.1S,    PT10S,    false"
    })
    void Should_process_parameter_as_a_valid_one(final String actual,
                                                 final String maxValue,
                                                 final boolean inclusive) {
        assertDoesNotThrow(() -> new MaxDurationConstraintValidator(maxValue, inclusive)
                .validate(Duration.parse(actual), PARAMETER, FIELD_NAME));
    }

    @ParameterizedTest
    @Order(12)
    @ValueSource(strings = {
            "PT10S",
            "PT10.0S",
            "PT10.00S",
            "PT10.000S"
    })
    void Should_throw_ConstraintViolationException_when_values_equal_and_inclusive_is_false(final String actual) {
        final ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () ->
                new MaxDurationConstraintValidator("10", false)
                        .validate(Duration.parse(actual), PARAMETER, FIELD_NAME)
        );
        assertEquals(
                "Invalid parameter \"fieldName\": Expected that value < PT10S, but actual is PT10S!",
                exception.getMessage()
        );
    }

    @ParameterizedTest
    @CsvSource({
            "true,  <=",
            "false, <"
    })
    @Order(13)
    void Should_throw_ConstraintViolationException_when_value_greater_then_max(final boolean inclusive,
                                                                               final String sign) {
        final ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () ->
                new MaxDurationConstraintValidator("PT10S", inclusive)
                        .validate(Duration.ofSeconds(20), PARAMETER, FIELD_NAME)
        );
        assertEquals(
                format("Invalid parameter \"fieldName\": Expected that value ? PT10S, but actual is PT20S!", sign),
                exception.getMessage()
        );
    }
}
