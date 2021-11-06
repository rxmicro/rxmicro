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

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * @author nedis
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
     * @since 0.1
     */
    @SuppressWarnings("NullableProblems")
    private static final class TempDocument extends Document {

        private static final long serialVersionUID = 4916175951040763157L;

        private final String name;

        private TempDocument(final String name) {
            this.name = name;
        }

        @Override
        public TempDocument append(final String key, final Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object put(final String key, final Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object remove(final Object key) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(final Object key, final Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void putAll(final Map<? extends String, ?> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Set<String> keySet() {
            return Set.of();
        }

        @Override
        public Collection<Object> values() {
            return Set.of();
        }

        @Override
        public Set<Entry<String, Object>> entrySet() {
            return Set.of();
        }

        @Override
        public void replaceAll(final BiFunction<? super String, ? super Object, ?> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object putIfAbsent(final String key, final Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(final String key, final Object oldValue, final Object newValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object replace(final String key, final Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            if (!super.equals(other)) {
                return false;
            }
            final TempDocument that = (TempDocument) other;
            return name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), name);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * @author nedis
     * @since 0.1
     */
    private static final class SubscriberImpl<T> implements Subscriber<T> {

        @Override
        public void onSubscribe(final Subscription subscription) {
            // do nothing
        }

        @Override
        public void onNext(final Object item) {
            // do nothing
        }

        @Override
        public void onError(final Throwable throwable) {
            // do nothing
        }

        @Override
        public void onComplete() {
            // do nothing
        }

        @Override
        public String toString() {
            return "<ANY-SUBSCRIBER>";
        }
    }
}
