package io.rxmicro.examples.rest.client.model.types;

import io.rxmicro.examples.rest.client.model.types.model.response.body.$$BodyOnlyResponseClientModelReader;
import io.rxmicro.examples.rest.client.model.types.model.response.body.$$BodyOnlyResponseConstraintValidator;
import io.rxmicro.examples.rest.client.model.types.model.response.body.$$BodyWithHeadersResponseClientModelReader;
import io.rxmicro.examples.rest.client.model.types.model.response.body.$$BodyWithHeadersResponseConstraintValidator;
import io.rxmicro.examples.rest.client.model.types.model.response.body.$$BodyWithInternalsAndHeadersResponseClientModelReader;
import io.rxmicro.examples.rest.client.model.types.model.response.body.$$BodyWithInternalsAndHeadersResponseConstraintValidator;
import io.rxmicro.examples.rest.client.model.types.model.response.body.$$BodyWithInternalsResponseClientModelReader;
import io.rxmicro.examples.rest.client.model.types.model.response.body.$$BodyWithInternalsResponseConstraintValidator;
import io.rxmicro.examples.rest.client.model.types.model.response.body.BodyOnlyResponse;
import io.rxmicro.examples.rest.client.model.types.model.response.body.BodyWithHeadersResponse;
import io.rxmicro.examples.rest.client.model.types.model.response.body.BodyWithInternalsAndHeadersResponse;
import io.rxmicro.examples.rest.client.model.types.model.response.body.BodyWithInternalsResponse;
import io.rxmicro.examples.rest.client.model.types.model.response.without_body.$$HeadersOnlyResponseClientModelReader;
import io.rxmicro.examples.rest.client.model.types.model.response.without_body.$$HeadersOnlyResponseConstraintValidator;
import io.rxmicro.examples.rest.client.model.types.model.response.without_body.$$InternalsAndHeadersResponseClientModelReader;
import io.rxmicro.examples.rest.client.model.types.model.response.without_body.$$InternalsAndHeadersResponseConstraintValidator;
import io.rxmicro.examples.rest.client.model.types.model.response.without_body.$$InternalsOnlyResponseClientModelReader;
import io.rxmicro.examples.rest.client.model.types.model.response.without_body.HeadersOnlyResponse;
import io.rxmicro.examples.rest.client.model.types.model.response.without_body.InternalsAndHeadersResponse;
import io.rxmicro.examples.rest.client.model.types.model.response.without_body.InternalsOnlyResponse;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;
import io.rxmicro.rest.client.detail.HttpClient;
import io.rxmicro.rest.client.detail.HttpResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static io.rxmicro.rest.client.detail.ErrorResponseCheckerHelper.throwExceptionIfNotSuccess;
import static io.rxmicro.validation.detail.ResponseValidators.validateIfResponseExists;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$GetterRestClient extends AbstractRestClient implements GetterRestClient {

    private final $$BodyOnlyResponseClientModelReader bodyOnlyResponseClientModelReader =
            new $$BodyOnlyResponseClientModelReader();

    private final $$BodyWithInternalsResponseClientModelReader bodyWithInternalsResponseClientModelReader =
            new $$BodyWithInternalsResponseClientModelReader();

    private final $$BodyWithHeadersResponseClientModelReader bodyWithHeadersResponseClientModelReader =
            new $$BodyWithHeadersResponseClientModelReader();

    private final $$InternalsAndHeadersResponseClientModelReader internalsAndHeadersResponseClientModelReader =
            new $$InternalsAndHeadersResponseClientModelReader();

    private final $$BodyWithInternalsAndHeadersResponseClientModelReader bodyWithInternalsAndHeadersResponseClientModelReader =
            new $$BodyWithInternalsAndHeadersResponseClientModelReader();

    private final $$InternalsOnlyResponseClientModelReader internalsOnlyResponseClientModelReader =
            new $$InternalsOnlyResponseClientModelReader();

    private final $$HeadersOnlyResponseClientModelReader headersOnlyResponseClientModelReader =
            new $$HeadersOnlyResponseClientModelReader();

    private final $$InternalsAndHeadersResponseConstraintValidator internalsAndHeadersResponseConstraintValidator =
            new $$InternalsAndHeadersResponseConstraintValidator();

    private final $$BodyOnlyResponseConstraintValidator bodyOnlyResponseConstraintValidator =
            new $$BodyOnlyResponseConstraintValidator();

    private final $$BodyWithHeadersResponseConstraintValidator bodyWithHeadersResponseConstraintValidator =
            new $$BodyWithHeadersResponseConstraintValidator();

    private final $$HeadersOnlyResponseConstraintValidator headersOnlyResponseConstraintValidator =
            new $$HeadersOnlyResponseConstraintValidator();

    private final $$BodyWithInternalsAndHeadersResponseConstraintValidator bodyWithInternalsAndHeadersResponseConstraintValidator =
            new $$BodyWithInternalsAndHeadersResponseConstraintValidator();

    private final $$BodyWithInternalsResponseConstraintValidator bodyWithInternalsResponseConstraintValidator =
            new $$BodyWithInternalsResponseConstraintValidator();

    private final HttpClient client;

    private final RestClientConfig config;

    public $$GetterRestClient(final HttpClient client,
                              final RestClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletionStage<BodyOnlyResponse> produce01() {
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", "/produce01", EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> bodyOnlyResponseClientModelReader.readSingle(resp))
                .whenComplete((resp, th) -> validateIfResponseExists(bodyOnlyResponseConstraintValidator, resp));
    }

    @Override
    public CompletionStage<BodyWithHeadersResponse> produce02() {
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", "/produce02", EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> bodyWithHeadersResponseClientModelReader.readSingle(resp))
                .whenComplete((resp, th) -> validateIfResponseExists(bodyWithHeadersResponseConstraintValidator, resp));
    }

    @Override
    public CompletionStage<BodyWithInternalsResponse> produce03() {
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", "/produce03", EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> bodyWithInternalsResponseClientModelReader.readSingle(resp))
                .whenComplete((resp, th) -> validateIfResponseExists(bodyWithInternalsResponseConstraintValidator, resp));
    }

    @Override
    public CompletionStage<BodyWithInternalsAndHeadersResponse> produce04() {
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", "/produce04", EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> bodyWithInternalsAndHeadersResponseClientModelReader.readSingle(resp))
                .whenComplete((resp, th) -> validateIfResponseExists(bodyWithInternalsAndHeadersResponseConstraintValidator, resp));
    }

    @Override
    public CompletionStage<HeadersOnlyResponse> produce11() {
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", "/produce11", EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> headersOnlyResponseClientModelReader.readSingle(resp))
                .whenComplete((resp, th) -> validateIfResponseExists(headersOnlyResponseConstraintValidator, resp));
    }

    @Override
    public CompletionStage<InternalsOnlyResponse> produce12() {
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", "/produce12", EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> internalsOnlyResponseClientModelReader.readSingle(resp));
    }

    @Override
    public CompletionStage<InternalsAndHeadersResponse> produce13() {
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", "/produce13", EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> internalsAndHeadersResponseClientModelReader.readSingle(resp))
                .whenComplete((resp, th) -> validateIfResponseExists(internalsAndHeadersResponseConstraintValidator, resp));
    }
}
