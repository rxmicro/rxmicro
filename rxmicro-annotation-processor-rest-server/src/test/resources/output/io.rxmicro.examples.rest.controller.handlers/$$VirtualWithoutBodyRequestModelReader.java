package io.rxmicro.examples.rest.controller.handlers;

import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$VirtualWithoutBodyRequestModelReader extends ModelReader<$$VirtualWithoutBodyRequest> {

    @Override
    public $$VirtualWithoutBodyRequest read(final PathVariableMapping pathVariableMapping,
                                            final HttpRequest request,
                                            final boolean readParametersFromBody) {
        final $$VirtualWithoutBodyRequest model = new $$VirtualWithoutBodyRequest();
        final QueryParams params = extractParams(request.getQueryString());
        model.requestParameter = toString(params.getValue("requestParameter"), HttpModelType.parameter, "requestParameter");
        return model;
    }
}
