package io.rxmicro.examples.rest.controller.model.fields.params;

import io.rxmicro.examples.rest.controller.model.fields.params.direct.$$BodyRequestModelReader;
import io.rxmicro.examples.rest.controller.model.fields.params.direct.$$QueryRequestModelReader;
import io.rxmicro.examples.rest.controller.model.fields.params.direct.$$ResponseModelWriter;
import io.rxmicro.examples.rest.controller.model.fields.params.direct.BodyRequest;
import io.rxmicro.examples.rest.controller.model.fields.params.direct.QueryRequest;
import io.rxmicro.examples.rest.controller.model.fields.params.direct.Response;
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
public final class $$DirectParamsMicroService extends AbstractRestController {

    private DirectParamsMicroService restController;

    private $$QueryRequestModelReader queryRequestModelReader;

    private $$BodyRequestModelReader bodyRequestModelReader;

    private $$ResponseModelWriter responseModelWriter;

    private HttpResponse getNotFoundResponse;

    private HttpResponse putNotFoundResponse;

    @Override
    protected void postConstruct() {
        restController = new DirectParamsMicroService();
        queryRequestModelReader = new $$QueryRequestModelReader();
        bodyRequestModelReader = new $$BodyRequestModelReader();
        responseModelWriter = new $$ResponseModelWriter(restServerConfig.isHumanReadableOutput());
        getNotFoundResponse = notFound("Not Found");
        putNotFoundResponse = notFound("Not Found");
    }

    @Override
    public Class<?> getRestControllerClass() {
        return DirectParamsMicroService.class;
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "/",
                        "get(io.rxmicro.examples.rest.controller.model.fields.params.direct.QueryRequest)",
                        this::get,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/params/direct",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "put(io.rxmicro.examples.rest.controller.model.fields.params.direct.BodyRequest)",
                        this::put,
                        false,
                        new ExactUrlRequestMappingRule(
                                "PUT",
                                "/params/direct",
                                true
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> get(final PathVariableMapping pathVariableMapping,
                                              final HttpRequest request) {
        final QueryRequest req = queryRequestModelReader.read(pathVariableMapping, request, request.contentExists());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.get(req)
                .thenApply(optionalResponse -> optionalResponse
                        .map(response -> buildResponse(response, 200, headers))
                        .orElse(getNotFoundResponse));
    }

    private CompletionStage<HttpResponse> put(final PathVariableMapping pathVariableMapping,
                                              final HttpRequest request) {
        final BodyRequest req = bodyRequestModelReader.read(pathVariableMapping, request, request.contentExists());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.put(req)
                .thenApply(optionalResponse -> optionalResponse
                        .map(response -> buildResponse(response, 200, headers))
                        .orElse(putNotFoundResponse));
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
