package io.rxmicro.examples.rest.controller.headers;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.RestControllerRegistrar;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$StaticHeadersMicroService extends AbstractRestController {

    private StaticHeadersMicroService restController;

    @Override
    protected void postConstruct() {
        restController = new StaticHeadersMicroService();
    }

    @Override
    public Class<?> getRestControllerClass() {
        return StaticHeadersMicroService.class;
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "/",
                        "get1",
                        List.of(),
                        this::get1,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/get1",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "get2",
                        List.of(),
                        this::get2,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/get2",
                                false
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> get1(final PathVariableMapping pathVariableMapping,
                                               final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of("Mode", "Demo");
        restController.get1();
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> get2(final PathVariableMapping pathVariableMapping,
                                               final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of(
                "Mode", "Demo",
                "Debug", "true"
        );
        restController.get2();
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
