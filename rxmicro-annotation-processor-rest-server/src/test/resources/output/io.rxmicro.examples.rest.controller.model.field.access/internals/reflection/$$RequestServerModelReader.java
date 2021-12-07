package io.rxmicro.examples.rest.controller.model.field.access.internals.reflection;

import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

import static rxmicro.$$Reflections.setFieldValue;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$RequestServerModelReader extends ServerModelReader<Request> {

    @Override
    public Request read(final PathVariableMapping pathVariableMapping,
                        final HttpRequest request,
                        final boolean readParametersFromBody) {
        final Request model = new Request();
        readPrimitivesToModel(pathVariableMapping, request, QueryParams.of(), model, readParametersFromBody);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final Request model,
                                      final boolean readParametersFromBody) {
        setFieldValue(model, "internalRemoteAddress1", String.valueOf(request.getRemoteAddress()));
        setFieldValue(model, "internalRemoteAddress2", request.getRemoteAddress());
        setFieldValue(model, "internalUrlPath", request.getUri());
        setFieldValue(model, "internalRequestMethod", request.getMethod());
        setFieldValue(model, "internalHttpVersion", request.getVersion());
        setFieldValue(model, "internalRequestHeaders", request.getHeaders());
        setFieldValue(model, "internalRequestBody", request.getContent());
        setFieldValue(model, "internalRequest", request);
    }
}