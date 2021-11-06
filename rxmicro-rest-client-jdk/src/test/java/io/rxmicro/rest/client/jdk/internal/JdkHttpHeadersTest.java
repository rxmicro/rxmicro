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

package io.rxmicro.rest.client.jdk.internal;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author nedis
 * @since 0.8
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class JdkHttpHeadersTest {

    private final JdkHttpHeaders headers = new JdkHttpHeaders(
            HttpHeaders.of(
                    Map.of("name", List.of("value"), "empty", List.of()),
                    (s1, s2) -> true
            )
    );

    @Test
    @Order(1)
    void method_getValue_should_return_value() {
        assertEquals("value", headers.getValue("name"));
    }

    @Test
    @Order(2)
    void method_getValue_should_return_null() {
        assertNull(headers.getValue("not_found"));
        assertNull(headers.getValue("empty"));
    }

    @Test
    @Order(3)
    void method_getValues() {
        assertEquals(List.of("value"), headers.getValues("name"));
    }

    @Test
    @Order(4)
    void method_contains() {
        assertTrue(headers.contains("name"));
    }

    @Test
    @Order(5)
    void method_getEntries() {
        assertEquals(List.of(entry("name", "value")), headers.getEntries());
    }

    @Test
    @Order(6)
    void method_size() {
        assertEquals(1, headers.size());
    }

    @Test
    @Order(7)
    void method_isNotEmpty_should_return_true() {
        assertTrue(headers.isNotEmpty());
    }

    @Test
    @Order(8)
    void method_isNotEmpty_should_return_false() {
        assertFalse(new JdkHttpHeaders(HttpHeaders.of(Map.of(), (s1, s2) -> true)).isNotEmpty());
    }

    @Test
    @Order(9)
    void method_toString() {
        assertEquals("JdkHttpHeaders {name: value}", headers.toString());
    }
}