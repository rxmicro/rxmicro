package io.rxmicro.examples.rest.client.base.url.path;

import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;
import io.rxmicro.rest.client.detail.HttpClient;
import io.rxmicro.rest.client.detail.HttpResponse;

import java.util.concurrent.CompletableFuture;

import static io.rxmicro.rest.client.detail.ErrorResponseCheckerHelper.throwExceptionIfNotSuccess;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$RESTClient extends AbstractRestClient implements RESTClient {

    private final HttpClient client;

    private final RestClientConfig config;

    public $$RESTClient(final HttpClient client,
                        final RestClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletableFuture<Void> path() {
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", "/base-url-path/path", EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }
}
