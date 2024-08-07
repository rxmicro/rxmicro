package io.rxmicro.examples.rest.controller.model.field.access.params;

import io.rxmicro.examples.rest.controller.model.field.access.params.direct.$$BodyRequestConstraintValidator;
import io.rxmicro.examples.rest.controller.model.field.access.params.direct.$$BodyRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.field.access.params.direct.$$QueryRequestConstraintValidator;
import io.rxmicro.examples.rest.controller.model.field.access.params.direct.$$QueryRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.field.access.params.direct.$$ResponseConstraintValidator;
import io.rxmicro.examples.rest.controller.model.field.access.params.direct.$$ResponseServerModelWriter;
import io.rxmicro.examples.rest.controller.model.field.access.params.direct.BodyRequest;
import io.rxmicro.examples.rest.controller.model.field.access.params.direct.QueryRequest;
import io.rxmicro.examples.rest.controller.model.field.access.params.direct.Response;
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

import static io.rxmicro.validation.detail.ResponseValidators.validateResponse;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$DirectParamsMicroService extends AbstractRestController {

    private DirectParamsMicroService restController;

    private $$BodyRequestServerModelReader bodyRequestServerModelReader;

    private $$QueryRequestServerModelReader queryRequestServerModelReader;

    private $$ResponseServerModelWriter responseServerModelWriter;

    private $$BodyRequestConstraintValidator bodyRequestConstraintValidator;

    private $$ResponseConstraintValidator responseConstraintValidator;

    private $$QueryRequestConstraintValidator queryRequestConstraintValidator;

    private HttpResponse getNotFoundResponse;

    private HttpResponse putNotFoundResponse;

    @Override
    protected void postConstruct() {
        restController = new DirectParamsMicroService();
        bodyRequestServerModelReader = new $$BodyRequestServerModelReader();
        queryRequestServerModelReader = new $$QueryRequestServerModelReader();
        responseServerModelWriter = new $$ResponseServerModelWriter(restServerConfig.isHumanReadableOutput());
        bodyRequestConstraintValidator = new $$BodyRequestConstraintValidator();
        responseConstraintValidator = new $$ResponseConstraintValidator();
        queryRequestConstraintValidator = new $$QueryRequestConstraintValidator();
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
                        "get",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.field.access.params.direct.QueryRequest.class
                        ),
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
                        "put",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.field.access.params.direct.BodyRequest.class
                        ),
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
        final QueryRequest req = queryRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        queryRequestConstraintValidator.validate(req);
        final HttpHeaders headers = HttpHeaders.of();
        return restController.get(req)
                .thenApply(optionalResponse -> optionalResponse
                        .map(response -> buildResponse(response, 200, headers))
                        .orElse(getNotFoundResponse));
    }

    private CompletionStage<HttpResponse> put(final PathVariableMapping pathVariableMapping,
                                              final HttpRequest request) {
        final BodyRequest req = bodyRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        bodyRequestConstraintValidator.validate(req);
        final HttpHeaders headers = HttpHeaders.of();
        return restController.put(req)
                .thenApply(optionalResponse -> optionalResponse
                        .map(response -> buildResponse(response, 200, headers))
                        .orElse(putNotFoundResponse));
    }

    private HttpResponse buildResponse(final Response model,
                                       final int statusCode,
                                       final HttpHeaders headers) {
        validateResponse(restServerConfig.isEnableAdditionalValidations(), responseConstraintValidator, model);
        final HttpResponse response = httpResponseBuilder.build();
        response.setStatus(statusCode);
        response.setOrAddHeaders(headers);
        responseServerModelWriter.write(model, response);
        return response;
    }
}
