package io.rxmicro.examples.rest.controller.routing;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.RestControllerRegistrar;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$RoutingUsingHttpBody extends AbstractRestController {

    private RoutingUsingHttpBody restController;

    private $$VirtualRoutingUsingHttpBodyRequestServerModelReader virtualRoutingUsingHttpBodyRequestServerModelReader;

    private $$VirtualRoutingUsingHttpBodyRequest2ServerModelReader virtualRoutingUsingHttpBodyRequest2ServerModelReader;

    private $$VirtualRoutingUsingHttpBodyRequestConstraintValidator virtualRoutingUsingHttpBodyRequestConstraintValidator;

    private $$VirtualRoutingUsingHttpBodyRequest2ConstraintValidator virtualRoutingUsingHttpBodyRequest2ConstraintValidator;

    @Override
    protected void postConstruct() {
        restController = new RoutingUsingHttpBody();
        virtualRoutingUsingHttpBodyRequestServerModelReader = new $$VirtualRoutingUsingHttpBodyRequestServerModelReader();
        virtualRoutingUsingHttpBodyRequest2ServerModelReader = new $$VirtualRoutingUsingHttpBodyRequest2ServerModelReader();
        virtualRoutingUsingHttpBodyRequestConstraintValidator = new $$VirtualRoutingUsingHttpBodyRequestConstraintValidator();
        virtualRoutingUsingHttpBodyRequest2ConstraintValidator = new $$VirtualRoutingUsingHttpBodyRequest2ConstraintValidator();
    }

    @Override
    public Class<?> getRestControllerClass() {
        return RoutingUsingHttpBody.class;
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "/",
                        "handleRequestsWithoutBody",
                        List.of(
                                java.lang.String.class
                        ),
                        this::handleRequestsWithoutBody,
                        false,
                        new ExactUrlRequestMappingRule(
                                "DELETE",
                                "/",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "HEAD",
                                "/",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "OPTIONS",
                                "/",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "PATCH",
                                "/",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "PUT",
                                "/",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "handleRequestsWithBody",
                        List.of(
                                java.lang.String.class
                        ),
                        this::handleRequestsWithBody,
                        false,
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/",
                                true
                        ),
                        new ExactUrlRequestMappingRule(
                                "PATCH",
                                "/",
                                true
                        ),
                        new ExactUrlRequestMappingRule(
                                "PUT",
                                "/",
                                true
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> handleRequestsWithoutBody(final PathVariableMapping pathVariableMapping,
                                                                    final HttpRequest request) {
        final $$VirtualRoutingUsingHttpBodyRequest req = virtualRoutingUsingHttpBodyRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        virtualRoutingUsingHttpBodyRequestConstraintValidator.validate(req);
        final HttpHeaders headers = HttpHeaders.of();
        restController.handleRequestsWithoutBody(req.parameter);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> handleRequestsWithBody(final PathVariableMapping pathVariableMapping,
                                                                 final HttpRequest request) {
        final $$VirtualRoutingUsingHttpBodyRequest2 req = virtualRoutingUsingHttpBodyRequest2ServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        virtualRoutingUsingHttpBodyRequest2ConstraintValidator.validate(req);
        final HttpHeaders headers = HttpHeaders.of();
        restController.handleRequestsWithBody(req.parameter);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private HttpResponse buildResponse(final int statusCode,
                                       final HttpHeaders headers) {
        final HttpResponse response = httpResponseBuilder.build();
        response.setStatus(statusCode);
        response.setOrAddHeaders(headers);
        return response;
    }
}
