package io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_simple_fields.child;

import io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_simple_fields.parent.$$ParentModelToJsonConverter;
import io.rxmicro.exchange.json.detail.ModelToJsonConverter;
import io.rxmicro.json.JsonObjectBuilder;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ChildModelToJsonConverter extends ModelToJsonConverter<Child> {

    private final $$ParentModelToJsonConverter parentConverter =
            new $$ParentModelToJsonConverter();

    @Override
    public Map<String, Object> toJsonObject(final Child model) {
        final JsonObjectBuilder builder = new JsonObjectBuilder();
        putValuesToBuilder(model, builder);
        return builder.build();
    }

    public void putValuesToBuilder(final Child model,
                                   final JsonObjectBuilder builder) {
        parentConverter.putValuesToBuilder(model, builder);
        builder.put("childParameter", model.childParameter);
    }
}
