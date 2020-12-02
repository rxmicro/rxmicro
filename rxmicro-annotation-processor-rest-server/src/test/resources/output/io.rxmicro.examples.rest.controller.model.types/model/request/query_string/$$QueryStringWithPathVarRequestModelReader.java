package io.rxmicro.examples.rest.controller.model.types.model.request.query_string;

import io.rxmicro.examples.rest.controller.model.types.model.Status;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$QueryStringWithPathVarRequestModelReader extends ModelReader<QueryStringWithPathVarRequest> {

    @Override
    public QueryStringWithPathVarRequest read(final PathVariableMapping pathVariableMapping,
                                              final HttpRequest request,
                                              final boolean readParametersFromBody) {
        final QueryStringWithPathVarRequest model = new QueryStringWithPathVarRequest();
        final QueryParams params = extractParams(request.getQueryString());
        readPrimitivesToModel(pathVariableMapping, request, params, model, readParametersFromBody);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final QueryStringWithPathVarRequest model,
                                      final boolean readParametersFromBody) {
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
