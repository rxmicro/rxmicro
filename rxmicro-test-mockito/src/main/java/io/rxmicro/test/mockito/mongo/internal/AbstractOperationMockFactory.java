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
import org.bson.Document;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.test.mockito.internal.CommonMatchers.any;
import static io.rxmicro.test.mockito.mongo.internal.AnyValues.ANY_SUBSCRIBER;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
abstract class AbstractOperationMockFactory {

    @SuppressWarnings("unchecked")
    final MongoCollection<Document> newMongoCollection(final MongoDatabase mongoDatabase,
                                                       final String collectionName) {
        final MongoCollection<Document> collection = mock(MongoCollection.class);
        when(mongoDatabase.getCollection(collectionName)).thenReturn(collection);
        return collection;
    }

    @SuppressWarnings("unchecked")
    final <T> void ifThrowableNotNullThenFailOtherwiseReturnItems(final Publisher<T> publisher,
                                                                  final Throwable throwable,
                                                                  final T... items) {
        doAnswer(invocation -> {
            final Subscriber<T> subscriber = invocation.getArgument(0);
            subscriber.onSubscribe(mock(Subscription.class));
            if (throwable != null) {
                subscriber.onError(throwable);
            } else {
                for (final T item : items) {
                    subscriber.onNext(require(item, "item or document couldn't be a null!"));
                }
                subscriber.onComplete();
            }
            return null;
        }).when(publisher).subscribe(any(Subscriber.class, ANY_SUBSCRIBER));
    }
}
