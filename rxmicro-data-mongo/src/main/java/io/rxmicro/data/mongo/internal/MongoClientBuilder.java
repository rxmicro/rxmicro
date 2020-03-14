/*
 * Copyright (c) 2020. http://rxmicro.io
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

import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import io.rxmicro.data.mongo.MongoConfig;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.runtime.AutoRelease;

import static com.mongodb.reactivestreams.client.MongoClients.create;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.runtime.local.InstanceContainer.registerAutoRelease;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class MongoClientBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            format("?.MongoClient", MongoClientBuilder.class.getPackageName())
    );

    public MongoClient getMongoClient(final String nameSpace) {
        final MongoConfig mongoConfig = getConfig(nameSpace, MongoConfig.class);
        return getMongoClient(mongoConfig);
    }

    private MongoClient getMongoClient(final MongoConfig mongoConfig) {
        final String connectionString = mongoConfig.getConnectionString();
        final MongoClientSettings settings = mongoConfig.buildMongoClientSettings();
        final MongoClient mongoClient = create(settings);
        LOGGER.info("Mongo client created: connectionString='?', database='?'",
                connectionString, mongoConfig.getDatabase());
        registerAutoRelease(new MongoClientProxy(connectionString, mongoClient));
        return mongoClient;
    }

    /**
     * @author nedis
     * @link http://rxmicro.io
     * @since 0.1
     */
    private static final class MongoClientProxy implements AutoRelease {

        private final String connectionString;

        private final MongoClient mongoClient;

        private MongoClientProxy(final String connectionString,
                                 final MongoClient mongoClient) {
            this.connectionString = require(connectionString);
            this.mongoClient = require(mongoClient);
        }

        @Override
        public void release() {
            mongoClient.close();
            LOGGER.info("Mongo client closed: connectionString='?'", connectionString);
        }
    }
}
