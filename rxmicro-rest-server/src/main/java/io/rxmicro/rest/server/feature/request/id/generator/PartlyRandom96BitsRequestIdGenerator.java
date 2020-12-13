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

/**
 * This request id generator returns 96 bit request id with 52 random and 44 deterministic bits.
 *
 * <p>
 * This request id generator uses the {@link SecureRandom} instance to get random bytes, so a generation of the next request id
 * can be block by operation system for example, if the entropy source is {@code /dev/random} on various Unix-like operating systems.
 * Read more: <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/security/SecureRandom.html">
 *     https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/security/SecureRandom.html
 * </a>
 *
 * <p>
 * If a {@link DeterministicValueProvider#CURRENT_TIME_IN_MILLIS_DETERMINISTIC_VALUE_PROVIDER} is used as
 * ${@link DeterministicValueProvider} that least significant {@code 44 bits} can contains milliseconds for {@code 557.46} years.
 *
 * <p>
 * Hex format of generated request id:
 * <pre>
 *      RR RD RR DD RR DD RR DD RR DD RR DD
 * </pre>
 * where {@code R} - random 4 bits and {@code D} - deterministic 4 bits
 *
 * <p>
 * Example of generated request ids:
 * <ul>
 *     <li>qQF3dmlR_IjVNjxY</li>
 *     <li>jeH1dnBRnIjwNiFY</li>
 *     <li>AmG2dvVR34g_NiNY</li>
 *     <li>YYHFdilR5ogUNtlZ</li>
 *     <li>VdFrdoVRoYhVNuNZ</li>
 *     <li>viGVdt5RuYi5NjxZ</li>
 * </ul>
 *
 * @author nedis
 * @see SecureRandom
 * @see Base64#getUrlEncoder()
 * @since 0.7.3
 */
public final class PartlyRandom96BitsRequestIdGenerator implements RequestIdGenerator {

    private static final byte DEFAULT_REQUEST_ID_LENGTH_IN_BYTES = 12;

    private final DeterministicValueProvider deterministicValueProvider;

    private final Base64.Encoder encoder;

    private final SecureRandom secureRandom;

    /**
     * Creates an instance of {@link PartlyRandom96BitsRequestIdGenerator} with the specified deterministic value provider.
     *
     * @param deterministicValueProvider the specified deterministic value provider
     */
    public PartlyRandom96BitsRequestIdGenerator(final DeterministicValueProvider deterministicValueProvider) {
        this.deterministicValueProvider = deterministicValueProvider;
        this.encoder = Base64.getUrlEncoder().withoutPadding();
        this.secureRandom = new SecureRandom();
    }

    @Override
    public String getNextId() {
        final byte[] bytes = new byte[DEFAULT_REQUEST_ID_LENGTH_IN_BYTES];
        secureRandom.nextBytes(bytes);
        final long deterministicValue = deterministicValueProvider.getValue();

        // RR RD RR DD RR DD RR DD RR DD RR DD
        //  0  1  2  3  4  5  6  7  8  9 10 11
        bytes[1] = (byte) ((((deterministicValue & 0x0000FF0000000000L) >>> 40) & 0x0FL) | (bytes[1] & 0xF0L));
        bytes[3] = (byte) ((deterministicValue & 0x000000FF00000000L) >>> 32);
        bytes[5] = (byte) ((deterministicValue & 0x00000000FF000000L) >>> 24);
        bytes[7] = (byte) ((deterministicValue & 0x0000000000FF0000L) >>> 16);
        bytes[9] = (byte) ((deterministicValue & 0x000000000000FF00L) >>> 8);
        bytes[11] = (byte) ((deterministicValue & 0x00000000000000FFL));

        return encoder.encodeToString(bytes);
    }

    @Override
    public String toString() {
        return "PartlyRandom96BitsRequestIdGenerator{" +
                "deterministicValueProvider=" + deterministicValueProvider +
                '}';
    }
}
