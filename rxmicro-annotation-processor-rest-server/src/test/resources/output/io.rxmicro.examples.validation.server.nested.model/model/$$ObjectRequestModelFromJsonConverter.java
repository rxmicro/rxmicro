package io.rxmicro.examples.validation.server.nested.model.model;

import io.rxmicro.examples.validation.server.nested.model.model.nested.$$NestedModelFromJsonConverter;
import io.rxmicro.exchange.json.detail.ModelFromJsonConverter;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ObjectRequestModelFromJsonConverter extends ModelFromJsonConverter<ObjectRequest> {

    private final $$NestedModelFromJsonConverter nestedModelFromJsonConverter =
            new $$NestedModelFromJsonConverter();

    @Override
    public ObjectRequest fromJsonObject(final Map<String, Object> params) {
        final ObjectRequest model = new ObjectRequest();
        readParamsToModel(params, model);
        return model;
    }

    public void readParamsToModel(final Map<String, Object> params,
                                  final ObjectRequest model) {
        model.nested = convertToObjectIfNotNull(nestedModelFromJsonConverter, params.get("nested"), "nested");
    }
}
