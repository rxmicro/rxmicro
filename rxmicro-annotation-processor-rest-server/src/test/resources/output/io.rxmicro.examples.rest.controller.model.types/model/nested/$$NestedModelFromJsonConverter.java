package io.rxmicro.examples.rest.controller.model.types.model.nested;

import io.rxmicro.exchange.json.detail.ModelFromJsonConverter;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$NestedModelFromJsonConverter extends ModelFromJsonConverter<Nested> {

    @Override
    public Nested fromJsonObject(final Map<String, Object> params) {
        final Nested model = new Nested();
        readParamsToModel(params, model);
        return model;
    }

    public void readParamsToModel(final Map<String, Object> params,
                                  final Nested model) {
        model.value = toString(params.get("value"), "value");
    }
}
