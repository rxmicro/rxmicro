package io.rxmicro.examples.rest.controller.notfound;

import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualNotFoundRequest2ModelReader extends ModelReader<$$VirtualNotFoundRequest2> {

    @Override
    public $$VirtualNotFoundRequest2 read(final PathVariableMapping pathVariableMapping,
                                          final HttpRequest request,
                                          final boolean readParametersFromBody) {
        final $$VirtualNotFoundRequest2 model = new $$VirtualNotFoundRequest2();
        final QueryParams params = extractParams(request.getQueryString());
        read(pathVariableMapping, request, params, model);
        return model;
    }

    protected void read(final PathVariableMapping pathVariableMapping,
                        final HttpRequest request,
                        final QueryParams params,
                        final $$VirtualNotFoundRequest2 model) {
        model.found = toBoolean(params.getValue("found"), HttpModelType.PARAMETER, "found");
    }
}
