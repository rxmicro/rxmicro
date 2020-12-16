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

package io.rxmicro.json;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author nedis
 * @since 0.1
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class JsonNumberTest {

    @Order(1)
    @ParameterizedTest
    @ValueSource(strings = {
            "0",
            "0.",
            "0.00000000",
            "00000000000000000000000.000000000000000000000000000",
            "123456789",
            "123456789.123456789",
            "123456789.",
            "-123456789",
            "-123456789.123456789",
            "+123456789",
            "+123456789.123456789",
            "1.23456789E5",
            "1.23456789E+5",
            "1.23456789E-5",
            "1.23456789e5",
            "1.23456789e+5",
            "1.23456789e-5",
            "9.2e12",
            "0e+7",
            "0e-7",
            "0E+7",
            "0E-7",
            "+0",
            "+0.0",
            "+0.0E-7",
            "+0.0E7",
            "+0.0E+7",
            "+0.0e-7",
            "+0.0e7",
            "+0.0e+7",
            "-0",
            "-0.0",
            "-0.0E-7",
            "-0.0E7",
            "-0.0E+7",
            "-0.0e-7",
            "-0.0e7",
            "-0.0e+7",
            "99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
            "99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999." +
                    "99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",


            "-0.000000000000000000000000000000000000000000000000000000000000000000000000000001",
            "123e45",
            "-1",
            "123.456789",
            "1E+2",
            "1E22",
            "1e+2",
            "-123",
            "0e+1",
            "1E-2",
            "1e-2",
            "-0",
            "0e1",
            "123",
            "123.456e78",
            "-0",
            "20e1",
            "2.e-3",
            "+1",
            "-012",
            "-2.",
            ".2e-3",
            "-.123",
            "2.e3",
            "0.e1",
            "012",
            "2.e+3",
            "1.",
            ".123",
            "１"
    })
    void Should_not_throw_NumberFormatException(final String value) {
        assertDoesNotThrow(() -> new JsonNumber(value));
    }

    @Order(2)
    @ParameterizedTest
    @ValueSource(strings = {
            "NaN",
            "+NaN",
            "-NaN",
            "Infinity",
            "+Infinity",
            "-Infinity",

            "hello",
            "+",
            "-",
            ".",
            "",
            "0.0.0",
            "1234+34",
            "12345.+45",
            "12345.-45",
            "e",
            "e+7",
            "e-7",
            "1.0e+",
            "1.0e-",
            "E",
            "E+7",
            "E-7",
            "1.2e",
            "1e",
            "0e-7e",
            "0Ee+7",
            "0EE-7",
            "1.2e-1.2",
            "1.2e+1.2",
            "3e-1.2",
            "3e+1.2",

            "1e�",
            "1.8011670033376514H-308",
            "1+2",
            "-Infinity",
            "- 1",
            "0E",
            "0E+",
            "0.3e+",
            "++1234",
            "Infinity",
            "1.0e+",
            "0.3e",
            "-NaN",

            "0e+",
            "-foo",
            "0x1",
            "1e1�",
            "0e",
            "1.0e",
            "Inf",
            "+Inf",
            "1.2a-3",
            ".-1",
            "9.e+",
            "-123.123foo",
            "1eE2",
            "-1.0.",
            "1ea",
            "-1x",
            "1 000.0",
            "0.1.2",
            "1.0e-",
            "0e+-1",
            "123�",
            "0x42",

            ".e12",
            ".e+12",
            ".e-12"
    })
    void Should_throw_NumberFormatException(final String value) {
        assertThrows(NumberFormatException.class, () -> new JsonNumber(value));
    }

    @Order(3)
    @Test
    void Should_override_equals_and_hash_code() {
        final JsonNumber first = new JsonNumber("0.123");
        final JsonNumber second = new JsonNumber("0.123");
        assertEquals(first.hashCode(), second.hashCode());
        assertEquals(first, first);
        assertEquals(first, second);
        assertNotEquals(first, null);
        assertNotEquals(first, new BigDecimal("0.123"));
    }

    @Order(4)
    @Test
    void Should_compare_numbers_correctly() {
        final JsonNumber first = new JsonNumber("5");
        final JsonNumber second = new JsonNumber("10");

        assertTrue(first.compareTo(second) < 0);
    }
}