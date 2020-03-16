/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.annotation.processor.rest.model;

import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.rest.model.UrlSegments;

import java.lang.annotation.Annotation;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class HttpMethodMapping {

    private final String method;

    private final String uri;

    private final UrlSegments urlSegments;

    private final boolean httpBody;

    private final String versionHeaderValue;

    private HttpMethodMapping(final String method,
                              final String uri,
                              final UrlSegments urlSegments,
                              final boolean httpBody,
                              final String versionHeaderValue) {
        this.method = require(method);
        this.uri = uri;
        this.urlSegments = urlSegments;
        this.httpBody = httpBody;
        this.versionHeaderValue = versionHeaderValue;
    }

    public String getMethod() {
        return method;
    }

    public String getExactOrTemplateUri() {
        if (isUrlSegmentsPresent()) {
            return urlSegments.getOriginalUrl();
        } else {
            return uri;
        }
    }

    public String getUri() {
        return uri;
    }

    public boolean isHttpBody() {
        return httpBody;
    }

    public boolean isUrlSegmentsPresent() {
        return urlSegments != null;
    }

    public UrlSegments getUrlSegments() {
        return require(urlSegments);
    }

    @UsedByFreemarker("$$RestControllerTemplate.javaftl")
    public boolean isVersionHeaderValuePresent() {
        return versionHeaderValue != null;
    }

    @UsedByFreemarker("$$RestControllerTemplate.javaftl")
    public String getVersionHeaderValue() {
        return require(versionHeaderValue);
    }

    @Override
    public int hashCode() {
        int result = method.hashCode();
        result = 31 * result + uri.hashCode();
        result = 31 * result + (httpBody ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final HttpMethodMapping that = (HttpMethodMapping) o;
        if (httpBody != that.httpBody) return false;
        if (!method.equals(that.method)) return false;
        return uri.equals(that.uri);
    }

    @Override
    public String toString() {
        return "HttpMethodMapping{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", urlSegments=" + urlSegments +
                ", httpBody=" + httpBody +
                '}';
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private String method;

        private String uri;

        private UrlSegments urlSegments;

        private boolean httpBody;

        private String versionHeaderValue;

        public Builder setMethod(final Class<? extends Annotation> method) {
            this.method = require(method).getSimpleName();
            return this;
        }

        public Builder setUri(final String uri) {
            this.uri = require(uri);
            return this;
        }

        public Builder setUrlSegments(final UrlSegments urlSegments) {
            this.urlSegments = require(urlSegments);
            return this;
        }

        public Builder setHttpBody(final boolean httpBody) {
            this.httpBody = httpBody;
            return this;
        }

        public Builder setVersionHeaderValue(final String versionHeaderValue) {
            this.versionHeaderValue = require(versionHeaderValue);
            return this;
        }

        public HttpMethodMapping build() {
            return new HttpMethodMapping(method, uri, urlSegments, httpBody, versionHeaderValue);
        }
    }
}
