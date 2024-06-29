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
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.rxmicro.common.util.Formats.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class SubEnumConstraintValidatorTest extends AbstractNullableConstraintValidatorTest<TimeUnit> {

    @Override
    ConstraintValidator<TimeUnit> instantiate() {
        return new SubEnumConstraintValidator<>(TimeUnit.class, List.of("NANOSECONDS"), List.of());
    }

    @ParameterizedTest
    @EnumSource(value = TimeUnit.class, names = {"NANOSECONDS", "MICROSECONDS"})
    @Order(11)
    void Should_process_parameter_as_a_valid_one_with_include_specified_items(final TimeUnit timeUnit) {
        final SubEnumConstraintValidator<TimeUnit> validator =
                new SubEnumConstraintValidator<>(TimeUnit.class, List.of("NANOSECONDS", "MICROSECONDS"), List.of());
        assertDoesNotThrow(() -> validator.validate(timeUnit, PARAMETER, FIELD_NAME));
    }

    @ParameterizedTest
    @EnumSource(value = TimeUnit.class, names = {"NANOSECONDS", "MICROSECONDS"}, mode = EnumSource.Mode.EXCLUDE)
    @Order(12)
    void Should_process_parameter_as_a_valid_one_with_exclude_specified_items(final TimeUnit timeUnit) {
        final SubEnumConstraintValidator<TimeUnit> validator =
                new SubEnumConstraintValidator<>(TimeUnit.class, List.of(), List.of("NANOSECONDS", "MICROSECONDS"));
        assertDoesNotThrow(() -> validator.validate(timeUnit, PARAMETER, FIELD_NAME));
    }

    @ParameterizedTest
    @EnumSource(TimeUnit.class)
    @Order(13)
    void Should_throw_ConstraintViolationException_if_include_and_exclude_are_missing(final TimeUnit timeUnit) {
        final SubEnumConstraintValidator<TimeUnit> validator = new SubEnumConstraintValidator<>(TimeUnit.class, List.of(), List.of());
        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> validator.validate(timeUnit, PARAMETER, FIELD_NAME));
        assertEquals(
                format("Invalid parameter \"fieldName\": Expected a value from the set [], but actual is '?'!", timeUnit),
                exception.getMessage()
        );
    }
}
