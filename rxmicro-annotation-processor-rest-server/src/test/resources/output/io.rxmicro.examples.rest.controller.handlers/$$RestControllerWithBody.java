package io.rxmicro.examples.rest.controller.handlers;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.RestControllerRegistrar;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;

import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$RestControllerWithBody extends AbstractRestController {

    private RestControllerWithBody restController;

    private $$VirtualWithBodyRequest5ModelReader virtualWithBodyRequest5ModelReader;

    private $$VirtualWithBodyRequest4ModelReader virtualWithBodyRequest4ModelReader;

    private $$VirtualWithBodyRequest7ModelReader virtualWithBodyRequest7ModelReader;

    private $$VirtualWithBodyRequest6ModelReader virtualWithBodyRequest6ModelReader;

    private $$VirtualWithBodyRequest3ModelReader virtualWithBodyRequest3ModelReader;

    private $$VirtualWithBodyRequest2ModelReader virtualWithBodyRequest2ModelReader;

    private $$VirtualWithBodyRequestModelReader virtualWithBodyRequestModelReader;

    private $$RequestModelReader requestModelReader;

    private $$ResponseModelWriter responseModelWriter;

    private HttpResponse completedFuture4NotFoundResponse;

    private HttpResponse completedFuture5NotFoundResponse;

    private HttpResponse completedFuture6NotFoundResponse;

    private HttpResponse completionStage4NotFoundResponse;

    private HttpResponse completionStage5NotFoundResponse;

    private HttpResponse completionStage6NotFoundResponse;

    private HttpResponse mono1NotFoundResponse;

    private HttpResponse mono2NotFoundResponse;

    private HttpResponse mono3NotFoundResponse;

    private HttpResponse maybe1NotFoundResponse;

    private HttpResponse maybe2NotFoundResponse;

    private HttpResponse maybe3NotFoundResponse;

    @Override
    protected void postConstruct() {
        restController = new RestControllerWithBody();
        virtualWithBodyRequest5ModelReader = new $$VirtualWithBodyRequest5ModelReader();
        virtualWithBodyRequest4ModelReader = new $$VirtualWithBodyRequest4ModelReader();
        virtualWithBodyRequest7ModelReader = new $$VirtualWithBodyRequest7ModelReader();
        virtualWithBodyRequest6ModelReader = new $$VirtualWithBodyRequest6ModelReader();
        virtualWithBodyRequest3ModelReader = new $$VirtualWithBodyRequest3ModelReader();
        virtualWithBodyRequest2ModelReader = new $$VirtualWithBodyRequest2ModelReader();
        virtualWithBodyRequestModelReader = new $$VirtualWithBodyRequestModelReader();
        requestModelReader = new $$RequestModelReader();
        responseModelWriter = new $$ResponseModelWriter(restServerConfig.isHumanReadableOutput());
        completedFuture4NotFoundResponse = notFound("Not Found");
        completedFuture5NotFoundResponse = notFound("Not Found");
        completedFuture6NotFoundResponse = notFound("Not Found");
        completionStage4NotFoundResponse = notFound("Not Found");
        completionStage5NotFoundResponse = notFound("Not Found");
        completionStage6NotFoundResponse = notFound("Not Found");
        mono1NotFoundResponse = notFound("Not Found");
        mono2NotFoundResponse = notFound("Not Found");
        mono3NotFoundResponse = notFound("Not Found");
        maybe1NotFoundResponse = notFound("Not Found");
        maybe2NotFoundResponse = notFound("Not Found");
        maybe3NotFoundResponse = notFound("Not Found");
    }

    @Override
    public Class<?> getRestControllerClass() {
        return RestControllerWithBody.class;
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "/",
                        "completedFuture1",
                        List.of(),
                        this::completedFuture1,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/jse/completedFuture1",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "completedFuture2",
                        List.of(
                                io.rxmicro.examples.rest.controller.handlers.Request.class
                        ),
                        this::completedFuture2,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/jse/completedFuture2",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "completedFuture3",
                        List.of(
                                java.lang.String.class
                        ),
                        this::completedFuture3,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/jse/completedFuture3",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "completedFuture4",
                        List.of(),
                        this::completedFuture4,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/jse/completedFuture4",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "completedFuture5",
                        List.of(
                                io.rxmicro.examples.rest.controller.handlers.Request.class
                        ),
                        this::completedFuture5,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/jse/completedFuture5",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "completedFuture6",
                        List.of(
                                java.lang.String.class
                        ),
                        this::completedFuture6,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/jse/completedFuture6",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "completionStage1",
                        List.of(),
                        this::completionStage1,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/jse/completionStage1",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "completionStage2",
                        List.of(
                                io.rxmicro.examples.rest.controller.handlers.Request.class
                        ),
                        this::completionStage2,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/jse/completionStage2",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "completionStage3",
                        List.of(
                                java.lang.String.class
                        ),
                        this::completionStage3,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/jse/completionStage3",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "completionStage4",
                        List.of(),
                        this::completionStage4,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/jse/completionStage4",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "completionStage5",
                        List.of(
                                io.rxmicro.examples.rest.controller.handlers.Request.class
                        ),
                        this::completionStage5,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/jse/completionStage5",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "completionStage6",
                        List.of(
                                java.lang.String.class
                        ),
                        this::completionStage6,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/jse/completionStage6",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "mono1",
                        List.of(),
                        this::mono1,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/spring-reactor/mono1",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "mono2",
                        List.of(
                                io.rxmicro.examples.rest.controller.handlers.Request.class
                        ),
                        this::mono2,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/spring-reactor/mono2",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "mono3",
                        List.of(
                                java.lang.String.class
                        ),
                        this::mono3,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/spring-reactor/mono3",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "single1",
                        List.of(),
                        this::single1,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/rxjava3/single1",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "single2",
                        List.of(
                                io.rxmicro.examples.rest.controller.handlers.Request.class
                        ),
                        this::single2,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/rxjava3/single2",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "single3",
                        List.of(
                                java.lang.String.class
                        ),
                        this::single3,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/rxjava3/single3",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "maybe1",
                        List.of(),
                        this::maybe1,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/rxjava3/maybe1",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "maybe2",
                        List.of(
                                io.rxmicro.examples.rest.controller.handlers.Request.class
                        ),
                        this::maybe2,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/rxjava3/maybe2",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "maybe3",
                        List.of(
                                java.lang.String.class
                        ),
                        this::maybe3,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/rxjava3/maybe3",
                                false
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> completedFuture1(final PathVariableMapping pathVariableMapping,
                                                           final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of();
        return restController.completedFuture1()
                .thenApply(response -> buildResponse(response, 200, headers));
    }

    private CompletionStage<HttpResponse> completedFuture2(final PathVariableMapping pathVariableMapping,
                                                           final HttpRequest request) {
        final Request req = requestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.completedFuture2(req)
                .thenApply(response -> buildResponse(response, 200, headers));
    }

    private CompletionStage<HttpResponse> completedFuture3(final PathVariableMapping pathVariableMapping,
                                                           final HttpRequest request) {
        final $$VirtualWithBodyRequest req = virtualWithBodyRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.completedFuture3(req.requestParameter)
                .thenApply(response -> buildResponse(response, 200, headers));
    }

    private CompletionStage<HttpResponse> completedFuture4(final PathVariableMapping pathVariableMapping,
                                                           final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of();
        return restController.completedFuture4()
                .thenApply(optionalResponse -> optionalResponse
                        .map(response -> buildResponse(response, 200, headers))
                        .orElse(completedFuture4NotFoundResponse));
    }

    private CompletionStage<HttpResponse> completedFuture5(final PathVariableMapping pathVariableMapping,
                                                           final HttpRequest request) {
        final Request req = requestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.completedFuture5(req)
                .thenApply(optionalResponse -> optionalResponse
                        .map(response -> buildResponse(response, 200, headers))
                        .orElse(completedFuture5NotFoundResponse));
    }

    private CompletionStage<HttpResponse> completedFuture6(final PathVariableMapping pathVariableMapping,
                                                           final HttpRequest request) {
        final $$VirtualWithBodyRequest2 req = virtualWithBodyRequest2ModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.completedFuture6(req.requestParameter)
                .thenApply(optionalResponse -> optionalResponse
                        .map(response -> buildResponse(response, 200, headers))
                        .orElse(completedFuture6NotFoundResponse));
    }

    private CompletionStage<HttpResponse> completionStage1(final PathVariableMapping pathVariableMapping,
                                                           final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of();
        return restController.completionStage1()
                .thenApply(response -> buildResponse(response, 200, headers));
    }

    private CompletionStage<HttpResponse> completionStage2(final PathVariableMapping pathVariableMapping,
                                                           final HttpRequest request) {
        final Request req = requestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.completionStage2(req)
                .thenApply(response -> buildResponse(response, 200, headers));
    }

    private CompletionStage<HttpResponse> completionStage3(final PathVariableMapping pathVariableMapping,
                                                           final HttpRequest request) {
        final $$VirtualWithBodyRequest3 req = virtualWithBodyRequest3ModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.completionStage3(req.requestParameter)
                .thenApply(response -> buildResponse(response, 200, headers));
    }

    private CompletionStage<HttpResponse> completionStage4(final PathVariableMapping pathVariableMapping,
                                                           final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of();
        return restController.completionStage4()
                .thenApply(optionalResponse -> optionalResponse
                        .map(response -> buildResponse(response, 200, headers))
                        .orElse(completionStage4NotFoundResponse));
    }

    private CompletionStage<HttpResponse> completionStage5(final PathVariableMapping pathVariableMapping,
                                                           final HttpRequest request) {
        final Request req = requestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.completionStage5(req)
                .thenApply(optionalResponse -> optionalResponse
                        .map(response -> buildResponse(response, 200, headers))
                        .orElse(completionStage5NotFoundResponse));
    }

    private CompletionStage<HttpResponse> completionStage6(final PathVariableMapping pathVariableMapping,
                                                           final HttpRequest request) {
        final $$VirtualWithBodyRequest4 req = virtualWithBodyRequest4ModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.completionStage6(req.requestParameter)
                .thenApply(optionalResponse -> optionalResponse
                        .map(response -> buildResponse(response, 200, headers))
                        .orElse(completionStage6NotFoundResponse));
    }

    private CompletionStage<HttpResponse> mono1(final PathVariableMapping pathVariableMapping,
                                                final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of();
        return restController.mono1()
                .map(response -> buildResponse(response, 200, headers))
                .defaultIfEmpty(mono1NotFoundResponse)
                .toFuture();
    }

    private CompletionStage<HttpResponse> mono2(final PathVariableMapping pathVariableMapping,
                                                final HttpRequest request) {
        final Request req = requestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.mono2(req)
                .map(response -> buildResponse(response, 200, headers))
                .defaultIfEmpty(mono2NotFoundResponse)
                .toFuture();
    }

    private CompletionStage<HttpResponse> mono3(final PathVariableMapping pathVariableMapping,
                                                final HttpRequest request) {
        final $$VirtualWithBodyRequest5 req = virtualWithBodyRequest5ModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.mono3(req.requestParameter)
                .map(response -> buildResponse(response, 200, headers))
                .defaultIfEmpty(mono3NotFoundResponse)
                .toFuture();
    }

    private CompletionStage<HttpResponse> single1(final PathVariableMapping pathVariableMapping,
                                                  final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of();
        return restController.single1()
                .map(response -> buildResponse(response, 200, headers))
                .toCompletionStage();
    }

    private CompletionStage<HttpResponse> single2(final PathVariableMapping pathVariableMapping,
                                                  final HttpRequest request) {
        final Request req = requestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.single2(req)
                .map(response -> buildResponse(response, 200, headers))
                .toCompletionStage();
    }

    private CompletionStage<HttpResponse> single3(final PathVariableMapping pathVariableMapping,
                                                  final HttpRequest request) {
        final $$VirtualWithBodyRequest6 req = virtualWithBodyRequest6ModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.single3(req.requestParameter)
                .map(response -> buildResponse(response, 200, headers))
                .toCompletionStage();
    }

    private CompletionStage<HttpResponse> maybe1(final PathVariableMapping pathVariableMapping,
                                                 final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of();
        return restController.maybe1()
                .map(response -> buildResponse(response, 200, headers))
                .defaultIfEmpty(maybe1NotFoundResponse)
                .toCompletionStage();
    }

    private CompletionStage<HttpResponse> maybe2(final PathVariableMapping pathVariableMapping,
                                                 final HttpRequest request) {
        final Request req = requestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.maybe2(req)
                .map(response -> buildResponse(response, 200, headers))
                .defaultIfEmpty(maybe2NotFoundResponse)
                .toCompletionStage();
    }

    private CompletionStage<HttpResponse> maybe3(final PathVariableMapping pathVariableMapping,
                                                 final HttpRequest request) {
        final $$VirtualWithBodyRequest7 req = virtualWithBodyRequest7ModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.maybe3(req.requestParameter)
                .map(response -> buildResponse(response, 200, headers))
                .defaultIfEmpty(maybe3NotFoundResponse)
                .toCompletionStage();
    }

    private HttpResponse buildResponse(final Response model,
                                       final int statusCode,
                                       final HttpHeaders headers) {
        final HttpResponse response = httpResponseBuilder.build();
        response.setStatus(statusCode);
        response.setOrAddHeaders(headers);
        responseModelWriter.write(model, response);
        return response;
    }
}
