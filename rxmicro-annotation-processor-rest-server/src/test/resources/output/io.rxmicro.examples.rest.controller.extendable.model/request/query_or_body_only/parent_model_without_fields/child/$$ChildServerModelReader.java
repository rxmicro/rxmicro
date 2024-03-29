package io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.parent_model_without_fields.child;

import io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.parent_model_without_fields.grand.$$GrandParentServerModelReader;
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

    private final $$GrandParentServerModelReader parentReader =
            new $$GrandParentServerModelReader();

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
        if (readParametersFromBody) {
            return;
        }
        model.childParameter = toString(params.getValue("childParameter"), HttpModelType.PARAMETER, "childParameter");
    }
}
