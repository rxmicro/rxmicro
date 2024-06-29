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
import org.junit.jupiter.params.provider.ValueSource;

import static io.rxmicro.validation.validator.HostNameConstraintValidator.HOST_NAME_RULE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class HostNameConstraintValidatorTest extends AbstractNullableConstraintValidatorTest<String> {

    @Override
    ConstraintValidator<String> instantiate() {
        return new HostNameConstraintValidator(true);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "localhost",
            "example.com",
            "example.me.org",
            "sub.example.me.org"
    })
    @Order(11)
    void Should_process_parameter_as_a_valid_one(final String domain) {
        assertDoesNotThrow(() -> validator.validate(domain, PARAMETER, "host"));
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "com                      ;Host name must contain at least two levels!",
            "example                  ;Host name must contain at least two levels!",
            "example.c                ;The last portion of the host name must be at least two characters!",
            "example..com             ;Host name contains redundant character: '.'!",
            "example.-com             ;Host name contains redundant character: '-'!",
            "example-.com             ;Host name contains redundant character: '.'!",
            "example._com             ;Host name contains redundant character: '_'!",
            "example_-com             ;Host name contains redundant character: '-'!",
            ".example.com             ;Host name can't start with '.'!",
            "example.com.             ;Host name can't end with '.'!",
            "-example.com             ;Host name can't start with '-'!",
            "example.com-             ;Host name can't end with '-'!",
            "_example.com             ;Host name can't start with '_'!",
            "example.com_             ;Host name can't end with '_'!",
            "sub#example.com          ;Unsupported host name character: '#'. " + HOST_NAME_RULE
    })
    @Order(12)
    void Should_throw_ConstraintViolationException(final String domain,
                                                   final String details) {
        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> validator.validate(domain, PARAMETER, "host"));
        assertEquals(
                "Invalid parameter \"host\": " + details,
                exception.getMessage()
        );
    }

    @Test
    @Order(13)
    void Should_throw_ConstraintViolationException_with_hiding_details() {
        final HostNameConstraintValidator validator = new HostNameConstraintValidator(false);
        final ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> validator.validate("com", PARAMETER, "host"));
        assertEquals(
                "Invalid parameter \"host\": Expected a valid host name!",
                exception.getMessage()
        );
    }
}
