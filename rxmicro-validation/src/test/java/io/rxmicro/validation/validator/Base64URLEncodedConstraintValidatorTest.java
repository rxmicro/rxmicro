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
import io.rxmicro.validation.constraint.Base64URLEncoded;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

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
final class Base64URLEncodedConstraintValidatorTest extends AbstractConstraintValidatorTest<String> {

    @Override
    ConstraintValidator<String> instantiate() {
        return new Base64URLEncodedConstraintValidator(Base64URLEncoded.Alphabet.BASE);
    }

    @Test
    @Order(10)
    void Should_ignore_validation_for_empty_string() {
        assertDoesNotThrow(() -> validator.validate("", TYPE, FIELD_NAME));
    }

    @Test
    @Order(11)
    void Should_process_parameter_as_a_valid_one_using_BASE_alphabet() {
        assertDoesNotThrow(() -> validator.validate(
                "+/0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz",
                PARAMETER,
                "value"
        ));
    }

    @Test
    @Order(11)
    void Should_process_parameter_as_a_valid_one_using_URL_alphabet() {
        final Base64URLEncodedConstraintValidator validator = new Base64URLEncodedConstraintValidator(Base64URLEncoded.Alphabet.URL);
        assertDoesNotThrow(() -> validator.validate(
                "-_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz",
                PARAMETER,
                "value"
        ));
    }

    @Test
    @Order(13)
    void Should_throw_ValidationException_if_parameter_contains_invalid_character() {
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> validator.validate("A*B", PARAMETER, "value"));
        assertEquals(
                "Invalid parameter \"value\": " +
                        "Expected a valid Base64 string, i.e. string which contains the following characters only " +
                        "[[+, /, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, " +
                        "A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, " +
                        "a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z]], " +
                        "but actual value contains invalid character: '*' (0x2a)!",
                exception.getMessage()
        );
    }
}
