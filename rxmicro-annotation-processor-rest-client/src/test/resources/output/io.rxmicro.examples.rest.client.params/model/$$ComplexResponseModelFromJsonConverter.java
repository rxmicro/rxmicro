package io.rxmicro.examples.rest.client.params.model;

import io.rxmicro.examples.rest.client.params.model.nested.$$NestedModelModelFromJsonConverter;
import io.rxmicro.exchange.json.detail.ModelFromJsonConverter;

import java.util.List;
import java.util.Map;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$ComplexResponseModelFromJsonConverter extends ModelFromJsonConverter<ComplexResponse> {

    private final $$NestedModelModelFromJsonConverter nestedModelModelFromJsonConverter =
            new $$NestedModelModelFromJsonConverter();

    @Override
    public ComplexResponse fromJsonObject(final Map<String, Object> params) {
        final ComplexResponse model = new ComplexResponse();
        model.integerParameter = toInteger(params.get("integer_parameter"), "integer_parameter");
        model.enumParameter = toEnum(Status.class, params.get("enum_parameter"), "enum_parameter");
        model.enumsParameter = toEnumArray(Status.class, params.get("enums_parameter"), "enums_parameter");
        model.nestedModelParameter = convertIfNotNull(nestedModelModelFromJsonConverter, (Map<String, Object>) params.get("nested_model_parameter"));
        model.nestedModelsParameter = convertIfNotNull(nestedModelModelFromJsonConverter, (List<Object>) params.get("nested_models_parameter"), "nested_models_parameter");
        return model;
    }
}
