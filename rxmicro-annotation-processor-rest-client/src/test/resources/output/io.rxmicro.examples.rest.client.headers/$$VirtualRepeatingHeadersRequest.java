package io.rxmicro.examples.rest.client.headers;

import io.rxmicro.examples.rest.client.headers.model.Status;

import java.util.List;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualRepeatingHeadersRequest {

    // Headers
    final List<Status> singleHeader;

    final List<Status> repeatingHeader;

    $$VirtualRepeatingHeadersRequest(final List<Status> singleHeader, final List<Status> repeatingHeader) {
        this.singleHeader = singleHeader;
        this.repeatingHeader = repeatingHeader;
    }
}
