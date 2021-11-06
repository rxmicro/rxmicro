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

package io.rxmicro.resource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.condition.OS.LINUX;
import static org.junit.jupiter.api.condition.OS.MAC;

/**
 * @author nedis
 * @since 0.9
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class PathsTest {

    private static final String ORIGINAL_HOME = System.getProperty("user.home");

    private static final String ORIGINAL_CURRENT = System.getProperty("user.dir");

    @BeforeAll
    static void beforeAll() {
        System.setProperty("user.home", "/home/rxmicro");
        System.setProperty("user.dir", "/tmp/work-dir");
    }

    @EnabledOnOs(value = {LINUX, MAC}, disabledReason = "This test verifies path using Unix style only!")
    @Order(1)
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "~;                         /home/rxmicro",
            ".;                         /tmp/work-dir",
            "~/jul.properties;          /home/rxmicro/jul.properties",
            "~jul.properties;           /home/rxmicrojul.properties",
            "./jul.properties;          /tmp/work-dir/jul.properties",
            "/tmp/work-dir/jul.txt;     /tmp/work-dir/jul.txt"
    })
    void method_createPath_should_create_absolute_path_with_supported_variables(final String path,
                                                                                final String expectedResult) {
        assertEquals(expectedResult, Paths.createPath(path).toString());
    }

    @AfterAll
    static void afterAll() {
        System.setProperty("user.home", ORIGINAL_HOME);
        System.setProperty("user.dir", ORIGINAL_CURRENT);
    }
}