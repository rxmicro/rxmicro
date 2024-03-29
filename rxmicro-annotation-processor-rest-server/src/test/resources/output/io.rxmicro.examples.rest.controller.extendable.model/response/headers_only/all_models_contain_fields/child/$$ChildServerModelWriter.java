package io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.all_models_contain_fields.child;

import io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.all_models_contain_fields.parent.$$ParentServerModelWriter;
import io.rxmicro.rest.server.detail.component.ServerModelWriter;
import io.rxmicro.rest.server.detail.model.HttpResponse;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ChildServerModelWriter extends ServerModelWriter<Child> {

    private final $$ParentServerModelWriter parentWriter;

    public $$ChildServerModelWriter(final boolean humanReadableOutput) {
        parentWriter = new $$ParentServerModelWriter(humanReadableOutput);
    }

    @Override
    public void write(final Child model,
                      final HttpResponse response) {
        writePrimitivesToResponse(model, response);
    }

    public void writePrimitivesToResponse(final Child model,
                                          final HttpResponse response) {
        parentWriter.writePrimitivesToResponse(model, response);
        response.setHeader("childHeader", model.childHeader);
    }
}
