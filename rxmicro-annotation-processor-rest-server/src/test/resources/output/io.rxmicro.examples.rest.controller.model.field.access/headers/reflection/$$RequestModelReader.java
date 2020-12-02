package io.rxmicro.examples.rest.controller.model.field.access.headers.reflection;

import io.rxmicro.examples.rest.controller.model.field.access.Status;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

import static rxmicro.$$Reflections.setFieldValue;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$RequestModelReader extends ModelReader<Request> {

    @Override
    public Request read(final PathVariableMapping pathVariableMapping,
                        final HttpRequest request,
                        final boolean readParametersFromBody) {
        final Request model = new Request();
        readPrimitivesToModel(pathVariableMapping, request, QueryParams.of(), model, readParametersFromBody);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final Request model,
                                      final boolean readParametersFromBody) {
        final HttpHeaders httpHeaders = request.getHeaders();
        setFieldValue(model, "booleanHeader", toBoolean(httpHeaders.getValue("booleanHeader"), HttpModelType.HEADER, "booleanHeader"));
        setFieldValue(model, "byteHeader", toByte(httpHeaders.getValue("byteHeader"), HttpModelType.HEADER, "byteHeader"));
        setFieldValue(model, "shortHeader", toShort(httpHeaders.getValue("shortHeader"), HttpModelType.HEADER, "shortHeader"));
        setFieldValue(model, "intHeader", toInteger(httpHeaders.getValue("intHeader"), HttpModelType.HEADER, "intHeader"));
        setFieldValue(model, "longHeader", toLong(httpHeaders.getValue("longHeader"), HttpModelType.HEADER, "longHeader"));
        setFieldValue(model, "bigIntHeader", toBigInteger(httpHeaders.getValue("bigIntHeader"), HttpModelType.HEADER, "bigIntHeader"));
        setFieldValue(model, "floatHeader", toFloat(httpHeaders.getValue("floatHeader"), HttpModelType.HEADER, "floatHeader"));
        setFieldValue(model, "doubleHeader", toDouble(httpHeaders.getValue("doubleHeader"), HttpModelType.HEADER, "doubleHeader"));
        setFieldValue(model, "decimalHeader", toBigDecimal(httpHeaders.getValue("decimalHeader"), HttpModelType.HEADER, "decimalHeader"));
        setFieldValue(model, "charHeader", toCharacter(httpHeaders.getValue("charHeader"), HttpModelType.HEADER, "charHeader"));
        setFieldValue(model, "stringHeader", toString(httpHeaders.getValue("stringHeader"), HttpModelType.HEADER, "stringHeader"));
        setFieldValue(model, "instantHeader", toInstant(httpHeaders.getValue("instantHeader"), HttpModelType.HEADER, "instantHeader"));
        setFieldValue(model, "enumHeader", toEnum(Status.class, httpHeaders.getValue("enumHeader"), HttpModelType.HEADER, "enumHeader"));
        setFieldValue(model, "booleanHeaderList", toBooleanList(httpHeaders.getValues("booleanHeaderList"), HttpModelType.HEADER, "booleanHeaderList"));
        setFieldValue(model, "byteHeaderList", toByteList(httpHeaders.getValues("byteHeaderList"), HttpModelType.HEADER, "byteHeaderList"));
        setFieldValue(model, "shortHeaderList", toShortList(httpHeaders.getValues("shortHeaderList"), HttpModelType.HEADER, "shortHeaderList"));
        setFieldValue(model, "intHeaderList", toIntegerList(httpHeaders.getValues("intHeaderList"), HttpModelType.HEADER, "intHeaderList"));
        setFieldValue(model, "longHeaderList", toLongList(httpHeaders.getValues("longHeaderList"), HttpModelType.HEADER, "longHeaderList"));
        setFieldValue(model, "bigIntHeaderList", toBigIntegerList(httpHeaders.getValues("bigIntHeaderList"), HttpModelType.HEADER, "bigIntHeaderList"));
        setFieldValue(model, "floatHeaderList", toFloatList(httpHeaders.getValues("floatHeaderList"), HttpModelType.HEADER, "floatHeaderList"));
        setFieldValue(model, "doubleHeaderList", toDoubleList(httpHeaders.getValues("doubleHeaderList"), HttpModelType.HEADER, "doubleHeaderList"));
        setFieldValue(model, "decimalHeaderList", toBigDecimalList(httpHeaders.getValues("decimalHeaderList"), HttpModelType.HEADER, "decimalHeaderList"));
        setFieldValue(model, "charHeaderList", toCharacterList(httpHeaders.getValues("charHeaderList"), HttpModelType.HEADER, "charHeaderList"));
        setFieldValue(model, "stringHeaderList", toStringList(httpHeaders.getValues("stringHeaderList"), HttpModelType.HEADER, "stringHeaderList"));
        setFieldValue(model, "instantHeaderList", toInstantList(httpHeaders.getValues("instantHeaderList"), HttpModelType.HEADER, "instantHeaderList"));
        setFieldValue(model, "enumHeaderList", toEnumList(Status.class, httpHeaders.getValues("enumHeaderList"), HttpModelType.HEADER, "enumHeaderList"));
        setFieldValue(model, "booleanHeaderSet", toBooleanSet(httpHeaders.getValues("booleanHeaderSet"), HttpModelType.HEADER, "booleanHeaderSet"));
        setFieldValue(model, "byteHeaderSet", toByteSet(httpHeaders.getValues("byteHeaderSet"), HttpModelType.HEADER, "byteHeaderSet"));
        setFieldValue(model, "shortHeaderSet", toShortSet(httpHeaders.getValues("shortHeaderSet"), HttpModelType.HEADER, "shortHeaderSet"));
        setFieldValue(model, "intHeaderSet", toIntegerSet(httpHeaders.getValues("intHeaderSet"), HttpModelType.HEADER, "intHeaderSet"));
        setFieldValue(model, "longHeaderSet", toLongSet(httpHeaders.getValues("longHeaderSet"), HttpModelType.HEADER, "longHeaderSet"));
        setFieldValue(model, "bigIntHeaderSet", toBigIntegerSet(httpHeaders.getValues("bigIntHeaderSet"), HttpModelType.HEADER, "bigIntHeaderSet"));
        setFieldValue(model, "floatHeaderSet", toFloatSet(httpHeaders.getValues("floatHeaderSet"), HttpModelType.HEADER, "floatHeaderSet"));
        setFieldValue(model, "doubleHeaderSet", toDoubleSet(httpHeaders.getValues("doubleHeaderSet"), HttpModelType.HEADER, "doubleHeaderSet"));
        setFieldValue(model, "decimalHeaderSet", toBigDecimalSet(httpHeaders.getValues("decimalHeaderSet"), HttpModelType.HEADER, "decimalHeaderSet"));
        setFieldValue(model, "charHeaderSet", toCharacterSet(httpHeaders.getValues("charHeaderSet"), HttpModelType.HEADER, "charHeaderSet"));
        setFieldValue(model, "stringHeaderSet", toStringSet(httpHeaders.getValues("stringHeaderSet"), HttpModelType.HEADER, "stringHeaderSet"));
        setFieldValue(model, "instantHeaderSet", toInstantSet(httpHeaders.getValues("instantHeaderSet"), HttpModelType.HEADER, "instantHeaderSet"));
        setFieldValue(model, "enumHeaderSet", toEnumSet(Status.class, httpHeaders.getValues("enumHeaderSet"), HttpModelType.HEADER, "enumHeaderSet"));
        setFieldValue(model, "requestIdHeader", toString(httpHeaders.getValue("Request-Id"), HttpModelType.HEADER, "Request-Id"));
    }
}
