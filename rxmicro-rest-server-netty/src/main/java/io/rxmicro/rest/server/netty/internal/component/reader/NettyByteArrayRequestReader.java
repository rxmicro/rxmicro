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

package io.rxmicro.rest.server.netty.internal.component.reader;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.rxmicro.logger.Logger;
import io.rxmicro.netty.runtime.NettyRuntimeConfig;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.feature.RequestIdGenerator;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpRequest;
import io.rxmicro.rest.server.netty.internal.util.HttpFragmentBuilder;

import java.util.Optional;

import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_LENGTH;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_TYPE;
import static io.rxmicro.http.local.PredefinedUrls.HEALTH_CHECK_URLS;
import static io.rxmicro.rest.server.netty.internal.model.NettyHttpRequest.REQUEST_ID_KEY;
import static io.rxmicro.rest.server.netty.internal.util.HealthCheckTools.isHealthCheckToolAddress;

/**
 * @author nedis
 * @since 0.8
 */
public final class NettyByteArrayRequestReader {

    private final Logger logger;

    private final NettyRuntimeConfig nettyRuntimeConfig;

    private final RestServerConfig restServerConfig;

    private final RequestIdGenerator requestIdGenerator;

    private final HttpFragmentBuilder httpFragmentBuilder;

    public NettyByteArrayRequestReader(final Logger logger) {
        this.logger = logger;
        this.nettyRuntimeConfig = getConfig(NettyRuntimeConfig.class);
        this.restServerConfig = getConfig(RestServerConfig.class);
        this.requestIdGenerator = this.restServerConfig.getRequestIdGenerator();
        this.httpFragmentBuilder = new HttpFragmentBuilder(this.restServerConfig);
    }

    public NettyHttpRequest read(final ChannelHandlerContext ctx,
                                 final FullHttpRequest msg) {
        final NettyHttpRequest request = new NettyHttpRequest(
                requestIdGenerator,
                msg,
                ctx.channel().remoteAddress()
        );
        ctx.channel().attr(REQUEST_ID_KEY).set(request.getRequestId());
        logRequest(ctx, request);
        return request;
    }

    private void logRequest(final ChannelHandlerContext ctx,
                            final NettyHttpRequest request) {
        if (logger.isTraceEnabled()) {
            if (isHealthCheckToolAddress(restServerConfig, ctx.channel()) ||
                    restServerConfig.isDisableLoggerMessagesForHttpHealthChecks() && HEALTH_CHECK_URLS.contains(request.getUri())) {
                return;
            }
            traceRequest(ctx, request);
        } else if (logger.isDebugEnabled()) {
            if (isHealthCheckToolAddress(restServerConfig, ctx.channel()) ||
                    restServerConfig.isDisableLoggerMessagesForHttpHealthChecks() && HEALTH_CHECK_URLS.contains(request.getUri())) {
                return;
            }
            debugRequest(ctx, request);
        }
    }

    private void traceRequest(final ChannelHandlerContext ctx,
                              final NettyHttpRequest request) {
        logger.trace(
                request,
                "HTTP request: (Channel=?, Socket=?):\n? ?\n?\n\n?",
                nettyRuntimeConfig.getChannelIdType().getId(ctx.channel().id()),
                httpFragmentBuilder.buildRemoteClientSocket(ctx, request),
                httpFragmentBuilder.buildRequestString(request),
                request.getVersion().getText(),
                httpFragmentBuilder.buildHeaders(request.getHeaders()),
                httpFragmentBuilder.buildBody(request)
        );
    }

    private void debugRequest(final ChannelHandlerContext ctx,
                              final NettyHttpRequest request) {
        final String remoteClientSocket = httpFragmentBuilder.buildRemoteClientSocket(ctx, request);
        final String requestString = httpFragmentBuilder.buildRequestString(request);
        if (request.isContentPresent()) {
            logger.debug(
                    request,
                    "HTTP request:  Channel=?, Socket=?, Request=?, Body=? bytes, ContentType=?",
                    nettyRuntimeConfig.getChannelIdType().getId(ctx.channel().id()),
                    remoteClientSocket,
                    requestString,
                    Optional.ofNullable(request.getHeaders().getValue(CONTENT_LENGTH)).orElse("0"),
                    Optional.ofNullable(request.getHeaders().getValue(CONTENT_TYPE)).orElse("undefined")
            );
        } else {
            logger.debug(
                    request,
                    "HTTP request:  Channel=?, Socket=?, Request=?",
                    nettyRuntimeConfig.getChannelIdType().getId(ctx.channel().id()),
                    remoteClientSocket,
                    requestString
            );
        }
    }
}
