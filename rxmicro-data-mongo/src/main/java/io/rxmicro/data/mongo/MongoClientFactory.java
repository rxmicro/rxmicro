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

import com.mongodb.reactivestreams.client.MongoClient;
import io.rxmicro.data.mongo.internal.MongoClientBuilder;
import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;
import io.rxmicro.runtime.local.provider.LazyInstanceProvider;

import static io.rxmicro.config.Config.getDefaultNameSpace;
import static io.rxmicro.runtime.local.InstanceContainer.getSingleton;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class MongoClientFactory {

    private static final MongoClientBuilder BUILDER = new MongoClientBuilder();

    public static MongoClient getMongoClient(final String nameSpace) {
        return getSingleton(
                new ByTypeInstanceQualifier<>(MongoClient.class),
                new LazyInstanceProvider<>(MongoClient.class, () -> BUILDER.getMongoClient(nameSpace))
        );
    }

    public static MongoClient getMongoClient() {
        return getMongoClient(getDefaultNameSpace(MongoConfig.class));
    }

    private MongoClientFactory() {
    }
}
