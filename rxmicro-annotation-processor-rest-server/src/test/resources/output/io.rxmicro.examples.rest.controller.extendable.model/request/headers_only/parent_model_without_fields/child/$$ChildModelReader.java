package io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.parent_model_without_fields.child;

import io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.parent_model_without_fields.grand.$$GrandParentModelReader;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ChildModelReader extends ModelReader<Child> {

    private final $$GrandParentModelReader parentReader =
            new $$GrandParentModelReader();

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
        final HttpHeaders httpHeaders = request.getHeaders();
        model.childHeader = toString(httpHeaders.getValue("childHeader"), HttpModelType.HEADER, "childHeader");
    }
}
