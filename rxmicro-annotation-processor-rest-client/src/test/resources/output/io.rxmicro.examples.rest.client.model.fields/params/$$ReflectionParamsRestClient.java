package io.rxmicro.examples.rest.client.model.fields.params;

import io.rxmicro.examples.rest.client.model.fields.params.reflection.$$BodyRequestModelToJsonConverter;
import io.rxmicro.examples.rest.client.model.fields.params.reflection.$$QueryRequestRequestModelExtractor;
import io.rxmicro.examples.rest.client.model.fields.params.reflection.$$ResponseModelReader;
import io.rxmicro.examples.rest.client.model.fields.params.reflection.BodyRequest;
import io.rxmicro.examples.rest.client.model.fields.params.reflection.QueryRequest;
import io.rxmicro.examples.rest.client.model.fields.params.reflection.Response;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.http.client.HttpClient;
import io.rxmicro.http.client.HttpClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;
import io.rxmicro.rest.client.detail.QueryBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static io.rxmicro.rest.client.detail.ErrorResponseCheckerHelper.throwExceptionIfNotSuccess;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$ReflectionParamsRestClient extends AbstractRestClient implements ReflectionParamsRestClient {

    private final $$ResponseModelReader responseModelReader =
            new $$ResponseModelReader();

    private final $$BodyRequestModelToJsonConverter bodyRequestModelToJsonConverter =
            new $$BodyRequestModelToJsonConverter();

    private final $$QueryRequestRequestModelExtractor queryRequestRequestModelExtractor =
            new $$QueryRequestRequestModelExtractor();

    private final HttpClient client;

    private final HttpClientConfig config;

    public $$ReflectionParamsRestClient(final HttpClient client,
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
        final String path = "/params/reflection";
        final Object body = bodyRequestModelToJsonConverter.toJson(request);
        final CompletableFuture<ClientHttpResponse> response = client
                .sendAsync("PUT", path, EMPTY_HEADERS, body)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> responseModelReader.readSingle(resp));
    }
}
