/*
 * Copyright (c) 2020 https://rxmicro.io
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

import io.rxmicro.config.ConfigException;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.PathMatcherResult;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;
import io.rxmicro.rest.server.detail.model.mapping.RequestMappingRule;
import io.rxmicro.rest.server.detail.model.mapping.UrlTemplateRequestMappingRule;
import io.rxmicro.rest.server.internal.component.ComponentResolver;
import io.rxmicro.rest.server.internal.component.RequestMappingKeyBuilder;
import io.rxmicro.rest.server.local.component.DynamicRestControllerRegistrar;
import io.rxmicro.rest.server.local.component.RequestHandler;
import io.rxmicro.rest.server.local.model.RestControllerMethod;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static io.rxmicro.rest.model.HttpMethod.OPTIONS;
import static io.rxmicro.rest.model.PathVariableMapping.NO_PATH_VARIABLES;
import static java.util.Collections.unmodifiableSet;
import static java.util.Map.entry;
import static java.util.concurrent.CompletableFuture.completedStage;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Router implements DynamicRestControllerRegistrar, RequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Router.class);

    private final Map<String, RestControllerMethod> exactUrlRestControllerMap =
            new ConcurrentHashMap<>();

    private final List<Map.Entry<UrlTemplateRequestMappingRule, RestControllerMethod>> urlTemplateRestControllers =
            new CopyOnWriteArrayList<>();

    private final Set<AbstractRestController> registeredRestControllers = new HashSet<>();

    private final Set<RequestMappingRule> registeredRequestMappingRules = new HashSet<>();

    private final ComponentResolver componentResolver;

    private final CompletionStage<HttpResponse> handlerNotFoundStage;

    private final CompletionStage<HttpResponse> corsNotAllowedStage;

    private final RequestMappingKeyBuilder requestMappingKeyBuilder;

    public Router(final ComponentResolver componentResolver) {
        this.componentResolver = componentResolver;
        this.requestMappingKeyBuilder = componentResolver.getRequestMappingKeyBuilder();
        this.handlerNotFoundStage = completedStage(
                componentResolver.getHttpErrorResponseBodyBuilder().build(
                        componentResolver.getHttpResponseBuilder().build(),
                        componentResolver.getRestServerConfig().getHandlerNotFoundErrorStatusCode(),
                        componentResolver.getRestServerConfig().getHandlerNotFoundErrorMessage()
                )
        );
        this.corsNotAllowedStage = completedStage(
                componentResolver.getHttpErrorResponseBodyBuilder().build(
                        componentResolver.getHttpResponseBuilder().build(),
                        componentResolver.getRestServerConfig().getCorsNotAllowedErrorStatusCode(),
                        componentResolver.getRestServerConfig().getCorsNotAllowedErrorMessage()
                )
        );
    }

    @Override
    public void register(final AbstractRestController restController,
                         final Registration... registrations) {
        if (registrations.length == 0) {
            throw new ConfigException("'?' does not have request mapping",
                    restController.getRestControllerClass());
        }
        if (registeredRestControllers.contains(restController)) {
            throw new ConfigException("'?' already registered",
                    restController.getRestControllerClass());
        }
        injectDependencies(restController);
        registeredRestControllers.add(restController);
        addRegistrations(restController, registrations);
    }

    private <S, D> void injectDependencies(final BaseRestController restController) {
        restController.httpResponseBuilder = componentResolver.getHttpResponseBuilder();
        restController.restServerConfig = componentResolver.getRestServerConfig();
        restController.httpErrorResponseBodyBuilder = componentResolver.getHttpErrorResponseBodyBuilder();
        restController.postConstruct();
    }

    private void addRegistrations(final AbstractRestController restController,
                                  final Registration... registrations) {
        Arrays.stream(registrations).forEach(registration -> {
            final RestControllerMethod method = new RestControllerMethod(
                    registration.getParentUrl(),
                    restController,
                    registration.getMethodName(),
                    registration.getMethod(),
                    registration.isCorsRequestPossible()
            );
            registration.getRequestMappingRules().forEach(requestMapping -> {
                if (!registeredRequestMappingRules.add(requestMapping)) {
                    throw new ConfigException("Request mapping '?' not unique", requestMapping);
                }
                if (requestMapping.isExactUrlRequestMappingRule()) {
                    final String requestMappingKey = requestMappingKeyBuilder.build(
                            (ExactUrlRequestMappingRule) requestMapping
                    );
                    exactUrlRestControllerMap.put(requestMappingKey, method);
                } else {
                    urlTemplateRestControllers.add(
                            entry((UrlTemplateRequestMappingRule) requestMapping, method));
                }
                LOGGER.debug("Mapped ? onto ?", () -> requestMapping, method::toString);
            });
        });
    }

    @Override
    public void clear() {
        exactUrlRestControllerMap.clear();
        registeredRestControllers.clear();
        registeredRequestMappingRules.clear();
    }

    @Override
    public Set<AbstractRestController> registeredRestControllers() {
        return unmodifiableSet(registeredRestControllers);
    }

    @Override
    public CompletionStage<HttpResponse> handle(final HttpRequest request) {
        final String requestMappingKey = requestMappingKeyBuilder.build(request);
        RestControllerMethod restControllerMethod = exactUrlRestControllerMap.get(requestMappingKey);
        if (restControllerMethod != null) {
            return restControllerMethod.call(NO_PATH_VARIABLES, request);
        }
        for (final var entry : urlTemplateRestControllers) {
            final PathMatcherResult match = entry.getKey().match(request);
            if (match.matches()) {
                restControllerMethod = entry.getValue();
                return restControllerMethod.call(newPathVariableMapping(entry, match), request);
            }
        }
        if (OPTIONS.name().equals(request.getMethod())) {
            LOGGER.error("CORS not allowed: Handler not found: ?", requestMappingKey);
            return corsNotAllowedStage;
        } else {
            LOGGER.error("Handler not found: ?", requestMappingKey);
            return handlerNotFoundStage;
        }
    }

    private PathVariableMapping newPathVariableMapping(
            final Map.Entry<UrlTemplateRequestMappingRule, RestControllerMethod> entry,
            final PathMatcherResult match) {
        return new PathVariableMapping(
                entry.getKey().getVariables(),
                match.getExtractedVariableValues()
        );
    }
}
