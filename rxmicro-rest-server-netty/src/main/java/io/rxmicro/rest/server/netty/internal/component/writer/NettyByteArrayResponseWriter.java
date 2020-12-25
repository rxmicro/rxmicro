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

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.rxmicro.logger.Logger;
import io.rxmicro.rest.server.netty.internal.component.NettyErrorHandler;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpRequest;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpResponse;

/**
 * @author nedis
 * @since 0.8
 */
public final class NettyByteArrayResponseWriter extends BaseNettyResponseWriter {

    public NettyByteArrayResponseWriter(final Logger logger,
                                        final NettyErrorHandler nettyErrorHandler) {
        super(logger, nettyErrorHandler);
    }

    public void writeResponse(final ChannelHandlerContext ctx,
                              final NettyHttpRequest request,
                              final NettyHttpResponse response) {
        final boolean keepAlive = isKeepAlive(request);
        setCommonHeaders(request, response, keepAlive);
        ctx.writeAndFlush(response.toHttpResponseWithBody())
                .addListener((ChannelFutureListener) future -> afterResponseWritten(ctx, future, request, response, keepAlive));
    }
}
