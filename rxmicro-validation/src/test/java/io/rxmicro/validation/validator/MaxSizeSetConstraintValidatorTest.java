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
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Set;

import static io.rxmicro.common.util.Formats.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7
 */
// CPD-OFF Read more: https://pmd.github.io/pmd-6.13.0/pmd_userdocs_cpd.html#suppression
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class MaxSizeSetConstraintValidatorTest extends AbstractNullableConstraintValidatorTest<Set<?>> {

    @Override
    ConstraintValidator<Set<?>> instantiate() {
        return new MaxSizeSetConstraintValidator(3, true);
    }

    @Test
    @Order(11)
    void Should_process_parameter_as_a_valid_one() {
        assertDoesNotThrow(() -> validator.validate(Set.of(1, 2), PARAMETER, FIELD_NAME));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1,2,3,4",
            "1,2,3,4,5,6,7,8,9,0"
    })
    @Order(12)
    void Should_throw_ConstraintViolationException(final String value) {
        final Set<String> list = value.isEmpty() ? Set.of() : Set.of(value.split(","));
        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> validator.validate(list, PARAMETER, FIELD_NAME));
        assertEquals(
                format("Invalid parameter \"fieldName\": " +
                        "Expected that array length <= 3, but actual is ?. (array: ?)!", list.size(), list),
                exception.getMessage()
        );
    }

    @Test
    @Order(13)
    void Should_throw_UnsupportedOperationException_1() {
        final UnsupportedOperationException exception =
                assertThrows(UnsupportedOperationException.class, () -> validator.validateIterable(List.of()));
        assertEquals("Use 'validate' instead!", exception.getMessage());
    }

    @Test
    @Order(14)
    void Should_throw_UnsupportedOperationException_2() {
        final UnsupportedOperationException exception =
                assertThrows(UnsupportedOperationException.class, () -> validator.validateIterable(List.of(), PARAMETER, "model"));
        assertEquals("Use 'validate' instead!", exception.getMessage());
    }
}
// CPD-ON Read more: https://pmd.github.io/pmd-6.13.0/pmd_userdocs_cpd.html#suppression
