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

package io.rxmicro.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static io.rxmicro.common.util.Requires.require;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link https://rxmicro.io
 */
final class JsonData {

    static final Object CUSTOM_JAVA_OBJECT = new Object() {
        @Override
        public String toString() {
            return "CUSTOM_OBJECT.toString()";
        }
    };

    static final Map<String, Object> EMPTY_JSON_OBJECT = Map.of();

    static final List<Object> EMPTY_JSON_ARRAY = List.of();

    static final List<Object> COMPLEX_WRITABLE_JSON_ARRAY = new JsonArrayBuilder()
            .add(null)
            .add(true)
            .add(false)
            .add((byte) 1)
            .add((short) 2)
            .add(3)
            .add(4)
            .add(5.5f)
            .add(6.6)
            .add(new BigDecimal("23.0123456789"))
            .add(new BigInteger("12345678901234567890123456789012345678901234567890"))
            .add(new JsonNumber("12345678901234567890123456789012345678901234567890"))
            .add(new JsonObjectBuilder()
                    .put("number", 1)
                    .put("string", "Hi 1")
                    .put("nested", new JsonObjectBuilder()
                            .put("index", 1)
                            .put("boolean1", true)
                            .put("boolean2", false)
                            .build())
                    .build())
            .add(new JsonObjectBuilder()
                    .put("number", 2)
                    .put("string", "Hi 2")
                    .put("nested", new JsonObjectBuilder()
                            .put("index", 2)
                            .put("boolean1", true)
                            .put("boolean2", false)
                            .build())
                    .build())
            .add(new JsonArrayBuilder().build())
            .add(new JsonArrayBuilder()
                    .add(1)
                    .add(2)
                    .add(3)
                    .build())
            .build();

    static final List<Object> COMPLEX_READABLE_JSON_ARRAY = new JsonArrayBuilder()
            .add(null)
            .add(true)
            .add(false)
            .add(new JsonNumber("1"))
            .add(new JsonNumber("2"))
            .add(new JsonNumber("3"))
            .add(new JsonNumber("4"))
            .add(new JsonNumber("5.5"))
            .add(new JsonNumber("6.6"))
            .add(new JsonNumber("23.0123456789"))
            .add(new JsonNumber("12345678901234567890123456789012345678901234567890"))
            .add(new JsonNumber("12345678901234567890123456789012345678901234567890"))
            .add(new JsonObjectBuilder()
                    .put("number", new JsonNumber("1"))
                    .put("string", "Hi 1")
                    .put("nested", new JsonObjectBuilder()
                            .put("index", new JsonNumber("1"))
                            .put("boolean1", true)
                            .put("boolean2", false)
                            .build())
                    .build())
            .add(new JsonObjectBuilder()
                    .put("number", new JsonNumber("2"))
                    .put("string", "Hi 2")
                    .put("nested", new JsonObjectBuilder()
                            .put("index", new JsonNumber("2"))
                            .put("boolean1", true)
                            .put("boolean2", false)
                            .build())
                    .build())
            .add(new JsonArrayBuilder().build())
            .add(new JsonArrayBuilder()
                    .add(new JsonNumber("1"))
                    .add(new JsonNumber("2"))
                    .add(new JsonNumber("3"))
                    .build())
            .build();

    static final Map<String, Object> COMPLEX_WRITABLE_JSON_OBJECT = new JsonObjectBuilder()
            .put("boolean", true)
            .put("boolean", false)
            .put("byte", (byte) 1)
            .put("short", (short) 2)
            .put("int", 3)
            .put("long", 4)
            .put("float", 5.5f)
            .put("double", 6.6)
            .put("bigDecimal", new BigDecimal("23.0123456789"))
            .put("bigInteger", new BigInteger("12345678901234567890123456789012345678901234567890"))
            .put("jsonNumber", new JsonNumber("12345678901234567890123456789012345678901234567890"))
            .put("string", "Hello world")
            .put("instant", Instant.parse("2019-08-29T10:51:55Z"))
            .put("javaObject", CUSTOM_JAVA_OBJECT)
            .put("nested", new JsonObjectBuilder()
                    .put("number", 1)
                    .put("string", "Hi!!!")
                    .put("nested", new JsonObjectBuilder()
                            .put("boolean1", true)
                            .put("boolean2", false)
                            .build())
                    .build())
            .put("array", COMPLEX_WRITABLE_JSON_ARRAY)
            .build();

    static final Map<String, Object> COMPLEX_READABLE_JSON_OBJECT = new JsonObjectBuilder()
            .put("boolean", true)
            .put("boolean", false)
            .put("byte", new JsonNumber("1"))
            .put("short", new JsonNumber("2"))
            .put("int", new JsonNumber("3"))
            .put("long", new JsonNumber("4"))
            .put("float", new JsonNumber("5.5"))
            .put("double", new JsonNumber("6.6"))
            .put("bigDecimal", new JsonNumber("23.0123456789"))
            .put("bigInteger", new JsonNumber("12345678901234567890123456789012345678901234567890"))
            .put("jsonNumber", new JsonNumber("12345678901234567890123456789012345678901234567890"))
            .put("string", "Hello world")
            .put("instant", "2019-08-29T10:51:55Z")
            .put("javaObject", "CUSTOM_OBJECT.toString()")
            .put("nested", new JsonObjectBuilder()
                    .put("number", new JsonNumber("1"))
                    .put("string", "Hi!!!")
                    .put("nested", new JsonObjectBuilder()
                            .put("boolean1", true)
                            .put("boolean2", false)
                            .build())
                    .build())
            .put("array", COMPLEX_READABLE_JSON_ARRAY)
            .build();

    static String getJsonStringFromClasspathResource(final String classpathResource) {
        try (final InputStream is = JsonWrite_IntegrationTest.class.getClassLoader().getResourceAsStream(classpathResource)) {
            try (final InputStreamReader isr = new InputStreamReader(require(is), UTF_8);
                 final BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(joining("\n"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonData() {
    }
}
