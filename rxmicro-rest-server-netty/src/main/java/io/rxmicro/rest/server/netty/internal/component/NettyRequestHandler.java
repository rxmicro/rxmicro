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

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.AttributeKey;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;
import io.rxmicro.rest.server.local.component.RequestHandler;
import io.rxmicro.rest.server.local.component.RequestIdGenerator;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpRequest;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpResponse;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.http.HttpHeaders.REQUEST_ID;
import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
final class NettyRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyRequestHandler.class);

    private static final AttributeKey<String> REQUEST_ID_KEY = AttributeKey.valueOf(REQUEST_ID);

    private final RequestHandler requestHandler;

    private final RequestIdGenerator requestIdGenerator;

    private final HttpResponseBuilder responseBuilder;

    private final HttpErrorResponseBodyBuilder responseContentBuilder;

    private final boolean returnGeneratedRequestId;

    NettyRequestHandler(final RequestHandler requestHandler,
                        final RequestIdGenerator requestIdGenerator,
                        final HttpResponseBuilder responseBuilder,
                        final HttpErrorResponseBodyBuilder responseContentBuilder,
                        final boolean returnGeneratedRequestId) {
        this.requestHandler = requestHandler;
        this.requestIdGenerator = requestIdGenerator;
        this.responseBuilder = responseBuilder;
        this.responseContentBuilder = responseContentBuilder;
        this.returnGeneratedRequestId = returnGeneratedRequestId;
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx,
                                final FullHttpRequest msg) {
        try {
            final long startTime = System.nanoTime();
            final NettyHttpRequest request = new NettyHttpRequest(
                    requestIdGenerator,
                    msg,
                    ctx.channel().remoteAddress()
            );
            ctx.channel().attr(REQUEST_ID_KEY).set(request.getRequestId());
            logRequest(request);
            requestHandler.handle(request)
                    .thenAccept(response -> writeResponse(ctx, request, response, startTime))
                    .exceptionally(th -> handleError(ctx, th));
        } catch (final Throwable th) {
            handleError(ctx, th);
        }
    }

    private void logRequest(final HttpRequest request) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("HTTP request received (Id=?):\n? ?\n?\n\n?",
                    request.getRequestId(),
                    format("? ??",
                            request.getMethod(),
                            request.getUri(),
                            request.isQueryStringPresent() ? "" : "?" + request.getQueryString()
                    ),
                    request.getVersion().getText(),
                    request.getHeaders().getEntries().stream()
                            .map(e -> format("?: ?", e.getKey(), String.join(", ", e.getValue())))
                            .collect(joining(lineSeparator())),
                    request.contentExists() ? new String(request.getContent(), UTF_8) : ""
            );
        } else if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("HTTP request received: Id=?, Request=?",
                    request.getRequestId(),
                    format("? ??",
                            request.getMethod(),
                            request.getUri(),
                            request.isQueryStringPresent() ? "" : "?" + request.getQueryString()
                    )
            );
        }
    }

    private void writeResponse(final ChannelHandlerContext ctx,
                               final NettyHttpRequest request,
                               final HttpResponse response,
                               final long startTime) {
        final NettyHttpResponse httpResponse = ((NettyHttpResponse) response);
        if (request.isRequestIdGenerated()) {
            if (returnGeneratedRequestId) {
                httpResponse.setHeader(REQUEST_ID, request.getRequestId());
            }
        } else {
            httpResponse.setHeader(REQUEST_ID, request.getRequestId());
        }
        ctx.writeAndFlush(httpResponse.toFullHttpResponse());
        logResponse(request, startTime, httpResponse);

    }

    private void logResponse(final HttpRequest request,
                             final long startTime,
                             final NettyHttpResponse httpResponse) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("HTTP response sent (Id=?, Duration=? millis):\n? ?\n?\n\n?",
                    request.getRequestId(),
                    (System.nanoTime() - startTime) / 1_000_000,
                    httpResponse.getHttpVersion(),
                    httpResponse.getStatus(),
                    httpResponse.getHeaders().getEntries().stream()
                            .map(e -> format("?: ?", e.getKey(), String.join(", ", e.getValue())))
                            .collect(joining(lineSeparator())),
                    httpResponse.getContentLength() > 0 ? new String(httpResponse.getContent(), UTF_8) : "");
        } else if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("HTTP response sent: Id=?, Content=? bytes, Duration=? millis",
                    request.getRequestId(),
                    httpResponse.getContentLength(),
                    (System.nanoTime() - startTime) / 1_000_000);
        }
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx,
                                final Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        handleError(ctx, cause);
    }

    @SuppressWarnings("SameReturnValue")
    private Void handleError(final ChannelHandlerContext ctx,
                             final Throwable cause) {
        final String requestId = ctx.channel().attr(REQUEST_ID_KEY).get();
        LOGGER.error(cause, "Error: message=?, ?=?", cause.getMessage(), REQUEST_ID, requestId);
        final NettyHttpResponse errorResponse = (NettyHttpResponse) responseContentBuilder.build(
                responseBuilder.build(),
                HttpResponseStatus.INTERNAL_SERVER_ERROR.code(),
                "Internal error");
        ctx.writeAndFlush(errorResponse.toFullHttpResponse());
        return null;
    }
}
