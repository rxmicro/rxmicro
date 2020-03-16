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
public final class $$VirtualRequest2ModelReader extends ModelReader<$$VirtualRequest2> {

    @Override
    public $$VirtualRequest2 read(final PathVariableMapping pathVariableMapping,
                                  final HttpRequest request,
                                  final boolean readParametersFromBody) {
        final $$VirtualRequest2 model = new $$VirtualRequest2();
        final QueryParams params = extractParams(request.getQueryString());
        model.id = toInteger(params.getValue("id"), HttpModelType.parameter, "id");
        return model;
    }
}
