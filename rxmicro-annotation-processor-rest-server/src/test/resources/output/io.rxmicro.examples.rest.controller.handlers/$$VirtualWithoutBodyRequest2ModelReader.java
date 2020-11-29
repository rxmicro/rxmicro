package io.rxmicro.examples.rest.controller.handlers;

import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualWithoutBodyRequest2ModelReader extends ModelReader<$$VirtualWithoutBodyRequest2> {

    @Override
    public $$VirtualWithoutBodyRequest2 read(final PathVariableMapping pathVariableMapping,
                                             final HttpRequest request,
                                             final boolean readParametersFromBody) {
        final $$VirtualWithoutBodyRequest2 model = new $$VirtualWithoutBodyRequest2();
        final QueryParams params = extractParams(request.getQueryString());
        read(pathVariableMapping, request, params, model);
        return model;
    }

    protected void read(final PathVariableMapping pathVariableMapping,
                        final HttpRequest request,
                        final QueryParams params,
                        final $$VirtualWithoutBodyRequest2 model) {
        model.requestParameter = toString(params.getValue("requestParameter"), HttpModelType.PARAMETER, "requestParameter");
    }
}
