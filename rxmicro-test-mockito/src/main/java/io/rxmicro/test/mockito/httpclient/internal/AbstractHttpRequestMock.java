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

package io.rxmicro.test.mockito.httpclient.internal;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpMethod;

import java.util.Optional;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractHttpRequestMock {

    protected static final Object EMPTY_BODY = new Object();

    private final HttpMethod method;

    private final String path;

    private final HttpHeaders headers;

    private final QueryParams queryParameters;

    private final Object body;

    private final boolean any;

    protected AbstractHttpRequestMock(final HttpMethod method,
                                      final String path,
                                      final HttpHeaders headers,
                                      final QueryParams queryParameters,
                                      final Object body,
                                      final boolean any) {
        this.method = method;
        this.path = path;
        this.headers = headers;
        this.queryParameters = queryParameters;
        this.body = body;
        this.any = any;
    }

    protected Optional<HttpMethod> getMethod() {
        return Optional.ofNullable(method);
    }

    protected Optional<String> getPath() {
        return Optional.ofNullable(path);
    }

    protected Optional<HttpHeaders> getHeaders() {
        return Optional.ofNullable(headers);
    }

    protected Optional<QueryParams> getQueryParameters() {
        return Optional.ofNullable(queryParameters);
    }

    protected Optional<Object> getBody() {
        return Optional.ofNullable(body).filter(b -> !EMPTY_BODY.equals(b));
    }

    protected boolean isBodyPresent() {
        return !EMPTY_BODY.equals(body);
    }

    protected boolean isAny() {
        return any;
    }
}
