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
import io.rxmicro.config.Config;
import io.rxmicro.data.mongo.internal.RxMicroMongoCodecRegistry;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.config.Networks.validatePort;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class MongoConfig extends Config {

    public static final String DEFAULT_HOST = "localhost";

    public static final int DEFAULT_PORT = 27017;

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
     * @param host host name
     * @return A reference to this {@code MongoConfig}
     */
    public MongoConfig setHost(final String host) {
        this.host = require(host);
        builder.applyConnectionString(new ConnectionString(getConnectionString()));
        return this;
    }

    /**
     * Sets the server port
     *
     * @param port server port
     * @return A reference to this {@code MongoConfig}
     */
    public MongoConfig setPort(final int port) {
        this.port = validatePort(port);
        builder.applyConnectionString(new ConnectionString(getConnectionString()));
        return this;
    }

    public String getDatabase() {
        return database;
    }

    /**
     * Sets the database name
     *
     * @param database database name
     * @return A reference to this {@code MongoConfig}
     */
    public MongoConfig setDatabase(final String database) {
        this.database = require(database);
        return this;
    }

    public MongoConfig setMongoCodecsConfigurator(final MongoCodecsConfigurator mongoCodecsConfigurator) {
        this.mongoCodecsConfigurator = require(mongoCodecsConfigurator);
        return this;
    }

    public String getConnectionString() {
        return format("mongodb://?:?", host, port);
    }

    public MongoClientSettings.Builder getMongoClientSettingsBuilder() {
        return builder;
    }

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
