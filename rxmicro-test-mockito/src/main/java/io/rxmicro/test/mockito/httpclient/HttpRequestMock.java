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
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.Version;
import io.rxmicro.rest.model.HttpMethod;
import io.rxmicro.test.mockito.httpclient.internal.AbstractHttpRequestMock;

import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.common.util.UrlPaths.normalizeUrlPath;
import static io.rxmicro.http.HttpHeaders.EMPTY_HEADERS;
import static io.rxmicro.http.HttpStandardHeaderNames.API_VERSION;
import static io.rxmicro.rest.Version.Strategy.HEADER;
import static io.rxmicro.rest.Version.Strategy.URL_PATH;
import static java.util.Map.entry;

/**
 * The HTTP request mock using <a href="https://site.mockito.org/">Mockito</a> testing framework
 *
 * @author nedis
 * @since 0.1
 * @see HttpMethod
 * @see HttpHeaders
 * @see QueryParams
 * @see Version.Strategy
 */
public final class HttpRequestMock extends AbstractHttpRequestMock {

    private HttpRequestMock(final HttpMethod method,
                            final String path,
                            final HttpHeaders headers,
                            final QueryParams queryParameters,
                            final Object body,
                            final boolean any) {
        super(method, path, headers, queryParameters, body, any);
    }

    /**
     * The builder for building a HTTP request mock
     *
     * @author nedis
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private HttpMethod method;

        private String versionValue;

        private Version.Strategy versionStrategy = URL_PATH;

        private String path;

        private HttpHeaders headers = EMPTY_HEADERS;

        private QueryParams queryParameters;

        private Object body = EMPTY_BODY;

        private boolean anyMethod;

        private boolean anyPath;

        private boolean any;

        /**
         * Configures the HTTP request mock that it will match to any HTTP request
         *
         * @return the reference to this {@link Builder} instance
         */
        @BuilderMethod
        public Builder setAnyRequest() {
            this.method = null;
            this.versionValue = null;
            this.path = null;
            this.headers = null;
            this.queryParameters = null;
            this.body = null;
            this.any = true;
            this.anyMethod = true;
            this.anyPath = true;
            return this;
        }

        /**
         * Sets the {@link HttpMethod} for the HTTP request mock that it will match to an HTTP request with the specified {@link HttpMethod}
         *
         * @param method the specified {@link HttpMethod}
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified {@link HttpMethod} is {@code null}
         */
        @BuilderMethod
        public Builder setMethod(final HttpMethod method) {
            this.method = require(method);
            return this;
        }

        /**
         * Configures the HTTP request mock that it will match to a HTTP request with any {@link HttpMethod}
         *
         * @return the reference to this {@link Builder} instance
         */
        @BuilderMethod
        public Builder setAnyMethod() {
            this.method = null;
            this.anyMethod = true;
            return this;
        }

        /**
         * Sets the version value for the HTTP request mock that it will match to an HTTP request with the specified version value
         *
         * @param versionValue the specified version value
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified version value is {@code null}
         * @see Version
         * @see Version.Strategy
         */
        @BuilderMethod
        public Builder setVersionValue(final String versionValue) {
            this.versionValue = require(versionValue);
            return this;
        }

        /**
         * Sets the version {@link Version.Strategy} for the HTTP request mock that it will match to an HTTP request with
         * the specified version {@link Version.Strategy}
         *
         * @param versionStrategy the specified version {@link Version.Strategy}
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified {@link Version.Strategy} is {@code null}
         * @see Version
         */
        @BuilderMethod
        public Builder setVersionStrategy(final Version.Strategy versionStrategy) {
            this.versionStrategy = require(versionStrategy);
            return this;
        }

        /**
         * Sets the URL path for the HTTP request mock that it will match to an HTTP request with the specified URL path
         *
         * @param path the specified URL path
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified URL path is {@code null}
         */
        @BuilderMethod
        public Builder setPath(final String path) {
            this.path = normalizeUrlPath(path);
            return this;
        }

        /**
         * Configures the HTTP request mock that it will match to a HTTP request with any URL path
         *
         * @return the reference to this {@link Builder} instance
         */
        @BuilderMethod
        public Builder setAnyPath() {
            this.path = null;
            this.anyPath = true;
            return this;
        }

        /**
         * Sets the {@link HttpHeaders} for the HTTP request mock that it will match to an HTTP request with
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
         * Configures the HTTP request mock that it will match to a HTTP request with any {@link HttpHeaders}
         *
         * @return the reference to this {@link Builder} instance
         */
        @BuilderMethod
        public Builder setAnyHeaders() {
            this.headers = null;
            return this;
        }

        /**
         * Sets the {@link QueryParams} for the HTTP request mock that it will match to an HTTP request with
         * the specified {@link QueryParams}
         *
         * @param queryParameters the specified {@link QueryParams}
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified {@link QueryParams} is {@code null}
         */
        @BuilderMethod
        public Builder setQueryParameters(final QueryParams queryParameters) {
            this.queryParameters = require(queryParameters);
            return this;
        }

        /**
         * Configures the HTTP request mock that it will match to a HTTP request with any {@link QueryParams}
         *
         * @return the reference to this {@link Builder} instance
         */
        @BuilderMethod
        public Builder setAnyQueryParameters() {
            this.queryParameters = null;
            return this;
        }

        /**
         * Sets the HTTP body for the HTTP request mock that it will match to an HTTP request with the specified HTTP body
         *
         * @param body the specified HTTP body
         * @return the reference to this {@link Builder} instance
         * @throws NullPointerException if the specified HTTP body is {@code null}
         */
        @BuilderMethod
        public Builder setBody(final Object body) {
            this.body = require(body);
            return this;
        }

        /**
         * Configures the HTTP request mock that it will match to a HTTP request with any HTTP body
         *
         * @return the reference to this {@link Builder} instance
         */
        @BuilderMethod
        public Builder setAnyBody() {
            this.body = null;
            return this;
        }

        /**
         * Builds the immutable HTTP request mock instance using the configured {@link Builder} settings
         *
         * @return the immutable HTTP request mock instance
         * @throws InvalidStateException if the current {@link Builder} contains invalid settings
         */
        public HttpRequestMock build() {
            validate();
            final String validPath = getValidPath();
            final HttpHeaders validHeaders = getValidHeaders();
            return new HttpRequestMock(method, validPath, validHeaders, queryParameters, body, any);
        }

        private void validate() {
            if (method == null && !anyMethod) {
                throw new InvalidStateException("HTTP method' is required");
            }
            if (path == null && !anyPath) {
                throw new InvalidStateException("'URL Path' is required!");
            }
            if (versionValue != null) {
                if (versionStrategy == URL_PATH) {
                    if (path == null) {
                        throw new InvalidStateException("For URL Path version strategy, 'URL Path' is required!");
                    }
                } else {
                    if (headers == null) {
                        throw new InvalidStateException("For HEADER version strategy, 'setAnyHeaders()' is unsupported!");
                    }
                }
            }
        }

        private String getValidPath() {
            if (versionValue != null && versionStrategy == URL_PATH) {
                return normalizeUrlPath(versionValue + "/" + path);
            } else {
                return path;
            }
        }

        private HttpHeaders getValidHeaders() {
            if (versionValue != null && versionStrategy == HEADER) {
                if (headers.isNotEmpty()) {
                    return HttpHeaders.of(
                            Stream.concat(
                                    Stream.of(entry(API_VERSION, (Object) versionValue)),
                                    headers.getEntries().stream()
                                            .map(e -> entry(e.getKey(), (Object) e.getValue()))
                            ).toArray((IntFunction<Map.Entry<String, Object>[]>) Map.Entry[]::new)
                    );
                } else {
                    return HttpHeaders.of(API_VERSION, versionValue);
                }
            } else {
                return headers;
            }
        }
    }
}
