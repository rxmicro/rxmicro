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
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static io.rxmicro.common.util.Environments.resolveVariables;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.6
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class EnvironmentsTest {

    private static final Map<String, String> VARIABLES = Map.of("HOME", "/home/rxmicro");

    @CsvSource({
            "/home/rxmicro/config.json,     /home/rxmicro/config.json",
            "${HOME}/config.json,           /home/rxmicro/config.json",
            "${HOME},                       /home/rxmicro",
            "${HOME}/tmp${HOME},            /home/rxmicro/tmp/home/rxmicro"
    })
    @ParameterizedTest
    void Should_resolve_variables(final String expression,
                                  final String expectedResult) {
        assertEquals(
                expectedResult,
                resolveVariables(expression, VARIABLES)
        );
    }

    @CsvSource({
            "${UNDEFINED},              Invalid expression: '${UNDEFINED}'. Variable 'UNDEFINED' not defined!",
            "{HOME}/config.json,        Invalid expression: '{HOME}/config.json'. Missing '$' at 0 position",
            "${HOME,                    Invalid expression: '${HOME'. Missing '}'",
            "$,                         Invalid expression: '$'. Expected '{' after '$' at 1 position!",
            "$HOME,                     Invalid expression: '$HOME'. Expected '{' after '$' at 1 position!",
            "}/config.json,             Invalid expression: '}/config.json'. Missing '$' at 0 position",
            "${}/config.json,           Invalid expression: '${}/config.json'. Missing variable name at 2 position!",
            "/tmp/${UNDEFINED},         Invalid expression: '/tmp/${UNDEFINED}'. Variable 'UNDEFINED' not defined!",
            "/tmp/{HOME}/config.json,   Invalid expression: '/tmp/{HOME}/config.json'. Missing '$' at 5 position",
            "/tmp/${HOME,               Invalid expression: '/tmp/${HOME'. Missing '}'",
            "/tmp/$,                    Invalid expression: '/tmp/$'. Expected '{' after '$' at 6 position!",
            "/tmp/$HOME,                Invalid expression: '/tmp/$HOME'. Expected '{' after '$' at 6 position!",
            "/tmp/}/config.json,        Invalid expression: '/tmp/}/config.json'. Missing '$' at 5 position",
            "/tmp/${}/config.json,      Invalid expression: '/tmp/${}/config.json'. Missing variable name at 7 position!"
    })
    @ParameterizedTest
    void Should_throw_IllegalArgumentException(final String expression,
                                               final String expectedMessage) {
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> resolveVariables(expression, VARIABLES));
        assertEquals(expectedMessage, exception.getMessage());
    }
}