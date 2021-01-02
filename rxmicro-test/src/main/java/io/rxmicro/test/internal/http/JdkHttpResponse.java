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

package io.rxmicro.test.internal.http;

import io.rxmicro.common.InvalidStateException;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.test.ClientHttpResponse;

import java.net.http.HttpClient;
import java.util.function.Function;

/**
 * @author nedis
 * @since 0.8
 */
final class JdkHttpResponse implements ClientHttpResponse {

    private final java.net.http.HttpResponse<byte[]> response;

    private final Function<byte[], Object> responseBodyConverter;

    private JdkHttpHeaders jdkHttpHeaders;

    JdkHttpResponse(final java.net.http.HttpResponse<byte[]> response,
                    final Function<byte[], Object> responseBodyConverter) {
        this.response = response;
        this.responseBodyConverter = responseBodyConverter;
    }

    @Override
    public int getStatusCode() {
        return response.statusCode();
    }

    @Override
    public HttpVersion getVersion() {
        if (response.version() == HttpClient.Version.HTTP_1_1) {
            return HttpVersion.HTTP_1_1;
        } else if (response.version() == HttpClient.Version.HTTP_2) {
            return HttpVersion.HTTP_2;
        } else {
            throw new InvalidStateException("Unsupported HTTP version: ?", response.version());
        }
    }

    @Override
    public HttpHeaders getHeaders() {
        if (jdkHttpHeaders == null) {
            jdkHttpHeaders = new JdkHttpHeaders(response.headers());
        }
        return jdkHttpHeaders;
    }

    @Override
    public boolean isBodyEmpty() {
        return response.body().length == 0;
    }

    @Override
    public Object getBody() {
        return responseBodyConverter.apply(response.body());
    }

    @Override
    public byte[] getBodyAsBytes() {
        return response.body();
    }
}
