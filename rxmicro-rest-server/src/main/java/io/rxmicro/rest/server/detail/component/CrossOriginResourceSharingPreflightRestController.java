/*
 * Copyright 2019 http://rxmicro.io
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

import io.rxmicro.common.ImpossibleException;
import io.rxmicro.http.error.ValidationException;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.model.CrossOriginResourceSharingResource;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;
import io.rxmicro.rest.server.detail.model.mapping.RequestMappingRule;
import io.rxmicro.rest.server.detail.model.mapping.UrlTemplateRequestMappingRule;
import io.rxmicro.rest.server.local.component.PathMatcher;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static io.rxmicro.http.HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS;
import static io.rxmicro.http.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS;
import static io.rxmicro.http.HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS;
import static io.rxmicro.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static io.rxmicro.http.HttpHeaders.ACCESS_CONTROL_MAX_AGE;
import static io.rxmicro.http.HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS;
import static io.rxmicro.http.HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD;
import static io.rxmicro.http.HttpHeaders.CONTENT_LENGTH;
import static io.rxmicro.http.HttpHeaders.ORIGIN;
import static io.rxmicro.http.HttpHeaders.VARY;
import static io.rxmicro.rest.model.HttpMethod.OPTIONS;
import static java.util.concurrent.CompletableFuture.completedStage;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class CrossOriginResourceSharingPreflightRestController extends AbstractRestController {

    private final Map<String, CrossOriginResourceSharingResource> exactUrlMapping;

    private final List<CrossOriginResourceSharingResource> urlTemplateMapping;

    public CrossOriginResourceSharingPreflightRestController(
            final Set<CrossOriginResourceSharingResource> crossOriginResourceSharingResources) {
        this.exactUrlMapping = crossOriginResourceSharingResources.stream()
                .filter(r -> !r.isUrlSegmentsPresent())
                .collect(toUnmodifiableMap(CrossOriginResourceSharingResource::getUri, identity()));
        this.urlTemplateMapping = crossOriginResourceSharingResources.stream()
                .filter(CrossOriginResourceSharingResource::isUrlSegmentsPresent)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "",
                        "handle",
                        this::handle,
                        false,
                        exactUrlMapping.keySet().stream()
                                .map(u -> new ExactUrlRequestMappingRule(OPTIONS.name(), u, false))
                                .toArray(RequestMappingRule[]::new)
                ),
                new Registration(
                        "",
                        "handle",
                        this::handle,
                        false,
                        urlTemplateMapping.stream()
                                .map(r -> new UrlTemplateRequestMappingRule(OPTIONS.name(), r.getUrlSegments(), false))
                                .toArray(RequestMappingRule[]::new)
                )
        );
    }

    @Override
    public Class<?> getRestControllerClass() {
        return CrossOriginResourceSharingPreflightRestController.class;
    }

    // https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS
    // https://www.w3.org/TR/cors/#preflight-request
    // https://www.html5rocks.com/static/images/cors_server_flowchart.png
    private CompletionStage<HttpResponse> handle(final PathVariableMapping pathVariableMapping,
                                                 final HttpRequest request) {
        final String origin = request.getHeaders().getValue(ORIGIN);
        validateOrigin(origin);
        final CrossOriginResourceSharingResource resource = getResource(request);
        validateAccessControlRequestMethod(request, resource);
        validateAccessControlRequestHeaders(request, resource);
        final HttpResponse httpResponse = httpResponseBuilder.build();
        setCORSHeaders(origin, resource, httpResponse);
        return completedStage(httpResponse);
    }

    private CrossOriginResourceSharingResource getResource(final HttpRequest request) {
        final String uri = request.getUri();
        final CrossOriginResourceSharingResource resource = exactUrlMapping.get(uri);
        if (resource != null) {
            return resource;
        }
        for (final CrossOriginResourceSharingResource sharingResource : urlTemplateMapping) {
            final PathMatcher pathMatcher = new PathMatcher(sharingResource.getUrlSegments());
            if (pathMatcher.matches(uri).matches()) {
                return sharingResource;
            }
        }
        throw new ImpossibleException("CrossOriginResourceSharingResource must be found");
    }

    private void validateOrigin(final String origin) {
        if (origin == null) {
            throw new ValidationException(
                    "Not a valid preflight request: Missing '?' header", ORIGIN);
        }
    }

    private void validateAccessControlRequestMethod(final HttpRequest request,
                                                    final CrossOriginResourceSharingResource resource) {
        final String accessControlRequestMethod = request.getHeaders().getValue(ACCESS_CONTROL_REQUEST_METHOD);
        if (accessControlRequestMethod == null) {
            throw new ValidationException(
                    "Not a valid preflight request: Missing '?' header", ACCESS_CONTROL_REQUEST_METHOD);
        }
        if (!resource.getAllowMethods().contains(accessControlRequestMethod)) {
            throw new ValidationException(
                    "Not a valid preflight request: Method '?' not supported. Allowed methods are: {?}",
                    accessControlRequestMethod, resource.getAllowMethodsCommaSeparated());
        }
    }

    private void validateAccessControlRequestHeaders(final HttpRequest request,
                                                     final CrossOriginResourceSharingResource resource) {
        final String accessControlRequestHeaders = request.getHeaders().getValue(ACCESS_CONTROL_REQUEST_HEADERS);
        if (accessControlRequestHeaders != null) {
            if (Arrays.stream(accessControlRequestHeaders.split(","))
                    .map(s -> s.trim().toLowerCase())
                    .noneMatch(h ->
                            resource.getAllowHeaders().contains(h) ||
                                    resource.getExposedHeaders().contains(h)
                    )) {
                throw new ValidationException(
                        "Not a valid preflight request: Header(s) {?} not supported. Allowed header(s): {?}",
                        accessControlRequestHeaders, resource.getAllHeadersCommaSeparated().orElse("<none>"));
            }
        }
    }

    private void setCORSHeaders(final String origin,
                                final CrossOriginResourceSharingResource resource,
                                final HttpResponse httpResponse) {
        if (resource.getAllowOrigins().contains(origin)) {
            httpResponse.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        } else {
            httpResponse.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, resource.getFirstOrigin());
        }
        httpResponse.setHeader(VARY, ORIGIN);
        httpResponse.setHeader(ACCESS_CONTROL_ALLOW_METHODS, resource.getAllowMethodsCommaSeparated());
        resource.getAllHeadersCommaSeparated().ifPresent(headers ->
                httpResponse.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, headers));
        if (resource.isAccessControlAllowCredentials()) {
            httpResponse.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        }
        httpResponse.setHeader(ACCESS_CONTROL_MAX_AGE, resource.getAccessControlMaxAge());
        httpResponse.setHeader(CONTENT_LENGTH, 0);
    }
}
