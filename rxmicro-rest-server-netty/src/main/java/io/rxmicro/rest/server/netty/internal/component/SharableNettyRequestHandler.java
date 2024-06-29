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
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;
import io.rxmicro.rest.server.local.component.RequestHandler;
import io.rxmicro.rest.server.netty.internal.component.reader.NettyByteArrayRequestReader;
import io.rxmicro.rest.server.netty.internal.component.writer.NettyByteArrayResponseWriter;
import io.rxmicro.rest.server.netty.internal.component.writer.NettySendFileResponseWriter;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpRequest;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpResponse;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.rest.server.netty.internal.model.NettyHttpRequest.REQUEST_ID_KEY;
import static io.rxmicro.rest.server.netty.internal.model.NettyHttpRequest.START_PROCESSING_REQUEST_TIME_KEY;

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

    SharableNettyRequestHandler(final RequestHandler requestHandler,
                                final HttpResponseBuilder responseBuilder,
                                final HttpErrorResponseBodyBuilder responseContentBuilder) {
        this.requestHandler = require(requestHandler);
        this.nettyErrorHandler = new NettyErrorHandler(responseBuilder, responseContentBuilder);
        this.nettyByteArrayRequestReader = new NettyByteArrayRequestReader(LOGGER);
        this.nettyByteArrayResponseWriter = new NettyByteArrayResponseWriter(LOGGER, nettyErrorHandler);
        this.nettySendFileResponseWriter = new NettySendFileResponseWriter(LOGGER, nettyErrorHandler);
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx,
                                final FullHttpRequest msg) {
        ctx.channel().attr(START_PROCESSING_REQUEST_TIME_KEY).set(System.nanoTime());
        final NettyHttpRequest request = nettyByteArrayRequestReader.read(ctx, msg);
        try {
            requestHandler.handle(request)
                    .thenAccept(response -> {
                        final NettyHttpResponse nettyHttpResponse = (NettyHttpResponse) response;
                        if (nettyHttpResponse.isFileContent()) {
                            nettySendFileResponseWriter.writeResponse(ctx, request, nettyHttpResponse);
                        } else {
                            nettyByteArrayResponseWriter.writeResponse(ctx, request, nettyHttpResponse);
                        }
                    })
                    .exceptionally(th -> handleError(ctx, request, th));
        } catch (final Throwable th) {
            handleError(ctx, request, th);
        }
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx,
                                final Throwable cause) {
        try {
            final String requestId = ctx.channel().attr(REQUEST_ID_KEY).get();
            nettyErrorHandler.logInternalError(ctx, requestId, cause);
        } finally {
            ctx.close();
        }
    }

    @SuppressWarnings("SameReturnValue")
    private Void handleError(final ChannelHandlerContext ctx,
                             final NettyHttpRequest request,
                             final Throwable cause) {
        final String requestId = ctx.channel().attr(REQUEST_ID_KEY).get();
        final NettyHttpResponse response = nettyErrorHandler.build(ctx, requestId, cause);
        nettyByteArrayResponseWriter.writeResponse(ctx, request, response);
        return null;
    }
}
