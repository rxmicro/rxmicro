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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.stream.Stream;

import static io.rxmicro.json.JsonData.COMPLEX_WRITABLE_JSON_ARRAY;
import static io.rxmicro.json.JsonData.COMPLEX_WRITABLE_JSON_OBJECT;
import static io.rxmicro.json.JsonData.CUSTOM_JAVA_OBJECT;
import static io.rxmicro.json.JsonData.EMPTY_JSON_ARRAY;
import static io.rxmicro.json.JsonData.EMPTY_JSON_OBJECT;
import static io.rxmicro.json.JsonData.getJsonStringFromClasspathResource;
import static io.rxmicro.json.JsonHelper.toJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author nedis
 * @link https://rxmicro.io
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class JsonWrite_IntegrationTest {

    @Test
    @Order(1)
    void Should_write_empty_json_object_correctly_at_the_human_readable_output_format() {
        final String actual = toJsonString(EMPTY_JSON_OBJECT, true);
        assertEquals("{\n}", actual);
    }

    @Test
    @Order(2)
    void Should_write_empty_json_object_correctly_and_at_the_compact_output_format() {
        final String actual = toJsonString(EMPTY_JSON_OBJECT, false);
        assertEquals("{}", actual);
    }

    @Test
    @Order(3)
    void Should_write_empty_json_array_correctly_and_at_the_human_readable_output_format() {
        final String actual = toJsonString(EMPTY_JSON_ARRAY, true);
        assertEquals("[\n]", actual);
    }

    @Test
    @Order(4)
    void Should_write_empty_json_array_correctly_and_at_the_compact_output_format() {
        final String actual = toJsonString(EMPTY_JSON_ARRAY, false);
        assertEquals("[]", actual);
    }

    @Test
    @Order(5)
    void Should_write_complex_json_object_correctly_and_at_the_human_readable_output_format() {
        final String actual = toJsonString(COMPLEX_WRITABLE_JSON_OBJECT, true);
        final String expected = getJsonStringFromClasspathResource("complex-object-human-readable.json");
        assertEquals(expected, actual);
    }

    @Test
    @Order(6)
    void Should_write_complex_json_object_correctly_and_at_the_compact_output_format() {
        final String actual = toJsonString(COMPLEX_WRITABLE_JSON_OBJECT, false);
        final String expected = getJsonStringFromClasspathResource("complex-object-compact.json");
        assertEquals(expected, actual);
    }

    @Test
    @Order(7)
    void Should_write_complex_json_array_correctly_and_at_the_human_readable_output_format() {
        final String actual = toJsonString(COMPLEX_WRITABLE_JSON_ARRAY, true);
        final String expected = getJsonStringFromClasspathResource("complex-array-human-readable.json");
        assertEquals(expected, actual);
    }

    @Test
    @Order(8)
    void Should_write_complex_json_array_correctly_and_at_the_compact_output_format() {
        final String actual = toJsonString(COMPLEX_WRITABLE_JSON_ARRAY, false);
        final String expected = getJsonStringFromClasspathResource("complex-array-compact.json");
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(JsonPrimitiveArgumentsProvider.class)
    @Order(9)
    void Should_write_json_primitive_correctly(final Object jsonPrimitive, final String expected) {
        final String actual = toJsonString(jsonPrimitive);
        assertEquals(expected, actual);
    }

    @Test
    @Order(10)
    void Should_write_escaped_characters_correctly() {
        final String actual = toJsonString("\r\t\n\u0090");
        final String expected = getJsonStringFromClasspathResource("escape.json");
        assertEquals(expected, actual);
    }

    private static final class JsonPrimitiveArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                    arguments(null, "null"),
                    arguments(true, "true"),
                    arguments(false, "false"),
                    arguments("hello world", "\"hello world\""),
                    arguments((byte) 1, "1"),
                    arguments((short) 2, "2"),
                    arguments('a', "\"a\""),
                    arguments(3, "3"),
                    arguments(4L, "4"),
                    arguments(5.5f, "5.5"),
                    arguments(6.6, "6.6"),
                    arguments(new BigInteger("7"), "7"),
                    arguments(new BigDecimal("8.8"), "8.8"),
                    arguments(new JsonNumber("9"), "9"),
                    arguments(CUSTOM_JAVA_OBJECT, "\"CUSTOM_OBJECT.toString()\"")
            );
        }
    }
}
