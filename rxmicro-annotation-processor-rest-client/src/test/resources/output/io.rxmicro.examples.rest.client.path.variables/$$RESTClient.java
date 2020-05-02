package io.rxmicro.examples.rest.client.path.variables;

import io.rxmicro.examples.rest.client.path.variables.model.$$RequestPathBuilder;
import io.rxmicro.examples.rest.client.path.variables.model.Request;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.http.client.HttpClient;
import io.rxmicro.http.client.HttpClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;

import java.util.concurrent.CompletableFuture;

import static io.rxmicro.rest.client.detail.ErrorResponseCheckerHelper.throwExceptionIfNotSuccess;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$RESTClient extends AbstractRestClient implements RESTClient {

    private final $$VirtualRESTRequestPathBuilder virtualRESTRequestPathBuilder =
            new $$VirtualRESTRequestPathBuilder();

    private final $$RequestPathBuilder requestPathBuilder =
            new $$RequestPathBuilder();

    private final HttpClient client;

    private final HttpClientConfig config;

    public $$RESTClient(final HttpClient client,
                        final HttpClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletableFuture<Void> consume(final Request request) {
        final String path = requestPathBuilder.build("/?/?-?", request);
        final CompletableFuture<ClientHttpResponse> response = client
                .sendAsync("GET", path, EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletableFuture<Void> consume(final String category, final String type, final String subType) {
        final $$VirtualRESTRequest virtualRequest = new $$VirtualRESTRequest(category, type, subType);
        final String path = virtualRESTRequestPathBuilder.build("/?/?-?", virtualRequest);
        final CompletableFuture<ClientHttpResponse> response = client
                .sendAsync("GET", path, EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }
}
