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

import java.time.Duration;

import static io.rxmicro.common.util.Requires.require;

/**
 * Allows configuring SQL DB pool options.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
public class SQLPooledDatabaseConfig extends SQLDatabaseConfig {

    private int acquireRetry = 2;

    private int initialSize = 3;

    private int maxSize = 5;

    private String validationQuery = "SELECT 2+2";

    private Duration maxIdleTime = Duration.ofMinutes(30);

    private Duration maxCreateConnectionTime = Duration.ZERO;  // ZERO indicates no-timeout

    private Duration maxAcquireTime = Duration.ZERO;  // ZERO indicates no-timeout

    private Duration maxLifeTime = Duration.ZERO;  // ZERO indicates no-timeout

    /**
     * Returns the number of retries if the first connection acquire attempt fails.
     * <p>
     * Defaults to {@code 2}
     *
     * @return the number of retries if the first connection acquire attempt fails.
     */
    public int getAcquireRetry() {
        return acquireRetry;
    }

    /**
     * Sets the number of retries if the first connection acquire attempt fails.
     * <p>
     * Defaults to {@code 2}
     *
     * @param acquireRetry the number of retries if the first connection acquire attempt fails.
     * @return the reference to this {@link SQLPooledDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLPooledDatabaseConfig setAcquireRetry(final int acquireRetry) {
        this.acquireRetry = acquireRetry;
        return this;
    }

    /**
     * Returns the initial pool size
     * <p>
     * Defaults to {@code 3}
     *
     * @return the initial pool size
     */
    public int getInitialSize() {
        return initialSize;
    }

    /**
     * Sets the initial pool size
     * <p>
     * Defaults to {@code 3}
     *
     * @param initialSize the initial pool size
     * @return the reference to this {@link SQLPooledDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLPooledDatabaseConfig setInitialSize(final int initialSize) {
        this.initialSize = initialSize;
        if (initialSize > this.maxSize) {
            this.maxSize = initialSize;
        }
        return this;
    }

    /**
     * Returns the maximum pool size
     * <p>
     * Defaults to {@code 5}
     *
     * @return the maximum pool size
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Sets the maximum pool size
     * <p>
     * Defaults to {@code 5}
     *
     * @param maxSize the maximum pool size
     * @return the reference to this {@link SQLPooledDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLPooledDatabaseConfig setMaxSize(final int maxSize) {
        this.maxSize = maxSize;
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
     *         connection to the database is still alive.
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
        this.validationQuery = require(validationQuery);
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
        this.maxIdleTime = require(maxIdleTime);
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
        this.maxCreateConnectionTime = require(maxCreateConnectionTime);
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
        this.maxAcquireTime = require(maxAcquireTime);
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
        this.maxLifeTime = require(maxLifeTime);
        return this;
    }

    @Override
    public SQLPooledDatabaseConfig setHost(final String host) {
        return (SQLPooledDatabaseConfig) super.setHost(host);
    }

    @Override
    public SQLPooledDatabaseConfig setPort(final int port) {
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
    public SQLPooledDatabaseConfig addOption(final String name, final String value) {
        return (SQLPooledDatabaseConfig) super.addOption(name, value);
    }

    @Override
    public SQLPooledDatabaseConfig setConnectTimeout(final Duration connectTimeout) {
        return (SQLPooledDatabaseConfig) super.setConnectTimeout(connectTimeout);
    }
}
