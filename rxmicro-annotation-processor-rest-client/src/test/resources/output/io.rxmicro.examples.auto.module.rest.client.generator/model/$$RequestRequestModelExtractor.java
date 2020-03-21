package io.rxmicro.examples.auto.module.rest.client.generator.model;

import io.rxmicro.rest.client.detail.QueryBuilder;
import io.rxmicro.rest.client.detail.RequestModelExtractor;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$RequestRequestModelExtractor extends RequestModelExtractor<Request> {

    @Override
    public void extract(final Request model,
                        final QueryBuilder queryBuilder) {
        queryBuilder.add("email", model.email);
    }
}
