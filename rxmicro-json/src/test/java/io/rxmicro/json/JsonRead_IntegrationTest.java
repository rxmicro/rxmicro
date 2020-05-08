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

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.rxmicro.json.JsonData.COMPLEX_READABLE_JSON_ARRAY;
import static io.rxmicro.json.JsonData.COMPLEX_READABLE_JSON_OBJECT;
import static io.rxmicro.json.JsonData.EMPTY_JSON_ARRAY;
import static io.rxmicro.json.JsonData.EMPTY_JSON_OBJECT;
import static io.rxmicro.json.JsonData.getJsonStringFromClasspathResource;
import static io.rxmicro.json.JsonHelper.readJson;
import static io.rxmicro.json.JsonHelper.readJsonArray;
import static io.rxmicro.json.JsonHelper.readJsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author nedis
 *
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
        final String expected = "\r\t\n\u0090";
        assertEquals(expected, actual);
    }

    @Test
    @Order(11)
    void Should_read_escaped_quotation_marks_correctly() {
        final Object actual = readJson(getJsonStringFromClasspathResource("string-with-quotation-mark.json"));
        final String expected = "Hello \"Java\"";
        assertEquals(expected, actual);
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
