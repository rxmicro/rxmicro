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

import io.rxmicro.logger.RequestIdSupplier;
import io.rxmicro.rest.model.HttpCallFailedException;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.feature.ErrorHandler;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.http.HttpStatuses.getErrorMessage;

/**
 * @author nedis
 * @since 0.1
 */
public final class HttpCallFailedHttpResponseBuilder extends ErrorHandler {

    private final HttpResponseBuilder httpResponseBuilder;

    private final HttpErrorResponseBodyBuilder httpErrorResponseBodyBuilder;

    private final RestServerConfig restServerConfig;

    public HttpCallFailedHttpResponseBuilder(final HttpResponseBuilder httpResponseBuilder,
                                             final HttpErrorResponseBodyBuilder httpErrorResponseBodyBuilder,
                                             final RestServerConfig restServerConfig) {
        this.httpResponseBuilder = require(httpResponseBuilder);
        this.httpErrorResponseBodyBuilder = require(httpErrorResponseBodyBuilder);
        this.restServerConfig = restServerConfig;
    }

    public HttpResponse build(final RequestIdSupplier requestIdSupplier,
                              final HttpCallFailedException exception) {
        if (exception.isServerErrorCode()) {
            LOGGER.error(requestIdSupplier, "Http call failed: ?", exception.getMessage());
        }
        if (httpErrorResponseBodyBuilder.isRxMicroError(exception)) {
            return httpErrorResponseBodyBuilder.build(httpResponseBuilder, exception);
        } else {
            final String message;
            if (restServerConfig.isHideInternalErrorMessage() && exception.isServerErrorCode()) {
                message = getErrorMessage(exception.getStatusCode());
            } else {
                message = exception.getMessage();
            }
            return httpErrorResponseBodyBuilder.build(httpResponseBuilder, exception.getStatusCode(), message);
        }
    }
}
