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

package io.rxmicro.test.dbunit.internal.data.type;

import io.rxmicro.config.Configs;
import org.dbunit.dataset.datatype.TypeCastException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nedis
 * @since 0.7
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class RxMicroTimestampDataTypeTest {

    public static final Instant EXPECTED_INSTANT = Instant.parse("2020-10-30T16:36:09Z");

    private final RxMicroTimestampDataType dataType = new RxMicroTimestampDataType();

    @BeforeAll
    static void beforeAll() {
        new Configs.Builder().build();
    }

    @Test
    @Order(1)
    void parseString_should_support_ISO_instant_format() throws TypeCastException {
        final Object actual = dataType.parseString("2020-10-30T16:36:09Z");
        assertEquals(Timestamp.from(EXPECTED_INSTANT), actual);
    }

    @Test
    @Order(2)
    void parseString_should_support_yyyy_MM_dd_HH_mm_ss_format_using_configured_timezone() throws TypeCastException {
        final Object actual = dataType.parseString("2020-10-30 16:36:09");
        assertEquals(Timestamp.from(EXPECTED_INSTANT), actual);
    }
}
