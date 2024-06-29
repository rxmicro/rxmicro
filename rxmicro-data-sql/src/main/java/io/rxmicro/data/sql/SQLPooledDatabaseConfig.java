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

package io.rxmicro.data.sql;

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.validation.constraint.MaxInt;
import io.rxmicro.validation.constraint.Min;
import io.rxmicro.validation.constraint.MinInt;
import io.rxmicro.validation.constraint.Nullable;

import java.time.Duration;
import java.util.Map;

import static io.rxmicro.config.Secrets.hideSecretInfo;

/**
 * Allows configuring SQL DB pool options.
 *
 * @author nedis
 * @see Duration
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
public class SQLPooledDatabaseConfig extends SQLDatabaseConfig {

    /**
     * Default acquire retry.
     */
    public static final int DEFAULT_ACQUIRE_RETRY = 2;

    /**
     * Default init pool size.
     */
    public static final int DEFAULT_INIT_POOL_SIZE = 3;

    /**
     * Default max pool size.
     */
    public static final int DEFAULT_MAX_POOL_SIZE = 5;

    /**
     * It is just assumption that this value can be interpreted as max possible pool size. If you have argument to update
     * this value, feel free to do it.
     */
    public static final int MAX_POSSIBLE_POOL_SIZE = 250;

    /**
     * Default max idle duration.
     */
    public static final Duration DEFAULT_MAX_IDLE_DURATION = Duration.ofMinutes(30);

    /**
     * Default validation query.
     */
    public static final String DEFAULT_VALIDATION_QUERY = "SELECT 2+2";

    @MinInt(1)
    private int acquireRetry = DEFAULT_ACQUIRE_RETRY;

    @MinInt(1)
    private int initialSize = DEFAULT_INIT_POOL_SIZE;

    @MaxInt(MAX_POSSIBLE_POOL_SIZE)
    private int maxSize = DEFAULT_MAX_POOL_SIZE;

    private String validationQuery = DEFAULT_VALIDATION_QUERY;

    @Min("PT0S")
    private Duration maxIdleTime = DEFAULT_MAX_IDLE_DURATION;

    @Nullable
    @Min("PT0S")
    private Duration maxCreateConnectionTime;

    @Nullable
    @Min("PT0S")
    private Duration maxAcquireTime;

    @Nullable
    @Min("PT0S")
    private Duration maxLifeTime;

    /**
     * Creates a pooled SQL config instance with default settings.
     */
    public SQLPooledDatabaseConfig(final String namespace) {
        super(namespace);
    }

    /**
     * For setting properties from child classes ignoring validation, i.e. ignoring correspond {@link #ensureValid(Object)} invocations.
     */
    protected SQLPooledDatabaseConfig(final String namespace, final Integer port) {
        super(namespace, port);
    }

    /**
     * Returns the number of retries if the first connection acquire attempt fails.
     *
     * <p>
     * Defaults to {@value #DEFAULT_ACQUIRE_RETRY}
     *
     * @return the number of retries if the first connection acquire attempt fails.
     */
    public int getAcquireRetry() {
        return acquireRetry;
    }

    /**
     * Sets the number of retries if the first connection acquire attempt fails.
     *
     * <p>
     * Defaults to {@value #DEFAULT_ACQUIRE_RETRY}
     *
     * @param acquireRetry the number of retries if the first connection acquire attempt fails.
     * @return the reference to this {@link SQLPooledDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLPooledDatabaseConfig setAcquireRetry(final int acquireRetry) {
        this.acquireRetry = ensureValid(acquireRetry);
        return this;
    }

    /**
     * Returns the initial pool size.
     *
     * <p>
     * Defaults to {@value #DEFAULT_INIT_POOL_SIZE}
     *
     * @return the initial pool size
     */
    public int getInitialSize() {
        return initialSize;
    }

    /**
     * Sets the initial pool size.
     *
     * <p>
     * Defaults to {@value #DEFAULT_INIT_POOL_SIZE}
     *
     * @param initialSize the initial pool size
     * @return the reference to this {@link SQLPooledDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLPooledDatabaseConfig setInitialSize(final int initialSize) {
        this.initialSize = ensureValid(initialSize);
        if (initialSize > this.maxSize) {
            this.maxSize = initialSize;
        }
        return this;
    }

    /**
     * Returns the maximum pool size.
     *
     * <p>
     * Defaults to {@value #DEFAULT_MAX_POOL_SIZE}
     *
     * @return the maximum pool size
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Sets the maximum pool size.
     *
     * <p>
     * Defaults to {@value #DEFAULT_MAX_POOL_SIZE}
     *
     * @param maxSize the maximum pool size
     * @return the reference to this {@link SQLPooledDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLPooledDatabaseConfig setMaxSize(final int maxSize) {
        this.maxSize = ensureValid(maxSize);
        if (this.initialSize > maxSize) {
            this.initialSize = maxSize;
        }
        return this;
    }

    /**
     * Returns the query that will be executed just before a connection is given to you from the pool to validate that the
     * connection to the database is still alive.
     *
     * @return the query that will be executed just before a connection is given to you from the pool to validate that the
     * connection to the database is still alive.
     */
    public String getValidationQuery() {
        return validationQuery;
    }

    /**
     * Sets the query that will be executed just before a connection is given to you from the pool to validate that the
     * connection to the database is still alive.
     *
     * @param validationQuery the query that will be executed just before a connection is given to you from the pool to validate that the
     *                        connection to the database is still alive.
     * @return the reference to this {@link SQLPooledDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLPooledDatabaseConfig setValidationQuery(final String validationQuery) {
        this.validationQuery = ensureValid(validationQuery);
        return this;
    }

    /**
     * Returns the maximum idle time of the connection in the pool.
     *
     * @return the maximum idle time of the connection in the pool.
     */
    public Duration getMaxIdleTime() {
        return maxIdleTime;
    }

    /**
     * Sets the maximum idle time of the connection in the pool.
     *
     * @param maxIdleTime the maximum idle time of the connection in the pool.
     * @return the reference to this {@link SQLPooledDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLPooledDatabaseConfig setMaxIdleTime(final Duration maxIdleTime) {
        this.maxIdleTime = ensureValid(maxIdleTime);
        return this;
    }

    /**
     * Returns the maximum time to create a new connection.
     *
     * @return the maximum time to create a new connection.
     */
    public Duration getMaxCreateConnectionTime() {
        return maxCreateConnectionTime;
    }

    /**
     * Sets the maximum time to create a new connection.
     *
     * @param maxCreateConnectionTime the maximum time to create a new connection.
     * @return the reference to this {@link SQLPooledDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLPooledDatabaseConfig setMaxCreateConnectionTime(final Duration maxCreateConnectionTime) {
        this.maxCreateConnectionTime = ensureValid(maxCreateConnectionTime);
        return this;
    }

    /**
     * Returns the maximum time to acquire connection from pool.
     *
     * @return the maximum time to acquire connection from pool.
     */
    public Duration getMaxAcquireTime() {
        return maxAcquireTime;
    }

    /**
     * Sets the maximum time to acquire connection from pool.
     *
     * @param maxAcquireTime the maximum time to acquire connection from pool.
     * @return the reference to this {@link SQLPooledDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLPooledDatabaseConfig setMaxAcquireTime(final Duration maxAcquireTime) {
        this.maxAcquireTime = ensureValid(maxAcquireTime);
        return this;
    }

    /**
     * Returns the maximum lifetime of the connection in the pool.
     *
     * @return the maximum lifetime of the connection in the pool.
     */
    public Duration getMaxLifeTime() {
        return maxLifeTime;
    }

    /**
     * Sets the maximum lifetime of the connection in the pool.
     *
     * @param maxLifeTime the maximum lifetime of the connection in the pool.
     * @return the reference to this {@link SQLPooledDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLPooledDatabaseConfig setMaxLifeTime(final Duration maxLifeTime) {
        this.maxLifeTime = ensureValid(maxLifeTime);
        return this;
    }

    @Override
    public SQLPooledDatabaseConfig setHost(final String host) {
        return (SQLPooledDatabaseConfig) super.setHost(host);
    }

    @Override
    public SQLPooledDatabaseConfig setPort(final Integer port) {
        return (SQLPooledDatabaseConfig) super.setPort(port);
    }

    @Override
    public SQLPooledDatabaseConfig setUser(final String user) {
        return (SQLPooledDatabaseConfig) super.setUser(user);
    }

    @Override
    public SQLPooledDatabaseConfig setPassword(final CharSequence password) {
        return (SQLPooledDatabaseConfig) super.setPassword(password);
    }

    @Override
    public SQLPooledDatabaseConfig setDatabase(final String database) {
        return (SQLPooledDatabaseConfig) super.setDatabase(database);
    }

    @Override
    public SQLPooledDatabaseConfig setOptions(final Map<String, String> options) {
        return (SQLPooledDatabaseConfig) super.setOptions(options);
    }

    @Override
    public SQLPooledDatabaseConfig setConnectTimeout(final Duration connectTimeout) {
        return (SQLPooledDatabaseConfig) super.setConnectTimeout(connectTimeout);
    }

    @Override
    public String toString() {
        return "SQLPooledDatabaseConfig{" +
                "options=" + getOptions() +
                ", host='" + getHost() + '\'' +
                ", port=" + getPort() +
                ", user='" + getUser() + '\'' +
                ", password=" + hideSecretInfo(getPassword().toString()) +
                ", database='" + getDatabase() + '\'' +
                ", connectTimeout=" + getConnectTimeout() +
                ", acquireRetry=" + acquireRetry +
                ", initialSize=" + initialSize +
                ", maxSize=" + maxSize +
                ", validationQuery='" + validationQuery + '\'' +
                ", maxIdleTime=" + maxIdleTime +
                ", maxCreateConnectionTime=" + maxCreateConnectionTime +
                ", maxAcquireTime=" + maxAcquireTime +
                ", maxLifeTime=" + maxLifeTime +
                '}';
    }
}
