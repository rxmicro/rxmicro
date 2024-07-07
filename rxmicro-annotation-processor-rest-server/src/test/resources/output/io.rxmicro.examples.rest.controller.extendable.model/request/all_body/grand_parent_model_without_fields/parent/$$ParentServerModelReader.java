package io.rxmicro.examples.rest.controller.extendable.model.request.all_body.grand_parent_model_without_fields.parent;

import io.rxmicro.exchange.json.detail.JsonExchangeDataFormatConverter;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.detail.ExchangeDataFormatConverter;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ParentServerModelReader extends ServerModelReader<Parent> {

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
        model.url = request.getUri();
        model.parentVar = toString(pathVariableMapping.getValue("parentVar"), HttpModelType.PATH, "parentVar");
        final HttpHeaders httpHeaders = request.getHeaders();
        model.parentHeader = toString(httpHeaders.getValue("parentHeader"), HttpModelType.HEADER, "parentHeader");
    }
}
