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

package io.rxmicro.rest.client.netty.internal;

import io.netty.handler.codec.http.DefaultHttpHeaders;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author nedis
 * @since 0.8
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class NettyHttpHeadersTest {

    private final NettyHttpHeaders headers = new NettyHttpHeaders(new DefaultHttpHeaders().add("name", "value"));

    @Test
    @Order(1)
    void getValue() {
        assertEquals("value", headers.getValue("name"));
    }

    @Test
    @Order(2)
    void getValues() {
        assertEquals(List.of("value"), headers.getValues("name"));
    }

    @Test
    @Order(3)
    void contains() {
        assertTrue(headers.contains("name"));
    }

    @Test
    @Order(4)
    void getEntries() {
        assertEquals(List.of(entry("name", "value")), headers.getEntries());
    }

    @Test
    @Order(5)
    void size() {
        assertEquals(1, headers.size());
    }

    @Test
    @Order(6)
    void isNotEmpty_should_return_true() {
        assertTrue(headers.isNotEmpty());
    }

    @Test
    @Order(7)
    void isNotEmpty_should_return_false() {
        assertFalse(new NettyHttpHeaders(new DefaultHttpHeaders()).isNotEmpty());
    }

    @Test
    @Order(8)
    void testToString() {
        assertEquals("NettyHttpHeaders {name: value}", headers.toString());
    }
}