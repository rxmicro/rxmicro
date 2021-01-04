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

package io.rxmicro.rest.client.netty.internal;

import io.rxmicro.http.local.AbstractHttpHeaders;

import java.util.List;
import java.util.Map;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;

/**
 * @author nedis
 * @since 0.8
 */
final class NettyHttpHeaders extends AbstractHttpHeaders {

    private final io.netty.handler.codec.http.HttpHeaders httpHeaders;

    NettyHttpHeaders(final io.netty.handler.codec.http.HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    @Override
    public String getValue(final String name) {
        return httpHeaders.get(name);
    }

    @Override
    public List<String> getValues(final String name) {
        return httpHeaders.getAll(name);
    }

    @Override
    public boolean contains(final String name) {
        return httpHeaders.contains(name);
    }

    @Override
    public List<Map.Entry<String, String>> getEntries() {
        return unmodifiableList(httpHeaders.entries());
    }

    @Override
    public int size() {
        return httpHeaders.size();
    }

    @Override
    public boolean isNotEmpty() {
        return !httpHeaders.isEmpty();
    }
}
