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

package io.rxmicro.rest.server;

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.rest.server.feature.RequestIdGenerator;
import io.rxmicro.rest.server.feature.request.id.generator.Deterministic96BitsRequestIdGenerator;
import io.rxmicro.rest.server.feature.request.id.generator.DeterministicValueProvider;
import io.rxmicro.rest.server.feature.request.id.generator.PartlyRandom96BitsRequestIdGenerator;
import io.rxmicro.rest.server.feature.request.id.generator.RandomRequestIdGenerator;
import io.rxmicro.rest.server.internal.RequestIdGeneratorProviderHelper;

import java.util.UUID;
import java.util.function.Supplier;

import static io.rxmicro.rest.server.feature.request.id.generator.Deterministic96BitsRequestIdGenerator.DEFAULT_CHECKSUM_ALGORITHM;
import static io.rxmicro.rest.server.feature.request.id.generator.Deterministic96BitsRequestIdGenerator.DEFAULT_COUNTER_INCREMENT_VALUE;
import static io.rxmicro.rest.server.feature.request.id.generator.Deterministic96BitsRequestIdGenerator.DEFAULT_RANDOMIZE_MASK;
import static io.rxmicro.rest.server.feature.request.id.generator.DeterministicValueProvider.CURRENT_TIME_IN_MILLIS_AS_DETERMINISTIC_VALUE_PROVIDER;
import static io.rxmicro.rest.server.feature.request.id.generator.RandomRequestIdGenerator.DEFAULT_RANDOM_REQUEST_ID_LENGTH_IN_BYTES;
import static io.rxmicro.rest.server.feature.request.id.generator.RandomRequestIdGenerator.DEFAULT_RANDOM_UUID_REQUEST_ID_LENGTH_IN_BYTES;

/**
 * Declares the predefined {@link RequestIdGenerator} generators.
 *
 * @author nedis
 * @see RestServerConfig
 * @since 0.7
 */
public enum PredefinedRequestIdGeneratorProvider implements RequestIdGeneratorProvider {

    /**
     * This request id generator returns random request id with
     * {@value RandomRequestIdGenerator#DEFAULT_RANDOM_UUID_REQUEST_ID_LENGTH_IN_BYTES} bytes count.
     *
     * <p>
     * According to specification this generator is similar to the {@link UUID#randomUUID()} generation.
     *
     * @see RandomRequestIdGenerator
     * @see RandomRequestIdGenerator#DEFAULT_RANDOM_UUID_REQUEST_ID_LENGTH_IN_BYTES
     */
    UUID_128_BITS(() ->
            new RandomRequestIdGenerator(DEFAULT_RANDOM_UUID_REQUEST_ID_LENGTH_IN_BYTES)
    ),

    /**
     * This request id generator returns random request id with
     * {@value RandomRequestIdGenerator#DEFAULT_RANDOM_REQUEST_ID_LENGTH_IN_BYTES} bytes count.
     *
     * @see RandomRequestIdGenerator
     * @see RandomRequestIdGenerator#DEFAULT_RANDOM_REQUEST_ID_LENGTH_IN_BYTES
     */
    RANDOM_96_BITS(() ->
            new RandomRequestIdGenerator(DEFAULT_RANDOM_REQUEST_ID_LENGTH_IN_BYTES)
    ),

    /**
     * This request id generator returns 96 bit request id with 52 random and 44 deterministic bits.
     *
     * @see PartlyRandom96BitsRequestIdGenerator
     * @see DeterministicValueProvider#CURRENT_TIME_IN_MILLIS_AS_DETERMINISTIC_VALUE_PROVIDER
     */
    PARTLY_RANDOM_96_BITS(() ->
            new PartlyRandom96BitsRequestIdGenerator(
                    CURRENT_TIME_IN_MILLIS_AS_DETERMINISTIC_VALUE_PROVIDER
            )
    ),

    /**
     * This request id generator returns 96 deterministic bit request id.
     *
     * @see Deterministic96BitsRequestIdGenerator
     * @see DeterministicValueProvider#CURRENT_TIME_IN_MILLIS_AS_DETERMINISTIC_VALUE_PROVIDER
     * @see Deterministic96BitsRequestIdGenerator#DEFAULT_CHECKSUM_ALGORITHM
     * @see Deterministic96BitsRequestIdGenerator#DEFAULT_COUNTER_INCREMENT_VALUE
     * @see Deterministic96BitsRequestIdGenerator#DEFAULT_RANDOMIZE_MASK
     */
    DETERMINISTIC_96_BITS(() ->
            new Deterministic96BitsRequestIdGenerator(
                    CURRENT_TIME_IN_MILLIS_AS_DETERMINISTIC_VALUE_PROVIDER,
                    DEFAULT_CHECKSUM_ALGORITHM,
                    DEFAULT_COUNTER_INCREMENT_VALUE,
                    DEFAULT_RANDOMIZE_MASK
            )
    ),

    /**
     * The default request id generator provider.
     *
     * <p>
     * By default this request id generator provider tries to use {@link #PARTLY_RANDOM_96_BITS} request id generator.
     * But if during {@link RestServerConfig#getRequestIdGeneratorInitTimeout()} the {@link java.security.SecureRandom}
     * instance can't generate random bytes the {@link #DETERMINISTIC_96_BITS} request id generator will be used.
     *
     * <p>
     * Read about why {@link java.security.SecureRandom} instance can't generate random bytes here:
     * <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/security/SecureRandom.html">
     *     https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/security/SecureRandom.html
     * </a>
     *
     * @see #PARTLY_RANDOM_96_BITS
     * @see #DETERMINISTIC_96_BITS
     */
    DEFAULT_96_BIT(
            PARTLY_RANDOM_96_BITS.requestIdGeneratorSupplier,
            DETERMINISTIC_96_BITS.requestIdGeneratorSupplier
    );

    private static final Logger LOGGER = LoggerFactory.getLogger(PredefinedRequestIdGeneratorProvider.class);

    private final Supplier<RequestIdGenerator> requestIdGeneratorSupplier;

    private final Supplier<RequestIdGenerator> fallbackRequestIdGeneratorSupplier;

    PredefinedRequestIdGeneratorProvider(final Supplier<RequestIdGenerator> requestIdGeneratorSupplier) {
        this(requestIdGeneratorSupplier, null);
    }

    PredefinedRequestIdGeneratorProvider(final Supplier<RequestIdGenerator> requestIdGeneratorSupplier,
                                         final Supplier<RequestIdGenerator> fallbackRequestIdGeneratorSupplier) {
        this.requestIdGeneratorSupplier = requestIdGeneratorSupplier;
        this.fallbackRequestIdGeneratorSupplier = fallbackRequestIdGeneratorSupplier;
    }

    @Override
    public RequestIdGenerator getRequestIdGenerator(final RestServerConfig restServerConfig) {
        return RequestIdGeneratorProviderHelper.getRequestIdGenerator(
                LOGGER, requestIdGeneratorSupplier, fallbackRequestIdGeneratorSupplier, restServerConfig
        );
    }
}
