package io.rxmicro.examples.rest.controller.cors;

import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualComplexCORSRequestModelReader extends ModelReader<$$VirtualComplexCORSRequest> {

    @Override
    public $$VirtualComplexCORSRequest read(final PathVariableMapping pathVariableMapping,
                                            final HttpRequest request,
                                            final boolean readParametersFromBody) {
        final $$VirtualComplexCORSRequest model = new $$VirtualComplexCORSRequest();
        readPrimitivesToModel(pathVariableMapping, request, QueryParams.of(), model, readParametersFromBody);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final $$VirtualComplexCORSRequest model,
                                      final boolean readParametersFromBody) {
        model.path = toString(pathVariableMapping.getValue("path"), HttpModelType.PATH, "path");
    }
}
