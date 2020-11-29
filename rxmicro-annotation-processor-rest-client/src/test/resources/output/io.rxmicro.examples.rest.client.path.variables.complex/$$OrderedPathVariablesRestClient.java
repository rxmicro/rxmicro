package io.rxmicro.examples.rest.client.path.variables.complex;

import io.rxmicro.examples.rest.client.path.variables.complex.model.$$Request1PathBuilder;
import io.rxmicro.examples.rest.client.path.variables.complex.model.$$Request2PathBuilder;
import io.rxmicro.examples.rest.client.path.variables.complex.model.Request1;
import io.rxmicro.examples.rest.client.path.variables.complex.model.Request2;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.http.client.HttpClient;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;

import java.util.concurrent.CompletableFuture;

import static io.rxmicro.rest.client.detail.ErrorResponseCheckerHelper.throwExceptionIfNotSuccess;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$OrderedPathVariablesRestClient extends AbstractRestClient implements OrderedPathVariablesRestClient {

    private final $$Request1PathBuilder request1PathBuilder =
            new $$Request1PathBuilder();

    private final $$VirtualOrderedPathVariablesRequestPathBuilder virtualOrderedPathVariablesRequestPathBuilder =
            new $$VirtualOrderedPathVariablesRequestPathBuilder();

    private final $$VirtualOrderedPathVariablesRequest2PathBuilder virtualOrderedPathVariablesRequest2PathBuilder =
            new $$VirtualOrderedPathVariablesRequest2PathBuilder();

    private final $$Request2PathBuilder request2PathBuilder =
            new $$Request2PathBuilder();

    private final HttpClient client;

    private final RestClientConfig config;

    public $$OrderedPathVariablesRestClient(final HttpClient client,
                                            final RestClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletableFuture<Void> consumeAB(final String a, final String b) {
        final $$VirtualOrderedPathVariablesRequest virtualRequest = new $$VirtualOrderedPathVariablesRequest(a, b);
        final String path = virtualOrderedPathVariablesRequestPathBuilder.build("/${a}/${b}", "/?/?", virtualRequest);
        final CompletableFuture<ClientHttpResponse> response = client
                .sendAsync("GET", path, EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletableFuture<Void> consumeBA(final String a, final String b) {
        final $$VirtualOrderedPathVariablesRequest2 virtualRequest = new $$VirtualOrderedPathVariablesRequest2(a, b);
        final String path = virtualOrderedPathVariablesRequest2PathBuilder.build("/${b}/${a}", "/?/?", virtualRequest);
        final CompletableFuture<ClientHttpResponse> response = client
                .sendAsync("GET", path, EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletableFuture<Void> consumeAB(final Request1 request1) {
        final String path = request1PathBuilder.build("/${a}/${b}", "/?/?", request1);
        final CompletableFuture<ClientHttpResponse> response = client
                .sendAsync("GET", path, EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletableFuture<Void> consumeBA(final Request1 request1) {
        final String path = request1PathBuilder.build("/${b}/${a}", "/?/?", request1);
        final CompletableFuture<ClientHttpResponse> response = client
                .sendAsync("GET", path, EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletableFuture<Void> getAB(final Request2 request2) {
        final String path = request2PathBuilder.build("/${a}/${b}", "/?/?", request2);
        final CompletableFuture<ClientHttpResponse> response = client
                .sendAsync("GET", path, EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletableFuture<Void> putAB(final Request2 request2) {
        final String path = request2PathBuilder.build("/${a}/${b}", "/?/?", request2);
        final CompletableFuture<ClientHttpResponse> response = client
                .sendAsync("PUT", path, EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletableFuture<Void> postAB(final Request2 request2) {
        final String path = request2PathBuilder.build("/${a}/${b}", "/?/?", request2);
        final CompletableFuture<ClientHttpResponse> response = client
                .sendAsync("POST", path, EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }
}