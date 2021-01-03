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

package io.rxmicro.rest.client.netty.internal;

import io.rxmicro.common.InvalidStateException;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.rest.client.detail.HttpResponse;
import reactor.netty.http.client.HttpClientResponse;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author nedis
 * @since 0.8
 */
final class NettyHttpResponse implements HttpResponse {

    private final HttpClientResponse response;

    private final Function<byte[], Object> responseBodyConverter;

    private HttpHeaders httpHeaders;

    private final byte[] body;

    NettyHttpResponse(final HttpClientResponse response,
                      final byte[] body,
                      final Function<byte[], Object> responseBodyConverter) {
        this.response = response;
        this.body = body;
        this.responseBodyConverter = responseBodyConverter;
    }

    @Override
    public int getStatusCode() {
        return response.status().code();
    }

    @Override
    public HttpVersion getVersion() {
        final io.netty.handler.codec.http.HttpVersion httpVersion = response.version();
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
            httpHeaders = new NettyHttpHeaders(response.responseHeaders());
        }
        return httpHeaders;
    }

    @Override
    public boolean isBodyEmpty() {
        return body.length == 0;
    }

    @Override
    public Object getBody() {
        return responseBodyConverter.apply(body);
    }

    @Override
    public byte[] getBodyAsBytes() {
        return body;
    }
}
