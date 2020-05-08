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

package io.rxmicro.rest.server.netty.internal.model;

import io.netty.handler.codec.http.HttpHeaders;

import java.time.Instant;
import java.util.Optional;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractReadableHttpHeaders extends HttpHeaders {

    @Override
    public Integer getInt(final CharSequence name) {
        return Optional.ofNullable(get(name.toString())).map(Integer::parseInt).orElse(null);
    }

    @Override
    public int getInt(final CharSequence name, final int defaultValue) {
        return Optional.ofNullable(get(name.toString())).map(Integer::parseInt).orElse(defaultValue);
    }

    @Override
    public Short getShort(final CharSequence name) {
        return Optional.ofNullable(get(name.toString())).map(Short::parseShort).orElse(null);
    }

    @Override
    public short getShort(final CharSequence name, final short defaultValue) {
        return Optional.ofNullable(get(name.toString())).map(Short::parseShort).orElse(defaultValue);
    }

    @Override
    public Long getTimeMillis(final CharSequence name) {
        return Optional.ofNullable(get(name.toString())).map(v -> Instant.parse(v).toEpochMilli()).orElse(null);
    }

    @Override
    public long getTimeMillis(final CharSequence name, final long defaultValue) {
        return Optional.ofNullable(get(name.toString())).map(v -> Instant.parse(v).toEpochMilli()).orElse(defaultValue);
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public HttpHeaders add(final String name, final Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HttpHeaders add(final String name, final Iterable<?> values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HttpHeaders addInt(final CharSequence name, final int value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HttpHeaders addShort(final CharSequence name, final short value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HttpHeaders set(final String name, final Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HttpHeaders set(final String name, final Iterable<?> values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HttpHeaders setInt(final CharSequence name, final int value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HttpHeaders setShort(final CharSequence name, final short value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HttpHeaders remove(final String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HttpHeaders clear() {
        throw new UnsupportedOperationException();
    }
}
