package io.rxmicro.examples.rest.client.versioning.header;

import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;
import io.rxmicro.rest.client.detail.HeaderBuilder;
import io.rxmicro.rest.client.detail.HttpClient;
import io.rxmicro.rest.client.detail.HttpResponse;

import java.util.concurrent.CompletableFuture;

import static io.rxmicro.rest.client.detail.ErrorResponseCheckerHelper.throwExceptionIfNotSuccess;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$OldRestClient extends AbstractRestClient implements OldRestClient {

    private final HttpClient client;

    private final RestClientConfig config;

    public $$OldRestClient(final HttpClient client,
                           final RestClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletableFuture<Void> update() {
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        headerBuilder.add("Api-Version", "v1");
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("PATCH", "/patch", headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }
}
