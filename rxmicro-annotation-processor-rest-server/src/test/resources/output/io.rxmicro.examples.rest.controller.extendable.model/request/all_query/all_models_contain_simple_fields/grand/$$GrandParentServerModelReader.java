package io.rxmicro.examples.rest.controller.extendable.model.request.all_query.all_models_contain_simple_fields.grand;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$GrandParentServerModelReader extends ServerModelReader<GrandParent> {

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final GrandParent model,
                                      final boolean readParametersFromBody) {
        model.httpVersion = request.getVersion();
        model.grandVar = toString(pathVariableMapping.getValue("grandVar"), HttpModelType.PATH, "grandVar");
        final HttpHeaders httpHeaders = request.getHeaders();
        model.grandHeader = toString(httpHeaders.getValue("grandHeader"), HttpModelType.HEADER, "grandHeader");
        model.grandParameter = toString(params.getValue("grandParameter"), HttpModelType.PARAMETER, "grandParameter");
    }
}
