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

package io.rxmicro.test.mockito.httpclient;

import io.rxmicro.common.InvalidStateException;
import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.test.mockito.httpclient.internal.AbstractHttpResponseMock;

import java.util.Optional;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.http.HttpHeaders.EMPTY_HEADERS;
import static io.rxmicro.http.HttpVersion.HTTP_1_1;

/**
 * The HTTP response mock using <a href="https://site.mockito.org/">Mockito</a> testing framework
 *
 * @author nedis
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
     * The builder for building a HTTP response mock
     *
     * @author nedis
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private static final int DEFAULT_STATUS_CODE = 200;

        private int status;

        private HttpVersion version;

        private HttpHeaders headers;

        private Object body;

        private boolean returnRequestBody;

        /**
         * Sets the status code for the HTTP response mock that it will match to an HTTP response with the specified status code
         *
         * @param status the specified status code
         * @return the reference to this {@link Builder} instance
         */
        @BuilderMethod
        public Builder setStatus(final int status) {
            this.status = status;
            return this;
        }

        /**
         * Sets the {@link HttpVersion} for the HTTP response mock that it will match to an HTTP response with
         * the specified {@link HttpVersion}
         *
         * @param version the specified {@link HttpVersion}
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified {@link HttpVersion} is {@code null}
         */
        @BuilderMethod
        public Builder setVersion(final HttpVersion version) {
            this.version = require(version);
            return this;
        }

        /**
         * Sets the {@link HttpHeaders} for the HTTP response mock that it will match to an HTTP response with
         * the specified {@link HttpHeaders}
         *
         * @param headers the specified {@link HttpHeaders}
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified {@link HttpHeaders} is {@code null}
         */
        @BuilderMethod
        public Builder setHeaders(final HttpHeaders headers) {
            this.headers = require(headers);
            return this;
        }

        /**
         * Sets the HTTP body for the HTTP response mock that it will match to an HTTP response with the specified HTTP body
         *
         * @param body the specified HTTP body
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified HTTP body is {@code null}
         * @throws IllegalArgumentException if the specified HTTP body is instance of {@link Throwable}
         * @throws InvalidStateException if {@code returnRequestBody} setting is enabled
         */
        @BuilderMethod
        public Builder setBody(final Object body) {
            if (body instanceof Throwable) {
                throw new IllegalArgumentException("Throwable couldn't be a HTTP body");
            } else if (returnRequestBody) {
                throw new InvalidStateException("Body populated automatically, because 'returnRequestBody' property is set!");
            }
            this.body = require(body);
            return this;
        }

        /**
         * Configures the HTTP response mock with the HTTP body which must be extracted from HTTP request
         *
         * @return the reference to this {@link Builder} instance
         * @throws InvalidStateException if HTTP body is set previously
         */
        @BuilderMethod
        public Builder setReturnRequestBody() {
            if (body != null) {
                throw new InvalidStateException("Remove the body for mock response!");
            }
            this.returnRequestBody = true;
            return this;
        }

        /**
         * Builds the immutable HTTP response mock instance using the configured {@link Builder} settings
         *
         * @return the immutable HTTP response mock instance
         */
        public HttpResponseMock build() {
            return new HttpResponseMock(
                    status == 0 ? DEFAULT_STATUS_CODE : status,
                    Optional.ofNullable(version).orElse(HTTP_1_1),
                    Optional.ofNullable(headers).orElse(EMPTY_HEADERS),
                    body,
                    returnRequestBody
            );
        }
    }
}
