package io.rxmicro.examples.rest.client.model.fields.params.gettersetter.nested;

import io.rxmicro.exchange.json.detail.ModelToJsonConverter;
import io.rxmicro.json.JsonObjectBuilder;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$NestedModelToJsonConverter extends ModelToJsonConverter<Nested> {

    @Override
    public Map<String, Object> toJsonObject(final Nested model) {
        final JsonObjectBuilder builder = new JsonObjectBuilder();
        putValues(model, builder);
        return builder.build();
    }

    protected void putValues(final Nested model,
                             final JsonObjectBuilder builder) {
        builder.put("value", model.getValue());
    }
}
