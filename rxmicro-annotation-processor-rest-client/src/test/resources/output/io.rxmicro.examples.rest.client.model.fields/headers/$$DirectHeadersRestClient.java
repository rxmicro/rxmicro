package io.rxmicro.examples.rest.client.model.fields.headers;

import io.rxmicro.examples.rest.client.model.fields.headers.direct.$$RequestRequestModelExtractor;
import io.rxmicro.examples.rest.client.model.fields.headers.direct.$$ResponseModelReader;
import io.rxmicro.examples.rest.client.model.fields.headers.direct.Request;
import io.rxmicro.examples.rest.client.model.fields.headers.direct.Response;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.http.client.HttpClient;
import io.rxmicro.http.client.HttpClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;
import io.rxmicro.rest.client.detail.HeaderBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static io.rxmicro.rest.client.detail.ErrorResponseCheckerHelper.throwExceptionIfNotSuccess;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$DirectHeadersRestClient extends AbstractRestClient implements DirectHeadersRestClient {

    private final $$RequestRequestModelExtractor requestRequestModelExtractor =
            new $$RequestRequestModelExtractor();

    private final $$ResponseModelReader responseModelReader =
            new $$ResponseModelReader();

    private final HttpClient client;

    private final HttpClientConfig config;

    public $$DirectHeadersRestClient(final HttpClient client,
                                     final HttpClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletionStage<Response> put(final Request request) {
        final String path = "/headers/direct";
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        requestRequestModelExtractor.extract(request, headerBuilder);
        final CompletableFuture<ClientHttpResponse> response = client
                .sendAsync("PUT", path, headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> responseModelReader.readSingle(resp));
    }
}
