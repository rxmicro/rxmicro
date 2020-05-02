package io.rxmicro.examples.rest.controller.model.fields.headers;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.RestControllerRegistrar;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$VirtualHeadersMicroService extends AbstractRestController {

    private VirtualHeadersMicroService restController;

    private $$VirtualVirtualHeadersRequestModelReader virtualVirtualHeadersRequestModelReader;

    @Override
    protected void postConstruct() {
        restController = new VirtualHeadersMicroService();
        virtualVirtualHeadersRequestModelReader = new $$VirtualVirtualHeadersRequestModelReader();
    }

    @Override
    public Class<?> getRestControllerClass() {
        return VirtualHeadersMicroService.class;
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "/",
                        "put(java.lang.Boolean,java.lang.Byte,java.lang.Short,java.lang.Integer,java.lang.Long,java.math.BigInteger,java.lang.Float,java.lang.Double,java.math.BigDecimal,java.lang.Character,java.lang.String,java.time.Instant,io.rxmicro.examples.rest.controller.model.fields.Status,java.util.List<java.lang.Boolean>,java.util.List<java.lang.Byte>,java.util.List<java.lang.Short>,java.util.List<java.lang.Integer>,java.util.List<java.lang.Long>,java.util.List<java.math.BigInteger>,java.util.List<java.lang.Float>,java.util.List<java.lang.Double>,java.util.List<java.math.BigDecimal>,java.util.List<java.lang.Character>,java.util.List<java.lang.String>,java.util.List<java.time.Instant>,java.util.List<io.rxmicro.examples.rest.controller.model.fields.Status>)",
                        this::put,
                        false,
                        new ExactUrlRequestMappingRule(
                                "PUT",
                                "/headers/virtual",
                                true
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> put(final PathVariableMapping pathVariableMapping,
                                              final HttpRequest request) {
        final $$VirtualVirtualHeadersRequest req = virtualVirtualHeadersRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.put(req.booleanParameter, req.byteParameter, req.shortParameter, req.intParameter, req.longParameter, req.bigIntParameter, req.floatParameter, req.doubleParameter, req.decimalParameter, req.charParameter, req.stringParameter, req.instantParameter, req.status, req.booleanParameters, req.byteParameters, req.shortParameters, req.intParameters, req.longParameters, req.bigIntParameters, req.floatParameters, req.doubleParameters, req.decimalParameters, req.charParameters, req.stringParameters, req.instantParameters, req.statuses);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private HttpResponse buildResponse(final int statusCode,
                                       final HttpHeaders headers) {
        final HttpResponse response = httpResponseBuilder.build();
        response.setStatus(statusCode);
        response.setOrAddHeaders(headers);
        return response;
    }
}
