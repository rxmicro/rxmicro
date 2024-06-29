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

import io.rxmicro.common.ImpossibleException;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.ConstraintViolationException;
import io.rxmicro.validation.constraint.TruncatedTime;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Instant;

import static io.rxmicro.common.util.Formats.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * @author nedis
 * @since 0.7
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class TruncatedTimeInstantConstraintValidatorTest extends AbstractNullableConstraintValidatorTest<Instant> {

    @Override
    ConstraintValidator<Instant> instantiate() {
        return new TruncatedTimeInstantConstraintValidator(TruncatedTime.Truncated.SECONDS);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "MILLIS;    2020-01-15T10:25:45.000Z",
            "SECONDS;   2020-01-15T10:25:00.000Z",
            "MINUTES;   2020-01-15T10:00:00.000Z",
            "HOURS;     2020-01-15T00:00:00.000Z"
    })
    @Order(11)
    void Should_process_parameter_as_a_valid_one(final TruncatedTime.Truncated truncated,
                                                 final String value) {
        final TruncatedTimeInstantConstraintValidator validator = new TruncatedTimeInstantConstraintValidator(truncated);
        assertDoesNotThrow(() -> validator.validate(Instant.parse(value), PARAMETER, "value"));
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "MILLIS;    2020-01-15T10:25:45.123Z;   Expected a time without milli seconds, but actual is '2020-01-15T10:25:45.123Z'!",

            "SECONDS;   2020-01-15T10:25:45.000Z;   Expected a time without seconds, but actual is '2020-01-15T10:25:45Z'!",
            "SECONDS;   2020-01-15T10:25:00.123Z;   Expected a time without seconds, but actual is '2020-01-15T10:25:00.123Z'!",

            "MINUTES;   2020-01-15T10:25:00.000Z;   Expected a time without minutes, but actual is '2020-01-15T10:25:00Z'!",
            "MINUTES;   2020-01-15T10:00:45.000Z;   Expected a time without minutes, but actual is '2020-01-15T10:00:45Z'!",
            "MINUTES;   2020-01-15T10:00:00.123Z;   Expected a time without minutes, but actual is '2020-01-15T10:00:00.123Z'!",

            "HOURS;     2020-01-15T10:00:00.000Z;   Expected a time without hours, but actual is '2020-01-15T10:00:00Z'!",
            "HOURS;     2020-01-15T00:25:00.000Z;   Expected a time without hours, but actual is '2020-01-15T00:25:00Z'!",
            "HOURS;     2020-01-15T00:00:45.000Z;   Expected a time without hours, but actual is '2020-01-15T00:00:45Z'!",
            "HOURS;     2020-01-15T00:00:00.123Z;   Expected a time without hours, but actual is '2020-01-15T00:00:00.123Z'!"
    })
    @Order(12)
    void Should_throw_ConstraintViolationException(final TruncatedTime.Truncated truncated,
                                                   final String value,
                                                   final String details) {
        final TruncatedTimeInstantConstraintValidator validator = new TruncatedTimeInstantConstraintValidator(truncated);
        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> validator.validate(Instant.parse(value), PARAMETER, "value"));
        assertEquals(
                "Invalid parameter \"value\": " + details,
                exception.getMessage()
        );
    }

    @Test
    @Order(13)
    void Should_throw_ImpossibleException() {
        final TruncatedTimeInstantConstraintValidator validator =
                new TruncatedTimeInstantConstraintValidator(mock(TruncatedTime.Truncated.class));
        final ImpossibleException exception =
                assertThrows(ImpossibleException.class, () -> validator.validate(Instant.now(), PARAMETER, "value"));
        final String requiredFragment = "Unsupported truncated: Mock for Truncated";
        assertTrue(
                exception.getMessage().contains(requiredFragment),
                format("Exception message {?} does not contain required fragment: {?}", exception.getMessage(), requiredFragment)
        );
    }
}
