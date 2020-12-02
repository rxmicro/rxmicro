package io.rxmicro.examples.rest.controller.model.field.access.params.reflection;

import io.rxmicro.examples.rest.controller.model.field.access.Status;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

import static rxmicro.$$Reflections.setFieldValue;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$QueryRequestModelReader extends ModelReader<QueryRequest> {

    @Override
    public QueryRequest read(final PathVariableMapping pathVariableMapping,
                             final HttpRequest request,
                             final boolean readParametersFromBody) {
        final QueryRequest model = new QueryRequest();
        final QueryParams params = extractParams(request.getQueryString());
        readPrimitivesToModel(pathVariableMapping, request, params, model, readParametersFromBody);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final QueryRequest model,
                                      final boolean readParametersFromBody) {
        setFieldValue(model, "booleanParameter", toBoolean(params.getValue("booleanParameter"), HttpModelType.PARAMETER, "booleanParameter"));
        setFieldValue(model, "byteParameter", toByte(params.getValue("byteParameter"), HttpModelType.PARAMETER, "byteParameter"));
        setFieldValue(model, "shortParameter", toShort(params.getValue("shortParameter"), HttpModelType.PARAMETER, "shortParameter"));
        setFieldValue(model, "intParameter", toInteger(params.getValue("intParameter"), HttpModelType.PARAMETER, "intParameter"));
        setFieldValue(model, "longParameter", toLong(params.getValue("longParameter"), HttpModelType.PARAMETER, "longParameter"));
        setFieldValue(model, "bigIntParameter", toBigInteger(params.getValue("bigIntParameter"), HttpModelType.PARAMETER, "bigIntParameter"));
        setFieldValue(model, "floatParameter", toFloat(params.getValue("floatParameter"), HttpModelType.PARAMETER, "floatParameter"));
        setFieldValue(model, "doubleParameter", toDouble(params.getValue("doubleParameter"), HttpModelType.PARAMETER, "doubleParameter"));
        setFieldValue(model, "decimalParameter", toBigDecimal(params.getValue("decimalParameter"), HttpModelType.PARAMETER, "decimalParameter"));
        setFieldValue(model, "charParameter", toCharacter(params.getValue("charParameter"), HttpModelType.PARAMETER, "charParameter"));
        setFieldValue(model, "stringParameter", toString(params.getValue("stringParameter"), HttpModelType.PARAMETER, "stringParameter"));
        setFieldValue(model, "instantParameter", toInstant(params.getValue("instantParameter"), HttpModelType.PARAMETER, "instantParameter"));
        setFieldValue(model, "enumParameter", toEnum(Status.class, params.getValue("enumParameter"), HttpModelType.PARAMETER, "enumParameter"));
        setFieldValue(model, "booleanParameterList", toBooleanList(params.getValues("booleanParameterList"), HttpModelType.PARAMETER, "booleanParameterList"));
        setFieldValue(model, "byteParameterList", toByteList(params.getValues("byteParameterList"), HttpModelType.PARAMETER, "byteParameterList"));
        setFieldValue(model, "shortParameterList", toShortList(params.getValues("shortParameterList"), HttpModelType.PARAMETER, "shortParameterList"));
        setFieldValue(model, "intParameterList", toIntegerList(params.getValues("intParameterList"), HttpModelType.PARAMETER, "intParameterList"));
        setFieldValue(model, "longParameterList", toLongList(params.getValues("longParameterList"), HttpModelType.PARAMETER, "longParameterList"));
        setFieldValue(model, "bigIntParameterList", toBigIntegerList(params.getValues("bigIntParameterList"), HttpModelType.PARAMETER, "bigIntParameterList"));
        setFieldValue(model, "floatParameterList", toFloatList(params.getValues("floatParameterList"), HttpModelType.PARAMETER, "floatParameterList"));
        setFieldValue(model, "doubleParameterList", toDoubleList(params.getValues("doubleParameterList"), HttpModelType.PARAMETER, "doubleParameterList"));
        setFieldValue(model, "decimalParameterList", toBigDecimalList(params.getValues("decimalParameterList"), HttpModelType.PARAMETER, "decimalParameterList"));
        setFieldValue(model, "charParameterList", toCharacterList(params.getValues("charParameterList"), HttpModelType.PARAMETER, "charParameterList"));
        setFieldValue(model, "stringParameterList", toStringList(params.getValues("stringParameterList"), HttpModelType.PARAMETER, "stringParameterList"));
        setFieldValue(model, "instantParameterList", toInstantList(params.getValues("instantParameterList"), HttpModelType.PARAMETER, "instantParameterList"));
        setFieldValue(model, "enumParameterList", toEnumList(Status.class, params.getValues("enumParameterList"), HttpModelType.PARAMETER, "enumParameterList"));
        setFieldValue(model, "booleanParameterSet", toBooleanSet(params.getValues("booleanParameterSet"), HttpModelType.PARAMETER, "booleanParameterSet"));
        setFieldValue(model, "byteParameterSet", toByteSet(params.getValues("byteParameterSet"), HttpModelType.PARAMETER, "byteParameterSet"));
        setFieldValue(model, "shortParameterSet", toShortSet(params.getValues("shortParameterSet"), HttpModelType.PARAMETER, "shortParameterSet"));
        setFieldValue(model, "intParameterSet", toIntegerSet(params.getValues("intParameterSet"), HttpModelType.PARAMETER, "intParameterSet"));
        setFieldValue(model, "longParameterSet", toLongSet(params.getValues("longParameterSet"), HttpModelType.PARAMETER, "longParameterSet"));
        setFieldValue(model, "bigIntParameterSet", toBigIntegerSet(params.getValues("bigIntParameterSet"), HttpModelType.PARAMETER, "bigIntParameterSet"));
        setFieldValue(model, "floatParameterSet", toFloatSet(params.getValues("floatParameterSet"), HttpModelType.PARAMETER, "floatParameterSet"));
        setFieldValue(model, "doubleParameterSet", toDoubleSet(params.getValues("doubleParameterSet"), HttpModelType.PARAMETER, "doubleParameterSet"));
        setFieldValue(model, "decimalParameterSet", toBigDecimalSet(params.getValues("decimalParameterSet"), HttpModelType.PARAMETER, "decimalParameterSet"));
        setFieldValue(model, "charParameterSet", toCharacterSet(params.getValues("charParameterSet"), HttpModelType.PARAMETER, "charParameterSet"));
        setFieldValue(model, "stringParameterSet", toStringSet(params.getValues("stringParameterSet"), HttpModelType.PARAMETER, "stringParameterSet"));
        setFieldValue(model, "instantParameterSet", toInstantSet(params.getValues("instantParameterSet"), HttpModelType.PARAMETER, "instantParameterSet"));
        setFieldValue(model, "enumParameterSet", toEnumSet(Status.class, params.getValues("enumParameterSet"), HttpModelType.PARAMETER, "enumParameterSet"));
    }
}
