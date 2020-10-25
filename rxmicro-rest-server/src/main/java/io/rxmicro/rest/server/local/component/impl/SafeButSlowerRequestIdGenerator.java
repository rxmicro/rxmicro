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

package io.rxmicro.rest.server.local.component.impl;

import io.rxmicro.rest.server.feature.RequestIdGenerator;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

/**
 * Generates unique IDs independently of starting the JVM.
 *
 * As a unique value uses the {@link UUID#randomUUID()}.
 *
 * @author nedis
 * @since 0.1
 */
public final class SafeButSlowerRequestIdGenerator implements RequestIdGenerator {

    private static final int BYTE_ARRAY_BUFFER_SIZE = 16;

    private final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

    @Override
    public String getNextId() {
        final UUID uuid = UUID.randomUUID();
        final byte[] array = ByteBuffer.wrap(new byte[BYTE_ARRAY_BUFFER_SIZE])
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits())
                .array();
        return encoder.encodeToString(array);
    }
}
