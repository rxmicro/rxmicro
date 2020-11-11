package io.rxmicro.examples.rest.controller.request.id;

import io.rxmicro.examples.rest.controller.request.id.model.$$RequestModelReader;
import io.rxmicro.examples.rest.controller.request.id.model.Request;
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

    private $$RequestModelReader requestModelReader;

    @Override
    protected void postConstruct() {
        restController = new MicroService();
        requestModelReader = new $$RequestModelReader();
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
                        "handle",
                        List.of(
                                io.rxmicro.examples.rest.controller.request.id.model.Request.class
                        ),
                        this::handle,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/",
                                false
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> handle(final PathVariableMapping pathVariableMapping,
                                                 final HttpRequest request) {
        final Request req = requestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.handle(req);
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
