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

package io.rxmicro.exchange.json.detail;

import io.rxmicro.http.error.ValidationException;
import io.rxmicro.json.JsonHelper;
import io.rxmicro.json.JsonNumber;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class ModelFromJsonConverterTest {

    private final ModelFromJsonConverter<Object> converter = new ModelFromJsonConverter<>() {

    };

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";        ",
            "RED;  RED"
    })
    void toEnum_should_convert_value_successfully(final String value,
                                                  final Color expected) {
        final Color actual =
                assertDoesNotThrow(() -> converter.toEnum(Color.class, value, "value"));
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;         Expected a string value from the set [RED, GREEN, BLUE], but actual is ''!",
            "DAYS;          Expected a string value from the set [RED, GREEN, BLUE], but actual is 'DAYS'!",
            "123;           Expected a string value, but actual is '123' (number)!",
            "true;          Expected a string value, but actual is 'true' (boolean)!",
            "{};            Expected a string value, but actual is '{}' (object)!",
            "[];            Expected a string value, but actual is '[]' (empty array)!",

            "true;          Expected a string value, but actual is 'true' (boolean)!",
            "123;           Expected a string value, but actual is '123' (number)!",
            "{};            Expected a string value, but actual is '{}' (object)!",
            "123,456;       Expected a string value, but actual is '[123,456]' (array of numbers)!",
            "true,false;    Expected a string value, but actual is '[true,false]' (array of booleans)!",
            "[],[];         Expected a string value, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected a string value, but actual is '[{},{}]' (array of objects)!",
            "1,null,null;   Expected a string value, but actual is '[1,null,null]' (array of numbers, nulls)!",
            "123,RED,true;  Expected a string value, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected a string value, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "true,null;     Expected a string value, but actual is '[true,null]' (array of booleans, nulls)!"
    })
    void toEnum_should_throw_ValidationException(final String value,
                                                 final String expectedError) {
        final Object valueParam = getParams(value);
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toEnum(Color.class, valueParam, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                  ",
            "RED,GREEN;         RED,GREEN",
            "RED,GREEN,BLUE;    RED,GREEN,BLUE"
    })
    void toEnumList_should_convert_value_successfully(final String value,
                                                      final String expected) {
        final Object params = getParams(value);
        final List<Color> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(Color::valueOf).collect(Collectors.toList());

        final List<Color> actualList =
                assertDoesNotThrow(() -> converter.toEnumList(Color.class, params, "value"));
        assertEquals(expectedList, actualList);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "true;          Expected an array of strings, but actual is 'true' (boolean)!",
            "123;           Expected an array of strings, but actual is '123' (number)!",
            "{};            Expected an array of strings, but actual is '{}' (object)!",
            "123,456;       Expected an array of strings, but actual is '[123,456]' (array of numbers)!",
            "true,false;    Expected an array of strings, but actual is '[true,false]' (array of booleans)!",
            "[],[];         Expected an array of strings, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected an array of strings, but actual is '[{},{}]' (array of objects)!",
            "1,null,null;   Expected an array of strings, but actual is '[1,null,null]' (array of numbers, nulls)!",
            "123,RED,true;  Expected an array of strings, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected an array of strings, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "true,null;     Expected an array of strings, but actual is '[true,null]' (array of booleans, nulls)!"
    })
    void toEnumList_should_throw_ValidationException(final String value,
                                                     final String expectedError) {
        final Object params = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toEnumList(Color.class, params, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "true",
            "false"
    })
    void toBoolean_should_convert_value_successfully(final String value) {
        final Object expected = value.isEmpty() ? null : Boolean.parseBoolean(value);

        final Boolean actual = assertDoesNotThrow(() -> converter.toBoolean(expected, "value"));
        assertSame(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;         Expected a boolean value, but actual is '' (string)!",
            "DAYS;          Expected a boolean value, but actual is 'DAYS' (string)!",
            "123;           Expected a boolean value, but actual is '123' (number)!",
            "{};            Expected a boolean value, but actual is '{}' (object)!",
            "[];            Expected a boolean value, but actual is '[]' (empty array)!",

            "123;           Expected a boolean value, but actual is '123' (number)!",
            "{};            Expected a boolean value, but actual is '{}' (object)!",
            "123,456;       Expected a boolean value, but actual is '[123,456]' (array of numbers)!",
            "RED,GREEN;     Expected a boolean value, but actual is '[\"RED\",\"GREEN\"]' (array of strings)!",
            "[],[];         Expected a boolean value, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected a boolean value, but actual is '[{},{}]' (array of objects)!",
            "1,null,null;   Expected a boolean value, but actual is '[1,null,null]' (array of numbers, nulls)!",
            "123,RED,true;  Expected a boolean value, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected a boolean value, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "12.34,null;    Expected a boolean value, but actual is '[12.34,null]' (array of numbers, nulls)!"
    })
    void toBoolean_should_throw_ValidationException(final String value,
                                                    final String expectedError) {
        final Object valueParam = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toBoolean(valueParam, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                  ",
            "true,true;         true,true",
            "true,false,true;   true,false,true"
    })
    void toBooleanList_should_convert_value_successfully(final String value,
                                                         final String expected) {
        final Object params = getParams(value);
        final List<Boolean> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(Boolean::valueOf).collect(Collectors.toList());

        final List<Boolean> actualList =
                assertDoesNotThrow(() -> converter.toBooleanList(params, "value"));
        assertEquals(expectedList, actualList);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "true;          Expected an array of booleans, but actual is 'true' (boolean)!",
            "123;           Expected an array of booleans, but actual is '123' (number)!",
            "{};            Expected an array of booleans, but actual is '{}' (object)!",
            "123,456;       Expected an array of booleans, but actual is '[123,456]' (array of numbers)!",
            "RED,GREEN;     Expected an array of booleans, but actual is '[\"RED\",\"GREEN\"]' (array of strings)!",
            "[],[];         Expected an array of booleans, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected an array of booleans, but actual is '[{},{}]' (array of objects)!",
            "1,null,null;   Expected an array of booleans, but actual is '[1,null,null]' (array of numbers, nulls)!",
            "123,RED,true;  Expected an array of booleans, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected an array of booleans, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "12.34,null;    Expected an array of booleans, but actual is '[12.34,null]' (array of numbers, nulls)!"
    })
    void toBooleanList_should_throw_ValidationException(final String value,
                                                        final String expectedError) {
        final Object params = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toBooleanList(params, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "127",
            "-128"
    })
    void toByte_should_convert_value_successfully(final String value) {
        final JsonNumber expected = value.isEmpty() ? null : new JsonNumber(value);

        final Byte actual = assertDoesNotThrow(() -> converter.toByte(expected, "value"));
        if (expected == null) {
            assertNull(actual);
        } else {
            assertEquals(expected.byteValueExact(), actual);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;         Expected an integer value, but actual is '' (string)!",
            "3.14159265;    Expected an integer value, but actual is '3.14159265'!",
            "-130;          Expected an integer value that >= '-128', but actual is '-130'!",
            "128;           Expected an integer value that <= '127', but actual is '128'!",
            "not_digits;    Expected an integer value, but actual is 'not_digits' (string)!",

            "RED,GREEN;     Expected an integer value, but actual is '[\"RED\",\"GREEN\"]' (array of strings)!",
            "[],[];         Expected an integer value, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected an integer value, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected an integer value, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected an integer value, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected an integer value, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected an integer value, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toByte_should_throw_ValidationException(final String value,
                                                 final String expectedError) {
        final Object valueParam = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toByte(valueParam, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";              ",
            "22,125;        22,125",
            "125,22;        125,22",
            "22,33,44;      22,33,44"
    })
    void toByteList_should_convert_value_successfully(final String value,
                                                      final String expected) {
        final Object params = getParams(value);
        final List<Byte> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(Byte::valueOf).collect(Collectors.toList());

        final List<Byte> actualList =
                assertDoesNotThrow(() -> converter.toByteList(params, "value"));
        assertEquals(expectedList, actualList);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "true;          Expected an array of numbers, but actual is 'true' (boolean)!",
            "123;           Expected an array of numbers, but actual is '123' (number)!",
            "{};            Expected an array of numbers, but actual is '{}' (object)!",
            "RED,GREEN;     Expected an array of numbers, but actual is '[\"RED\",\"GREEN\"]' (array of strings)!",
            "[],[];         Expected an array of numbers, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected an array of numbers, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected an array of numbers, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected an array of numbers, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected an array of numbers, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected an array of numbers, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toByteList_should_throw_ValidationException(final String value,
                                                     final String expectedError) {
        final Object params = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toByteList(params, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "8765"
    })
    void toShort_should_convert_value_successfully(final String value) {
        final JsonNumber expected = value.isEmpty() ? null : new JsonNumber(value);

        final Short actual = assertDoesNotThrow(() -> converter.toShort(expected, "value"));
        if (expected == null) {
            assertNull(actual);
        } else {
            assertEquals(expected.shortValueExact(), actual);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;         Expected an integer value, but actual is '' (string)!",
            "3.14159265;    Expected an integer value, but actual is '3.14159265'!",
            "-43210;        Expected an integer value that >= '-32768', but actual is '-43210'!",
            "+43210;        Expected an integer value that <= '32767', but actual is '+43210'!",
            "not_digits;    Expected an integer value, but actual is 'not_digits' (string)!",

            "RED,GREEN;     Expected an integer value, but actual is '[\"RED\",\"GREEN\"]' (array of strings)!",
            "[],[];         Expected an integer value, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected an integer value, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected an integer value, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected an integer value, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected an integer value, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected an integer value, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toShort_should_throw_ValidationException(final String value,
                                                  final String expectedError) {
        final Object valueParam = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toShort(valueParam, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                  ",
            "30123,256;         30123,256",
            "30123,256;         30123,256",
            "30123,256,8765;    30123,256,8765"
    })
    void toShortList_should_convert_value_successfully(final String value,
                                                       final String expected) {
        final Object params = getParams(value);
        final List<Short> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(Short::valueOf).collect(Collectors.toList());

        final List<Short> actualList =
                assertDoesNotThrow(() -> converter.toShortList(params, "value"));
        assertEquals(expectedList, actualList);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "true;          Expected an array of numbers, but actual is 'true' (boolean)!",
            "123;           Expected an array of numbers, but actual is '123' (number)!",
            "{};            Expected an array of numbers, but actual is '{}' (object)!",
            "RED,GREEN;     Expected an array of numbers, but actual is '[\"RED\",\"GREEN\"]' (array of strings)!",
            "[],[];         Expected an array of numbers, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected an array of numbers, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected an array of numbers, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected an array of numbers, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected an array of numbers, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected an array of numbers, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toShortList_should_throw_ValidationException(final String value,
                                                      final String expectedError) {
        final Object params = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toShortList(params, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "2147483647"
    })
    void toInteger_should_convert_value_successfully(final String value) {
        final JsonNumber expected = value.isEmpty() ? null : new JsonNumber(value);

        final Integer actual = assertDoesNotThrow(() -> converter.toInteger(expected, "value"));
        if (expected == null) {
            assertNull(actual);
        } else {
            assertEquals(expected.intValueExact(), actual);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;         Expected an integer value, but actual is '' (string)!",
            "3.14159265;    Expected an integer value, but actual is '3.14159265'!",
            "-21474836478;  Expected an integer value that >= '-2147483648', but actual is '-21474836478'!",
            "+21474836478;  Expected an integer value that <= '2147483647', but actual is '+21474836478'!",
            "not_digits;    Expected an integer value, but actual is 'not_digits' (string)!",

            "RED,GREEN;     Expected an integer value, but actual is '[\"RED\",\"GREEN\"]' (array of strings)!",
            "[],[];         Expected an integer value, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected an integer value, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected an integer value, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected an integer value, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected an integer value, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected an integer value, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toInteger_should_throw_ValidationException(final String value,
                                                    final String expectedError) {
        final Object valueParam = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toInteger(valueParam, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                          ",
            "2147483647,32767854;       2147483647,32767854",
            "32767854,327678;           32767854,327678",
            "327678,327679,3276787;     327678,327679,3276787"
    })
    void toIntegerList_should_convert_value_successfully(final String value,
                                                         final String expected) {
        final Object params = getParams(value);
        final List<Integer> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(Integer::valueOf).collect(Collectors.toList());

        final List<Integer> actualList =
                assertDoesNotThrow(() -> converter.toIntegerList(params, "value"));
        assertEquals(expectedList, actualList);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "true;          Expected an array of numbers, but actual is 'true' (boolean)!",
            "123;           Expected an array of numbers, but actual is '123' (number)!",
            "{};            Expected an array of numbers, but actual is '{}' (object)!",
            "RED,GREEN;     Expected an array of numbers, but actual is '[\"RED\",\"GREEN\"]' (array of strings)!",
            "[],[];         Expected an array of numbers, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected an array of numbers, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected an array of numbers, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected an array of numbers, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected an array of numbers, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected an array of numbers, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toIntegerList_should_throw_ValidationException(final String value,
                                                        final String expectedError) {
        final Object params = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toIntegerList(params, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "22474836478902987"
    })
    void toLong_should_convert_value_successfully(final String value) {
        final JsonNumber expected = value.isEmpty() ? null : new JsonNumber(value);

        final Long actual = assertDoesNotThrow(() -> converter.toLong(expected, "value"));
        if (expected == null) {
            assertNull(actual);
        } else {
            assertEquals(expected.longValueExact(), actual);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;                 Expected an integer value, but actual is '' (string)!",
            "3.14159265;            Expected an integer value, but actual is '3.14159265'!",
            "-9999999999999999999;  Expected an integer value that >= '-9223372036854775808', but actual is '-9999999999999999999'!",
            "9999999999999999999;   Expected an integer value that <= '9223372036854775807', but actual is '9999999999999999999'!",
            "not_digits;            Expected an integer value, but actual is 'not_digits' (string)!",

            "RED,GREEN;     Expected an integer value, but actual is '[\"RED\",\"GREEN\"]' (array of strings)!",
            "[],[];         Expected an integer value, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected an integer value, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected an integer value, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected an integer value, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected an integer value, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected an integer value, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toLong_should_throw_ValidationException(final String value,
                                                 final String expectedError) {
        final Object valueParam = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toLong(valueParam, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                                  ",
            "22474836478902987,34;              22474836478902987,34",
            "2247483,22474836478902;            2247483,22474836478902",
            "2247483647,2247483647,2247483647;  2247483647,2247483647,2247483647"
    })
    void toLongList_should_convert_value_successfully(final String value,
                                                      final String expected) {
        final Object params = getParams(value);
        final List<Long> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(Long::valueOf).collect(Collectors.toList());

        final List<Long> actualList =
                assertDoesNotThrow(() -> converter.toLongList(params, "value"));
        assertEquals(expectedList, actualList);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "true;          Expected an array of numbers, but actual is 'true' (boolean)!",
            "123;           Expected an array of numbers, but actual is '123' (number)!",
            "{};            Expected an array of numbers, but actual is '{}' (object)!",
            "RED,GREEN;     Expected an array of numbers, but actual is '[\"RED\",\"GREEN\"]' (array of strings)!",
            "[],[];         Expected an array of numbers, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected an array of numbers, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected an array of numbers, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected an array of numbers, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected an array of numbers, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected an array of numbers, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toLongList_should_throw_ValidationException(final String value,
                                                     final String expectedError) {
        final Object params = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toLongList(params, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "9999999999999999999"
    })
    void toBigInteger_should_convert_value_successfully(final String value) {
        final JsonNumber expected = value.isEmpty() ? null : new JsonNumber(value);

        final BigInteger actual = assertDoesNotThrow(() -> converter.toBigInteger(expected, "value"));
        if (expected == null) {
            assertNull(actual);
        } else {
            assertEquals(expected.bigIntegerValueExact(), actual);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;         Expected an integer value, but actual is '' (string)!",
            "3.14159265;    Expected an integer value, but actual is '3.14159265'!",
            "not_digits;    Expected an integer value, but actual is 'not_digits' (string)!",

            "RED,GREEN;     Expected an integer value, but actual is '[\"RED\",\"GREEN\"]' (array of strings)!",
            "[],[];         Expected an integer value, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected an integer value, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected an integer value, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected an integer value, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected an integer value, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected an integer value, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toBigInteger_should_throw_ValidationException(final String value,
                                                       final String expectedError) {
        final Object valueParam = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toBigInteger(valueParam, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                                  ",
            "999999999999999999999,3455;        999999999999999999999,3455",
            "2247483647,2247483647,2247483647;  2247483647,2247483647,2247483647"
    })
    void toBigIntegerList_should_convert_value_successfully(final String value,
                                                            final String expected) {
        final Object params = getParams(value);
        final List<BigInteger> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(BigInteger::new).collect(Collectors.toList());

        final List<BigInteger> actualList =
                assertDoesNotThrow(() -> converter.toBigIntegerList(params, "value"));
        assertEquals(expectedList, actualList);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "true;          Expected an array of numbers, but actual is 'true' (boolean)!",
            "123;           Expected an array of numbers, but actual is '123' (number)!",
            "{};            Expected an array of numbers, but actual is '{}' (object)!",
            "RED,GREEN;     Expected an array of numbers, but actual is '[\"RED\",\"GREEN\"]' (array of strings)!",
            "[],[];         Expected an array of numbers, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected an array of numbers, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected an array of numbers, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected an array of numbers, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected an array of numbers, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected an array of numbers, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toBigIntegerList_should_throw_ValidationException(final String value,
                                                           final String expectedError) {
        final Object params = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toBigIntegerList(params, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "3.14"
    })
    void toFloat_should_convert_value_successfully(final String value) {
        final JsonNumber expected = value.isEmpty() ? null : new JsonNumber(value);

        final Float actual = assertDoesNotThrow(() -> converter.toFloat(expected, "value"));
        if (expected == null) {
            assertNull(actual);
        } else {
            assertEquals(expected.floatValueExact(), actual);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;         Expected a decimal value, but actual is '' (string)!",
            "not_digits;    Expected a decimal value, but actual is 'not_digits' (string)!",
            "-3.4E40;       Expected a decimal value that >= '-3.4028235E38', but actual is '-3.4E40'!",
            "3.4E40;        Expected a decimal value that <= '3.4028235E38', but actual is '3.4E40'!",

            "RED,GREEN;     Expected a decimal value, but actual is '[\"RED\",\"GREEN\"]' (array of strings)!",
            "[],[];         Expected a decimal value, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected a decimal value, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected a decimal value, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected a decimal value, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected a decimal value, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected a decimal value, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toFloat_should_throw_ValidationException(final String value,
                                                  final String expectedError) {
        final Object valueParam = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toFloat(valueParam, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                      ",
            "3.14,3.1415;           3.14,3.1415",
            "3.1415,3.14;           3.1415,3.14",
            "3.14,3.1415,3.141592;  3.14,3.1415,3.141592"
    })
    void toFloatList_should_convert_value_successfully(final String value,
                                                       final String expected) {
        final Object params = getParams(value);
        final List<Float> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(Float::valueOf).collect(Collectors.toList());

        final List<Float> actualList =
                assertDoesNotThrow(() -> converter.toFloatList(params, "value"));
        assertEquals(expectedList, actualList);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "true;          Expected an array of numbers, but actual is 'true' (boolean)!",
            "123;           Expected an array of numbers, but actual is '123' (number)!",
            "{};            Expected an array of numbers, but actual is '{}' (object)!",
            "RED,GREEN;     Expected an array of numbers, but actual is '[\"RED\",\"GREEN\"]' (array of strings)!",
            "[],[];         Expected an array of numbers, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected an array of numbers, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected an array of numbers, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected an array of numbers, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected an array of numbers, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected an array of numbers, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toFloatList_should_throw_ValidationException(final String value,
                                                      final String expectedError) {
        final Object params = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toFloatList(params, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "3.14"
    })
    void toDouble_should_convert_value_successfully(final String value) {
        final JsonNumber expected = value.isEmpty() ? null : new JsonNumber(value);

        final Double actual = assertDoesNotThrow(() -> converter.toDouble(expected, "value"));
        if (expected == null) {
            assertNull(actual);
        } else {
            assertEquals(expected.doubleValueExact(), actual);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;         Expected a decimal value, but actual is '' (string)!",
            "not_digits;    Expected a decimal value, but actual is 'not_digits' (string)!",
            "-1.2E310;      Expected a decimal value that >= '-1.7976931348623157E308', but actual is '-1.2E310'!",
            "1.79E310;      Expected a decimal value that <= '1.7976931348623157E308', but actual is '1.79E310'!",

            "RED,GREEN;     Expected a decimal value, but actual is '[\"RED\",\"GREEN\"]' (array of strings)!",
            "[],[];         Expected a decimal value, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected a decimal value, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected a decimal value, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected a decimal value, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected a decimal value, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected a decimal value, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toDouble_should_throw_ValidationException(final String value,
                                                   final String expectedError) {
        final Object valueParam = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toDouble(valueParam, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                      ",
            "3.14,3.1415;           3.14,3.1415",
            "3.1415,3.14;           3.1415,3.14",
            "3.14,3.1415,3.141592;  3.14,3.1415,3.141592"
    })
    void toDoubleList_should_convert_value_successfully(final String value,
                                                        final String expected) {
        final Object params = getParams(value);
        final List<Double> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(Double::valueOf).collect(Collectors.toList());

        final List<Double> actualList =
                assertDoesNotThrow(() -> converter.toDoubleList(params, "value"));
        assertEquals(expectedList, actualList);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "true;          Expected an array of numbers, but actual is 'true' (boolean)!",
            "123;           Expected an array of numbers, but actual is '123' (number)!",
            "{};            Expected an array of numbers, but actual is '{}' (object)!",
            "RED,GREEN;     Expected an array of numbers, but actual is '[\"RED\",\"GREEN\"]' (array of strings)!",
            "[],[];         Expected an array of numbers, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected an array of numbers, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected an array of numbers, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected an array of numbers, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected an array of numbers, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected an array of numbers, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toDoubleList_should_throw_ValidationException(final String value,
                                                       final String expectedError) {
        final Object params = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toDoubleList(params, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "3.1415926535897932384626433832795"
    })
    void toBigDecimal_should_convert_value_successfully(final String value) {
        final JsonNumber expected = value.isEmpty() ? null : new JsonNumber(value);

        final BigDecimal actual = assertDoesNotThrow(() -> converter.toBigDecimal(expected, "value"));
        if (expected == null) {
            assertNull(actual);
        } else {
            assertEquals(expected.bigDecimalValueExact(), actual);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;         Expected a decimal value, but actual is '' (string)!",
            "not_digits;    Expected a decimal value, but actual is 'not_digits' (string)!",

            "RED,GREEN;     Expected a decimal value, but actual is '[\"RED\",\"GREEN\"]' (array of strings)!",
            "[],[];         Expected a decimal value, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected a decimal value, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected a decimal value, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected a decimal value, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected a decimal value, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected a decimal value, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toBigDecimal_should_throw_ValidationException(final String value,
                                                       final String expectedError) {
        final Object valueParam = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toBigDecimal(valueParam, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                      ",
            "3.14,3.1415;           3.14,3.1415",
            "3.1415,3.14;           3.1415,3.14",
            "3.14,3.1415,3.141592;  3.14,3.1415,3.141592"
    })
    void toBigDecimalList_should_convert_value_successfully(final String value,
                                                            final String expected) {
        final Object params = getParams(value);
        final List<BigDecimal> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(BigDecimal::new).collect(Collectors.toList());

        final List<BigDecimal> actualList =
                assertDoesNotThrow(() -> converter.toBigDecimalList(params, "value"));
        assertEquals(expectedList, actualList);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "true;          Expected an array of numbers, but actual is 'true' (boolean)!",
            "123;           Expected an array of numbers, but actual is '123' (number)!",
            "{};            Expected an array of numbers, but actual is '{}' (object)!",
            "RED,GREEN;     Expected an array of numbers, but actual is '[\"RED\",\"GREEN\"]' (array of strings)!",
            "[],[];         Expected an array of numbers, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected an array of numbers, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected an array of numbers, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected an array of numbers, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected an array of numbers, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected an array of numbers, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toBigDecimalList_should_throw_ValidationException(final String value,
                                                           final String expectedError) {
        final Object params = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toBigDecimalList(params, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "2020-01-15T10:25:45Z"
    })
    void toInstant_should_convert_value_successfully(final String value) {
        final String expected = value.isEmpty() ? null : value;

        final Instant actual = assertDoesNotThrow(() -> converter.toInstant(expected, "value"));
        if (expected == null) {
            assertNull(actual);
        } else {
            assertEquals(Instant.parse(value), actual);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;                 Expected an ISO-8601 instant (Example: '1987-04-10T23:40:15.789Z'), but actual is ''!",
            "2020-13-15T10:25:45Z;  Expected an ISO-8601 instant (Example: '1987-04-10T23:40:15.789Z'), but actual is '2020-13-15T10:25:45Z'!",

            "true;                  Expected a string value, but actual is 'true' (boolean)!",
            "123;                   Expected a string value, but actual is '123' (number)!",
            "{};                    Expected a string value, but actual is '{}' (object)!",
            "[],[];                 Expected a string value, but actual is '[[],[]]' (array of arrays)!",
            "{},{};                 Expected a string value, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;        Expected a string value, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;          Expected a string value, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};            Expected a string value, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];           Expected a string value, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toInstant_should_throw_ValidationException(final String value,
                                                    final String expectedError) {
        final Object valueParam = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toInstant(valueParam, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                                                              ",
            "2020-01-15T10:25:45Z,2020-01-15T10:25:45Z;                     2020-01-15T10:25:45Z,2020-01-15T10:25:45Z",
            "2020-01-15T10:25:45Z,2020-01-15T10:25:45Z,2020-01-15T10:25:45Z;2020-01-15T10:25:45Z,2020-01-15T10:25:45Z,2020-01-15T10:25:45Z"
    })
    void toInstantList_should_convert_value_successfully(final String value,
                                                         final String expected) {
        final Object params = getParams(value);
        final List<Instant> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(Instant::parse).collect(Collectors.toList());

        final List<Instant> actualList =
                assertDoesNotThrow(() -> converter.toInstantList(params, "value"));
        assertEquals(expectedList, actualList);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "true;          Expected an array of strings, but actual is 'true' (boolean)!",
            "123;           Expected an array of strings, but actual is '123' (number)!",
            "{};            Expected an array of strings, but actual is '{}' (object)!",
            "[],[];         Expected an array of strings, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected an array of strings, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected an array of strings, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected an array of strings, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected an array of strings, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected an array of strings, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toInstantList_should_throw_ValidationException(final String value,
                                                        final String expectedError) {
        final Object params = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toInstantList(params, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "R"
    })
    void toCharacter_should_convert_value_successfully(final String value) {
        final String expected = value.isEmpty() ? null : value;

        final Character actual = assertDoesNotThrow(() -> converter.toCharacter(expected, "value"));
        if (expected == null) {
            assertNull(actual);
        } else {
            assertEquals(value.charAt(0), actual);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;         Expected a character, but actual is '' (string)!",
            "RG;            Expected a character, but actual is 'RG' (string)!",

            "true;          Expected a character, but actual is 'true' (boolean)!",
            "123;           Expected a character, but actual is '123' (number)!",
            "{};            Expected a character, but actual is '{}' (object)!",
            "[],[];         Expected a character, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected a character, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected a character, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected a character, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected a character, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected a character, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toCharacter_should_throw_ValidationException(final String value,
                                                      final String expectedError) {
        final Object valueParam = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toCharacter(valueParam, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";          ",
            "R,G;       R,G",
            "R,G,B;     R,G,B"
    })
    void toCharacterList_should_convert_value_successfully(final String value,
                                                           final String expected) {
        final Object params = getParams(value);
        final List<Character> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(s -> s.charAt(0)).collect(Collectors.toList());

        final List<Character> actualList =
                assertDoesNotThrow(() -> converter.toCharacterList(params, "value"));
        assertEquals(expectedList, actualList);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "true;          Expected an array of characters, but actual is 'true' (boolean)!",
            "123;           Expected an array of characters, but actual is '123' (number)!",
            "{};            Expected an array of characters, but actual is '{}' (object)!",
            "[],[];         Expected an array of characters, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected an array of characters, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected an array of characters, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected an array of characters, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected an array of characters, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected an array of characters, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toCharacterList_should_throw_ValidationException(final String value,
                                                          final String expectedError) {
        final Object params = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toCharacterList(params, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "test"
    })
    void toString_should_convert_value_successfully(final String value) {
        final String expected = value.isEmpty() ? null : value;

        final String actual = assertDoesNotThrow(() -> converter.toString(expected, "value"));
        if (expected == null) {
            assertNull(actual);
        } else {
            assertEquals(value, actual);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "true;          Expected a string value, but actual is 'true' (boolean)!",
            "123;           Expected a string value, but actual is '123' (number)!",
            "{};            Expected a string value, but actual is '{}' (object)!",
            "[],[];         Expected a string value, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected a string value, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected a string value, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected a string value, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected a string value, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected a string value, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toString_should_throw_ValidationException(final String value,
                                                   final String expectedError) {
        final Object valueParam = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toString(valueParam, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                  ",
            "RED,GREEN;         RED,GREEN",
            "RED,GREEN,BLUE;    RED,GREEN,BLUE"
    })
    void toStringList_should_convert_value_successfully(final String value,
                                                        final String expected) {
        final Object params = getParams(value);
        final List<String> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).collect(Collectors.toList());

        final List<String> actualList =
                assertDoesNotThrow(() -> converter.toStringList(params, "value"));
        assertEquals(expectedList, actualList);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "true;          Expected an array of strings, but actual is 'true' (boolean)!",
            "123;           Expected an array of strings, but actual is '123' (number)!",
            "{};            Expected an array of strings, but actual is '{}' (object)!",
            "[],[];         Expected an array of strings, but actual is '[[],[]]' (array of arrays)!",
            "{},{};         Expected an array of strings, but actual is '[{},{}]' (array of objects)!",
            "true,null,null;Expected an array of strings, but actual is '[true,null,null]' (array of booleans, nulls)!",
            "123,RED,true;  Expected an array of strings, but actual is '[123,\"RED\",true]' (array of numbers, strings, booleans)!",
            "true,[],{};    Expected an array of strings, but actual is '[true,[],{}]' (array of booleans, arrays, objects)!",
            "123,null,[];   Expected an array of strings, but actual is '[123,null,[]]' (array of numbers, nulls, arrays)!"
    })
    void toStringList_should_throw_ValidationException(final String value,
                                                       final String expectedError) {
        final Object params = getParams(value);

        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toStringList(params, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    private Object getParams(final String value) {
        if (value == null) {
            return null;
        } else {
            final List<Object> collect = List.of(value.split(",")).stream().map(this::convertValue).collect(Collectors.toList());
            return collect.size() == 1 ? collect.get(0) : collect;
        }
    }

    private Object convertValue(final String value) {
        if (value == null || "null".equals(value)) {
            return null;
        } else if ("true".equals(value) || "false".equals(value)) {
            return Boolean.parseBoolean(value);
        } else {
            try {
                return new JsonNumber(value);
            } catch (final NumberFormatException ex1) {
                if (value.startsWith("{")) {
                    return JsonHelper.readJsonObject(value);
                } else if (value.startsWith("[")) {
                    return JsonHelper.readJsonArray(value);
                } else {
                    return "empty".equals(value) ? "" : value;
                }
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    enum Color {

        RED,

        GREEN,

        BLUE
    }
}
