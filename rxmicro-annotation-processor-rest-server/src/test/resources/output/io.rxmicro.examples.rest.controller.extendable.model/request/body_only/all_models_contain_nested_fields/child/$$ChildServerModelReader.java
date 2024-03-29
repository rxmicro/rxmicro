package io.rxmicro.examples.rest.controller.extendable.model.request.body_only.all_models_contain_nested_fields.child;

import io.rxmicro.exchange.json.detail.JsonExchangeDataFormatConverter;
import io.rxmicro.rest.detail.ExchangeDataFormatConverter;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ChildServerModelReader extends ServerModelReader<Child> {

    private final ExchangeDataFormatConverter<Object> exchangeDataFormatConverter =
            new JsonExchangeDataFormatConverter(false);

    private final $$ChildModelFromJsonConverter childModelFromJsonConverter =
            new $$ChildModelFromJsonConverter();

    @Override
    public Child read(final PathVariableMapping pathVariableMapping,
                      final HttpRequest request,
                      final boolean readParametersFromBody) {
        final Object body = exchangeDataFormatConverter.fromBytes(request.getContent());
        final Child model = childModelFromJsonConverter.fromJsonObject(body);
        return model;
    }
}
