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

import com.mongodb.reactivestreams.client.DistinctPublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.test.mockito.mongo.DistinctOperationMock;
import org.bson.Document;

import java.util.Optional;

import static io.rxmicro.test.mockito.internal.CommonMatchers.any;
import static io.rxmicro.test.mockito.internal.CommonMatchers.equal;
import static io.rxmicro.test.mockito.mongo.internal.AnyValues.ANY_DOCUMENT;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("unchecked")
public final class DistinctOperationMockFactory extends AbstractOperationMockFactory {

    public <T> void prepare(final MongoDatabase mongoDatabase,
                            final String collectionName,
                            final DistinctOperationMock<T> operationMock,
                            final Throwable throwable,
                            final T... items) {
        final MongoCollection<Document> collection = newMongoCollection(mongoDatabase, collectionName);
        final DistinctPublisher<T> distinctPublisher = newDistinctPublisher(collection, operationMock);
        ifThrowableNotNullThenFailOtherwiseReturnItems(distinctPublisher, throwable, items);
    }

    private <T> DistinctPublisher<T> newDistinctPublisher(final MongoCollection<Document> collection,
                                                          final DistinctOperationMock<T> operationMock) {
        final DistinctPublisher<T> distinctPublisher = mock(DistinctPublisher.class);
        final Optional<Document> query = operationMock.getQuery();
        if (query.isPresent()) {
            when(collection.distinct(
                    operationMock.getField(),
                    query.get(),
                    operationMock.getResultClass())
            ).thenReturn(distinctPublisher);
        } else if (operationMock.isAnyQuery()) {
            when(collection.distinct(
                    equal(operationMock.getField()),
                    any(Document.class, ANY_DOCUMENT),
                    equal(operationMock.getResultClass()))
            ).thenReturn(distinctPublisher);
        } else {
            when(collection.distinct(
                    operationMock.getField(),
                    operationMock.getResultClass())
            ).thenReturn(distinctPublisher);
        }
        return distinctPublisher;
    }

}
