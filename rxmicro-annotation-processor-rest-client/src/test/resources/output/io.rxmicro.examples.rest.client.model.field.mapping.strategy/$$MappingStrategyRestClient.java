package io.rxmicro.examples.rest.client.model.field.mapping.strategy;

import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;
import io.rxmicro.rest.client.detail.HeaderBuilder;
import io.rxmicro.rest.client.detail.HttpClient;
import io.rxmicro.rest.client.detail.HttpResponse;
import io.rxmicro.rest.client.detail.QueryBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static io.rxmicro.rest.client.detail.ErrorResponseCheckerHelper.throwExceptionIfNotSuccess;
import static io.rxmicro.validation.detail.RequestValidators.validateRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$MappingStrategyRestClient extends AbstractRestClient implements MappingStrategyRestClient {

    private final $$VirtualMappingStrategyRequestRequestModelExtractor virtualMappingStrategyRequestRequestModelExtractor =
            new $$VirtualMappingStrategyRequestRequestModelExtractor();

    private final $$VirtualMappingStrategyRequest2RequestModelExtractor virtualMappingStrategyRequest2RequestModelExtractor =
            new $$VirtualMappingStrategyRequest2RequestModelExtractor();

    private final $$VirtualMappingStrategyRequest3RequestModelExtractor virtualMappingStrategyRequest3RequestModelExtractor =
            new $$VirtualMappingStrategyRequest3RequestModelExtractor();

    private final $$VirtualMappingStrategyRequestConstraintValidator virtualMappingStrategyRequestConstraintValidator =
            new $$VirtualMappingStrategyRequestConstraintValidator();

    private final $$VirtualMappingStrategyRequest3ConstraintValidator virtualMappingStrategyRequest3ConstraintValidator =
            new $$VirtualMappingStrategyRequest3ConstraintValidator();

    private final $$VirtualMappingStrategyRequest2ConstraintValidator virtualMappingStrategyRequest2ConstraintValidator =
            new $$VirtualMappingStrategyRequest2ConstraintValidator();

    private final HttpClient client;

    private final RestClientConfig config;

    public $$MappingStrategyRestClient(final HttpClient client,
                                       final RestClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletionStage<Void> consume1(final BigDecimal supportedApiVersionCode, final Instant maxSupportedDateTime) {
        final $$VirtualMappingStrategyRequest virtualRequest = new $$VirtualMappingStrategyRequest(supportedApiVersionCode, maxSupportedDateTime);
        validateRequest(config.isEnableAdditionalValidations(), virtualMappingStrategyRequestConstraintValidator, virtualRequest);
        final String path = "/consume1";
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        final QueryBuilder queryBuilder = new QueryBuilder();
        virtualMappingStrategyRequestRequestModelExtractor.extract(virtualRequest, headerBuilder, queryBuilder);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("PUT", joinPath(path, queryBuilder.build()), headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletionStage<Void> consume2(final BigDecimal supportedApiVersionCode, final Instant maxSupportedDateTime) {
        final $$VirtualMappingStrategyRequest2 virtualRequest = new $$VirtualMappingStrategyRequest2(supportedApiVersionCode, maxSupportedDateTime);
        validateRequest(config.isEnableAdditionalValidations(), virtualMappingStrategyRequest2ConstraintValidator, virtualRequest);
        final String path = "/consume2";
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        final QueryBuilder queryBuilder = new QueryBuilder();
        virtualMappingStrategyRequest2RequestModelExtractor.extract(virtualRequest, headerBuilder, queryBuilder);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("PUT", joinPath(path, queryBuilder.build()), headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletionStage<Void> consume3(final BigDecimal supportedApiVersionCode, final Instant maxSupportedDateTime) {
        final $$VirtualMappingStrategyRequest3 virtualRequest = new $$VirtualMappingStrategyRequest3(supportedApiVersionCode, maxSupportedDateTime);
        validateRequest(config.isEnableAdditionalValidations(), virtualMappingStrategyRequest3ConstraintValidator, virtualRequest);
        final String path = "/consume3";
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        final QueryBuilder queryBuilder = new QueryBuilder();
        virtualMappingStrategyRequest3RequestModelExtractor.extract(virtualRequest, headerBuilder, queryBuilder);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("PUT", joinPath(path, queryBuilder.build()), headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }
}
