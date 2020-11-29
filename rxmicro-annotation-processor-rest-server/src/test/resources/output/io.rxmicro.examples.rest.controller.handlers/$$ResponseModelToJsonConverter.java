package io.rxmicro.examples.rest.controller.handlers;

import io.rxmicro.exchange.json.detail.ModelToJsonConverter;
import io.rxmicro.json.JsonObjectBuilder;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ResponseModelToJsonConverter extends ModelToJsonConverter<Response> {

    @Override
    public Map<String, Object> toJsonObject(final Response model) {
        final JsonObjectBuilder builder = new JsonObjectBuilder();
        putValues(model, builder);
        return builder.build();
    }

    protected void putValues(final Response model,
                             final JsonObjectBuilder builder) {
        builder.put("message", model.message);
    }
}
