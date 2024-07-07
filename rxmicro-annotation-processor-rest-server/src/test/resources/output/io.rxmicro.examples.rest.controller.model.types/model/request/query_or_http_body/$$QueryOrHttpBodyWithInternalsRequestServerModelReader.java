package io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body;

import io.rxmicro.examples.rest.controller.model.types.model.Status;
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
public final class $$QueryOrHttpBodyWithInternalsRequestServerModelReader extends ServerModelReader<QueryOrHttpBodyWithInternalsRequest> {

    private final ExchangeDataFormatConverter<Object> exchangeDataFormatConverter =
            new JsonExchangeDataFormatConverter(false);

    private final $$QueryOrHttpBodyWithInternalsRequestModelFromJsonConverter queryOrHttpBodyWithInternalsRequestModelFromJsonConverter =
            new $$QueryOrHttpBodyWithInternalsRequestModelFromJsonConverter();

    @Override
    public QueryOrHttpBodyWithInternalsRequest read(final PathVariableMapping pathVariableMapping,
                                                    final HttpRequest request,
                                                    final boolean readParametersFromBody) {
        if (readParametersFromBody) {
            final Object body = exchangeDataFormatConverter.fromBytes(request.getContent());
            final QueryOrHttpBodyWithInternalsRequest model = queryOrHttpBodyWithInternalsRequestModelFromJsonConverter.fromJsonObject(body);
            readPrimitivesToModel(pathVariableMapping, request, QueryParams.of(), model, readParametersFromBody);
            return model;
        } else {
            final QueryOrHttpBodyWithInternalsRequest model = new QueryOrHttpBodyWithInternalsRequest();
            final QueryParams params = extractParams(request.getQueryString());
            readPrimitivesToModel(pathVariableMapping, request, params, model, readParametersFromBody);
            return model;
        }
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final QueryOrHttpBodyWithInternalsRequest model,
                                      final boolean readParametersFromBody) {
        model.internalRemoteAddress1 = String.valueOf(request.getRemoteAddress());
        model.internalRemoteAddress2 = request.getRemoteAddress();
        model.internalUrlPath = request.getUri();
        model.internalRequestMethod = request.getMethod();
        model.internalHttpVersion = request.getVersion();
        model.internalRequestHeaders = request.getHeaders();
        model.internalRequestBody = request.getContent();
        model.internalRequest = request;
        if (readParametersFromBody) {
            return;
        }
        model.booleanParameter = toBoolean(params.getValue("booleanParameter"), HttpModelType.PARAMETER, "booleanParameter");
        model.byteParameter = toByte(params.getValue("byteParameter"), HttpModelType.PARAMETER, "byteParameter");
        model.shortParameter = toShort(params.getValue("shortParameter"), HttpModelType.PARAMETER, "shortParameter");
        model.intParameter = toInteger(params.getValue("intParameter"), HttpModelType.PARAMETER, "intParameter");
        model.longParameter = toLong(params.getValue("longParameter"), HttpModelType.PARAMETER, "longParameter");
        model.bigIntParameter = toBigInteger(params.getValue("bigIntParameter"), HttpModelType.PARAMETER, "bigIntParameter");
        model.floatParameter = toFloat(params.getValue("floatParameter"), HttpModelType.PARAMETER, "floatParameter");
        model.doubleParameter = toDouble(params.getValue("doubleParameter"), HttpModelType.PARAMETER, "doubleParameter");
        model.decimalParameter = toBigDecimal(params.getValue("decimalParameter"), HttpModelType.PARAMETER, "decimalParameter");
        model.charParameter = toCharacter(params.getValue("charParameter"), HttpModelType.PARAMETER, "charParameter");
        model.stringParameter = toString(params.getValue("stringParameter"), HttpModelType.PARAMETER, "stringParameter");
        model.instantParameter = toInstant(params.getValue("instantParameter"), HttpModelType.PARAMETER, "instantParameter");
        model.enumParameter = toEnum(Status.class, params.getValue("enumParameter"), HttpModelType.PARAMETER, "enumParameter");
        model.booleanParameterList = toBooleanList(params.getValues("booleanParameterList"), HttpModelType.PARAMETER, "booleanParameterList");
        model.byteParameterList = toByteList(params.getValues("byteParameterList"), HttpModelType.PARAMETER, "byteParameterList");
        model.shortParameterList = toShortList(params.getValues("shortParameterList"), HttpModelType.PARAMETER, "shortParameterList");
        model.intParameterList = toIntegerList(params.getValues("intParameterList"), HttpModelType.PARAMETER, "intParameterList");
        model.longParameterList = toLongList(params.getValues("longParameterList"), HttpModelType.PARAMETER, "longParameterList");
        model.bigIntParameterList = toBigIntegerList(params.getValues("bigIntParameterList"), HttpModelType.PARAMETER, "bigIntParameterList");
        model.floatParameterList = toFloatList(params.getValues("floatParameterList"), HttpModelType.PARAMETER, "floatParameterList");
        model.doubleParameterList = toDoubleList(params.getValues("doubleParameterList"), HttpModelType.PARAMETER, "doubleParameterList");
        model.decimalParameterList = toBigDecimalList(params.getValues("decimalParameterList"), HttpModelType.PARAMETER, "decimalParameterList");
        model.charParameterList = toCharacterList(params.getValues("charParameterList"), HttpModelType.PARAMETER, "charParameterList");
        model.stringParameterList = toStringList(params.getValues("stringParameterList"), HttpModelType.PARAMETER, "stringParameterList");
        model.instantParameterList = toInstantList(params.getValues("instantParameterList"), HttpModelType.PARAMETER, "instantParameterList");
        model.enumParameterList = toEnumList(Status.class, params.getValues("enumParameterList"), HttpModelType.PARAMETER, "enumParameterList");
        model.booleanParameterSet = toBooleanSet(params.getValues("booleanParameterSet"), HttpModelType.PARAMETER, "booleanParameterSet");
        model.byteParameterSet = toByteSet(params.getValues("byteParameterSet"), HttpModelType.PARAMETER, "byteParameterSet");
        model.shortParameterSet = toShortSet(params.getValues("shortParameterSet"), HttpModelType.PARAMETER, "shortParameterSet");
        model.intParameterSet = toIntegerSet(params.getValues("intParameterSet"), HttpModelType.PARAMETER, "intParameterSet");
        model.longParameterSet = toLongSet(params.getValues("longParameterSet"), HttpModelType.PARAMETER, "longParameterSet");
        model.bigIntParameterSet = toBigIntegerSet(params.getValues("bigIntParameterSet"), HttpModelType.PARAMETER, "bigIntParameterSet");
        model.floatParameterSet = toFloatSet(params.getValues("floatParameterSet"), HttpModelType.PARAMETER, "floatParameterSet");
        model.doubleParameterSet = toDoubleSet(params.getValues("doubleParameterSet"), HttpModelType.PARAMETER, "doubleParameterSet");
        model.decimalParameterSet = toBigDecimalSet(params.getValues("decimalParameterSet"), HttpModelType.PARAMETER, "decimalParameterSet");
        model.charParameterSet = toCharacterSet(params.getValues("charParameterSet"), HttpModelType.PARAMETER, "charParameterSet");
        model.stringParameterSet = toStringSet(params.getValues("stringParameterSet"), HttpModelType.PARAMETER, "stringParameterSet");
        model.instantParameterSet = toInstantSet(params.getValues("instantParameterSet"), HttpModelType.PARAMETER, "instantParameterSet");
        model.enumParameterSet = toEnumSet(Status.class, params.getValues("enumParameterSet"), HttpModelType.PARAMETER, "enumParameterSet");
    }
}
