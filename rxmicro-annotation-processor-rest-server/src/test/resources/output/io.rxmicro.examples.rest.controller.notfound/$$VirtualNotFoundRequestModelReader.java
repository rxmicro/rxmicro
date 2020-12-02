package io.rxmicro.examples.rest.controller.notfound;

import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualNotFoundRequestModelReader extends ModelReader<$$VirtualNotFoundRequest> {

    @Override
    public $$VirtualNotFoundRequest read(final PathVariableMapping pathVariableMapping,
                                         final HttpRequest request,
                                         final boolean readParametersFromBody) {
        final $$VirtualNotFoundRequest model = new $$VirtualNotFoundRequest();
        final QueryParams params = extractParams(request.getQueryString());
        readPrimitivesToModel(pathVariableMapping, request, params, model, readParametersFromBody);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final $$VirtualNotFoundRequest model,
                                      final boolean readParametersFromBody) {
        model.found = toBoolean(params.getValue("found"), HttpModelType.PARAMETER, "found");
    }
}
