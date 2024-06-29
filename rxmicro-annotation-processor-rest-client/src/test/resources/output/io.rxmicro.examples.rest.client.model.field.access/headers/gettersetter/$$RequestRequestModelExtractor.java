package io.rxmicro.examples.rest.client.model.field.access.headers.gettersetter;

import io.rxmicro.examples.rest.client.model.field.access.Status;
import io.rxmicro.rest.client.detail.HeaderBuilder;
import io.rxmicro.rest.client.detail.RequestModelExtractor;
import org.junit.runner.Request;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$RequestRequestModelExtractor extends RequestModelExtractor<Request> {

    @Override
    public void extract(final Request model,
                        final HeaderBuilder headerBuilder) {
        headerBuilder.add("booleanHeader", model.getBooleanHeader());
        headerBuilder.add("byteHeader", model.getByteHeader());
        headerBuilder.add("shortHeader", model.getShortHeader());
        headerBuilder.add("intHeader", model.getIntHeader());
        headerBuilder.add("longHeader", model.getLongHeader());
        headerBuilder.add("bigIntHeader", model.getBigIntHeader());
        headerBuilder.add("floatHeader", model.getFloatHeader());
        headerBuilder.add("doubleHeader", model.getDoubleHeader());
        headerBuilder.add("decimalHeader", model.getDecimalHeader());
        headerBuilder.add("charHeader", model.getCharHeader());
        headerBuilder.add("stringHeader", model.getStringHeader());
        headerBuilder.add("instantHeader", model.getInstantHeader());
        headerBuilder.add("enumHeader", model.getEnumHeader());
        headerBuilder.add("booleanHeaderList", model.getBooleanHeaderList());
        headerBuilder.add("byteHeaderList", model.getByteHeaderList());
        headerBuilder.add("shortHeaderList", model.getShortHeaderList());
        headerBuilder.add("intHeaderList", model.getIntHeaderList());
        headerBuilder.add("longHeaderList", model.getLongHeaderList());
        headerBuilder.add("bigIntHeaderList", model.getBigIntHeaderList());
        headerBuilder.add("floatHeaderList", model.getFloatHeaderList());
        headerBuilder.add("doubleHeaderList", model.getDoubleHeaderList());
        headerBuilder.add("decimalHeaderList", model.getDecimalHeaderList());
        headerBuilder.add("charHeaderList", model.getCharHeaderList());
        headerBuilder.add("stringHeaderList", model.getStringHeaderList());
        headerBuilder.add("instantHeaderList", model.getInstantHeaderList());
        headerBuilder.add("enumHeaderList", model.getEnumHeaderList());
        headerBuilder.add("booleanHeaderSet", model.getBooleanHeaderSet());
        headerBuilder.add("byteHeaderSet", model.getByteHeaderSet());
        headerBuilder.add("shortHeaderSet", model.getShortHeaderSet());
        headerBuilder.add("intHeaderSet", model.getIntHeaderSet());
        headerBuilder.add("longHeaderSet", model.getLongHeaderSet());
        headerBuilder.add("bigIntHeaderSet", model.getBigIntHeaderSet());
        headerBuilder.add("floatHeaderSet", model.getFloatHeaderSet());
        headerBuilder.add("doubleHeaderSet", model.getDoubleHeaderSet());
        headerBuilder.add("decimalHeaderSet", model.getDecimalHeaderSet());
        headerBuilder.add("charHeaderSet", model.getCharHeaderSet());
        headerBuilder.add("stringHeaderSet", model.getStringHeaderSet());
        headerBuilder.add("instantHeaderSet", model.getInstantHeaderSet());
        headerBuilder.add("enumHeaderSet", model.getEnumHeaderSet());
        headerBuilder.add("Request-Id", model.getRequestIdHeader());
        for (final Status item : model.getRepeatingEnumHeaderList()) {
            headerBuilder.add("repeatingEnumHeaderList", item);
        }
        for (final Status item : model.getRepeatingEnumHeaderSet()) {
            headerBuilder.add("repeatingEnumHeaderSet", item);
        }
    }
}
