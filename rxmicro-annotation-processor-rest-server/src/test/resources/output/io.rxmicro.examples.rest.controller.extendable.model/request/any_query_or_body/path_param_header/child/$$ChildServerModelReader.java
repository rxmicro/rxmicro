package io.rxmicro.examples.rest.controller.extendable.model.request.any_query_or_body.path_param_header.child;

import io.rxmicro.examples.rest.controller.extendable.model.request.any_query_or_body.path_param_header.parent.$$ParentServerModelReader;
import io.rxmicro.exchange.json.detail.JsonExchangeDataFormatConverter;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.detail.ExchangeDataFormatConverter;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ChildServerModelReader extends ServerModelReader<Child> {

    private final $$ParentServerModelReader parentReader =
            new $$ParentServerModelReader();

    private final ExchangeDataFormatConverter<Object> exchangeDataFormatConverter =
            new JsonExchangeDataFormatConverter(false);

    private final $$ChildModelFromJsonConverter childModelFromJsonConverter =
            new $$ChildModelFromJsonConverter();

    @Override
    public Child read(final PathVariableMapping pathVariableMapping,
                      final HttpRequest request,
                      final boolean readParametersFromBody) {
        if (readParametersFromBody) {
            final Object body = exchangeDataFormatConverter.fromBytes(request.getContent());
            final Child model = childModelFromJsonConverter.fromJsonObject(body);
            readPrimitivesToModel(pathVariableMapping, request, QueryParams.of(), model, readParametersFromBody);
            return model;
        } else {
            final Child model = new Child();
            final QueryParams params = extractParams(request.getQueryString());
            readPrimitivesToModel(pathVariableMapping, request, params, model, readParametersFromBody);
            return model;
        }
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final Child model,
                                      final boolean readParametersFromBody) {
        parentReader.readPrimitivesToModel(pathVariableMapping, request, params, model, readParametersFromBody);
        model.childVar = toString(pathVariableMapping.getValue("childVar"), HttpModelType.PATH, "childVar");
    }
}
