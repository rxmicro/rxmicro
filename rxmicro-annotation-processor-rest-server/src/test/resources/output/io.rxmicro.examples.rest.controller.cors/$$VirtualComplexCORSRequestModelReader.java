package io.rxmicro.examples.rest.controller.cors;

import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$VirtualComplexCORSRequestModelReader extends ModelReader<$$VirtualComplexCORSRequest> {

    @Override
    public $$VirtualComplexCORSRequest read(final PathVariableMapping pathVariableMapping,
                                            final HttpRequest request,
                                            final boolean readParametersFromBody) {
        final $$VirtualComplexCORSRequest model = new $$VirtualComplexCORSRequest();
        model.path = toString(pathVariableMapping.getValue("path"), HttpModelType.path, "path");
        return model;
    }
}
