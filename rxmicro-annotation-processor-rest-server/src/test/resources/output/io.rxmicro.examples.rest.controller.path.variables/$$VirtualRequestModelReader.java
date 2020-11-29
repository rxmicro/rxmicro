package io.rxmicro.examples.rest.controller.path.variables;

import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualRequestModelReader extends ModelReader<$$VirtualRequest> {

    @Override
    public $$VirtualRequest read(final PathVariableMapping pathVariableMapping,
                                 final HttpRequest request,
                                 final boolean readParametersFromBody) {
        final $$VirtualRequest model = new $$VirtualRequest();
        read(pathVariableMapping, request, model);
        return model;
    }

    protected void read(final PathVariableMapping pathVariableMapping,
                        final HttpRequest request,
                        final $$VirtualRequest model) {
        model.category = toString(pathVariableMapping.getValue("category"), HttpModelType.PATH, "category");
        model.type = toString(pathVariableMapping.getValue("class"), HttpModelType.PATH, "class");
        model.subType = toString(pathVariableMapping.getValue("subType"), HttpModelType.PATH, "subType");
    }
}
