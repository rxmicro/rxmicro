package io.rxmicro.examples.rest.controller.params.model;

import io.rxmicro.exchange.json.detail.JsonExchangeDataFormatConverter;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.detail.ExchangeDataFormatConverter;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import org.junit.runner.Request;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$RequestServerModelReader extends ServerModelReader<Request> {

    private final ExchangeDataFormatConverter<Object> exchangeDataFormatConverter =
            new JsonExchangeDataFormatConverter(false);

    private final $$RequestModelFromJsonConverter requestModelFromJsonConverter =
            new $$RequestModelFromJsonConverter();

    @Override
    public Request read(final PathVariableMapping pathVariableMapping,
                        final HttpRequest request,
                        final boolean readParametersFromBody) {
        if (readParametersFromBody) {
            final Object body = exchangeDataFormatConverter.fromBytes(request.getContent());
            final Request model = requestModelFromJsonConverter.fromJsonObject(body);
            return model;
        } else {
            final Request model = new Request();
            final QueryParams params = extractParams(request.getQueryString());
            readPrimitivesToModel(pathVariableMapping, request, params, model, readParametersFromBody);
            return model;
        }
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final Request model,
                                      final boolean readParametersFromBody) {
        if (readParametersFromBody) {
            return;
        }
        model.endpointVersion = toString(params.getValue("endpoint_version"), HttpModelType.PARAMETER, "endpoint_version");
        model.useProxy = toBoolean(params.getValue("use-Proxy"), HttpModelType.PARAMETER, "use-Proxy");
    }
}
