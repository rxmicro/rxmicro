package io.rxmicro.examples.rest.controller.base.url.path;

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
public final class $$MicroService1 extends AbstractRestController {

    private MicroService1 restController;

    @Override
    protected void postConstruct() {
        restController = new MicroService1();
    }

    @Override
    public Class<?> getRestControllerClass() {
        return MicroService1.class;
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "/",
                        "patch",
                        List.of(),
                        this::patch,
                        false,
                        new ExactUrlRequestMappingRule(
                                "PATCH",
                                "/patch",
                                false
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> patch(final PathVariableMapping pathVariableMapping,
                                                final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of();
        restController.patch();
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
