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

package io.rxmicro.json;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @link http://rxmicro.io
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class JsonNumber_UnitTest {

    @Order(1)
    @ParameterizedTest
    @ValueSource(strings = {
            "NaN",
            "+NaN",
            "-NaN",
            "Infinity",
            "+Infinity",
            "-Infinity",
            "0",
            "0.",
            "0.00000000",
            "00000000000000000000000.000000000000000000000000000",
            "123456789",
            "123456789.123456789",
            "123456789.",
            "-123456789",
            "-123456789.123456789",
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
            "-0",
            "-0.0",
            "-0.0E-7",
            "-0.0E7",
            "-0.0E+7",
            "-0.0e-7",
            "-0.0e7",
            "-0.0e+7",
            "99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
            "99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999.99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"
    })
    void Should_not_throw_NumberFormatException(final String value) {
        assertDoesNotThrow(() -> new JsonNumber(value));
    }

    @Order(2)
    @ParameterizedTest
    @ValueSource(strings = {
            "hello",
            "+",
            "-",
            "",
            "0.0.0",
            "1234+34",
            "12345.+45",
            "12345.-45",
            "e",
            "e+7",
            "e-7",
            "1.2e",
            "1e",
            "0e-7e",
            "0Ee+7",
            "0EE-7",
            "1.2e-1.2",
            "1.2e+1.2",
            "3e-1.2",
            "3e+1.2"
    })
    void Should_throw_NumberFormatException(final String value) {
        assertThrows(NumberFormatException.class, () -> new JsonNumber(value));
    }
}