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

package io.rxmicro.rest.server.netty.internal.component.writer;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.rxmicro.config.Secrets;
import io.rxmicro.logger.Logger;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.netty.NettyRestServerConfig;
import io.rxmicro.rest.server.netty.internal.component.NettyErrorHandler;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpRequest;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpResponse;

import java.time.Duration;

import static io.netty.handler.codec.http.HttpHeaderValues.CLOSE;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.http.HttpStandardHeaderNames.CONNECTION;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_LENGTH;
import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static io.rxmicro.http.HttpVersion.HTTP_1_0;
import static io.rxmicro.http.local.PredefinedUrls.HTTP_HEALTH_CHECK_ENDPOINT;
import static io.rxmicro.rest.server.netty.internal.model.NettyHttpRequest.REQUEST_ID_KEY;
import static io.rxmicro.rest.server.netty.internal.model.NettyHttpRequest.START_PROCESSING_REQUEST_TIME_KEY;
import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.8
 */
class BaseNettyResponseWriter {

    final Logger logger;

    final Secrets secrets;

    final NettyErrorHandler nettyErrorHandler;

    final NettyRestServerConfig nettyRestServerConfig;

    final RestServerConfig restServerConfig;

    BaseNettyResponseWriter(final Logger logger,
                            final NettyErrorHandler nettyErrorHandler) {
        this.logger = logger;
        this.secrets = Secrets.getDefaultInstance();
        this.nettyErrorHandler = nettyErrorHandler;
        this.nettyRestServerConfig = getConfig(NettyRestServerConfig.class);
        this.restServerConfig = getConfig(RestServerConfig.class);
    }

    final boolean isKeepAlive(final NettyHttpRequest httpRequest) {
        final String value = httpRequest.getFullHttpRequest().headers().getAsString(CONNECTION);
        return !"close".equalsIgnoreCase(value);
    }

    final void setCommonHeaders(final NettyHttpRequest request,
                                final NettyHttpResponse response,
                                final boolean keepAlive) {
        if (request.isRequestIdGenerated()) {
            if (restServerConfig.isReturnGeneratedRequestId()) {
                response.setHeader(REQUEST_ID, request.getRequestId());
            }
        } else {
            response.setHeader(REQUEST_ID, request.getRequestId());
        }
        if (keepAlive) {
            if (request.getVersion() == HTTP_1_0) {
                response.setHeader(CONNECTION, KEEP_ALIVE);
            }
        } else {
            response.setHeader(CONNECTION, CLOSE);
        }
    }

    final void afterResponseWritten(final ChannelHandlerContext ctx,
                                    final ChannelFuture future,
                                    final NettyHttpRequest request,
                                    final NettyHttpResponse response,
                                    final boolean keepAlive) {
        if (future.isSuccess()) {
            logResponse(ctx, request, response);
            if (!keepAlive) {
                future.channel().close(ctx.voidPromise());
            }
        } else {
            final String requestId = ctx.channel().attr(REQUEST_ID_KEY).get();
            nettyErrorHandler.logInternalError(ctx, requestId, future.cause());
            future.channel().close(ctx.voidPromise());
        }
    }

    private void logResponse(final ChannelHandlerContext ctx,
                             final NettyHttpRequest request,
                             final NettyHttpResponse response) {
        if (logger.isTraceEnabled()) {
            if (restServerConfig.isDisableLoggerMessagesForHttpHealthChecks() && HTTP_HEALTH_CHECK_ENDPOINT.equals(request.getUri())) {
                return;
            }
            traceResponse(ctx, request, response);
        } else if (logger.isDebugEnabled()) {
            if (restServerConfig.isDisableLoggerMessagesForHttpHealthChecks() && HTTP_HEALTH_CHECK_ENDPOINT.equals(request.getUri())) {
                return;
            }
            debugResponse(ctx, request, response);
        }
    }

    private void traceResponse(final ChannelHandlerContext ctx,
                               final NettyHttpRequest request,
                               final NettyHttpResponse response) {
        final Long startTime = ctx.channel().attr(START_PROCESSING_REQUEST_TIME_KEY).get();
        logger.trace(
                request,
                "HTTP response: (Channel=?, Duration=?):\n? ?\n?\n\n?",
                nettyRestServerConfig.getChannelIdType().getId(ctx.channel().id()),
                startTime == null ? "undefined" : format(Duration.ofNanos(System.nanoTime() - startTime)),
                response.getHttpVersion(),
                response.getStatus(),
                response.getHeaders().getEntries().stream()
                        .map(e -> format("?: ?", e.getKey(), secrets.hideIfSecret(e.getValue())))
                        .collect(joining(lineSeparator())),
                response.isSendFileResponse() ? "<file content>" :
                        response.getContentLength() > 0 ?
                                secrets.hideAllSecretsIn(new String(response.getContent(), UTF_8)) :
                                ""
        );
    }

    private void debugResponse(final ChannelHandlerContext ctx,
                               final NettyHttpRequest request,
                               final NettyHttpResponse response) {
        final Long startTime = ctx.channel().attr(START_PROCESSING_REQUEST_TIME_KEY).get();
        logger.debug(
                request,
                "HTTP response: Channel=?, Content=? bytes, Duration=?",
                nettyRestServerConfig.getChannelIdType().getId(ctx.channel().id()),
                response.getHeaders().getValue(CONTENT_LENGTH),
                startTime == null ? "undefined" : format(Duration.ofNanos(System.nanoTime() - startTime))
        );
    }
}
