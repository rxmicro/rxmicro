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

import com.mongodb.reactivestreams.client.AggregatePublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.test.mockito.mongo.AggregateOperationMock;
import org.bson.Document;

import static io.rxmicro.test.mockito.internal.CommonMatchers.anyList;
import static io.rxmicro.test.mockito.mongo.internal.AnyValues.ANY_PIPELINE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @since 0.1
 */
public final class AggregateOperationMockFactory extends AbstractOperationMockFactory {

    public void prepare(final MongoDatabase mongoDatabase,
                        final String collectionName,
                        final AggregateOperationMock operationMock,
                        final Throwable throwable,
                        final Document... items) {
        final MongoCollection<Document> collection = newMongoCollection(mongoDatabase, collectionName);
        final AggregatePublisher<Document> aggregatePublisher = newAggregatePublisher(collection, operationMock);
        ifThrowableNotNullThenFailOtherwiseReturnItems(aggregatePublisher, throwable, items);
    }

    @SuppressWarnings("unchecked")
    private AggregatePublisher<Document> newAggregatePublisher(final MongoCollection<Document> collection,
                                                               final AggregateOperationMock operationMock) {
        final AggregatePublisher<Document> publisher = mock(AggregatePublisher.class);
        if (operationMock.getPipeline().isEmpty()) {
            when(collection.aggregate(anyList(ANY_PIPELINE))).thenReturn(publisher);
        } else {
            when(collection.aggregate(operationMock.getPipeline())).thenReturn(publisher);
        }
        operationMock.getHint().ifPresent(hint -> when(publisher.hint(hint)).thenReturn(publisher));
        when(publisher.allowDiskUse(operationMock.isAllowDiskUse())).thenReturn(publisher);
        return publisher;
    }
}
