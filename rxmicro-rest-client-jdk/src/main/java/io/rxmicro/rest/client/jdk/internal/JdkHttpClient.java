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
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.rest.client.HttpClientTimeoutException;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.HttpClient;
import io.rxmicro.rest.client.detail.HttpClientContentConverter;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
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
import static io.rxmicro.common.util.Strings.startsWith;
import static io.rxmicro.http.HttpStandardHeaderNames.ACCEPT;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_TYPE;
import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static io.rxmicro.http.HttpStandardHeaderNames.USER_AGENT;
import static io.rxmicro.logger.RequestIdSupplier.UNDEFINED_REQUEST_ID;
import static io.rxmicro.runtime.detail.RxMicroRuntime.getRxMicroVersion;
import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Map.entry;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.8
 */
final class JdkHttpClient implements HttpClient {

    private final Logger logger;

    private final java.net.http.HttpClient client;

    private final String connectionString;

    private final Secrets secrets;

    private final Function<Object, byte[]> requestBodyConverter;

    private final Function<byte[], Object> responseBodyConverter;

    private final Map.Entry<String, String> acceptHeader;

    private final Map.Entry<String, String> contentTypeHeader;

    private final Map.Entry<String, String> userAgentHeader;

    private final Duration timeout;

    JdkHttpClient(final Class<?> loggerClass,
                  final RestClientConfig restClientConfig,
                  final Secrets secrets,
                  final HttpClientContentConverter contentConverter) {
        this.logger = LoggerFactory.getLogger(loggerClass);
        this.connectionString = restClientConfig.getConnectionString();
        this.secrets = secrets;

        final String contentType = require(contentConverter.getContentType());
        this.acceptHeader = entry(ACCEPT, contentType);
        this.contentTypeHeader = entry(CONTENT_TYPE, contentType);
        this.userAgentHeader = entry(USER_AGENT, format("?-jdk-http-client/?", RX_MICRO_FRAMEWORK_NAME, getRxMicroVersion()));

        this.requestBodyConverter = require(contentConverter.getRequestContentConverter());
        this.responseBodyConverter = require(contentConverter.getResponseContentConverter());
        this.client = java.net.http.HttpClient.newBuilder()
                .followRedirects(restClientConfig.isFollowRedirects() ?
                        java.net.http.HttpClient.Redirect.ALWAYS :
                        java.net.http.HttpClient.Redirect.NEVER)
                .build();
        this.timeout = restClientConfig.getRequestTimeout();
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
        final HttpRequest.Builder requestBuilder;
        if (startsWith(path, '/')) {
            requestBuilder = HttpRequest.newBuilder().uri(URI.create(connectionString + path));
        } else {
            requestBuilder = HttpRequest.newBuilder().uri(URI.create(connectionString + '/' + path));
        }
        setHeaders(requestBuilder, headers, withBody);
        if (!timeout.isZero()) {
            requestBuilder.timeout(timeout);
        }
        requestBuilder.version(java.net.http.HttpClient.Version.HTTP_1_1);
        return requestBuilder;
    }

    private void setHeaders(final HttpRequest.Builder requestBuilder,
                            final List<Map.Entry<String, String>> headers,
                            final boolean withBody) {
        final Set<String> addedHeaders = headers.isEmpty() ? Set.of() : new TreeSet<>(CASE_INSENSITIVE_ORDER);
        if (!headers.isEmpty()) {
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
        } else {
            requestBuilder.header(acceptHeader.getKey(), acceptHeader.getValue());
            if (withBody) {
                requestBuilder.header(contentTypeHeader.getKey(), contentTypeHeader.getValue());
            }
            requestBuilder.header(userAgentHeader.getKey(), userAgentHeader.getValue());
        }
    }

    private CompletableFuture<io.rxmicro.rest.client.detail.HttpResponse> send(final HttpRequest request,
                                                                               final byte[] requestBody) {
        CompletableFuture<HttpResponse<byte[]>> response = client.sendAsync(request, ofByteArray());
        if (logger.isTraceEnabled()) {
            response = trace(request, requestBody, response);
        } else if (logger.isDebugEnabled()) {
            response = debug(request, response);
        }
        return response
                .handle((resp, throwable) -> {
                    if (throwable != null) {
                        if (isInstanceOf(throwable, HttpTimeoutException.class)) {
                            throw new HttpClientTimeoutException(
                                    "HTTP connect timed out to ?",
                                    request.uri()
                            );
                        } else {
                            reThrow(throwable);
                        }
                    }
                    return resp;
                })
                .thenApply(resp -> new JdkHttpResponse(resp, responseBodyConverter));
    }

    private CompletableFuture<HttpResponse<byte[]>> trace(final HttpRequest request,
                                                          final byte[] requestBody,
                                                          final CompletableFuture<HttpResponse<byte[]>> response) {
        final String requestId = request.headers().firstValue(REQUEST_ID).orElse(UNDEFINED_REQUEST_ID);
        final long startTime = System.nanoTime();
        logger.trace(
                () -> requestId,
                "HTTP request sent:\n? ?\n?\n\n?",
                format("? ?", request.method(), secrets.hideAllSecretsIn(request.uri().toString())),
                request.version().map(Enum::toString).orElse(""),
                request.headers().map().entrySet().stream()
                        .flatMap(e -> e.getValue().stream().map(v -> entry(e.getKey(), v)))
                        .map(e -> format("?: ?", e.getKey(), secrets.hideIfSecret(e.getValue())))
                        .collect(joining(lineSeparator())),
                requestBody != null ?
                        secrets.hideAllSecretsIn(new String(requestBody, UTF_8)) :
                        ""
        );
        return response.whenComplete((resp, th) -> {
            if (resp != null) {
                logger.trace(
                        () -> requestId,
                        "HTTP response received (Duration=?):\n? ?\n?\n\n?",
                        format(Duration.ofNanos(System.nanoTime() - startTime)),
                        resp.version(),
                        resp.statusCode(),
                        resp.headers().map().entrySet().stream()
                                .flatMap(e -> e.getValue().stream().map(v -> entry(e.getKey(), v)))
                                .map(e -> format("?: ?", e.getKey(), secrets.hideIfSecret(e.getValue())))
                                .collect(joining(lineSeparator())),
                        resp.body().length > 0 ?
                                secrets.hideAllSecretsIn(new String(resp.body(), UTF_8)) :
                                ""
                );
            }
        });
    }

    private CompletableFuture<HttpResponse<byte[]>> debug(final HttpRequest request,
                                                          final CompletableFuture<HttpResponse<byte[]>> response) {
        final String requestId = request.headers().firstValue(REQUEST_ID).orElse(UNDEFINED_REQUEST_ID);
        final String uri = request.uri().toString();
        final int index = uri.indexOf('?');
        final long startTime = System.nanoTime();
        logger.debug(
                () -> requestId,
                "HTTP request sent: '?'",
                format("? ?", request.method(),
                        index != -1 ? uri.substring(0, index) : uri));
        return response.whenComplete((resp, th) -> {
            if (resp != null) {
                logger.debug(
                        () -> requestId,
                        "HTTP response received (Duration=?): '?/?', Content=? bytes",
                        format(Duration.ofNanos(System.nanoTime() - startTime)),
                        resp.statusCode(),
                        resp.version(),
                        resp.body().length
                );
            }
        });
    }

    private HttpResponse.BodyHandler<byte[]> ofByteArray() {
        return responseInfo -> HttpResponse.BodySubscribers.ofByteArray();
    }

    @Override
    public void release() {
        logger.info("? released", getClass().getSimpleName());
    }
}
