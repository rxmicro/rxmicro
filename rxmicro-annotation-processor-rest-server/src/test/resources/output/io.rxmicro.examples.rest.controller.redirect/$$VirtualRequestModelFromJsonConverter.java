package io.rxmicro.examples.rest.controller.redirect;

import io.rxmicro.exchange.json.detail.ModelFromJsonConverter;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualRequestModelFromJsonConverter extends ModelFromJsonConverter<$$VirtualRequest> {

    @Override
    public $$VirtualRequest fromJsonObject(final Map<String, Object> params) {
        final $$VirtualRequest model = new $$VirtualRequest();
        readParamsToModel(params, model);
        return model;
    }

    public void readParamsToModel(final Map<String, Object> params,
                                  final $$VirtualRequest model) {
        model.parameter = toString(params.get("parameter"), "parameter");
    }
}
