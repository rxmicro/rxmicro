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
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;

import java.util.Objects;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.http.HttpStatuses.getErrorMessage;

/**
 * @author nedis
 * @since 0.1
 */
public final class AnyHttpErrorHttpResponseBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnyHttpErrorHttpResponseBuilder.class);

    private final HttpResponseBuilder httpResponseBuilder;

    private final HttpErrorResponseBodyBuilder httpErrorResponseBodyBuilder;

    private final boolean hideInternalErrorMessage;

    private final boolean logNotServerErrors;

    public AnyHttpErrorHttpResponseBuilder(final HttpResponseBuilder httpResponseBuilder,
                                           final HttpErrorResponseBodyBuilder httpErrorResponseBodyBuilder,
                                           final boolean hideInternalErrorMessage,
                                           final boolean logNotServerErrors) {
        this.httpResponseBuilder = require(httpResponseBuilder);
        this.httpErrorResponseBodyBuilder = require(httpErrorResponseBodyBuilder);
        this.hideInternalErrorMessage = hideInternalErrorMessage;
        this.logNotServerErrors = logNotServerErrors;
    }

    public HttpResponse build(final HttpErrorException ex) {
        if (ex.isServerErrorCode()) {
            LOGGER.error("HTTP server error: ?", ex.getMessage());
            if (hideInternalErrorMessage) {
                return httpErrorResponseBodyBuilder.build(httpResponseBuilder, ex.getStatusCode(), getErrorMessage(ex.getStatusCode()));
            } else {
                return httpErrorResponseBodyBuilder.build(httpResponseBuilder, ex);
            }
        } else {
            if (logNotServerErrors) {
                LOGGER.error(
                        "HTTP error: status=?, content=?, class=?",
                        ex.getStatusCode(),
                        ex.getResponseData().map(Objects::toString).orElse("null"),
                        ex.getClass().getName()
                );
            }
            return httpErrorResponseBodyBuilder.build(httpResponseBuilder, ex);
        }
    }
}
