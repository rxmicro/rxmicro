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

package io.rxmicro.config.internal.waitfor;

import io.rxmicro.config.ConfigException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.rxmicro.config.internal.waitfor.WaitForServiceFactory.createWaitForService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class WaitForServiceFactoryTest {

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "wait-for;                                      Expected destination. For example: java Main.class wait-for localhost:8080",
            "wait-for --type=unsupported;                   Expected destination. For example: java Main.class wait-for localhost:8080",
            "wait-for --type=unsupported localhost:8080;    Unsupported type: 'unsupported'!",
            "WAIT_FOR localhost:8080;                       Invalid command line argument: 'WAIT_FOR'. Use 'wait-for' instead!",

            "wait-for --type=1 --type=2 localhost:8080;     Detected a duplicate of parameter: name=type, value1=2, value2=1",
            "wait-for --timeout:5s localhost:8080;          Invalid parameter expression. Expected '--${name}=${value}', " +
                    "but actual is '--timeout:5s'",
            "wait-for --timeout=5s localhost:8080;          Invalid timeout value: '5s'! Must be a parsable by " +
                    "java.time.Duration.parse(String) method duration!"
    })
    @Order(1)
    void Should_throw_ConfigException(final String args,
                                      final String expectedMessage) {
        final ConfigException exception =
                assertThrows(ConfigException.class, () -> createWaitForService(args.split(" ")));
        assertEquals(expectedMessage, exception.getMessage());
    }
}