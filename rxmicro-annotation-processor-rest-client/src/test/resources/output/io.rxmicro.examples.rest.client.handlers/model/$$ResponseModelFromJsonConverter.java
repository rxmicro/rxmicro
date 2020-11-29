package io.rxmicro.examples.rest.client.handlers.model;

import io.rxmicro.exchange.json.detail.ModelFromJsonConverter;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ResponseModelFromJsonConverter extends ModelFromJsonConverter<Response> {

    @Override
    public Response fromJsonObject(final Map<String, Object> params) {
        final Response model = new Response();
        readBody(params, model);
        return model;
    }

    protected void readBody(final Map<String, Object> params,
                            final Response model) {
        model.message = toString(params.get("message"), "message");
    }
}
