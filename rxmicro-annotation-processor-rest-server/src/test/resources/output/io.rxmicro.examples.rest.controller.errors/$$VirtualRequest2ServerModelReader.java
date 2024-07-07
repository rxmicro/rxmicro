package io.rxmicro.examples.rest.controller.errors;

import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualRequest2ServerModelReader extends ServerModelReader<$$VirtualRequest2> {

    @Override
    public $$VirtualRequest2 read(final PathVariableMapping pathVariableMapping,
                                  final HttpRequest request,
                                  final boolean readParametersFromBody) {
        final $$VirtualRequest2 model = new $$VirtualRequest2();
        final QueryParams params = extractParams(request.getQueryString());
        readPrimitivesToModel(pathVariableMapping, request, params, model, readParametersFromBody);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final $$VirtualRequest2 model,
                                      final boolean readParametersFromBody) {
        model.id = toInteger(params.getValue("id"), HttpModelType.PARAMETER, "id");
    }
}