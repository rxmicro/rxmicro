package io.rxmicro.examples.rest.controller.headers.model;

import io.rxmicro.rest.server.detail.component.ModelWriter;
import io.rxmicro.rest.server.detail.model.HttpResponse;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ResponseModelWriter extends ModelWriter<Response> {

    public $$ResponseModelWriter(final boolean humanReadableOutput) {
        //do nothing
    }

    @Override
    public void write(final Response model,
                      final HttpResponse response) {
        setHeaders(model, response);
    }

    protected void setHeaders(final Response model,
                              final HttpResponse response) {
        response.setHeader("Endpoint-Version", model.endpointVersion);
        response.setHeader("UseProxy", model.useProxy);
    }
}
