package io.rxmicro.examples.rest.client.model.field.access.params.direct;

import io.rxmicro.rest.client.detail.HttpResponse;
import io.rxmicro.rest.client.detail.ModelReader;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ResponseModelReader extends ModelReader<Response> {

    private final $$ResponseModelFromJsonConverter responseModelFromJsonConverter =
            new $$ResponseModelFromJsonConverter();

    @Override
    public Response readSingle(final HttpResponse response) {
        return responseModelFromJsonConverter.fromJsonObject(response.getBody());
    }
}
