package io.rxmicro.examples.rest.client.model.field.access.internals;

import io.rxmicro.examples.rest.client.model.field.access.internals.direct.$$ResponseModelReader;
import io.rxmicro.examples.rest.client.model.field.access.internals.direct.Response;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;
import io.rxmicro.rest.client.detail.HttpClient;
import io.rxmicro.rest.client.detail.HttpResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static io.rxmicro.rest.client.detail.ErrorResponseCheckerHelper.throwExceptionIfNotSuccess;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$DirectInternalsRestClient extends AbstractRestClient implements DirectInternalsRestClient {

    private final $$ResponseModelReader responseModelReader =
            new $$ResponseModelReader();

    private final HttpClient client;

    private final RestClientConfig config;

    public $$DirectInternalsRestClient(final HttpClient client,
                                       final RestClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletionStage<Response> put() {
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("PUT", "/internals/direct", EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> responseModelReader.readSingle(resp));
    }
}
