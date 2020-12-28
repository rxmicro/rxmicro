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
import io.netty.handler.codec.http.HttpResponseStatus;
import io.rxmicro.netty.runtime.NettyRuntimeConfig;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.feature.ErrorHandler;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpResponse;

import static io.rxmicro.config.Configs.getConfig;

/**
 * @author nedis
 * @since 0.7
 */
public final class NettyErrorHandler extends ErrorHandler {

    private final HttpResponseBuilder responseBuilder;

    private final HttpErrorResponseBodyBuilder responseContentBuilder;

    private final NettyRuntimeConfig nettyRuntimeConfig;

    NettyErrorHandler(final HttpResponseBuilder responseBuilder,
                      final HttpErrorResponseBodyBuilder responseContentBuilder) {
        this.responseBuilder = responseBuilder;
        this.responseContentBuilder = responseContentBuilder;
        this.nettyRuntimeConfig = getConfig(NettyRuntimeConfig.class);
    }

    public NettyHttpResponse build(final ChannelHandlerContext ctx,
                                   final String requestId,
                                   final Throwable cause) {
        logInternalError(ctx, requestId, "Request handling failed: message=?, Channel=?, IP=?", cause);
        return (NettyHttpResponse) responseContentBuilder.build(
                responseBuilder,
                HttpResponseStatus.INTERNAL_SERVER_ERROR.code(),
                "Internal error"
        );
    }

    public void logInternalError(final ChannelHandlerContext ctx,
                                 final String requestId,
                                 final Throwable cause) {
        logInternalError(ctx, requestId, "Netty channel error: message=?, Channel=?, IP=?", cause);
    }

    private void logInternalError(final ChannelHandlerContext ctx,
                                  final String requestId,
                                  final String messageTemplate,
                                  final Throwable cause) {
        LOGGER.error(
                () -> requestId,
                cause,
                messageTemplate,
                cause.getMessage(),
                nettyRuntimeConfig.getChannelIdType().getId(ctx.channel().id()),
                ctx.channel().remoteAddress()
        );
    }
}
