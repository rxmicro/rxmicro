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

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.config.Config;
import io.rxmicro.data.mongo.internal.RxMicroMongoCodecRegistry;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.config.Networks.validatePort;

/**
 * Allows configuring Monfo DB options.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
public final class MongoConfig extends Config {

    public static final String DEFAULT_HOST = "localhost";

    public static final int DEFAULT_PORT = 27_017;

    public static final String DEFAULT_DB = "db";

    private final MongoClientSettings.Builder builder = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(format("mongodb://?:?", DEFAULT_HOST, DEFAULT_PORT)));

    private MongoCodecsConfigurator mongoCodecsConfigurator = new MongoCodecsConfigurator();

    private String host = DEFAULT_HOST;

    private int port = DEFAULT_PORT;

    private String database = DEFAULT_DB;

    /**
     * Sets the server host name
     *
     * @param host the server host name
     * @return the reference to this {@link MongoConfig} instance
     */
    @BuilderMethod
    public MongoConfig setHost(final String host) {
        this.host = require(host);
        builder.applyConnectionString(new ConnectionString(getConnectionString()));
        return this;
    }

    /**
     * Sets the server port
     *
     * @param port the server port
     * @return the reference to this {@link MongoConfig} instance
     */
    @BuilderMethod
    public MongoConfig setPort(final int port) {
        this.port = validatePort(port);
        builder.applyConnectionString(new ConnectionString(getConnectionString()));
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
     * @return the reference to this {@link MongoConfig} instance
     */
    @BuilderMethod
    public MongoConfig setDatabase(final String database) {
        this.database = require(database);
        return this;
    }

    /**
     * Sets the custom mongo codecs configurator
     *
     * @param mongoCodecsConfigurator the custom mongo codecs configurator
     * @return the reference to this {@link MongoConfig} instance
     */
    @BuilderMethod
    public MongoConfig setMongoCodecsConfigurator(final MongoCodecsConfigurator mongoCodecsConfigurator) {
        this.mongoCodecsConfigurator = require(mongoCodecsConfigurator);
        return this;
    }

    /**
     * Returns the connection string built from schema, host and port parameters
     *
     * @return the connection string built from schema, host and port parameters
     */
    public String getConnectionString() {
        return format("mongodb://?:?", host, port);
    }

    /**
     * Returns {@link MongoClientSettings.Builder} instance that allows configuring the
     * {@link com.mongodb.reactivestreams.client.MongoClient} using low-level reactive Mongo DB java driver API.
     *
     * @return Returns {@link MongoClientSettings.Builder} instance
     */
    public MongoClientSettings.Builder getMongoClientSettingsBuilder() {
        return builder;
    }

    /**
     * Builds the {@link MongoClientSettings} instance
     *
     * @return the {@link MongoClientSettings} instance
     */
    public MongoClientSettings buildMongoClientSettings() {
        return builder
                .codecRegistry(
                        new RxMicroMongoCodecRegistry(
                                mongoCodecsConfigurator.withDefaultConfigurationIfNotConfigured()
                        )
                )
                .build();
    }

    @Override
    public String toString() {
        return "MongoConfig {" +
                "connectionString='" + getConnectionString() + '\'' +
                ", database='" + database + '\'' +
                '}';
    }
}
