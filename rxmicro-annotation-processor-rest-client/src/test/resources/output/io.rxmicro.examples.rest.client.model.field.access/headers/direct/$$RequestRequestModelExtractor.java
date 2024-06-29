package io.rxmicro.examples.rest.client.model.field.access.headers.direct;

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
        headerBuilder.add("booleanHeader", model.booleanHeader);
        headerBuilder.add("byteHeader", model.byteHeader);
        headerBuilder.add("shortHeader", model.shortHeader);
        headerBuilder.add("intHeader", model.intHeader);
        headerBuilder.add("longHeader", model.longHeader);
        headerBuilder.add("bigIntHeader", model.bigIntHeader);
        headerBuilder.add("floatHeader", model.floatHeader);
        headerBuilder.add("doubleHeader", model.doubleHeader);
        headerBuilder.add("decimalHeader", model.decimalHeader);
        headerBuilder.add("charHeader", model.charHeader);
        headerBuilder.add("stringHeader", model.stringHeader);
        headerBuilder.add("instantHeader", model.instantHeader);
        headerBuilder.add("enumHeader", model.enumHeader);
        headerBuilder.add("booleanHeaderList", model.booleanHeaderList);
        headerBuilder.add("byteHeaderList", model.byteHeaderList);
        headerBuilder.add("shortHeaderList", model.shortHeaderList);
        headerBuilder.add("intHeaderList", model.intHeaderList);
        headerBuilder.add("longHeaderList", model.longHeaderList);
        headerBuilder.add("bigIntHeaderList", model.bigIntHeaderList);
        headerBuilder.add("floatHeaderList", model.floatHeaderList);
        headerBuilder.add("doubleHeaderList", model.doubleHeaderList);
        headerBuilder.add("decimalHeaderList", model.decimalHeaderList);
        headerBuilder.add("charHeaderList", model.charHeaderList);
        headerBuilder.add("stringHeaderList", model.stringHeaderList);
        headerBuilder.add("instantHeaderList", model.instantHeaderList);
        headerBuilder.add("enumHeaderList", model.enumHeaderList);
        headerBuilder.add("booleanHeaderSet", model.booleanHeaderSet);
        headerBuilder.add("byteHeaderSet", model.byteHeaderSet);
        headerBuilder.add("shortHeaderSet", model.shortHeaderSet);
        headerBuilder.add("intHeaderSet", model.intHeaderSet);
        headerBuilder.add("longHeaderSet", model.longHeaderSet);
        headerBuilder.add("bigIntHeaderSet", model.bigIntHeaderSet);
        headerBuilder.add("floatHeaderSet", model.floatHeaderSet);
        headerBuilder.add("doubleHeaderSet", model.doubleHeaderSet);
        headerBuilder.add("decimalHeaderSet", model.decimalHeaderSet);
        headerBuilder.add("charHeaderSet", model.charHeaderSet);
        headerBuilder.add("stringHeaderSet", model.stringHeaderSet);
        headerBuilder.add("instantHeaderSet", model.instantHeaderSet);
        headerBuilder.add("enumHeaderSet", model.enumHeaderSet);
        headerBuilder.add("Request-Id", model.requestIdHeader);
        for (final Status item : model.repeatingEnumHeaderList) {
            headerBuilder.add("repeatingEnumHeaderList", item);
        }
        for (final Status item : model.repeatingEnumHeaderSet) {
            headerBuilder.add("repeatingEnumHeaderSet", item);
        }
    }
}
