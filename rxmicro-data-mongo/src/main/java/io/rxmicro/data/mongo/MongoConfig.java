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

import io.rxmicro.common.CommonConstants;
import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.config.Config;
import io.rxmicro.validation.constraint.HostName;
import io.rxmicro.validation.constraint.Nullable;
import io.rxmicro.validation.constraint.Port;

import java.util.Optional;

import static io.rxmicro.common.util.Formats.format;

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
     * Default Mongo port.
     */
    public static final int DEFAULT_PORT = 27_017;

    /**
     * Default Mongo database name.
     */
    public static final String DEFAULT_DB = "db";

    @HostName
    private String host = CommonConstants.LOCALHOST;

    @Nullable
    @Port
    private Integer port = DEFAULT_PORT;

    private String database = DEFAULT_DB;

    public MongoConfig(final String namespace) {
        super(namespace);
    }

    /**
     * Sets the server host name.
     *
     * @param host the server host name
     * @return the reference to this {@link MongoConfig} instance
     */
    @BuilderMethod
    public MongoConfig setHost(final String host) {
        this.host = ensureValid(host);
        return this;
    }

    /**
     * Sets the server port.
     *
     * @param port the server port or {@code null} if default port should be applied.
     * @return the reference to this {@link MongoConfig} instance
     */
    @BuilderMethod
    public MongoConfig setPort(final Integer port) {
        this.port = ensureValid(port);
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
        this.database = ensureValid(database);
        return this;
    }

    /**
     * Returns the connection string built from schema, host and port parameters.
     *
     * @return the connection string built from schema, host and port parameters
     */
    public String getConnectionString() {
        return format("mongodb://?", host, Optional.ofNullable(port).map(p -> ":" + p).orElse(""));
    }

    @Override
    public String toString() {
        return "MongoConfig {" +
                "connectionString='" + getConnectionString() + '\'' +
                ", database='" + database + '\'' +
                '}';
    }
}
