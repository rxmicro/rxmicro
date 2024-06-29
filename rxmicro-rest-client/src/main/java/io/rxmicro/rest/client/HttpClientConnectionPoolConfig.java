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

package io.rxmicro.rest.client;

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.http.HttpConfig;
import io.rxmicro.http.ProtocolSchema;
import io.rxmicro.validation.constraint.Min;
import io.rxmicro.validation.constraint.MinInt;
import io.rxmicro.validation.constraint.Nullable;

import java.time.Duration;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.TimeoutException;

/**
 * Configures connection pool settings for http client.
 *
 * @author nedis
 * @since 0.8
 */
public class HttpClientConnectionPoolConfig extends HttpConfig {

    /**
     * Default max connections. Fallback to available number of processors (but with a minimum value of 16)
     */
    public static final int DEFAULT_POOL_MAX_CONNECTIONS = Math.max(Runtime.getRuntime().availableProcessors(), 8) * 2;

    /**
     * Default acquisition timeout (milliseconds) before error. If -1 will never wait to acquire before opening a new
     * connection in an unbounded fashion
     */
    public static final Duration DEFAULT_POOL_ACQUIRE_TIMEOUT = Duration.ofSeconds(45);

    /**
     * Providing an {@code evictionInterval} of {@link Duration#ZERO zero} means the background eviction is disabled.
     */
    public static final Duration EVICT_IN_BACKGROUND_DISABLED = Duration.ZERO;

    /**
     * Default leasing strategy.
     */
    public static final LeasingStrategy DEFAULT_POOL_LEASING_STRATEGY = LeasingStrategy.FIFO;

    @Min("PT0S")
    private Duration evictionInterval = EVICT_IN_BACKGROUND_DISABLED;

    @MinInt(1)
    private int maxConnections = DEFAULT_POOL_MAX_CONNECTIONS;

    @Nullable
    @MinInt(1)
    private Integer pendingAcquireMaxCount;

    private Duration pendingAcquireTimeout = DEFAULT_POOL_ACQUIRE_TIMEOUT;

    @Nullable
    @Min("PT0S")
    private Duration maxIdleTime;

    @Nullable
    @Min("PT0S")
    private Duration maxLifeTime;

    private LeasingStrategy leasingStrategy = DEFAULT_POOL_LEASING_STRATEGY;

    /**
     * This is basic class designed for extension only.
     */
    protected HttpClientConnectionPoolConfig(final String namespace) {
        super(namespace);
    }

    /**
     * For setting properties from child classes ignoring validation, i.e. ignoring correspond {@link #ensureValid(Object)} invocations.
     */
    protected HttpClientConnectionPoolConfig(final String namespace,
                                             final ProtocolSchema schema,
                                             final String host,
                                             final Integer port) {
        super(namespace, schema, host, port);
    }

    /**
     * Returns the options to use for configuring connection provider background eviction.
     *
     * @return the options to use for configuring connection provider background eviction.
     */
    public Duration getEvictionInterval() {
        return evictionInterval;
    }

    /**
     * Set the options to use for configuring connection provider background eviction.
     * When a background eviction is enabled, the connection pool is regularly checked for connections,
     * that are applicable for removal.
     * Default to {@link #EVICT_IN_BACKGROUND_DISABLED} - the background eviction is disabled.
     * Providing an {@code evictionInterval} of {@link Duration#ZERO zero} means the background eviction is disabled.
     *
     * @param evictionInterval specifies the interval to be used for checking the connection pool, (resolution: ns)
     * @return the reference to this {@link HttpClientConnectionPoolConfig} instance
     * @throws NullPointerException if evictionInterval is null
     */
    @BuilderMethod
    public HttpClientConnectionPoolConfig setEvictionInterval(final Duration evictionInterval) {
        this.evictionInterval = ensureValid(evictionInterval);
        return this;
    }

    /**
     * Returns the options to use for configuring connection provider maximum connections per connection pool.
     *
     * @return the options to use for configuring connection provider maximum connections per connection pool.
     */
    public int getMaxConnections() {
        return maxConnections;
    }

    /**
     * Set the options to use for configuring connection provider maximum connections per connection pool.
     * Default to {@link #DEFAULT_POOL_MAX_CONNECTIONS}.
     *
     * @param maxConnections the maximum number of connections (per connection pool) before start pending
     * @return the reference to this {@link HttpClientConnectionPoolConfig} instance
     * @throws IllegalArgumentException if maxConnections is negative
     */
    @BuilderMethod
    public HttpClientConnectionPoolConfig setMaxConnections(final int maxConnections) {
        this.maxConnections = ensureValid(maxConnections);
        return this;
    }

    /**
     * Returns the options to use for configuring connection provider the maximum number of registered requests for acquire to keep in
     * a pending queue.
     *
     * @return the options to use for configuring connection provider the maximum number of registered requests for acquire to keep in
     * a pending queue.
     */
    public OptionalInt getPendingAcquireMaxCount() {
        if (pendingAcquireMaxCount == null) {
            return OptionalInt.empty();
        } else {
            return OptionalInt.of(pendingAcquireMaxCount);
        }
    }

    /**
     * Set the options to use for configuring connection provider the maximum number of registered requests for acquire to keep in
     * a pending queue.
     * When invoked with {@code null} the pending queue will not have upper limit.
     * Default to {@code 2 * max connections}.
     *
     * @param pendingAcquireMaxCount the maximum number of registered requests for acquire to keep in a pending queue
     * @return the reference to this {@link HttpClientConnectionPoolConfig} instance
     * @throws IllegalArgumentException if pendingAcquireMaxCount is negative
     */
    @BuilderMethod
    public HttpClientConnectionPoolConfig setPendingAcquireMaxCount(final Integer pendingAcquireMaxCount) {
        this.pendingAcquireMaxCount = ensureValid(pendingAcquireMaxCount);
        return this;
    }

    /**
     * Returns the options to use for configuring connection provider acquire timeout (resolution: ms).
     *
     * @return the options to use for configuring connection provider acquire timeout (resolution: ms).
     */
    public Duration getPendingAcquireTimeout() {
        return pendingAcquireTimeout;
    }

    /**
     * Set the options to use for configuring connection provider acquire timeout (resolution: ms).
     * Default to {@link #DEFAULT_POOL_ACQUIRE_TIMEOUT}.
     *
     * @param pendingAcquireTimeout the maximum time after which a pending acquire
     *                              must complete or the {@link TimeoutException} will be thrown (resolution: ms)
     * @return the reference to this {@link HttpClientConnectionPoolConfig} instance
     * @throws NullPointerException if pendingAcquireTimeout is null
     */
    @BuilderMethod
    public HttpClientConnectionPoolConfig setPendingAcquireTimeout(final Duration pendingAcquireTimeout) {
        this.pendingAcquireTimeout = ensureValid(pendingAcquireTimeout);
        return this;
    }

    /**
     * Returns the options to use for configuring connection provider max idle time (resolution: ms).
     *
     * @return the options to use for configuring connection provider max idle time (resolution: ms).
     */
    public Optional<Duration> getMaxIdleTime() {
        return Optional.ofNullable(maxIdleTime);
    }

    /**
     * Set the options to use for configuring connection provider max idle time (resolution: ms).
     *
     * @param maxIdleTime the {@link Duration} after which the channel will be closed when idle (resolution: ms)
     * @return the reference to this {@link HttpClientConnectionPoolConfig} instance
     * @throws NullPointerException if maxIdleTime is null
     */
    @BuilderMethod
    public HttpClientConnectionPoolConfig setMaxIdleTime(final Duration maxIdleTime) {
        this.maxIdleTime = ensureValid(maxIdleTime);
        return this;
    }

    /**
     * Returns the options to use for configuring connection provider max life time (resolution: ms).
     *
     * @return the options to use for configuring connection provider max life time (resolution: ms).
     */
    public Optional<Duration> getMaxLifeTime() {
        return Optional.ofNullable(maxLifeTime);
    }

    /**
     * Set the options to use for configuring connection provider max life time (resolution: ms).
     *
     * @param maxLifeTime the {@link Duration} after which the channel will be closed (resolution: ms)
     * @return the reference to this {@link HttpClientConnectionPoolConfig} instance
     * @throws NullPointerException if maxLifeTime is null
     */
    @BuilderMethod
    public HttpClientConnectionPoolConfig setMaxLifeTime(final Duration maxLifeTime) {
        this.maxLifeTime = ensureValid(maxLifeTime);
        return this;
    }

    /**
     * Returns the configured leasing strategy.
     *
     * @return the configured leasing strategy.
     */
    public LeasingStrategy getLeasingStrategy() {
        return leasingStrategy;
    }

    /**
     * Sets the custom leasing strategy.
     *
     * @param leasingStrategy the custom leasing strategy.
     * @return the reference to this {@link HttpClientConnectionPoolConfig} instance
     * @throws NullPointerException if leasingStrategy is null
     */
    @BuilderMethod
    public HttpClientConnectionPoolConfig setLeasingStrategy(final LeasingStrategy leasingStrategy) {
        this.leasingStrategy = ensureValid(leasingStrategy);
        return this;
    }

    @Override
    public String toString() {
        return "HttpClientConnectionPoolConfig{connectionString=" + getConnectionString() +
                ", evictionInterval=" + evictionInterval +
                ", maxConnections=" + maxConnections +
                ", pendingAcquireMaxCount=" + pendingAcquireMaxCount +
                ", pendingAcquireTimeout=" + pendingAcquireTimeout +
                ", maxIdleTime=" + maxIdleTime +
                ", maxLifeTime=" + maxLifeTime +
                ", leasingStrategy=" + leasingStrategy +
                '}';
    }
}
