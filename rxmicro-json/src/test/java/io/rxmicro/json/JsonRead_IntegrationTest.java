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
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.json.JsonData.COMPLEX_READABLE_JSON_ARRAY;
import static io.rxmicro.json.JsonData.COMPLEX_READABLE_JSON_OBJECT;
import static io.rxmicro.json.JsonData.EMPTY_JSON_ARRAY;
import static io.rxmicro.json.JsonData.EMPTY_JSON_OBJECT;
import static io.rxmicro.json.JsonData.getJsonStringFromClasspathResource;
import static io.rxmicro.json.JsonHelper.readJson;
import static io.rxmicro.json.JsonHelper.readJsonArray;
import static io.rxmicro.json.JsonHelper.readJsonObject;
import static io.rxmicro.json.internal.reader.JsonReader.readJsonPrimitive;
import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author nedis
 * @since 0.1
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class JsonRead_IntegrationTest {

    @Test
    @Order(1)
    void Should_read_empty_json_object_correctly_from_human_readable_string() {
        final Map<String, Object> actual = readJsonObject("{\n}");
        assertEquals(EMPTY_JSON_OBJECT, actual);
    }

    @Test
    @Order(2)
    void Should_read_empty_json_object_correctly_from_compact_string() {
        final Map<String, Object> actual = readJsonObject("{}");
        assertEquals(EMPTY_JSON_OBJECT, actual);
    }

    @Test
    @Order(3)
    void Should_read_empty_json_array_correctly_from_human_readable_string() {
        final List<Object> actual = readJsonArray("[\n]");
        assertEquals(EMPTY_JSON_ARRAY, actual);
    }

    @Test
    @Order(4)
    void Should_read_empty_json_array_correctly_from_compact_string() {
        final List<Object> actual = readJsonArray("[\n]");
        assertEquals(EMPTY_JSON_ARRAY, actual);
    }

    @Test
    @Order(5)
    void Should_read_complex_json_object_correctly_from_human_readable_string() {
        final Map<String, Object> actual = readJsonObject(
                getJsonStringFromClasspathResource("complex-object-human-readable.json"));
        assertEquals(COMPLEX_READABLE_JSON_OBJECT, actual);
    }

    @Test
    @Order(6)
    void Should_read_complex_json_object_correctly_from_compact_string() {
        final Map<String, Object> actual = readJsonObject(
                getJsonStringFromClasspathResource("complex-object-compact.json"));
        assertEquals(COMPLEX_READABLE_JSON_OBJECT, actual);
    }

    @Test
    @Order(7)
    void Should_read_complex_json_array_correctly_from_human_readable_string() {
        final List<Object> actual = readJsonArray(
                getJsonStringFromClasspathResource("complex-array-human-readable.json"));
        assertEquals(COMPLEX_READABLE_JSON_ARRAY, actual);
    }

    @Test
    @Order(8)
    void Should_read_complex_json_array_correctly_from_compact_string() {
        final List<Object> actual = readJsonArray(
                getJsonStringFromClasspathResource("complex-array-compact.json"));
        assertEquals(COMPLEX_READABLE_JSON_ARRAY, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(JsonPrimitiveArgumentsProvider.class)
    @Order(9)
    void Should_read_json_primitive_correctly(final String jsonPrimitive,
                                              final Object expectedJsonPrimitive) {
        final Object actual = readJson(jsonPrimitive);
        assertEquals(expectedJsonPrimitive, actual);
    }

    @Test
    @Order(10)
    void Should_read_escaped_characters_correctly() {
        final Object actual = readJson(getJsonStringFromClasspathResource("escape.json"));
        final String expected = "\r\t\n\u0090\\qwerty";
        assertEquals(expected, actual);
    }

    @Test
    @Order(11)
    void Should_read_escaped_quotation_marks_correctly() {
        final Object actual = readJson(getJsonStringFromClasspathResource("string-with-quotation-mark.json"));
        final String expected = "Hello \"Java\"";
        assertEquals(expected, actual);
    }

    @Test
    @Order(12)
    void Should_support_null_values() {
        final Map<String, Object> actual = readJsonObject("{\"property\":null}");
        assertEquals(singletonMap("property", null), actual);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            ";                                      Blank string is not valid json!",
            "{property:12};                         Expected '\"'. Index=1",
            "[12,];                                 Expected an array item, but actual is ']'! Index=4",
            "qwerty;                                qwerty is not a json number. Index=6",

            "[]];                                   Expected END_OF_JSON but actual is ']'. Index=2",
            "[]{};                                  Expected END_OF_JSON but actual is '{'. Index=2",
            "[]true;                                Expected END_OF_JSON but actual is 't'. Index=2",
            "[],;                                   Expected END_OF_JSON but actual is ','. Index=2",
            "{}};                                   Expected END_OF_JSON but actual is '}'. Index=2",
            "{}{};                                  Expected END_OF_JSON but actual is '{'. Index=2",
            "{}true;                                Expected END_OF_JSON but actual is 't'. Index=2",
            "{},;                                   Expected END_OF_JSON but actual is ','. Index=2",
            "true{;                                 Expected END_OF_JSON but actual is '{'. Index=4",
            "true[];                                Expected END_OF_JSON but actual is '['. Index=4",
            "true 12;                               Expected END_OF_JSON but actual is '1'. Index=5",
            "true,;                                 Expected END_OF_JSON but actual is ','. Index=4",
    })
    @Order(13)
    void readJson_should_throw_JsonException(final String json,
                                             final String expectedMessage) {
        final String validJson = json == null ? "" : json;
        final JsonException exception = assertThrows(JsonException.class, () -> readJson(validJson));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "string;   The provided string is not JSON object: string!",

            "{property:12};                         Expected '\"'. Index=1",
            "{\"property:12};                       Expected '\"' at the end of string",
            "{\"property\":\"value};                Expected '\"' at the end of string",
            "{\"property\";                         Expected ':'. Index=11",
            "{\"property\"};                        Expected ':'. Index=11",
            "{\"property\"true};                    Expected ':'. Index=11",
            "{\"property\":};                       Expected a value for the 'property' property, but actual is '}'! Index=12",
            "{\"property\";                         Expected ':'. Index=11",
            "{\"property\":true;                    Expected ',' or '}', but actual is 'END_OF_JSON'! Index=17",
            "{,\"property\":true};                  Expected '}' or a property name, but actual is ','! Index=1",
            "{\"property\":true,};                  Expected a property name, but actual is '}'! Index=17",

            "{\"k1\":true,\"k2\":{\"k3\":{}};       Expected ',' or '}', but actual is 'END_OF_JSON'! Index=25",

            "{\"k\":};                              Expected a value for the 'k' property, but actual is '}'! Index=5",
            "{\"k\":]};                             Expected a value for the 'k' property, but actual is ']'! Index=5",
            "{\"k\":,};                             Expected a value for the 'k' property, but actual is ','! Index=5",
            "{\"k\"::};                             Expected a value for the 'k' property, but actual is ':'! Index=5",

            "{\"k\":1]};                            Expected ',' or '}', but actual is ']'! Index=6",
            "{\"k\":1[};                            Expected ',' or '}', but actual is '['! Index=6",
            "{\"k\":1{};                            Expected ',' or '}', but actual is '{'! Index=6",

            "{\"k\":1:};                            Expected ',' or '}', but actual is ':'! Index=6"
    })
    @Order(14)
    void readJsonObject_should_throw_JsonException(final String json,
                                                   final String expectedMessage) {
        final JsonException exception = assertThrows(JsonException.class, () -> readJsonObject(json));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "string;                                The provided string is not JSON array: string!",

            "[12,];                                 Expected an array item, but actual is ']'! Index=4",
            "[12,qwerty];                           qwerty is not a json number. Index=9",
            "[{},];                                 Expected an array item, but actual is ']'! Index=4",
            "[{},qwerty];                           qwerty is not a json number. Index=9",
            "[[],];                                 Expected an array item, but actual is ']'! Index=4",
            "[[],qwerty];                           qwerty is not a json number. Index=9",
            "[,12];                                 Expected ']' or an array item, but actual is ','! Index=1",
            "[qwerty,12];                           qwerty is not a json number. Index=6",
            "[12,,12];                              Expected an array item, but actual is ','! Index=4",
            "[12,qwerty,12];                        qwerty is not a json number. Index=9",
            "[[[12,]]];                             Expected an array item, but actual is ']'! Index=6",
            "[[[12,qwerty]]];                       qwerty is not a json number. Index=11",

            "[12;                                   Expected ']' or ',', but actual is 'END_OF_JSON'! Index=4",
            "[[[12;                                 Expected ']' or ',', but actual is 'END_OF_JSON'! Index=6",
            "[[[12],[13]];                          Expected ']' or ',', but actual is 'END_OF_JSON'! Index=12",

            "[12[];                                 Expected ']' or ',', but actual is '['! Index=3",
            "[12{];                                 Expected ']' or ',', but actual is '{'! Index=3",
            "[12}];                                 Expected ']' or ',', but actual is '}'! Index=3",
            "[12 [];                                Expected ']' or ',', but actual is '['! Index=4",
            "[12 {];                                Expected ']' or ',', but actual is '{'! Index=4",
            "[12 }];                                Expected ']' or ',', but actual is '}'! Index=4"
    })
    @Order(15)
    void readJsonArray_should_throw_JsonException(final String json,
                                                  final String expectedMessage) {
        final JsonException exception = assertThrows(JsonException.class, () -> readJsonArray(json));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "{};                                    The provided string is not JSON primitive: {}!",
            "[];                                    The provided string is not JSON primitive: []!",

            "true{;                                 Expected END_OF_JSON but actual is '{'. Index=4",
            "true 1;                                Expected END_OF_JSON but actual is '1'. Index=5",
            "true \"string\";                       Expected END_OF_JSON but actual is '\"'. Index=5",
            "true [;                                Expected END_OF_JSON but actual is '['. Index=5",
            "true null;                             Expected END_OF_JSON but actual is 'n'. Index=5",

            "qwerty;                                qwerty is not a json number. Index=6"
    })
    @Order(16)
    void readJsonPrimitive_should_throw_JsonException(final String json,
                                                      final String expectedMessage) {
        final JsonException exception = assertThrows(JsonException.class, () -> readJsonPrimitive(json));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[[[]]]",
            "{\"k\":{\"k\":{}}}"
    })
    @Order(16)
    void readJson_should_throw_JsonException_with_increase_recursionDepth_recommendation(final String json) {
        final JsonException exception = assertThrows(JsonException.class, () -> readJson(json, 2));
        assertEquals(
                "The provided JSON contains a large number of nested items! Increase the recursionDepth parameter!",
                exception.getMessage()
        );
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "invalid-unicode1.json;     \\u009",
            "invalid-unicode2.json;     \\u009",
            "invalid-unicode3.json;     \\u009"
    })
    @Order(17)
    void readJson_should_throw_JsonException_if_detected_invalid_unicode_character(final String resourceName,
                                                                                   final String invalidString) {
        final JsonException exception = assertThrows(JsonException.class, () ->
                readJson(getJsonStringFromClasspathResource(resourceName)));
        assertEquals(
                format("Expected a valid Unicode character, but actual is '?'. Index=6", invalidString),
                exception.getMessage()
        );
    }

    @Test
    @Order(18)
    void readJson_should_ignore_all_delimiters() {
        assertTrue((boolean) readJson(((char) 0x0B) + " \u00A0\t\n\rtrue\r\n\t\u00A0 " + ((char) 0x0B)));
    }

    // Get more examples: https://github.com/nst/JSONTestSuite
    @ParameterizedTest
    @ValueSource(strings = {
            "[\"\\u0060\\u012a\\u12AB\"]",
            "[\"\\uD801\\udc37\"]",
            "[\"\\ud83d\\ude39\\ud83d\\udc8d\"]",
            "[\"\\uFFFF\"]",
            "[\"\\uDBFF\\uDFFF\"]",
            "[\"new\\u00A0line\"]",
            "[\"\\u002c\"]",
            "[\"\\uD834\\uDd1e\"]",
            "[\"\\u0061\\u30af\\u30EA\\u30b9\"]",
            "[\"new\\u000Aline\"]",
            "[\"\\uA66D\"]",
            "[\"\\u005C\"]",
            "[\"\\uDBFF\\uDFFE\"]",
            "[\"\\uD83F\\uDFFE\"]",
            "[\"\\u200B\"]",
            "[\"\\uFDD0\"]",
            "[\"\\uFFFE\"]",
            "{\"title\":\"\\u041f\\u043e\\u043b\\u0442\\u043e\\u0440\\u0430 \\u0417\\u0435\\u043c\\u043b\\u0435\\u043a\\u043e\\u043f\"}"
    })
    @Order(19)
    void readJson_should_parse_unicode_characters(final String json){
        assertDoesNotThrow(() -> readJson(json));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"a\":/*comment*/\"b\"}",
            "{\"a\":1//comment\n}"
    })
    @Order(20)
    void readJson_should_not_support_comments(final String json){
        assertThrows(JsonException.class, () -> readJson(json));
    }

    private static final class JsonPrimitiveArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                    arguments("null", null),
                    arguments("true", true),
                    arguments("false", false),
                    arguments("\"hello world\"", "hello world"),
                    arguments("9", new JsonNumber("9"))
            );
        }
    }
}
