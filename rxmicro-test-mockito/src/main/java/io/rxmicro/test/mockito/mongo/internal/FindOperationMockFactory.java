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

import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.test.mockito.mongo.FindOperationMock;
import org.bson.Document;

import java.util.Optional;
import java.util.OptionalInt;

import static io.rxmicro.test.mockito.internal.CommonMatchers.any;
import static io.rxmicro.test.mockito.mongo.internal.AnyValues.ANY_DOCUMENT;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("unchecked")
public final class FindOperationMockFactory extends AbstractOperationMockFactory {

    public void prepare(final MongoDatabase mongoDatabase,
                        final String collectionName,
                        final FindOperationMock operationMock,
                        final Throwable throwable,
                        final Document... items) {
        final MongoCollection<Document> collection = newMongoCollection(mongoDatabase, collectionName);
        final FindPublisher<Document> findPublisher = newFindPublisher(collection, operationMock);
        ifThrowableNotNullThenFailOtherwiseReturnItems(findPublisher, throwable, items);
    }

    private FindPublisher<Document> newFindPublisher(final MongoCollection<Document> collection,
                                                     final FindOperationMock operationMock) {
        FindPublisher<Document> findPublisher = mock(FindPublisher.class);
        final Optional<Document> query = operationMock.getQuery();
        if (query.isPresent()) {
            when(collection.find(query.get())).thenReturn(findPublisher);
        } else if (operationMock.isAnyQuery()) {
            when(collection.find(any(Document.class, ANY_DOCUMENT))).thenReturn(findPublisher);
        } else {
            when(collection.find()).thenReturn(findPublisher);
        }
        operationMock.getProjection().ifPresent(s ->
                when(findPublisher.projection(s)).thenReturn(findPublisher)
        );
        operationMock.getHint().ifPresent(s ->
                when(findPublisher.hint(s)).thenReturn(findPublisher)
        );
        operationMock.getSort().ifPresent(s ->
                when(findPublisher.sort(s)).thenReturn(findPublisher)
        );
        final OptionalInt limit = operationMock.getLimit();
        if (limit.isPresent()) {
            when(findPublisher.limit(limit.getAsInt())).thenReturn(findPublisher);
        }
        final OptionalInt skip = operationMock.getSkip();
        if (skip.isPresent()) {
            when(findPublisher.skip(skip.getAsInt())).thenReturn(findPublisher);
        }
        when(findPublisher.returnKey(false)).thenReturn(findPublisher);
        return findPublisher;
    }
}
