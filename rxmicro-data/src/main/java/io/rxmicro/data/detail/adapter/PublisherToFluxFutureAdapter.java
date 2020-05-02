/*
 * Copyright 2019 https://rxmicro.io
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public class PublisherToFluxFutureAdapter<T> extends CompletableFuture<List<T>> {

    private final List<T> list = new ArrayList<>();

    public PublisherToFluxFutureAdapter(final Publisher<T> publisher) {
        publisher.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(final Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(final T item) {
                list.add(item);
            }

            @Override
            public void onError(final Throwable throwable) {
                completeExceptionally(throwable);
            }

            @Override
            public void onComplete() {
                complete(list);
            }
        });
    }
}
