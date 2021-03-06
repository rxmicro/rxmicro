package io.rxmicro.examples.rest.client.headers;

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
public final class $$ComplexStaticHeadersRestClient extends AbstractRestClient implements ComplexStaticHeadersRestClient {

    private final HttpClient client;

    private final RestClientConfig config;

    public $$ComplexStaticHeadersRestClient(final HttpClient client,
                                            final RestClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletableFuture<Void> get1() {
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        headerBuilder.add("Parent-Header2", "new-Parent-Header2-value");
        headerBuilder.add("Parent-Header2", "new-Parent-Header2-value");
        headerBuilder.add("Parent-Header1", "new-Parent-Header1-value");
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", "/get1", headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletableFuture<Void> get2() {
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        headerBuilder.add("Parent-Header2", "new-Parent-Header2-value");
        headerBuilder.add("Parent-Header2", "new-Parent-Header2-value");
        headerBuilder.add("Parent-Header1", "new-Parent-Header1-value");
        headerBuilder.add("Child-Header2", "new-Child-Header2-value");
        headerBuilder.add("Child-Header2", "new-Child-Header2-value");
        headerBuilder.add("Child-Header1", "new-Child-Header1-value");
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", "/get2", headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletableFuture<Void> get3() {
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        headerBuilder.add("Parent-Header2", "new-Child-Parent-Header2-value");
        headerBuilder.add("Parent-Header1", "new-Child-Parent-Header1-value");
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", "/get3", headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletableFuture<Void> get4() {
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        headerBuilder.add("Parent-Header2", "new-Parent-Header2-value");
        headerBuilder.add("Parent-Header2", "new-Parent-Header2-value");
        headerBuilder.add("Parent-Header2", "new-Child-Parent-Header2-value");
        headerBuilder.add("Parent-Header1", "new-Parent-Header1-value");
        headerBuilder.add("Parent-Header1", "new-Child-Parent-Header1-value");
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", "/get4", headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }
}
