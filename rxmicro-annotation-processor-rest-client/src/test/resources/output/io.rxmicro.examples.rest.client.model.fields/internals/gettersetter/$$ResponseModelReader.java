package io.rxmicro.examples.rest.client.model.fields.internals.gettersetter;

import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.rest.client.detail.ModelReader;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ResponseModelReader extends ModelReader<Response> {

    @Override
    public Response readSingle(final ClientHttpResponse response) {
        final Response model = new Response();
        read(response, model);
        return model;
    }

    protected void read(final ClientHttpResponse response,
                        final Response model) {
        model.setStatus(response.getStatusCode());
        model.setVersion(response.getVersion());
        model.setHeaders(response.getHeaders());
        model.setBody(response.getBodyAsBytes());
    }
}
