package io.rxmicro.examples.rest.controller.model.fields.params.direct.nested;

import io.rxmicro.exchange.json.detail.ModelFromJsonConverter;

import java.util.Map;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$NestedModelFromJsonConverter extends ModelFromJsonConverter<Nested> {

    @Override
    public Nested fromJsonObject(final Map<String, Object> params) {
        final Nested model = new Nested();
        model.value = toString(params.get("value"), "value");
        return model;
    }
}
