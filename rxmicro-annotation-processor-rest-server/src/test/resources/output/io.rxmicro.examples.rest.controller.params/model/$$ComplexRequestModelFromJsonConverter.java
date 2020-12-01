package io.rxmicro.examples.rest.controller.params.model;

import io.rxmicro.examples.rest.controller.params.model.nested.$$NestedModelModelFromJsonConverter;
import io.rxmicro.exchange.json.detail.ModelFromJsonConverter;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ComplexRequestModelFromJsonConverter extends ModelFromJsonConverter<ComplexRequest> {

    private final $$NestedModelModelFromJsonConverter nestedModelModelFromJsonConverter =
            new $$NestedModelModelFromJsonConverter();

    @Override
    public ComplexRequest fromJsonObject(final Map<String, Object> params) {
        final ComplexRequest model = new ComplexRequest();
        readParamsToModel(params, model);
        return model;
    }

    public void readParamsToModel(final Map<String, Object> params,
                                  final ComplexRequest model) {
        model.integerParameter = toInteger(params.get("integer_parameter"), "integer_parameter");
        model.enumParameter = toEnum(Status.class, params.get("enum_parameter"), "enum_parameter");
        model.enumsParameter = toEnumList(Status.class, params.get("enums_parameter"), "enums_parameter");
        model.nestedModelParameter = convertToObjectIfNotNull(nestedModelModelFromJsonConverter, params.get("nested_model_parameter"), "nested_model_parameter");
        model.nestedModelsParameter = convertToListIfNotNull(nestedModelModelFromJsonConverter, params.get("nested_models_parameter"), "nested_models_parameter");
    }
}
