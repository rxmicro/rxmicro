package io.rxmicro.examples.rest.controller.headers;

import io.rxmicro.examples.rest.controller.headers.model.$$RequestModelReader;
import io.rxmicro.examples.rest.controller.headers.model.$$ResponseModelWriter;
import io.rxmicro.examples.rest.controller.headers.model.Request;
import io.rxmicro.examples.rest.controller.headers.model.Response;
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
public final class $$SimpleUsageMicroService extends AbstractRestController {

    private SimpleUsageMicroService restController;

    private $$RequestModelReader requestModelReader;

    private $$VirtualSimpleUsageRequestModelReader virtualSimpleUsageRequestModelReader;

    private $$ResponseModelWriter responseModelWriter;

    @Override
    protected void postConstruct() {
        restController = new SimpleUsageMicroService();
        requestModelReader = new $$RequestModelReader();
        virtualSimpleUsageRequestModelReader = new $$VirtualSimpleUsageRequestModelReader();
        responseModelWriter = new $$ResponseModelWriter(restServerConfig.isHumanReadableOutput());
    }

    @Override
    public Class<?> getRestControllerClass() {
        return SimpleUsageMicroService.class;
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "/",
                        "get1(io.rxmicro.examples.rest.controller.headers.model.Request)",
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
                        "get2(java.lang.String,java.lang.Boolean)",
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
        final Request req = requestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.get1(req)
                .thenApply(response -> buildResponse(response, 200, headers));
    }

    private CompletionStage<HttpResponse> get2(final PathVariableMapping pathVariableMapping,
                                               final HttpRequest request) {
        final $$VirtualSimpleUsageRequest req = virtualSimpleUsageRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.get2(req.endpointVersion, req.useProxy)
                .thenApply(response -> buildResponse(response, 200, headers));
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
