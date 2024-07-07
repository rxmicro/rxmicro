package io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_nested_fields.child;

import io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_nested_fields.child.nested.$$NestedModelToJsonConverter;
import io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_nested_fields.parent.$$ParentModelToJsonConverter;
import io.rxmicro.exchange.json.detail.ModelToJsonConverter;
import io.rxmicro.json.JsonObjectBuilder;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ChildModelToJsonConverter extends ModelToJsonConverter<Child> {

    private final $$ParentModelToJsonConverter parentConverter =
            new $$ParentModelToJsonConverter();

    private final $$NestedModelToJsonConverter nestedModelToJsonConverter =
            new $$NestedModelToJsonConverter();

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
        builder.put("childNestedParameter", convertFromObjectIfNotNull(nestedModelToJsonConverter, model.childNestedParameter));
    }
}