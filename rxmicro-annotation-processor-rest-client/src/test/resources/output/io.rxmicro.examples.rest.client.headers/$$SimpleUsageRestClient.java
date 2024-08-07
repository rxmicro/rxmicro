package io.rxmicro.examples.rest.client.headers;

import io.rxmicro.examples.rest.client.headers.model.$$RequestConstraintValidator;
import io.rxmicro.examples.rest.client.headers.model.$$RequestRequestModelExtractor;
import io.rxmicro.examples.rest.client.headers.model.$$ResponseClientModelReader;
import io.rxmicro.examples.rest.client.headers.model.$$ResponseConstraintValidator;
import io.rxmicro.examples.rest.client.headers.model.Request;
import io.rxmicro.examples.rest.client.headers.model.Response;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;
import io.rxmicro.rest.client.detail.HeaderBuilder;
import io.rxmicro.rest.client.detail.HttpClient;
import io.rxmicro.rest.client.detail.HttpResponse;

import java.util.concurrent.CompletableFuture;

import static io.rxmicro.rest.client.detail.ErrorResponseCheckerHelper.throwExceptionIfNotSuccess;
import static io.rxmicro.validation.detail.RequestValidators.validateRequest;
import static io.rxmicro.validation.detail.ResponseValidators.validateIfResponseExists;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$SimpleUsageRestClient extends AbstractRestClient implements SimpleUsageRestClient {

    private final $$VirtualSimpleUsageRequestRequestModelExtractor virtualSimpleUsageRequestRequestModelExtractor =
            new $$VirtualSimpleUsageRequestRequestModelExtractor();

    private final $$ResponseClientModelReader responseClientModelReader =
            new $$ResponseClientModelReader();

    private final $$RequestRequestModelExtractor requestRequestModelExtractor =
            new $$RequestRequestModelExtractor();

    private final $$VirtualSimpleUsageRequestConstraintValidator virtualSimpleUsageRequestConstraintValidator =
            new $$VirtualSimpleUsageRequestConstraintValidator();

    private final $$RequestConstraintValidator requestConstraintValidator =
            new $$RequestConstraintValidator();

    private final $$ResponseConstraintValidator responseConstraintValidator =
            new $$ResponseConstraintValidator();

    private final HttpClient client;

    private final RestClientConfig config;

    public $$SimpleUsageRestClient(final HttpClient client,
                                   final RestClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletableFuture<Response> get1(final Request request) {
        validateRequest(config.isEnableAdditionalValidations(), requestConstraintValidator, request);
        final String path = "/get1";
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        requestRequestModelExtractor.extract(request, headerBuilder);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", path, headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> responseClientModelReader.readSingle(resp))
                .whenComplete((resp, th) -> validateIfResponseExists(responseConstraintValidator, resp));
    }

    @Override
    public CompletableFuture<Response> get2(final String endpointVersion, final Boolean useProxy) {
        final $$VirtualSimpleUsageRequest virtualRequest = new $$VirtualSimpleUsageRequest(endpointVersion, useProxy);
        validateRequest(config.isEnableAdditionalValidations(), virtualSimpleUsageRequestConstraintValidator, virtualRequest);
        final String path = "/get2";
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        virtualSimpleUsageRequestRequestModelExtractor.extract(virtualRequest, headerBuilder);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", path, headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> responseClientModelReader.readSingle(resp))
                .whenComplete((resp, th) -> validateIfResponseExists(responseConstraintValidator, resp));
    }
}
