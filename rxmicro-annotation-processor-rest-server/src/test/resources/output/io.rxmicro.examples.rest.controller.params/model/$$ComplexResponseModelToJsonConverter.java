package io.rxmicro.examples.rest.controller.params.model;

import io.rxmicro.examples.rest.controller.params.model.nested.$$NestedModelModelToJsonConverter;
import io.rxmicro.exchange.json.detail.ModelToJsonConverter;
import io.rxmicro.json.JsonObjectBuilder;

import java.util.Map;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$ComplexResponseModelToJsonConverter extends ModelToJsonConverter<ComplexResponse> {

    private final $$NestedModelModelToJsonConverter nestedModelModelToJsonConverter =
            new $$NestedModelModelToJsonConverter();

    @Override
    public Map<String, Object> toJsonObject(final ComplexResponse model) {
        return new JsonObjectBuilder()
                .put("integer_parameter", model.integerParameter)
                .put("enum_parameter", model.enumParameter)
                .put("enums_parameter", model.enumsParameter)
                .put("nested_model_parameter", convertIfNotNull(nestedModelModelToJsonConverter, model.nestedModelParameter))
                .put("nested_models_parameter", convertIfNotNull(nestedModelModelToJsonConverter, model.nestedModelsParameter))
                .build();
    }
}
