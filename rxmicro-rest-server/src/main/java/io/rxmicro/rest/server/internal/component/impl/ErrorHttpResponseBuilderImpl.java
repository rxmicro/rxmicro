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

package io.rxmicro.rest.server.internal.component.impl;

import io.rxmicro.http.error.HttpErrorException;
import io.rxmicro.logger.RequestIdSupplier;
import io.rxmicro.rest.model.HttpCallFailedException;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.internal.component.ErrorHttpResponseBuilder;
import io.rxmicro.rest.server.internal.component.impl.error.AnyHttpErrorHttpResponseBuilder;
import io.rxmicro.rest.server.internal.component.impl.error.HttpCallFailedHttpResponseBuilder;
import io.rxmicro.rest.server.internal.component.impl.error.RedirectHttpResponseBuilder;
import io.rxmicro.rest.server.internal.component.impl.error.ThrowableHttpResponseBuilder;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;

import static io.rxmicro.common.util.Exceptions.getRealThrowable;

/**
 * @author nedis
 * @since 0.1
 */
public final class ErrorHttpResponseBuilderImpl implements ErrorHttpResponseBuilder {

    private final RedirectHttpResponseBuilder redirectHttpResponseBuilder;

    private final HttpCallFailedHttpResponseBuilder httpCallFailedHttpResponseBuilder;

    private final AnyHttpErrorHttpResponseBuilder anyHttpErrorHttpResponseBuilder;

    private final ThrowableHttpResponseBuilder throwableHttpResponseBuilder;

    public ErrorHttpResponseBuilderImpl(final HttpResponseBuilder httpResponseBuilder,
                                        final HttpErrorResponseBodyBuilder httpErrorResponseBodyBuilder,
                                        final String parentUrl,
                                        final RestServerConfig restServerConfig) {
        this.redirectHttpResponseBuilder =
                new RedirectHttpResponseBuilder(httpResponseBuilder, parentUrl);
        this.httpCallFailedHttpResponseBuilder =
                new HttpCallFailedHttpResponseBuilder(httpResponseBuilder, httpErrorResponseBodyBuilder, restServerConfig);
        this.anyHttpErrorHttpResponseBuilder =
                new AnyHttpErrorHttpResponseBuilder(httpResponseBuilder, httpErrorResponseBodyBuilder, restServerConfig);
        this.throwableHttpResponseBuilder =
                new ThrowableHttpResponseBuilder(httpResponseBuilder, httpErrorResponseBodyBuilder, restServerConfig);
    }

    @Override
    public HttpResponse build(final RequestIdSupplier requestIdSupplier,
                              final Throwable throwable) {
        final Throwable th = getRealThrowable(throwable);
        if (th instanceof HttpErrorException) {
            final HttpErrorException exception = (HttpErrorException) th;
            if (exception.isRedirectCode()) {
                return redirectHttpResponseBuilder.build(exception);
            } else if (exception instanceof HttpCallFailedException) {
                return httpCallFailedHttpResponseBuilder.build(requestIdSupplier, (HttpCallFailedException) exception);
            } else {
                return anyHttpErrorHttpResponseBuilder.build(requestIdSupplier, exception);
            }
        } else {
            return throwableHttpResponseBuilder.build(requestIdSupplier, th);
        }
    }
}
