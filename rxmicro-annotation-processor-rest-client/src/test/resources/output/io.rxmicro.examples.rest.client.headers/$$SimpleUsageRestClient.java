package io.rxmicro.examples.rest.client.headers;

import io.rxmicro.examples.rest.client.headers.model.$$RequestRequestModelExtractor;
import io.rxmicro.examples.rest.client.headers.model.$$ResponseModelReader;
import io.rxmicro.examples.rest.client.headers.model.Request;
import io.rxmicro.examples.rest.client.headers.model.Response;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.http.client.HttpClient;
import io.rxmicro.http.client.HttpClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;
import io.rxmicro.rest.client.detail.HeaderBuilder;

import java.util.concurrent.CompletableFuture;

import static io.rxmicro.rest.client.detail.ErrorResponseCheckerHelper.throwExceptionIfNotSuccess;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$SimpleUsageRestClient extends AbstractRestClient implements SimpleUsageRestClient {

    private final $$VirtualSimpleUsageRequestRequestModelExtractor virtualSimpleUsageRequestRequestModelExtractor =
            new $$VirtualSimpleUsageRequestRequestModelExtractor();

    private final $$ResponseModelReader responseModelReader =
            new $$ResponseModelReader();

    private final $$RequestRequestModelExtractor requestRequestModelExtractor =
            new $$RequestRequestModelExtractor();

    private final HttpClient client;

    private final HttpClientConfig config;

    public $$SimpleUsageRestClient(final HttpClient client,
                                   final HttpClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletableFuture<Response> get1(final Request request) {
        final String path = "/get1";
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        requestRequestModelExtractor.extract(request, headerBuilder);
        final CompletableFuture<ClientHttpResponse> response = client
                .sendAsync("GET", path, headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> responseModelReader.readSingle(resp));
    }

    @Override
    public CompletableFuture<Response> get2(final String endpointVersion, final Boolean useProxy) {
        final $$VirtualSimpleUsageRequest virtualRequest = new $$VirtualSimpleUsageRequest(endpointVersion, useProxy);
        final String path = "/get2";
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        virtualSimpleUsageRequestRequestModelExtractor.extract(virtualRequest, headerBuilder);
        final CompletableFuture<ClientHttpResponse> response = client
                .sendAsync("GET", path, headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> responseModelReader.readSingle(resp));
    }
}
