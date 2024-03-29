package io.rxmicro.examples.rest.controller.model.field.access.headers;

import io.rxmicro.examples.rest.controller.model.field.access.Status;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualVirtualHeadersRequestServerModelReader extends ServerModelReader<$$VirtualVirtualHeadersRequest> {

    @Override
    public $$VirtualVirtualHeadersRequest read(final PathVariableMapping pathVariableMapping,
                                               final HttpRequest request,
                                               final boolean readParametersFromBody) {
        final $$VirtualVirtualHeadersRequest model = new $$VirtualVirtualHeadersRequest();
        readPrimitivesToModel(pathVariableMapping, request, QueryParams.of(), model, readParametersFromBody);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final $$VirtualVirtualHeadersRequest model,
                                      final boolean readParametersFromBody) {
        final HttpHeaders httpHeaders = request.getHeaders();
        model.booleanHeader = toBoolean(httpHeaders.getValue("Boolean-Header"), HttpModelType.HEADER, "Boolean-Header");
        model.byteHeader = toByte(httpHeaders.getValue("Byte-Header"), HttpModelType.HEADER, "Byte-Header");
        model.shortHeader = toShort(httpHeaders.getValue("Short-Header"), HttpModelType.HEADER, "Short-Header");
        model.intHeader = toInteger(httpHeaders.getValue("Int-Header"), HttpModelType.HEADER, "Int-Header");
        model.longHeader = toLong(httpHeaders.getValue("Long-Header"), HttpModelType.HEADER, "Long-Header");
        model.bigIntHeader = toBigInteger(httpHeaders.getValue("Big-Int-Header"), HttpModelType.HEADER, "Big-Int-Header");
        model.floatHeader = toFloat(httpHeaders.getValue("Float-Header"), HttpModelType.HEADER, "Float-Header");
        model.doubleHeader = toDouble(httpHeaders.getValue("Double-Header"), HttpModelType.HEADER, "Double-Header");
        model.decimalHeader = toBigDecimal(httpHeaders.getValue("Decimal-Header"), HttpModelType.HEADER, "Decimal-Header");
        model.charHeader = toCharacter(httpHeaders.getValue("Char-Header"), HttpModelType.HEADER, "Char-Header");
        model.stringHeader = toString(httpHeaders.getValue("String-Header"), HttpModelType.HEADER, "String-Header");
        model.instantHeader = toInstant(httpHeaders.getValue("Instant-Header"), HttpModelType.HEADER, "Instant-Header");
        model.enumHeader = toEnum(Status.class, httpHeaders.getValue("Enum-Header"), HttpModelType.HEADER, "Enum-Header");
        model.booleanHeaderList = toBooleanList(httpHeaders.getValues("Boolean-Header-List"), HttpModelType.HEADER, "Boolean-Header-List");
        model.byteHeaderList = toByteList(httpHeaders.getValues("Byte-Header-List"), HttpModelType.HEADER, "Byte-Header-List");
        model.shortHeaderList = toShortList(httpHeaders.getValues("Short-Header-List"), HttpModelType.HEADER, "Short-Header-List");
        model.intHeaderList = toIntegerList(httpHeaders.getValues("Int-Header-List"), HttpModelType.HEADER, "Int-Header-List");
        model.longHeaderList = toLongList(httpHeaders.getValues("Long-Header-List"), HttpModelType.HEADER, "Long-Header-List");
        model.bigIntHeaderList = toBigIntegerList(httpHeaders.getValues("Big-Int-Header-List"), HttpModelType.HEADER, "Big-Int-Header-List");
        model.floatHeaderList = toFloatList(httpHeaders.getValues("Float-Header-List"), HttpModelType.HEADER, "Float-Header-List");
        model.doubleHeaderList = toDoubleList(httpHeaders.getValues("Double-Header-List"), HttpModelType.HEADER, "Double-Header-List");
        model.decimalHeaderList = toBigDecimalList(httpHeaders.getValues("Decimal-Header-List"), HttpModelType.HEADER, "Decimal-Header-List");
        model.charHeaderList = toCharacterList(httpHeaders.getValues("Char-Header-List"), HttpModelType.HEADER, "Char-Header-List");
        model.stringHeaderList = toStringList(httpHeaders.getValues("String-Header-List"), HttpModelType.HEADER, "String-Header-List");
        model.instantHeaderList = toInstantList(httpHeaders.getValues("Instant-Header-List"), HttpModelType.HEADER, "Instant-Header-List");
        model.enumHeaderList = toEnumList(Status.class, httpHeaders.getValues("Enum-Header-List"), HttpModelType.HEADER, "Enum-Header-List");
        model.booleanHeaderSet = toBooleanSet(httpHeaders.getValues("Boolean-Header-Set"), HttpModelType.HEADER, "Boolean-Header-Set");
        model.byteHeaderSet = toByteSet(httpHeaders.getValues("Byte-Header-Set"), HttpModelType.HEADER, "Byte-Header-Set");
        model.shortHeaderSet = toShortSet(httpHeaders.getValues("Short-Header-Set"), HttpModelType.HEADER, "Short-Header-Set");
        model.intHeaderSet = toIntegerSet(httpHeaders.getValues("Int-Header-Set"), HttpModelType.HEADER, "Int-Header-Set");
        model.longHeaderSet = toLongSet(httpHeaders.getValues("Long-Header-Set"), HttpModelType.HEADER, "Long-Header-Set");
        model.bigIntHeaderSet = toBigIntegerSet(httpHeaders.getValues("Big-Int-Header-Set"), HttpModelType.HEADER, "Big-Int-Header-Set");
        model.floatHeaderSet = toFloatSet(httpHeaders.getValues("Float-Header-Set"), HttpModelType.HEADER, "Float-Header-Set");
        model.doubleHeaderSet = toDoubleSet(httpHeaders.getValues("Double-Header-Set"), HttpModelType.HEADER, "Double-Header-Set");
        model.decimalHeaderSet = toBigDecimalSet(httpHeaders.getValues("Decimal-Header-Set"), HttpModelType.HEADER, "Decimal-Header-Set");
        model.charHeaderSet = toCharacterSet(httpHeaders.getValues("Char-Header-Set"), HttpModelType.HEADER, "Char-Header-Set");
        model.stringHeaderSet = toStringSet(httpHeaders.getValues("String-Header-Set"), HttpModelType.HEADER, "String-Header-Set");
        model.instantHeaderSet = toInstantSet(httpHeaders.getValues("Instant-Header-Set"), HttpModelType.HEADER, "Instant-Header-Set");
        model.enumHeaderSet = toEnumSet(Status.class, httpHeaders.getValues("Enum-Header-Set"), HttpModelType.HEADER, "Enum-Header-Set");
        model.requestIdHeader = toString(httpHeaders.getValue("Request-Id"), HttpModelType.HEADER, "Request-Id");
    }
}
