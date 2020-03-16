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

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.rxmicro.http.local.RepeatableHttpHeaders;
import io.rxmicro.rest.server.detail.model.HttpResponse;

import java.util.List;
import java.util.Map;

import static io.netty.buffer.Unpooled.wrappedBuffer;
import static io.rxmicro.http.HttpHeaders.CONTENT_LENGTH;
import static io.rxmicro.http.HttpHeaders.EMPTY_HEADERS;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class NettyHttpResponse implements HttpResponse {

    private final NettyWriteOnlyHttpHeaders headers = new NettyWriteOnlyHttpHeaders();

    private HttpVersion httpVersion;

    private HttpResponseStatus status;

    private byte[] content;

    public NettyHttpResponse() {
        httpVersion = HttpVersion.HTTP_1_1;
        status = HttpResponseStatus.OK;
        setContent(EMPTY_CONTENT);
    }

    public FullHttpResponse toFullHttpResponse() {
        final HttpHeaders headers = this.headers.toHttpHeaders();
        return new DefaultFullHttpResponse(httpVersion, status, wrappedBuffer(content), headers, headers);
    }

    public HttpResponseStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(final int status) {
        this.status = HttpResponseStatus.valueOf(status);
    }

    @Override
    public void setVersion(final io.rxmicro.http.HttpVersion httpVersion) {
        if (httpVersion == io.rxmicro.http.HttpVersion.HTTP_1_1) {
            this.httpVersion = HttpVersion.HTTP_1_1;
        } else if (httpVersion == io.rxmicro.http.HttpVersion.HTTP_1_0) {
            this.httpVersion = HttpVersion.HTTP_1_0;
        } else {
            throw new IllegalArgumentException("HTTP/2 is not supported now");
        }
    }

    @Override
    public void addHeader(final String name,
                          final String value) {
        if (value != null) {
            headers.add(name, value);
        }
    }

    @Override
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public void setOrAddHeaders(final io.rxmicro.http.HttpHeaders headers) {
        if (headers != EMPTY_HEADERS && headers.isNotEmpty()) {
            if (headers instanceof RepeatableHttpHeaders) {
                this.headers.setOrAddAll((RepeatableHttpHeaders) headers);
            } else {
                final List<Map.Entry<String, String>> all = headers.getEntries();
                for (int i = 0; i < all.size(); i++) {
                    final Map.Entry<String, String> entry = all.get(i);
                    this.headers.setOrAdd(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    @Override
    public void setHeader(final String name,
                          final String value) {
        if (value != null) {
            headers.set(name, value);
        }
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public NettyWriteOnlyHttpHeaders getHeaders() {
        return headers;
    }

    public int getContentLength() {
        return content.length;
    }

    public byte[] getContent() {
        return content;
    }

    @Override
    public void setContent(final byte[] content) {
        this.content = content;
        setHeader(CONTENT_LENGTH, content.length);
    }
}
