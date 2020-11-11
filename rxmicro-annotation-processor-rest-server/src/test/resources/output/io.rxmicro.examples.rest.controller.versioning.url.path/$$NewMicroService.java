package io.rxmicro.examples.rest.controller.versioning.url.path;

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
public final class $$NewMicroService extends AbstractRestController {

    private NewMicroService restController;

    @Override
    protected void postConstruct() {
        restController = new NewMicroService();
    }

    @Override
    public Class<?> getRestControllerClass() {
        return NewMicroService.class;
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "/v2",
                        "update",
                        List.of(),
                        this::update,
                        false,
                        new ExactUrlRequestMappingRule(
                                "PATCH",
                                "/v2/patch",
                                false
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> update(final PathVariableMapping pathVariableMapping,
                                                 final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of();
        restController.update();
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
