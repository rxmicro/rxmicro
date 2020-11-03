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

package io.rxmicro.data.mongo;

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.config.Config;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.config.Networks.validatePort;

/**
 * Allows configuring the environment specific configs for Mongo database.
 *
 * <ul>
 *     <li>{@link MongoConfig} must be used for environment specific configs.</li>
 *     <li>{@link MongoConfigCustomizer} must be used for application specific configs.</li>
 * </ul>
 *
 * @author nedis
 * @see MongoRepository
 * @see MongoClientFactory
 * @see MongoCodecsConfigurator
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
public final class MongoConfig extends Config {

    /**
     * Default Mongo host.
     */
    public static final String DEFAULT_HOST = "localhost";

    /**
     * Default Mongo port.
     */
    public static final int DEFAULT_PORT = 27_017;

    /**
     * Default Mongo database name.
     */
    public static final String DEFAULT_DB = "db";

    private String host = DEFAULT_HOST;

    private int port = DEFAULT_PORT;

    private String database = DEFAULT_DB;

    /**
     * Sets the server host name.
     *
     * @param host the server host name
     * @return the reference to this {@link MongoConfig} instance
     */
    @BuilderMethod
    public MongoConfig setHost(final String host) {
        this.host = require(host);
        return this;
    }

    /**
     * Sets the server port.
     *
     * @param port the server port
     * @return the reference to this {@link MongoConfig} instance
     */
    @BuilderMethod
    public MongoConfig setPort(final int port) {
        this.port = validatePort(port);
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
     * @return the reference to this {@link MongoConfig} instance
     */
    @BuilderMethod
    public MongoConfig setDatabase(final String database) {
        this.database = require(database);
        return this;
    }

    /**
     * Returns the connection string built from schema, host and port parameters.
     *
     * @return the connection string built from schema, host and port parameters
     */
    public String getConnectionString() {
        return format("mongodb://?:?", host, port);
    }

    @Override
    public String toString() {
        return "MongoConfig {" +
                "connectionString='" + getConnectionString() + '\'' +
                ", database='" + database + '\'' +
                '}';
    }
}
