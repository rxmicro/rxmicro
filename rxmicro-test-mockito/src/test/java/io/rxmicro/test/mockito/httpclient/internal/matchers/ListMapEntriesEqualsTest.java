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

package io.rxmicro.test.mockito.httpclient.internal.matchers;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author nedis
 *
 * @since 0.1
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class ListMapEntriesEqualsTest {

    private final ListMapEntriesEquals listMapEntriesEquals = new ListMapEntriesEquals(
            List.of(
                    entry("H1", "V1"),
                    entry("H1", "V1"),
                    entry("H1", "V2"),
                    entry("H2", "V2")
            )
    );

    @ParameterizedTest
    @CsvSource({
            "H1=V1;H1=V1;H1=V2;H2=V2",
            "H1=V1;H1=V2;H2=V2;H1=V1",
            "H1=V2;H2=V2;H1=V1;H1=V1",
            "H1=V2;H1=V1;H1=V1;H2=V2"
    })
    @Order(1)
    void Should_return_true(final String data) {
        final List<Map.Entry<String, String>> actual = Arrays.stream(data.split(";"))
                .map(pair -> pair.split("="))
                .map(array -> entry(array[0], array[1]))
                .collect(Collectors.toList());
        assertTrue(listMapEntriesEquals.matches(actual));
    }

    @ParameterizedTest
    @CsvSource({
            "H1=V1",
            "H1=V1;H2=V2",
            "H1=V1;H1=V1;H2=V2",
            "H1=V1;H1=V1;H1=V1;H1=V2;H2=V2",
            "H1=V1;H1=V1;H1=V2;H1=V2;H2=V2",
            "H1=V1;H1=V1;H1=V2;H2=V2;H2=V2",
    })
    @Order(2)
    void Should_return_false(final String data) {
        final List<Map.Entry<String, String>> actual = Arrays.stream(data.split(";"))
                .map(pair -> pair.split("="))
                .map(array -> entry(array[0], array[1]))
                .collect(Collectors.toList());
        assertFalse(listMapEntriesEquals.matches(actual));
    }
}