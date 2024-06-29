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

import io.rxmicro.rest.server.feature.RequestIdGenerator;

import java.security.SecureRandom;
import java.util.Base64;

import static io.rxmicro.common.util.Formats.format;

/**
 * This request id generator returns random request id with specified bytes count.
 *
 * <p>
 * This request id generator uses the {@link SecureRandom} instance to get random bytes, so a generation of the next request id
 * can be block by operation system for example, if the entropy source is {@code /dev/random} on various Unix-like operating systems.
 * Read more: <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/security/SecureRandom.html">
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/security/SecureRandom.html
 * </a>
 *
 * <p>
 * Example of generated 12 bytes request ids:
 * <ul>
 *     <li>6f78_hbMCkQ2kwCi</li>
 *     <li>6zR64glSC7NSMrSI</li>
 *     <li>fdYMvHBsV0mT24ro</li>
 *     <li>SFmvLk5zzFbrCoLR</li>
 *     <li>MYXdsqph5__2dIFj</li>
 *     <li>h_eyys8n25vicD9y</li>
 * </ul>
 *
 * <p>
 * Example of generated 16 bytes request ids:
 * <ul>
 *     <li>FTM8EemdQbqCl4f83F8Jog</li>
 *     <li>Qc_vaNfBTOa_DZyeKeKPag</li>
 *     <li>phzrtGKUSiaJ6t8uXomDvA</li>
 *     <li>AeqdVgFlT52VJuQo-O64vw</li>
 *     <li>06YNzsnYSDGHM2Ij_X_ZeA</li>
 *     <li>JcU0PPoIR32I4d5GJy5Y6Q</li>
 * </ul>
 *
 * @author nedis
 * @see SecureRandom
 * @see Base64#getUrlEncoder()
 * @since 0.7.3
 */
public final class RandomRequestIdGenerator implements RequestIdGenerator {

    /**
     * Default request id size.
     */
    public static final byte DEFAULT_RANDOM_REQUEST_ID_LENGTH_IN_BYTES = 12;

    /**
     * Request id size for {@link java.util.UUID} random request id.
     *
     * @see java.util.UUID
     */
    public static final byte DEFAULT_RANDOM_UUID_REQUEST_ID_LENGTH_IN_BYTES = 16;

    private final int bytesCount;

    private final Base64.Encoder encoder;

    private final SecureRandom secureRandom;

    /**
     * Creates an instance of {@link RandomRequestIdGenerator} with the specified bytes count.
     *
     * @param bytesCount the specified bytes count
     * @throws IllegalArgumentException if {@code bytesCount} is negative or {@code 0}.
     */
    public RandomRequestIdGenerator(final byte bytesCount) {
        if (bytesCount <= 0) {
            throw new IllegalArgumentException(format(
                    "Invalid bytes count: Expected a value between 1 and ? but actual is ?",
                    Byte.MAX_VALUE, bytesCount
            ));
        }
        this.bytesCount = bytesCount;
        this.encoder = Base64.getUrlEncoder().withoutPadding();
        this.secureRandom = new SecureRandom();
    }

    @Override
    public String getNextId() {
        final byte[] bytes = new byte[bytesCount];
        secureRandom.nextBytes(bytes);
        return encoder.encodeToString(bytes);
    }

    @Override
    public String toString() {
        return "RandomRequestIdGenerator{" +
                "bytesCount=" + bytesCount +
                '}';
    }
}
