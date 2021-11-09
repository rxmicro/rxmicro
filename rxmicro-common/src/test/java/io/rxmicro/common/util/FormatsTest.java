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

package io.rxmicro.common.util;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.rxmicro.common.util.Formats.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nedis
 * @since 0.10
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class FormatsTest {

    @Order(1)
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "(?)[?] - ?, ?;             1,2,3,4;        (1)[2] - 3, 4",
            "(?)[?] - ?, ?;             1,2;            (1)[2] - ?, ?",
            "null;                      1,2,3,4;        null -> Unused arguments: [1, 2, 3, 4]",
            "(?)[?];                    1,2,3,4;        (1)[2] -> Unused arguments: [3, 4]"
    })
    void method_format_should_format_message_with_args_correctly(final String template,
                                                                 final String argsSeparatedByComma,
                                                                 final String expected) {
        final Object[] args = argsSeparatedByComma.split(",");
        assertEquals(expected, format("null".equals(template) ? null : template, args));
    }

    @Order(2)
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "(?)[?] - ?, ?;             (?)[?] - ?, ?",
            "null;                      null",
            "1,2,3,4;                   1,2,3,4"
    })
    void method_format_should_format_message_without_args_correctly(final String template,
                                                                    final String expected) {
        assertEquals(expected, format("null".equals(template) ? null : template));
    }

    @Test
    @Order(3)
    @EnabledOnOs({OS.LINUX, OS.MAC})
    void method_format_with_args_should_use_system_depend_lineSeparator_for_Linux_and_Mac() {
        assertEquals("(1)[2]\n - 3, 4\n", format("(?)[?]\n - ?, ?\n", "1", "2", "3", "4"));
    }

    @Test
    @Order(4)
    @EnabledOnOs({OS.LINUX, OS.MAC})
    void method_format_without_args_should_use_system_depend_lineSeparator_for_Linux_and_Mac() {
        assertEquals("(?)[?]\n - ?, ?\n", format("(?)[?]\n - ?, ?\n"));
    }

    @Test
    @Order(5)
    @EnabledOnOs({OS.LINUX, OS.MAC})
    void method_format_without_args_should_return_null_string_if_provided_template_is_null_for_Linux_and_Mac() {
        assertEquals("null", format((String) null));
    }

    @Test
    @Order(6)
    @EnabledOnOs(OS.WINDOWS)
    void method_format_with_args_should_use_system_depend_lineSeparator_for_Windows() {
        assertEquals("(1)[2]\r\n - 3, 4\r\n", format("(?)[?]\n - ?, ?\n", "1", "2", "3", "4"));
    }

    @Test
    @Order(7)
    @EnabledOnOs(OS.WINDOWS)
    void method_format_without_args_should_use_system_depend_lineSeparator_for_Windows() {
        assertEquals("(?)[?]\r\n - ?, ?\r\n", format("(?)[?]\n - ?, ?\n"));
    }

    @Test
    @Order(8)
    @EnabledOnOs(OS.WINDOWS)
    void method_format_without_args_should_return_null_string_if_provided_template_is_null_for_Windows() {
        assertEquals("null", format((String) null));
    }
}