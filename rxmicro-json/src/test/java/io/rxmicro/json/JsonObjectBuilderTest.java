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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class JsonObjectBuilderTest {

    private final JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder(true);

    @SuppressWarnings("unchecked")
    @ParameterizedTest
    @Order(1)
    @ArgumentsSource(EmptyValuesArgumentsProvider.class)
    void put_should_ignore_null_and_empty_values(final Object value) {
        if (value instanceof Collection) {
            jsonObjectBuilder.put("empty", (Collection<?>) value);
        } else if (value instanceof Map) {
            jsonObjectBuilder.put("empty", (Map<String, ?>) value);
        } else {
            jsonObjectBuilder.put("empty", value);
        }
        assertEquals(Map.of(), jsonObjectBuilder.build());
    }

    private static final class EmptyValuesArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
            return Stream.of(
                    null,
                    Set.of(),
                    List.of(),
                    Map.of()
            ).map(Arguments::of);
        }
    }
}
