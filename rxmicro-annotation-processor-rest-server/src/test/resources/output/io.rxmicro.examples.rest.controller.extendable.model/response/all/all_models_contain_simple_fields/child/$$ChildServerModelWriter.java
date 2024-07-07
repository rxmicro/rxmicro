package io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_simple_fields.child;

import io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_simple_fields.parent.$$ParentServerModelWriter;
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

    private final $$ParentServerModelWriter parentWriter;

    private final $$ChildModelToJsonConverter childModelToJsonConverter;

    private final ExchangeDataFormatConverter<Object> exchangeDataFormatConverter;

    private final String outputMimeType;

    public $$ChildServerModelWriter(final boolean humanReadableOutput) {
        exchangeDataFormatConverter = new JsonExchangeDataFormatConverter(humanReadableOutput);
        parentWriter = new $$ParentServerModelWriter(humanReadableOutput);
        childModelToJsonConverter = new $$ChildModelToJsonConverter();
        outputMimeType = exchangeDataFormatConverter.getMimeType();
    }

    @Override
    public void write(final Child model,
                      final HttpResponse response) {
        writePrimitivesToResponse(model, response);
        response.setHeader(HttpStandardHeaderNames.CONTENT_TYPE, outputMimeType);
        final Map<String, Object> json = childModelToJsonConverter.toJsonObject(model);
        response.setContent(exchangeDataFormatConverter.toBytes(json));
    }

    public void writePrimitivesToResponse(final Child model,
                                          final HttpResponse response) {
        parentWriter.writePrimitivesToResponse(model, response);
        response.setOrAddHeaders(model.headers);
        response.setHeader("childHeader", model.childHeader);
    }
}
