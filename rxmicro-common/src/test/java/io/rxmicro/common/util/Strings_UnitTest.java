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

package io.rxmicro.common.util;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static io.rxmicro.common.util.Strings.split;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nedis
 * @link http://rxmicro.io
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class Strings_UnitTest {

    @Test
    @Order(1)
    void split_should_convert_null_to_empty_list() {
        final List<String> actual = assertDoesNotThrow(() -> split(null, ","));
        assertEquals(List.of(), actual);
    }

    @Test
    @Order(2)
    void split_should_convert_empty_string_to_empty_list() {
        final List<String> actual = assertDoesNotThrow(() -> split("", ","));
        assertEquals(List.of(), actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            ",",
            ",,",
            ",,,",
            ",,,,",
            ",,,,,"
    })
    @Order(3)
    void split_should_convert_string_with_delimiters_only_to_empty_list(final String source) {
        final List<String> actual = assertDoesNotThrow(() -> split(source, ","));
        assertEquals(List.of(), actual);
    }

    @Test
    @Order(4)
    void split_should_return_a_singleton_list_if_delimiters_not_found() {
        final List<String> actual = assertDoesNotThrow(() -> split("value1", ","));
        assertEquals(List.of("value1"), actual);
    }

    @Test
    @Order(5)
    void split_should_split_by_delimiter_correctly() {
        final List<String> actual = assertDoesNotThrow(() -> split("value1,value2,value3", ","));
        assertEquals(List.of("value1", "value2", "value3"), actual);
    }

    @Test
    @Order(6)
    void split_should_ignore_a_last_comma() {
        final List<String> actual = assertDoesNotThrow(() -> split("value1,value2,value3,", ","));
        assertEquals(List.of("value1", "value2", "value3"), actual);
    }
}