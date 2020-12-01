package io.rxmicro.examples.rest.controller.model.field.access.internals;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualVirtualInternalsRequestModelReader extends ModelReader<$$VirtualVirtualInternalsRequest> {

    @Override
    public $$VirtualVirtualInternalsRequest read(final PathVariableMapping pathVariableMapping,
                                                 final HttpRequest request,
                                                 final boolean readParametersFromBody) {
        final $$VirtualVirtualInternalsRequest model = new $$VirtualVirtualInternalsRequest();
        readPrimitivesToModel(pathVariableMapping, request, model);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final $$VirtualVirtualInternalsRequest model) {
        model.internalRemoteAddress1 = String.valueOf(request.getRemoteAddress());
        model.internalRemoteAddress2 = request.getRemoteAddress();
        model.internalUrlPath = request.getUri();
        model.internalRequestMethod = request.getMethod();
        model.internalHttpVersion = request.getVersion();
        model.internalRequestHeaders = request.getHeaders();
        model.internalRequestBody = request.getContent();
        model.internalRequest = request;
        final HttpHeaders httpHeaders = request.getHeaders();
        model.internalRequestId = toString(httpHeaders.getValue("Request-Id"), HttpModelType.HEADER, "Request-Id");
    }
}
