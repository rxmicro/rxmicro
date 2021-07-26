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

import io.rxmicro.config.ConfigException;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.PathMatcherResult;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.AnyPathFragmentRequestMappingRule;
import io.rxmicro.rest.server.detail.model.mapping.WithUrlPathVariablesRequestMappingRule;
import io.rxmicro.rest.server.internal.component.ComponentResolver;
import io.rxmicro.rest.server.internal.component.RequestMappingKeyBuilder;
import io.rxmicro.rest.server.internal.component.RequestMappingRuleVerifier;
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
 * @since 0.1
 */
public final class Router implements DynamicRestControllerRegistrar, RequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Router.class);

    private final Map<String, RestControllerMethod> exactUrlRestControllerMap =
            new ConcurrentHashMap<>();

    private final List<Map.Entry<WithUrlPathVariablesRequestMappingRule, RestControllerMethod>> urlTemplateRestControllers =
            new CopyOnWriteArrayList<>();

    private final List<Map.Entry<AnyPathFragmentRequestMappingRule, RestControllerMethod>> staticResourceTemplateRestControllers =
            new CopyOnWriteArrayList<>();

    private final Set<AbstractRestController> registeredRestControllers = new HashSet<>();

    private final RequestMappingRuleVerifier requestMappingRuleVerifier;

    private final ComponentResolver componentResolver;

    private final RestServerConfig restServerConfig;

    private final CompletionStage<HttpResponse> handlerNotFoundStage;

    private final CompletionStage<HttpResponse> corsNotAllowedStage;

    private final RequestMappingKeyBuilder requestMappingKeyBuilder;

    public Router(final ComponentResolver componentResolver) {
        this.componentResolver = componentResolver;
        this.restServerConfig = componentResolver.getRestServerConfig();
        this.requestMappingKeyBuilder = componentResolver.getRequestMappingKeyBuilder();
        this.requestMappingRuleVerifier = new RequestMappingRuleVerifier(requestMappingKeyBuilder);
        this.handlerNotFoundStage = completedStage(
                componentResolver.getHttpErrorResponseBodyBuilder().build(
                        componentResolver.getHttpResponseBuilder(),
                        componentResolver.getRestServerConfig().getHandlerNotFoundErrorStatusCode(),
                        componentResolver.getRestServerConfig().getHandlerNotFoundErrorMessage()
                )
        );
        this.corsNotAllowedStage = completedStage(
                componentResolver.getHttpErrorResponseBodyBuilder().build(
                        componentResolver.getHttpResponseBuilder(),
                        componentResolver.getRestServerConfig().getCorsNotAllowedErrorStatusCode(),
                        componentResolver.getRestServerConfig().getCorsNotAllowedErrorMessage()
                )
        );
    }

    @Override
    public void register(final AbstractRestController restController,
                         final Registration... registrations) {
        if (registrations.length == 0) {
            throw new ConfigException("'?' does not have request mapping", restController.getRestControllerClass());
        }
        if (registeredRestControllers.contains(restController)) {
            throw new ConfigException("'?' already registered", restController.getRestControllerClass());
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
            final RestControllerMethod method = new RestControllerMethod(registration, restController);
            registration.getRequestMappingRules().forEach(requestMapping -> {
                requestMappingRuleVerifier.verifyThatRequestMappingRuleIsUnique(requestMapping, method);
                if (requestMapping instanceof WithUrlPathVariablesRequestMappingRule) {
                    urlTemplateRestControllers.add(entry((WithUrlPathVariablesRequestMappingRule) requestMapping, method));
                } else if (requestMapping instanceof AnyPathFragmentRequestMappingRule) {
                    staticResourceTemplateRestControllers.add(entry((AnyPathFragmentRequestMappingRule) requestMapping, method));
                } else {
                    final String requestMappingKey = requestMappingKeyBuilder.build(requestMapping);
                    exactUrlRestControllerMap.put(requestMappingKey, method);
                }
                LOGGER.info("Mapped ? onto ?", () -> requestMapping, () -> getMethodName(method));
            });
        });
    }

    private String getMethodName(final RestControllerMethod method) {
        if (restServerConfig.isUseFullClassNamesForRouterMappingLogMessages()) {
            return method.getFullName();
        } else {
            return method.getShortName();
        }
    }

    @Override
    public void clear() {
        exactUrlRestControllerMap.clear();
        registeredRestControllers.clear();
        requestMappingRuleVerifier.reset();
    }

    @Override
    public Set<AbstractRestController> getRegisteredRestControllers() {
        return unmodifiableSet(registeredRestControllers);
    }

    @Override
    public CompletionStage<HttpResponse> handle(final HttpRequest request) {
        final String requestMappingKey = requestMappingKeyBuilder.build(request);
        final RestControllerMethod restControllerMethod = exactUrlRestControllerMap.get(requestMappingKey);
        if (restControllerMethod != null) {
            return restControllerMethod.call(NO_PATH_VARIABLES, request);
        }
        if (!urlTemplateRestControllers.isEmpty()) {
            for (final var entry : urlTemplateRestControllers) {
                final PathMatcherResult match = entry.getKey().match(request);
                if (match.matches()) {
                    return entry.getValue().call(newPathVariableMapping(entry, match), request);
                }
            }
        }
        if (!staticResourceTemplateRestControllers.isEmpty()) {
            for (final var entry : staticResourceTemplateRestControllers) {
                if (entry.getKey().match(request)) {
                    return entry.getValue().call(NO_PATH_VARIABLES, request);
                }
            }
        }
        return returnNotFoundHandler(request, requestMappingKey);
    }

    private CompletionStage<HttpResponse> returnNotFoundHandler(final HttpRequest request,
                                                                final String requestMappingKey) {
        if (OPTIONS.name().equals(request.getMethod())) {
            LOGGER.error(request, "CORS not allowed: Handler not found: ?", requestMappingKey);
            return corsNotAllowedStage;
        } else {
            LOGGER.error(request, "Handler not found: ?", requestMappingKey);
            return handlerNotFoundStage;
        }
    }

    private PathVariableMapping newPathVariableMapping(
            final Map.Entry<WithUrlPathVariablesRequestMappingRule, RestControllerMethod> entry,
            final PathMatcherResult match) {
        return new PathVariableMapping(
                entry.getKey().getVariables(),
                match.getExtractedVariableValues()
        );
    }
}
