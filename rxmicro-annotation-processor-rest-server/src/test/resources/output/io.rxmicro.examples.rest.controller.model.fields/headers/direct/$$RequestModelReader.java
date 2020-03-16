package io.rxmicro.examples.rest.controller.model.fields.headers.direct;

import io.rxmicro.examples.rest.controller.model.fields.Status;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$RequestModelReader extends ModelReader<Request> {

    @Override
    public Request read(final PathVariableMapping pathVariableMapping,
                        final HttpRequest request,
                        final boolean readParametersFromBody) {
        final Request model = new Request();
        final HttpHeaders httpHeaders = request.getHeaders();
        model.booleanParameter = toBoolean(httpHeaders.getValue("booleanParameter"), HttpModelType.header, "booleanParameter");
        model.byteParameter = toByte(httpHeaders.getValue("byteParameter"), HttpModelType.header, "byteParameter");
        model.shortParameter = toShort(httpHeaders.getValue("shortParameter"), HttpModelType.header, "shortParameter");
        model.intParameter = toInteger(httpHeaders.getValue("intParameter"), HttpModelType.header, "intParameter");
        model.longParameter = toLong(httpHeaders.getValue("longParameter"), HttpModelType.header, "longParameter");
        model.bigIntParameter = toBigInteger(httpHeaders.getValue("bigIntParameter"), HttpModelType.header, "bigIntParameter");
        model.floatParameter = toFloat(httpHeaders.getValue("floatParameter"), HttpModelType.header, "floatParameter");
        model.doubleParameter = toDouble(httpHeaders.getValue("doubleParameter"), HttpModelType.header, "doubleParameter");
        model.decimalParameter = toBigDecimal(httpHeaders.getValue("decimalParameter"), HttpModelType.header, "decimalParameter");
        model.charParameter = toCharacter(httpHeaders.getValue("charParameter"), HttpModelType.header, "charParameter");
        model.stringParameter = toString(httpHeaders.getValue("stringParameter"), HttpModelType.header, "stringParameter");
        model.instantParameter = toInstant(httpHeaders.getValue("instantParameter"), HttpModelType.header, "instantParameter");
        model.status = toEnum(Status.class, httpHeaders.getValue("status"), HttpModelType.header, "status");
        model.booleanParameters = toBooleanArray(httpHeaders.getValues("booleanParameters"), HttpModelType.header, "booleanParameters");
        model.byteParameters = toByteArray(httpHeaders.getValues("byteParameters"), HttpModelType.header, "byteParameters");
        model.shortParameters = toShortArray(httpHeaders.getValues("shortParameters"), HttpModelType.header, "shortParameters");
        model.intParameters = toIntegerArray(httpHeaders.getValues("intParameters"), HttpModelType.header, "intParameters");
        model.longParameters = toLongArray(httpHeaders.getValues("longParameters"), HttpModelType.header, "longParameters");
        model.bigIntParameters = toBigIntegerArray(httpHeaders.getValues("bigIntParameters"), HttpModelType.header, "bigIntParameters");
        model.floatParameters = toFloatArray(httpHeaders.getValues("floatParameters"), HttpModelType.header, "floatParameters");
        model.doubleParameters = toDoubleArray(httpHeaders.getValues("doubleParameters"), HttpModelType.header, "doubleParameters");
        model.decimalParameters = toBigDecimalArray(httpHeaders.getValues("decimalParameters"), HttpModelType.header, "decimalParameters");
        model.charParameters = toCharacterArray(httpHeaders.getValues("charParameters"), HttpModelType.header, "charParameters");
        model.stringParameters = toStringArray(httpHeaders.getValues("stringParameters"), HttpModelType.header, "stringParameters");
        model.instantParameters = toInstantArray(httpHeaders.getValues("instantParameters"), HttpModelType.header, "instantParameters");
        model.statuses = toEnumArray(Status.class, httpHeaders.getValues("statuses"), HttpModelType.header, "statuses");
        return model;
    }
}
