package io.rxmicro.examples.rest.client.model.field.access.headers;

import io.rxmicro.examples.rest.client.model.field.access.Status;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.AbstractRestClient;
import io.rxmicro.rest.client.detail.HeaderBuilder;
import io.rxmicro.rest.client.detail.HttpClient;
import io.rxmicro.rest.client.detail.HttpResponse;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static io.rxmicro.rest.client.detail.ErrorResponseCheckerHelper.throwExceptionIfNotSuccess;
import static io.rxmicro.validation.detail.RequestValidators.validateRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualHeadersRestClient extends AbstractRestClient implements VirtualHeadersRestClient {

    private final $$VirtualVirtualHeadersRequestRequestModelExtractor virtualVirtualHeadersRequestRequestModelExtractor =
            new $$VirtualVirtualHeadersRequestRequestModelExtractor();

    private final $$VirtualVirtualHeadersRequestConstraintValidator virtualVirtualHeadersRequestConstraintValidator =
            new $$VirtualVirtualHeadersRequestConstraintValidator();

    private final HttpClient client;

    private final RestClientConfig config;

    public $$VirtualHeadersRestClient(final HttpClient client,
                                      final RestClientConfig config) {
        this.client = client;
        this.config = config;
    }

    @Override
    public CompletionStage<Void> put(final Boolean booleanHeader, final Byte byteHeader, final Short shortHeader, final Integer intHeader, final Long longHeader, final BigInteger bigIntHeader, final Float floatHeader, final Double doubleHeader, final BigDecimal decimalHeader, final Character charHeader, final String stringHeader, final Instant instantHeader, final Status enumHeader, final List<Boolean> booleanHeaderList, final List<Byte> byteHeaderList, final List<Short> shortHeaderList, final List<Integer> intHeaderList, final List<Long> longHeaderList, final List<BigInteger> bigIntHeaderList, final List<Float> floatHeaderList, final List<Double> doubleHeaderList, final List<BigDecimal> decimalHeaderList, final List<Character> charHeaderList, final List<String> stringHeaderList, final List<Instant> instantHeaderList, final List<Status> enumHeaderList, final Set<Boolean> booleanHeaderSet, final Set<Byte> byteHeaderSet, final Set<Short> shortHeaderSet, final Set<Integer> intHeaderSet, final Set<Long> longHeaderSet, final Set<BigInteger> bigIntHeaderSet, final Set<Float> floatHeaderSet, final Set<Double> doubleHeaderSet, final Set<BigDecimal> decimalHeaderSet, final Set<Character> charHeaderSet, final Set<String> stringHeaderSet, final Set<Instant> instantHeaderSet, final Set<Status> enumHeaderSet, final String requestIdHeader, final List<Status> repeatingEnumHeaderList, final Set<Status> repeatingEnumHeaderSet) {
        final $$VirtualVirtualHeadersRequest virtualRequest = new $$VirtualVirtualHeadersRequest(booleanHeader, byteHeader, shortHeader, intHeader, longHeader, bigIntHeader, floatHeader, doubleHeader, decimalHeader, charHeader, stringHeader, instantHeader, enumHeader, booleanHeaderList, byteHeaderList, shortHeaderList, intHeaderList, longHeaderList, bigIntHeaderList, floatHeaderList, doubleHeaderList, decimalHeaderList, charHeaderList, stringHeaderList, instantHeaderList, enumHeaderList, booleanHeaderSet, byteHeaderSet, shortHeaderSet, intHeaderSet, longHeaderSet, bigIntHeaderSet, floatHeaderSet, doubleHeaderSet, decimalHeaderSet, charHeaderSet, stringHeaderSet, instantHeaderSet, enumHeaderSet, requestIdHeader, repeatingEnumHeaderList, repeatingEnumHeaderSet);
        validateRequest(config.isEnableAdditionalValidations(), virtualVirtualHeadersRequestConstraintValidator, virtualRequest);
        final String path = "/headers/virtual";
        final HeaderBuilder headerBuilder = new HeaderBuilder();
        virtualVirtualHeadersRequestRequestModelExtractor.extract(virtualRequest, headerBuilder);
        final CompletableFuture<HttpResponse> response = client
                .sendAsync("PUT", path, headerBuilder.build())
                .handle(throwExceptionIfNotSuccess());
        return response
                .thenApply(resp -> null);
    }
}
