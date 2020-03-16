package io.rxmicro.examples.rest.client.model.fields.params;

import io.rxmicro.examples.rest.client.model.fields.params.direct.$$BodyRequestModelToJsonConverter;
import io.rxmicro.examples.rest.client.model.fields.params.direct.$$QueryRequestRequestModelExtractor;
import io.rxmicro.examples.rest.client.model.fields.params.direct.$$ResponseModelReader;
import io.rxmicro.examples.rest.client.model.fields.params.direct.BodyRequest;
import io.rxmicro.examples.rest.client.model.fields.params.direct.QueryRequest;
import io.rxmicro.examples.rest.client.model.fields.params.direct.Response;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.http.client.HttpClient;
import io.rxmicro.http.client.HttpClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;
import io.rxmicro.rest.client.detail.QueryBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static io.rxmicro.rest.client.detail.ErrorResponseChecker.throwExceptionIfNotSuccess;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$DirectParamsRestClient extends AbstractRestClient implements DirectParamsRestClient {

    private final $$ResponseModelReader responseModelReader =
            new $$ResponseModelReader();

    private final $$BodyRequestModelToJsonConverter bodyRequestModelToJsonConverter =
            new $$BodyRequestModelToJsonConverter();

    private final $$QueryRequestRequestModelExtractor queryRequestRequestModelExtractor =
            new $$QueryRequestRequestModelExtractor();

    private final HttpClient client;

    private final HttpClientConfig config;

    public $$DirectParamsRestClient(final HttpClient client,
                                    final HttpClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletionStage<Response> get(final QueryRequest request) {
        final String path = "/params/direct";
        final QueryBuilder queryBuilder = new QueryBuilder();
        queryRequestRequestModelExtractor.extract(request, queryBuilder);
        final CompletableFuture<ClientHttpResponse> response = client
                .sendAsync("GET", joinPath(path, queryBuilder.build()), EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> responseModelReader.readSingle(resp));
    }

    @Override
    public CompletionStage<Response> put(final BodyRequest request) {
        final String path = "/params/direct";
        final Object body = bodyRequestModelToJsonConverter.toJson(request);
        final CompletableFuture<ClientHttpResponse> response = client
                .sendAsync("PUT", path, EMPTY_HEADERS, body)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> responseModelReader.readSingle(resp));
    }
}
