/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.test.mockito.httpclient;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.test.mockito.httpclient.internal.AbstractHttpResponseMock;

import java.util.Optional;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.http.HttpHeaders.EMPTY_HEADERS;
import static io.rxmicro.http.HttpVersion.HTTP_1_1;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class HttpResponseMock extends AbstractHttpResponseMock {

    private HttpResponseMock(final int status,
                             final HttpVersion version,
                             final HttpHeaders headers,
                             final Object body,
                             final boolean returnRequestBody) {
        super(status, version, headers, body, returnRequestBody);
    }

    /**
     * @author nedis
     * @link http://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private int status;

        private HttpVersion version;

        private HttpHeaders headers;

        private Object body;

        private boolean returnRequestBody;

        public Builder setStatus(final int status) {
            this.status = status;
            return this;
        }

        public Builder setVersion(final HttpVersion version) {
            this.version = require(version);
            return this;
        }

        public Builder setHeaders(final HttpHeaders headers) {
            this.headers = require(headers);
            return this;
        }

        public Builder setBody(final Object body) {
            if (body instanceof Throwable) {
                throw new IllegalArgumentException("Throwable couldn't be a HTTP body");
            } else if (returnRequestBody) {
                throw new IllegalStateException("Body populated automatically, because 'returnRequestBody' property is set!");
            }
            this.body = require(body);
            return this;
        }

        public Builder setReturnRequestBody() {
            if (body != null) {
                throw new IllegalStateException("Remove the body for mock response!");
            }
            this.returnRequestBody = true;
            return this;
        }

        public HttpResponseMock build() {
            return new HttpResponseMock(
                    status == 0 ? 200 : status,
                    Optional.ofNullable(version).orElse(HTTP_1_1),
                    Optional.ofNullable(headers).orElse(EMPTY_HEADERS),
                    body,
                    returnRequestBody
            );
        }
    }
}
