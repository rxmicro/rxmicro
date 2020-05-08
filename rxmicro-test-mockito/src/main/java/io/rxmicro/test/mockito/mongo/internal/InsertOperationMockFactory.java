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

import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.test.mockito.mongo.InsertOperationMock;
import org.bson.Document;
import org.reactivestreams.Publisher;

import static io.rxmicro.test.mockito.internal.CommonMatchers.any;
import static io.rxmicro.test.mockito.mongo.internal.AnyValues.ANY_DOCUMENT;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @since 0.1
 */
public final class InsertOperationMockFactory extends AbstractOperationMockFactory {

    @SuppressWarnings("unchecked")
    public void prepare(final MongoDatabase mongoDatabase,
                        final String collectionName,
                        final InsertOperationMock operationMock,
                        final Throwable throwable,
                        final InsertOneResult insertOneResult) {
        final MongoCollection<Document> collection = newMongoCollection(mongoDatabase, collectionName);
        final Publisher<InsertOneResult> publisher = mock(Publisher.class);
        final Document insertedDocument = operationMock.getDocument()
                .orElseGet(() -> any(Document.class, ANY_DOCUMENT));
        when(collection.insertOne(insertedDocument)).thenReturn(publisher);
        ifThrowableNotNullThenFailOtherwiseReturnItems(publisher, throwable, insertOneResult);
    }
}
