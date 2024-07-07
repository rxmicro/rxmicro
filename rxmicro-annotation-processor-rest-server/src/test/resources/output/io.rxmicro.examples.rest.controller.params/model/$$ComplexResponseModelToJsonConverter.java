package io.rxmicro.examples.rest.controller.params.model;

import io.rxmicro.examples.rest.controller.params.model.nested.$$NestedModelModelToJsonConverter;
import io.rxmicro.exchange.json.detail.ModelToJsonConverter;
import io.rxmicro.json.JsonObjectBuilder;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ComplexResponseModelToJsonConverter extends ModelToJsonConverter<ComplexResponse> {

    private final $$NestedModelModelToJsonConverter nestedModelModelToJsonConverter =
            new $$NestedModelModelToJsonConverter();

    @Override
    public Map<String, Object> toJsonObject(final ComplexResponse model) {
        final JsonObjectBuilder builder = new JsonObjectBuilder();
        putValuesToBuilder(model, builder);
        return builder.build();
    }

    public void putValuesToBuilder(final ComplexResponse model,
                                   final JsonObjectBuilder builder) {
        builder.put("integer_parameter", model.integerParameter);
        builder.put("enum_parameter", model.enumParameter);
        builder.put("enums_parameter", model.enumsParameter);
        builder.put("nested_model_parameter", convertFromObjectIfNotNull(nestedModelModelToJsonConverter, model.nestedModelParameter));
        builder.put("nested_models_parameter", convertFromListIfNotNull(nestedModelModelToJsonConverter, model.nestedModelsParameter));
    }
}
