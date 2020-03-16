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

import io.rxmicro.rest.server.local.component.RequestIdGenerator;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Random;

/**
 * Example:
 * 62jJeu8x1310662,
 * 62jQa6Ux1584949
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class FasterButUnSafeRequestIdGenerator implements RequestIdGenerator {

    private static final Object MONITOR = new Object();

    private static final int DELTA_SIZE = 200;

    private final String start;

    private final short[] deltas;

    private long generator;

    private int index;

    public FasterButUnSafeRequestIdGenerator() {
        final byte[] array = getNowAsByteArray();
        this.start = Base64.getUrlEncoder().withoutPadding().encodeToString(array).replace("-", "_") + "x";
        this.generator = 1_000_000 + System.nanoTime() % 1_000_000;
        this.deltas = getRandomDeltas();
    }

    @Override
    public String getNextId() {
        final long shift;
        synchronized (MONITOR) {
            generator += deltas[index++];
            shift = generator;
            if (index == DELTA_SIZE) {
                index = 0;
            }
        }
        return start + shift;
    }

    private byte[] getNowAsByteArray() {
        final byte[] array = ByteBuffer.wrap(new byte[8])
                .putLong(Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMhhmmssSSS"))))
                .array();
        final int index = indexOfFirstNotNullItem(array);
        final byte[] result = new byte[8 - index];
        System.arraycopy(array, index, result, 0, result.length);
        return result;
    }

    private int indexOfFirstNotNullItem(final byte[] array) {
        int index = 0;
        for (; index < array.length; index++) {
            if (array[index] != 0) {
                break;
            }
        }
        return index;
    }

    private short[] getRandomDeltas() {
        final Random random = new Random();
        final short[] deltas = new short[DELTA_SIZE];
        for (int i = 0; i < deltas.length; i++) {
            deltas[i] = (short) (random.nextInt() % DELTA_SIZE + 1);
        }
        return deltas;
    }
}
