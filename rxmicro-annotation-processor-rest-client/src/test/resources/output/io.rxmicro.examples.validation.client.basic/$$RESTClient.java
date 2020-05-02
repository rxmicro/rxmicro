package io.rxmicro.examples.validation.client.basic;

import io.rxmicro.examples.validation.client.basic.model.$$ResponseConstraintValidator;
import io.rxmicro.examples.validation.client.basic.model.$$ResponseModelReader;
import io.rxmicro.examples.validation.client.basic.model.Response;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.http.client.HttpClient;
import io.rxmicro.http.client.HttpClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;

import java.util.concurrent.CompletableFuture;

import static io.rxmicro.rest.client.detail.ErrorResponseCheckerHelper.throwExceptionIfNotSuccess;
import static io.rxmicro.validation.detail.ResponseValidators.validateIfResponseExists;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$RESTClient extends AbstractRestClient implements RESTClient {

    private final $$ResponseModelReader responseModelReader =
            new $$ResponseModelReader();

    private final $$ResponseConstraintValidator responseConstraintValidator =
            new $$ResponseConstraintValidator();

    private final HttpClient client;

    private final HttpClientConfig config;

    public $$RESTClient(final HttpClient client,
                        final HttpClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletableFuture<Response> get() {
        final CompletableFuture<ClientHttpResponse> response = client
                .sendAsync("GET", "/", EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> responseModelReader.readSingle(resp))
                .whenComplete((resp, th) -> validateIfResponseExists(responseConstraintValidator, resp));
    }
}
