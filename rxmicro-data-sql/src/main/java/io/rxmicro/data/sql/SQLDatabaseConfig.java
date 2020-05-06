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
import io.rxmicro.config.Config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.config.Networks.validatePort;

/**
 * Allows configuring SQL DB options.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
public class SQLDatabaseConfig extends Config {

    private final Map<String, String> options = new HashMap<>();

    private String host = "localhost";

    private int port;

    private String user;

    private CharSequence password;

    private String database = "db";

    private Duration connectTimeout;

    /**
     * Returns the server host name
     *
     * @return the server host name
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the server host name
     *
     * @param host the server host name
     * @return the reference to this {@link SQLDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLDatabaseConfig setHost(final String host) {
        this.host = require(host);
        return this;
    }

    /**
     * Returns the server port
     *
     * @return the server port
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the server port
     *
     * @param port the server port
     * @return the reference to this {@link SQLDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLDatabaseConfig setPort(final int port) {
        this.port = validatePort(port);
        return this;
    }

    /**
     * Returns the database username
     *
     * @return the database username
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the database username
     *
     * @param user the database username
     * @return the reference to this {@link SQLDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLDatabaseConfig setUser(final String user) {
        this.user = require(user);
        return this;
    }

    /**
     * Returns the database password
     *
     * @return the database password
     */
    public CharSequence getPassword() {
        return password;
    }

    /**
     * Sets the database password
     *
     * @param password the database password
     * @return the reference to this {@link SQLDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLDatabaseConfig setPassword(final CharSequence password) {
        this.password = require(password);
        return this;
    }

    /**
     * Returns the database name
     *
     * @return the database name
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Sets the database name
     *
     * @param database the database name
     * @return the reference to this {@link SQLDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLDatabaseConfig setDatabase(final String database) {
        this.database = require(database);
        return this;
    }

    /**
     * Adds the new db specific option
     *
     * @param name the option name
     * @param value the option value
     * @return the reference to this {@link SQLDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLDatabaseConfig addOption(final String name,
                                       final String value) {
        options.put(require(name), require(value));
        return this;
    }

    /**
     * Returns the db specific options
     *
     * @return the db specific options
     */
    public Optional<Map<String, String>> getOptions() {
        return Optional.of(options).filter(m -> !m.isEmpty());
    }

    /**
     * Returns the connection timeout
     *
     * @return the connection timeout
     */
    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * Sets the connection timeout
     *
     * @param connectTimeout the connection timeout
     * @return the reference to this {@link SQLDatabaseConfig} instance
     */
    @BuilderMethod
    public SQLDatabaseConfig setConnectTimeout(final Duration connectTimeout) {
        this.connectTimeout = require(connectTimeout);
        return this;
    }

    /**
     * Returns the connection string built from schema, host, port and database parameters
     *
     * @return the connection string built from schema, host, port and database parameters
     */
    public String getConnectionString() {
        return format("sql://?:?/?", host, port, database);
    }
}
