package io.rxmicro.examples.rest.controller.model.field.access.params.reflection;

import io.rxmicro.examples.rest.controller.model.field.access.Status;
import io.rxmicro.examples.rest.controller.model.field.access.params.reflection.nested.$$NestedModelFromJsonConverter;
import io.rxmicro.exchange.json.detail.ModelFromJsonConverter;

import java.util.Map;

import static rxmicro.$$Reflections.setFieldValue;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$BodyRequestModelFromJsonConverter extends ModelFromJsonConverter<BodyRequest> {

    private final $$NestedModelFromJsonConverter nestedModelFromJsonConverter =
            new $$NestedModelFromJsonConverter();

    @Override
    public BodyRequest fromJsonObject(final Map<String, Object> params) {
        final BodyRequest model = new BodyRequest();
        readParamsToModel(params, model);
        return model;
    }

    public void readParamsToModel(final Map<String, Object> params,
                                  final BodyRequest model) {
        setFieldValue(model, "booleanParameter", toBoolean(params.get("booleanParameter"), "booleanParameter"));
        setFieldValue(model, "byteParameter", toByte(params.get("byteParameter"), "byteParameter"));
        setFieldValue(model, "shortParameter", toShort(params.get("shortParameter"), "shortParameter"));
        setFieldValue(model, "intParameter", toInteger(params.get("intParameter"), "intParameter"));
        setFieldValue(model, "longParameter", toLong(params.get("longParameter"), "longParameter"));
        setFieldValue(model, "bigIntParameter", toBigInteger(params.get("bigIntParameter"), "bigIntParameter"));
        setFieldValue(model, "floatParameter", toFloat(params.get("floatParameter"), "floatParameter"));
        setFieldValue(model, "doubleParameter", toDouble(params.get("doubleParameter"), "doubleParameter"));
        setFieldValue(model, "decimalParameter", toBigDecimal(params.get("decimalParameter"), "decimalParameter"));
        setFieldValue(model, "charParameter", toCharacter(params.get("charParameter"), "charParameter"));
        setFieldValue(model, "stringParameter", toString(params.get("stringParameter"), "stringParameter"));
        setFieldValue(model, "instantParameter", toInstant(params.get("instantParameter"), "instantParameter"));
        setFieldValue(model, "enumParameter", toEnum(Status.class, params.get("enumParameter"), "enumParameter"));
        setFieldValue(model, "booleanParameterList", toBooleanList(params.get("booleanParameterList"), "booleanParameterList"));
        setFieldValue(model, "byteParameterList", toByteList(params.get("byteParameterList"), "byteParameterList"));
        setFieldValue(model, "shortParameterList", toShortList(params.get("shortParameterList"), "shortParameterList"));
        setFieldValue(model, "intParameterList", toIntegerList(params.get("intParameterList"), "intParameterList"));
        setFieldValue(model, "longParameterList", toLongList(params.get("longParameterList"), "longParameterList"));
        setFieldValue(model, "bigIntParameterList", toBigIntegerList(params.get("bigIntParameterList"), "bigIntParameterList"));
        setFieldValue(model, "floatParameterList", toFloatList(params.get("floatParameterList"), "floatParameterList"));
        setFieldValue(model, "doubleParameterList", toDoubleList(params.get("doubleParameterList"), "doubleParameterList"));
        setFieldValue(model, "decimalParameterList", toBigDecimalList(params.get("decimalParameterList"), "decimalParameterList"));
        setFieldValue(model, "charParameterList", toCharacterList(params.get("charParameterList"), "charParameterList"));
        setFieldValue(model, "stringParameterList", toStringList(params.get("stringParameterList"), "stringParameterList"));
        setFieldValue(model, "instantParameterList", toInstantList(params.get("instantParameterList"), "instantParameterList"));
        setFieldValue(model, "enumParameterList", toEnumList(Status.class, params.get("enumParameterList"), "enumParameterList"));
        setFieldValue(model, "booleanParameterSet", toBooleanSet(params.get("booleanParameterSet"), "booleanParameterSet"));
        setFieldValue(model, "byteParameterSet", toByteSet(params.get("byteParameterSet"), "byteParameterSet"));
        setFieldValue(model, "shortParameterSet", toShortSet(params.get("shortParameterSet"), "shortParameterSet"));
        setFieldValue(model, "intParameterSet", toIntegerSet(params.get("intParameterSet"), "intParameterSet"));
        setFieldValue(model, "longParameterSet", toLongSet(params.get("longParameterSet"), "longParameterSet"));
        setFieldValue(model, "bigIntParameterSet", toBigIntegerSet(params.get("bigIntParameterSet"), "bigIntParameterSet"));
        setFieldValue(model, "floatParameterSet", toFloatSet(params.get("floatParameterSet"), "floatParameterSet"));
        setFieldValue(model, "doubleParameterSet", toDoubleSet(params.get("doubleParameterSet"), "doubleParameterSet"));
        setFieldValue(model, "decimalParameterSet", toBigDecimalSet(params.get("decimalParameterSet"), "decimalParameterSet"));
        setFieldValue(model, "charParameterSet", toCharacterSet(params.get("charParameterSet"), "charParameterSet"));
        setFieldValue(model, "stringParameterSet", toStringSet(params.get("stringParameterSet"), "stringParameterSet"));
        setFieldValue(model, "instantParameterSet", toInstantSet(params.get("instantParameterSet"), "instantParameterSet"));
        setFieldValue(model, "enumParameterSet", toEnumSet(Status.class, params.get("enumParameterSet"), "enumParameterSet"));
        setFieldValue(model, "booleanParameterMap", toBooleanMap(params.get("booleanParameterMap"), "booleanParameterMap"));
        setFieldValue(model, "byteParameterMap", toByteMap(params.get("byteParameterMap"), "byteParameterMap"));
        setFieldValue(model, "shortParameterMap", toShortMap(params.get("shortParameterMap"), "shortParameterMap"));
        setFieldValue(model, "integerParameterMap", toIntegerMap(params.get("integerParameterMap"), "integerParameterMap"));
        setFieldValue(model, "longParameterMap", toLongMap(params.get("longParameterMap"), "longParameterMap"));
        setFieldValue(model, "bigIntegerParameterMap", toBigIntegerMap(params.get("bigIntegerParameterMap"), "bigIntegerParameterMap"));
        setFieldValue(model, "floatParameterMap", toFloatMap(params.get("floatParameterMap"), "floatParameterMap"));
        setFieldValue(model, "doubleParameterMap", toDoubleMap(params.get("doubleParameterMap"), "doubleParameterMap"));
        setFieldValue(model, "bigDecimalParameterMap", toBigDecimalMap(params.get("bigDecimalParameterMap"), "bigDecimalParameterMap"));
        setFieldValue(model, "characterParameterMap", toCharacterMap(params.get("characterParameterMap"), "characterParameterMap"));
        setFieldValue(model, "stringParameterMap", toStringMap(params.get("stringParameterMap"), "stringParameterMap"));
        setFieldValue(model, "instantParameterMap", toInstantMap(params.get("instantParameterMap"), "instantParameterMap"));
        setFieldValue(model, "enumParameterMap", toEnumMap(Status.class, params.get("enumParameterMap"), "enumParameterMap"));
        setFieldValue(model, "nested", convertToObjectIfNotNull(nestedModelFromJsonConverter, params.get("nested"), "nested"));
        setFieldValue(model, "nestedList", convertToListIfNotNull(nestedModelFromJsonConverter, params.get("nestedList"), "nestedList"));
        setFieldValue(model, "nestedSet", convertToSetIfNotNull(nestedModelFromJsonConverter, params.get("nestedSet"), "nestedSet"));
        setFieldValue(model, "nestedParameterMap", convertToMapIfNotNull(nestedModelFromJsonConverter, params.get("nestedParameterMap"), "nestedParameterMap"));
    }
}
