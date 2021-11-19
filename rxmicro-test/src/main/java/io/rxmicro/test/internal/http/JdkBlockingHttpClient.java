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

package io.rxmicro.test.internal.http;

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.BaseUrlPath;
import io.rxmicro.rest.Version;
import io.rxmicro.rest.client.detail.HttpClientContentConverter;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.test.local.BlockingHttpClientConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static io.rxmicro.common.CommonConstants.RX_MICRO_FRAMEWORK_NAME;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.common.util.Strings.startsWith;
import static io.rxmicro.common.util.UrlPaths.normalizeUrlPath;
import static io.rxmicro.http.HttpStandardHeaderNames.ACCEPT;
import static io.rxmicro.http.HttpStandardHeaderNames.API_VERSION;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_TYPE;
import static io.rxmicro.http.HttpStandardHeaderNames.USER_AGENT;
import static io.rxmicro.runtime.detail.RxMicroRuntime.getRxMicroVersion;
import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.8
 */
public final class JdkBlockingHttpClient implements BlockingHttpClient {

    private final BlockingHttpClientConfig config;

    private final HttpClient client;

    private final Function<Object, byte[]> requestBodyConverter;

    private final Function<byte[], Object> responseBodyConverter;

    private final Map.Entry<String, String> acceptHeader;

    private final Map.Entry<String, String> contentTypeHeader;

    private final Map.Entry<String, String> userAgentHeader;

    private final Map.Entry<String, String> versionHeader;

    private final String baseUrlPath;

    private JdkBlockingHttpClient(final BlockingHttpClientConfig config,
                                  final HttpClientContentConverter contentConverter,
                                  final String baseUrlPath,
                                  final BaseUrlPath.Position baseUrlPosition,
                                  final String versionValue,
                                  final Version.Strategy versionStrategy) {
        this.config = config;
        final String contentType = require(contentConverter.getContentType());
        this.acceptHeader = entry(ACCEPT, contentType);
        this.contentTypeHeader = entry(CONTENT_TYPE, contentType);
        this.userAgentHeader = entry(USER_AGENT, format("?-test-jdk-http-client/?", RX_MICRO_FRAMEWORK_NAME, getRxMicroVersion()));
        final List<String> baseUrlFragments = new ArrayList<>();
        if (baseUrlPath != null) {
            baseUrlFragments.add(baseUrlPath);
        }
        if (versionStrategy == Version.Strategy.URL_PATH) {
            if (baseUrlPath != null) {
                final int index = baseUrlPosition == BaseUrlPath.Position.BEFORE_VERSION ? 1 : 0;
                baseUrlFragments.add(index, versionValue);
            } else {
                baseUrlFragments.add(versionValue);
            }
            this.versionHeader = null;
        } else {
            this.versionHeader = Optional.ofNullable(versionValue).map(v -> entry(API_VERSION, v)).orElse(null);
        }
        this.baseUrlPath = baseUrlFragments.isEmpty() ? null : String.join(EMPTY_STRING, baseUrlFragments);
        this.requestBodyConverter = require(contentConverter.getRequestContentConverter());
        this.responseBodyConverter = require(contentConverter.getResponseContentConverter());
        this.client = HttpClient.newBuilder()
                .followRedirects(config.isFollowRedirects() ?
                        HttpClient.Redirect.ALWAYS :
                        HttpClient.Redirect.NEVER)
                .build();
    }

    @Override
    public ClientHttpResponse send(final String method,
                                   final String path,
                                   final HttpHeaders headers) {
        final HttpRequest request = newRequestBuilder(getValidPath(path), nonNull(headers, "headers").getEntries(), false)
                .method(nonNull(method, "method"), HttpRequest.BodyPublishers.noBody())
                .build();
        return sendRequest(request);
    }

    @Override
    public ClientHttpResponse send(final String method,
                                   final String path,
                                   final HttpHeaders headers,
                                   final Object body) {
        final String validPath = getValidPath(path);
        validateThatPathNotContainsQueryParams(validPath);
        final byte[] requestBody = requestBodyConverter.apply(nonNull(body, "body"));
        final HttpRequest request = newRequestBuilder(validPath, nonNull(headers, "headers").getEntries(), true)
                .method(nonNull(method, "method"), HttpRequest.BodyPublishers.ofByteArray(requestBody))
                .build();
        return sendRequest(request);
    }

    private String getValidPath(final String path) {
        final String validPath = path == null ? "/" : path;
        if (baseUrlPath != null) {
            if (startsWith(validPath, '/')) {
                return normalizeUrlPath(baseUrlPath + validPath);
            } else {
                return normalizeUrlPath(baseUrlPath + '/' + validPath);
            }
        } else {
            return normalizeUrlPath(path);
        }
    }

    private HttpRequest.Builder newRequestBuilder(final String path,
                                                  final List<Map.Entry<String, String>> headers,
                                                  final boolean withBody) {
        final HttpRequest.Builder requestBuilder;
        if (startsWith(path, '/')) {
            requestBuilder = HttpRequest.newBuilder().uri(URI.create(config.getConnectionString() + path));
        } else {
            requestBuilder = HttpRequest.newBuilder().uri(URI.create(config.getConnectionString() + '/' + path));
        }
        setHeaders(requestBuilder, headers, withBody);
        if (!config.getRequestTimeout().isZero()) {
            requestBuilder.timeout(config.getRequestTimeout());
        }
        requestBuilder.version(HttpClient.Version.HTTP_1_1);
        return requestBuilder;
    }

    @SuppressWarnings("ConstantConditions")
    private void setHeaders(final HttpRequest.Builder requestBuilder,
                            final List<Map.Entry<String, String>> headers,
                            final boolean withBody) {
        final Set<String> addedHeaders = headers.isEmpty() ? Set.of() : new TreeSet<>(CASE_INSENSITIVE_ORDER);
        headers.forEach(e -> {
            addedHeaders.add(e.getKey());
            requestBuilder.header(e.getKey(), e.getValue());
        });
        if (!addedHeaders.contains(acceptHeader.getKey())) {
            requestBuilder.header(acceptHeader.getKey(), acceptHeader.getValue());
        }
        if (withBody && !addedHeaders.contains(contentTypeHeader.getKey())) {
            requestBuilder.header(contentTypeHeader.getKey(), contentTypeHeader.getValue());
        }
        if (!addedHeaders.contains(userAgentHeader.getKey())) {
            requestBuilder.header(userAgentHeader.getKey(), userAgentHeader.getValue());
        }
        if (versionHeader != null && !addedHeaders.contains(versionHeader.getKey())) {
            requestBuilder.header(versionHeader.getKey(), versionHeader.getValue());
        }
    }

    private ClientHttpResponse sendRequest(final HttpRequest request) {
        return new JdkHttpResponse(
                client.sendAsync(request, responseInfo -> HttpResponse.BodySubscribers.ofByteArray()).join(),
                responseBodyConverter
        );
    }

    private void validateThatPathNotContainsQueryParams(final String path) {
        if (path.indexOf('?') != -1) {
            throw new IllegalArgumentException("Query params not supported here. Use HTTP body instead!");
        }
    }

    private <T> T nonNull(final T object,
                          final String name) {
        return require(object, format("'?' couldn't be null", name));
    }

    @Override
    public void release() {
        // do nothing
    }

    @Override
    public String toString() {
        return "JdkBlockingHttpClient{" +
                "config=" + config +
                ", client=" + client +
                ", requestBodyConverter=" + requestBodyConverter +
                ", responseBodyConverter=" + responseBodyConverter +
                ", acceptHeader=" + acceptHeader +
                ", contentTypeHeader=" + contentTypeHeader +
                ", userAgentHeader=" + userAgentHeader +
                ", versionHeader=" + versionHeader +
                ", baseUrlPath='" + baseUrlPath + '\'' +
                '}';
    }

    /**
     * @author nedis
     * @since 0.8
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private BlockingHttpClientConfig blockingHttpClientConfig;

        private HttpClientContentConverter contentConverter;

        private String versionValue;

        private Version.Strategy versionStrategy;

        private String baseUrlPath;

        private BaseUrlPath.Position baseUrlPosition;

        @BuilderMethod
        public Builder setBlockingHttpClientConfig(final BlockingHttpClientConfig blockingHttpClientConfig) {
            this.blockingHttpClientConfig = require(blockingHttpClientConfig);
            return this;
        }

        @BuilderMethod
        public Builder setContentConverter(final HttpClientContentConverter contentConverter) {
            this.contentConverter = require(contentConverter);
            return this;
        }

        @BuilderMethod
        public Builder setVersion(final Version.Strategy versionStrategy,
                                  final String versionValue) {
            this.versionStrategy = require(versionStrategy);
            if (versionStrategy == Version.Strategy.URL_PATH) {
                this.versionValue = normalizeUrlPath(versionValue);
            } else {
                this.versionValue = require(versionValue);
            }
            return this;
        }

        @BuilderMethod
        public Builder setBaseUrl(final String baseUrlPath,
                                  final BaseUrlPath.Position baseUrlPosition) {
            this.baseUrlPath = normalizeUrlPath(baseUrlPath);
            this.baseUrlPosition = require(baseUrlPosition);
            return this;
        }

        public JdkBlockingHttpClient build() {
            return new JdkBlockingHttpClient(
                    blockingHttpClientConfig, contentConverter, baseUrlPath, baseUrlPosition, versionValue, versionStrategy
            );
        }
    }
}
