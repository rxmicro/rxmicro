package io.rxmicro.examples.rest.client.params;

import io.rxmicro.examples.rest.client.params.model.Status;

import java.util.List;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualRepeatingQueryParamsRequest {

    // Parameters
    final List<Status> singleHeader;

    final List<Status> repeatingHeader;

    $$VirtualRepeatingQueryParamsRequest(final List<Status> singleHeader, final List<Status> repeatingHeader) {
        this.singleHeader = singleHeader;
        this.repeatingHeader = repeatingHeader;
    }
}
