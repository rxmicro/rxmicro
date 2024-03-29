package io.rxmicro.examples.rest.controller.model.field.access.params.direct;

import io.rxmicro.exchange.json.detail.JsonExchangeDataFormatConverter;
import io.rxmicro.rest.detail.ExchangeDataFormatConverter;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$BodyRequestServerModelReader extends ServerModelReader<BodyRequest> {

    private final ExchangeDataFormatConverter<Object> exchangeDataFormatConverter =
            new JsonExchangeDataFormatConverter(false);

    private final $$BodyRequestModelFromJsonConverter bodyRequestModelFromJsonConverter =
            new $$BodyRequestModelFromJsonConverter();

    @Override
    public BodyRequest read(final PathVariableMapping pathVariableMapping,
                            final HttpRequest request,
                            final boolean readParametersFromBody) {
        final Object body = exchangeDataFormatConverter.fromBytes(request.getContent());
        final BodyRequest model = bodyRequestModelFromJsonConverter.fromJsonObject(body);
        return model;
    }
}
