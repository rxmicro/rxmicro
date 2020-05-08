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

package io.rxmicro.test.local;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.http.client.HttpClient;
import io.rxmicro.rest.Version;
import io.rxmicro.test.BlockingHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.common.util.UrlPaths.normalizeUrlPath;
import static io.rxmicro.http.HttpStandardHeaderNames.API_VERSION;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.1
 */
public final class BlockingHttpClientImpl implements BlockingHttpClient {

    private final HttpClient httpClient;

    private final String versionValue;

    private final boolean urlPathVersion;

    private final boolean headerVersion;

    public BlockingHttpClientImpl(final HttpClient httpClient,
                                  final String versionValue,
                                  final Version.Strategy versionStrategy) {
        this.httpClient = require(httpClient);
        this.urlPathVersion = !versionValue.isEmpty() && versionStrategy == Version.Strategy.URL_PATH;
        this.headerVersion = !versionValue.isEmpty() && versionStrategy == Version.Strategy.HEADER;
        if (urlPathVersion) {
            this.versionValue = normalizeUrlPath(versionValue);
        } else {
            this.versionValue = require(versionValue);
        }
    }

    @Override
    public ClientHttpResponse send(final String method,
                                   final String path,
                                   final HttpHeaders headers) {
        return httpClient.sendAsync(
                nonNull(method, "method"),
                getValidPath(path),
                getHeaders(headers)
        ).join();
    }

    @Override
    public ClientHttpResponse send(final String method,
                                   final String path,
                                   final HttpHeaders headers,
                                   final Object body) {
        validateThatPathNotContainsQueryParams(path);
        return httpClient.sendAsync(
                nonNull(method, "method"),
                getValidPath(path),
                getHeaders(headers),
                nonNull(body, "body")
        ).join();
    }

    private List<Map.Entry<String, String>> getHeaders(final HttpHeaders headers) {
        if (headerVersion) {
            final List<Map.Entry<String, String>> headerEntries =
                    new ArrayList<>(nonNull(headers, "headers").getEntries());
            if (headerEntries.stream().noneMatch(e -> API_VERSION.equalsIgnoreCase(e.getKey()))) {
                headerEntries.add(entry(API_VERSION, versionValue));
            }
            return headerEntries;
        } else {
            return nonNull(headers, "headers").getEntries();
        }

    }

    private String getValidPath(final String path) {
        final String normalizeUrlPath = normalizeUrlPath(nonNull(path, "path"));
        if (urlPathVersion && !normalizeUrlPath.startsWith(versionValue)) {
            return versionValue + normalizeUrlPath;
        } else {
            return normalizeUrlPath;
        }
    }

    @Override
    public void release() {
        httpClient.release();
    }

    private void validateThatPathNotContainsQueryParams(final String path) {
        if (nonNull(path, "path").indexOf('?') != -1) {
            throw new IllegalArgumentException("Query params not supported here. Use HTTP body instead!");
        }
    }

    private <T> T nonNull(final T object,
                          final String name) {
        return require(object, format("'?' couldn't be null", name));
    }
}
