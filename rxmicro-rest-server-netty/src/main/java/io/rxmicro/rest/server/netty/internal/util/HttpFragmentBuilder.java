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

package io.rxmicro.rest.server.netty.internal.util;

import io.netty.channel.ChannelHandlerContext;
import io.rxmicro.config.Secrets;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.netty.internal.model.HttpContentHolder;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpRequest;

import java.util.Optional;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.9
 */
public final class HttpFragmentBuilder {

    private final Secrets secrets;

    private final RestServerConfig restServerConfig;

    public HttpFragmentBuilder(final RestServerConfig restServerConfig) {
        this.secrets = Secrets.getDefaultInstance();
        this.restServerConfig = restServerConfig;
    }

    public String buildRequestString(final NettyHttpRequest request) {
        return format(
                "? ??",
                request.getMethod(),
                request.getUri(),
                request.isQueryStringPresent() ?
                        '?' + secrets.hideAllSecretsIn(request.getQueryString()) :
                        ""
        );
    }

    public String buildRemoteClientSocket(final ChannelHandlerContext ctx,
                                          final NettyHttpRequest request) {
        if (restServerConfig.getForwardedHeaderNames().isEmpty()) {
            return ctx.channel().remoteAddress().toString();
        } else {
            final String realIpAddresses = restServerConfig.getForwardedHeaderNames().stream()
                    .flatMap(h -> Optional.ofNullable(request.getHeaders().getValue(h)).stream().map(v -> format("?: ?", h, v)))
                    .collect(joining(";"));
            if (realIpAddresses.isEmpty()) {
                return ctx.channel().remoteAddress().toString();
            } else {
                return format("? (?)", ctx.channel().remoteAddress(), realIpAddresses);
            }
        }
    }

    public String buildHeaders(final boolean isRequestIdGenerated,
                               final HttpHeaders headers) {
        return headers.getEntries().stream()
                .filter(e -> isRequestIdGenerated && !REQUEST_ID.equals(e.getKey()))
                .map(e -> format("?: ?", e.getKey(), secrets.hideIfSecret(e.getValue())))
                .collect(joining(lineSeparator()));
    }

    public String buildBody(final HttpContentHolder httpContentHolder) {
        return httpContentHolder.isFileContent() ? "<file content>" :
                httpContentHolder.isContentPresent() ?
                        secrets.hideAllSecretsIn(new String(httpContentHolder.getContent(), UTF_8)) :
                        "";
    }
}
