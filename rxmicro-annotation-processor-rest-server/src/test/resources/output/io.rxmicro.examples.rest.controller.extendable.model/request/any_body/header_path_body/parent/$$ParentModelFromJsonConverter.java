package io.rxmicro.examples.rest.controller.extendable.model.request.any_body.header_path_body.parent;

import io.rxmicro.examples.rest.controller.extendable.model.request.any_body.header_path_body.grand.$$GrandParentModelFromJsonConverter;
import io.rxmicro.exchange.json.detail.ModelFromJsonConverter;
import org.apache.maven.model.Parent;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ParentModelFromJsonConverter extends ModelFromJsonConverter<Parent> {

    private final $$GrandParentModelFromJsonConverter parentConverter =
            new $$GrandParentModelFromJsonConverter();

    @Override
    public Parent fromJsonObject(final Map<String, Object> params) {
        final Parent model = new Parent();
        readParamsToModel(params, model);
        return model;
    }

    public void readParamsToModel(final Map<String, Object> params,
                                  final Parent model) {
        parentConverter.readParamsToModel(params, model);
    }
}
