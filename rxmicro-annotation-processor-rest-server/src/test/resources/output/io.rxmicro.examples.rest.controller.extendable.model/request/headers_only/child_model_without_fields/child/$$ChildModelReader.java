package io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.child_model_without_fields.child;

import io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.child_model_without_fields.parent.$$ParentModelReader;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ChildModelReader extends ModelReader<Child> {

    private final $$ParentModelReader parentReader =
            new $$ParentModelReader();

    @Override
    public Child read(final PathVariableMapping pathVariableMapping,
                      final HttpRequest request,
                      final boolean readParametersFromBody) {
        final Child model = new Child();
        readPrimitivesToModel(pathVariableMapping, request, model);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final Child model) {
        parentReader.readPrimitivesToModel(pathVariableMapping, request, model);
    }
}
