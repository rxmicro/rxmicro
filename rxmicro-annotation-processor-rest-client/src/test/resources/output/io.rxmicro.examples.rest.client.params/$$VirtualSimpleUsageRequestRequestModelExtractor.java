package io.rxmicro.examples.rest.client.params;

import io.rxmicro.rest.client.detail.QueryBuilder;
import io.rxmicro.rest.client.detail.RequestModelExtractor;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$VirtualSimpleUsageRequestRequestModelExtractor extends RequestModelExtractor<$$VirtualSimpleUsageRequest> {

    @Override
    public void extract(final $$VirtualSimpleUsageRequest model,
                        final QueryBuilder queryBuilder) {
        queryBuilder.add("endpoint_version", model.endpointVersion);
        queryBuilder.add("use-Proxy", model.useProxy);
    }
}
