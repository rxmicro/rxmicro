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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class PhoneConstraintValidatorTest extends AbstractNullableConstraintValidatorTest<String> {

    @Override
    ConstraintValidator<String> instantiate() {
        return new PhoneConstraintValidator(true, false);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "380501234567",
            "3040",
            "02"
    })
    @Order(11)
    void Should_process_parameter_as_a_valid_one(final String phone) {
        assertDoesNotThrow(() -> validator.validate(phone, PARAMETER, "phone"));
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "true; false; +380501234567; Expected a digit as a first character, but actual is '+'!",
            "true; false; 380501234567g; Expected digits only, but actual is '380501234567g' and contains invalid character: 'g' (0x67)!",
            "true; false; 38050 1234567; Expected digits only, but actual is '38050 1234567' and contains invalid character: ' ' (0x20)!",
            "false; true; 380501234567;  Expected a plus as a first character, but actual is '3'!",
            "false; true; +380501234567g; Expected digits only, but actual is '+380501234567g' and contains invalid character: 'g' (0x67)!",
            "false; true; +38050e1234567; Expected digits only, but actual is '+38050e1234567' and contains invalid character: 'e' (0x65)!",
            "false; true; +38050 1234567e; Expected digits only, but actual is '+38050 1234567e' and contains invalid character: 'e' (0x65)!"
    })
    @Order(13)
    void Should_throw_ConstraintViolationException(final boolean withoutPlus,
                                                   final boolean allowsSpaces,
                                                   final String value,
                                                   final String details) {
        final PhoneConstraintValidator validator = new PhoneConstraintValidator(withoutPlus, allowsSpaces);
        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> validator.validate(value, PARAMETER, FIELD_NAME));
        assertEquals(
                "Invalid parameter \"fieldName\": " + details,
                exception.getMessage()
        );
    }
}
