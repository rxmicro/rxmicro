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

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.detail.model.HttpHealthCheckRegistration;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletionStage;

import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static io.rxmicro.config.Configs.getConfig;
import static java.util.concurrent.CompletableFuture.completedStage;
import static java.util.stream.Collectors.toList;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public final class HttpHealthCheckRestController extends AbstractRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpHealthCheckRestController.class);

    private final Set<HttpHealthCheckRegistration> httpHealthCheckRegistrations;

    private final RestServerConfig restServerConfig;

    public HttpHealthCheckRestController(final HttpHealthCheckRegistration... httpHealthCheckRegistrations) {
        this.httpHealthCheckRegistrations = Set.of(httpHealthCheckRegistrations);
        this.restServerConfig = getConfig(RestServerConfig.class);
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        EMPTY_STRING,
                        "handle",
                        List.of(PathVariableMapping.class, HttpRequest.class),
                        this::handle,
                        false,
                        httpHealthCheckRegistrations.stream()
                                .map(r -> new ExactUrlRequestMappingRule(r.getMethod(), r.getEndpoint(), false))
                                .collect(toList())
                )
        );
    }

    @Override
    public Class<?> getRestControllerClass() {
        return HttpHealthCheckRestController.class;
    }

    private CompletionStage<HttpResponse> handle(final PathVariableMapping pathVariableMapping,
                                                 final HttpRequest request) {
        if (!restServerConfig.isDisableLoggerMessagesForHttpHealthChecks()) {
            LOGGER.trace(request, "http health check successful");
        }
        return completedStage(httpResponseBuilder.build());
    }
}
