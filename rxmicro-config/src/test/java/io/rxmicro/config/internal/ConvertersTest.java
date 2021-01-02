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

package io.rxmicro.config.internal;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.annotation.Retention;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedMap;
import static io.rxmicro.config.internal.Converters.convertToType;
import static io.rxmicro.config.internal.Converters.convertWithoutTypeDefinition;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author nedis
 * @since 0.7
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class ConvertersTest {

    static Stream<Arguments> convertWithoutTypeDefinitionArguments() {
        return Stream.of(
                arguments("true", true, true, Boolean.TRUE),
                arguments("false", true, true, Boolean.FALSE),
                arguments("a,b,c", true, true, List.of("a", "b", "c")),
                arguments("k1=v1,k2=v2", true, true, unmodifiableOrderedMap(new LinkedHashMap<>() {
                    {
                        put("k1", "v1");
                        put("k2", "v2");
                    }
                })),
                arguments("987654321", true, true, 987654321L),
                arguments("-987654321", true, true, -987654321L),
                arguments("+987654321", true, true, 987654321L),
                arguments("3.14", true, true, new BigDecimal("3.14")),
                arguments("-3.14", true, true, new BigDecimal("-3.14")),
                arguments("+3.14", true, true, new BigDecimal("+3.14")),
                arguments("3.14E+12", true, true, new BigDecimal("3.14E+12")),
                arguments("3.14e+12", true, true, new BigDecimal("3.14e+12")),
                arguments("-3.14E+12", true, true, new BigDecimal("-3.14E+12")),
                arguments("-3.14e+12", true, true, new BigDecimal("-3.14e+12")),
                arguments("+3.14E+12", true, true, new BigDecimal("+3.14E+12")),
                arguments("+3.14e+12", true, true, new BigDecimal("+3.14e+12")),
                arguments("3.14E-12", true, true, new BigDecimal("3.14E-12")),
                arguments("3.14e-12", true, true, new BigDecimal("3.14e-12")),
                arguments("-3.14E-12", true, true, new BigDecimal("-3.14E-12")),
                arguments("-3.14e-12", true, true, new BigDecimal("-3.14e-12")),
                arguments("+3.14E-12", true, true, new BigDecimal("+3.14E-12")),
                arguments("+3.14e-12", true, true, new BigDecimal("+3.14e-12")),
                arguments("hello world", true, true, "hello world"),
                arguments("hello.world.", true, true, "hello.world."),
                arguments("a,b,c", true, false, "a,b,c"),
                arguments("k1=v1,k2=v2", false, false, "k1=v1,k2=v2")
        );
    }

    @ParameterizedTest
    @MethodSource("convertWithoutTypeDefinitionArguments")
    @Order(1)
    void Should_convert_without_type_definition(final String value,
                                                final boolean supportsMap,
                                                final boolean supportsList,
                                                final Object expected) {
        assertEquals(expected, convertWithoutTypeDefinition(value, supportsMap, supportsList));
    }

    @Test
    @Order(2)
    void Should_convert_to_interface_type_using_defined_enum_constant() {
        final Object result = convertToType(TestInterface.class, "@io.rxmicro.config.internal.ConvertersTest$TestEnum:VALUE");
        assertSame(TestEnum.VALUE, result);
    }

    @Test
    @Order(3)
    void Should_convert_to_interface_type_using_defined_interface_constant() {
        final Object result = convertToType(TestInterface.class, "@io.rxmicro.config.internal.ConvertersTest$TestInterface:VALUE");
        assertSame(TestInterface.VALUE, result);
    }

    @Test
    @Order(4)
    void Should_convert_to_interface_type_using_defined_class_constant() {
        final Object result = convertToType(TestInterface.class, "@io.rxmicro.config.internal.ConvertersTest$TestClass:VALUE");
        assertSame(TestClass.VALUE, result);
    }

    @Test
    @Order(5)
    void Should_convert_to_interface_type_using_defined_annotation_constant() {
        final Object result = convertToType(TestInterface.class, "@io.rxmicro.config.internal.ConvertersTest$TestAnnotation:VALUE");
        assertSame(TestAnnotation.VALUE, result);
    }

    /**
     * @author nedis
     * @since 0.7.2
     */
    private interface TestInterface {

        TestInterface VALUE = new TestInterface() {

        };
    }

    /**
     * @author nedis
     * @since 0.7.2
     */
    private enum TestEnum implements TestInterface {

        VALUE
    }

    /**
     * @author nedis
     * @since 0.7.2
     */
    private static final class TestClass {

        public static final TestInterface VALUE = new TestInterface() {

        };
    }

    /**
     * @author nedis
     * @since 0.7.2
     */
    @Retention(RUNTIME)
    private @interface TestAnnotation {

        TestInterface VALUE = new TestInterface() {

        };
    }
}
