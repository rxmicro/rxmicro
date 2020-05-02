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

    public String getHost() {
        return host;
    }

    @BuilderMethod
    public SQLDatabaseConfig setHost(final String host) {
        this.host = require(host);
        return this;
    }

    public int getPort() {
        return port;
    }

    @BuilderMethod
    public SQLDatabaseConfig setPort(final int port) {
        this.port = validatePort(port);
        return this;
    }

    public String getUser() {
        return user;
    }

    @BuilderMethod
    public SQLDatabaseConfig setUser(final String user) {
        this.user = require(user);
        return this;
    }

    public CharSequence getPassword() {
        return password;
    }

    @BuilderMethod
    public SQLDatabaseConfig setPassword(final CharSequence password) {
        this.password = require(password);
        return this;
    }

    public String getDatabase() {
        return database;
    }

    @BuilderMethod
    public SQLDatabaseConfig setDatabase(final String database) {
        this.database = require(database);
        return this;
    }

    @BuilderMethod
    public SQLDatabaseConfig addOption(final String name,
                                       final String value) {
        options.put(require(name), require(value));
        return this;
    }

    public Optional<Map<String, String>> getOptions() {
        return Optional.of(options).filter(m -> !m.isEmpty());
    }

    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    @BuilderMethod
    public SQLDatabaseConfig setConnectTimeout(final Duration connectTimeout) {
        this.connectTimeout = require(connectTimeout);
        return this;
    }

    public String getConnectionString() {
        return format("sql://?:?/?", host, port, database);
    }
}
