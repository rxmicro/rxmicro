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

import java.time.Duration;

import static io.rxmicro.common.util.Requires.require;

/**
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

    public int getAcquireRetry() {
        return acquireRetry;
    }

    public SQLPooledDatabaseConfig setAcquireRetry(final int acquireRetry) {
        this.acquireRetry = acquireRetry;
        return this;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public SQLPooledDatabaseConfig setInitialSize(final int initialSize) {
        this.initialSize = initialSize;
        if (initialSize > this.maxSize) {
            this.maxSize = initialSize;
        }
        return this;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public SQLPooledDatabaseConfig setMaxSize(final int maxSize) {
        this.maxSize = maxSize;
        if (this.initialSize > maxSize) {
            this.initialSize = maxSize;
        }
        return this;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public SQLPooledDatabaseConfig setValidationQuery(final String validationQuery) {
        this.validationQuery = require(validationQuery);
        return this;
    }

    public Duration getMaxIdleTime() {
        return maxIdleTime;
    }

    public SQLPooledDatabaseConfig setMaxIdleTime(final Duration maxIdleTime) {
        this.maxIdleTime = require(maxIdleTime);
        return this;
    }

    public Duration getMaxCreateConnectionTime() {
        return maxCreateConnectionTime;
    }

    public SQLPooledDatabaseConfig setMaxCreateConnectionTime(final Duration maxCreateConnectionTime) {
        this.maxCreateConnectionTime = require(maxCreateConnectionTime);
        return this;
    }

    public Duration getMaxAcquireTime() {
        return maxAcquireTime;
    }

    public SQLPooledDatabaseConfig setMaxAcquireTime(final Duration maxAcquireTime) {
        this.maxAcquireTime = require(maxAcquireTime);
        return this;
    }

    public Duration getMaxLifeTime() {
        return maxLifeTime;
    }

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
