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

package io.rxmicro.rest.server.detail.component;

import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.RequestMappingRule;

import java.util.List;
import java.util.concurrent.CompletionStage;

import static io.rxmicro.common.util.Requires.require;
import static java.util.concurrent.CompletableFuture.completedStage;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.3
 */
public final class BadHttpRequestRestController extends AbstractRestController {

    private static final int DEFAULT_BAD_REQUEST_STATUS_CODE = 400;

    private final RequestMappingRule requestMappingRule;

    private CompletionStage<HttpResponse> badHttpRequestStage;

    public BadHttpRequestRestController(final RequestMappingRule requestMappingRule) {
        this.requestMappingRule = require(requestMappingRule);
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "",
                        "handle",
                        List.of(PathVariableMapping.class, HttpRequest.class),
                        this::handle,
                        false,
                        requestMappingRule
                )
        );
    }

    @Override
    protected void postConstruct() {
        badHttpRequestStage = completedStage(
                httpErrorResponseBodyBuilder.build(
                        httpResponseBuilder,
                        DEFAULT_BAD_REQUEST_STATUS_CODE,
                        "Current message is not a HTTP request!"
                )
        );
    }

    @Override
    public Class<?> getRestControllerClass() {
        return BadHttpRequestRestController.class;
    }

    private CompletionStage<HttpResponse> handle(final PathVariableMapping pathVariableMapping,
                                                 final HttpRequest request) {
        return badHttpRequestStage;
    }
}
