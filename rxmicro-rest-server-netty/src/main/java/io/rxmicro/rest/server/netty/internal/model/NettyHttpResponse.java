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
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.rxmicro.common.InvalidStateException;
import io.rxmicro.http.local.RepeatableHttpHeaders;
import io.rxmicro.rest.server.detail.model.HttpResponse;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static io.netty.buffer.Unpooled.wrappedBuffer;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_LENGTH;

/**
 * @author nedis
 * @since 0.1
 */
public final class NettyHttpResponse implements HttpResponse, HttpContentHolder {

    private final NettyWriteOnlyHttpHeaders headers = new NettyWriteOnlyHttpHeaders();

    private HttpVersion httpVersion;

    private HttpResponseStatus status;

    private byte[] content;

    private Path sendFilePath;

    public NettyHttpResponse() {
        httpVersion = HttpVersion.HTTP_1_1;
        status = HttpResponseStatus.OK;
        setContent(EMPTY_CONTENT);
    }

    public FullHttpResponse toHttpResponseWithBody() {
        final HttpHeaders headers = this.headers.toHttpHeaders();
        return new DefaultFullHttpResponse(httpVersion, status, wrappedBuffer(content), headers, headers);
    }

    public io.netty.handler.codec.http.HttpResponse toHttpResponseWithoutBody() {
        final HttpHeaders headers = this.headers.toHttpHeaders();
        return new DefaultHttpResponse(httpVersion, status, headers);
    }

    public HttpResponseStatus getStatus() {
        return status;
    }

    @Override
    public NettyHttpResponse setStatus(final int status) {
        this.status = HttpResponseStatus.valueOf(status);
        return this;
    }

    @Override
    public NettyHttpResponse setVersion(final io.rxmicro.http.HttpVersion httpVersion) {
        if (httpVersion == io.rxmicro.http.HttpVersion.HTTP_1_1) {
            this.httpVersion = HttpVersion.HTTP_1_1;
        } else if (httpVersion == io.rxmicro.http.HttpVersion.HTTP_1_0) {
            this.httpVersion = HttpVersion.HTTP_1_0;
        } else {
            throw new IllegalArgumentException("HTTP/2 is not supported now");
        }
        return this;
    }

    @Override
    public NettyHttpResponse addHeader(final String name,
                                       final String value) {
        if (value != null) {
            headers.add(name, value);
        }
        return this;
    }

    @Override
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public NettyHttpResponse setOrAddHeaders(final io.rxmicro.http.HttpHeaders headers) {
        if (headers.isNotEmpty()) {
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
        return this;
    }

    @Override
    public NettyHttpResponse setHeader(final String name,
                                       final String value) {
        if (value != null) {
            headers.set(name, value);
        }
        return this;
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

    @Override
    public boolean isContentPresent() {
        return content.length > 0;
    }

    @Override
    public byte[] getContent() {
        return content;
    }

    public Path getSendFilePath() {
        return sendFilePath;
    }

    @Override
    public NettyHttpResponse setContent(final byte[] content) {
        if (this.sendFilePath != null) {
            throw new InvalidStateException(
                    "Can't set byte array content, because '?' send file path already set!", this.sendFilePath.toAbsolutePath()
            );
        }
        this.content = content;
        setHeader(CONTENT_LENGTH, content.length);
        return this;
    }

    @Override
    public NettyHttpResponse sendFile(final Path path) {
        if (this.content.length != 0) {
            throw new InvalidStateException(
                    "Can't send file, because byte content with ? bytes already set!", this.content.length
            );
        }
        this.sendFilePath = path;
        return this;
    }

    @Override
    public boolean isFileContent() {
        return this.sendFilePath != null;
    }
}
