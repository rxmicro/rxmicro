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
import io.rxmicro.config.Secrets;
import io.rxmicro.logger.Logger;
import io.rxmicro.netty.runtime.NettyRuntimeConfig;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.feature.RequestIdGenerator;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpRequest;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static io.rxmicro.http.local.PredefinedUrls.HTTP_HEALTH_CHECK_ENDPOINT;
import static io.rxmicro.rest.server.netty.internal.model.NettyHttpRequest.REQUEST_ID_KEY;
import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.8
 */
public final class NettyByteArrayRequestReader {

    private final Logger logger;

    private final RequestIdGenerator requestIdGenerator;

    private final Secrets secrets;

    private final NettyRuntimeConfig nettyRuntimeConfig;

    private final RestServerConfig restServerConfig;

    public NettyByteArrayRequestReader(final Logger logger) {
        this.logger = logger;
        this.secrets = Secrets.getDefaultInstance();
        this.nettyRuntimeConfig = getConfig(NettyRuntimeConfig.class);
        this.restServerConfig = getConfig(RestServerConfig.class);
        this.requestIdGenerator = this.restServerConfig.getRequestIdGenerator();
    }

    public NettyHttpRequest read(final ChannelHandlerContext ctx,
                                 final FullHttpRequest msg) {
        final NettyHttpRequest request = new NettyHttpRequest(
                requestIdGenerator,
                msg,
                ctx.channel().remoteAddress()
        );
        ctx.channel().attr(REQUEST_ID_KEY).set(request.getRequestId());
        logRequest(request, ctx);
        return request;
    }

    private void logRequest(final NettyHttpRequest request,
                            final ChannelHandlerContext ctx) {
        if (logger.isTraceEnabled()) {
            if (restServerConfig.isDisableLoggerMessagesForHttpHealthChecks() && HTTP_HEALTH_CHECK_ENDPOINT.equals(request.getUri())) {
                return;
            }
            traceRequest(request, ctx);
        } else if (logger.isDebugEnabled()) {
            if (restServerConfig.isDisableLoggerMessagesForHttpHealthChecks() && HTTP_HEALTH_CHECK_ENDPOINT.equals(request.getUri())) {
                return;
            }
            debugRequest(request, ctx);
        }
    }

    private void traceRequest(final NettyHttpRequest request,
                              final ChannelHandlerContext ctx) {
        logger.trace(
                request,
                "HTTP request:  (Channel=?, IP=?):\n? ?\n?\n\n?",
                nettyRuntimeConfig.getChannelIdType().getId(ctx.channel().id()),
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
        logger.debug(
                request,
                "HTTP request:  Channel=?, IP=?, Request=?",
                nettyRuntimeConfig.getChannelIdType().getId(ctx.channel().id()),
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
}
