package io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields.parent;

import io.rxmicro.exchange.json.detail.ModelToJsonConverter;
import io.rxmicro.json.JsonObjectBuilder;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ParentModelToJsonConverter extends ModelToJsonConverter<Parent> {

    @Override
    public Map<String, Object> toJsonObject(final Parent model) {
        final JsonObjectBuilder builder = new JsonObjectBuilder();
        putValuesToBuilder(model, builder);
        return builder.build();
    }

    public void putValuesToBuilder(final Parent model,
                                   final JsonObjectBuilder builder) {
        builder.put("parentParameter", model.parentParameter);
    }
}
