/*
 * Copyright (c) 2020. http://rxmicro.io
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

import com.mongodb.client.result.DeleteResult;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.test.mockito.mongo.DeleteOperationMock;
import org.bson.Document;
import org.reactivestreams.Publisher;

import static io.rxmicro.test.mockito.internal.CommonMatchers.any;
import static io.rxmicro.test.mockito.mongo.internal.AnyValues.ANY_DOCUMENT;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class DeleteOperationMockFactory extends AbstractOperationMockFactory {

    @SuppressWarnings("unchecked")
    public void prepare(final MongoDatabase mongoDatabase,
                        final String collectionName,
                        final DeleteOperationMock operationMock,
                        final Throwable throwable,
                        final DeleteResult deleteResult) {
        final MongoCollection<Document> collection = newMongoCollection(mongoDatabase, collectionName);
        final Publisher<DeleteResult> publisher = mock(Publisher.class);
        final Document filter = operationMock.getFilter()
                .orElseGet(() -> any(Document.class, ANY_DOCUMENT));
        when(collection.deleteMany(filter)).thenReturn(publisher);
        ifThrowableNotNullThenFailOtherwiseReturnItems(publisher, throwable, deleteResult);
    }
}
