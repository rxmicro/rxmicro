package io.rxmicro.examples.rest.controller.errors;

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
public final class $$VirtualRequestModelReader extends ModelReader<$$VirtualRequest> {

    @Override
    public $$VirtualRequest read(final PathVariableMapping pathVariableMapping,
                                 final HttpRequest request,
                                 final boolean readParametersFromBody) {
        final $$VirtualRequest model = new $$VirtualRequest();
        final QueryParams params = extractParams(request.getQueryString());
        model.id = toInteger(params.getValue("id"), HttpModelType.parameter, "id");
        return model;
    }
}
