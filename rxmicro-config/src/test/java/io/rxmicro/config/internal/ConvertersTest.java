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

import io.rxmicro.common.util.ExCollections;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;

import static io.rxmicro.config.internal.Converters.convertWithoutTypeDefinition;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author nedis
 * @since 0.7
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class ConvertersTest {

    static Stream<Arguments> convertWithoutTypeDefinitionArguments() {
        return Stream.of(
                arguments("true", true, true, Boolean.TRUE),
                arguments("false", true, true, Boolean.FALSE),
                arguments("a,b,c", true, true, List.of("a", "b", "c")),
                arguments("k1=v1,k2=v2", true, true, ExCollections.unmodifiableOrderedMap(new LinkedHashMap<>() {
                    {
                        put("k1", "v1");
                        put("k2", "v2");
                    }
                })),
                arguments("987654321", true, true, 987654321L),
                arguments("-987654321", true, true, -987654321L),
                arguments("+987654321", true, true, 987654321L),
                arguments("987654321987654321987654321987654321", true, true, new BigInteger("987654321987654321987654321987654321")),
                arguments("+987654321987654321987654321987654321", true, true, new BigInteger("987654321987654321987654321987654321")),
                arguments("-987654321987654321987654321987654321", true, true, new BigInteger("-987654321987654321987654321987654321")),
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
    void Should_convert_without_type_definition(final String value,
                                                final boolean supportsMap,
                                                final boolean supportsList,
                                                final Object expected) {
        assertEquals(expected, convertWithoutTypeDefinition(value, supportsMap, supportsList));
    }
}