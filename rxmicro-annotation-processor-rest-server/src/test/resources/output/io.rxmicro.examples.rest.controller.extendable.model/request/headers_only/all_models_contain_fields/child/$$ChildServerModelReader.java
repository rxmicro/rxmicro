package io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.all_models_contain_fields.child;

import io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.all_models_contain_fields.parent.$$ParentServerModelReader;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ChildServerModelReader extends ServerModelReader<Child> {

    private final $$ParentServerModelReader parentReader =
            new $$ParentServerModelReader();

    @Override
    public Child read(final PathVariableMapping pathVariableMapping,
                      final HttpRequest request,
                      final boolean readParametersFromBody) {
        final Child model = new Child();
        readPrimitivesToModel(pathVariableMapping, request, QueryParams.of(), model, readParametersFromBody);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final Child model,
                                      final boolean readParametersFromBody) {
        parentReader.readPrimitivesToModel(pathVariableMapping, request, params, model, readParametersFromBody);
        final HttpHeaders httpHeaders = request.getHeaders();
        model.childHeader = toString(httpHeaders.getValue("childHeader"), HttpModelType.HEADER, "childHeader");
    }
}
