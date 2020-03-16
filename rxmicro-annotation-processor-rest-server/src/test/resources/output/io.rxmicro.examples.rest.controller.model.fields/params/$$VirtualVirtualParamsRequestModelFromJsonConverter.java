package io.rxmicro.examples.rest.controller.model.fields.params;

import io.rxmicro.examples.rest.controller.model.fields.Status;
import io.rxmicro.examples.rest.controller.model.fields.params.direct.nested.$$NestedModelFromJsonConverter;
import io.rxmicro.exchange.json.detail.ModelFromJsonConverter;

import java.util.List;
import java.util.Map;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$VirtualVirtualParamsRequestModelFromJsonConverter extends ModelFromJsonConverter<$$VirtualVirtualParamsRequest> {

    private final $$NestedModelFromJsonConverter nestedModelFromJsonConverter =
            new $$NestedModelFromJsonConverter();

    @Override
    public $$VirtualVirtualParamsRequest fromJsonObject(final Map<String, Object> params) {
        final $$VirtualVirtualParamsRequest model = new $$VirtualVirtualParamsRequest();
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
        model.status = toEnum(Status.class, params.get("status"), "status");
        model.nested = convertIfNotNull(nestedModelFromJsonConverter, (Map<String, Object>) params.get("nested"));
        model.booleanParameters = toBooleanArray(params.get("booleanParameters"), "booleanParameters");
        model.byteParameters = toByteArray(params.get("byteParameters"), "byteParameters");
        model.shortParameters = toShortArray(params.get("shortParameters"), "shortParameters");
        model.intParameters = toIntegerArray(params.get("intParameters"), "intParameters");
        model.longParameters = toLongArray(params.get("longParameters"), "longParameters");
        model.bigIntParameters = toBigIntegerArray(params.get("bigIntParameters"), "bigIntParameters");
        model.floatParameters = toFloatArray(params.get("floatParameters"), "floatParameters");
        model.doubleParameters = toDoubleArray(params.get("doubleParameters"), "doubleParameters");
        model.decimalParameters = toBigDecimalArray(params.get("decimalParameters"), "decimalParameters");
        model.charParameters = toCharacterArray(params.get("charParameters"), "charParameters");
        model.stringParameters = toStringArray(params.get("stringParameters"), "stringParameters");
        model.instantParameters = toInstantArray(params.get("instantParameters"), "instantParameters");
        model.statuses = toEnumArray(Status.class, params.get("statuses"), "statuses");
        model.nestedList = convertIfNotNull(nestedModelFromJsonConverter, (List<Object>) params.get("nestedList"), "nestedList");
        return model;
    }
}
