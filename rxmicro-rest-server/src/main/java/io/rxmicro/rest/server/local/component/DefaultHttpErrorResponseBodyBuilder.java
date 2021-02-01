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

package io.rxmicro.rest.server.local.component;

import io.rxmicro.http.error.HttpErrorException;
import io.rxmicro.rest.model.HttpCallFailedException;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.detail.model.HttpResponse;

import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_TYPE;

/**
 * @author nedis
 * @since 0.8
 */
public final class DefaultHttpErrorResponseBodyBuilder implements HttpErrorResponseBodyBuilder {

    private static final String DEFAULT_CONTENT_TYPE = "text/plain";

    @Override
    public HttpResponse build(final HttpResponseBuilder httpResponseBuilder,
                              final int status,
                              final String message) {
        final HttpResponse response = httpResponseBuilder.build();
        response.setStatus(status);
        response.setHeader(CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
        response.setContent(String.valueOf(message));
        return response;
    }

    @Override
    public HttpResponse build(final HttpResponseBuilder httpResponseBuilder,
                              final HttpErrorException exception) {
        final HttpResponse response = httpResponseBuilder.build();
        response.setStatus(exception.getStatusCode());
        if (exception.getMessage() != null) {
            response.setHeader(CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
            response.setContent(exception.getMessage());
        }
        return response;
    }

    @Override
    public HttpResponse build(final HttpResponseBuilder httpResponseBuilder,
                              final HttpCallFailedException exception) {
        final HttpResponse response = httpResponseBuilder.build(false);
        response.setStatus(exception.getStatusCode());
        response.setVersion(exception.getVersion());
        response.setOrAddHeaders(exception.getHeaders());
        if (exception.isBodyPresent()) {
            response.setContent(exception.getBody());
        }
        return response;
    }

    @Override
    public boolean isRxMicroError(final HttpCallFailedException exception) {
        return false;
    }
}
