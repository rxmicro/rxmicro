package io.rxmicro.examples.rest.client.model.types;

import io.rxmicro.examples.rest.client.model.types.model.request.http_body.$$HttpBodyRequestConstraintValidator;
import io.rxmicro.examples.rest.client.model.types.model.request.http_body.$$HttpBodyRequestModelToJsonConverter;
import io.rxmicro.examples.rest.client.model.types.model.request.http_body.$$HttpBodyWithHeadersRequestConstraintValidator;
import io.rxmicro.examples.rest.client.model.types.model.request.http_body.$$HttpBodyWithHeadersRequestModelToJsonConverter;
import io.rxmicro.examples.rest.client.model.types.model.request.http_body.$$HttpBodyWithHeadersRequestRequestModelExtractor;
import io.rxmicro.examples.rest.client.model.types.model.request.http_body.$$HttpBodyWithPathVarAndHeadersRequestConstraintValidator;
import io.rxmicro.examples.rest.client.model.types.model.request.http_body.$$HttpBodyWithPathVarAndHeadersRequestModelToJsonConverter;
import io.rxmicro.examples.rest.client.model.types.model.request.http_body.$$HttpBodyWithPathVarAndHeadersRequestPathBuilder;
import io.rxmicro.examples.rest.client.model.types.model.request.http_body.$$HttpBodyWithPathVarAndHeadersRequestRequestModelExtractor;
import io.rxmicro.examples.rest.client.model.types.model.request.http_body.$$HttpBodyWithPathVarRequestConstraintValidator;
import io.rxmicro.examples.rest.client.model.types.model.request.http_body.$$HttpBodyWithPathVarRequestModelToJsonConverter;
import io.rxmicro.examples.rest.client.model.types.model.request.http_body.$$HttpBodyWithPathVarRequestPathBuilder;
import io.rxmicro.examples.rest.client.model.types.model.request.http_body.HttpBodyRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.http_body.HttpBodyWithHeadersRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.http_body.HttpBodyWithPathVarAndHeadersRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.http_body.HttpBodyWithPathVarRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.query_string.$$QueryStringRequestConstraintValidator;
import io.rxmicro.examples.rest.client.model.types.model.request.query_string.$$QueryStringRequestRequestModelExtractor;
import io.rxmicro.examples.rest.client.model.types.model.request.query_string.$$QueryStringWithHeadersRequestConstraintValidator;
import io.rxmicro.examples.rest.client.model.types.model.request.query_string.$$QueryStringWithHeadersRequestRequestModelExtractor;
import io.rxmicro.examples.rest.client.model.types.model.request.query_string.$$QueryStringWithPathVarAndHeadersRequestConstraintValidator;
import io.rxmicro.examples.rest.client.model.types.model.request.query_string.$$QueryStringWithPathVarAndHeadersRequestPathBuilder;
import io.rxmicro.examples.rest.client.model.types.model.request.query_string.$$QueryStringWithPathVarAndHeadersRequestRequestModelExtractor;
import io.rxmicro.examples.rest.client.model.types.model.request.query_string.$$QueryStringWithPathVarRequestConstraintValidator;
import io.rxmicro.examples.rest.client.model.types.model.request.query_string.$$QueryStringWithPathVarRequestPathBuilder;
import io.rxmicro.examples.rest.client.model.types.model.request.query_string.$$QueryStringWithPathVarRequestRequestModelExtractor;
import io.rxmicro.examples.rest.client.model.types.model.request.query_string.QueryStringRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.query_string.QueryStringWithHeadersRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.query_string.QueryStringWithPathVarAndHeadersRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.query_string.QueryStringWithPathVarRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.without_any_fields.WithoutAnyFieldsRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.without_body.$$HeadersOnlyRequestConstraintValidator;
import io.rxmicro.examples.rest.client.model.types.model.request.without_body.$$HeadersOnlyRequestRequestModelExtractor;
import io.rxmicro.examples.rest.client.model.types.model.request.without_body.$$PathVarAndHeadersRequestConstraintValidator;
import io.rxmicro.examples.rest.client.model.types.model.request.without_body.$$PathVarAndHeadersRequestPathBuilder;
import io.rxmicro.examples.rest.client.model.types.model.request.without_body.$$PathVarAndHeadersRequestRequestModelExtractor;
import io.rxmicro.examples.rest.client.model.types.model.request.without_body.$$PathVarOnlyRequestConstraintValidator;
import io.rxmicro.examples.rest.client.model.types.model.request.without_body.$$PathVarOnlyRequestPathBuilder;
import io.rxmicro.examples.rest.client.model.types.model.request.without_body.HeadersOnlyRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.without_body.PathVarAndHeadersRequest;
import io.rxmicro.examples.rest.client.model.types.model.request.without_body.PathVarOnlyRequest;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;
import io.rxmicro.rest.client.detail.HeaderBuilder;
import io.rxmicro.rest.client.detail.HttpClient;
import io.rxmicro.rest.client.detail.HttpResponse;
import io.rxmicro.rest.client.detail.QueryBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static io.rxmicro.rest.client.detail.ErrorResponseCheckerHelper.throwExceptionIfNotSuccess;
import static io.rxmicro.validation.detail.RequestValidators.validateRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$SenderRestClient extends AbstractRestClient implements SenderRestClient {

    private final $$QueryStringWithHeadersRequestRequestModelExtractor queryStringWithHeadersRequestRequestModelExtractor =
            new $$QueryStringWithHeadersRequestRequestModelExtractor();

    private final $$HttpBodyWithPathVarAndHeadersRequestRequestModelExtractor httpBodyWithPathVarAndHeadersRequestRequestModelExtractor =
            new $$HttpBodyWithPathVarAndHeadersRequestRequestModelExtractor();

    private final $$HttpBodyWithHeadersRequestModelToJsonConverter httpBodyWithHeadersRequestModelToJsonConverter =
            new $$HttpBodyWithHeadersRequestModelToJsonConverter();

    private final $$QueryStringWithPathVarRequestRequestModelExtractor queryStringWithPathVarRequestRequestModelExtractor =
            new $$QueryStringWithPathVarRequestRequestModelExtractor();

    private final $$HttpBodyRequestModelToJsonConverter httpBodyRequestModelToJsonConverter =
            new $$HttpBodyRequestModelToJsonConverter();

    private final $$HttpBodyWithHeadersRequestRequestModelExtractor httpBodyWithHeadersRequestRequestModelExtractor =
            new $$HttpBodyWithHeadersRequestRequestModelExtractor();

    private final $$HeadersOnlyRequestRequestModelExtractor headersOnlyRequestRequestModelExtractor =
            new $$HeadersOnlyRequestRequestModelExtractor();

    private final $$PathVarOnlyRequestPathBuilder pathVarOnlyRequestPathBuilder =
            new $$PathVarOnlyRequestPathBuilder();

    private final $$QueryStringWithPathVarRequestPathBuilder queryStringWithPathVarRequestPathBuilder =
            new $$QueryStringWithPathVarRequestPathBuilder();

    private final $$QueryStringWithPathVarAndHeadersRequestRequestModelExtractor queryStringWithPathVarAndHeadersRequestRequestModelExtractor =
            new $$QueryStringWithPathVarAndHeadersRequestRequestModelExtractor();

    private final $$HttpBodyWithPathVarAndHeadersRequestPathBuilder httpBodyWithPathVarAndHeadersRequestPathBuilder =
            new $$HttpBodyWithPathVarAndHeadersRequestPathBuilder();

    private final $$QueryStringWithPathVarAndHeadersRequestPathBuilder queryStringWithPathVarAndHeadersRequestPathBuilder =
            new $$QueryStringWithPathVarAndHeadersRequestPathBuilder();

    private final $$HttpBodyWithPathVarRequestPathBuilder httpBodyWithPathVarRequestPathBuilder =
            new $$HttpBodyWithPathVarRequestPathBuilder();

    private final $$HttpBodyWithPathVarRequestModelToJsonConverter httpBodyWithPathVarRequestModelToJsonConverter =
            new $$HttpBodyWithPathVarRequestModelToJsonConverter();

    private final $$QueryStringRequestRequestModelExtractor queryStringRequestRequestModelExtractor =
            new $$QueryStringRequestRequestModelExtractor();

    private final $$HttpBodyWithPathVarAndHeadersRequestModelToJsonConverter httpBodyWithPathVarAndHeadersRequestModelToJsonConverter =
            new $$HttpBodyWithPathVarAndHeadersRequestModelToJsonConverter();

    private final $$PathVarAndHeadersRequestPathBuilder pathVarAndHeadersRequestPathBuilder =
            new $$PathVarAndHeadersRequestPathBuilder();

    private final $$PathVarAndHeadersRequestRequestModelExtractor pathVarAndHeadersRequestRequestModelExtractor =
            new $$PathVarAndHeadersRequestRequestModelExtractor();

    private final $$QueryStringWithHeadersRequestConstraintValidator queryStringWithHeadersRequestConstraintValidator =
            new $$QueryStringWithHeadersRequestConstraintValidator();

    private final $$HttpBodyRequestConstraintValidator httpBodyRequestConstraintValidator =
            new $$HttpBodyRequestConstraintValidator();

    private final $$QueryStringRequestConstraintValidator queryStringRequestConstraintValidator =
            new $$QueryStringRequestConstraintValidator();

    private final $$HttpBodyWithPathVarAndHeadersRequestConstraintValidator httpBodyWithPathVarAndHeadersRequestConstraintValidator =
            new $$HttpBodyWithPathVarAndHeadersRequestConstraintValidator();

    private final $$HttpBodyWithHeadersRequestConstraintValidator httpBodyWithHeadersRequestConstraintValidator =
            new $$HttpBodyWithHeadersRequestConstraintValidator();

    private final $$HttpBodyWithPathVarRequestConstraintValidator httpBodyWithPathVarRequestConstraintValidator =
            new $$HttpBodyWithPathVarRequestConstraintValidator();

    private final $$HeadersOnlyRequestConstraintValidator headersOnlyRequestConstraintValidator =
            new $$HeadersOnlyRequestConstraintValidator();

    private final $$PathVarOnlyRequestConstraintValidator pathVarOnlyRequestConstraintValidator =
            new $$PathVarOnlyRequestConstraintValidator();

    private final $$QueryStringWithPathVarRequestConstraintValidator queryStringWithPathVarRequestConstraintValidator =
            new $$QueryStringWithPathVarRequestConstraintValidator();

    private final $$PathVarAndHeadersRequestConstraintValidator pathVarAndHeadersRequestConstraintValidator =
            new $$PathVarAndHeadersRequestConstraintValidator();

    private final $$QueryStringWithPathVarAndHeadersRequestConstraintValidator queryStringWithPathVarAndHeadersRequestConstraintValidator =
            new $$QueryStringWithPathVarAndHeadersRequestConstraintValidator();

    private final HttpClient client;

    private final RestClientConfig config;

    public $$SenderRestClient(final HttpClient client,
                              final RestClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletionStage<Void> consume(final HttpBodyRequest request) {
        validateRequest(config.isEnableAdditionalValidations(), httpBodyRequestConstraintValidator, request);
        final String path = "/consume01";
        final Object body = httpBodyRequestModelToJsonConverter.toJson(request);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("POST", path, EMPTY_HEADERS, body)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletionStage<Void> consume(final HttpBodyWithHeadersRequest request) {
        validateRequest(config.isEnableAdditionalValidations(), httpBodyWithHeadersRequestConstraintValidator, request);
        final String path = "/consume02";
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        httpBodyWithHeadersRequestRequestModelExtractor.extract(request, headerBuilder);
        final Object body = httpBodyWithHeadersRequestModelToJsonConverter.toJson(request);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("POST", path, headerBuilder.build(), body)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletionStage<Void> consume(final HttpBodyWithPathVarRequest request) {
        validateRequest(config.isEnableAdditionalValidations(), httpBodyWithPathVarRequestConstraintValidator, request);
        final String path = httpBodyWithPathVarRequestPathBuilder.build("/consume03/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}", "/consume03/?/?/?/?/?/?/?/?/?/?/?/?/?/?", request);
        final Object body = httpBodyWithPathVarRequestModelToJsonConverter.toJson(request);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("POST", path, EMPTY_HEADERS, body)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletionStage<Void> consume(final HttpBodyWithPathVarAndHeadersRequest request) {
        validateRequest(config.isEnableAdditionalValidations(), httpBodyWithPathVarAndHeadersRequestConstraintValidator, request);
        final String path = httpBodyWithPathVarAndHeadersRequestPathBuilder.build("/consume06/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}", "/consume06/?/?/?/?/?/?/?/?/?/?/?/?/?/?", request);
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        httpBodyWithPathVarAndHeadersRequestRequestModelExtractor.extract(request, headerBuilder);
        final Object body = httpBodyWithPathVarAndHeadersRequestModelToJsonConverter.toJson(request);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("POST", path, headerBuilder.build(), body)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletionStage<Void> get(final PathVarOnlyRequest request) {
        validateRequest(config.isEnableAdditionalValidations(), pathVarOnlyRequestConstraintValidator, request);
        final String path = pathVarOnlyRequestPathBuilder.build("/consume12/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}", "/consume12/?/?/?/?/?/?/?/?/?/?/?/?/?/?", request);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", path, EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletionStage<Void> post(final PathVarOnlyRequest request) {
        validateRequest(config.isEnableAdditionalValidations(), pathVarOnlyRequestConstraintValidator, request);
        final String path = pathVarOnlyRequestPathBuilder.build("/consume12/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}", "/consume12/?/?/?/?/?/?/?/?/?/?/?/?/?/?", request);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("POST", path, EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletionStage<Void> get(final HeadersOnlyRequest request) {
        validateRequest(config.isEnableAdditionalValidations(), headersOnlyRequestConstraintValidator, request);
        final String path = "/consume13";
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        headersOnlyRequestRequestModelExtractor.extract(request, headerBuilder);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", path, headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletionStage<Void> post(final HeadersOnlyRequest request) {
        validateRequest(config.isEnableAdditionalValidations(), headersOnlyRequestConstraintValidator, request);
        final String path = "/consume13";
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        headersOnlyRequestRequestModelExtractor.extract(request, headerBuilder);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("POST", path, headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletionStage<Void> get(final PathVarAndHeadersRequest request) {
        validateRequest(config.isEnableAdditionalValidations(), pathVarAndHeadersRequestConstraintValidator, request);
        final String path = pathVarAndHeadersRequestPathBuilder.build("/consume16/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}", "/consume16/?/?/?/?/?/?/?/?/?/?/?/?/?/?", request);
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        pathVarAndHeadersRequestRequestModelExtractor.extract(request, headerBuilder);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", path, headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletionStage<Void> post(final PathVarAndHeadersRequest request) {
        validateRequest(config.isEnableAdditionalValidations(), pathVarAndHeadersRequestConstraintValidator, request);
        final String path = pathVarAndHeadersRequestPathBuilder.build("/consume16/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}", "/consume16/?/?/?/?/?/?/?/?/?/?/?/?/?/?", request);
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        pathVarAndHeadersRequestRequestModelExtractor.extract(request, headerBuilder);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("POST", path, headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletionStage<Void> consume(final QueryStringRequest request) {
        validateRequest(config.isEnableAdditionalValidations(), queryStringRequestConstraintValidator, request);
        final String path = "/consume41";
        final QueryBuilder queryBuilder = new QueryBuilder();
        queryStringRequestRequestModelExtractor.extract(request, queryBuilder);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", joinPath(path, queryBuilder.build()), EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletionStage<Void> consume(final QueryStringWithHeadersRequest request) {
        validateRequest(config.isEnableAdditionalValidations(), queryStringWithHeadersRequestConstraintValidator, request);
        final String path = "/consume42";
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        final QueryBuilder queryBuilder = new QueryBuilder();
        queryStringWithHeadersRequestRequestModelExtractor.extract(request, headerBuilder, queryBuilder);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", joinPath(path, queryBuilder.build()), headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletionStage<Void> consume(final QueryStringWithPathVarRequest request) {
        validateRequest(config.isEnableAdditionalValidations(), queryStringWithPathVarRequestConstraintValidator, request);
        final String path = queryStringWithPathVarRequestPathBuilder.build("/consume43/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}", "/consume43/?/?/?/?/?/?/?/?/?/?/?/?/?/?", request);
        final QueryBuilder queryBuilder = new QueryBuilder();
        queryStringWithPathVarRequestRequestModelExtractor.extract(request, queryBuilder);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", joinPath(path, queryBuilder.build()), EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletionStage<Void> consume(final QueryStringWithPathVarAndHeadersRequest request) {
        validateRequest(config.isEnableAdditionalValidations(), queryStringWithPathVarAndHeadersRequestConstraintValidator, request);
        final String path = queryStringWithPathVarAndHeadersRequestPathBuilder.build("/consume46/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}", "/consume46/?/?/?/?/?/?/?/?/?/?/?/?/?/?", request);
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        final QueryBuilder queryBuilder = new QueryBuilder();
        queryStringWithPathVarAndHeadersRequestRequestModelExtractor.extract(request, headerBuilder, queryBuilder);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", joinPath(path, queryBuilder.build()), headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletionStage<Void> get(final WithoutAnyFieldsRequest request) {
        final String path = "/consume51";
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", path, EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletionStage<Void> post(final WithoutAnyFieldsRequest request) {
        final String path = "/consume51";
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("POST", path, EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }
}
