package io.rxmicro.examples.rest.client.headers;

import io.rxmicro.examples.rest.client.headers.model.Status;
import io.rxmicro.rest.client.detail.HeaderBuilder;
import io.rxmicro.rest.client.detail.RequestModelExtractor;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualRepeatingHeadersRequestRequestModelExtractor extends RequestModelExtractor<$$VirtualRepeatingHeadersRequest> {

    @Override
    public void extract(final $$VirtualRepeatingHeadersRequest model,
                        final HeaderBuilder headerBuilder) {
        headerBuilder.add("Single-Header", model.singleHeader);
        for (final Status item : model.repeatingHeader) {
            headerBuilder.add("Repeating-Header", item);
        }
    }
}
