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

package io.rxmicro.rest.server.netty.internal.component;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.AttributeKey;
import io.rxmicro.config.Secrets;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.logger.RequestIdSupplier;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.feature.RequestIdGenerator;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;
import io.rxmicro.rest.server.local.component.RequestHandler;
import io.rxmicro.rest.server.netty.NettyRestServerConfig;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpRequest;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpResponse;

import java.time.Duration;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.http.HttpStandardHeaderNames.CONNECTION;
import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static io.rxmicro.http.local.PredefinedUrls.HTTP_HEALTH_CHECK_ENDPOINT;
import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.1
 */
final class NettyRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyRequestHandler.class);

    private static final AttributeKey<String> REQUEST_ID_KEY = AttributeKey.valueOf(REQUEST_ID);

    private final Secrets secrets = Secrets.getDefaultInstance();

    private final NettyRestServerConfig nettyRestServerConfig;

    private final RequestHandler requestHandler;

    private final RequestIdGenerator requestIdGenerator;

    private final boolean returnGeneratedRequestId;

    private final boolean disableLoggerMessagesForHttpHealthChecks;

    private final NettyErrorHandler nettyErrorHandler;

    NettyRequestHandler(final NettyRestServerConfig nettyRestServerConfig,
                        final RequestHandler requestHandler,
                        final RequestIdGenerator requestIdGenerator,
                        final HttpResponseBuilder responseBuilder,
                        final HttpErrorResponseBodyBuilder responseContentBuilder,
                        final RestServerConfig restServerConfig) {
        this.nettyRestServerConfig = nettyRestServerConfig;
        this.requestHandler = requestHandler;
        this.requestIdGenerator = requestIdGenerator;
        this.returnGeneratedRequestId = restServerConfig.isReturnGeneratedRequestId();
        this.disableLoggerMessagesForHttpHealthChecks = restServerConfig.isDisableLoggerMessagesForHttpHealthChecks();
        this.nettyErrorHandler = new NettyErrorHandler(nettyRestServerConfig, responseBuilder, responseContentBuilder);
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx,
                                final FullHttpRequest msg) {
        final boolean keepAlive = isKeepAlive(msg);
        final long startTime = System.nanoTime();
        try {
            final NettyHttpRequest request = new NettyHttpRequest(
                    requestIdGenerator,
                    msg,
                    ctx.channel().remoteAddress()
            );
            ctx.channel().attr(REQUEST_ID_KEY).set(request.getRequestId());
            logRequest(request, ctx);
            requestHandler.handle(request)
                    .thenAccept(response -> writeResponse(ctx, request, response, startTime, keepAlive))
                    .exceptionally(th -> handleError(ctx, th, startTime, keepAlive));
        } catch (final Throwable th) {
            handleError(ctx, th, startTime, keepAlive);
        }
    }

    private boolean isKeepAlive(final FullHttpRequest msg) {
        final String value = msg.headers().getAsString(CONNECTION);
        return !"close".equalsIgnoreCase(value);
    }

    private void logRequest(final NettyHttpRequest request,
                            final ChannelHandlerContext ctx) {
        if (LOGGER.isTraceEnabled()) {
            if (disableLoggerMessagesForHttpHealthChecks && HTTP_HEALTH_CHECK_ENDPOINT.equals(request.getUri())) {
                return;
            }
            traceRequest(request, ctx);
        } else if (LOGGER.isDebugEnabled()) {
            if (disableLoggerMessagesForHttpHealthChecks && HTTP_HEALTH_CHECK_ENDPOINT.equals(request.getUri())) {
                return;
            }
            debugRequest(request, ctx);
        }
    }

    private void traceRequest(final NettyHttpRequest request,
                              final ChannelHandlerContext ctx) {
        LOGGER.trace(
                request,
                "HTTP request:  (Channel=?, IP=?):\n? ?\n?\n\n?",
                nettyRestServerConfig.getChannelIdType().getId(ctx.channel().id()),
                ctx.channel().remoteAddress(),
                format("? ??",
                        request.getMethod(),
                        request.getUri(),
                        request.isQueryStringPresent() ?
                                "" :
                                "?" + secrets.hideAllSecretsIn(request.getQueryString())
                ),
                request.getVersion().getText(),
                request.getHeaders().getEntries().stream()
                        .filter(e -> request.isRequestIdGenerated() && !REQUEST_ID.equals(e.getKey()))
                        .map(e -> format("?: ?", e.getKey(), secrets.hideIfSecret(e.getValue())))
                        .collect(joining(lineSeparator())),
                request.isContentPresent() ?
                        secrets.hideAllSecretsIn(new String(request.getContent(), UTF_8)) :
                        ""
        );
    }

    private void debugRequest(final NettyHttpRequest request,
                              final ChannelHandlerContext ctx) {
        LOGGER.debug(
                request,
                "HTTP request:  Channel=?, IP=?, Request=?",
                nettyRestServerConfig.getChannelIdType().getId(ctx.channel().id()),
                ctx.channel().remoteAddress(),
                format("? ??",
                        request.getMethod(),
                        request.getUri(),
                        request.isQueryStringPresent() ?
                                "" :
                                "?" + secrets.hideAllSecretsIn(request.getQueryString())
                )
        );
    }

    private void writeResponse(final ChannelHandlerContext ctx,
                               final NettyHttpRequest request,
                               final HttpResponse response,
                               final long startTime,
                               final boolean keepAlive) {
        final NettyHttpResponse httpResponse = (NettyHttpResponse) response;
        if (request.isRequestIdGenerated()) {
            if (returnGeneratedRequestId) {
                httpResponse.setHeader(REQUEST_ID, request.getRequestId());
            }
        } else {
            httpResponse.setHeader(REQUEST_ID, request.getRequestId());
        }
        if (!keepAlive) {
            httpResponse.setHeader(CONNECTION, "close");
        }
        writeAndFlush(ctx, request, request.getUri(), startTime, keepAlive, httpResponse);
    }

    private void writeAndFlush(final ChannelHandlerContext ctx,
                               final RequestIdSupplier requestIdSupplier,
                               final String requestUri,
                               final long startTime,
                               final boolean keepAlive,
                               final NettyHttpResponse httpResponse) {
        ctx.writeAndFlush(httpResponse.toFullHttpResponse())
                .addListener((ChannelFutureListener) future -> {
                    logResponse(requestIdSupplier, requestUri, startTime, httpResponse, ctx);
                    if (!keepAlive) {
                        future.channel().close();
                    }
                });
    }

    private void logResponse(final RequestIdSupplier requestIdSupplier,
                             final String requestUri,
                             final long startTime,
                             final NettyHttpResponse httpResponse,
                             final ChannelHandlerContext ctx) {
        if (LOGGER.isTraceEnabled()) {
            if (disableLoggerMessagesForHttpHealthChecks && HTTP_HEALTH_CHECK_ENDPOINT.equals(requestUri)) {
                return;
            }
            traceResponse(requestIdSupplier, startTime, httpResponse, ctx);
        } else if (LOGGER.isDebugEnabled()) {
            if (disableLoggerMessagesForHttpHealthChecks && HTTP_HEALTH_CHECK_ENDPOINT.equals(requestUri)) {
                return;
            }
            debugResponse(requestIdSupplier, startTime, httpResponse, ctx);
        }
    }

    private void traceResponse(final RequestIdSupplier requestIdSupplier,
                               final long startTime,
                               final NettyHttpResponse httpResponse,
                               final ChannelHandlerContext ctx) {
        LOGGER.trace(
                requestIdSupplier,
                "HTTP response: (Channel=?, Duration=?):\n? ?\n?\n\n?",
                nettyRestServerConfig.getChannelIdType().getId(ctx.channel().id()),
                startTime == 0L ? "undefined" : format(Duration.ofNanos(System.nanoTime() - startTime)),
                httpResponse.getHttpVersion(),
                httpResponse.getStatus(),
                httpResponse.getHeaders().getEntries().stream()
                        .map(e -> format("?: ?", e.getKey(), secrets.hideIfSecret(e.getValue())))
                        .collect(joining(lineSeparator())),
                httpResponse.getContentLength() > 0 ?
                        secrets.hideAllSecretsIn(new String(httpResponse.getContent(), UTF_8)) :
                        ""
        );
    }

    private void debugResponse(final RequestIdSupplier requestIdSupplier,
                               final long startTime,
                               final NettyHttpResponse httpResponse,
                               final ChannelHandlerContext ctx) {
        LOGGER.debug(
                requestIdSupplier,
                "HTTP response: Channel=?, Content=? bytes, Duration=?",
                nettyRestServerConfig.getChannelIdType().getId(ctx.channel().id()),
                httpResponse.getContentLength(),
                startTime == 0L ? "undefined" : format(Duration.ofNanos(System.nanoTime() - startTime))
        );
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx,
                                final Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        handleError(ctx, cause, 0L, false);
    }

    @SuppressWarnings("SameReturnValue")
    private Void handleError(final ChannelHandlerContext ctx,
                             final Throwable cause,
                             final long startTime,
                             final boolean keepAlive) {
        final String requestId = ctx.channel().attr(REQUEST_ID_KEY).get();
        final NettyHttpResponse errorResponse = nettyErrorHandler.build(requestId, ctx, cause);
        writeAndFlush(ctx, () -> requestId, "", startTime, keepAlive, errorResponse);
        return null;
    }
}
