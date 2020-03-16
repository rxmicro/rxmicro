package io.rxmicro.examples.rest.controller.params;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.RestControllerRegistrar;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$ListQueryParamMicroService extends AbstractRestController {

    private ListQueryParamMicroService restController;

    private $$VirtualListQueryParamRequestModelReader virtualListQueryParamRequestModelReader;

    @Override
    protected void postConstruct() {
        restController = new ListQueryParamMicroService();
        virtualListQueryParamRequestModelReader = new $$VirtualListQueryParamRequestModelReader();
    }

    @Override
    public Class<?> getRestControllerClass() {
        return ListQueryParamMicroService.class;
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "/",
                        "consume(java.util.List<io.rxmicro.examples.rest.controller.params.model.Status>)",
                        this::consume,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/",
                                false
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> consume(final PathVariableMapping pathVariableMapping,
                                                  final HttpRequest request) {
        final $$VirtualListQueryParamRequest req = virtualListQueryParamRequestModelReader.read(pathVariableMapping, request, request.contentExists());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req.supportedStatuses);
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
