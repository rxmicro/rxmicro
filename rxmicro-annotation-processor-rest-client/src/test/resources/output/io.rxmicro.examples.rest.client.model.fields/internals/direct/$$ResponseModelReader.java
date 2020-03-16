package io.rxmicro.examples.rest.client.model.fields.internals.direct;

import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.rest.client.detail.ModelReader;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$ResponseModelReader extends ModelReader<Response> {

    @Override
    public Response readSingle(final ClientHttpResponse response) {
        final Response model = new Response();
        model.status = response.statusCode();
        model.version = response.version();
        model.headers = response.headers();
        model.body = response.bodyAsBytes();
        return model;
    }
}
