package io.rxmicro.examples.rest.client.model.field.access.params.gettersetter.nested;

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
        putValuesToBuilder(model, builder);
        return builder.build();
    }

    public void putValuesToBuilder(final Nested model,
                                   final JsonObjectBuilder builder) {
        builder.put("value", model.getValue());
    }
}
