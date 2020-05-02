package io.rxmicro.examples.rest.controller.redirect;

import io.rxmicro.examples.rest.controller.redirect.model.$$RedirectResponseModelWriter;
import io.rxmicro.examples.rest.controller.redirect.model.RedirectResponse;
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
public final class $$MicroService2 extends AbstractRestController {

    private MicroService2 restController;

    private $$Virtual2RequestModelReader virtual2RequestModelReader;

    private $$RedirectResponseModelWriter redirectResponseModelWriter;

    @Override
    protected void postConstruct() {
        restController = new MicroService2();
        virtual2RequestModelReader = new $$Virtual2RequestModelReader();
        redirectResponseModelWriter = new $$RedirectResponseModelWriter(restServerConfig.isHumanReadableOutput());
    }

    @Override
    public Class<?> getRestControllerClass() {
        return MicroService2.class;
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "/",
                        "redirect1()",
                        this::redirect1,
                        false,
                        new ExactUrlRequestMappingRule(
                                "PUT",
                                "/old-url1",
                                true
                        )
                ),
                new Registration(
                        "/",
                        "redirect2()",
                        this::redirect2,
                        false,
                        new ExactUrlRequestMappingRule(
                                "PUT",
                                "/old-url2",
                                true
                        )
                ),
                new Registration(
                        "/",
                        "put(java.lang.String)",
                        this::put,
                        false,
                        new ExactUrlRequestMappingRule(
                                "PUT",
                                "/new-url",
                                true
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> redirect1(final PathVariableMapping pathVariableMapping,
                                                    final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of();
        return restController.redirect1()
                .thenApply(response -> buildResponse(response, 200, headers));
    }

    private CompletionStage<HttpResponse> redirect2(final PathVariableMapping pathVariableMapping,
                                                    final HttpRequest request) {
        final HttpHeaders headers = HttpHeaders.of("Location", "/new-url");
        restController.redirect2();
        return CompletableFuture.completedStage(buildResponse(307, headers));
    }

    private CompletionStage<HttpResponse> put(final PathVariableMapping pathVariableMapping,
                                              final HttpRequest request) {
        final $$Virtual2Request req = virtual2RequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.put(req.parameter);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private HttpResponse buildResponse(final RedirectResponse model,
                                       final int statusCode,
                                       final HttpHeaders headers) {
        final HttpResponse response = httpResponseBuilder.build();
        response.setStatus(statusCode);
        response.setOrAddHeaders(headers);
        redirectResponseModelWriter.write(model, response);
        return response;
    }

    private HttpResponse buildResponse(final int statusCode,
                                       final HttpHeaders headers) {
        final HttpResponse response = httpResponseBuilder.build();
        response.setStatus(statusCode);
        response.setOrAddHeaders(headers);
        return response;
    }
}
