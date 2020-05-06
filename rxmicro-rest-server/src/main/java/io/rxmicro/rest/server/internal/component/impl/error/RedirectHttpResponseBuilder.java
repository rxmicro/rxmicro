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

package io.rxmicro.rest.server.internal.component.impl.error;

import io.rxmicro.common.InvalidStateException;
import io.rxmicro.http.error.RedirectException;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.detail.model.HttpResponse;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.http.HttpHeaders.LOCATION;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class RedirectHttpResponseBuilder {

    private final HttpResponseBuilder httpResponseBuilder;

    private final String parentUrl;

    private final boolean parentUrlNotFound;

    public RedirectHttpResponseBuilder(final HttpResponseBuilder httpResponseBuilder,
                                       final String parentUrl) {
        this.httpResponseBuilder = require(httpResponseBuilder);
        this.parentUrl = require(parentUrl);
        this.parentUrlNotFound = this.parentUrl.isEmpty() || "/".equals(this.parentUrl);
    }

    public HttpResponse build(final Throwable th) {
        if (th instanceof RedirectException) {
            return build((RedirectException) th);
        } else {
            throw new InvalidStateException(
                    "Class '?' must extend '?' class!", th.getClass().getName(), RedirectException.class.getName()
            );
        }
    }

    private HttpResponse build(final RedirectException redirectException) {
        final HttpResponse response = httpResponseBuilder.build();
        response.setStatus(redirectException.getStatusCode());
        if (parentUrlNotFound || redirectException.isAbsolute()) {
            response.setHeader(LOCATION, redirectException.getLocation());
        } else {
            response.setHeader(LOCATION, getFullLocationUrl(redirectException.getLocation()));
        }
        return response;
    }

    private String getFullLocationUrl(final String location) {
        if (location.startsWith(parentUrl)) {
            return location;
        } else {
            return parentUrl + location;
        }
    }
}
