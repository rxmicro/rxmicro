package io.rxmicro.examples.rest.client.params;

import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;
import io.rxmicro.rest.client.detail.HttpClient;
import io.rxmicro.rest.client.detail.HttpResponse;
import io.rxmicro.rest.client.detail.QueryBuilder;

import java.util.concurrent.CompletableFuture;

import static io.rxmicro.rest.client.detail.ErrorResponseCheckerHelper.throwExceptionIfNotSuccess;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ComplexStaticQueryParametersRestClient extends AbstractRestClient implements ComplexStaticQueryParametersRestClient {

    private final HttpClient client;

    private final RestClientConfig config;

    public $$ComplexStaticQueryParametersRestClient(final HttpClient client,
                                                    final RestClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletableFuture<Void> get1() {
        final QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.add("parent-param2", "new-parent-param2-value");
        queryBuilder.add("parent-param2", "new-parent-param2-value");
        queryBuilder.add("parent-param1", "new-parent-param1-value");
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", joinPath("/get1", queryBuilder.build()), EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletableFuture<Void> get2() {
        final QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.add("parent-param2", "new-parent-param2-value");
        queryBuilder.add("parent-param2", "new-parent-param2-value");
        queryBuilder.add("parent-param1", "new-parent-param1-value");
        queryBuilder.add("child-param2", "new-child-param2-value");
        queryBuilder.add("child-param2", "new-child-param2-value");
        queryBuilder.add("child-param1", "new-child-param1-value");
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", joinPath("/get2", queryBuilder.build()), EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletableFuture<Void> get3() {
        final QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.add("parent-param2", "new-child-parent-param2-value");
        queryBuilder.add("parent-param1", "new-child-parent-param1-value");
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", joinPath("/get3", queryBuilder.build()), EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletableFuture<Void> get4() {
        final QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.add("parent-param2", "new-parent-param2-value");
        queryBuilder.add("parent-param2", "new-parent-param2-value");
        queryBuilder.add("parent-param2", "new-child-parent-param2-value");
        queryBuilder.add("parent-param1", "new-parent-param1-value");
        queryBuilder.add("parent-param1", "new-child-parent-param1-value");
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", joinPath("/get4", queryBuilder.build()), EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }
}
