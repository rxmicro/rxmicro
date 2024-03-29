package io.rxmicro.examples.rest.controller.extendable.model.request.all_query.grand_parent_model_without_fields.child;

import io.rxmicro.examples.rest.controller.extendable.model.request.all_query.grand_parent_model_without_fields.parent.$$ParentServerModelReader;
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
        final QueryParams params = extractParams(request.getQueryString());
        readPrimitivesToModel(pathVariableMapping, request, params, model, readParametersFromBody);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final Child model,
                                      final boolean readParametersFromBody) {
        parentReader.readPrimitivesToModel(pathVariableMapping, request, params, model, readParametersFromBody);
        model.method = request.getMethod();
        model.childVar = toString(pathVariableMapping.getValue("childVar"), HttpModelType.PATH, "childVar");
        final HttpHeaders httpHeaders = request.getHeaders();
        model.childHeader = toString(httpHeaders.getValue("childHeader"), HttpModelType.HEADER, "childHeader");
        model.childParameter = toString(params.getValue("childParameter"), HttpModelType.PARAMETER, "childParameter");
    }
}
