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

package io.rxmicro.test.mockito.mongo.internal;

import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.test.mockito.mongo.EstimatedDocumentCountMock;
import org.bson.Document;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class EstimatedDocumentCountOperationMockFactory extends AbstractOperationMockFactory {

    @SuppressWarnings("unused")
    public void prepare(final MongoDatabase mongoDatabase,
                        final String collectionName,
                        final EstimatedDocumentCountMock operationMock,
                        final Throwable throwable,
                        final Long count) {
        final MongoCollection<Document> collection = newMongoCollection(mongoDatabase, collectionName);
        final Mono<Long> result;
        if (count != null) {
            result = Mono.just(count);
        } else {
            result = Mono.error(throwable);
        }
        when(collection.estimatedDocumentCount()).thenReturn(result);
    }
}
