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

import io.rxmicro.config.ConfigException;
import io.rxmicro.rest.server.feature.RequestIdGenerator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * This request id generator returns 96 deterministic bit request id.
 *
 * <p>
 * This request id generator is useful if random generator can't be used.
 * Read more: <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/security/SecureRandom.html">
 *     https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/security/SecureRandom.html
 * </a>
 *
 * <p>
 * If a {@link DeterministicValueProvider#CURRENT_TIME_IN_MILLIS_AS_DETERMINISTIC_VALUE_PROVIDER} is used as
 * ${@link DeterministicValueProvider} that least significant {@code 44 bits} can contains milliseconds for {@code 557.46} years.
 *
 * <p>
 * Format rule:
 * <pre>
 *     96 result bit = 44 deterministic bits + 24 incremental counter bits + 28 checksum bits.
 * </pre>
 *
 * <p>
 * Hex format of generated request id:
 * <pre>
 *      SD DD SS DD SS DD CC SS CC DD DD CC
 * </pre>
 * where {@code D} - deterministic 4 bits, {@code C} - incremental counter 4 bits, {@code S} - checksum 4 bits
 *
 * <p>
 * Example of generated 12 bytes request ids:
 * <ul>
 *     <li>kXb3UMTRVKlVXILq</li>
 *     <li>sXYdUJLRVKVVXIKd</li>
 *     <li>8XbLUC_SVGZVXIKE</li>
 *     <li>EXZ3UHrSVG5VXIKP</li>
 *     <li>8XYcUEHSVOZVXIK2</li>
 *     <li>8XbHUCDSVMBVXIK6</li>
 * </ul>
 *
 * @author nedis
 * @see Base64#getUrlEncoder()
 * @see MessageDigest
 * @since 0.7.3
 */
public final class Deterministic96BitsRequestIdGenerator implements RequestIdGenerator {

    /**
     * By default this request id generator uses {@value Deterministic96BitsRequestIdGenerator#DEFAULT_CHECKSUM_ALGORITHM} algorithm.
     */
    public static final String DEFAULT_CHECKSUM_ALGORITHM = "SHA-256";

    /**
     * Default randomize mask is {@value #DEFAULT_RANDOMIZE_MASK}.
     *
     * <p>
     * The randomize mask is used to randomize incremental counter: {@code return (counter++ ^ randomizeMask)}
     */
    public static final int DEFAULT_RANDOMIZE_MASK = 0b0101_0101__0101_0101__0101_0101;

    /**
     * Default increment value is {@value #DEFAULT_COUNTER_INCREMENT_VALUE}.
     */
    public static final int DEFAULT_COUNTER_INCREMENT_VALUE = 3;

    private static final byte DEFAULT_REQUEST_ID_LENGTH_IN_BYTES = 12;

    private static final int MAX_COUNTER_VALUE = 0xFF_FF_FF;

    private static final int DETERMINISTIC_VALUE_SHIFT = 0;

    private static final int COUNTER_VALUE_SHIFT = 6;

    private final DeterministicValueProvider deterministicValueProvider;

    private final String checksumAlgorithm;

    private final int incrementValue;

    private final int randomizeMask;

    private final Base64.Encoder encoder;

    private volatile int counterGenerator;

    /**
     * Creates an instance of {@link Deterministic96BitsRequestIdGenerator} with the specified parameters.
     *
     * <p>
     * The randomize mask is used to randomize incremental counter: {@code return (counter++ ^ randomizeMask)}
     *  @param deterministicValueProvider the deterministic value provider.
     * @param checksumAlgorithm the checksum algorithm
     * @param incrementValue the increment value for counter
     * @param randomizeMask the randomize mask
     */
    public Deterministic96BitsRequestIdGenerator(final DeterministicValueProvider deterministicValueProvider,
                                                 final String checksumAlgorithm,
                                                 final int incrementValue,
                                                 final int randomizeMask) {
        this.deterministicValueProvider = deterministicValueProvider;
        this.incrementValue = incrementValue;
        this.randomizeMask = randomizeMask;
        this.encoder = Base64.getUrlEncoder().withoutPadding();
        this.counterGenerator = 0;
        getMessageDigest(checksumAlgorithm);
        this.checksumAlgorithm = checksumAlgorithm;
    }

    @Override
    public String getNextId() {
        final long deterministicValue = deterministicValueProvider.getValue();
        final byte[] sourceBytes = new byte[9];
        convertDeterministicValueToByteArray(deterministicValue, sourceBytes);
        convertCounterValueToByteArray(sourceBytes);
        final byte[] checksumBytes = getMessageDigest(checksumAlgorithm).digest(sourceBytes);
        final int checksumStartIndex = (int) (deterministicValue % ((long) checksumBytes.length - 4L));
        return shuffleBitsAndEncode(sourceBytes, checksumBytes, checksumStartIndex);
    }

    private void convertDeterministicValueToByteArray(final long deterministicValue,
                                                      final byte[] sourceBytes) {
        sourceBytes[DETERMINISTIC_VALUE_SHIFT] = (byte) (((deterministicValue & 0x0000FF0000000000L) >>> 40) & 0x0FL);
        sourceBytes[DETERMINISTIC_VALUE_SHIFT + 1] = (byte) ((deterministicValue & 0x000000FF00000000L) >>> 32);
        sourceBytes[DETERMINISTIC_VALUE_SHIFT + 2] = (byte) ((deterministicValue & 0x00000000FF000000L) >>> 24);
        sourceBytes[DETERMINISTIC_VALUE_SHIFT + 3] = (byte) ((deterministicValue & 0x0000000000FF0000L) >>> 16);
        sourceBytes[DETERMINISTIC_VALUE_SHIFT + 4] = (byte) ((deterministicValue & 0x000000000000FF00L) >>> 8);
        sourceBytes[DETERMINISTIC_VALUE_SHIFT + 5] = (byte) ((deterministicValue & 0x00000000000000FFL));
    }

    private void convertCounterValueToByteArray(final byte[] sourceBytes) {
        final long counter = getCounterValue();
        sourceBytes[COUNTER_VALUE_SHIFT] = (byte) ((counter & 0x0000000000FF0000L) >>> 16);
        sourceBytes[COUNTER_VALUE_SHIFT + 1] = (byte) ((counter & 0x000000000000FF00L) >>> 8);
        sourceBytes[COUNTER_VALUE_SHIFT + 2] = (byte) ((counter & 0x00000000000000FFL));
    }

    private long getCounterValue() {
        final int generatedCount;
        synchronized (this) {
            generatedCount = counterGenerator;
            counterGenerator += incrementValue;
            if (generatedCount >= MAX_COUNTER_VALUE) {
                counterGenerator = generatedCount - MAX_COUNTER_VALUE;
            }
        }
        return generatedCount ^ randomizeMask;
    }

    private MessageDigest getMessageDigest(final String checksumAlgorithm) {
        try {
            return MessageDigest.getInstance(checksumAlgorithm);
        } catch (final NoSuchAlgorithmException ex) {
            throw new ConfigException("Checksum algorithm not found: '?'!", checksumAlgorithm);
        }
    }

    private String shuffleBitsAndEncode(final byte[] sourceBytes,
                                        final byte[] checksumBytes,
                                        final int checksumStartIndex) {
        // SD DD SS DD SS DD CC SS CC DD DD CC
        //  0  1  2  3  4  5  6  7  8  9 10 11
        final byte[] bytes = new byte[DEFAULT_REQUEST_ID_LENGTH_IN_BYTES];
        bytes[0] = (byte) (sourceBytes[DETERMINISTIC_VALUE_SHIFT] | checksumBytes[checksumStartIndex] & 0xF0);
        bytes[1] = sourceBytes[DETERMINISTIC_VALUE_SHIFT + 1];
        bytes[2] = checksumBytes[checksumStartIndex + 1];
        bytes[3] = sourceBytes[DETERMINISTIC_VALUE_SHIFT + 3];
        bytes[4] = checksumBytes[checksumStartIndex + 2];
        bytes[5] = sourceBytes[DETERMINISTIC_VALUE_SHIFT + 5];
        bytes[6] = sourceBytes[COUNTER_VALUE_SHIFT + 1];
        bytes[7] = checksumBytes[checksumStartIndex + 3];
        bytes[8] = sourceBytes[COUNTER_VALUE_SHIFT];
        bytes[9] = sourceBytes[DETERMINISTIC_VALUE_SHIFT + 2];
        bytes[10] = sourceBytes[DETERMINISTIC_VALUE_SHIFT + 4];
        bytes[11] = sourceBytes[COUNTER_VALUE_SHIFT + 2];
        return encoder.encodeToString(bytes);
    }

    @Override
    public String toString() {
        return "Deterministic96BitsRequestIdGenerator{" +
                "deterministicValueProvider=" + deterministicValueProvider +
                ", checksumAlgorithm='" + checksumAlgorithm + '\'' +
                ", incrementValue=" + incrementValue +
                ", randomizeMask=" + Integer.toBinaryString(randomizeMask) +
                '}';
    }
}
