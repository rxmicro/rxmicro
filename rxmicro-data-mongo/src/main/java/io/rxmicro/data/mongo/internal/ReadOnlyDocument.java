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

package io.rxmicro.data.mongo.internal;

import org.bson.Document;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public class ReadOnlyDocument extends Document {

    @Override
    public Document append(final String key, final Object value) {
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
    public void putAll(final Map<? extends String, ?> map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public Set<String> keySet() {
        return Set.of();
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public Collection<Object> values() {
        return Set.of();
    }

    @Override
    @SuppressWarnings("NullableProblems")
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
    public boolean remove(final Object key, final Object value) {
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
    public Object computeIfAbsent(final String key, final Function<? super String, ?> mappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object computeIfPresent(final String key, final BiFunction<? super String, ? super Object, ?> remappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object compute(final String key, final BiFunction<? super String, ? super Object, ?> remappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object merge(final String key, final Object value, final BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        throw new UnsupportedOperationException();
    }
}
