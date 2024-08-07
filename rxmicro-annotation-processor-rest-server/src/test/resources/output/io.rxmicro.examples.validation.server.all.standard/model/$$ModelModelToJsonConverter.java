package io.rxmicro.examples.validation.server.all.standard.model;

import io.rxmicro.exchange.json.detail.ModelToJsonConverter;
import io.rxmicro.json.JsonObjectBuilder;

import java.util.Map;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ModelModelToJsonConverter extends ModelToJsonConverter<Model> {

    @Override
    public Map<String, Object> toJsonObject(final Model model) {
        final JsonObjectBuilder builder = new JsonObjectBuilder();
        putValuesToBuilder(model, builder);
        return builder.build();
    }

    public void putValuesToBuilder(final Model model,
                                   final JsonObjectBuilder builder) {
        builder.put("optionalParameter", model.optionalParameter);
        builder.put("booleanParameter", model.booleanParameter);
        builder.put("byteParameter", model.byteParameter);
        builder.put("shortParameter", model.shortParameter);
        builder.put("intParameter", model.intParameter);
        builder.put("longParameter", model.longParameter);
        builder.put("bigIntParameter", model.bigIntParameter);
        builder.put("floatParameter", model.floatParameter);
        builder.put("doubleParameter", model.doubleParameter);
        builder.put("decimalParameter", model.decimalParameter);
        builder.put("charParameter", model.charParameter);
        builder.put("stringParameter", model.stringParameter);
        builder.put("instantParameter", model.instantParameter);
        builder.put("colorParameter", model.colorParameter);
        builder.put("optionalList", model.optionalList);
        builder.put("booleanValues", model.booleanValues);
        builder.put("byteValues", model.byteValues);
        builder.put("shortValues", model.shortValues);
        builder.put("intValues", model.intValues);
        builder.put("longValues", model.longValues);
        builder.put("charValues", model.charValues);
        builder.put("floatValues", model.floatValues);
        builder.put("doubleValues", model.doubleValues);
        builder.put("decimals", model.decimals);
        builder.put("bigIntegers", model.bigIntegers);
        builder.put("strings", model.strings);
        builder.put("instants", model.instants);
        builder.put("colors", model.colors);
        builder.put("optionalSet", model.optionalSet);
        builder.put("booleanSet", model.booleanSet);
        builder.put("byteSet", model.byteSet);
        builder.put("shortSet", model.shortSet);
        builder.put("intSet", model.intSet);
        builder.put("longSet", model.longSet);
        builder.put("charSet", model.charSet);
        builder.put("floatSet", model.floatSet);
        builder.put("doubleSet", model.doubleSet);
        builder.put("decimalSet", model.decimalSet);
        builder.put("bigIntegerSet", model.bigIntegerSet);
        builder.put("stringSet", model.stringSet);
        builder.put("instantSet", model.instantSet);
        builder.put("colorSet", model.colorSet);
        builder.put("optionalMap", model.optionalMap);
        builder.put("booleanMap", model.booleanMap);
        builder.put("byteMap", model.byteMap);
        builder.put("shortMap", model.shortMap);
        builder.put("intMap", model.intMap);
        builder.put("longMap", model.longMap);
        builder.put("charMap", model.charMap);
        builder.put("floatMap", model.floatMap);
        builder.put("doubleMap", model.doubleMap);
        builder.put("decimalMap", model.decimalMap);
        builder.put("bigIntegerMap", model.bigIntegerMap);
        builder.put("stringMap", model.stringMap);
        builder.put("instantMap", model.instantMap);
        builder.put("colorMap", model.colorMap);
        builder.put("countryCodeAlpha2", model.countryCodeAlpha2);
        builder.put("countryCodeAlpha3", model.countryCodeAlpha3);
        builder.put("countryCodeNumeric", model.countryCodeNumeric);
        builder.put("base64URLEncodedBase", model.base64URLEncodedBase);
        builder.put("base64URLEncodedUrl", model.base64URLEncodedUrl);
        builder.put("ip", model.ip);
        builder.put("ip4", model.ip4);
        builder.put("ip6", model.ip6);
        builder.put("lat111km", model.lat111km);
        builder.put("lng111km", model.lng111km);
        builder.put("lat11km", model.lat11km);
        builder.put("lng11km", model.lng11km);
        builder.put("lat1km", model.lat1km);
        builder.put("lng1km", model.lng1km);
        builder.put("lat111m", model.lat111m);
        builder.put("lng111m", model.lng111m);
        builder.put("lat11m", model.lat11m);
        builder.put("lng11m", model.lng11m);
        builder.put("lat1m", model.lat1m);
        builder.put("lng1m", model.lng1m);
        builder.put("lat11cm", model.lat11cm);
        builder.put("lng11cm", model.lng11cm);
        builder.put("lat1cm", model.lat1cm);
        builder.put("lng1cm", model.lng1cm);
        builder.put("port", model.port);
    }
}
