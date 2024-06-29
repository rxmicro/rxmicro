package io.rxmicro.examples.rest.controller.extendable.model.request.all_body.child_model_without_fields.parent;

import io.rxmicro.examples.rest.controller.extendable.model.request.all_body.child_model_without_fields.grand.$$GrandParentModelFromJsonConverter;
import io.rxmicro.exchange.json.detail.ModelFromJsonConverter;
import org.apache.maven.model.Parent;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ParentModelFromJsonConverter extends ModelFromJsonConverter<Parent> {

    private final $$GrandParentModelFromJsonConverter parentConverter =
            new $$GrandParentModelFromJsonConverter();

    public void readParamsToModel(final Map<String, Object> params,
                                  final Parent model) {
        parentConverter.readParamsToModel(params, model);
        model.parentParameter = toString(params.get("parentParameter"), "parentParameter");
    }
}
