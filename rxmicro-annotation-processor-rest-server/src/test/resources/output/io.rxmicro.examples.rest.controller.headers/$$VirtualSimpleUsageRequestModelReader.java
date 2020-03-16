package io.rxmicro.examples.rest.controller.headers;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$VirtualSimpleUsageRequestModelReader extends ModelReader<$$VirtualSimpleUsageRequest> {

    @Override
    public $$VirtualSimpleUsageRequest read(final PathVariableMapping pathVariableMapping,
                                            final HttpRequest request,
                                            final boolean readParametersFromBody) {
        final $$VirtualSimpleUsageRequest model = new $$VirtualSimpleUsageRequest();
        final HttpHeaders httpHeaders = request.getHeaders();
        model.endpointVersion = toString(httpHeaders.getValue("Endpoint-Version"), HttpModelType.header, "Endpoint-Version");
        model.useProxy = toBoolean(httpHeaders.getValue("UseProxy"), HttpModelType.header, "UseProxy");
        return model;
    }
}
