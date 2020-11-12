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

package io.rxmicro.data.mongo.internal;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import io.rxmicro.data.mongo.MongoCodecsConfigurator;
import io.rxmicro.data.mongo.MongoConfig;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.runtime.AutoRelease;

import static com.mongodb.reactivestreams.client.MongoClients.create;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.runtime.local.InstanceContainer.registerAutoRelease;

/**
 * @author nedis
 * @since 0.1
 */
public final class MongoClientBuilder {

    private static final MongoClientBuilder INSTANCE = new MongoClientBuilder();

    private final MongoCodecsConfigurator mongoCodecsConfigurator = new MongoCodecsConfigurator();

    private final MongoClientSettings.Builder mongoClientSettingsBuilder = MongoClientSettings.builder();

    private MongoDBClient mongoDBClient;

    public static MongoClientBuilder getInstance() {
        return INSTANCE;
    }

    private MongoClientBuilder() {
    }

    public MongoCodecsConfigurator getMongoCodecsConfigurator() {
        validateState();
        return mongoCodecsConfigurator;
    }

    public MongoClientSettings.Builder getMongoClientSettingsBuilder() {
        validateState();
        return mongoClientSettingsBuilder;
    }

    public MongoClient build(final String namespace) {
        validateState();
        return build(getConfig(namespace, MongoConfig.class));
    }

    private MongoClient build(final MongoConfig mongoConfig) {
        final String connectionString = mongoConfig.getConnectionString();
        final MongoClientSettings settings = getMongoClientSettingsBuilder()
                .applyConnectionString(new ConnectionString(connectionString))
                .codecRegistry(
                        new RxMicroMongoCodecRegistry(
                                ((AbstractMongoCodecsConfigurator<MongoCodecsConfigurator>) getMongoCodecsConfigurator())
                                        .withDefaultConfigurationIfNotConfigured()
                        )
                )
                .build();
        final MongoClient mongoClient = create(settings);
        mongoDBClient = new MongoDBClient(this, mongoConfig, connectionString, mongoClient);
        return mongoClient;
    }

    private void validateState() {
        if (mongoDBClient != null) {
            throw new IllegalStateException("Mongo client already built! " +
                    "Any customizations must be done before building of the mongo client!");
        }
    }

    /**
     * @author nedis
     * @since 0.1
     */
    private static final class MongoDBClient implements AutoRelease {

        private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBClient.class);

        private final MongoClientBuilder builder;

        private final String connectionString;

        private final MongoClient mongoClient;

        private MongoDBClient(final MongoClientBuilder builder,
                              final MongoConfig mongoConfig,
                              final String connectionString,
                              final MongoClient mongoClient) {
            this.builder = builder;
            this.connectionString = require(connectionString);
            this.mongoClient = require(mongoClient);
            LOGGER.info("Mongo client created: connectionString='?', database='?'", connectionString, mongoConfig.getDatabase());
            registerAutoRelease(this);
        }

        @Override
        public void release() {
            mongoClient.close();
            LOGGER.info("Mongo client closed: connectionString='?'", connectionString);
            builder.mongoDBClient = null;
        }
    }
}
