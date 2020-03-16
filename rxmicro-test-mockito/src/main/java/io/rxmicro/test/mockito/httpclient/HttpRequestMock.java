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
import static io.rxmicro.http.HttpHeaders.API_VERSION;
import static io.rxmicro.http.HttpHeaders.EMPTY_HEADERS;
import static io.rxmicro.rest.Version.Strategy.HEADER;
import static io.rxmicro.rest.Version.Strategy.URL_PATH;
import static java.util.Map.entry;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
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
     * @author nedis
     * @link https://rxmicro.io
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

        public Builder setMethod(final HttpMethod method) {
            this.method = require(method);
            return this;
        }

        public Builder setAnyMethod() {
            this.method = null;
            this.anyMethod = true;
            return this;
        }

        public Builder setVersionValue(final String versionValue) {
            this.versionValue = require(versionValue);
            return this;
        }

        public Builder setVersionStrategy(final Version.Strategy versionStrategy) {
            this.versionStrategy = require(versionStrategy);
            return this;
        }

        public Builder setPath(final String path) {
            this.path = normalizeUrlPath(path);
            return this;
        }

        public Builder setAnyPath() {
            this.path = null;
            this.anyPath = true;
            return this;
        }

        public Builder setHeaders(final HttpHeaders headers) {
            this.headers = require(headers);
            return this;
        }

        public Builder setAnyHeaders() {
            this.headers = null;
            return this;
        }

        public Builder setQueryParameters(final QueryParams queryParameters) {
            this.queryParameters = require(queryParameters);
            return this;
        }

        public Builder setAnyQueryParameters() {
            this.queryParameters = null;
            return this;
        }

        public Builder setBody(final Object body) {
            this.body = require(body);
            return this;
        }

        public Builder setAnyBody() {
            this.body = null;
            return this;
        }


        public HttpRequestMock build() {
            validate();
            final String validPath = getValidPath();
            final HttpHeaders validHeaders = getValidHeaders();
            return new HttpRequestMock(method, validPath, validHeaders, queryParameters, body, any);
        }

        private void validate() {
            if (method == null && !anyMethod) {
                throw new IllegalStateException("HTTP method' is required");
            }
            if (path == null && !anyPath) {
                throw new IllegalStateException("'URL Path' is required!");
            }
            if (versionValue != null) {
                if (versionStrategy == URL_PATH) {
                    if (path == null) {
                        throw new IllegalStateException("For URL Path version strategy, 'URL Path' is required!");
                    }
                } else {
                    if (headers == null) {
                        throw new IllegalStateException("For HEADER version strategy, 'setAnyHeaders()' is unsupported!");
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
