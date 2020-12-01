package io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.child_model_without_fields.parent;

import io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.child_model_without_fields.grand.$$GrandParentModelReader;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ParentModelReader extends ModelReader<Parent> {

    private final $$GrandParentModelReader parentReader =
            new $$GrandParentModelReader();

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final Parent model) {
        parentReader.readPrimitivesToModel(pathVariableMapping, request, model);
        final HttpHeaders httpHeaders = request.getHeaders();
        model.parentHeader = toString(httpHeaders.getValue("parentHeader"), HttpModelType.HEADER, "parentHeader");
    }
}
