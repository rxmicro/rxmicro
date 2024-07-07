package io.rxmicro.examples.rest.controller.headers;

import io.rxmicro.examples.rest.controller.headers.model.Status;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualListHeaderRequestServerModelReader extends ServerModelReader<$$VirtualListHeaderRequest> {

    @Override
    public $$VirtualListHeaderRequest read(final PathVariableMapping pathVariableMapping,
                                           final HttpRequest request,
                                           final boolean readParametersFromBody) {
        final $$VirtualListHeaderRequest model = new $$VirtualListHeaderRequest();
        readPrimitivesToModel(pathVariableMapping, request, QueryParams.of(), model, readParametersFromBody);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final $$VirtualListHeaderRequest model,
                                      final boolean readParametersFromBody) {
        final HttpHeaders httpHeaders = request.getHeaders();
        model.supportedStatuses = toEnumList(Status.class, httpHeaders.getValues("Supported-Statuses"), HttpModelType.HEADER, "Supported-Statuses");
    }
}
