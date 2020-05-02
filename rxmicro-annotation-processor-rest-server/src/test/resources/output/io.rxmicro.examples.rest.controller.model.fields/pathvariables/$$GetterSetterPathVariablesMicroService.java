package io.rxmicro.examples.rest.controller.model.fields.pathvariables;

import io.rxmicro.examples.rest.controller.model.fields.pathvariables.gettersetter.$$RequestModelReader;
import io.rxmicro.examples.rest.controller.model.fields.pathvariables.gettersetter.Request;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.model.UrlSegments;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.RestControllerRegistrar;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.UrlTemplateRequestMappingRule;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$GetterSetterPathVariablesMicroService extends AbstractRestController {

    private GetterSetterPathVariablesMicroService restController;

    private $$RequestModelReader requestModelReader;

    @Override
    protected void postConstruct() {
        restController = new GetterSetterPathVariablesMicroService();
        requestModelReader = new $$RequestModelReader();
    }

    @Override
    public Class<?> getRestControllerClass() {
        return GetterSetterPathVariablesMicroService.class;
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "/",
                        "put(io.rxmicro.examples.rest.controller.model.fields.pathvariables.gettersetter.Request)",
                        this::put,
                        false,
                        new UrlTemplateRequestMappingRule(
                                "PUT",
                                new UrlSegments(
                                        "/path-variables/gettersetter/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                true
                        )

                )
        );
    }

    private CompletionStage<HttpResponse> put(final PathVariableMapping pathVariableMapping,
                                              final HttpRequest request) {
        final Request req = requestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.put(req);
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
