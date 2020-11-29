package io.rxmicro.examples.validation.client.basic.model;

import io.rxmicro.exchange.json.detail.ModelFromJsonConverter;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ResponseModelFromJsonConverter extends ModelFromJsonConverter<Response> {

    @Override
    public Response fromJsonObject(final Map<String, Object> params) {
        final Response model = new Response();
        readParamsToModel(params, model);
        return model;
    }

    protected void readParamsToModel(final Map<String, Object> params,
                                     final Response model) {
        model.email = toString(params.get("email"), "email");
    }
}
