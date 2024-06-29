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

import io.rxmicro.common.CommonConstants;
import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.config.Config;
import io.rxmicro.validation.constraint.HostName;
import io.rxmicro.validation.constraint.Nullable;
import io.rxmicro.validation.constraint.Port;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.config.Secrets.hideSecretInfo;

/**
 * Allows configuring SQL DB options.
 *
 * @author nedis
 * @see Duration
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
public class SQLDatabaseConfig extends Config {

    @HostName
    private String host = CommonConstants.LOCALHOST;

    @Nullable
    @Port
    private Integer port;

    private String user;

    private CharSequence password;

    private String database;

    @Nullable
    private Duration connectTimeout;

    @Nullable
    private Map<String, String> options;

    /**
     * Creates an SQL config instance with default settings.
     */
    public SQLDatabaseConfig(final String namespace) {
        super(namespace);
    }

    /**
     * For setting properties from child classes ignoring validation, i.e. ignoring correspond {@link #ensureValid(Object)} invocations.
     */
    protected SQLDatabaseConfig(final String namespace, final Integer port) {
        super(namespace);
        this.port = port;
    }

    /**
     * Returns the server host name.
     *
     * @return the server host name
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the server host name.
     *
     * @param host the server host name
     * @return the reference to this {@link SQLDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLDatabaseConfig setHost(final String host) {
        this.host = ensureValid(host);
        return this;
    }

    /**
     * Returns the server port.
     *
     * @return the server port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Sets the server port.
     *
     * @param port the server port or {@code null} if default port should be applied.
     * @return the reference to this {@link SQLDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLDatabaseConfig setPort(final Integer port) {
        this.port = ensureValid(port);
        return this;
    }

    /**
     * Returns the database username.
     *
     * @return the database username
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the database username.
     *
     * @param user the database username
     * @return the reference to this {@link SQLDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLDatabaseConfig setUser(final String user) {
        this.user = ensureValid(user);
        return this;
    }

    /**
     * Returns the database password.
     *
     * @return the database password
     */
    public CharSequence getPassword() {
        return password;
    }

    /**
     * Sets the database password.
     *
     * @param password the database password
     * @return the reference to this {@link SQLDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLDatabaseConfig setPassword(final CharSequence password) {
        this.password = ensureValid(password);
        return this;
    }

    /**
     * Returns the database name.
     *
     * @return the database name
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Sets the database name.
     *
     * @param database the database name
     * @return the reference to this {@link SQLDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLDatabaseConfig setDatabase(final String database) {
        this.database = ensureValid(database);
        return this;
    }

    /**
     * Sets the additional database options.
     *
     * @param options the additional options
     * @return the reference to this {@link SQLDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLDatabaseConfig setOptions(final Map<String, String> options) {
        this.options = options;
        return this;
    }

    /**
     * Returns the db specific options.
     *
     * @return the db specific options
     */
    public Optional<Map<String, String>> getOptions() {
        return Optional.ofNullable(options).filter(m -> !m.isEmpty());
    }

    /**
     * Returns the connection timeout.
     *
     * @return the connection timeout
     */
    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * Sets the connection timeout.
     *
     * @param connectTimeout the connection timeout
     * @return the reference to this {@link SQLDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLDatabaseConfig setConnectTimeout(final Duration connectTimeout) {
        this.connectTimeout = ensureValid(connectTimeout);
        return this;
    }

    /**
     * Returns the connection string built from schema, host, port and database parameters.
     *
     * @return the connection string built from schema, host, port and database parameters
     */
    public String getConnectionString() {
        return format("sql://?/?", host, Optional.ofNullable(port).map(p -> ":" + p).orElse(""), database);
    }

    @Override
    public String toString() {
        return "SQLDatabaseConfig{" +
                "options=" + options +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", user='" + user + '\'' +
                ", password=" + hideSecretInfo(password.toString()) +
                ", database='" + database + '\'' +
                ", connectTimeout=" + connectTimeout +
                '}';
    }
}
