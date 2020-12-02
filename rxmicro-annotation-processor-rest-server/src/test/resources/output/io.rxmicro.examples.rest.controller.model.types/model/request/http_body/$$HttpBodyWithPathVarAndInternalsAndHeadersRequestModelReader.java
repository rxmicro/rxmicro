package io.rxmicro.examples.rest.controller.model.types.model.request.http_body;

import io.rxmicro.examples.rest.controller.model.types.model.Status;
import io.rxmicro.exchange.json.detail.JsonExchangeDataFormatConverter;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.detail.ExchangeDataFormatConverter;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$HttpBodyWithPathVarAndInternalsAndHeadersRequestModelReader extends ModelReader<HttpBodyWithPathVarAndInternalsAndHeadersRequest> {

    private final ExchangeDataFormatConverter<Object> exchangeDataFormatConverter =
            new JsonExchangeDataFormatConverter(false);

    private final $$HttpBodyWithPathVarAndInternalsAndHeadersRequestModelFromJsonConverter httpBodyWithPathVarAndInternalsAndHeadersRequestModelFromJsonConverter =
            new $$HttpBodyWithPathVarAndInternalsAndHeadersRequestModelFromJsonConverter();

    @Override
    public HttpBodyWithPathVarAndInternalsAndHeadersRequest read(final PathVariableMapping pathVariableMapping,
                                                                 final HttpRequest request,
                                                                 final boolean readParametersFromBody) {
        final Object body = exchangeDataFormatConverter.fromBytes(request.getContent());
        final HttpBodyWithPathVarAndInternalsAndHeadersRequest model = httpBodyWithPathVarAndInternalsAndHeadersRequestModelFromJsonConverter.fromJsonObject(body);
        readPrimitivesToModel(pathVariableMapping, request, QueryParams.of(), model, readParametersFromBody);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final HttpBodyWithPathVarAndInternalsAndHeadersRequest model,
                                      final boolean readParametersFromBody) {
        model.internalRemoteAddress1 = String.valueOf(request.getRemoteAddress());
        model.internalRemoteAddress2 = request.getRemoteAddress();
        model.internalUrlPath = request.getUri();
        model.internalRequestMethod = request.getMethod();
        model.internalHttpVersion = request.getVersion();
        model.internalRequestHeaders = request.getHeaders();
        model.internalRequestBody = request.getContent();
        model.internalRequest = request;
        model.booleanPathVariable = toBoolean(pathVariableMapping.getValue("a"), HttpModelType.PATH, "a");
        model.bytePathVariable = toByte(pathVariableMapping.getValue("b"), HttpModelType.PATH, "b");
        model.shortPathVariable = toShort(pathVariableMapping.getValue("c"), HttpModelType.PATH, "c");
        model.intPathVariable = toInteger(pathVariableMapping.getValue("d"), HttpModelType.PATH, "d");
        model.longPathVariable = toLong(pathVariableMapping.getValue("e"), HttpModelType.PATH, "e");
        model.bigIntegerPathVariable = toBigInteger(pathVariableMapping.getValue("f"), HttpModelType.PATH, "f");
        model.floatPathVariable = toFloat(pathVariableMapping.getValue("g"), HttpModelType.PATH, "g");
        model.doublePathVariable = toDouble(pathVariableMapping.getValue("h"), HttpModelType.PATH, "h");
        model.decimalPathVariable = toBigDecimal(pathVariableMapping.getValue("i"), HttpModelType.PATH, "i");
        model.charPathVariable = toCharacter(pathVariableMapping.getValue("j"), HttpModelType.PATH, "j");
        model.stringPathVariable = toString(pathVariableMapping.getValue("k"), HttpModelType.PATH, "k");
        model.instantPathVariable = toInstant(pathVariableMapping.getValue("l"), HttpModelType.PATH, "l");
        model.enumPathVariable = toEnum(Status.class, pathVariableMapping.getValue("m"), HttpModelType.PATH, "m");
        final HttpHeaders httpHeaders = request.getHeaders();
        model.booleanHeader = toBoolean(httpHeaders.getValue("booleanHeader"), HttpModelType.HEADER, "booleanHeader");
        model.byteHeader = toByte(httpHeaders.getValue("byteHeader"), HttpModelType.HEADER, "byteHeader");
        model.shortHeader = toShort(httpHeaders.getValue("shortHeader"), HttpModelType.HEADER, "shortHeader");
        model.intHeader = toInteger(httpHeaders.getValue("intHeader"), HttpModelType.HEADER, "intHeader");
        model.longHeader = toLong(httpHeaders.getValue("longHeader"), HttpModelType.HEADER, "longHeader");
        model.bigIntHeader = toBigInteger(httpHeaders.getValue("bigIntHeader"), HttpModelType.HEADER, "bigIntHeader");
        model.floatHeader = toFloat(httpHeaders.getValue("floatHeader"), HttpModelType.HEADER, "floatHeader");
        model.doubleHeader = toDouble(httpHeaders.getValue("doubleHeader"), HttpModelType.HEADER, "doubleHeader");
        model.decimalHeader = toBigDecimal(httpHeaders.getValue("decimalHeader"), HttpModelType.HEADER, "decimalHeader");
        model.charHeader = toCharacter(httpHeaders.getValue("charHeader"), HttpModelType.HEADER, "charHeader");
        model.stringHeader = toString(httpHeaders.getValue("stringHeader"), HttpModelType.HEADER, "stringHeader");
        model.instantHeader = toInstant(httpHeaders.getValue("instantHeader"), HttpModelType.HEADER, "instantHeader");
        model.enumHeader = toEnum(Status.class, httpHeaders.getValue("enumHeader"), HttpModelType.HEADER, "enumHeader");
        model.booleanHeaderList = toBooleanList(httpHeaders.getValues("booleanHeaderList"), HttpModelType.HEADER, "booleanHeaderList");
        model.byteHeaderList = toByteList(httpHeaders.getValues("byteHeaderList"), HttpModelType.HEADER, "byteHeaderList");
        model.shortHeaderList = toShortList(httpHeaders.getValues("shortHeaderList"), HttpModelType.HEADER, "shortHeaderList");
        model.intHeaderList = toIntegerList(httpHeaders.getValues("intHeaderList"), HttpModelType.HEADER, "intHeaderList");
        model.longHeaderList = toLongList(httpHeaders.getValues("longHeaderList"), HttpModelType.HEADER, "longHeaderList");
        model.bigIntHeaderList = toBigIntegerList(httpHeaders.getValues("bigIntHeaderList"), HttpModelType.HEADER, "bigIntHeaderList");
        model.floatHeaderList = toFloatList(httpHeaders.getValues("floatHeaderList"), HttpModelType.HEADER, "floatHeaderList");
        model.doubleHeaderList = toDoubleList(httpHeaders.getValues("doubleHeaderList"), HttpModelType.HEADER, "doubleHeaderList");
        model.decimalHeaderList = toBigDecimalList(httpHeaders.getValues("decimalHeaderList"), HttpModelType.HEADER, "decimalHeaderList");
        model.charHeaderList = toCharacterList(httpHeaders.getValues("charHeaderList"), HttpModelType.HEADER, "charHeaderList");
        model.stringHeaderList = toStringList(httpHeaders.getValues("stringHeaderList"), HttpModelType.HEADER, "stringHeaderList");
        model.instantHeaderList = toInstantList(httpHeaders.getValues("instantHeaderList"), HttpModelType.HEADER, "instantHeaderList");
        model.enumHeaderList = toEnumList(Status.class, httpHeaders.getValues("enumHeaderList"), HttpModelType.HEADER, "enumHeaderList");
        model.booleanHeaderSet = toBooleanSet(httpHeaders.getValues("booleanHeaderSet"), HttpModelType.HEADER, "booleanHeaderSet");
        model.byteHeaderSet = toByteSet(httpHeaders.getValues("byteHeaderSet"), HttpModelType.HEADER, "byteHeaderSet");
        model.shortHeaderSet = toShortSet(httpHeaders.getValues("shortHeaderSet"), HttpModelType.HEADER, "shortHeaderSet");
        model.intHeaderSet = toIntegerSet(httpHeaders.getValues("intHeaderSet"), HttpModelType.HEADER, "intHeaderSet");
        model.longHeaderSet = toLongSet(httpHeaders.getValues("longHeaderSet"), HttpModelType.HEADER, "longHeaderSet");
        model.bigIntHeaderSet = toBigIntegerSet(httpHeaders.getValues("bigIntHeaderSet"), HttpModelType.HEADER, "bigIntHeaderSet");
        model.floatHeaderSet = toFloatSet(httpHeaders.getValues("floatHeaderSet"), HttpModelType.HEADER, "floatHeaderSet");
        model.doubleHeaderSet = toDoubleSet(httpHeaders.getValues("doubleHeaderSet"), HttpModelType.HEADER, "doubleHeaderSet");
        model.decimalHeaderSet = toBigDecimalSet(httpHeaders.getValues("decimalHeaderSet"), HttpModelType.HEADER, "decimalHeaderSet");
        model.charHeaderSet = toCharacterSet(httpHeaders.getValues("charHeaderSet"), HttpModelType.HEADER, "charHeaderSet");
        model.stringHeaderSet = toStringSet(httpHeaders.getValues("stringHeaderSet"), HttpModelType.HEADER, "stringHeaderSet");
        model.instantHeaderSet = toInstantSet(httpHeaders.getValues("instantHeaderSet"), HttpModelType.HEADER, "instantHeaderSet");
        model.enumHeaderSet = toEnumSet(Status.class, httpHeaders.getValues("enumHeaderSet"), HttpModelType.HEADER, "enumHeaderSet");
        model.requestIdHeader = toString(httpHeaders.getValue("Request-Id"), HttpModelType.HEADER, "Request-Id");
    }
}
