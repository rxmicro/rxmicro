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

package io.rxmicro.data.detail.adapter;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public class PublisherToOptionalMonoFutureAdapter<T> extends CompletableFuture<Optional<T>> {

    private Subscription subscription;

    private T value;

    public PublisherToOptionalMonoFutureAdapter(final Publisher<T> publisher) {
        publisher.subscribe(new SubscriberAdapter());
    }

    /**
     * @author nedis
     * @since 0.4
     */
    private final class SubscriberAdapter implements Subscriber<T> {

        @Override
        public void onSubscribe(final Subscription sub) {
            subscription = sub;
            sub.request(1);
        }

        @Override
        public void onNext(final T item) {
            subscription.cancel();
            value = item;
            onComplete();
        }

        @Override
        public void onError(final Throwable throwable) {
            completeExceptionally(throwable);
        }

        @Override
        public void onComplete() {
            complete(Optional.ofNullable(value));
        }
    }
}
