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

package io.rxmicro.data.mongo.detail;

import com.mongodb.reactivestreams.client.MongoCollection;
import io.rxmicro.data.detail.AbstractDataRepository;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import org.bson.Document;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * <p>
 * Represents a base class for the Mongo data repository implementations.
 *
 * <p>
 * Read more:
 * <a href="https://docs.rxmicro.io/latest/user-guide/data-mongo.html#data-mongo-partial-implementation-section">
 * https://docs.rxmicro.io/latest/user-guide/data-mongo.html#data-mongo-partial-implementation-section
 * </a>
 *
 * @author nedis
 * @see io.rxmicro.data.mongo.PartialImplementation
 * @since 0.1
 */
public abstract class AbstractMongoRepository extends AbstractDataRepository {

    /**
     * The logger.
     */
    protected final Logger logger;

    /**
     * The Mongo collection.
     */
    protected final MongoCollection<Document> collection;

    /**
     * Creates an instance of {@link AbstractMongoRepository} type.
     *
     * @param repositoryInterface the specified repository interface
     * @param collection          the collection name
     */
    protected AbstractMongoRepository(final Class<?> repositoryInterface,
                                      final MongoCollection<Document> collection) {
        this.logger = LoggerFactory.getLogger(repositoryInterface);
        this.collection = collection;
    }
}
