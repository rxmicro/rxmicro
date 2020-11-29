package io.rxmicro.examples.rest.client.model.fields.internals.reflection;

import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.rest.client.detail.ModelReader;

import static rxmicro.$$Reflections.setFieldValue;

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
        setFieldValue(model, "status", response.getStatusCode());
        setFieldValue(model, "version", response.getVersion());
        setFieldValue(model, "headers", response.getHeaders());
        setFieldValue(model, "body", response.getBodyAsBytes());
    }
}
