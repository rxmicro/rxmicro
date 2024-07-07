package io.rxmicro.examples.rest.client.headers;

import io.rxmicro.rest.client.detail.HeaderBuilder;
import io.rxmicro.rest.client.detail.RequestModelExtractor;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualSimpleUsageRequestRequestModelExtractor extends RequestModelExtractor<$$VirtualSimpleUsageRequest> {

    @Override
    public void extract(final $$VirtualSimpleUsageRequest model,
                        final HeaderBuilder headerBuilder) {
        headerBuilder.add("Endpoint-Version", model.endpointVersion);
        headerBuilder.add("UseProxy", model.useProxy);
    }
}
