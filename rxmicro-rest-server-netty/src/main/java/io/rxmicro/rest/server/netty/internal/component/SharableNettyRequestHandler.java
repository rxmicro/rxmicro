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

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.rxmicro.config.Secrets;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.feature.RequestIdGenerator;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;
import io.rxmicro.rest.server.local.component.RequestHandler;
import io.rxmicro.rest.server.netty.NettyRestServerConfig;
import io.rxmicro.rest.server.netty.internal.component.reader.NettyByteArrayRequestReader;
import io.rxmicro.rest.server.netty.internal.component.writer.NettyByteArrayResponseWriter;
import io.rxmicro.rest.server.netty.internal.component.writer.NettySendFileResponseWriter;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpRequest;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpResponse;

import static io.rxmicro.http.HttpStandardHeaderNames.CONNECTION;
import static io.rxmicro.http.ProtocolSchema.HTTPS;
import static io.rxmicro.rest.server.netty.internal.model.NettyHttpRequest.REQUEST_ID_KEY;

/**
 * @author nedis
 * @since 0.1
 */
@ChannelHandler.Sharable
final class SharableNettyRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SharableNettyRequestHandler.class);

    private final RequestHandler requestHandler;

    private final NettyByteArrayRequestReader nettyByteArrayRequestReader;

    private final NettyByteArrayResponseWriter nettyByteArrayResponseWriter;

    private final NettySendFileResponseWriter nettySendFileResponseWriter;

    private final NettyErrorHandler nettyErrorHandler;

    SharableNettyRequestHandler(final NettyRestServerConfig nettyRestServerConfig,
                                final RequestHandler requestHandler,
                                final RequestIdGenerator requestIdGenerator,
                                final HttpResponseBuilder responseBuilder,
                                final HttpErrorResponseBodyBuilder responseContentBuilder,
                                final HttpServerConfig httpServerConfig,
                                final RestServerConfig restServerConfig) {
        this.requestHandler = requestHandler;

        final Secrets secrets = Secrets.getDefaultInstance();
        final boolean disableLoggerMessagesForHttpHealthChecks = restServerConfig.isDisableLoggerMessagesForHttpHealthChecks();
        final boolean returnGeneratedRequestId = restServerConfig.isReturnGeneratedRequestId();
        this.nettyByteArrayRequestReader = new NettyByteArrayRequestReader(
                LOGGER, requestIdGenerator, secrets, nettyRestServerConfig, disableLoggerMessagesForHttpHealthChecks
        );
        this.nettyByteArrayResponseWriter = new NettyByteArrayResponseWriter(
                LOGGER, secrets, nettyRestServerConfig, returnGeneratedRequestId, disableLoggerMessagesForHttpHealthChecks
        );
        this.nettySendFileResponseWriter = new NettySendFileResponseWriter(
                httpServerConfig.getSchema() == HTTPS,
                LOGGER, secrets, httpServerConfig, nettyRestServerConfig, returnGeneratedRequestId, disableLoggerMessagesForHttpHealthChecks
        );
        this.nettyErrorHandler = new NettyErrorHandler(nettyRestServerConfig, responseBuilder, responseContentBuilder);
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx,
                                final FullHttpRequest msg) {
        final boolean keepAlive = isKeepAlive(msg);
        final long startTime = System.nanoTime();
        final NettyHttpRequest request = nettyByteArrayRequestReader.read(ctx, msg);
        try {
            requestHandler.handle(request)
                    .thenAccept(response -> {
                        final NettyHttpResponse nettyHttpResponse = (NettyHttpResponse) response;
                        if (nettyHttpResponse.isSendFileResponse()) {
                            nettySendFileResponseWriter.writeResponse(ctx, request, nettyHttpResponse, startTime, keepAlive);
                        } else {
                            nettyByteArrayResponseWriter.writeResponse(ctx, request, nettyHttpResponse, startTime, keepAlive);
                        }
                    })
                    .exceptionally(th -> handleError(ctx, request, th, startTime, keepAlive));
        } catch (final Throwable th) {
            handleError(ctx, request, th, startTime, keepAlive);
        }
    }

    private boolean isKeepAlive(final FullHttpRequest msg) {
        final String value = msg.headers().getAsString(CONNECTION);
        return !"close".equalsIgnoreCase(value);
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx,
                                final Throwable cause) {
        final String requestId = ctx.channel().attr(REQUEST_ID_KEY).get();
        nettyErrorHandler.logInternalError(requestId, ctx, cause);
        ctx.close(ctx.voidPromise());
    }

    @SuppressWarnings("SameReturnValue")
    private Void handleError(final ChannelHandlerContext ctx,
                             final NettyHttpRequest request,
                             final Throwable cause,
                             final long startTime,
                             final boolean keepAlive) {
        final String requestId = ctx.channel().attr(REQUEST_ID_KEY).get();
        final NettyHttpResponse errorResponse = nettyErrorHandler.build(requestId, ctx, cause);
        nettyByteArrayResponseWriter.writeResponse(ctx, request, errorResponse, startTime, keepAlive);
        return null;
    }
}
