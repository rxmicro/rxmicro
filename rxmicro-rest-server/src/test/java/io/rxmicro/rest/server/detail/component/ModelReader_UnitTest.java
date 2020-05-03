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

package io.rxmicro.rest.server.detail.component;

import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ModelReader_UnitTest {

    private final ModelReader<Object> reader = new ModelReader<>() {
        @Override
        public Object read(final PathVariableMapping pathVariableMapping,
                           final HttpRequest request,
                           final boolean readParametersFromBody) {
            throw new UnsupportedOperationException();
        }
    };

    @Order(1)
    @ParameterizedTest
    @ArgumentsSource(QueryParamsProvider.class)
    void extractParams_should_extract_query_params_correctly(final String query,
                                                             final String expected) {
        assertEquals(
                expected,
                reader.extractParams(query).toString()
        );
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    private static class QueryParamsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                    arguments(
                            "param1=value1",
                            "param1=value1"
                    ),
                    arguments(
                            "param1=value1&param2=value2",
                            "param1=value1&param2=value2"
                    ),
                    arguments(
                            "param1=value1&param2=value2&param3=value3",
                            "param1=value1&param2=value2&param3=value3"
                    ),
                    arguments(
                            "param1=" + encode("Value is = &12", UTF_8),
                            "param1=Value is = &12"
                    ),
                    arguments(
                            "param1=+2+",
                            "param1= 2 "
                    ),
                    arguments(
                            "param1=value1&param1=value2&param1=value3",
                            "param1=value1&param1=value2&param1=value3"
                    ),
                    arguments(
                            "param1=value1&param1=&param1=",
                            "param1=value1"
                    ),
                    arguments(
                            "param1=value1&",
                            "param1=value1"
                    ),
                    arguments(
                            "param1=value1&param1=",
                            "param1=value1"
                    ),
                    arguments(
                            "param1=value1&param1",
                            "param1=value1"
                    )
            );
        }
    }
}