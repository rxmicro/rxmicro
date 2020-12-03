package io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields;

import io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields.child.$$ChildConstraintValidator;
import io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields.child.$$ChildModelReader;
import io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields.child.$$ChildModelWriter;
import io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields.child.Child;
import io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields.parent.$$ParentConstraintValidator;
import io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields.parent.$$ParentModelReader;
import io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields.parent.$$ParentModelWriter;
import io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields.parent.Parent;
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
public final class $$MicroService extends AbstractRestController {

    private MicroService restController;

    private $$ChildModelReader childModelReader;

    private $$ParentModelReader parentModelReader;

    private $$ChildModelWriter childModelWriter;

    private $$ParentModelWriter parentModelWriter;

    private $$ChildConstraintValidator childConstraintValidator;

    private $$ParentConstraintValidator parentConstraintValidator;

    @Override
    protected void postConstruct() {
        restController = new MicroService();
        childModelReader = new $$ChildModelReader();
        parentModelReader = new $$ParentModelReader();
        childModelWriter = new $$ChildModelWriter(restServerConfig.isHumanReadableOutput());
        parentModelWriter = new $$ParentModelWriter(restServerConfig.isHumanReadableOutput());
        childConstraintValidator = new $$ChildConstraintValidator();
        parentConstraintValidator = new $$ParentConstraintValidator();
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
                                io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields.child.Child.class
                        ),
                        this::consumeChild,
                        false,
                        new ExactUrlRequestMappingRule(
                                "PUT",
                                "/1",
                                true
                        )
                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields.parent.Parent.class
                        ),
                        this::consumeParent,
                        false,
                        new ExactUrlRequestMappingRule(
                                "PUT",
                                "/2",
                                true
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> consumeChild(final PathVariableMapping pathVariableMapping,
                                                       final HttpRequest request) {
        final Child req = childModelReader.read(pathVariableMapping, request, request.isContentPresent());
        childConstraintValidator.validate(req);
        final HttpHeaders headers = HttpHeaders.of();
        return restController.consume(req)
                .thenApply(response -> buildResponse(response, 200, headers));
    }

    private CompletionStage<HttpResponse> consumeParent(final PathVariableMapping pathVariableMapping,
                                                        final HttpRequest request) {
        final Parent req = parentModelReader.read(pathVariableMapping, request, request.isContentPresent());
        parentConstraintValidator.validate(req);
        final HttpHeaders headers = HttpHeaders.of();
        return restController.consume(req)
                .thenApply(response -> buildResponse(response, 200, headers));
    }

    private HttpResponse buildResponse(final Child model,
                                       final int statusCode,
                                       final HttpHeaders headers) {
        validateResponse(restServerConfig.isEnableAdditionalValidations(), childConstraintValidator, model);
        final HttpResponse response = httpResponseBuilder.build();
        response.setStatus(statusCode);
        response.setOrAddHeaders(headers);
        childModelWriter.write(model, response);
        return response;
    }

    private HttpResponse buildResponse(final Parent model,
                                       final int statusCode,
                                       final HttpHeaders headers) {
        validateResponse(restServerConfig.isEnableAdditionalValidations(), parentConstraintValidator, model);
        final HttpResponse response = httpResponseBuilder.build();
        response.setStatus(statusCode);
        response.setOrAddHeaders(headers);
        parentModelWriter.write(model, response);
        return response;
    }
}
