/*
 * Copyright 2019 http://rxmicro.io
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

package io.rxmicro.data.mongo.detail;

import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.data.mongo.internal.MongoDatabaseBuilder;
import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;
import io.rxmicro.runtime.local.provider.LazyInstanceProvider;

import java.util.function.Function;

import static io.rxmicro.runtime.local.InstanceContainer.getSingleton;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class MongoRepositoryFactory {

    private static final MongoDatabaseBuilder BUILDER = new MongoDatabaseBuilder();

    public static <T> T createMongoRepository(final String nameSpace,
                                              final Function<MongoDatabase, T> creator) {
        final MongoDatabase mongoDatabase = getSingleton(
                new ByTypeInstanceQualifier<>(MongoDatabase.class),
                new LazyInstanceProvider<>(MongoDatabase.class, () -> BUILDER.createMongoDatabase(nameSpace))
        );
        return creator.apply(mongoDatabase);
    }

    private MongoRepositoryFactory() {
    }
}
