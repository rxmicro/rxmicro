package io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body;

import io.rxmicro.examples.rest.controller.model.types.model.Status;
import io.rxmicro.exchange.json.detail.ModelFromJsonConverter;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$QueryOrHttpBodyWithPathVarAndHeadersRequestModelFromJsonConverter extends ModelFromJsonConverter<QueryOrHttpBodyWithPathVarAndHeadersRequest> {

    @Override
    public QueryOrHttpBodyWithPathVarAndHeadersRequest fromJsonObject(final Map<String, Object> params) {
        final QueryOrHttpBodyWithPathVarAndHeadersRequest model = new QueryOrHttpBodyWithPathVarAndHeadersRequest();
        readParamsToModel(params, model);
        return model;
    }

    public void readParamsToModel(final Map<String, Object> params,
                                  final QueryOrHttpBodyWithPathVarAndHeadersRequest model) {
        model.booleanParameter = toBoolean(params.get("booleanParameter"), "booleanParameter");
        model.byteParameter = toByte(params.get("byteParameter"), "byteParameter");
        model.shortParameter = toShort(params.get("shortParameter"), "shortParameter");
        model.intParameter = toInteger(params.get("intParameter"), "intParameter");
        model.longParameter = toLong(params.get("longParameter"), "longParameter");
        model.bigIntParameter = toBigInteger(params.get("bigIntParameter"), "bigIntParameter");
        model.floatParameter = toFloat(params.get("floatParameter"), "floatParameter");
        model.doubleParameter = toDouble(params.get("doubleParameter"), "doubleParameter");
        model.decimalParameter = toBigDecimal(params.get("decimalParameter"), "decimalParameter");
        model.charParameter = toCharacter(params.get("charParameter"), "charParameter");
        model.stringParameter = toString(params.get("stringParameter"), "stringParameter");
        model.instantParameter = toInstant(params.get("instantParameter"), "instantParameter");
        model.enumParameter = toEnum(Status.class, params.get("enumParameter"), "enumParameter");
        model.booleanParameterList = toBooleanList(params.get("booleanParameterList"), "booleanParameterList");
        model.byteParameterList = toByteList(params.get("byteParameterList"), "byteParameterList");
        model.shortParameterList = toShortList(params.get("shortParameterList"), "shortParameterList");
        model.intParameterList = toIntegerList(params.get("intParameterList"), "intParameterList");
        model.longParameterList = toLongList(params.get("longParameterList"), "longParameterList");
        model.bigIntParameterList = toBigIntegerList(params.get("bigIntParameterList"), "bigIntParameterList");
        model.floatParameterList = toFloatList(params.get("floatParameterList"), "floatParameterList");
        model.doubleParameterList = toDoubleList(params.get("doubleParameterList"), "doubleParameterList");
        model.decimalParameterList = toBigDecimalList(params.get("decimalParameterList"), "decimalParameterList");
        model.charParameterList = toCharacterList(params.get("charParameterList"), "charParameterList");
        model.stringParameterList = toStringList(params.get("stringParameterList"), "stringParameterList");
        model.instantParameterList = toInstantList(params.get("instantParameterList"), "instantParameterList");
        model.enumParameterList = toEnumList(Status.class, params.get("enumParameterList"), "enumParameterList");
        model.booleanParameterSet = toBooleanSet(params.get("booleanParameterSet"), "booleanParameterSet");
        model.byteParameterSet = toByteSet(params.get("byteParameterSet"), "byteParameterSet");
        model.shortParameterSet = toShortSet(params.get("shortParameterSet"), "shortParameterSet");
        model.intParameterSet = toIntegerSet(params.get("intParameterSet"), "intParameterSet");
        model.longParameterSet = toLongSet(params.get("longParameterSet"), "longParameterSet");
        model.bigIntParameterSet = toBigIntegerSet(params.get("bigIntParameterSet"), "bigIntParameterSet");
        model.floatParameterSet = toFloatSet(params.get("floatParameterSet"), "floatParameterSet");
        model.doubleParameterSet = toDoubleSet(params.get("doubleParameterSet"), "doubleParameterSet");
        model.decimalParameterSet = toBigDecimalSet(params.get("decimalParameterSet"), "decimalParameterSet");
        model.charParameterSet = toCharacterSet(params.get("charParameterSet"), "charParameterSet");
        model.stringParameterSet = toStringSet(params.get("stringParameterSet"), "stringParameterSet");
        model.instantParameterSet = toInstantSet(params.get("instantParameterSet"), "instantParameterSet");
        model.enumParameterSet = toEnumSet(Status.class, params.get("enumParameterSet"), "enumParameterSet");
    }
}
