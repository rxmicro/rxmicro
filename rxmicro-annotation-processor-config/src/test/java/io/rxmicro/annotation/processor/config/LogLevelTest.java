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

package io.rxmicro.annotation.processor.config;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author nedis
 * @since 0.7.2
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class LogLevelTest {

    @ParameterizedTest
    @CsvSource({
            "INFO,   INFO",
            "DEBUG,  INFO",
            "DEBUG,  DEBUG",
            "TRACE,  INFO",
            "TRACE,  DEBUG",
            "TRACE,  TRACE"
    })
    @Order(1)
    void Should_log_be_enabled(final LogLevel actual,
                               final LogLevel expected) {
        assertTrue(actual.isEnabled(expected));
    }

    @ParameterizedTest
    @CsvSource({
            "OFF,   OFF",
            "OFF,   INFO",
            "OFF,   DEBUG",
            "OFF,   TRACE",
            "INFO,  DEBUG",
            "INFO,  TRACE",
            "DEBUG, TRACE",
    })
    @Order(1)
    void Should_log_be_disabled(final LogLevel actual,
                                final LogLevel expected) {
        assertFalse(actual.isEnabled(expected));
    }
}