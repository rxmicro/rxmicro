package io.rxmicro.examples.rest.controller.extendable.model.response.body_only.grand_parent_model_without_fields.parent;

import io.rxmicro.exchange.json.detail.JsonExchangeDataFormatConverter;
import io.rxmicro.http.HttpStandardHeaderNames;
import io.rxmicro.rest.detail.ExchangeDataFormatConverter;
import io.rxmicro.rest.server.detail.component.ServerModelWriter;
import io.rxmicro.rest.server.detail.model.HttpResponse;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ParentServerModelWriter extends ServerModelWriter<Parent> {

    private final $$ParentModelToJsonConverter parentModelToJsonConverter;

    private final ExchangeDataFormatConverter<Object> exchangeDataFormatConverter;

    private final String outputMimeType;

    public $$ParentServerModelWriter(final boolean humanReadableOutput) {
        exchangeDataFormatConverter = new JsonExchangeDataFormatConverter(humanReadableOutput);
        parentModelToJsonConverter = new $$ParentModelToJsonConverter();
        outputMimeType = exchangeDataFormatConverter.getMimeType();
    }

    @Override
    public void write(final Parent model,
                      final HttpResponse response) {
        response.setHeader(HttpStandardHeaderNames.CONTENT_TYPE, outputMimeType);
        final Map<String, Object> json = parentModelToJsonConverter.toJsonObject(model);
        response.setContent(exchangeDataFormatConverter.toBytes(json));
    }
}
