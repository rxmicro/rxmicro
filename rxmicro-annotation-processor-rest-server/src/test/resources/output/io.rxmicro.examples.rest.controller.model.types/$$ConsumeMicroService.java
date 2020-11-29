package io.rxmicro.examples.rest.controller.model.types;

import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.$$HttpBodyRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.$$HttpBodyWithHeadersRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.$$HttpBodyWithInternalsAndHeadersRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.$$HttpBodyWithInternalsRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.$$HttpBodyWithPathVarAndHeadersRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.$$HttpBodyWithPathVarAndInternalsAndHeadersRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.$$HttpBodyWithPathVarAndInternalsRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.$$HttpBodyWithPathVarRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithInternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithPathVarAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithPathVarAndInternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithPathVarAndInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithPathVarRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.$$QueryOrHttpBodyRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.$$QueryOrHttpBodyWithHeadersRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.$$QueryOrHttpBodyWithInternalsAndHeadersRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.$$QueryOrHttpBodyWithInternalsRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.$$QueryOrHttpBodyWithPathVarAndHeadersRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.$$QueryOrHttpBodyWithPathVarAndInternalsAndHeadersRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.$$QueryOrHttpBodyWithPathVarAndInternalsRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.$$QueryOrHttpBodyWithPathVarRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithInternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithPathVarAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithPathVarAndInternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithPathVarAndInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithPathVarRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.$$QueryStringRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.$$QueryStringWithHeadersRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.$$QueryStringWithInternalsAndHeadersRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.$$QueryStringWithInternalsRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.$$QueryStringWithPathVarAndHeadersRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.$$QueryStringWithPathVarAndInternalsAndHeadersRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.$$QueryStringWithPathVarAndInternalsRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.$$QueryStringWithPathVarRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithInternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithPathVarAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithPathVarAndInternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithPathVarAndInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithPathVarRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_any_fields.$$WithoutAnyFieldsRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_any_fields.WithoutAnyFieldsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.$$HeadersOnlyRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.$$InternalsAndHeadersRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.$$InternalsOnlyRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.$$PathVarAndHeadersRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.$$PathVarAndInternalsRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.$$PathVarOnlyRequestModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.HeadersOnlyRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.InternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.InternalsOnlyRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.PathVarAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.PathVarAndInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.PathVarOnlyRequest;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.model.UrlSegments;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.RestControllerRegistrar;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;
import io.rxmicro.rest.server.detail.model.mapping.UrlTemplateRequestMappingRule;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ConsumeMicroService extends AbstractRestController {

    private ConsumeMicroService restController;

    private $$HttpBodyWithPathVarAndHeadersRequestModelReader httpBodyWithPathVarAndHeadersRequestModelReader;

    private $$QueryOrHttpBodyWithInternalsRequestModelReader queryOrHttpBodyWithInternalsRequestModelReader;

    private $$QueryOrHttpBodyWithInternalsAndHeadersRequestModelReader queryOrHttpBodyWithInternalsAndHeadersRequestModelReader;

    private $$QueryOrHttpBodyWithPathVarAndHeadersRequestModelReader queryOrHttpBodyWithPathVarAndHeadersRequestModelReader;

    private $$QueryStringWithPathVarRequestModelReader queryStringWithPathVarRequestModelReader;

    private $$QueryStringWithHeadersRequestModelReader queryStringWithHeadersRequestModelReader;

    private $$HttpBodyWithPathVarAndInternalsAndHeadersRequestModelReader httpBodyWithPathVarAndInternalsAndHeadersRequestModelReader;

    private $$HttpBodyRequestModelReader httpBodyRequestModelReader;

    private $$HttpBodyWithInternalsAndHeadersRequestModelReader httpBodyWithInternalsAndHeadersRequestModelReader;

    private $$HttpBodyWithPathVarAndInternalsRequestModelReader httpBodyWithPathVarAndInternalsRequestModelReader;

    private $$PathVarAndInternalsRequestModelReader pathVarAndInternalsRequestModelReader;

    private $$QueryStringWithPathVarAndHeadersRequestModelReader queryStringWithPathVarAndHeadersRequestModelReader;

    private $$WithoutAnyFieldsRequestModelReader withoutAnyFieldsRequestModelReader;

    private $$QueryOrHttpBodyWithPathVarAndInternalsRequestModelReader queryOrHttpBodyWithPathVarAndInternalsRequestModelReader;

    private $$HttpBodyWithHeadersRequestModelReader httpBodyWithHeadersRequestModelReader;

    private $$QueryOrHttpBodyRequestModelReader queryOrHttpBodyRequestModelReader;

    private $$InternalsOnlyRequestModelReader internalsOnlyRequestModelReader;

    private $$QueryOrHttpBodyWithPathVarAndInternalsAndHeadersRequestModelReader queryOrHttpBodyWithPathVarAndInternalsAndHeadersRequestModelReader;

    private $$PathVarOnlyRequestModelReader pathVarOnlyRequestModelReader;

    private $$QueryStringWithInternalsRequestModelReader queryStringWithInternalsRequestModelReader;

    private $$InternalsAndHeadersRequestModelReader internalsAndHeadersRequestModelReader;

    private $$HttpBodyWithPathVarRequestModelReader httpBodyWithPathVarRequestModelReader;

    private $$QueryStringWithPathVarAndInternalsAndHeadersRequestModelReader queryStringWithPathVarAndInternalsAndHeadersRequestModelReader;

    private $$PathVarAndHeadersRequestModelReader pathVarAndHeadersRequestModelReader;

    private $$QueryStringRequestModelReader queryStringRequestModelReader;

    private $$QueryOrHttpBodyWithPathVarRequestModelReader queryOrHttpBodyWithPathVarRequestModelReader;

    private $$QueryOrHttpBodyWithHeadersRequestModelReader queryOrHttpBodyWithHeadersRequestModelReader;

    private $$QueryStringWithPathVarAndInternalsRequestModelReader queryStringWithPathVarAndInternalsRequestModelReader;

    private $$HttpBodyWithInternalsRequestModelReader httpBodyWithInternalsRequestModelReader;

    private $$HeadersOnlyRequestModelReader headersOnlyRequestModelReader;

    private $$QueryStringWithInternalsAndHeadersRequestModelReader queryStringWithInternalsAndHeadersRequestModelReader;

    @Override
    protected void postConstruct() {
        restController = new ConsumeMicroService();
        httpBodyWithPathVarAndHeadersRequestModelReader = new $$HttpBodyWithPathVarAndHeadersRequestModelReader();
        queryOrHttpBodyWithInternalsRequestModelReader = new $$QueryOrHttpBodyWithInternalsRequestModelReader();
        queryOrHttpBodyWithInternalsAndHeadersRequestModelReader = new $$QueryOrHttpBodyWithInternalsAndHeadersRequestModelReader();
        queryOrHttpBodyWithPathVarAndHeadersRequestModelReader = new $$QueryOrHttpBodyWithPathVarAndHeadersRequestModelReader();
        queryStringWithPathVarRequestModelReader = new $$QueryStringWithPathVarRequestModelReader();
        queryStringWithHeadersRequestModelReader = new $$QueryStringWithHeadersRequestModelReader();
        httpBodyWithPathVarAndInternalsAndHeadersRequestModelReader = new $$HttpBodyWithPathVarAndInternalsAndHeadersRequestModelReader();
        httpBodyRequestModelReader = new $$HttpBodyRequestModelReader();
        httpBodyWithInternalsAndHeadersRequestModelReader = new $$HttpBodyWithInternalsAndHeadersRequestModelReader();
        httpBodyWithPathVarAndInternalsRequestModelReader = new $$HttpBodyWithPathVarAndInternalsRequestModelReader();
        pathVarAndInternalsRequestModelReader = new $$PathVarAndInternalsRequestModelReader();
        queryStringWithPathVarAndHeadersRequestModelReader = new $$QueryStringWithPathVarAndHeadersRequestModelReader();
        withoutAnyFieldsRequestModelReader = new $$WithoutAnyFieldsRequestModelReader();
        queryOrHttpBodyWithPathVarAndInternalsRequestModelReader = new $$QueryOrHttpBodyWithPathVarAndInternalsRequestModelReader();
        httpBodyWithHeadersRequestModelReader = new $$HttpBodyWithHeadersRequestModelReader();
        queryOrHttpBodyRequestModelReader = new $$QueryOrHttpBodyRequestModelReader();
        internalsOnlyRequestModelReader = new $$InternalsOnlyRequestModelReader();
        queryOrHttpBodyWithPathVarAndInternalsAndHeadersRequestModelReader = new $$QueryOrHttpBodyWithPathVarAndInternalsAndHeadersRequestModelReader();
        pathVarOnlyRequestModelReader = new $$PathVarOnlyRequestModelReader();
        queryStringWithInternalsRequestModelReader = new $$QueryStringWithInternalsRequestModelReader();
        internalsAndHeadersRequestModelReader = new $$InternalsAndHeadersRequestModelReader();
        httpBodyWithPathVarRequestModelReader = new $$HttpBodyWithPathVarRequestModelReader();
        queryStringWithPathVarAndInternalsAndHeadersRequestModelReader = new $$QueryStringWithPathVarAndInternalsAndHeadersRequestModelReader();
        pathVarAndHeadersRequestModelReader = new $$PathVarAndHeadersRequestModelReader();
        queryStringRequestModelReader = new $$QueryStringRequestModelReader();
        queryOrHttpBodyWithPathVarRequestModelReader = new $$QueryOrHttpBodyWithPathVarRequestModelReader();
        queryOrHttpBodyWithHeadersRequestModelReader = new $$QueryOrHttpBodyWithHeadersRequestModelReader();
        queryStringWithPathVarAndInternalsRequestModelReader = new $$QueryStringWithPathVarAndInternalsRequestModelReader();
        httpBodyWithInternalsRequestModelReader = new $$HttpBodyWithInternalsRequestModelReader();
        headersOnlyRequestModelReader = new $$HeadersOnlyRequestModelReader();
        queryStringWithInternalsAndHeadersRequestModelReader = new $$QueryStringWithInternalsAndHeadersRequestModelReader();
    }

    @Override
    public Class<?> getRestControllerClass() {
        return ConsumeMicroService.class;
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        registrar.register(
                this,
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyRequest.class
                        ),
                        this::consumeHttpBodyRequest,
                        false,
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/consume01",
                                true
                        )
                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithHeadersRequest.class
                        ),
                        this::consumeHttpBodyWithHeadersRequest,
                        false,
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/consume02",
                                true
                        )
                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithPathVarRequest.class
                        ),
                        this::consumeHttpBodyWithPathVarRequest,
                        false,
                        new UrlTemplateRequestMappingRule(
                                "POST",
                                new UrlSegments(
                                        "/consume03/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                true
                        )

                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithInternalsRequest.class
                        ),
                        this::consumeHttpBodyWithInternalsRequest,
                        false,
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/consume04",
                                true
                        )
                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithInternalsAndHeadersRequest.class
                        ),
                        this::consumeHttpBodyWithInternalsAndHeadersRequest,
                        false,
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/consume05",
                                true
                        )
                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithPathVarAndHeadersRequest.class
                        ),
                        this::consumeHttpBodyWithPathVarAndHeadersRequest,
                        false,
                        new UrlTemplateRequestMappingRule(
                                "POST",
                                new UrlSegments(
                                        "/consume06/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                true
                        )

                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithPathVarAndInternalsRequest.class
                        ),
                        this::consumeHttpBodyWithPathVarAndInternalsRequest,
                        false,
                        new UrlTemplateRequestMappingRule(
                                "POST",
                                new UrlSegments(
                                        "/consume07/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                true
                        )

                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithPathVarAndInternalsAndHeadersRequest.class
                        ),
                        this::consumeHttpBodyWithPathVarAndInternalsAndHeadersRequest,
                        false,
                        new UrlTemplateRequestMappingRule(
                                "POST",
                                new UrlSegments(
                                        "/consume08/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                true
                        )

                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.without_body.InternalsOnlyRequest.class
                        ),
                        this::consumeInternalsOnlyRequest,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/consume11",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/consume11",
                                true
                        )
                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.without_body.PathVarOnlyRequest.class
                        ),
                        this::consumePathVarOnlyRequest,
                        false,
                        new UrlTemplateRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/consume12/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                false
                        ),

                        new UrlTemplateRequestMappingRule(
                                "POST",
                                new UrlSegments(
                                        "/consume12/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                true
                        )

                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.without_body.HeadersOnlyRequest.class
                        ),
                        this::consumeHeadersOnlyRequest,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/consume13",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/consume13",
                                true
                        )
                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.without_body.InternalsAndHeadersRequest.class
                        ),
                        this::consumeInternalsAndHeadersRequest,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/consume14",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/consume14",
                                true
                        )
                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.without_body.PathVarAndInternalsRequest.class
                        ),
                        this::consumePathVarAndInternalsRequest,
                        false,
                        new UrlTemplateRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/consume15/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                false
                        ),

                        new UrlTemplateRequestMappingRule(
                                "POST",
                                new UrlSegments(
                                        "/consume15/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                true
                        )

                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.without_body.PathVarAndHeadersRequest.class
                        ),
                        this::consumePathVarAndHeadersRequest,
                        false,
                        new UrlTemplateRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/consume16/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                false
                        ),

                        new UrlTemplateRequestMappingRule(
                                "POST",
                                new UrlSegments(
                                        "/consume16/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                true
                        )

                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyRequest.class
                        ),
                        this::consumeQueryOrHttpBodyRequest,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/consume31",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/consume31",
                                true
                        )
                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithHeadersRequest.class
                        ),
                        this::consumeQueryOrHttpBodyWithHeadersRequest,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/consume32",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/consume32",
                                true
                        )
                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithPathVarRequest.class
                        ),
                        this::consumeQueryOrHttpBodyWithPathVarRequest,
                        false,
                        new UrlTemplateRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/consume33/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                false
                        ),

                        new UrlTemplateRequestMappingRule(
                                "POST",
                                new UrlSegments(
                                        "/consume33/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                true
                        )

                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithInternalsRequest.class
                        ),
                        this::consumeQueryOrHttpBodyWithInternalsRequest,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/consume34",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/consume34",
                                true
                        )
                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithInternalsAndHeadersRequest.class
                        ),
                        this::consumeQueryOrHttpBodyWithInternalsAndHeadersRequest,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/consume35",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/consume35",
                                true
                        )
                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithPathVarAndHeadersRequest.class
                        ),
                        this::consumeQueryOrHttpBodyWithPathVarAndHeadersRequest,
                        false,
                        new UrlTemplateRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/consume36/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                false
                        ),

                        new UrlTemplateRequestMappingRule(
                                "POST",
                                new UrlSegments(
                                        "/consume36/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                true
                        )

                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithPathVarAndInternalsRequest.class
                        ),
                        this::consumeQueryOrHttpBodyWithPathVarAndInternalsRequest,
                        false,
                        new UrlTemplateRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/consume37/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                false
                        ),

                        new UrlTemplateRequestMappingRule(
                                "POST",
                                new UrlSegments(
                                        "/consume37/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                true
                        )

                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithPathVarAndInternalsAndHeadersRequest.class
                        ),
                        this::consumeQueryOrHttpBodyWithPathVarAndInternalsAndHeadersRequest,
                        false,
                        new UrlTemplateRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/consume38/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                false
                        ),

                        new UrlTemplateRequestMappingRule(
                                "POST",
                                new UrlSegments(
                                        "/consume38/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                true
                        )

                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringRequest.class
                        ),
                        this::consumeQueryStringRequest,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/consume41",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithHeadersRequest.class
                        ),
                        this::consumeQueryStringWithHeadersRequest,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/consume42",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithPathVarRequest.class
                        ),
                        this::consumeQueryStringWithPathVarRequest,
                        false,
                        new UrlTemplateRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/consume43/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                false
                        )

                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithInternalsRequest.class
                        ),
                        this::consumeQueryStringWithInternalsRequest,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/consume44",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithInternalsAndHeadersRequest.class
                        ),
                        this::consumeQueryStringWithInternalsAndHeadersRequest,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/consume45",
                                false
                        )
                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithPathVarAndHeadersRequest.class
                        ),
                        this::consumeQueryStringWithPathVarAndHeadersRequest,
                        false,
                        new UrlTemplateRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/consume46/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                false
                        )

                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithPathVarAndInternalsRequest.class
                        ),
                        this::consumeQueryStringWithPathVarAndInternalsRequest,
                        false,
                        new UrlTemplateRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/consume47/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                false
                        )

                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithPathVarAndInternalsAndHeadersRequest.class
                        ),
                        this::consumeQueryStringWithPathVarAndInternalsAndHeadersRequest,
                        false,
                        new UrlTemplateRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/consume48/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                false
                        )

                ),
                new Registration(
                        "/",
                        "consume",
                        List.of(
                                io.rxmicro.examples.rest.controller.model.types.model.request.without_any_fields.WithoutAnyFieldsRequest.class
                        ),
                        this::consumeWithoutAnyFieldsRequest,
                        false,
                        new ExactUrlRequestMappingRule(
                                "GET",
                                "/consume51",
                                false
                        ),
                        new ExactUrlRequestMappingRule(
                                "POST",
                                "/consume51",
                                true
                        )
                )
        );
    }

    private CompletionStage<HttpResponse> consumeHttpBodyRequest(final PathVariableMapping pathVariableMapping,
                                                                 final HttpRequest request) {
        final HttpBodyRequest req = httpBodyRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeHttpBodyWithHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                            final HttpRequest request) {
        final HttpBodyWithHeadersRequest req = httpBodyWithHeadersRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeHttpBodyWithPathVarRequest(final PathVariableMapping pathVariableMapping,
                                                                            final HttpRequest request) {
        final HttpBodyWithPathVarRequest req = httpBodyWithPathVarRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeHttpBodyWithInternalsRequest(final PathVariableMapping pathVariableMapping,
                                                                              final HttpRequest request) {
        final HttpBodyWithInternalsRequest req = httpBodyWithInternalsRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeHttpBodyWithInternalsAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                        final HttpRequest request) {
        final HttpBodyWithInternalsAndHeadersRequest req = httpBodyWithInternalsAndHeadersRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeHttpBodyWithPathVarAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                      final HttpRequest request) {
        final HttpBodyWithPathVarAndHeadersRequest req = httpBodyWithPathVarAndHeadersRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeHttpBodyWithPathVarAndInternalsRequest(final PathVariableMapping pathVariableMapping,
                                                                                        final HttpRequest request) {
        final HttpBodyWithPathVarAndInternalsRequest req = httpBodyWithPathVarAndInternalsRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeHttpBodyWithPathVarAndInternalsAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                                  final HttpRequest request) {
        final HttpBodyWithPathVarAndInternalsAndHeadersRequest req = httpBodyWithPathVarAndInternalsAndHeadersRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeInternalsOnlyRequest(final PathVariableMapping pathVariableMapping,
                                                                      final HttpRequest request) {
        final InternalsOnlyRequest req = internalsOnlyRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumePathVarOnlyRequest(final PathVariableMapping pathVariableMapping,
                                                                    final HttpRequest request) {
        final PathVarOnlyRequest req = pathVarOnlyRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeHeadersOnlyRequest(final PathVariableMapping pathVariableMapping,
                                                                    final HttpRequest request) {
        final HeadersOnlyRequest req = headersOnlyRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeInternalsAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                            final HttpRequest request) {
        final InternalsAndHeadersRequest req = internalsAndHeadersRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumePathVarAndInternalsRequest(final PathVariableMapping pathVariableMapping,
                                                                            final HttpRequest request) {
        final PathVarAndInternalsRequest req = pathVarAndInternalsRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumePathVarAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                          final HttpRequest request) {
        final PathVarAndHeadersRequest req = pathVarAndHeadersRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryOrHttpBodyRequest(final PathVariableMapping pathVariableMapping,
                                                                        final HttpRequest request) {
        final QueryOrHttpBodyRequest req = queryOrHttpBodyRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryOrHttpBodyWithHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                   final HttpRequest request) {
        final QueryOrHttpBodyWithHeadersRequest req = queryOrHttpBodyWithHeadersRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryOrHttpBodyWithPathVarRequest(final PathVariableMapping pathVariableMapping,
                                                                                   final HttpRequest request) {
        final QueryOrHttpBodyWithPathVarRequest req = queryOrHttpBodyWithPathVarRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryOrHttpBodyWithInternalsRequest(final PathVariableMapping pathVariableMapping,
                                                                                     final HttpRequest request) {
        final QueryOrHttpBodyWithInternalsRequest req = queryOrHttpBodyWithInternalsRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryOrHttpBodyWithInternalsAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                               final HttpRequest request) {
        final QueryOrHttpBodyWithInternalsAndHeadersRequest req = queryOrHttpBodyWithInternalsAndHeadersRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryOrHttpBodyWithPathVarAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                             final HttpRequest request) {
        final QueryOrHttpBodyWithPathVarAndHeadersRequest req = queryOrHttpBodyWithPathVarAndHeadersRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryOrHttpBodyWithPathVarAndInternalsRequest(final PathVariableMapping pathVariableMapping,
                                                                                               final HttpRequest request) {
        final QueryOrHttpBodyWithPathVarAndInternalsRequest req = queryOrHttpBodyWithPathVarAndInternalsRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryOrHttpBodyWithPathVarAndInternalsAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                                         final HttpRequest request) {
        final QueryOrHttpBodyWithPathVarAndInternalsAndHeadersRequest req = queryOrHttpBodyWithPathVarAndInternalsAndHeadersRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryStringRequest(final PathVariableMapping pathVariableMapping,
                                                                    final HttpRequest request) {
        final QueryStringRequest req = queryStringRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryStringWithHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                               final HttpRequest request) {
        final QueryStringWithHeadersRequest req = queryStringWithHeadersRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryStringWithPathVarRequest(final PathVariableMapping pathVariableMapping,
                                                                               final HttpRequest request) {
        final QueryStringWithPathVarRequest req = queryStringWithPathVarRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryStringWithInternalsRequest(final PathVariableMapping pathVariableMapping,
                                                                                 final HttpRequest request) {
        final QueryStringWithInternalsRequest req = queryStringWithInternalsRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryStringWithInternalsAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                           final HttpRequest request) {
        final QueryStringWithInternalsAndHeadersRequest req = queryStringWithInternalsAndHeadersRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryStringWithPathVarAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                         final HttpRequest request) {
        final QueryStringWithPathVarAndHeadersRequest req = queryStringWithPathVarAndHeadersRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryStringWithPathVarAndInternalsRequest(final PathVariableMapping pathVariableMapping,
                                                                                           final HttpRequest request) {
        final QueryStringWithPathVarAndInternalsRequest req = queryStringWithPathVarAndInternalsRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryStringWithPathVarAndInternalsAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                                     final HttpRequest request) {
        final QueryStringWithPathVarAndInternalsAndHeadersRequest req = queryStringWithPathVarAndInternalsAndHeadersRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeWithoutAnyFieldsRequest(final PathVariableMapping pathVariableMapping,
                                                                         final HttpRequest request) {
        final WithoutAnyFieldsRequest req = withoutAnyFieldsRequestModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
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
