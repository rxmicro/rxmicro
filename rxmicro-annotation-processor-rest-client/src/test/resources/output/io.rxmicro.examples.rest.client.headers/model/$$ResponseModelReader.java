package io.rxmicro.examples.rest.client.headers.model;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.rest.client.detail.ModelReader;
import io.rxmicro.rest.model.HttpModelType;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$ResponseModelReader extends ModelReader<Response> {

    @Override
    public Response readSingle(final ClientHttpResponse response) {
        final Response model = new Response();
        final HttpHeaders httpHeaders = response.headers();
        model.endpointVersion = toString(httpHeaders.getValue("Endpoint-Version"), HttpModelType.header, "Endpoint-Version");
        model.useProxy = toBoolean(httpHeaders.getValue("UseProxy"), HttpModelType.header, "UseProxy");
        return model;
    }
}
