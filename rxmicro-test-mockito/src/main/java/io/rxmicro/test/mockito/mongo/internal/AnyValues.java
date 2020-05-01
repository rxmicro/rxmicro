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

import org.bson.Document;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Objects;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class AnyValues {

    public static final Document ANY_DOCUMENT = new TempDocument("<ANY-DOCUMENT>");

    public static final Subscriber<?> ANY_SUBSCRIBER = new SubscriberImpl<>();

    public static final String ANY_PIPELINE = "<ANY-PIPELINE>";

    private AnyValues() {
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    private static final class TempDocument extends Document {
        private final String name;

        public TempDocument(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            final TempDocument that = (TempDocument) o;
            return name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), name);
        }
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    private static final class SubscriberImpl<T> implements Subscriber<T> {

        @Override
        public void onSubscribe(final Subscription s) {

        }

        @Override
        public void onNext(final Object o) {

        }

        @Override
        public void onError(final Throwable t) {

        }

        @Override
        public void onComplete() {

        }

        @Override
        public String toString() {
            return "<ANY-SUBSCRIBER>";
        }
    }
}
