package io.rxmicro.examples.rest.client.model.field.mapping.strategy;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualMappingStrategyRequest {

    // Headers
    final BigDecimal supportedApiVersionCode;

    // Parameters
    final Instant maxSupportedDateTime;

    $$VirtualMappingStrategyRequest(final BigDecimal supportedApiVersionCode, final Instant maxSupportedDateTime) {
        this.supportedApiVersionCode = supportedApiVersionCode;
        this.maxSupportedDateTime = maxSupportedDateTime;
    }
}
