package io.rxmicro.examples.rest.client.model.types.model.request.query_string;

import io.rxmicro.examples.rest.client.model.types.model.Status;
import io.rxmicro.rest.client.detail.HeaderBuilder;
import io.rxmicro.rest.client.detail.QueryBuilder;
import io.rxmicro.rest.client.detail.RequestModelExtractor;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$QueryStringWithHeadersRequestRequestModelExtractor extends RequestModelExtractor<QueryStringWithHeadersRequest> {

    @Override
    public void extract(final QueryStringWithHeadersRequest model,
                        final HeaderBuilder headerBuilder,
                        final QueryBuilder queryBuilder) {
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
        queryBuilder.add("booleanParameter", model.booleanParameter);
        queryBuilder.add("byteParameter", model.byteParameter);
        queryBuilder.add("shortParameter", model.shortParameter);
        queryBuilder.add("intParameter", model.intParameter);
        queryBuilder.add("longParameter", model.longParameter);
        queryBuilder.add("bigIntParameter", model.bigIntParameter);
        queryBuilder.add("floatParameter", model.floatParameter);
        queryBuilder.add("doubleParameter", model.doubleParameter);
        queryBuilder.add("decimalParameter", model.decimalParameter);
        queryBuilder.add("charParameter", model.charParameter);
        queryBuilder.add("stringParameter", model.stringParameter);
        queryBuilder.add("instantParameter", model.instantParameter);
        queryBuilder.add("enumParameter", model.enumParameter);
        queryBuilder.add("booleanParameterList", model.booleanParameterList);
        queryBuilder.add("byteParameterList", model.byteParameterList);
        queryBuilder.add("shortParameterList", model.shortParameterList);
        queryBuilder.add("intParameterList", model.intParameterList);
        queryBuilder.add("longParameterList", model.longParameterList);
        queryBuilder.add("bigIntParameterList", model.bigIntParameterList);
        queryBuilder.add("floatParameterList", model.floatParameterList);
        queryBuilder.add("doubleParameterList", model.doubleParameterList);
        queryBuilder.add("decimalParameterList", model.decimalParameterList);
        queryBuilder.add("charParameterList", model.charParameterList);
        queryBuilder.add("stringParameterList", model.stringParameterList);
        queryBuilder.add("instantParameterList", model.instantParameterList);
        queryBuilder.add("enumParameterList", model.enumParameterList);
        queryBuilder.add("booleanParameterSet", model.booleanParameterSet);
        queryBuilder.add("byteParameterSet", model.byteParameterSet);
        queryBuilder.add("shortParameterSet", model.shortParameterSet);
        queryBuilder.add("intParameterSet", model.intParameterSet);
        queryBuilder.add("longParameterSet", model.longParameterSet);
        queryBuilder.add("bigIntParameterSet", model.bigIntParameterSet);
        queryBuilder.add("floatParameterSet", model.floatParameterSet);
        queryBuilder.add("doubleParameterSet", model.doubleParameterSet);
        queryBuilder.add("decimalParameterSet", model.decimalParameterSet);
        queryBuilder.add("charParameterSet", model.charParameterSet);
        queryBuilder.add("stringParameterSet", model.stringParameterSet);
        queryBuilder.add("instantParameterSet", model.instantParameterSet);
        queryBuilder.add("enumParameterSet", model.enumParameterSet);
    }
}
