package io.rxmicro.examples.validation.client.all.types;

import io.rxmicro.exchange.json.detail.ModelToJsonConverter;
import io.rxmicro.json.JsonObjectBuilder;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualREST2Request2ModelToJsonConverter extends ModelToJsonConverter<$$VirtualREST2Request2> {

    @Override
    public Map<String, Object> toJsonObject(final $$VirtualREST2Request2 model) {
        final JsonObjectBuilder builder = new JsonObjectBuilder();
        putValuesToBuilder(model, builder);
        return builder.build();
    }

    protected void putValuesToBuilder(final $$VirtualREST2Request2 model,
                                      final JsonObjectBuilder builder) {
        builder.put("email", model.email);
    }
}
