package io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_simple_fields.grand;

import io.rxmicro.exchange.json.detail.JsonExchangeDataFormatConverter;
import io.rxmicro.rest.detail.ExchangeDataFormatConverter;
import io.rxmicro.rest.server.detail.component.ServerModelWriter;
import io.rxmicro.rest.server.detail.model.HttpResponse;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$GrandParentServerModelWriter extends ServerModelWriter<GrandParent> {

    private final $$GrandParentModelToJsonConverter grandParentModelToJsonConverter;

    private final ExchangeDataFormatConverter<Object> exchangeDataFormatConverter;

    private final String outputMimeType;

    public $$GrandParentServerModelWriter(final boolean humanReadableOutput) {
        exchangeDataFormatConverter = new JsonExchangeDataFormatConverter(humanReadableOutput);
        grandParentModelToJsonConverter = new $$GrandParentModelToJsonConverter();
        outputMimeType = exchangeDataFormatConverter.getMimeType();
    }

    public void writePrimitivesToResponse(final GrandParent model,
                                          final HttpResponse response) {
        response.setHeader("grandHeader", model.grandHeader);
    }
}
