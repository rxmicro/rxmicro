package io.rxmicro.examples.rest.controller.redirect;

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
public final class $$MicroService extends AbstractRestController {

    private MicroService restController;

    private $$VirtualRequestModelReader virtualRequestModelReader;

    @Override
    protected void postConstruct() {
        restController = new MicroService();
        virtualRequestModelReader = new $$VirtualRequestModelReader();
    }

    @Override
    public Class<?> getRestControllerClass() {
        return MicroService.class;
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "/",
                        "redirect",
                        List.of(),
                        this::redirect,
                        false,
                        new ExactUrlRequestMappingRule(
                                "PUT",
                                "/old-url",
                                true
                        )
                ),
                new Registration(
                        "/",
                        "put",
                        List.of(
                                java.lang.String.class
                        ),
                        this::put,
                        false,
                        new ExactUrlRequestMappingRule(
                                "PUT",
                                "/new-url",
                                true
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> redirect(final PathVariableMapping pathVariableMapping,
                                                   final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of();
        return restController.redirect()
                .thenApply(nothing -> buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> put(final PathVariableMapping pathVariableMapping,
                                              final HttpRequest request) {
        final $$VirtualRequest req = virtualRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.put(req.parameter);
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
