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

package io.rxmicro.data.sql.r2dbc.postgresql;

import io.rxmicro.data.sql.SQLPooledDatabaseConfig;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.config.Secrets.hideSecretInfo;

/**
 * Allows configuring the environment specific configs for PostgreSQL database.
 *
 * <ul>
 *     <li>{@link PostgreSQLConfig} must be used for environment specific configs.</li>
 *     <li>{@link PostgreSQLConfigCustomizer} must be used for application specific configs.</li>
 * </ul>
 *
 * @author nedis
 * @see PostgreSQLRepository
 * @see PostgreSQLClientFactory
 * @see PostgreSQLConfigCustomizer
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
public final class PostgreSQLConfig extends SQLPooledDatabaseConfig {

    /**
     * Default Postgre SQL port.
     */
    public static final int DEFAULT_POSTGRESQL_PORT = 5432;

    /**
     * Creates a Postgre SQL config instance with default settings.
     */
    public PostgreSQLConfig(final String namespace) {
        super(namespace, DEFAULT_POSTGRESQL_PORT);
    }

    @Override
    public String getConnectionString() {
        return format("r2dbc:postgresql://?/?", getHost(), Optional.ofNullable(getPort()).map(p -> ":" + p).orElse(""), getDatabase());
    }

    @Override
    public PostgreSQLConfig setAcquireRetry(final int acquireRetry) {
        return (PostgreSQLConfig) super.setAcquireRetry(acquireRetry);
    }

    @Override
    public PostgreSQLConfig setInitialSize(final int initialSize) {
        return (PostgreSQLConfig) super.setInitialSize(initialSize);
    }

    @Override
    public PostgreSQLConfig setMaxSize(final int maxSize) {
        return (PostgreSQLConfig) super.setMaxSize(maxSize);
    }

    @Override
    public PostgreSQLConfig setValidationQuery(final String validationQuery) {
        return (PostgreSQLConfig) super.setValidationQuery(validationQuery);
    }

    @Override
    public PostgreSQLConfig setMaxIdleTime(final Duration maxIdleTime) {
        return (PostgreSQLConfig) super.setMaxIdleTime(maxIdleTime);
    }

    @Override
    public PostgreSQLConfig setMaxCreateConnectionTime(final Duration maxCreateConnectionTime) {
        return (PostgreSQLConfig) super.setMaxCreateConnectionTime(maxCreateConnectionTime);
    }

    @Override
    public PostgreSQLConfig setMaxAcquireTime(final Duration maxAcquireTime) {
        return (PostgreSQLConfig) super.setMaxAcquireTime(maxAcquireTime);
    }

    @Override
    public PostgreSQLConfig setMaxLifeTime(final Duration maxLifeTime) {
        return (PostgreSQLConfig) super.setMaxLifeTime(maxLifeTime);
    }

    @Override
    public PostgreSQLConfig setHost(final String host) {
        return (PostgreSQLConfig) super.setHost(host);
    }

    @Override
    public PostgreSQLConfig setPort(final Integer port) {
        return (PostgreSQLConfig) super.setPort(port);
    }

    @Override
    public PostgreSQLConfig setUser(final String user) {
        return (PostgreSQLConfig) super.setUser(user);
    }

    @Override
    public PostgreSQLConfig setPassword(final CharSequence password) {
        return (PostgreSQLConfig) super.setPassword(password);
    }

    @Override
    public PostgreSQLConfig setDatabase(final String database) {
        return (PostgreSQLConfig) super.setDatabase(database);
    }

    @Override
    public PostgreSQLConfig setOptions(final Map<String, String> options) {
        return (PostgreSQLConfig) super.setOptions(options);
    }

    @Override
    public PostgreSQLConfig setConnectTimeout(final Duration connectTimeout) {
        return (PostgreSQLConfig) super.setConnectTimeout(connectTimeout);
    }

    @Override
    public String toString() {
        return "PostgreSQLConfig{" +
                "options=" + getOptions() +
                ", host='" + getHost() + '\'' +
                ", port=" + getPort() +
                ", user='" + getUser() + '\'' +
                ", password=" + hideSecretInfo(getPassword().toString()) +
                ", database='" + getDatabase() + '\'' +
                ", connectTimeout=" + getConnectTimeout() +
                ", acquireRetry=" + getAcquireRetry() +
                ", initialSize=" + getInitialSize() +
                ", maxSize=" + getMaxSize() +
                ", validationQuery='" + getValidationQuery() + '\'' +
                ", maxIdleTime=" + getMaxIdleTime() +
                ", maxCreateConnectionTime=" + getMaxCreateConnectionTime() +
                ", maxAcquireTime=" + getMaxAcquireTime() +
                ", maxLifeTime=" + getMaxLifeTime() +
                '}';
    }
}
