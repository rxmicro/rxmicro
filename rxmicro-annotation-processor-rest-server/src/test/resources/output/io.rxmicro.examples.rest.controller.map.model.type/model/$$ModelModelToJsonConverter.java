package io.rxmicro.examples.rest.controller.map.model.type.model;

import io.rxmicro.examples.rest.controller.map.model.type.model.nested.$$NestedModelToJsonConverter;
import io.rxmicro.exchange.json.detail.ModelToJsonConverter;
import io.rxmicro.json.JsonObjectBuilder;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ModelModelToJsonConverter extends ModelToJsonConverter<Model> {

    private final $$NestedModelToJsonConverter nestedModelToJsonConverter =
            new $$NestedModelToJsonConverter();

    @Override
    public Map<String, Object> toJsonObject(final Model model) {
        final JsonObjectBuilder builder = new JsonObjectBuilder();
        putValuesToBuilder(model, builder);
        return builder.build();
    }

    public void putValuesToBuilder(final Model model,
                                   final JsonObjectBuilder builder) {
        builder.put("nested", convertFromMapIfNotNull(nestedModelToJsonConverter, model.nested));
    }
}
