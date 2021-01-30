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

import io.rxmicro.http.error.HttpErrorException;
import io.rxmicro.logger.RequestIdSupplier;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.feature.ErrorHandler;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class AnyHttpErrorHttpResponseBuilder extends ErrorHandler {

    private final HttpResponseBuilder httpResponseBuilder;

    private final HttpErrorResponseBodyBuilder httpErrorResponseBodyBuilder;

    private final boolean logHttpErrorExceptions;

    public AnyHttpErrorHttpResponseBuilder(final HttpResponseBuilder httpResponseBuilder,
                                           final HttpErrorResponseBodyBuilder httpErrorResponseBodyBuilder,
                                           final boolean logHttpErrorExceptions) {
        this.httpResponseBuilder = require(httpResponseBuilder);
        this.httpErrorResponseBodyBuilder = require(httpErrorResponseBodyBuilder);
        this.logHttpErrorExceptions = logHttpErrorExceptions;
    }

    public HttpResponse build(final RequestIdSupplier requestIdSupplier,
                              final HttpErrorException ex) {
        if (logHttpErrorExceptions) {
            LOGGER.error(
                    requestIdSupplier,
                    "HTTP error: status=?, headers=?, content=?, class=?",
                    ex.getStatusCode(),
                    ex.getResponseHeaders(),
                    ex.getResponseBody(),
                    ex.getClass().getName()
            );
        }
        return httpErrorResponseBodyBuilder.build(httpResponseBuilder, ex);
    }
}
