package io.rxmicro.examples.rest.controller.params;

import io.rxmicro.examples.rest.controller.params.model.$$RequestModelReader;
import io.rxmicro.examples.rest.controller.params.model.$$ResponseModelWriter;
import io.rxmicro.examples.rest.controller.params.model.Request;
import io.rxmicro.examples.rest.controller.params.model.Response;
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
public final class $$SimpleUsageMicroService extends AbstractRestController {

    private SimpleUsageMicroService restController;

    private $$VirtualSimpleUsageRequestModelReader virtualSimpleUsageRequestModelReader;

    private $$RequestModelReader requestModelReader;

    private $$ResponseModelWriter responseModelWriter;

    @Override
    protected void postConstruct() {
        restController = new SimpleUsageMicroService();
        virtualSimpleUsageRequestModelReader = new $$VirtualSimpleUsageRequestModelReader();
        requestModelReader = new $$RequestModelReader();
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
                        "get1",
                        List.of(
                                io.rxmicro.examples.rest.controller.params.model.Request.class
                        ),
                        this::get1,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/get1",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/post1",
                                true
                        )
                ),
                new Registration(
                        "/",
                        "get2",
                        List.of(
                                java.lang.String.class, java.lang.Boolean.class
                        ),
                        this::get2,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/get2",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/post2",
                                true
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
