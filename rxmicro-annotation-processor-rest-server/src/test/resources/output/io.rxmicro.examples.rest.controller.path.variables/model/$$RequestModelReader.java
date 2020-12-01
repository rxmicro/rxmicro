package io.rxmicro.examples.rest.controller.path.variables.model;

import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$RequestModelReader extends ModelReader<Request> {

    @Override
    public Request read(final PathVariableMapping pathVariableMapping,
                        final HttpRequest request,
                        final boolean readParametersFromBody) {
        final Request model = new Request();
        readPrimitivesToModel(pathVariableMapping, request, model);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final Request model) {
        model.category = toString(pathVariableMapping.getValue("category"), HttpModelType.PATH, "category");
        model.type = toString(pathVariableMapping.getValue("class"), HttpModelType.PATH, "class");
        model.subType = toString(pathVariableMapping.getValue("subType"), HttpModelType.PATH, "subType");
    }
}
