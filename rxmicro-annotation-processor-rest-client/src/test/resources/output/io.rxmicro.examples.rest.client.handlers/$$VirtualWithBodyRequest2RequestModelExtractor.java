package io.rxmicro.examples.rest.client.handlers;

import io.rxmicro.rest.client.detail.QueryBuilder;
import io.rxmicro.rest.client.detail.RequestModelExtractor;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualWithBodyRequest2RequestModelExtractor extends RequestModelExtractor<$$VirtualWithBodyRequest2> {

    @Override
    public void extract(final $$VirtualWithBodyRequest2 model,
                        final QueryBuilder queryBuilder) {
        queryBuilder.add("requestParameter", model.requestParameter);
    }
}
