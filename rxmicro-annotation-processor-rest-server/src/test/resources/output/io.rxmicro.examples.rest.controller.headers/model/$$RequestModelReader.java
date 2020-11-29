package io.rxmicro.examples.rest.controller.headers.model;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$RequestModelReader extends ModelReader<Request> {

    @Override
    public Request read(final PathVariableMapping pathVariableMapping,
                        final HttpRequest request,
                        final boolean readParametersFromBody) {
        final Request model = new Request();
        read(pathVariableMapping, request, model);
        return model;
    }

    protected void read(final PathVariableMapping pathVariableMapping,
                        final HttpRequest request,
                        final Request model) {
        final HttpHeaders httpHeaders = request.getHeaders();
        model.endpointVersion = toString(httpHeaders.getValue("Endpoint-Version"), HttpModelType.HEADER, "Endpoint-Version");
        model.useProxy = toBoolean(httpHeaders.getValue("UseProxy"), HttpModelType.HEADER, "UseProxy");
    }
}
