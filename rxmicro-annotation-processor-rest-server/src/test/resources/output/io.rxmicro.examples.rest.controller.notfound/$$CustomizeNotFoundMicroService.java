package io.rxmicro.examples.rest.controller.notfound;

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
public final class $$CustomizeNotFoundMicroService extends AbstractRestController {

    private CustomizeNotFoundMicroService restController;

    private $$VirtualCustomizeNotFoundRequestServerModelReader virtualCustomizeNotFoundRequestServerModelReader;

    private $$ResponseServerModelWriter responseServerModelWriter;

    private $$ResponseConstraintValidator responseConstraintValidator;

    private $$VirtualCustomizeNotFoundRequestConstraintValidator virtualCustomizeNotFoundRequestConstraintValidator;

    private HttpResponse getOptional1NotFoundResponse;

    @Override
    protected void postConstruct() {
        restController = new CustomizeNotFoundMicroService();
        virtualCustomizeNotFoundRequestServerModelReader = new $$VirtualCustomizeNotFoundRequestServerModelReader();
        responseServerModelWriter = new $$ResponseServerModelWriter(restServerConfig.isHumanReadableOutput());
        responseConstraintValidator = new $$ResponseConstraintValidator();
        virtualCustomizeNotFoundRequestConstraintValidator = new $$VirtualCustomizeNotFoundRequestConstraintValidator();
        getOptional1NotFoundResponse = notFound("Custom not found message");
    }

    @Override
    public Class<?> getRestControllerClass() {
        return CustomizeNotFoundMicroService.class;
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "/",
                        "getOptional1",
                        List.of(
                                java.lang.Boolean.class
                        ),
                        this::getOptional1,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/",
                                false
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> getOptional1(final PathVariableMapping pathVariableMapping,
                                                       final HttpRequest request) {
        final $$VirtualCustomizeNotFoundRequest req = virtualCustomizeNotFoundRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        virtualCustomizeNotFoundRequestConstraintValidator.validate(req);
        final HttpHeaders headers = HttpHeaders.of();
        return restController.getOptional1(req.found)
                .thenApply(optionalResponse -> optionalResponse
                        .map(response -> buildResponse(response, 200, headers))
                        .orElse(getOptional1NotFoundResponse));
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
