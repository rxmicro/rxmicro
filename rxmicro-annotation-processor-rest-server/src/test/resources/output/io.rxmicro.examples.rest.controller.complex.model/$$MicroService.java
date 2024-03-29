package io.rxmicro.examples.rest.controller.complex.model;

import io.rxmicro.examples.rest.controller.complex.model.model.$$ComplexModelServerModelReader;
import io.rxmicro.examples.rest.controller.complex.model.model.$$ComplexModelServerModelWriter;
import io.rxmicro.examples.rest.controller.complex.model.model.ComplexModel;
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
public final class $$MicroService extends AbstractRestController {

    private MicroService restController;

    private $$ComplexModelServerModelReader complexModelServerModelReader;

    private $$ComplexModelServerModelWriter complexModelServerModelWriter;

    @Override
    protected void postConstruct() {
        restController = new MicroService();
        complexModelServerModelReader = new $$ComplexModelServerModelReader();
        complexModelServerModelWriter = new $$ComplexModelServerModelWriter(restServerConfig.isHumanReadableOutput());
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
                        "post",
                        List.of(
                                io.rxmicro.examples.rest.controller.complex.model.model.ComplexModel.class
                        ),
                        this::post,
                        false,
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/",
                                true
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> post(final PathVariableMapping pathVariableMapping,
                                               final HttpRequest request) {
        final ComplexModel req = complexModelServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        return restController.post(req)
                .thenApply(response -> buildResponse(response, 200, headers));
    }

    private HttpResponse buildResponse(final ComplexModel model,
                                       final int statusCode,
                                       final HttpHeaders headers) {
        final HttpResponse response = httpResponseBuilder.build();
        response.setStatus(statusCode);
        response.setOrAddHeaders(headers);
        complexModelServerModelWriter.write(model, response);
        return response;
    }
}
