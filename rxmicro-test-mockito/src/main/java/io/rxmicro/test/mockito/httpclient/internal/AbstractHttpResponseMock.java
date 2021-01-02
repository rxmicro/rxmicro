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
import io.rxmicro.http.HttpVersion;
import io.rxmicro.rest.client.detail.HttpResponse;
import io.rxmicro.test.mockito.httpclient.internal.model.HttpResponseImpl;

import java.util.Optional;

import static io.rxmicro.http.HttpHeaders.EMPTY_HEADERS;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractHttpResponseMock {

    private final int status;

    private final HttpVersion version;

    private final HttpHeaders headers;

    private final Object body;

    private final boolean returnRequestBody;

    protected AbstractHttpResponseMock(final int status,
                                       final HttpVersion version,
                                       final HttpHeaders headers,
                                       final Object body,
                                       final boolean returnRequestBody) {
        this.status = status;
        this.version = version;
        this.headers = headers;
        this.body = body;
        this.returnRequestBody = returnRequestBody;
    }

    protected boolean isReturnRequestBody() {
        return returnRequestBody;
    }

    protected HttpResponse getClientHttpResponse() {
        if (body == null) {
            return new HttpResponseImpl(status, version, Optional.ofNullable(headers).orElse(EMPTY_HEADERS));
        } else {
            return new HttpResponseImpl(status, version, Optional.ofNullable(headers).orElse(EMPTY_HEADERS), body);
        }
    }

    protected HttpResponse getClientHttpResponse(final Object body) {
        return new HttpResponseImpl(status, version, Optional.ofNullable(headers).orElse(EMPTY_HEADERS), body);
    }
}
