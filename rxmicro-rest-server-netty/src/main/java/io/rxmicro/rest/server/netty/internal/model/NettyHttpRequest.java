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

package io.rxmicro.rest.server.netty.internal.model;

import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.AttributeKey;
import io.rxmicro.common.InvalidStateException;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.logger.RequestIdSupplier;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.feature.RequestIdGenerator;

import java.net.SocketAddress;
import java.util.Objects;

import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;

/**
 * @author nedis
 * @since 0.1
 */
public final class NettyHttpRequest implements HttpRequest, RequestIdSupplier {

    public static final AttributeKey<String> REQUEST_ID_KEY = AttributeKey.valueOf(REQUEST_ID);

    public static final AttributeKey<Long> START_PROCESSING_REQUEST_TIME_KEY = AttributeKey.valueOf("START_PROCESSING_REQUEST_TIME");

    private static final byte[] EMPTY = new byte[0];

    private final SocketAddress remoteAddress;

    private final String requestId;

    private final boolean requestIdGenerated;

    private final String uri;

    private final String queryString;

    private final FullHttpRequest fullHttpRequest;

    private HttpHeaders httpHeaders;

    private Boolean contentPresent;

    public NettyHttpRequest(final RequestIdGenerator requestIdGenerator,
                            final FullHttpRequest fullHttpRequest,
                            final SocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
        final String requestIdHeaderValue = fullHttpRequest.headers().get(REQUEST_ID);
        if (requestIdHeaderValue != null) {
            this.requestId = requestIdHeaderValue;
            this.requestIdGenerated = false;
        } else {
            this.requestId = requestIdGenerator.getNextId();
            this.requestIdGenerated = true;
        }
        final String uri = fullHttpRequest.uri();
        final int index = uri.indexOf('?');
        if (index > 0) {
            this.uri = uri.substring(0, index);
            this.queryString = uri.substring(index + 1);
        } else {
            this.uri = uri;
            this.queryString = "";
        }
        this.fullHttpRequest = fullHttpRequest;
    }

    @Override
    public SocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    @Override
    public String getRequestId() {
        return requestId;
    }

    @Override
    public String getMethod() {
        return fullHttpRequest.method().name();
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getQueryString() {
        return queryString;
    }

    @Override
    public HttpVersion getVersion() {
        final io.netty.handler.codec.http.HttpVersion httpVersion = fullHttpRequest.protocolVersion();
        if (Objects.equals(io.netty.handler.codec.http.HttpVersion.HTTP_1_1, httpVersion)) {
            return HttpVersion.HTTP_1_1;
        } else if (Objects.equals(io.netty.handler.codec.http.HttpVersion.HTTP_1_0, httpVersion)) {
            return HttpVersion.HTTP_1_0;
        } else {
            throw new InvalidStateException("Unsupported HTTP version: ?", httpVersion);
        }
    }

    @Override
    public HttpHeaders getHeaders() {
        if (httpHeaders == null) {
            httpHeaders = new NettyReadOnlyHttpHeaders(requestId, requestIdGenerated, fullHttpRequest.headers());
        }
        return httpHeaders;
    }

    @Override
    public boolean isContentPresent() {
        if (contentPresent == null) {
            contentPresent = fullHttpRequest.content().readableBytes() > 0;
        }
        return contentPresent;
    }

    @Override
    public byte[] getContent() {
        if (isContentPresent()) {
            return ByteBufUtil.getBytes(fullHttpRequest.content());
        } else {
            return EMPTY;
        }
    }

    public FullHttpRequest getFullHttpRequest() {
        return fullHttpRequest;
    }

    public boolean isRequestIdGenerated() {
        return requestIdGenerated;
    }
}
