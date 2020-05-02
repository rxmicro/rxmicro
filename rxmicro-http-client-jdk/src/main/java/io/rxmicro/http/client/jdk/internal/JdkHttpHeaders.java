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

package io.rxmicro.http.client.jdk.internal;

import io.rxmicro.http.HttpHeaders;

import java.util.List;
import java.util.Map;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;
import static io.rxmicro.common.util.Formats.format;
import static java.util.Map.entry;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
final class JdkHttpHeaders implements HttpHeaders {

    private final java.net.http.HttpHeaders httpHeaders;

    JdkHttpHeaders(final java.net.http.HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    @Override
    public String getValue(final String name) {
        return httpHeaders.firstValue(name).orElse(null);
    }

    @Override
    public List<String> getValues(final String name) {
        return httpHeaders.allValues(name);
    }

    @Override
    public boolean contains(final String name) {
        return httpHeaders.map().containsKey(name);
    }

    @Override
    public List<Map.Entry<String, String>> getEntries() {
        return unmodifiableList(
                httpHeaders.map().entrySet()
                        .stream()
                        .flatMap(e -> e.getValue().stream().map(v -> entry(e.getKey(), v)))
                        .collect(toList())
        );
    }

    @Override
    public int size() {
        return httpHeaders.map().size();
    }

    @Override
    public boolean isNotEmpty() {
        return !httpHeaders.map().isEmpty();
    }

    @Override
    public String toString() {
        return getEntries().stream()
                .map(e -> format("?=?", e.getKey(), e.getValue()))
                .collect(joining(", "));
    }
}
