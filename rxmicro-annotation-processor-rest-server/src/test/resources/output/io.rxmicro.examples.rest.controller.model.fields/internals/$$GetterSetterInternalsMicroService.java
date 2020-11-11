package io.rxmicro.examples.rest.controller.model.fields.internals;

import io.rxmicro.examples.rest.controller.model.fields.internals.gettersetter.$$RequestModelReader;
import io.rxmicro.examples.rest.controller.model.fields.internals.gettersetter.$$ResponseModelWriter;
import io.rxmicro.examples.rest.controller.model.fields.internals.gettersetter.Request;
import io.rxmicro.examples.rest.controller.model.fields.internals.gettersetter.Response;
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
public final class $$GetterSetterInternalsMicroService extends AbstractRestController {

    private GetterSetterInternalsMicroService restController;

    private $$RequestModelReader requestModelReader;

    private $$ResponseModelWriter responseModelWriter;

    private HttpResponse putNotFoundResponse;

    @Override
    protected void postConstruct() {
        restController = new GetterSetterInternalsMicroService();
        requestModelReader = new $$RequestModelReader();
        responseModelWriter = new $$ResponseModelWriter(restServerConfig.isHumanReadableOutput());
        putNotFoundResponse = notFound("Not Found");
    }

    @Override
    public Class<?> getRestControllerClass() {
        return GetterSetterInternalsMicroService.class;
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "/",
                        "put",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.fields.internals.gettersetter.Request.class
                        ),
                        this::put,
                        false,
                        new ExactUrlRequestMappingRule(
                                "PUT",
                                "/internals/gettersetter",
                                true
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> put(final PathVariableMapping pathVariableMapping,
                                              final HttpRequest request) {
        final Request req = requestModelReader.read(pathVariableMapping, request, request.isContentPresent());
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
