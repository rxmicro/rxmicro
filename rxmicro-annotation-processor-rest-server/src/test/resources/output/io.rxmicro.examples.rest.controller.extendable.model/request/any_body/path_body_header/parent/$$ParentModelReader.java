package io.rxmicro.examples.rest.controller.extendable.model.request.any_body.path_body_header.parent;

import io.rxmicro.examples.rest.controller.extendable.model.request.any_body.path_body_header.grand.$$GrandParentModelReader;
import io.rxmicro.exchange.json.detail.JsonExchangeDataFormatConverter;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.detail.ExchangeDataFormatConverter;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ParentModelReader extends ModelReader<Parent> {

    private final $$GrandParentModelReader parentReader =
            new $$GrandParentModelReader();

    private final ExchangeDataFormatConverter<Object> exchangeDataFormatConverter =
            new JsonExchangeDataFormatConverter(false);

    private final $$ParentModelFromJsonConverter parentModelFromJsonConverter =
            new $$ParentModelFromJsonConverter();

    @Override
    public Parent read(final PathVariableMapping pathVariableMapping,
                       final HttpRequest request,
                       final boolean readParametersFromBody) {
        final Object body = exchangeDataFormatConverter.fromBytes(request.getContent());
        final Parent model = parentModelFromJsonConverter.fromJsonObject(body);
        readPrimitivesToModel(pathVariableMapping, request, QueryParams.of(), model, readParametersFromBody);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final Parent model,
                                      final boolean readParametersFromBody) {
        parentReader.readPrimitivesToModel(pathVariableMapping, request, params, model, readParametersFromBody);
    }
}
