package io.rxmicro.examples.rest.controller.extendable.model.request.any_query_or_body.header_path_param.grand;

import io.rxmicro.exchange.json.detail.ModelFromJsonConverter;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$GrandParentModelFromJsonConverter extends ModelFromJsonConverter<GrandParent> {

    public void readParamsToModel(final Map<String, Object> params,
                                  final GrandParent model) {
        model.grandParameter = toString(params.get("grandParameter"), "grandParameter");
    }
}
