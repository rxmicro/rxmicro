package io.rxmicro.examples.rest.controller.path.variables;

import io.rxmicro.examples.rest.controller.path.variables.model.$$RequestConstraintValidator;
import io.rxmicro.examples.rest.controller.path.variables.model.$$RequestServerModelReader;
import io.rxmicro.examples.rest.controller.path.variables.model.Request;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.model.UrlSegments;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.RestControllerRegistrar;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.WithUrlPathVariablesRequestMappingRule;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$MicroService extends AbstractRestController {

    private MicroService restController;

    private $$RequestServerModelReader requestServerModelReader;

    private $$VirtualRequestServerModelReader virtualRequestServerModelReader;

    private $$RequestConstraintValidator requestConstraintValidator;

    private $$VirtualRequestConstraintValidator virtualRequestConstraintValidator;

    @Override
    protected void postConstruct() {
        restController = new MicroService();
        requestServerModelReader = new $$RequestServerModelReader();
        virtualRequestServerModelReader = new $$VirtualRequestServerModelReader();
        requestConstraintValidator = new $$RequestConstraintValidator();
        virtualRequestConstraintValidator = new $$VirtualRequestConstraintValidator();
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
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.path.variables.model.Request.class
                        ),
                        this::consumeRequest,
                        false,
                        new WithUrlPathVariablesRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/1/?/?/?",
                                        List.of("category", "class", "subType")
                                ),
                                false
                        ),

                        new WithUrlPathVariablesRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/1/?/?_?",
                                        List.of("category", "class", "subType")
                                ),
                                false
                        ),

                        new WithUrlPathVariablesRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/1/?-?-?",
                                        List.of("category", "class", "subType")
                                ),
                                false
                        ),

                        new WithUrlPathVariablesRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/1-?-?-?",
                                        List.of("category", "class", "subType")
                                ),
                                false
                        )

                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                java.lang.String.class, java.lang.String.class, java.lang.String.class
                        ),
                        this::consumeStringStringString,
                        false,
                        new WithUrlPathVariablesRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/2/?/?/?",
                                        List.of("category", "class", "subType")
                                ),
                                false
                        ),

                        new WithUrlPathVariablesRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/2/?/?_?",
                                        List.of("category", "class", "subType")
                                ),
                                false
                        ),

                        new WithUrlPathVariablesRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/2/?-?-?",
                                        List.of("category", "class", "subType")
                                ),
                                false
                        ),

                        new WithUrlPathVariablesRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/2-?-?-?",
                                        List.of("category", "class", "subType")
                                ),
                                false
                        )

                )
        );
    }

    private CompletionStage<HttpResponse> consumeRequest(final PathVariableMapping pathVariableMapping,
                                                         final HttpRequest request) {
        final Request req = requestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        requestConstraintValidator.validate(req);
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeStringStringString(final PathVariableMapping pathVariableMapping,
                                                                    final HttpRequest request) {
        final $$VirtualRequest req = virtualRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        virtualRequestConstraintValidator.validate(req);
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req.category, req.type, req.subType);
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
