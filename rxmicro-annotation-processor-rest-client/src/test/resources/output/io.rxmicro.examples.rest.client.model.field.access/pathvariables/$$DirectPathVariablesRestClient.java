package io.rxmicro.examples.rest.client.model.field.access.pathvariables;

import io.rxmicro.examples.rest.client.model.field.access.pathvariables.direct.$$RequestConstraintValidator;
import io.rxmicro.examples.rest.client.model.field.access.pathvariables.direct.$$RequestPathBuilder;
import io.rxmicro.examples.rest.client.model.field.access.pathvariables.direct.Request;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;
import io.rxmicro.rest.client.detail.HttpClient;
import io.rxmicro.rest.client.detail.HttpResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static io.rxmicro.rest.client.detail.ErrorResponseCheckerHelper.throwExceptionIfNotSuccess;
import static io.rxmicro.validation.detail.RequestValidators.validateRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$DirectPathVariablesRestClient extends AbstractRestClient implements DirectPathVariablesRestClient {

    private final $$RequestPathBuilder requestPathBuilder =
            new $$RequestPathBuilder();

    private final $$RequestConstraintValidator requestConstraintValidator =
            new $$RequestConstraintValidator();

    private final HttpClient client;

    private final RestClientConfig config;

    public $$DirectPathVariablesRestClient(final HttpClient client,
                                           final RestClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletionStage<Void> put(final Request request) {
        validateRequest(config.isEnableAdditionalValidations(), requestConstraintValidator, request);
        final String path = requestPathBuilder.build("/path-variables/direct/${a}/${b}/${c}/${d}/${e}/${f}/${g}/${j}/${h}/${i}/${j}/${k}/${l}/${m}", "/path-variables/direct/?/?/?/?/?/?/?/?/?/?/?/?/?/?", request);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("PUT", path, EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }
}
