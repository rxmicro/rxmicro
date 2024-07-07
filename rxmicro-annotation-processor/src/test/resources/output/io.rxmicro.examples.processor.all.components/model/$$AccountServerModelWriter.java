package io.rxmicro.examples.processor.all.components.model;

import io.rxmicro.exchange.json.detail.JsonExchangeDataFormatConverter;
import io.rxmicro.http.HttpStandardHeaderNames;
import io.rxmicro.rest.detail.ExchangeDataFormatConverter;
import io.rxmicro.rest.server.detail.component.ServerModelWriter;
import io.rxmicro.rest.server.detail.model.HttpResponse;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$AccountServerModelWriter extends ServerModelWriter<Account> {

    private final $$AccountModelToJsonConverter accountModelToJsonConverter;

    private final ExchangeDataFormatConverter<Object> exchangeDataFormatConverter;

    private final String outputMimeType;

    public $$AccountServerModelWriter(final boolean humanReadableOutput) {
        exchangeDataFormatConverter = new JsonExchangeDataFormatConverter(humanReadableOutput);
        accountModelToJsonConverter = new $$AccountModelToJsonConverter();
        outputMimeType = exchangeDataFormatConverter.getMimeType();
    }

    @Override
    public void write(final Account model,
                      final HttpResponse response) {
        response.setHeader(HttpStandardHeaderNames.CONTENT_TYPE, outputMimeType);
        final Map<String, Object> json = accountModelToJsonConverter.toJsonObject(model);
        response.setContent(exchangeDataFormatConverter.toBytes(json));
    }
}