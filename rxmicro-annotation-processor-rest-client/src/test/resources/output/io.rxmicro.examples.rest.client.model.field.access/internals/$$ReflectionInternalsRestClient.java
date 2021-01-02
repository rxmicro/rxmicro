package io.rxmicro.examples.rest.client.model.field.access.internals;

import io.rxmicro.examples.rest.client.model.field.access.internals.reflection.$$ResponseModelReader;
import io.rxmicro.examples.rest.client.model.field.access.internals.reflection.Response;
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
public final class $$ReflectionInternalsRestClient extends AbstractRestClient implements ReflectionInternalsRestClient {

    private final $$ResponseModelReader responseModelReader =
            new $$ResponseModelReader();

    private final HttpClient client;

    private final RestClientConfig config;

    public $$ReflectionInternalsRestClient(final HttpClient client,
                                           final RestClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletionStage<Response> put() {
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("PUT", "/internals/reflection", EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> responseModelReader.readSingle(resp));
    }
}
