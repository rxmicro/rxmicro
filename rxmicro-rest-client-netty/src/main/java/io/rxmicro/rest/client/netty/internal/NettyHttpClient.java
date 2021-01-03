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

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.timeout.ReadTimeoutException;
import io.rxmicro.config.Secrets;
import io.rxmicro.http.ProtocolSchema;
import io.rxmicro.rest.client.HttpClientTimeoutException;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.HttpClientContentConverter;
import io.rxmicro.rest.client.detail.HttpResponse;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClient.RequestSender;
import reactor.netty.http.client.HttpClientResponse;
import reactor.netty.resources.ConnectionProvider;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;

import static io.netty.buffer.Unpooled.wrappedBuffer;
import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
import static io.netty.handler.codec.http.HttpHeaderValues.CLOSE;
import static io.rxmicro.common.CommonConstants.RX_MICRO_FRAMEWORK_NAME;
import static io.rxmicro.common.util.ExCollectors.toTreeSet;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.http.HttpStandardHeaderNames.ACCEPT;
import static io.rxmicro.http.HttpStandardHeaderNames.CONNECTION;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_LENGTH;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_TYPE;
import static io.rxmicro.http.HttpStandardHeaderNames.HOST;
import static io.rxmicro.http.HttpStandardHeaderNames.USER_AGENT;
import static io.rxmicro.netty.runtime.local.EventLoopGroupFactory.getEventLoopGroupFactory;
import static io.rxmicro.runtime.detail.RxMicroRuntime.getRxMicroVersion;
import static java.lang.String.CASE_INSENSITIVE_ORDER;

/**
 * @author nedis
 * @since 0.8
 */
final class NettyHttpClient implements io.rxmicro.rest.client.detail.HttpClient {

    private static final String DEFAULT_WORKER_THREAD_QUALIFIER = "http-client";

    private static final Set<String> RESTRICTED_HEADER_NAMES =
            List.of(
                    CONNECTION, CONTENT_LENGTH, HOST
            ).stream().collect(toTreeSet(CASE_INSENSITIVE_ORDER));

    private final NettyHttpClientLogger logger;

    private final RestClientConfig config;

    private final HttpClient client;

    private final String contentType;

    private final String userAgent;

    private final Function<Object, byte[]> requestBodyConverter;

    private final Function<byte[], Object> responseBodyConverter;

    NettyHttpClient(final Class<?> loggerClass,
                    final RestClientConfig config,
                    final Secrets secrets,
                    final HttpClientContentConverter contentConverter) {
        this.logger = new NettyHttpClientLogger(loggerClass, secrets);
        this.config = config;
        this.client = buildImmutableClient(config);
        this.contentType = require(contentConverter.getContentType());
        this.userAgent = format("?-netty-http-client/?", RX_MICRO_FRAMEWORK_NAME, getRxMicroVersion());
        this.requestBodyConverter = require(contentConverter.getRequestContentConverter());
        this.responseBodyConverter = require(contentConverter.getResponseContentConverter());
    }

    private HttpClient buildImmutableClient(final RestClientConfig config) {
        final ConnectionProvider connectionProvider = ConnectionProvider.builder("rx-micro-netty-http-pool")
                // Add pool config here
                .build();
        HttpClient client = HttpClient.create(connectionProvider)
                .host(config.getHost())
                .port(config.getPort())
                .followRedirect(config.isFollowRedirects())
                .protocol(HttpProtocol.HTTP11)
                .runOn(getEventLoopGroupFactory().getRequiredWorkerEventLoopGroup(DEFAULT_WORKER_THREAD_QUALIFIER));
        if (config.getSchema() == ProtocolSchema.HTTPS) {
            final SslContextBuilder sslContextBuilder = SslContextBuilder.forClient();
            client = client.secure(spec -> spec.sslContext(sslContextBuilder));
        }
        if (!config.getConnectTimeout().isZero()) {
            client = client.option(CONNECT_TIMEOUT_MILLIS, (int) config.getConnectTimeout().toMillis());
        }
        if (!config.getRequestTimeout().isZero()) {
            client = client.responseTimeout(config.getRequestTimeout());
        }
        return client;
    }

    @Override
    public CompletableFuture<HttpResponse> sendAsync(final String method,
                                                     final String path,
                                                     final List<Map.Entry<String, String>> headers) {
        final RequestSender sender = createRequestSender(method, path, headers, null);
        final long startTime = System.nanoTime();
        return sender.responseSingle((BiFunction<HttpClientResponse, ByteBufMono, Mono<HttpResponse>>) (response, byteBufMono) ->
                byteBufMono.asByteArray().map(responseBodyBytes -> buildNettyHttpResponse(startTime, response, responseBodyBytes)))
                .onErrorMap(createTimeoutHandler(path))
                .toFuture();
    }


    @Override
    public CompletableFuture<HttpResponse> sendAsync(final String method,
                                                     final String path,
                                                     final List<Map.Entry<String, String>> headers,
                                                     final Object body) {
        final byte[] requestBody = requestBodyConverter.apply(body);
        final RequestSender sender = createRequestSender(method, path, headers, requestBody);
        final long startTime = System.nanoTime();
        return sender
                .send(Mono.just(wrappedBuffer(requestBody)))
                .responseSingle((BiFunction<HttpClientResponse, ByteBufMono, Mono<HttpResponse>>) (response, byteBufMono) ->
                        byteBufMono.asByteArray().map(responseBodyBytes -> buildNettyHttpResponse(startTime, response, responseBodyBytes))
                )
                .onErrorMap(createTimeoutHandler(path))
                .toFuture();
    }

    private RequestSender createRequestSender(final String method,
                                              final String path,
                                              final List<Map.Entry<String, String>> headers,
                                              final byte[] requestBody) {
        final HttpClient clientWithHeaders = client.headers(nettyHeaders -> setHeaders(nettyHeaders, headers, requestBody));
        final RequestSender sender = clientWithHeaders
                .request(HttpMethod.valueOf(method))
                .uri(path);
        if (logger.isTraceEnabled()) {
            logger.trace(method, path, clientWithHeaders.configuration().headers(), requestBody);
        } else if (logger.isDebugEnabled()) {
            logger.debug(method, path, clientWithHeaders.configuration().headers(), requestBody);
        }
        return sender;
    }

    private void setHeaders(final HttpHeaders nettyHeaders,
                            final List<Map.Entry<String, String>> headers,
                            final byte[] requestBody) {
        nettyHeaders.set(HOST, config.getHost());
        nettyHeaders.set(CONNECTION, CLOSE);
        if (!headers.isEmpty()) {
            final Set<String> addedHeaders = new TreeSet<>(CASE_INSENSITIVE_ORDER);
            headers.forEach(e -> {
                if (RESTRICTED_HEADER_NAMES.contains(e.getKey())) {
                    throw new IllegalArgumentException(
                            format("Restricted header name: '?'! Remove this header!", e.getKey())
                    );
                }
                addedHeaders.add(e.getKey());
                nettyHeaders.set(e.getKey(), e.getValue());
            });
            if (!addedHeaders.contains(ACCEPT)) {
                nettyHeaders.set(ACCEPT, contentType);
            }
            if (requestBody != null) {
                if (!addedHeaders.contains(CONTENT_TYPE)) {
                    nettyHeaders.set(CONTENT_TYPE, contentType);
                }
                nettyHeaders.set(CONTENT_LENGTH, requestBody.length);
            } else {
                nettyHeaders.set(CONTENT_LENGTH, 0);
            }
            if (!addedHeaders.contains(USER_AGENT)) {
                nettyHeaders.set(USER_AGENT, userAgent);
            }
        } else {
            nettyHeaders.set(ACCEPT, contentType);
            if (requestBody != null) {
                nettyHeaders.set(CONTENT_TYPE, contentType);
                nettyHeaders.set(CONTENT_LENGTH, requestBody.length);
            } else {
                nettyHeaders.set(CONTENT_LENGTH, 0);
            }
            nettyHeaders.set(USER_AGENT, userAgent);
        }
    }

    private NettyHttpResponse buildNettyHttpResponse(final long startTime,
                                                     final HttpClientResponse response,
                                                     final byte[] responseBodyBytes) {
        if (logger.isTraceEnabled()) {
            logger.trace(startTime, response, responseBodyBytes);
        } else if (logger.isDebugEnabled()) {
            logger.debug(startTime, response, responseBodyBytes);
        }
        return new NettyHttpResponse(response, responseBodyBytes, responseBodyConverter);
    }

    private Function<Throwable, Throwable> createTimeoutHandler(final String path) {
        return throwable -> {
            if (throwable instanceof ReadTimeoutException) {
                throw new HttpClientTimeoutException("HTTP connect timed out to ?", path
                );
            } else {
                return throwable;
            }
        };
    }

    @Override
    public void release() {
        // do nothing
    }
}
