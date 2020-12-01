package io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.parent_model_without_fields.grand;

import io.rxmicro.rest.server.detail.component.ModelWriter;
import io.rxmicro.rest.server.detail.model.HttpResponse;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$GrandParentModelWriter extends ModelWriter<GrandParent> {

    public $$GrandParentModelWriter(final boolean humanReadableOutput) {
        //do nothing
    }

    public void writePrimitivesToResponse(final GrandParent model,
                                          final HttpResponse response) {
        response.setHeader("grandHeader", model.grandHeader);
    }
}
