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
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static io.rxmicro.common.util.Formats.format;
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
final class MaxSizeConstraintValidatorTest extends AbstractConstraintValidatorTest<List<?>> {

    @Override
    ConstraintValidator<List<?>> instantiate() {
        return new MaxSizeConstraintValidator(3, true);
    }

    @Test
    @Order(11)
    void Should_process_parameter_as_a_valid_one() {
        assertDoesNotThrow(() -> validator.validate(List.of(1, 2), PARAMETER, "value"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1,2,3,4",
            "1,2,3,4,5,6,7,8,9,0"
    })
    @Order(12)
    void Should_throw_ValidationException(final String value) {
        final List<String> list = value.isEmpty() ? List.of() : List.of(value.split(","));
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> validator.validate(list, PARAMETER, "value"));
        assertEquals(
                format("Invalid parameter \"value\": Expected that 'list size' <= 3, where 'list size' is '?'!", list.size()),
                exception.getMessage()
        );
    }

    @Test
    @Order(13)
    void Should_throw_AbstractMethodError_1() {
        final AbstractMethodError exception =
                assertThrows(AbstractMethodError.class, () -> validator.validateList(List.of()));
        assertEquals("Use 'validate' instead!", exception.getMessage());
    }

    @Test
    @Order(14)
    void Should_throw_AbstractMethodError_2() {
        final AbstractMethodError exception =
                assertThrows(AbstractMethodError.class, () -> validator.validateList(List.of(), PARAMETER, "model"));
        assertEquals("Use 'validate' instead!", exception.getMessage());
    }
}