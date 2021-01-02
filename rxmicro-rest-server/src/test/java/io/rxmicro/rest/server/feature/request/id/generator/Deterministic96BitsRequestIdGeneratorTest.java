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

package io.rxmicro.rest.server.feature.request.id.generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Base64;

import static io.rxmicro.rest.server.feature.request.id.generator.Deterministic96BitsRequestIdGenerator.DEFAULT_CHECKSUM_ALGORITHM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
final class Deterministic96BitsRequestIdGeneratorTest {

    @Mock
    private DeterministicValueProvider deterministicValueProvider;

    private Deterministic96BitsRequestIdGenerator deterministic96BitsRequestIdGenerator;

    @BeforeEach
    void beforeEach() {
        deterministic96BitsRequestIdGenerator = new Deterministic96BitsRequestIdGenerator(
                deterministicValueProvider,
                DEFAULT_CHECKSUM_ALGORITHM,
                1,
                0
        );
    }

    @Test
    @Order(1)
    void Should_generate_unique_values_if_deterministicValueProvider_returns_the_same_value() {
        when(deterministicValueProvider.getValue()).thenReturn(1L);
        final String first = deterministic96BitsRequestIdGenerator.getNextId();
        final String second = deterministic96BitsRequestIdGenerator.getNextId();
        assertNotEquals(first, second);
    }

    @Test
    @Order(2)
    void Should_generated_request_id_match_to_SD_DD_SS_DD_SS_DD_CC_SS_CC_DD_DD_CC_template() {
        when(deterministicValueProvider.getValue()).thenReturn(0x0A_1B_2C_3D_4E_5FL);

        final String actual = deterministic96BitsRequestIdGenerator.getNextId();
        assertEquals(16, actual.length());

        final byte[] bytes = Base64.getUrlDecoder().decode(actual);
        assertEquals(12, bytes.length);
        // Deterministic value:
        assertEquals((byte) 0x0A, (byte) (bytes[0] & 0x0F));
        assertEquals((byte) 0x1B, bytes[1]);
        assertEquals((byte) 0x2C, bytes[9]);
        assertEquals((byte) 0x3D, bytes[3]);
        assertEquals((byte) 0x4E, bytes[10]);
        assertEquals((byte) 0x5F, bytes[5]);
        // Counter value:
        assertEquals((byte) 0, bytes[6]);
        assertEquals((byte) 0, bytes[8]);
        assertEquals((byte) 0, bytes[11]);
    }
}
