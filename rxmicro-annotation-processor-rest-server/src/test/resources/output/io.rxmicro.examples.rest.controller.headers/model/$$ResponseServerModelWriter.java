package io.rxmicro.examples.rest.controller.headers.model;

import io.rxmicro.rest.server.detail.component.ServerModelWriter;
import io.rxmicro.rest.server.detail.model.HttpResponse;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ResponseServerModelWriter extends ServerModelWriter<Response> {

    public $$ResponseServerModelWriter(final boolean humanReadableOutput) {
        //do nothing
    }

    @Override
    public void write(final Response model,
                      final HttpResponse response) {
        writePrimitivesToResponse(model, response);
    }

    public void writePrimitivesToResponse(final Response model,
                                          final HttpResponse response) {
        response.setHeader("Endpoint-Version", model.endpointVersion);
        response.setHeader("UseProxy", model.useProxy);
    }
}
