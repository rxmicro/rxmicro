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

import com.mongodb.MongoClientSettings;
import io.rxmicro.data.mongo.internal.MongoClientBuilder;

/**
 * Allows configuring the application specific configs for Mongo database driver.
 *
 * <ul>
 *     <li>{@link MongoConfig} must be used for environment specific configs.</li>
 *     <li>{@link MongoConfigCustomizer} must be used for application specific configs.</li>
 * </ul>
 *
 * @author nedis
 * @see MongoConfig
 * @since 0.7
 */
public final class MongoConfigCustomizer {

    /**
     * Returns the {@link MongoCodecsConfigurator} to configure required codecs.
     *
     * @return the {@link MongoCodecsConfigurator} to configure required codecs
     */
    public static MongoCodecsConfigurator getCurrentMongoCodecsConfigurator() {
        return MongoClientBuilder.getInstance().getMongoCodecsConfigurator();
    }

    /**
     * Returns {@link MongoClientSettings.Builder} instance that allows configuring the
     * {@link com.mongodb.reactivestreams.client.MongoClient} using low-level reactive Mongo DB java driver API.
     *
     * @return Returns {@link MongoClientSettings.Builder} instance
     */
    public MongoClientSettings.Builder getCurrentMongoClientSettingsBuilder() {
        return MongoClientBuilder.getInstance().getMongoClientSettingsBuilder();
    }
}
