package io.rxmicro.examples.rest.controller.static_resources.complex;

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
public final class $$RestController extends AbstractRestController {

    private RestController restController;

    private $$ResponseServerModelWriter responseServerModelWriter;

    private $$ResponseConstraintValidator responseConstraintValidator;

    @Override
    protected void postConstruct() {
        restController = new RestController();
        responseServerModelWriter = new $$ResponseServerModelWriter(restServerConfig.isHumanReadableOutput());
        responseConstraintValidator = new $$ResponseConstraintValidator();
    }

    @Override
    public Class<?> getRestControllerClass() {
        return RestController.class;
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "/",
                        "getHelloWorld",
                        List.of(),
                        this::getHelloWorld,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/say-hello-world",
                                false
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> getHelloWorld(final PathVariableMapping pathVariableMapping,
                                                        final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of();
        return restController.getHelloWorld()
                .thenApply(response -> buildResponse(response, 200, headers));
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
