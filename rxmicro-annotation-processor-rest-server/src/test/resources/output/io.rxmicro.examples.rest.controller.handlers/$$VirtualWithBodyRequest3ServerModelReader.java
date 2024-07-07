package io.rxmicro.examples.rest.controller.handlers;

import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualWithBodyRequest3ServerModelReader extends ServerModelReader<$$VirtualWithBodyRequest3> {

    @Override
    public $$VirtualWithBodyRequest3 read(final PathVariableMapping pathVariableMapping,
                                          final HttpRequest request,
                                          final boolean readParametersFromBody) {
        final $$VirtualWithBodyRequest3 model = new $$VirtualWithBodyRequest3();
        final QueryParams params = extractParams(request.getQueryString());
        readPrimitivesToModel(pathVariableMapping, request, params, model, readParametersFromBody);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final $$VirtualWithBodyRequest3 model,
                                      final boolean readParametersFromBody) {
        model.requestParameter = toString(params.getValue("requestParameter"), HttpModelType.PARAMETER, "requestParameter");
    }
}
