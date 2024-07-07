package io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.parent_model_without_fields.grand;

import io.rxmicro.rest.server.detail.component.ServerModelWriter;
import io.rxmicro.rest.server.detail.model.HttpResponse;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$GrandParentServerModelWriter extends ServerModelWriter<GrandParent> {

    public $$GrandParentServerModelWriter(final boolean humanReadableOutput) {
        //do nothing
    }

    public void writePrimitivesToResponse(final GrandParent model,
                                          final HttpResponse response) {
        response.setHeader("grandHeader", model.grandHeader);
    }
}
