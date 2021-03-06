package io.rxmicro.examples.rest.controller.model.types.model.request.http_body;

import io.rxmicro.exchange.json.detail.JsonExchangeDataFormatConverter;
import io.rxmicro.rest.detail.ExchangeDataFormatConverter;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$HttpBodyRequestModelReader extends ModelReader<HttpBodyRequest> {

    private final ExchangeDataFormatConverter<Object> exchangeDataFormatConverter =
            new JsonExchangeDataFormatConverter(false);

    private final $$HttpBodyRequestModelFromJsonConverter httpBodyRequestModelFromJsonConverter =
            new $$HttpBodyRequestModelFromJsonConverter();

    @Override
    public HttpBodyRequest read(final PathVariableMapping pathVariableMapping,
                                final HttpRequest request,
                                final boolean readParametersFromBody) {
        final Object body = exchangeDataFormatConverter.fromBytes(request.getContent());
        final HttpBodyRequest model = httpBodyRequestModelFromJsonConverter.fromJsonObject(body);
        return model;
    }
}
