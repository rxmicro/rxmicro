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

package io.rxmicro.rest.server.internal;

import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.internal.component.ErrorHttpResponseBuilder;
import io.rxmicro.rest.server.internal.component.impl.ErrorHttpResponseBuilderImpl;

import java.util.concurrent.CompletionStage;

import static io.rxmicro.http.HttpStandardHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN;
import static io.rxmicro.http.HttpStandardHeaderNames.ORIGIN;
import static java.util.concurrent.CompletableFuture.completedStage;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class BaseRestControllerMethod {

    private final BaseRestController baseRestController;

    private final boolean corsRequestPossible;

    private final ErrorHttpResponseBuilder errorHttpResponseBuilder;

    protected BaseRestControllerMethod(final String parentUrl,
                                       final BaseRestController baseRestController,
                                       final boolean corsRequestPossible) {
        this.baseRestController = baseRestController;
        this.corsRequestPossible = corsRequestPossible;
        this.errorHttpResponseBuilder = new ErrorHttpResponseBuilderImpl(
                baseRestController.httpResponseBuilder,
                baseRestController.httpErrorResponseBodyBuilder,
                parentUrl,
                baseRestController.restServerConfig
        );
    }

    public BaseRestController getRestController() {
        return baseRestController;
    }

    protected final CompletionStage<HttpResponse> call(final PathVariableMapping pathVariableMapping,
                                                       final HttpRequest request) {
        final CompletionStage<HttpResponse> response = invokeAndHandlePossibleErrors(pathVariableMapping, request);
        return decorateWithCorsIfNecessary(request, response);
    }

    protected abstract CompletionStage<HttpResponse> invoke(PathVariableMapping pathVariableMapping,
                                                            HttpRequest request);

    private CompletionStage<HttpResponse> invokeAndHandlePossibleErrors(final PathVariableMapping pathVariableMapping,
                                                                        final HttpRequest request) {
        try {
            return invoke(pathVariableMapping, request)
                    .exceptionally(throwable -> errorHttpResponseBuilder.build(request, throwable));
        } catch (final Throwable th) {
            return completedStage(errorHttpResponseBuilder.build(request, th));
        }
    }

    private CompletionStage<HttpResponse> decorateWithCorsIfNecessary(final HttpRequest request,
                                                                      final CompletionStage<HttpResponse> response) {
        final String origin = request.getHeaders().getValue(ORIGIN);
        if (corsRequestPossible && origin != null) {
            return response.whenComplete((resp, th) ->
                    resp.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, origin));
        } else {
            return response;
        }
    }
}
