package io.rxmicro.examples.validation.server.extendable.model.all_models_contain_fields.child;

import io.rxmicro.examples.validation.server.extendable.model.all_models_contain_fields.parent.$$ParentModelFromJsonConverter;
import io.rxmicro.exchange.json.detail.ModelFromJsonConverter;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ChildModelFromJsonConverter extends ModelFromJsonConverter<Child> {

    private final $$ParentModelFromJsonConverter parentConverter =
            new $$ParentModelFromJsonConverter();

    @Override
    public Child fromJsonObject(final Map<String, Object> params) {
        final Child model = new Child();
        readParamsToModel(params, model);
        return model;
    }

    public void readParamsToModel(final Map<String, Object> params,
                                  final Child model) {
        parentConverter.readParamsToModel(params, model);
        model.childParameter = toString(params.get("childParameter"), "childParameter");
    }
}
