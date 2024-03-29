package io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_simple_fields.parent;

import io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_simple_fields.grand.$$GrandParentServerModelWriter;
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

    private final $$GrandParentServerModelWriter parentWriter;

    private final $$ParentModelToJsonConverter parentModelToJsonConverter;

    private final ExchangeDataFormatConverter<Object> exchangeDataFormatConverter;

    private final String outputMimeType;

    public $$ParentServerModelWriter(final boolean humanReadableOutput) {
        exchangeDataFormatConverter = new JsonExchangeDataFormatConverter(humanReadableOutput);
        parentWriter = new $$GrandParentServerModelWriter(humanReadableOutput);
        parentModelToJsonConverter = new $$ParentModelToJsonConverter();
        outputMimeType = exchangeDataFormatConverter.getMimeType();
    }

    @Override
    public void write(final Parent model,
                      final HttpResponse response) {
        writePrimitivesToResponse(model, response);
        response.setHeader(HttpStandardHeaderNames.CONTENT_TYPE, outputMimeType);
        final Map<String, Object> json = parentModelToJsonConverter.toJsonObject(model);
        response.setContent(exchangeDataFormatConverter.toBytes(json));
    }

    public void writePrimitivesToResponse(final Parent model,
                                          final HttpResponse response) {
        parentWriter.writePrimitivesToResponse(model, response);
        response.setStatus(model.internalStatusCode);
        response.setHeader("parentHeader", model.parentHeader);
    }
}
