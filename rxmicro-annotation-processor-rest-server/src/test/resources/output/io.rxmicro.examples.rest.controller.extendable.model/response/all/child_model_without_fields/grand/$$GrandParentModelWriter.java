package io.rxmicro.examples.rest.controller.extendable.model.response.all.child_model_without_fields.grand;

import io.rxmicro.exchange.json.detail.JsonExchangeDataFormatConverter;
import io.rxmicro.rest.detail.ExchangeDataFormatConverter;
import io.rxmicro.rest.server.detail.component.ModelWriter;
import io.rxmicro.rest.server.detail.model.HttpResponse;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$GrandParentModelWriter extends ModelWriter<GrandParent> {

    private final $$GrandParentModelToJsonConverter grandParentModelToJsonConverter;

    private final ExchangeDataFormatConverter<Object> exchangeDataFormatConverter;

    private final String outputMimeType;

    public $$GrandParentModelWriter(final boolean humanReadableOutput) {
        exchangeDataFormatConverter = new JsonExchangeDataFormatConverter(humanReadableOutput);
        grandParentModelToJsonConverter = new $$GrandParentModelToJsonConverter();
        outputMimeType = exchangeDataFormatConverter.getMimeType();
    }

    public void writePrimitivesToResponse(final GrandParent model,
                                          final HttpResponse response) {
        response.setVersion(model.httpVersion);
        response.setHeader("grandHeader", model.grandHeader);
    }
}
