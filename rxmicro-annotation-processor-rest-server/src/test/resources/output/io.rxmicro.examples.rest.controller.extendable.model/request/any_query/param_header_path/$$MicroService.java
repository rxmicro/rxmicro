package io.rxmicro.examples.rest.controller.extendable.model.request.any_query.param_header_path;

import io.rxmicro.examples.rest.controller.extendable.model.request.any_query.param_header_path.child.$$ChildConstraintValidator;
import io.rxmicro.examples.rest.controller.extendable.model.request.any_query.param_header_path.child.$$ChildServerModelReader;
import io.rxmicro.examples.rest.controller.extendable.model.request.any_query.param_header_path.child.Child;
import io.rxmicro.examples.rest.controller.extendable.model.request.any_query.param_header_path.parent.$$ParentConstraintValidator;
import io.rxmicro.examples.rest.controller.extendable.model.request.any_query.param_header_path.parent.$$ParentServerModelReader;
import io.rxmicro.examples.rest.controller.extendable.model.request.any_query.param_header_path.parent.Parent;
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

    private $$ParentServerModelReader parentServerModelReader;

    private $$ChildServerModelReader childServerModelReader;

    private $$ParentConstraintValidator parentConstraintValidator;

    private $$ChildConstraintValidator childConstraintValidator;

    @Override
    protected void postConstruct() {
        restController = new MicroService();
        parentServerModelReader = new $$ParentServerModelReader();
        childServerModelReader = new $$ChildServerModelReader();
        parentConstraintValidator = new $$ParentConstraintValidator();
        childConstraintValidator = new $$ChildConstraintValidator();
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
                                io.rxmicro.examples.rest.controller.extendable.model.request.any_query.param_header_path.child.Child.class
                        ),
                        this::consumeChild,
                        false,
                        new WithUrlPathVariablesRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/1/?",
                                        List.of("grandVar")
                                ),
                                false
                        )

                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.extendable.model.request.any_query.param_header_path.parent.Parent.class
                        ),
                        this::consumeParent,
                        false,
                        new WithUrlPathVariablesRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/2/?",
                                        List.of("grandVar")
                                ),
                                false
                        )

                )
        );
    }

    private CompletionStage<HttpResponse> consumeChild(final PathVariableMapping pathVariableMapping,
                                                       final HttpRequest request) {
        final Child req = childServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        childConstraintValidator.validate(req);
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeParent(final PathVariableMapping pathVariableMapping,
                                                        final HttpRequest request) {
        final Parent req = parentServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        parentConstraintValidator.validate(req);
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
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
