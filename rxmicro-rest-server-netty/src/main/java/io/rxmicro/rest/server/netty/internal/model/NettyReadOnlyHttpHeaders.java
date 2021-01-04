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

import io.rxmicro.http.local.AbstractHttpHeaders;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;
import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.1
 */
public final class NettyReadOnlyHttpHeaders extends AbstractHttpHeaders {

    private final String requestId;

    private final boolean requestIdGenerated;

    private final io.netty.handler.codec.http.HttpHeaders httpHeaders;

    NettyReadOnlyHttpHeaders(final String requestId,
                             final boolean requestIdGenerated,
                             final io.netty.handler.codec.http.HttpHeaders httpHeaders) {
        this.requestId = requestId;
        this.requestIdGenerated = requestIdGenerated;
        this.httpHeaders = httpHeaders;
    }

    @Override
    public String getValue(final String name) {
        if (requestIdGenerated && REQUEST_ID.equalsIgnoreCase(name)) {
            return requestId;
        } else {
            return httpHeaders.get(name);
        }
    }

    @Override
    public List<String> getValues(final String name) {
        if (requestIdGenerated && REQUEST_ID.equalsIgnoreCase(name)) {
            return List.of(requestId);
        } else {
            return httpHeaders.getAll(name);
        }
    }

    @Override
    public boolean contains(final String name) {
        if (requestIdGenerated && REQUEST_ID.equalsIgnoreCase(name)) {
            return true;
        } else {
            return httpHeaders.contains(name);
        }
    }

    @Override
    public List<Map.Entry<String, String>> getEntries() {
        if (requestIdGenerated) {
            return unmodifiableList(
                    Stream.concat(
                            httpHeaders.entries().stream(),
                            Stream.of(entry(REQUEST_ID, requestId))
                    ).collect(Collectors.toList()));
        } else {
            return unmodifiableList(httpHeaders.entries());
        }
    }

    @Override
    public int size() {
        if (requestIdGenerated) {
            return httpHeaders.size() + 1;
        } else {
            return httpHeaders.size();
        }
    }

    @Override
    public boolean isNotEmpty() {
        return requestIdGenerated || !httpHeaders.isEmpty();
    }
}
