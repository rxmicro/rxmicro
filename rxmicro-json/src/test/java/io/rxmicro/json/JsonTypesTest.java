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

import java.util.List;
import java.util.Map;

import static io.rxmicro.json.JsonTypes.asJsonArray;
import static io.rxmicro.json.JsonTypes.asJsonBoolean;
import static io.rxmicro.json.JsonTypes.asJsonNumber;
import static io.rxmicro.json.JsonTypes.asJsonObject;
import static io.rxmicro.json.JsonTypes.asJsonString;
import static io.rxmicro.json.JsonTypes.isJsonArray;
import static io.rxmicro.json.JsonTypes.isJsonBoolean;
import static io.rxmicro.json.JsonTypes.isJsonNumber;
import static io.rxmicro.json.JsonTypes.isJsonObject;
import static io.rxmicro.json.JsonTypes.isJsonString;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class JsonTypesTest {

    @Test
    @Order(1)
    void isJsonObject_should_return_true() {
        assertTrue(isJsonObject(Map.of()));
    }

    @Test
    @Order(2)
    void isJsonObject_should_return_false() {
        assertFalse(isJsonObject(List.of()));
    }

    @Test
    @Order(3)
    void asJsonObject_should_convert_correctly() {
        assertDoesNotThrow(() -> asJsonObject(Map.of()));
    }

    @Test
    @Order(4)
    void asJsonObject_should_throw_JsonException() {
        final JsonException exception = assertThrows(JsonException.class, () -> asJsonObject("string"));
        assertEquals("Not a json object: string", exception.getMessage());
    }

    @Test
    @Order(5)
    void isJsonArray_should_return_true() {
        assertTrue(isJsonArray(List.of()));
    }

    @Test
    @Order(6)
    void isJsonArray_should_return_false() {
        assertFalse(isJsonArray(Map.of()));
    }

    @Test
    @Order(7)
    void asJsonArray_should_convert_correctly() {
        assertDoesNotThrow(() -> asJsonArray(List.of()));
    }

    @Test
    @Order(8)
    void asJsonArray_should_throw_JsonException() {
        final JsonException exception = assertThrows(JsonException.class, () -> asJsonArray("string"));
        assertEquals("Not a json array: string", exception.getMessage());
    }

    @Test
    @Order(9)
    void isJsonString_should_return_true() {
        assertTrue(isJsonString("string"));
    }

    @Test
    @Order(10)
    void isJsonString_should_return_false() {
        assertFalse(isJsonString(true));
    }

    @Test
    @Order(11)
    void asJsonString_should_convert_correctly() {
        assertDoesNotThrow(() -> asJsonString("string"));
    }

    @Test
    @Order(12)
    void asJsonString_should_throw_JsonException() {
        final JsonException exception = assertThrows(JsonException.class, () -> asJsonString(true));
        assertEquals("Not a json string: true", exception.getMessage());
    }

    @Test
    @Order(13)
    void isJsonNumber_should_return_true() {
        assertTrue(isJsonNumber(new JsonNumber("1")));
    }

    @Test
    @Order(14)
    void isJsonNumber_should_return_false() {
        assertFalse(isJsonNumber("string"));
    }

    @Test
    @Order(15)
    void asJsonNumber_should_convert_correctly() {
        assertDoesNotThrow(() -> asJsonNumber(new JsonNumber("1")));
    }

    @Test
    @Order(16)
    void asJsonNumber_should_throw_JsonException() {
        final JsonException exception = assertThrows(JsonException.class, () -> asJsonNumber("string"));
        assertEquals("Not a json number: string", exception.getMessage());
    }

    @Test
    @Order(17)
    void isJsonBoolean_should_return_true() {
        assertTrue(isJsonBoolean(true));
    }

    @Test
    @Order(18)
    void isJsonBoolean_should_return_false() {
        assertFalse(isJsonBoolean("string"));
    }

    @Test
    @Order(19)
    void asJsonBoolean_should_convert_correctly() {
        assertDoesNotThrow(() -> asJsonBoolean(true));
    }

    @Test
    @Order(20)
    void asJsonBoolean_should_throw_JsonException() {
        final JsonException exception = assertThrows(JsonException.class, () -> asJsonBoolean("string"));
        assertEquals("Not a json boolean: string", exception.getMessage());
    }
}