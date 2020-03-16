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
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.local.component.RequestIdGenerator;

import java.net.SocketAddress;

import static io.rxmicro.http.HttpHeaders.REQUEST_ID;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class NettyHttpRequest implements HttpRequest {

    private static final byte[] EMPTY = new byte[0];

    private final SocketAddress remoteAddress;

    private final String requestId;

    private final boolean requestIdGenerated;

    private final String uri;

    private final String queryString;

    private final FullHttpRequest request;

    private HttpHeaders httpHeaders;

    private Boolean contentExists;

    public NettyHttpRequest(final RequestIdGenerator requestIdGenerator,
                            final FullHttpRequest request,
                            final SocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
        final String requestIdHeaderValue = request.headers().get(REQUEST_ID);
        if (requestIdHeaderValue != null) {
            this.requestId = requestIdHeaderValue;
            this.requestIdGenerated = false;
        } else {
            this.requestId = requestIdGenerator.getNextId();
            this.requestIdGenerated = true;
        }
        final String uri = request.uri();
        final int index = uri.indexOf('?');
        if (index > 0) {
            this.uri = uri.substring(0, index);
            this.queryString = uri.substring(index + 1);
        } else {
            this.uri = uri;
            this.queryString = "";
        }
        this.request = request;
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
        return request.method().name();
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
        final io.netty.handler.codec.http.HttpVersion httpVersion = request.protocolVersion();
        if (httpVersion == io.netty.handler.codec.http.HttpVersion.HTTP_1_1) {
            return HttpVersion.HTTP_1_1;
        } else if (httpVersion == io.netty.handler.codec.http.HttpVersion.HTTP_1_0) {
            return HttpVersion.HTTP_1_0;
        } else {
            throw new IllegalStateException("Unsupported HTTP version: " + httpVersion);
        }
    }

    @Override
    public HttpHeaders getHeaders() {
        if (httpHeaders == null) {
            httpHeaders = new NettyReadOnlyHttpHeaders(requestId, requestIdGenerated, request.headers());
        }
        return httpHeaders;
    }

    @Override
    public boolean contentExists() {
        if (contentExists == null) {
            contentExists = request.content().readableBytes() > 0;
        }
        return contentExists;
    }

    @Override
    public byte[] getContent() {
        if (contentExists()) {
            return ByteBufUtil.getBytes(request.content());
        } else {
            return EMPTY;
        }
    }

    public boolean isRequestIdGenerated() {
        return requestIdGenerated;
    }
}
