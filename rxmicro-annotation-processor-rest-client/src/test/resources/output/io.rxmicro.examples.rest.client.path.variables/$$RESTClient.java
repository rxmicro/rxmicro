package io.rxmicro.examples.rest.client.path.variables;

import io.rxmicro.examples.rest.client.path.variables.model.$$RequestConstraintValidator;
import io.rxmicro.examples.rest.client.path.variables.model.$$RequestPathBuilder;
import io.rxmicro.examples.rest.client.path.variables.model.Request;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;
import io.rxmicro.rest.client.detail.HttpClient;
import io.rxmicro.rest.client.detail.HttpResponse;

import java.util.concurrent.CompletableFuture;

import static io.rxmicro.rest.client.detail.ErrorResponseCheckerHelper.throwExceptionIfNotSuccess;
import static io.rxmicro.validation.detail.RequestValidators.validateRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$RESTClient extends AbstractRestClient implements RESTClient {

    private final $$VirtualRESTRequestPathBuilder virtualRESTRequestPathBuilder =
            new $$VirtualRESTRequestPathBuilder();

    private final $$RequestPathBuilder requestPathBuilder =
            new $$RequestPathBuilder();

    private final $$VirtualRESTRequestConstraintValidator virtualRESTRequestConstraintValidator =
            new $$VirtualRESTRequestConstraintValidator();

    private final $$RequestConstraintValidator requestConstraintValidator =
            new $$RequestConstraintValidator();

    private final HttpClient client;

    private final RestClientConfig config;

    public $$RESTClient(final HttpClient client,
                        final RestClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletableFuture<Void> consume(final Request request) {
        validateRequest(config.isEnableAdditionalValidations(), requestConstraintValidator, request);
        final String path = requestPathBuilder.build("/${category}/${class}-${subType}", "/?/?-?", request);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", path, EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }

    @Override
    public CompletableFuture<Void> consume(final String category, final String type, final String subType) {
        final $$VirtualRESTRequest virtualRequest = new $$VirtualRESTRequest(category, type, subType);
        validateRequest(config.isEnableAdditionalValidations(), virtualRESTRequestConstraintValidator, virtualRequest);
        final String path = virtualRESTRequestPathBuilder.build("/${category}/${class}-${subType}", "/?/?-?", virtualRequest);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("GET", path, EMPTY_HEADERS)
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }
}
