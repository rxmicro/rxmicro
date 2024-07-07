package io.rxmicro.examples.rest.client.model.field.access.params;

import io.rxmicro.examples.rest.client.model.field.access.params.gettersetter.$$BodyRequestConstraintValidator;
import io.rxmicro.examples.rest.client.model.field.access.params.gettersetter.$$BodyRequestModelToJsonConverter;
import io.rxmicro.examples.rest.client.model.field.access.params.gettersetter.$$QueryRequestConstraintValidator;
import io.rxmicro.examples.rest.client.model.field.access.params.gettersetter.$$QueryRequestRequestModelExtractor;
import io.rxmicro.examples.rest.client.model.field.access.params.gettersetter.$$ResponseClientModelReader;
import io.rxmicro.examples.rest.client.model.field.access.params.gettersetter.$$ResponseConstraintValidator;
import io.rxmicro.examples.rest.client.model.field.access.params.gettersetter.BodyRequest;
import io.rxmicro.examples.rest.client.model.field.access.params.gettersetter.QueryRequest;
import io.rxmicro.examples.rest.client.model.field.access.params.gettersetter.Response;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;
import io.rxmicro.rest.client.detail.HttpClient;
import io.rxmicro.rest.client.detail.HttpResponse;
import io.rxmicro.rest.client.detail.QueryBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static io.rxmicro.rest.client.detail.ErrorResponseCheckerHelper.throwExceptionIfNotSuccess;
import static io.rxmicro.validation.detail.RequestValidators.validateRequest;
import static io.rxmicro.validation.detail.ResponseValidators.validateIfResponseExists;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$GetterSetterParamsRestClient extends AbstractRestClient implements GetterSetterParamsRestClient {

    private final $$QueryRequestRequestModelExtractor queryRequestRequestModelExtractor =
            new $$QueryRequestRequestModelExtractor();

    private final $$BodyRequestModelToJsonConverter bodyRequestModelToJsonConverter =
            new $$BodyRequestModelToJsonConverter();

    private final $$ResponseClientModelReader responseClientModelReader =
            new $$ResponseClientModelReader();

    private final $$QueryRequestConstraintValidator queryRequestConstraintValidator =
            new $$QueryRequestConstraintValidator();

    private final $$BodyRequestConstraintValidator bodyRequestConstraintValidator =
            new $$BodyRequestConstraintValidator();

    private final $$ResponseConstraintValidator responseConstraintValidator =
            new $$ResponseConstraintValidator();

    private final HttpClient client;

    private final RestClientConfig config;

    public $$GetterSetterParamsRestClient(final HttpClient client,
                                          final RestClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletionStage<Response> get(final QueryRequest request) {
        validateRequest(config.isEnableAdditionalValidations(), queryRequestConstraintValidator, request);
        final String path = "/params/direct";
        final QueryBuilder queryBuilder = new QueryBuilder();
        queryRequestRequestModelExtractor.extract(request, queryBuilder);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", joinPath(path, queryBuilder.build()), EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> responseClientModelReader.readSingle(resp))
                .whenComplete((resp, th) -> validateIfResponseExists(responseConstraintValidator, resp));
    }

    @Override
    public CompletionStage<Response> put(final BodyRequest request) {
        validateRequest(config.isEnableAdditionalValidations(), bodyRequestConstraintValidator, request);
        final String path = "/params/gettersetter";
        final Object body = bodyRequestModelToJsonConverter.toJson(request);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("PUT", path, EMPTY_HEADERS, body)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> responseClientModelReader.readSingle(resp))
                .whenComplete((resp, th) -> validateIfResponseExists(responseConstraintValidator, resp));
    }
}