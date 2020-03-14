/*
 * Copyright (c) 2020. http://rxmicro.io
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
import io.rxmicro.rest.server.detail.model.HttpHealthCheckRegistration;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;
import io.rxmicro.rest.server.detail.model.mapping.RequestMappingRule;

import java.util.Set;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedStage;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class HttpHealthCheckRestController extends AbstractRestController {

    private final Set<HttpHealthCheckRegistration> httpHealthCheckRegistrations;

    public HttpHealthCheckRestController(final HttpHealthCheckRegistration... httpHealthCheckRegistrations) {
        this.httpHealthCheckRegistrations = Set.of(httpHealthCheckRegistrations);
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "",
                        "handle(PathVariableMapping, HttpRequest)",
                        this::handle,
                        false,
                        httpHealthCheckRegistrations.stream()
                                .map(r -> new ExactUrlRequestMappingRule(r.getMethod(), r.getEndpoint(), false))
                                .toArray(RequestMappingRule[]::new)
                )
        );
    }

    @Override
    public Class<?> getRestControllerClass() {
        return HttpHealthCheckRestController.class;
    }

    private CompletionStage<HttpResponse> handle(final PathVariableMapping pathVariableMapping,
                                                 final HttpRequest request) {
        return completedStage(httpResponseBuilder.build());
    }
}
