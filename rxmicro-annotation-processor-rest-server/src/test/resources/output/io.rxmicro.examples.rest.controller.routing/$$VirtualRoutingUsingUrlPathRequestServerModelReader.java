package io.rxmicro.examples.rest.controller.routing;

import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualRoutingUsingUrlPathRequestServerModelReader extends ServerModelReader<$$VirtualRoutingUsingUrlPathRequest> {

    @Override
    public $$VirtualRoutingUsingUrlPathRequest read(final PathVariableMapping pathVariableMapping,
                                                    final HttpRequest request,
                                                    final boolean readParametersFromBody) {
        final $$VirtualRoutingUsingUrlPathRequest model = new $$VirtualRoutingUsingUrlPathRequest();
        readPrimitivesToModel(pathVariableMapping, request, QueryParams.of(), model, readParametersFromBody);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final $$VirtualRoutingUsingUrlPathRequest model,
                                      final boolean readParametersFromBody) {
        model.type = toString(pathVariableMapping.getValue("type"), HttpModelType.PATH, "type");
    }
}
