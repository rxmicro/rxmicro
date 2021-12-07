package io.rxmicro.examples.rest.controller.model.types.model.request.without_any_fields;

import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$WithoutAnyFieldsRequestServerModelReader extends ServerModelReader<WithoutAnyFieldsRequest> {

    @Override
    public WithoutAnyFieldsRequest read(final PathVariableMapping pathVariableMapping,
                                        final HttpRequest request,
                                        final boolean readParametersFromBody) {
        return new WithoutAnyFieldsRequest();
    }
}