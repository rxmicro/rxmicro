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

package io.rxmicro.test.mockito.httpclient.internal.model;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.http.client.ClientHttpResponse;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import static io.rxmicro.common.util.Requires.require;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author nedis
 * @since 0.1
 */
public final class ClientHttpResponseImpl implements ClientHttpResponse {

    private final int status;

    private final HttpVersion version;

    private final HttpHeaders headers;

    private final Object body;

    public ClientHttpResponseImpl(final int status,
                                  final HttpVersion version,
                                  final HttpHeaders headers,
                                  final Object body) {
        this.status = status;
        this.version = require(version);
        this.headers = require(headers);
        this.body = require(body);
    }

    public ClientHttpResponseImpl(final int status,
                                  final HttpVersion version,
                                  final HttpHeaders headers) {
        this.status = status;
        this.version = require(version);
        this.headers = require(headers);
        this.body = null;
    }

    @Override
    public int getStatusCode() {
        return status;
    }

    @Override
    public HttpVersion getVersion() {
        return version;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean isBodyEmpty() {
        if (body == null) {
            return true;
        } else if (body instanceof Map) {
            return ((Map) body).isEmpty();
        } else if (body instanceof Collection) {
            return ((Collection) body).isEmpty();
        } else if (body.getClass().isArray()) {
            return Array.getLength(body) == 0;
        } else if (body.getClass() == String.class) {
            return body.toString().isEmpty();
        } else {
            return false;
        }
    }

    @Override
    public Object getBody() {
        return body;
    }

    @Override
    public byte[] getBodyAsBytes() {
        if (body == null) {
            return new byte[0];
        } else if (body instanceof String) {
            return ((String) body).getBytes(UTF_8);
        } else if (body instanceof byte[]) {
            return (byte[]) body;
        } else {
            throw new UnsupportedOperationException(
                    "Can't convert body object to byte array definitely! " +
                            "Use String or byte array body instead!"
            );
        }
    }
}
