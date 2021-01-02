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

package io.rxmicro.rest.local;

import io.rxmicro.http.error.ValidationException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.rxmicro.rest.model.HttpModelType.PARAMETER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class FromStringValueConverterTest {

    private final FromStringValueConverter converter = new FromStringValueConverter() {

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
                assertDoesNotThrow(() -> converter.toEnum(Color.class, value, PARAMETER, "value"));
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;     Expected a value from the set [RED, GREEN, BLUE], but actual is ''!",
            "DAYS;      Expected a value from the set [RED, GREEN, BLUE], but actual is 'DAYS'!"
    })
    void toEnum_should_throw_ValidationException(final String value,
                                                 final String expectedError) {
        final String valueParam = "empty".equals(value) ? "" : value;
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toEnum(Color.class, valueParam, PARAMETER, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                  ",
            "RED;               RED",
            "RED,GREEN;         RED,GREEN",
            "RED|GREEN;         RED,GREEN",
            "RED|GREEN,BLUE;    RED,GREEN,BLUE"
    })
    void toEnumList_should_convert_value_successfully(final String list,
                                                      final String expected) {
        final List<String> headers = list == null ?
                null :
                List.of(list.split(","));
        final List<Color> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(Color::valueOf).collect(Collectors.toList());

        final List<Color> actualList =
                assertDoesNotThrow(() -> converter.toEnumList(Color.class, headers, PARAMETER, "value"));
        assertEquals(expectedList, actualList);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";          ",
            "true;      true",
            "false;     false"
    })
    void toBoolean_should_convert_value_successfully(final String value,
                                                     final Boolean expected) {
        final Boolean actual =
                assertDoesNotThrow(() -> converter.toBoolean(value, PARAMETER, "value"));
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;     Expected a boolean value ('true' or 'false'), but actual is ''!",
            "True;      Expected a boolean value ('true' or 'false'), but actual is 'True'!",
            "TRUE;      Expected a boolean value ('true' or 'false'), but actual is 'TRUE'!",
            "False;     Expected a boolean value ('true' or 'false'), but actual is 'False'!",
            "FALSE;     Expected a boolean value ('true' or 'false'), but actual is 'FALSE'!",
            "12.34;     Expected a boolean value ('true' or 'false'), but actual is '12.34'!",
            "12;        Expected a boolean value ('true' or 'false'), but actual is '12'!",
            "0;         Expected a boolean value ('true' or 'false'), but actual is '0'!",
            "1;         Expected a boolean value ('true' or 'false'), but actual is '1'!",
            "hello;     Expected a boolean value ('true' or 'false'), but actual is 'hello'!"
    })
    void toBoolean_should_throw_ValidationException(final String value,
                                                    final String expectedError) {
        final String valueParam = "empty".equals(value) ? "" : value;
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toBoolean(valueParam, PARAMETER, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                  ",
            "true;              true",
            "true,true;         true,true",
            "false|false;       false,false",
            "true|false,true;   true,false,true"
    })
    void toBooleanList_should_convert_value_successfully(final String list,
                                                         final String expected) {
        final List<String> headers = list == null ?
                null :
                List.of(list.split(","));
        final List<Boolean> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(Boolean::valueOf).collect(Collectors.toList());

        final List<Boolean> actualList =
                assertDoesNotThrow(() -> converter.toBooleanList(headers, PARAMETER, "value"));
        assertEquals(expectedList, actualList);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";          ",
            "127;       127",
            "-128;      -128"
    })
    void toByte_should_convert_value_successfully(final String value,
                                                  final Byte expected) {
        final Byte actual =
                assertDoesNotThrow(() -> converter.toByte(value, PARAMETER, "value"));
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;         Expected an integer value, but actual is ''!",
            "3.14159265;    Expected an integer value, but actual is '3.14159265'!",
            "-130;          Expected an integer value that >= '-128', but actual is '-130'!",
            "128;           Expected an integer value that <= '127', but actual is '128'!",
            "not_digits;    Expected an integer value, but actual is 'not_digits'!",
            "NaN;           Expected an integer value, but actual is 'NaN'!",
            "+NaN;          Expected an integer value, but actual is 'NaN'!",
            "-NaN;          Expected an integer value, but actual is 'NaN'!",
            "Infinity;      Expected an integer value that <= '127', but actual is 'Infinity'!",
            "+Infinity;     Expected an integer value that <= '127', but actual is '+Infinity'!",
            "-Infinity;     Expected an integer value that >= '-128', but actual is '-Infinity'!"
    })
    void toByte_should_throw_ValidationException(final String value,
                                                 final String expectedError) {
        final String valueParam = "empty".equals(value) ? "" : value;
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toByte(valueParam, PARAMETER, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";              ",
            "127;           127",
            "22,125;        22,125",
            "125|22;        125,22",
            "22|33,44;      22,33,44"
    })
    void toByteList_should_convert_value_successfully(final String list,
                                                      final String expected) {
        final List<String> headers = list == null ?
                null :
                List.of(list.split(","));
        final List<Byte> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(Byte::valueOf).collect(Collectors.toList());

        final List<Byte> actualList =
                assertDoesNotThrow(() -> converter.toByteList(headers, PARAMETER, "value"));
        assertEquals(expectedList, actualList);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                  ",
            "8765;              8765"
    })
    void toShort_should_convert_value_successfully(final String value,
                                                   final Short expected) {
        final Short actual =
                assertDoesNotThrow(() -> converter.toShort(value, PARAMETER, "value"));
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;         Expected an integer value, but actual is ''!",
            "3.14159265;    Expected an integer value, but actual is '3.14159265'!",
            "-43210;        Expected an integer value that >= '-32768', but actual is '-43210'!",
            "+43210;        Expected an integer value that <= '32767', but actual is '+43210'!",
            "not_digits;    Expected an integer value, but actual is 'not_digits'!",
            "NaN;           Expected an integer value, but actual is 'NaN'!",
            "+NaN;          Expected an integer value, but actual is 'NaN'!",
            "-NaN;          Expected an integer value, but actual is 'NaN'!",
            "Infinity;      Expected an integer value that <= '32767', but actual is 'Infinity'!",
            "+Infinity;     Expected an integer value that <= '32767', but actual is '+Infinity'!",
            "-Infinity;     Expected an integer value that >= '-32768', but actual is '-Infinity'!"
    })
    void toShort_should_throw_ValidationException(final String value,
                                                  final String expectedError) {
        final String valueParam = "empty".equals(value) ? "" : value;
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toShort(valueParam, PARAMETER, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                  ",
            "8765;              8765",
            "30123,256;         30123,256",
            "30123|256;         30123,256",
            "30123|256,8765;    30123,256,8765"
    })
    void toShortList_should_convert_value_successfully(final String list,
                                                       final String expected) {
        final List<String> headers = list == null ?
                null :
                List.of(list.split(","));
        final List<Short> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(Short::valueOf).collect(Collectors.toList());

        final List<Short> actualList =
                assertDoesNotThrow(() -> converter.toShortList(headers, PARAMETER, "value"));
        assertEquals(expectedList, actualList);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";              ",
            "2147483647;    2147483647"
    })
    void toInteger_should_convert_value_successfully(final String value,
                                                     final Integer expected) {
        final Integer actual =
                assertDoesNotThrow(() -> converter.toInteger(value, PARAMETER, "value"));
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;         Expected an integer value, but actual is ''!",
            "3.14159265;    Expected an integer value, but actual is '3.14159265'!",
            "-21474836478;  Expected an integer value that >= '-2147483648', but actual is '-21474836478'!",
            "+21474836478;  Expected an integer value that <= '2147483647', but actual is '+21474836478'!",
            "not_digits;    Expected an integer value, but actual is 'not_digits'!",
            "NaN;           Expected an integer value, but actual is 'NaN'!",
            "+NaN;          Expected an integer value, but actual is 'NaN'!",
            "-NaN;          Expected an integer value, but actual is 'NaN'!",
            "Infinity;      Expected an integer value that <= '2147483647', but actual is 'Infinity'!",
            "+Infinity;     Expected an integer value that <= '2147483647', but actual is '+Infinity'!",
            "-Infinity;     Expected an integer value that >= '-2147483648', but actual is '-Infinity'!"
    })
    void toInteger_should_throw_ValidationException(final String value,
                                                    final String expectedError) {
        final String valueParam = "empty".equals(value) ? "" : value;
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toInteger(valueParam, PARAMETER, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                          ",
            "2147483647;                2147483647",
            "327678,32767854;           327678,32767854",
            "32767854|327678;           32767854,327678",
            "327678|327679,3276787;     327678,327679,3276787"
    })
    void toIntegerList_should_convert_value_successfully(final String list,
                                                         final String expected) {
        final List<String> headers = list == null ?
                null :
                List.of(list.split(","));
        final List<Integer> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(Integer::valueOf).collect(Collectors.toList());

        final List<Integer> actualList =
                assertDoesNotThrow(() -> converter.toIntegerList(headers, PARAMETER, "value"));
        assertEquals(expectedList, actualList);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                  ",
            "22474836478902987; 22474836478902987"
    })
    void toLong_should_convert_value_successfully(final String value,
                                                  final Long expected) {
        final Long actual =
                assertDoesNotThrow(() -> converter.toLong(value, PARAMETER, "value"));
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;                 Expected an integer value, but actual is ''!",
            "3.14159265;            Expected an integer value, but actual is '3.14159265'!",
            "-9999999999999999999;  Expected an integer value that >= '-9223372036854775808', but actual is '-9999999999999999999'!",
            "9999999999999999999;   Expected an integer value that <= '9223372036854775807', but actual is '9999999999999999999'!",
            "not_digits;            Expected an integer value, but actual is 'not_digits'!",
            "NaN;           Expected an integer value, but actual is 'NaN'!",
            "+NaN;          Expected an integer value, but actual is 'NaN'!",
            "-NaN;          Expected an integer value, but actual is 'NaN'!",
            "Infinity;      Expected an integer value that <= '9223372036854775807', but actual is 'Infinity'!",
            "+Infinity;     Expected an integer value that <= '9223372036854775807', but actual is '+Infinity'!",
            "-Infinity;     Expected an integer value that >= '-9223372036854775808', but actual is '-Infinity'!"
    })
    void toLong_should_throw_ValidationException(final String value,
                                                 final String expectedError) {
        final String valueParam = "empty".equals(value) ? "" : value;
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toLong(valueParam, PARAMETER, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                                  ",
            "22474836478902987;                 22474836478902987",
            "2247483,22474836478902;            2247483,22474836478902",
            "224748364789|2247483;              224748364789,2247483",
            "2247483647|2247483647,2247483647;  2247483647,2247483647,2247483647"
    })
    void toLongList_should_convert_value_successfully(final String list,
                                                      final String expected) {
        final List<String> headers = list == null ?
                null :
                List.of(list.split(","));
        final List<Long> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(Long::valueOf).collect(Collectors.toList());

        final List<Long> actualList =
                assertDoesNotThrow(() -> converter.toLongList(headers, PARAMETER, "value"));
        assertEquals(expectedList, actualList);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                      ",
            "9999999999999999999;   9999999999999999999",
    })
    void toBigInteger_should_convert_value_successfully(final String value,
                                                        final BigInteger expected) {
        final BigInteger actual =
                assertDoesNotThrow(() -> converter.toBigInteger(value, PARAMETER, "value"));
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;         Expected an integer value, but actual is ''!",
            "3.14159265;    Expected an integer value, but actual is '3.14159265'!",
            "not_digits;    Expected an integer value, but actual is 'not_digits'!",
            "NaN;           Expected an integer value, but actual is 'NaN'!",
            "+NaN;          Expected an integer value, but actual is '+NaN'!",
            "-NaN;          Expected an integer value, but actual is '-NaN'!",
            "Infinity;      Expected an integer value, but actual is 'Infinity'!",
            "+Infinity;     Expected an integer value, but actual is '+Infinity'!",
            "-Infinity;     Expected an integer value, but actual is '-Infinity'!"
    })
    void toBigInteger_should_throw_ValidationException(final String value,
                                                       final String expectedError) {
        final String valueParam = "empty".equals(value) ? "" : value;
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toBigInteger(valueParam, PARAMETER, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                                                              ",
            "999999999999999999999;                                         999999999999999999999",
            "99999999999999999999,9999999999999999999;                      99999999999999999999,9999999999999999999",
            "9999999999999999999|99999999999999999999;                      9999999999999999999,99999999999999999999",
            "9999999999999999999|9999999999999999999,9999999999999999999;   9999999999999999999,9999999999999999999,9999999999999999999"
    })
    void toBigIntegerList_should_convert_value_successfully(final String list,
                                                            final String expected) {
        final List<String> headers = list == null ?
                null :
                List.of(list.split(","));
        final List<BigInteger> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(BigInteger::new).collect(Collectors.toList());

        final List<BigInteger> actualList =
                assertDoesNotThrow(() -> converter.toBigIntegerList(headers, PARAMETER, "value"));
        assertEquals(expectedList, actualList);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";        ",
            "3.14;    3.14"
    })
    void toFloat_should_convert_value_successfully(final String value,
                                                   final Float expected) {
        final Float actual =
                assertDoesNotThrow(() -> converter.toFloat(value, PARAMETER, "value"));
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;      Expected a decimal value, but actual is ''!",
            "not_digits; Expected a decimal value, but actual is 'not_digits'!",
            "-3.4E40;    Expected a decimal value that >= '-3.4028235E38', but actual is '-3.4E40'!",
            "3.4E40;     Expected a decimal value that <= '3.4028235E38', but actual is '3.4E40'!",
            "NaN;        Expected a decimal value, but actual is 'NaN'!"
    })
    void toFloat_should_throw_ValidationException(final String value,
                                                  final String expectedError) {
        final String valueParam = "empty".equals(value) ? "" : value;
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toFloat(valueParam, PARAMETER, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                      ",
            "3.14;                  3.14",
            "3.14,3.1415;           3.14,3.1415",
            "3.1415|3.14;           3.1415,3.14",
            "3.14|3.1415,3.141592;  3.14,3.1415,3.141592"
    })
    void toFloatList_should_convert_value_successfully(final String list,
                                                       final String expected) {
        final List<String> headers = list == null ?
                null :
                List.of(list.split(","));
        final List<Float> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(Float::valueOf).collect(Collectors.toList());

        final List<Float> actualList =
                assertDoesNotThrow(() -> converter.toFloatList(headers, PARAMETER, "value"));
        assertEquals(expectedList, actualList);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";        ",
            "3.14;    3.14"
    })
    void toDouble_should_convert_value_successfully(final String value,
                                                    final Double expected) {
        final Double actual =
                assertDoesNotThrow(() -> converter.toDouble(value, PARAMETER, "value"));
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;         Expected a decimal value, but actual is ''!",
            "not_digits;    Expected a decimal value, but actual is 'not_digits'!",
            "-1.2E310;      Expected a decimal value that >= '-1.7976931348623157E308', but actual is '-1.2E310'!",
            "1.79E310;      Expected a decimal value that <= '1.7976931348623157E308', but actual is '1.79E310'!",
            "NaN;           Expected a decimal value, but actual is 'NaN'!",
            "+NaN;          Expected a decimal value, but actual is 'NaN'!",
            "-NaN;          Expected a decimal value, but actual is 'NaN'!",
            "Infinity;      Expected a decimal value that <= '1.7976931348623157E308', but actual is 'Infinity'!",
            "+Infinity;     Expected a decimal value that <= '1.7976931348623157E308', but actual is '+Infinity'!",
            "-Infinity;     Expected a decimal value that >= '-1.7976931348623157E308', but actual is '-Infinity'!"
    })
    void toDouble_should_throw_ValidationException(final String value,
                                                   final String expectedError) {
        final String valueParam = "empty".equals(value) ? "" : value;
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toDouble(valueParam, PARAMETER, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                      ",
            "3.14;                  3.14",
            "3.14,3.1415;           3.14,3.1415",
            "3.1415|3.14;           3.1415,3.14",
            "3.14|3.1415,3.141592;  3.14,3.1415,3.141592"
    })
    void toDoubleList_should_convert_value_successfully(final String list,
                                                        final String expected) {
        final List<String> headers = list == null ?
                null :
                List.of(list.split(","));
        final List<Double> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(Double::valueOf).collect(Collectors.toList());

        final List<Double> actualList =
                assertDoesNotThrow(() -> converter.toDoubleList(headers, PARAMETER, "value"));
        assertEquals(expectedList, actualList);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                                      ",
            "3.1415926535897932384626433832795;     3.1415926535897932384626433832795",
    })
    void toBigDecimal_should_convert_value_successfully(final String value,
                                                        final BigDecimal expected) {
        final BigDecimal actual =
                assertDoesNotThrow(() -> converter.toBigDecimal(value, PARAMETER, "value"));
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;         Expected a decimal value, but actual is ''!",
            "not_digits;    Expected a decimal value, but actual is 'not_digits'!",
            "NaN;           Expected a decimal value, but actual is 'NaN'!",
            "+NaN;          Expected a decimal value, but actual is '+NaN'!",
            "-NaN;          Expected a decimal value, but actual is '-NaN'!",
            "Infinity;      Expected a decimal value, but actual is 'Infinity'!",
            "+Infinity;     Expected a decimal value, but actual is '+Infinity'!",
            "-Infinity;     Expected a decimal value, but actual is '-Infinity'!"
    })
    void toBigDecimal_should_throw_ValidationException(final String value,
                                                       final String expectedError) {
        final String valueParam = "empty".equals(value) ? "" : value;
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toBigDecimal(valueParam, PARAMETER, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                                                              ",
            "3.1415926535897932384626433832795;                             3.1415926535897932384626433832795",
            "3.1415926535897932384626,3.141592653589793238;                 3.1415926535897932384626,3.141592653589793238",
            "3.14159265358979323846|3.1415926535897932384626;               3.14159265358979323846,3.1415926535897932384626",
            "3.14159265358979323|3.14159265358979323,3.14159265358979323;   3.14159265358979323,3.14159265358979323,3.14159265358979323"
    })
    void toBigDecimalList_should_convert_value_successfully(final String list,
                                                            final String expected) {
        final List<String> headers = list == null ?
                null :
                List.of(list.split(","));
        final List<BigDecimal> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(BigDecimal::new).collect(Collectors.toList());

        final List<BigDecimal> actualList =
                assertDoesNotThrow(() -> converter.toBigDecimalList(headers, PARAMETER, "value"));
        assertEquals(expectedList, actualList);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                      ",
            "2020-01-15T10:25:45Z;  2020-01-15T10:25:45Z"
    })
    void toInstant_should_convert_value_successfully(final String value,
                                                     final Instant expected) {
        final Instant actual =
                assertDoesNotThrow(() -> converter.toInstant(value, PARAMETER, "value"));
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;                 Expected an ISO-8601 instant (Example: '1987-04-10T23:40:15.789Z'), but actual is ''!",
            "2020-13-15T10:25:45Z;  Expected an ISO-8601 instant (Example: '1987-04-10T23:40:15.789Z'), but actual is '2020-13-15T10:25:45Z'!",
    })
    void toInstant_should_throw_ValidationException(final String value,
                                                    final String expectedError) {
        final String valueParam = "empty".equals(value) ? "" : value;
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toInstant(valueParam, PARAMETER, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                                                              ",
            "2020-01-15T10:25:45Z;                                          2020-01-15T10:25:45Z",
            "2020-01-15T10:25:45Z,2020-01-15T10:25:45Z;                     2020-01-15T10:25:45Z,2020-01-15T10:25:45Z",
            "2020-01-15T10:25:45Z|2020-01-15T10:25:45Z;                     2020-01-15T10:25:45Z,2020-01-15T10:25:45Z",
            "2020-01-15T10:25:45Z|2020-01-15T10:25:45Z,2020-01-15T10:25:45Z;2020-01-15T10:25:45Z,2020-01-15T10:25:45Z,2020-01-15T10:25:45Z"
    })
    void toInstantList_should_convert_value_successfully(final String list,
                                                         final String expected) {
        final List<String> headers = list == null ?
                null :
                List.of(list.split(","));
        final List<Instant> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(Instant::parse).collect(Collectors.toList());

        final List<Instant> actualList =
                assertDoesNotThrow(() -> converter.toInstantList(headers, PARAMETER, "value"));
        assertEquals(expectedList, actualList);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";          ",
            "R;         R"
    })
    void toCharacter_should_convert_value_successfully(final String value,
                                                       final Character expected) {
        final Character actual =
                assertDoesNotThrow(() -> converter.toCharacter(value, PARAMETER, "value"));
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "empty;     Expected a character, but actual is ''!",
            "RG;        Expected a character, but actual is 'RG'!"
    })
    void toCharacter_should_throw_ValidationException(final String value,
                                                      final String expectedError) {
        final String valueParam = "empty".equals(value) ? "" : value;
        final ValidationException exception =
                assertThrows(ValidationException.class, () -> converter.toCharacter(valueParam, PARAMETER, "value"));
        assertEquals("Invalid parameter \"value\": " + expectedError, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";          ",
            "R;         R",
            "R,G;       R,G",
            "R|G;       R,G",
            "R|G,B;     R,G,B"
    })
    void toCharacterList_should_convert_value_successfully(final String list,
                                                           final String expected) {
        final List<String> headers = list == null ?
                null :
                List.of(list.split(","));
        final List<Character> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).map(s -> s.charAt(0)).collect(Collectors.toList());

        final List<Character> actualList =
                assertDoesNotThrow(() -> converter.toCharacterList(headers, PARAMETER, "value"));
        assertEquals(expectedList, actualList);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(strings = {
            "null",
            "",
            "test"
    })
    void toString_should_convert_value_successfully(final String value) {
        final String validValue = "null".equals(value) ? null : value;
        final String actual =
                assertDoesNotThrow(() -> converter.toString(validValue, PARAMETER, "value"));
        assertSame(validValue, actual);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                  ",
            "RED;               RED",
            "RED,GREEN;         RED,GREEN",
            "RED|GREEN;         RED,GREEN",
            "RED|GREEN,BLUE;    RED,GREEN,BLUE"
    })
    void toStringList_should_convert_value_successfully(final String list,
                                                        final String expected) {
        final List<String> headers = list == null ?
                null :
                List.of(list.split(","));
        final List<String> expectedList = expected == null ?
                List.of() :
                Arrays.stream(expected.split(",")).collect(Collectors.toList());

        final List<String> actualList =
                assertDoesNotThrow(() -> converter.toStringList(headers, PARAMETER, "value"));
        assertEquals(expectedList, actualList);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    @Test
    void throwNotImplYet_should_throw_UnsupportedOperationException() {
        final String message = "message";
        final UnsupportedOperationException exception =
                assertThrows(UnsupportedOperationException.class, () -> converter.throwNotImplYet(message));
        assertEquals(message, exception.getMessage());
    }

    // -------------------------------------------------------------------------------------------------------------------------------------

    enum Color {

        RED,

        GREEN,

        BLUE
    }
}
