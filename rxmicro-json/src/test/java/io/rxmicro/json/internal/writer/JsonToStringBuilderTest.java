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

package io.rxmicro.json.internal.writer;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class JsonToStringBuilderTest {

    private final JsonToStringBuilder builder = new JsonToStringBuilder() {

        @Override
        JsonToStringBuilder nameSeparator() {
            return this;
        }

        @Override
        JsonToStringBuilder valueSeparator() {
            return this;
        }

        @Override
        JsonToStringBuilder tab(final int count) {
            return this;
        }

        @Override
        JsonToStringBuilder newLine() {
            return this;
        }
    };

    @Test
    @Order(1)
    void toString_should_return_built_string() {
        builder.beginObject().endObject();
        assertEquals(builder.build(), builder.toString());
    }
}