package io.rxmicro.examples.rest.controller.extendable.model.response.body_only.grand_parent_model_without_fields.child;

import io.rxmicro.exchange.json.detail.JsonExchangeDataFormatConverter;
import io.rxmicro.http.HttpStandardHeaderNames;
import io.rxmicro.rest.detail.ExchangeDataFormatConverter;
import io.rxmicro.rest.server.detail.component.ServerModelWriter;
import io.rxmicro.rest.server.detail.model.HttpResponse;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ChildServerModelWriter extends ServerModelWriter<Child> {

    private final $$ChildModelToJsonConverter childModelToJsonConverter;

    private final ExchangeDataFormatConverter<Object> exchangeDataFormatConverter;

    private final String outputMimeType;

    public $$ChildServerModelWriter(final boolean humanReadableOutput) {
        exchangeDataFormatConverter = new JsonExchangeDataFormatConverter(humanReadableOutput);
        childModelToJsonConverter = new $$ChildModelToJsonConverter();
        outputMimeType = exchangeDataFormatConverter.getMimeType();
    }

    @Override
    public void write(final Child model,
                      final HttpResponse response) {
        response.setHeader(HttpStandardHeaderNames.CONTENT_TYPE, outputMimeType);
        final Map<String, Object> json = childModelToJsonConverter.toJsonObject(model);
        response.setContent(exchangeDataFormatConverter.toBytes(json));
    }
}
