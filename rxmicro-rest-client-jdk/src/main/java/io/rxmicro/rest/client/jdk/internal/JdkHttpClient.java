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

package io.rxmicro.rest.client.jdk.internal;

import io.rxmicro.config.Secrets;
import io.rxmicro.rest.client.HttpClientTimeoutException;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.HttpClientContentConverter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static io.rxmicro.common.CommonConstants.RX_MICRO_FRAMEWORK_NAME;
import static io.rxmicro.common.util.Exceptions.isInstanceOf;
import static io.rxmicro.common.util.Exceptions.reThrow;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.http.HttpStandardHeaderNames.ACCEPT;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_TYPE;
import static io.rxmicro.http.HttpStandardHeaderNames.USER_AGENT;
import static io.rxmicro.runtime.detail.RxMicroRuntime.getRxMicroVersion;
import static java.lang.String.CASE_INSENSITIVE_ORDER;

/**
 * @author nedis
 * @since 0.8
 */
final class JdkHttpClient implements io.rxmicro.rest.client.detail.HttpClient {

    static final String DEFAULT_USER_AGENT = format("?-jdk-http-client/?", RX_MICRO_FRAMEWORK_NAME, getRxMicroVersion());

    private final JdkHttpClientLogger logger;

    private final RestClientConfig config;

    private final HttpClient client;

    private final String connectionString;

    private final String contentType;

    private final Function<Object, byte[]> requestBodyConverter;

    private final Function<byte[], Object> responseBodyConverter;


    JdkHttpClient(final Class<?> loggerClass,
                  final RestClientConfig config,
                  final Secrets secrets,
                  final HttpClientContentConverter contentConverter) {
        this.config = config;
        this.logger = new JdkHttpClientLogger(loggerClass, secrets);
        this.connectionString = config.getConnectionString();
        this.contentType = require(contentConverter.getContentType());
        this.requestBodyConverter = require(contentConverter.getRequestContentConverter());
        this.responseBodyConverter = require(contentConverter.getResponseContentConverter());
        final HttpClient.Builder builder = HttpClient.newBuilder()
                .followRedirects(config.isFollowRedirects() ?
                        HttpClient.Redirect.ALWAYS :
                        HttpClient.Redirect.NEVER);
        if (!config.getConnectTimeout().isZero()) {
            builder.connectTimeout(config.getConnectTimeout());
        }
        this.client = builder.build();
    }

    @Override
    public CompletableFuture<io.rxmicro.rest.client.detail.HttpResponse> sendAsync(final String method,
                                                                                   final String path,
                                                                                   final List<Map.Entry<String, String>> headers) {
        final HttpRequest request = newRequestBuilder(path, headers, false)
                .method(method, BodyPublishers.noBody())
                .build();
        return send(request, null);
    }

    @Override
    public CompletableFuture<io.rxmicro.rest.client.detail.HttpResponse> sendAsync(final String method,
                                                                                   final String path,
                                                                                   final List<Map.Entry<String, String>> headers,
                                                                                   final Object body) {
        final byte[] requestBody = requestBodyConverter.apply(body);
        final HttpRequest request = newRequestBuilder(path, headers, true)
                .method(method, BodyPublishers.ofByteArray(requestBody))
                .build();
        return send(request, requestBody);
    }

    private HttpRequest.Builder newRequestBuilder(final String path,
                                                  final List<Map.Entry<String, String>> headers,
                                                  final boolean withBody) {
        final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(connectionString + path))
                .version(HttpClient.Version.HTTP_1_1);
        setHeaders(requestBuilder, headers, withBody);
        if (!config.getRequestTimeout().isZero()) {
            requestBuilder.timeout(config.getRequestTimeout());
        }
        return requestBuilder;
    }

    private void setHeaders(final HttpRequest.Builder requestBuilder,
                            final List<Map.Entry<String, String>> headers,
                            final boolean withBody) {
        if (headers.isEmpty()) {
            requestBuilder.header(ACCEPT, contentType);
            if (withBody) {
                requestBuilder.header(CONTENT_TYPE, contentType);
            }
            requestBuilder.header(USER_AGENT, DEFAULT_USER_AGENT);
        } else {
            final Set<String> addedHeaders = new TreeSet<>(CASE_INSENSITIVE_ORDER);
            headers.forEach(e -> {
                addedHeaders.add(e.getKey());
                requestBuilder.header(e.getKey(), e.getValue());
            });
            if (!addedHeaders.contains(ACCEPT)) {
                requestBuilder.header(ACCEPT, contentType);
            }
            if (withBody && !addedHeaders.contains(CONTENT_TYPE)) {
                requestBuilder.header(CONTENT_TYPE, contentType);
            }
            if (!addedHeaders.contains(USER_AGENT)) {
                requestBuilder.header(USER_AGENT, DEFAULT_USER_AGENT);
            }
        }
    }

    private CompletableFuture<io.rxmicro.rest.client.detail.HttpResponse> send(final HttpRequest request,
                                                                               final byte[] requestBody) {
        CompletableFuture<HttpResponse<byte[]>> response = client.sendAsync(request, ofByteArray());
        if (logger.isTraceEnabled()) {
            response = logger.trace(request, requestBody, response);
        } else if (logger.isDebugEnabled()) {
            response = logger.debug(request, requestBody, response);
        }
        return response
                .handle((resp, throwable) -> {
                    if (throwable != null) {
                        if (isInstanceOf(throwable, HttpTimeoutException.class)) {
                            throw new HttpClientTimeoutException("HTTP connect timed out to ?", request.uri());
                        } else {
                            reThrow(throwable);
                        }
                    }
                    return resp;
                })
                .thenApply(resp -> new JdkHttpResponse(resp, responseBodyConverter));
    }

    private HttpResponse.BodyHandler<byte[]> ofByteArray() {
        return responseInfo -> HttpResponse.BodySubscribers.ofByteArray();
    }

    @Override
    public void release() {
        // do nothing
    }

    @Override
    public String toString() {
        return "JdkHttpClient{" +
                "config=" + config +
                ", client=" + client +
                ", connectionString='" + connectionString + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
