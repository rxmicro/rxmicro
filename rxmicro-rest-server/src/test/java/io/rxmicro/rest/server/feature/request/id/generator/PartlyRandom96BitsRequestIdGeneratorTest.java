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

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
final class PartlyRandom96BitsRequestIdGeneratorTest {

    @Mock
    private DeterministicValueProvider deterministicValueProvider;

    @Test
    void Should_generate_request_id_with_52_random_and_44_deterministic_bits() {
        when(deterministicValueProvider.getValue()).thenReturn(0x0A_1B_2C_3D_4E_5FL);
        final String actual = new PartlyRandom96BitsRequestIdGenerator(deterministicValueProvider).getNextId();
        assertEquals(16, actual.length());

        final byte[] bytes = Base64.getUrlDecoder().decode(actual);
        assertEquals(12, bytes.length);
        assertEquals((byte) 0x0A, (byte) (bytes[1] & 0x0F));
        assertEquals((byte) 0x1B, bytes[3]);
        assertEquals((byte) 0x2C, bytes[5]);
        assertEquals((byte) 0x3D, bytes[7]);
        assertEquals((byte) 0x4E, bytes[9]);
        assertEquals((byte) 0x5F, bytes[11]);
    }
}
