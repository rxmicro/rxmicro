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
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static io.rxmicro.rest.model.HttpModelType.PARAMETER;
import static io.rxmicro.validation.validator.DomainNameConstraintValidator.DOMAIN_RULE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 *
 * @since 0.4
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class DomainNameConstraintValidatorTest {

    private final DomainNameConstraintValidator validator = new DomainNameConstraintValidator(true);

    @ParameterizedTest
    @ValueSource(strings = {
            "example.com",
            "example.me.org",
            "sub.example.me.org"
    })
    void Should_resolve_domains_as_valid_ones(final String domain) {
        assertDoesNotThrow(() -> validator.validate(domain, PARAMETER, "domain"));
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "com                      ;Domain name must contain at least two levels!",
            "example                  ;Domain name must contain at least two levels!",
            "example.c                ;The last portion of the domain name must be at least two characters!",
            "example..com             ;Domain name contains redundant character: '.'!",
            "example.-com             ;Domain name contains redundant character: '-'!",
            "example-.com             ;Domain name contains redundant character: '.'!",
            "example._com             ;Domain name contains redundant character: '_'!",
            "example_-com             ;Domain name contains redundant character: '-'!",
            ".example.com             ;Domain name can't start with '.'!",
            "example.com.             ;Domain name can't end with '.'!",
            "-example.com             ;Domain name can't start with '-'!",
            "example.com-             ;Domain name can't end with '-'!",
            "_example.com             ;Domain name can't start with '_'!",
            "example.com_             ;Domain name can't end with '_'!",
            "sub#example.com          ;Unsupported domain name character: '#'. " + DOMAIN_RULE
    })
    void Should_resolve_domains_as_invalid_ones(final String domain,
                                                final String details) {
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> validator.validate(domain, PARAMETER, "domain"));
        assertEquals(
                "Invalid parameter \"domain\": " + details,
                exception.getMessage()
        );
    }

    @Test
    void Should_hide_error_details() {
        final DomainNameConstraintValidator validator = new DomainNameConstraintValidator(false);
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> validator.validate("com", PARAMETER, "domain"));
        assertEquals(
                "Invalid parameter \"domain\": Expected a valid domain name!",
                exception.getMessage()
        );
    }
}