package io.rxmicro.examples.validation.server.all.standard;

import io.rxmicro.exchange.json.detail.JsonExchangeDataFormatConverter;
import io.rxmicro.rest.component.ExchangeDataFormatConverter;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$VirtualRequestModelReader extends ModelReader<$$VirtualRequest> {

    private final ExchangeDataFormatConverter<Object> exchangeDataFormatConverter =
            new JsonExchangeDataFormatConverter(false);

    private final $$VirtualRequestModelFromJsonConverter virtualRequestModelFromJsonConverter =
            new $$VirtualRequestModelFromJsonConverter();

    @Override
    public $$VirtualRequest read(final PathVariableMapping pathVariableMapping,
                                 final HttpRequest request,
                                 final boolean readParametersFromBody) {
        final Object body = exchangeDataFormatConverter.fromBytes(request.getContent());
        final $$VirtualRequest model = virtualRequestModelFromJsonConverter.fromJsonObject(body);
        return model;
    }
}
