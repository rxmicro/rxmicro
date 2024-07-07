package io.rxmicro.examples.rest.controller.cors;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.model.UrlSegments;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.RestControllerRegistrar;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;
import io.rxmicro.rest.server.detail.model.mapping.WithUrlPathVariablesRequestMappingRule;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ComplexCORSMicroService extends AbstractRestController {

    private ComplexCORSMicroService restController;

    private $$VirtualComplexCORSRequestServerModelReader virtualComplexCORSRequestServerModelReader;

    private $$VirtualComplexCORSRequestConstraintValidator virtualComplexCORSRequestConstraintValidator;

    @Override
    protected void postConstruct() {
        restController = new ComplexCORSMicroService();
        virtualComplexCORSRequestServerModelReader = new $$VirtualComplexCORSRequestServerModelReader();
        virtualComplexCORSRequestConstraintValidator = new $$VirtualComplexCORSRequestConstraintValidator();
    }

    @Override
    public Class<?> getRestControllerClass() {
        return ComplexCORSMicroService.class;
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "/",
                        "handler1",
                        List.of(),
                        this::handler1,
                        true,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/handler1",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "HEAD",
                                "/handler1",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/handler1",
                                true
                        ),
                        new ExactUrlRequestMappingRule(
                                "PUT",
                                "/handler1",
                                true
                        )
                ),
                new Registration(
                        "/",
                        "handler2",
                        List.of(),
                        this::handler2,
                        true,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/handler2",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/handler2",
                                true
                        )
                ),
                new Registration(
                        "/",
                        "handler3",
                        List.of(
                                java.lang.String.class
                        ),
                        this::handler3,
                        true,
                        new WithUrlPathVariablesRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/?",
                                        List.of("path")
                                ),
                                false
                        ),

                        new WithUrlPathVariablesRequestMappingRule(
                                "HEAD",
                                new UrlSegments(
                                        "/?",
                                        List.of("path")
                                ),
                                false
                        ),

                        new WithUrlPathVariablesRequestMappingRule(
                                "POST",
                                new UrlSegments(
                                        "/?",
                                        List.of("path")
                                ),
                                true
                        )

                )
        );
    }

    private CompletionStage<HttpResponse> handler1(final PathVariableMapping pathVariableMapping,
                                                   final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of();
        restController.handler1();
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> handler2(final PathVariableMapping pathVariableMapping,
                                                   final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of();
        restController.handler2();
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> handler3(final PathVariableMapping pathVariableMapping,
                                                   final HttpRequest request) {
        final $$VirtualComplexCORSRequest req = virtualComplexCORSRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        virtualComplexCORSRequestConstraintValidator.validate(req);
        final HttpHeaders headers = HttpHeaders.of();
        restController.handler3(req.path);
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
