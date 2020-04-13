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

import io.rxmicro.config.RxMicroSecrets;
import io.rxmicro.http.ProtocolSchema;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.http.client.HttpClient;
import io.rxmicro.http.client.HttpClientConfig;
import io.rxmicro.http.client.HttpClientContentConverter;
import io.rxmicro.http.client.HttpClientTimeoutException;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static io.rxmicro.common.Constants.RX_MICRO_FRAMEWORK_NAME;
import static io.rxmicro.common.util.Exceptions.isInstanceOf;
import static io.rxmicro.common.util.Exceptions.reThrow;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.http.HttpHeaders.ACCEPT;
import static io.rxmicro.http.HttpHeaders.REQUEST_ID;
import static io.rxmicro.http.HttpHeaders.USER_AGENT;
import static io.rxmicro.runtime.detail.Runtimes.getRxMicroVersion;
import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Map.entry;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
final class JdkHttpClient implements HttpClient {

    private final Logger logger;

    private final java.net.http.HttpClient client;

    private final ProtocolSchema protocol;

    private final String host;

    private final int port;

    private final RxMicroSecrets rxMicroSecrets = RxMicroSecrets.getInstance();

    private final Function<Object, byte[]> requestBodyConverter;

    private final Function<byte[], Object> responseBodyConverter;

    private final List<Map.Entry<String, String>> requiredHeaders;

    private final Duration timeout;

    JdkHttpClient(final Class<?> loggerClass,
                  final HttpClientConfig httpClientConfig,
                  final HttpClientContentConverter contentConverter) {
        this.logger = LoggerFactory.getLogger(loggerClass);
        this.protocol = httpClientConfig.getSchema();
        this.host = httpClientConfig.getHost();
        this.port = httpClientConfig.getPort();
        this.requiredHeaders = List.of(
                entry(ACCEPT, require(contentConverter.getContentType())),
                entry(USER_AGENT, format("?-JdkHttpClient/?", RX_MICRO_FRAMEWORK_NAME, getRxMicroVersion()))
        );
        this.requestBodyConverter = require(contentConverter.getRequestContentConverter());
        this.responseBodyConverter = require(contentConverter.getResponseContentConverter());
        this.client = java.net.http.HttpClient.newBuilder()
                .followRedirects(httpClientConfig.isFollowRedirects() ?
                        java.net.http.HttpClient.Redirect.ALWAYS :
                        java.net.http.HttpClient.Redirect.NEVER)
                .build();
        this.timeout = httpClientConfig.getRequestTimeout();
    }

    @Override
    public CompletableFuture<ClientHttpResponse> sendAsync(final String method,
                                                           final String path,
                                                           final List<Map.Entry<String, String>> headers) {
        final HttpRequest request = newRequestBuilder(path, headers)
                .method(method, BodyPublishers.noBody())
                .build();
        return sendAsync(request, null);
    }

    @Override
    public CompletableFuture<ClientHttpResponse> sendAsync(final String method,
                                                           final String path,
                                                           final List<Map.Entry<String, String>> headers,
                                                           final Object body) {
        final byte[] requestBody = requestBodyConverter.apply(body);
        final HttpRequest request = newRequestBuilder(path, headers)
                .method(method, BodyPublishers.ofByteArray(requestBody))
                .build();
        return sendAsync(request, requestBody);
    }

    private HttpRequest.Builder newRequestBuilder(final String path,
                                                  final List<Map.Entry<String, String>> headers) {
        final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(format("?://?:??",
                        protocol.getSchema(), host, port, path.startsWith("/") ? path : "/" + path)));
        setHeaders(requestBuilder, headers);
        if (!timeout.isZero()) {
            requestBuilder.timeout(timeout);
        }
        requestBuilder.version(java.net.http.HttpClient.Version.HTTP_1_1);
        return requestBuilder;
    }

    private void setHeaders(final HttpRequest.Builder requestBuilder,
                            final List<Map.Entry<String, String>> headers) {
        if (headers.isEmpty()) {
            requiredHeaders.forEach(e -> requestBuilder.header(e.getKey(), e.getValue()));
        } else {
            final Set<String> addedHeaders = new HashSet<>();
            headers.forEach(e -> {
                addedHeaders.add(e.getKey());
                requestBuilder.header(e.getKey(), e.getValue());
            });
            if (addedHeaders.isEmpty()) {
                requiredHeaders.forEach(e -> requestBuilder.header(e.getKey(), e.getValue()));
            } else {
                requiredHeaders.forEach(e -> {
                    if (!addedHeaders.contains(e.getKey())) {
                        requestBuilder.header(e.getKey(), e.getValue());
                    }
                });
            }
        }
    }

    private CompletableFuture<ClientHttpResponse> sendAsync(final HttpRequest request,
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
                                    request.uri());
                        } else {
                            reThrow(throwable);
                        }
                    }
                    return resp;
                })
                .thenApply(resp -> new JdkClientHttpResponse(resp, responseBodyConverter));
    }

    private CompletableFuture<HttpResponse<byte[]>> trace(final HttpRequest request,
                                                          final byte[] requestBody,
                                                          final CompletableFuture<HttpResponse<byte[]>> response) {
        final String requestId = request.headers().firstValue(REQUEST_ID).orElse(null);
        final long startTime = System.nanoTime();
        logger.trace("HTTP request sent?:\n? ?\n?\n\n?",
                requestId != null ? format(" (Id=?)", requestId) : "",
                format("? ?", request.method(), rxMicroSecrets.replaceAllSecretsIfFound(request.uri().toString())),
                request.version().map(Enum::toString).orElse(""),
                request.headers().map().entrySet().stream()
                        .flatMap(e -> e.getValue().stream().map(v -> entry(e.getKey(), v)))
                        .map(e -> format("?: ?", e.getKey(), rxMicroSecrets.hideIfSecret(e.getValue())))
                        .collect(joining(lineSeparator())),
                requestBody != null ?
                        rxMicroSecrets.replaceAllSecretsIfFound(new String(requestBody, UTF_8)) :
                        ""
        );
        return response.whenComplete((resp, th) -> {
            if (resp != null) {
                logger.trace("HTTP response received (?Duration=?):\n? ?\n?\n\n?",
                        requestId != null ? format("Id=?, ", requestId) : "",
                        format(Duration.ofNanos(System.nanoTime() - startTime)),
                        resp.version(),
                        resp.statusCode(),
                        resp.headers().map().entrySet().stream()
                                .flatMap(e -> e.getValue().stream().map(v -> entry(e.getKey(), v)))
                                .map(e -> format("?: ?", e.getKey(), rxMicroSecrets.hideIfSecret(e.getValue())))
                                .collect(joining(lineSeparator())),
                        resp.body().length > 0 ?
                                rxMicroSecrets.replaceAllSecretsIfFound(new String(resp.body(), UTF_8)) :
                                ""
                );
            }
        });
    }

    private CompletableFuture<HttpResponse<byte[]>> debug(final HttpRequest request,
                                                          final CompletableFuture<HttpResponse<byte[]>> response) {
        final String requestId = request.headers().firstValue(REQUEST_ID).orElse(null);
        final String uri = request.uri().toString();
        final int index = uri.indexOf('?');
        final long startTime = System.nanoTime();
        logger.debug("HTTP request sent?: '?'",
                requestId != null ? format(" (Id=?)", requestId) : "",
                format("? ?", request.method(),
                        index != -1 ? uri.substring(0, index) : uri));
        return response.whenComplete((resp, th) -> {
            if (resp != null) {
                logger.debug("HTTP response received (?Duration=?): '?/?', Content=? bytes",
                        requestId != null ? format("Id=?, ", requestId) : "",
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
