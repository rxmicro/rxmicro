package io.rxmicro.examples.validation.server.nested.model.model;

import io.rxmicro.exchange.json.detail.JsonExchangeDataFormatConverter;
import io.rxmicro.rest.detail.ExchangeDataFormatConverter;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ArrayRequestServerModelReader extends ServerModelReader<ArrayRequest> {

    private final ExchangeDataFormatConverter<Object> exchangeDataFormatConverter =
            new JsonExchangeDataFormatConverter(false);

    private final $$ArrayRequestModelFromJsonConverter arrayRequestModelFromJsonConverter =
            new $$ArrayRequestModelFromJsonConverter();

    @Override
    public ArrayRequest read(final PathVariableMapping pathVariableMapping,
                             final HttpRequest request,
                             final boolean readParametersFromBody) {
        final Object body = exchangeDataFormatConverter.fromBytes(request.getContent());
        final ArrayRequest model = arrayRequestModelFromJsonConverter.fromJsonObject(body);
        return model;
    }
}
