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

package io.rxmicro.http.internal;

import io.rxmicro.common.RxMicroException;
import io.rxmicro.config.ConfigException;
import io.rxmicro.http.HttpConfig;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class HttpConfigExtractorTest {

    private final HttpConfig config = new HttpConfig("http") {

    };

    private final HttpConfigExtractor extractor = new HttpConfigExtractor();

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "http://localhost:8765;         HttpConfig {connectionString='http://localhost:8765'}",
            "http://localhost:80;           HttpConfig {connectionString='http://localhost'}",
            "https://localhost:80;          HttpConfig {connectionString='https://localhost:80'}",
            "https://localhost:443;         HttpConfig {connectionString='https://localhost'}",
            "https://localhost;             HttpConfig {connectionString='https://localhost'}",
            "localhost;                     HttpConfig {connectionString='http://localhost'}",
            "localhost:8080;                HttpConfig {connectionString='http://localhost:8080'}"
    })
    @Order(1)
    void Should_extract_correctly(final String source,
                                  final String expectedToStringResult) {
        assertDoesNotThrow(() -> extractor.extract(source, config));
        assertEquals(expectedToStringResult, config.toString());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "ftp://localhost:8765;      Unsupported protocol schema: 'ftp'! Only following schemas are supported: [http, https]",
            "http://localhost:DEFAULT;  Invalid port value: expected a number, but actual is 'DEFAULT'!",
            "http://localhost:-1;       Invalid configuration parameter \"http.port\": Expected that value >= 0, but actual is -1!",
            "http://localhost:65536;    Invalid configuration parameter \"http.port\": Expected that value <= 65535, but actual is 65536!",
            "http://localhost:99999;    Invalid configuration parameter \"http.port\": Expected that value <= 65535, but actual is 99999!"
    })
    @Order(2)
    void Should_throw_ConfigException(final String source,
                                      final String expectedMessage) {
        final RxMicroException exception = assertThrows(RxMicroException.class, () -> extractor.extract(source, config));
        assertEquals(expectedMessage, exception.getMessage());
    }
}
