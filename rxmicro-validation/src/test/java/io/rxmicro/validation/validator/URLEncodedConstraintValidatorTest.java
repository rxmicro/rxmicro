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
final class URLEncodedConstraintValidatorTest extends AbstractConstraintValidatorTest<String> {

    @Override
    ConstraintValidator<String> instantiate() {
        return new URLEncodedConstraintValidator();
    }

    @Test
    @Order(10)
    void Should_ignore_validation_for_empty_string() {
        assertDoesNotThrow(() -> validator.validate("", type, fieldName));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "%E4%B8%8A%E6%B5%B7%2B%E4%B8%AD%E5%9C%8B",
            "http://localhost:8080?value=%E4%B8%8A%E6%B5%B7%2B%E4%B8%AD%E5%9C%8B",
            "https://rxmicro.io/doc",
            "//rxmicro.io"
    })
    @Order(11)
    void Should_process_parameter_as_a_valid_one(final String value) {
        assertDoesNotThrow(() -> validator.validate(value, PARAMETER, "value"));
    }

    @Test
    @Order(12)
    void Should_throw_ValidationException_if_parameter_contains_invalid_character() {
        final ValidationException exception = assertThrows(ValidationException.class, () ->
                validator.validate("http://localhost:8080?value=ю", PARAMETER, "value"));
        assertEquals(
                "Invalid parameter \"value\": " +
                        "Expected a URL encoded string, where all characters are between 0x00 and 0x7F (ASCII), " +
                        "but actual is 'http://localhost:8080?value=ю'. " +
                        "Invalid character is 'ю' (0x44e)!",
                exception.getMessage()
        );
    }
}
