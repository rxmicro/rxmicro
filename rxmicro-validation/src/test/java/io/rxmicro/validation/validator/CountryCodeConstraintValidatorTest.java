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
import io.rxmicro.http.error.ValidationException;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.constraint.CountryCode;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.rest.model.HttpModelType.PARAMETER;
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
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class CountryCodeConstraintValidatorTest extends AbstractConstraintValidatorTest<String> {

    @Override
    ConstraintValidator<String> instantiate() {
        return new CountryCodeConstraintValidator(CountryCode.Format.ISO_3166_1_ALPHA_2);
    }

    @Test
    @Order(11)
    void Should_process_parameter_as_a_valid_ISO_3166_1_ALPHA_2_one() {
        assertDoesNotThrow(() -> validator.validate("US", PARAMETER, "country"));
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "                         ;Expected 2 characters, but actual is 0!",
            "com                      ;Expected 2 characters, but actual is 3!",
            "co                       ;Expected an uppercase string, but actual is 'co'!"
    })
    @Order(12)
    void Should_throw_ValidationException_if_parameter_is_invalid_ISO_3166_1_ALPHA_2_one(final String country,
                                                                                         final String details) {
        final ValidationException exception = assertThrows(ValidationException.class, () ->
                validator.validate(country == null ? "" : country, PARAMETER, "country"));
        assertEquals(
                "Invalid parameter \"country\": " + details,
                exception.getMessage()
        );
    }

    @Test
    @Order(13)
    void Should_process_parameter_as_a_valid_ISO_3166_1_ALPHA_3_one() {
        final CountryCodeConstraintValidator validator = new CountryCodeConstraintValidator(CountryCode.Format.ISO_3166_1_ALPHA_3);
        assertDoesNotThrow(() -> validator.validate("USA", PARAMETER, "country"));
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "                         ;Expected 3 characters, but actual is 0!",
            "co                       ;Expected 3 characters, but actual is 2!",
            "com                      ;Expected an uppercase string, but actual is 'com'!"
    })
    @Order(14)
    void Should_throw_ValidationException_if_parameter_is_invalid_ISO_3166_1_ALPHA_3_one(final String country,
                                                                                         final String details) {
        final CountryCodeConstraintValidator validator = new CountryCodeConstraintValidator(CountryCode.Format.ISO_3166_1_ALPHA_3);
        final ValidationException exception = assertThrows(ValidationException.class, () ->
                validator.validate(country == null ? "" : country, PARAMETER, "country"));
        assertEquals(
                "Invalid parameter \"country\": " + details,
                exception.getMessage()
        );
    }

    @Test
    @Order(15)
    void Should_process_parameter_as_a_valid_ISO_3166_1_NUMERIC_one() {
        final CountryCodeConstraintValidator validator = new CountryCodeConstraintValidator(CountryCode.Format.ISO_3166_1_NUMERIC);
        assertDoesNotThrow(() -> validator.validate("840", PARAMETER, "country"));
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "                         ;Expected 3 characters, but actual is 0!",
            "co                       ;Expected 3 characters, but actual is 2!",
            "com                      ;Expected a string with digits only, but actual value contains invalid character: 'c' (0x63)!"
    })
    @Order(16)
    void Should_throw_ValidationException_if_parameter_is_invalid_ISO_3166_1_NUMERIC_one(final String country,
                                                                                         final String details) {
        final CountryCodeConstraintValidator validator = new CountryCodeConstraintValidator(CountryCode.Format.ISO_3166_1_NUMERIC);
        final ValidationException exception = assertThrows(ValidationException.class, () ->
                validator.validate(country == null ? "" : country, PARAMETER, "country"));
        assertEquals(
                "Invalid parameter \"country\": " + details,
                exception.getMessage()
        );
    }

    @Test
    @Order(17)
    void Should_throw_ImpossibleException() {
        final ImpossibleException exception =
                assertThrows(ImpossibleException.class, () -> new CountryCodeConstraintValidator(mock(CountryCode.Format.class)));
        final String requiredFragment = "Unsupported format: Mock for Format";
        assertTrue(
                exception.getMessage().contains(requiredFragment),
                format("Exception message {?} does not contain required fragment: {?}", exception.getMessage(), requiredFragment)
        );
    }
}
