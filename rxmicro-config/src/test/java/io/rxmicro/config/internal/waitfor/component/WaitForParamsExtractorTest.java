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

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static io.rxmicro.config.internal.waitfor.component.WaitForParamsExtractor.extractWaitForParams;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nedis
 * @since 0.10
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class WaitForParamsExtractorTest {

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "wait-for localhost:12017;                                  localhost:12017",
            "wait-for --type=tcp-socket localhost:12017;                --type=tcp-socket localhost:12017",
            "wait-for --timeout=30 localhost:12017;                     --timeout=30 localhost:12017",
            "wait-for --type=tcp-socket --timeout=30 localhost:12017;   --type=tcp-socket --timeout=30 localhost:12017",

            "wait-for localhost:12017 & localhost:12018;                localhost:12017 & localhost:12018",
            "wait-for localhost:12 & localhost:13 & localhost:14;       localhost:12 & localhost:13 & localhost:14",

            "wait-for --type=tcp-socket localhost:12017 & --type=tcp-socket localhost:12018; " +
                    "--type=tcp-socket localhost:12017 & --type=tcp-socket localhost:12018",
            "wait-for --timeout=30 localhost:12017 & --timeout=30 localhost:12018; " +
                    "--timeout=30 localhost:12017 & --timeout=30 localhost:12018",
            "wait-for --type=tcp-socket --timeout=30 localhost:12017 & --type=tcp-socket --timeout=30 localhost:12018; " +
                    "--type=tcp-socket --timeout=30 localhost:12017 & --type=tcp-socket --timeout=30 localhost:12018"
    })
    void Should_extract_wait_for_params_correctly(final String argsString,
                                                  final String expectedString) {
        final String[] argsParams = argsString.split(" ");
        final List<String> expected = List.of(expectedString.split(" "));
        assertEquals(expected, extractWaitForParams(argsParams));
    }

}