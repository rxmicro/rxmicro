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
final class MinLengthConstraintValidatorTest extends AbstractConstraintValidatorTest<String> {

    @Override
    ConstraintValidator<String> instantiate() {
        return new MinLengthConstraintValidator(3, true);
    }

    @Test
    @Order(11)
    void Should_process_parameter_as_a_valid_one() {
        assertDoesNotThrow(() -> validator.validate("usa", PARAMETER, "value"));
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "as;    true;   Expected that 'string length' >= 3, where 'string length' is '2'!",
            "bye;   false;  Expected that 'string length' > 3, where 'string length' is '3'!",
            ";      true;   Expected that 'string length' >= 3, where 'string length' is '0'!"
    })
    @Order(12)
    void Should_throw_ValidationException(final String value,
                                          final boolean inclusive,
                                          final String details) {
        final MinLengthConstraintValidator validator = new MinLengthConstraintValidator(3, inclusive);
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> validator.validate(value == null ? "" : value, PARAMETER, "value"));
        assertEquals(
                "Invalid parameter \"value\": " + details,
                exception.getMessage()
        );
    }
}
