package io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_nested_fields.parent;

import io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_nested_fields.grand.$$GrandParentModelToJsonConverter;
import io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_nested_fields.parent.nested.$$NestedModelToJsonConverter;
import io.rxmicro.exchange.json.detail.ModelToJsonConverter;
import io.rxmicro.json.JsonObjectBuilder;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ParentModelToJsonConverter extends ModelToJsonConverter<Parent> {

    private final $$GrandParentModelToJsonConverter parentConverter =
            new $$GrandParentModelToJsonConverter();

    private final $$NestedModelToJsonConverter nestedModelToJsonConverter =
            new $$NestedModelToJsonConverter();

    @Override
    public Map<String, Object> toJsonObject(final Parent model) {
        final JsonObjectBuilder builder = new JsonObjectBuilder();
        putValuesToBuilder(model, builder);
        return builder.build();
    }

    public void putValuesToBuilder(final Parent model,
                                   final JsonObjectBuilder builder) {
        parentConverter.putValuesToBuilder(model, builder);
        builder.put("parentNestedParameter", convertFromObjectIfNotNull(nestedModelToJsonConverter, model.parentNestedParameter));
        builder.put("parentParameter", model.parentParameter);
    }
}
