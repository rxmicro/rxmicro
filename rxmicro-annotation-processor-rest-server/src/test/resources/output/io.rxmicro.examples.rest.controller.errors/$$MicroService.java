package io.rxmicro.examples.rest.controller.errors;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.RestControllerRegistrar;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;

import java.util.concurrent.CompletionStage;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$MicroService extends AbstractRestController {

    private MicroService restController;

    private $$VirtualRequestModelReader virtualRequestModelReader;

    private $$VirtualRequest2ModelReader virtualRequest2ModelReader;

    @Override
    protected void postConstruct() {
        restController = new MicroService();
        virtualRequestModelReader = new $$VirtualRequestModelReader();
        virtualRequest2ModelReader = new $$VirtualRequest2ModelReader();
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
                        "updateObject1(java.lang.Integer)",
                        this::updateObject1,
                        false,
                        new ExactUrlRequestMappingRule(
                                "PUT",
                                "/business-object-1",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "updateObject2(java.lang.Integer)",
                        this::updateObject2,
                        false,
                        new ExactUrlRequestMappingRule(
                                "PUT",
                                "/business-object-2",
                                false
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> updateObject1(final PathVariableMapping pathVariableMapping,
                                                        final HttpRequest request) {
        final $$VirtualRequest req = virtualRequestModelReader.read(pathVariableMapping, request, request.contentExists());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.updateObject1(req.id)
                .thenApply(nothing -> buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> updateObject2(final PathVariableMapping pathVariableMapping,
                                                        final HttpRequest request) {
        final $$VirtualRequest2 req = virtualRequest2ModelReader.read(pathVariableMapping, request, request.contentExists());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.updateObject2(req.id)
                .thenApply(nothing -> buildResponse(200, headers));
    }

    private HttpResponse buildResponse(final int statusCode,
                                       final HttpHeaders headers) {
        final HttpResponse response = httpResponseBuilder.build();
        response.setStatus(statusCode);
        response.setOrAddHeaders(headers);
        return response;
    }
}
