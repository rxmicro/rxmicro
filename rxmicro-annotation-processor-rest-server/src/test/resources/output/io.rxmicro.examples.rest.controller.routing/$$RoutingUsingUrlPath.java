package io.rxmicro.examples.rest.controller.routing;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.model.UrlSegments;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.RestControllerRegistrar;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;
import io.rxmicro.rest.server.detail.model.mapping.UrlTemplateRequestMappingRule;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$RoutingUsingUrlPath extends AbstractRestController {

    private RoutingUsingUrlPath restController;

    private $$VirtualRoutingUsingUrlPathRequestModelReader virtualRoutingUsingUrlPathRequestModelReader;

    @Override
    protected void postConstruct() {
        restController = new RoutingUsingUrlPath();
        virtualRoutingUsingUrlPathRequestModelReader = new $$VirtualRoutingUsingUrlPathRequestModelReader();
    }

    @Override
    public Class<?> getRestControllerClass() {
        return RoutingUsingUrlPath.class;
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "/",
                        "get1()",
                        this::get1,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/urlPath1",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "get2()",
                        this::get2,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/urlPath2",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "get3(java.lang.String)",
                        this::get3,
                        false,
                        new UrlTemplateRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/?",
                                        List.of("type")
                                ),
                                false
                        )

                )
        );
    }

    private CompletionStage<HttpResponse> get1(final PathVariableMapping pathVariableMapping,
                                               final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of();
        restController.get1();
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> get2(final PathVariableMapping pathVariableMapping,
                                               final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of();
        restController.get2();
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> get3(final PathVariableMapping pathVariableMapping,
                                               final HttpRequest request) {
        final $$VirtualRoutingUsingUrlPathRequest req = virtualRoutingUsingUrlPathRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.get3(req.type);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private HttpResponse buildResponse(final int statusCode,
                                       final HttpHeaders headers) {
        final HttpResponse response = httpResponseBuilder.build();
        response.setStatus(statusCode);
        response.setOrAddHeaders(headers);
        return response;
    }
}
