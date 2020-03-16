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
public final class $$VirtualWithoutBodyRequest3ModelReader extends ModelReader<$$VirtualWithoutBodyRequest3> {

    @Override
    public $$VirtualWithoutBodyRequest3 read(final PathVariableMapping pathVariableMapping,
                                             final HttpRequest request,
                                             final boolean readParametersFromBody) {
        final $$VirtualWithoutBodyRequest3 model = new $$VirtualWithoutBodyRequest3();
        final QueryParams params = extractParams(request.getQueryString());
        model.requestParameter = toString(params.getValue("requestParameter"), HttpModelType.parameter, "requestParameter");
        return model;
    }
}
