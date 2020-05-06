/*
 * Copyright (c) 2020. http://rxmicro.io
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
import static io.rxmicro.validation.validator.EmailConstraintValidator.EMAIL_DOMAIN_RULE;
import static io.rxmicro.validation.validator.EmailConstraintValidator.EMAIL_PREFIX_RULE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.4
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class EmailConstraintValidatorTest {

    private final EmailConstraintValidator validator = new EmailConstraintValidator(true);

    @ParameterizedTest
    @ValueSource(strings = {
            "alice@example.com",
            "alice.bob@example.com",
            "alice@example.me.org",
            "alice.o'brain@example.com",
            "alice+one@example.com",
            "alice+two@example.com"
    })
    void Should_resolve_emails_as_valid_ones(final String email){
        assertDoesNotThrow(() -> validator.validate(email, PARAMETER, "email"));
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "@                              ;Missing prefix!",
            "@example.com                   ;Missing prefix!",
            "user@                          ;Missing domain!",
            "example.com                    ;Missing '@'!",

            ".alice@example.com             ;Prefix can't start with '.'!",
            "-alice@example.com             ;Prefix can't start with '-'!",
            "_alice@example.com             ;Prefix can't start with '_'!",
            "+alice@example.com             ;Prefix can't start with '+'!",
            ".@example.com                  ;Prefix can't start with '.'!",
            "-@example.com                  ;Prefix can't start with '-'!",
            "_@example.com                  ;Prefix can't start with '_'!",
            "+@example.com                  ;Prefix can't start with '+'!",

            "alice.@example.com             ;Prefix can't end with '.'!",
            "alice-@example.com             ;Prefix can't end with '-'!",
            "alice_@example.com             ;Prefix can't end with '_'!",
            "alice'@example.com             ;Prefix can't end with '''!",
            "alice+@example.com             ;Prefix can't end with '+'!",

            "alice..o'brain@example.com     ;Prefix contains redundant character: '.'!",
            "alice+.o'brain@example.com     ;Prefix contains redundant character: '.'!",
            "alice-.o'brain@example.com     ;Prefix contains redundant character: '.'!",
            "alice_.o'brain@example.com     ;Prefix contains redundant character: '.'!",
            "alice'.o'brain@example.com     ;Prefix contains redundant character: '.'!",

            "alice++o'brain@example.com     ;Prefix contains redundant character: '+'!",
            "alice.+o'brain@example.com     ;Prefix contains redundant character: '+'!",
            "alice-+o'brain@example.com     ;Prefix contains redundant character: '+'!",
            "alice_+o'brain@example.com     ;Prefix contains redundant character: '+'!",
            "alice'+o'brain@example.com     ;Prefix contains redundant character: '+'!",

            "alice+-o'brain@example.com     ;Prefix contains redundant character: '-'!",
            "alice.-o'brain@example.com     ;Prefix contains redundant character: '-'!",
            "alice--o'brain@example.com     ;Prefix contains redundant character: '-'!",
            "alice_-o'brain@example.com     ;Prefix contains redundant character: '-'!",
            "alice'-o'brain@example.com     ;Prefix contains redundant character: '-'!",

            "alice+_o'brain@example.com     ;Prefix contains redundant character: '_'!",
            "alice._o'brain@example.com     ;Prefix contains redundant character: '_'!",
            "alice-_o'brain@example.com     ;Prefix contains redundant character: '_'!",
            "alice__o'brain@example.com     ;Prefix contains redundant character: '_'!",
            "alice'_o'brain@example.com     ;Prefix contains redundant character: '_'!",

            "alice+'o'brain@example.com     ;Prefix contains redundant character: '''!",
            "alice.'o'brain@example.com     ;Prefix contains redundant character: '''!",
            "alice-'o'brain@example.com     ;Prefix contains redundant character: '''!",
            "alice_'o'brain@example.com     ;Prefix contains redundant character: '''!",
            "alice''o'brain@example.com     ;Prefix contains redundant character: '''!",

            "alica @example.com             ;Unsupported prefix character: ' '. " + EMAIL_PREFIX_RULE,
            "%@example.com                  ;Unsupported prefix character: '%'. " + EMAIL_PREFIX_RULE,
            "alice?o'brain@example.com      ;Unsupported prefix character: '?'. " + EMAIL_PREFIX_RULE,

            "alice@example                  ;Domain name must contain at least two levels!",
            "alice@com                      ;Domain name must contain at least two levels!",
            "alice@example.c                ;The last portion of the domain name must be at least two characters!",
            "alice@example..com             ;Domain name contains redundant character: '.'!",
            "alice@.example.com             ;Domain name can't start with '.'!",
            "alice@example.com.             ;Domain name can't end with '.'!",
            "alice@-example.com             ;Domain name can't start with '-'!",
            "alice@example.com-             ;Domain name can't end with '-'!",
            "alice@sub?example.com          ;Unsupported domain name character: '?'. " + EMAIL_DOMAIN_RULE
    })
    void Should_resolve_emails_as_invalid_ones(final String email,
                                               final String details) {
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> validator.validate(email, PARAMETER, "email"));
        assertEquals(
                "Invalid parameter \"email\": " + details,
                exception.getMessage()
        );
    }

    @Test
    void Should_hide_error_details() {
        final EmailConstraintValidator validator = new EmailConstraintValidator(false);
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> validator.validate("com", PARAMETER, "email"));
        assertEquals(
                "Invalid parameter \"email\": Expected a valid email format!",
                exception.getMessage()
        );
    }
}