package io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.grand_parent_model_without_fields.parent;

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
public final class $$ParentServerModelReader extends ServerModelReader<Parent> {

    private final ExchangeDataFormatConverter<Object> exchangeDataFormatConverter =
            new JsonExchangeDataFormatConverter(false);

    private final $$ParentModelFromJsonConverter parentModelFromJsonConverter =
            new $$ParentModelFromJsonConverter();

    @Override
    public Parent read(final PathVariableMapping pathVariableMapping,
                       final HttpRequest request,
                       final boolean readParametersFromBody) {
        if (readParametersFromBody) {
            final Object body = exchangeDataFormatConverter.fromBytes(request.getContent());
            final Parent model = parentModelFromJsonConverter.fromJsonObject(body);
            return model;
        } else {
            final Parent model = new Parent();
            final QueryParams params = extractParams(request.getQueryString());
            readPrimitivesToModel(pathVariableMapping, request, params, model, readParametersFromBody);
            return model;
        }
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final Parent model,
                                      final boolean readParametersFromBody) {
        if (readParametersFromBody) {
            return;
        }
        model.parentParameter = toString(params.getValue("parentParameter"), HttpModelType.PARAMETER, "parentParameter");
    }
}
