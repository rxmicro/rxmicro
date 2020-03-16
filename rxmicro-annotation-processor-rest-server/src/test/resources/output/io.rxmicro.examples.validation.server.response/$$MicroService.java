package io.rxmicro.examples.validation.server.response;

import io.rxmicro.examples.validation.server.response.model.$$ResponseConstraintValidator;
import io.rxmicro.examples.validation.server.response.model.$$ResponseModelWriter;
import io.rxmicro.examples.validation.server.response.model.Response;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.RestControllerRegistrar;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;

import java.util.concurrent.CompletionStage;

import static io.rxmicro.validation.detail.ResponseValidators.validateResponse;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$MicroService extends AbstractRestController {

    private MicroService restController;

    private $$ResponseModelWriter responseModelWriter;

    private $$ResponseConstraintValidator responseConstraintValidator;

    @Override
    protected void postConstruct() {
        restController = new MicroService();
        responseModelWriter = new $$ResponseModelWriter(restServerConfig.isHumanReadableOutput());
        responseConstraintValidator = new $$ResponseConstraintValidator();
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
                        "get()",
                        this::get,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/",
                                false
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> get(final PathVariableMapping pathVariableMapping,
                                              final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of();
        return restController.get()
                .thenApply(response -> buildResponse(response, 200, headers));
    }

    private HttpResponse buildResponse(final Response model,
                                       final int statusCode,
                                       final HttpHeaders headers) {
        validateResponse(responseConstraintValidator, model);
        final HttpResponse response = httpResponseBuilder.build();
        response.setStatus(statusCode);
        response.setOrAddHeaders(headers);
        responseModelWriter.write(model, response);
        return response;
    }
}
