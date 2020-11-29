package io.rxmicro.examples.rest.controller.headers.model;

import io.rxmicro.rest.server.detail.component.ModelWriter;
import io.rxmicro.rest.server.detail.model.HttpResponse;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$AllSupportedTypesModelWriter extends ModelWriter<AllSupportedTypes> {

    public $$AllSupportedTypesModelWriter(final boolean humanReadableOutput) {
        //do nothing
    }

    @Override
    public void write(final AllSupportedTypes model,
                      final HttpResponse response) {
        setHeaders(model, response);
    }

    protected void setHeaders(final AllSupportedTypes model,
                              final HttpResponse response) {
        response.setHeader("Status", model.status);
        response.setHeader("Status-List", model.statusList);
        response.setHeader("A-Boolean", model.aBoolean);
        response.setHeader("Boolean-List", model.booleanList);
        response.setHeader("A-Byte", model.aByte);
        response.setHeader("Byte-List", model.byteList);
        response.setHeader("A-Short", model.aShort);
        response.setHeader("Short-List", model.shortList);
        response.setHeader("A-Integer", model.aInteger);
        response.setHeader("Integer-List", model.integerList);
        response.setHeader("A-Long", model.aLong);
        response.setHeader("Long-List", model.longList);
        response.setHeader("Big-Integer", model.bigInteger);
        response.setHeader("Big-Integer-List", model.bigIntegerList);
        response.setHeader("A-Float", model.aFloat);
        response.setHeader("Float-List", model.floatList);
        response.setHeader("A-Double", model.aDouble);
        response.setHeader("Double-List", model.doubleList);
        response.setHeader("Big-Decimal", model.bigDecimal);
        response.setHeader("Big-Decimal-List", model.bigDecimalList);
        response.setHeader("Character", model.character);
        response.setHeader("Character-List", model.characterList);
        response.setHeader("String", model.string);
        response.setHeader("String-List", model.stringList);
        response.setHeader("Instant", model.instant);
        response.setHeader("Instant-List", model.instantList);
    }
}
