package io.rxmicro.examples.rest.client.model.field.access.internals.direct;

import io.rxmicro.rest.client.detail.HttpResponse;
import io.rxmicro.rest.client.detail.ModelReader;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ResponseModelReader extends ModelReader<Response> {

    @Override
    public Response readSingle(final HttpResponse response) {
        final Response model = new Response();
        readPrimitivesToModel(response, model);
        return model;
    }

    protected void readPrimitivesToModel(final HttpResponse response,
                                         final Response model) {
        model.internalResponseStatusCode = response.getStatusCode();
        model.internalHttpVersion = response.getVersion();
        model.internalHttpHeaders = response.getHeaders();
        model.internalResponseBody = response.getBodyAsBytes();
    }
}
