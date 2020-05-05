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

import com.mongodb.client.model.CountOptions;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.data.mongo.detail.MongoQueries;
import io.rxmicro.test.mockito.internal.CommonMatchers;
import io.rxmicro.test.mockito.mongo.CountDocumentsOperationMock;
import io.rxmicro.test.mockito.mongo.internal.util.CountOptionsMatcher;
import org.bson.Document;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static io.rxmicro.test.mockito.internal.CommonMatchers.any;
import static io.rxmicro.test.mockito.internal.CommonMatchers.isEqual;
import static io.rxmicro.test.mockito.mongo.internal.AnyValues.ANY_DOCUMENT;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class CountDocumentsOperationMockFactory extends AbstractOperationMockFactory {

    public void prepare(final MongoDatabase mongoDatabase,
                        final String collectionName,
                        final CountDocumentsOperationMock operationMock,
                        final Throwable throwable,
                        final Long count) {
        final MongoCollection<Document> collection = newMongoCollection(mongoDatabase, collectionName);
        final Mono<Long> result;
        if (count != null) {
            result = Mono.just(count);
        } else {
            result = Mono.error(throwable);
        }
        if (operationMock.hasOptions()) {
            mockWithOptions(operationMock, collection, result);
        } else {
            mockWithoutOptions(operationMock, collection, result);
        }
    }

    private void mockWithOptions(final CountDocumentsOperationMock operationMock,
                                 final MongoCollection<Document> collection,
                                 final Mono<Long> result) {
        final CountOptions countOptions = new CountOptions();
        operationMock.getHint().ifPresent(countOptions::hint);
        operationMock.getLimit().ifPresent(countOptions::limit);
        operationMock.getSkip().ifPresent(countOptions::skip);
        if (operationMock.isAnyQuery()) {
            when(collection.countDocuments(
                    any(Document.class, ANY_DOCUMENT),
                    isEqual(new CountOptionsMatcher(countOptions), countOptions))
            ).thenReturn(result);
        } else {
            when(collection.countDocuments(
                    operationMock.getQuery()
                            .map(CommonMatchers::isEqual)
                            .orElse(MongoQueries.NULL),
                    isEqual(new CountOptionsMatcher(countOptions), countOptions))
            ).thenReturn(result);
        }
    }

    private void mockWithoutOptions(final CountDocumentsOperationMock operationMock,
                                    final MongoCollection<Document> collection,
                                    final Mono<Long> result) {
        if (operationMock.isAnyQuery()) {
            when(collection.countDocuments(any(Document.class, ANY_DOCUMENT))).thenReturn(result);
        } else {
            final Optional<Document> query = operationMock.getQuery();
            if (query.isPresent()) {
                when(collection.countDocuments(CommonMatchers.isEqual(query.get()))).thenReturn(result);
            } else {
                when(collection.countDocuments()).thenReturn(result);
            }
        }
    }
}
