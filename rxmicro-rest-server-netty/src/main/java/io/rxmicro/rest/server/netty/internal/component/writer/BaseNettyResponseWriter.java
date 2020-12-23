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

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.rxmicro.config.Secrets;
import io.rxmicro.logger.Logger;
import io.rxmicro.rest.server.netty.NettyRestServerConfig;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpRequest;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpResponse;

import java.time.Duration;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.http.HttpStandardHeaderNames.CONNECTION;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_LENGTH;
import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static io.rxmicro.http.HttpVersion.HTTP_1_0;
import static io.rxmicro.http.local.PredefinedUrls.HTTP_HEALTH_CHECK_ENDPOINT;
import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.8
 */
class BaseNettyResponseWriter {

    private final Logger logger;

    private final Secrets secrets;

    private final NettyRestServerConfig nettyRestServerConfig;

    private final boolean disableLoggerMessagesForHttpHealthChecks;

    private final boolean returnGeneratedRequestId;

    BaseNettyResponseWriter(final Logger logger,
                            final Secrets secrets,
                            final NettyRestServerConfig nettyRestServerConfig,
                            final boolean disableLoggerMessagesForHttpHealthChecks,
                            final boolean returnGeneratedRequestId) {
        this.logger = logger;
        this.secrets = secrets;
        this.nettyRestServerConfig = nettyRestServerConfig;
        this.disableLoggerMessagesForHttpHealthChecks = disableLoggerMessagesForHttpHealthChecks;
        this.returnGeneratedRequestId = returnGeneratedRequestId;
    }

    final void setCommonHeaders(final NettyHttpRequest request,
                                final NettyHttpResponse response,
                                final boolean keepAlive) {
        if (request.isRequestIdGenerated()) {
            if (returnGeneratedRequestId) {
                response.setHeader(REQUEST_ID, request.getRequestId());
            }
        } else {
            response.setHeader(REQUEST_ID, request.getRequestId());
        }
        if (keepAlive) {
            if (request.getVersion() == HTTP_1_0) {
                response.setHeader(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
        } else {
            response.setHeader(CONNECTION, HttpHeaderValues.CLOSE);
        }
    }

    final void logResponse(final NettyHttpRequest request,
                           final long startTime,
                           final NettyHttpResponse httpResponse,
                           final ChannelHandlerContext ctx) {
        if (logger.isTraceEnabled()) {
            if (disableLoggerMessagesForHttpHealthChecks && HTTP_HEALTH_CHECK_ENDPOINT.equals(request.getUri())) {
                return;
            }
            traceResponse(request, startTime, httpResponse, ctx);
        } else if (logger.isDebugEnabled()) {
            if (disableLoggerMessagesForHttpHealthChecks && HTTP_HEALTH_CHECK_ENDPOINT.equals(request.getUri())) {
                return;
            }
            debugResponse(request, startTime, httpResponse, ctx);
        }
    }

    private void traceResponse(final NettyHttpRequest request,
                               final long startTime,
                               final NettyHttpResponse httpResponse,
                               final ChannelHandlerContext ctx) {
        logger.trace(
                request,
                "HTTP response: (Channel=?, Duration=?):\n? ?\n?\n\n?",
                nettyRestServerConfig.getChannelIdType().getId(ctx.channel().id()),
                startTime == 0L ? "undefined" : format(Duration.ofNanos(System.nanoTime() - startTime)),
                httpResponse.getHttpVersion(),
                httpResponse.getStatus(),
                httpResponse.getHeaders().getEntries().stream()
                        .map(e -> format("?: ?", e.getKey(), secrets.hideIfSecret(e.getValue())))
                        .collect(joining(lineSeparator())),
                httpResponse.isSendFileResponse() ? "<file content>" :
                        httpResponse.getContentLength() > 0 ?
                                secrets.hideAllSecretsIn(new String(httpResponse.getContent(), UTF_8)) :
                                ""
        );
    }

    private void debugResponse(final NettyHttpRequest request,
                               final long startTime,
                               final NettyHttpResponse httpResponse,
                               final ChannelHandlerContext ctx) {
        logger.debug(
                request,
                "HTTP response: Channel=?, Content=? bytes, Duration=?",
                nettyRestServerConfig.getChannelIdType().getId(ctx.channel().id()),
                httpResponse.getHeaders().getValue(CONTENT_LENGTH),
                startTime == 0L ? "undefined" : format(Duration.ofNanos(System.nanoTime() - startTime))
        );
    }
}
