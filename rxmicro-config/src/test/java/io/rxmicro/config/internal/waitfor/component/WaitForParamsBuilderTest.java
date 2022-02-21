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

package io.rxmicro.config.internal.waitfor.component;

import io.rxmicro.config.internal.waitfor.model.Params;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static io.rxmicro.config.internal.waitfor.component.WaitForParamsBuilder.buildWaitForParams;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nedis
 * @since 0.10
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class WaitForParamsBuilderTest {

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "localhost:12017;                                   [Params{type='tcp-socket', timeout=PT10S, destination='localhost:12017'}]",
            "--type=test localhost:12017;                       [Params{type='test', timeout=PT10S, destination='localhost:12017'}]",
            "--timeout=5 localhost:12017;                       [Params{type='tcp-socket', timeout=PT5S, destination='localhost:12017'}]",
            "--type=test --timeout=5 localhost:12017;           [Params{type='test', timeout=PT5S, destination='localhost:12017'}]",

            "localhost:12017 & localhost:12018; " +
                    "[" +
                    "Params{type='tcp-socket', timeout=PT10S, destination='localhost:12017'}, " +
                    "Params{type='tcp-socket', timeout=PT10S, destination='localhost:12018'}" +
                    "]",
            "localhost:12 & localhost:13 & localhost:14; " +
                    "[" +
                    "Params{type='tcp-socket', timeout=PT10S, destination='localhost:12'}, " +
                    "Params{type='tcp-socket', timeout=PT10S, destination='localhost:13'}, " +
                    "Params{type='tcp-socket', timeout=PT10S, destination='localhost:14'}" +
                    "]",
            "--timeout=5 localhost:12017 & --timeout=6 localhost:12018; " +
                    "[" +
                    "Params{type='tcp-socket', timeout=PT5S, destination='localhost:12017'}, " +
                    "Params{type='tcp-socket', timeout=PT6S, destination='localhost:12018'}" +
                    "]",
            "--type=test localhost:12017 & --type=qw localhost:12018; " +
                    "[" +
                    "Params{type='test', timeout=PT10S, destination='localhost:12017'}, " +
                    "Params{type='qw', timeout=PT10S, destination='localhost:12018'}" +
                    "]",
            "--type=test --timeout=5 localhost:12017 & --type=qw --timeout=6 localhost:12018; " +
                    "[" +
                    "Params{type='test', timeout=PT5S, destination='localhost:12017'}, " +
                    "Params{type='qw', timeout=PT6S, destination='localhost:12018'}" +
                    "]",
    })
    void Should_build_wait_for_params_correctly(final String params,
                                                final String expectedResult) {
        final List<String> paramsList = List.of(params.split(" "));
        final List<Params> expectedList = buildWaitForParams(paramsList);
        assertEquals(expectedResult, expectedList.toString());
    }
}