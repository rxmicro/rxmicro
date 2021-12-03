package io.rxmicro.examples.rest.controller.model.types;

import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.$$HttpBodyRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.$$HttpBodyWithHeadersRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.$$HttpBodyWithInternalsAndHeadersRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.$$HttpBodyWithInternalsRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.$$HttpBodyWithPathVarAndHeadersRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.$$HttpBodyWithPathVarAndInternalsAndHeadersRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.$$HttpBodyWithPathVarAndInternalsRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.$$HttpBodyWithPathVarRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithInternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithPathVarAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithPathVarAndInternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithPathVarAndInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.http_body.HttpBodyWithPathVarRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.$$QueryOrHttpBodyRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.$$QueryOrHttpBodyWithHeadersRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.$$QueryOrHttpBodyWithInternalsAndHeadersRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.$$QueryOrHttpBodyWithInternalsRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.$$QueryOrHttpBodyWithPathVarAndHeadersRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.$$QueryOrHttpBodyWithPathVarAndInternalsAndHeadersRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.$$QueryOrHttpBodyWithPathVarAndInternalsRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.$$QueryOrHttpBodyWithPathVarRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithInternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithPathVarAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithPathVarAndInternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithPathVarAndInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_or_http_body.QueryOrHttpBodyWithPathVarRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.$$QueryStringRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.$$QueryStringWithHeadersRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.$$QueryStringWithInternalsAndHeadersRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.$$QueryStringWithInternalsRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.$$QueryStringWithPathVarAndHeadersRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.$$QueryStringWithPathVarAndInternalsAndHeadersRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.$$QueryStringWithPathVarAndInternalsRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.$$QueryStringWithPathVarRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithInternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithPathVarAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithPathVarAndInternalsAndHeadersRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithPathVarAndInternalsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.query_string.QueryStringWithPathVarRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_any_fields.$$WithoutAnyFieldsRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_any_fields.WithoutAnyFieldsRequest;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.$$HeadersOnlyRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.$$InternalsAndHeadersRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.$$InternalsOnlyRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.$$PathVarAndHeadersRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.$$PathVarAndInternalsRequestServerModelReader;
import io.rxmicro.examples.rest.controller.model.types.model.request.without_body.$$PathVarOnlyRequestServerModelReader;
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
import io.rxmicro.rest.server.detail.model.mapping.WithUrlPathVariablesRequestMappingRule;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ConsumeMicroService extends AbstractRestController {

    private ConsumeMicroService restController;

    private $$HttpBodyWithPathVarAndHeadersRequestServerModelReader httpBodyWithPathVarAndHeadersRequestServerModelReader;

    private $$QueryOrHttpBodyWithInternalsRequestServerModelReader queryOrHttpBodyWithInternalsRequestServerModelReader;

    private $$QueryOrHttpBodyWithInternalsAndHeadersRequestServerModelReader queryOrHttpBodyWithInternalsAndHeadersRequestServerModelReader;

    private $$QueryOrHttpBodyWithPathVarAndHeadersRequestServerModelReader queryOrHttpBodyWithPathVarAndHeadersRequestServerModelReader;

    private $$QueryStringWithPathVarRequestServerModelReader queryStringWithPathVarRequestServerModelReader;

    private $$QueryStringWithHeadersRequestServerModelReader queryStringWithHeadersRequestServerModelReader;

    private $$HttpBodyWithPathVarAndInternalsAndHeadersRequestServerModelReader httpBodyWithPathVarAndInternalsAndHeadersRequestServerModelReader;

    private $$HttpBodyRequestServerModelReader httpBodyRequestServerModelReader;

    private $$HttpBodyWithInternalsAndHeadersRequestServerModelReader httpBodyWithInternalsAndHeadersRequestServerModelReader;

    private $$HttpBodyWithPathVarAndInternalsRequestServerModelReader httpBodyWithPathVarAndInternalsRequestServerModelReader;

    private $$PathVarAndInternalsRequestServerModelReader pathVarAndInternalsRequestServerModelReader;

    private $$QueryStringWithPathVarAndHeadersRequestServerModelReader queryStringWithPathVarAndHeadersRequestServerModelReader;

    private $$WithoutAnyFieldsRequestServerModelReader withoutAnyFieldsRequestServerModelReader;

    private $$QueryOrHttpBodyWithPathVarAndInternalsRequestServerModelReader queryOrHttpBodyWithPathVarAndInternalsRequestServerModelReader;

    private $$HttpBodyWithHeadersRequestServerModelReader httpBodyWithHeadersRequestServerModelReader;

    private $$QueryOrHttpBodyRequestServerModelReader queryOrHttpBodyRequestServerModelReader;

    private $$InternalsOnlyRequestServerModelReader internalsOnlyRequestServerModelReader;

    private $$QueryOrHttpBodyWithPathVarAndInternalsAndHeadersRequestServerModelReader queryOrHttpBodyWithPathVarAndInternalsAndHeadersRequestServerModelReader;

    private $$PathVarOnlyRequestServerModelReader pathVarOnlyRequestServerModelReader;

    private $$QueryStringWithInternalsRequestServerModelReader queryStringWithInternalsRequestServerModelReader;

    private $$InternalsAndHeadersRequestServerModelReader internalsAndHeadersRequestServerModelReader;

    private $$HttpBodyWithPathVarRequestServerModelReader httpBodyWithPathVarRequestServerModelReader;

    private $$QueryStringWithPathVarAndInternalsAndHeadersRequestServerModelReader queryStringWithPathVarAndInternalsAndHeadersRequestServerModelReader;

    private $$PathVarAndHeadersRequestServerModelReader pathVarAndHeadersRequestServerModelReader;

    private $$QueryStringRequestServerModelReader queryStringRequestServerModelReader;

    private $$QueryOrHttpBodyWithPathVarRequestServerModelReader queryOrHttpBodyWithPathVarRequestServerModelReader;

    private $$QueryOrHttpBodyWithHeadersRequestServerModelReader queryOrHttpBodyWithHeadersRequestServerModelReader;

    private $$QueryStringWithPathVarAndInternalsRequestServerModelReader queryStringWithPathVarAndInternalsRequestServerModelReader;

    private $$HttpBodyWithInternalsRequestServerModelReader httpBodyWithInternalsRequestServerModelReader;

    private $$HeadersOnlyRequestServerModelReader headersOnlyRequestServerModelReader;

    private $$QueryStringWithInternalsAndHeadersRequestServerModelReader queryStringWithInternalsAndHeadersRequestServerModelReader;

    @Override
    protected void postConstruct() {
        restController = new ConsumeMicroService();
        httpBodyWithPathVarAndHeadersRequestServerModelReader = new $$HttpBodyWithPathVarAndHeadersRequestServerModelReader();
        queryOrHttpBodyWithInternalsRequestServerModelReader = new $$QueryOrHttpBodyWithInternalsRequestServerModelReader();
        queryOrHttpBodyWithInternalsAndHeadersRequestServerModelReader = new $$QueryOrHttpBodyWithInternalsAndHeadersRequestServerModelReader();
        queryOrHttpBodyWithPathVarAndHeadersRequestServerModelReader = new $$QueryOrHttpBodyWithPathVarAndHeadersRequestServerModelReader();
        queryStringWithPathVarRequestServerModelReader = new $$QueryStringWithPathVarRequestServerModelReader();
        queryStringWithHeadersRequestServerModelReader = new $$QueryStringWithHeadersRequestServerModelReader();
        httpBodyWithPathVarAndInternalsAndHeadersRequestServerModelReader = new $$HttpBodyWithPathVarAndInternalsAndHeadersRequestServerModelReader();
        httpBodyRequestServerModelReader = new $$HttpBodyRequestServerModelReader();
        httpBodyWithInternalsAndHeadersRequestServerModelReader = new $$HttpBodyWithInternalsAndHeadersRequestServerModelReader();
        httpBodyWithPathVarAndInternalsRequestServerModelReader = new $$HttpBodyWithPathVarAndInternalsRequestServerModelReader();
        pathVarAndInternalsRequestServerModelReader = new $$PathVarAndInternalsRequestServerModelReader();
        queryStringWithPathVarAndHeadersRequestServerModelReader = new $$QueryStringWithPathVarAndHeadersRequestServerModelReader();
        withoutAnyFieldsRequestServerModelReader = new $$WithoutAnyFieldsRequestServerModelReader();
        queryOrHttpBodyWithPathVarAndInternalsRequestServerModelReader = new $$QueryOrHttpBodyWithPathVarAndInternalsRequestServerModelReader();
        httpBodyWithHeadersRequestServerModelReader = new $$HttpBodyWithHeadersRequestServerModelReader();
        queryOrHttpBodyRequestServerModelReader = new $$QueryOrHttpBodyRequestServerModelReader();
        internalsOnlyRequestServerModelReader = new $$InternalsOnlyRequestServerModelReader();
        queryOrHttpBodyWithPathVarAndInternalsAndHeadersRequestServerModelReader = new $$QueryOrHttpBodyWithPathVarAndInternalsAndHeadersRequestServerModelReader();
        pathVarOnlyRequestServerModelReader = new $$PathVarOnlyRequestServerModelReader();
        queryStringWithInternalsRequestServerModelReader = new $$QueryStringWithInternalsRequestServerModelReader();
        internalsAndHeadersRequestServerModelReader = new $$InternalsAndHeadersRequestServerModelReader();
        httpBodyWithPathVarRequestServerModelReader = new $$HttpBodyWithPathVarRequestServerModelReader();
        queryStringWithPathVarAndInternalsAndHeadersRequestServerModelReader = new $$QueryStringWithPathVarAndInternalsAndHeadersRequestServerModelReader();
        pathVarAndHeadersRequestServerModelReader = new $$PathVarAndHeadersRequestServerModelReader();
        queryStringRequestServerModelReader = new $$QueryStringRequestServerModelReader();
        queryOrHttpBodyWithPathVarRequestServerModelReader = new $$QueryOrHttpBodyWithPathVarRequestServerModelReader();
        queryOrHttpBodyWithHeadersRequestServerModelReader = new $$QueryOrHttpBodyWithHeadersRequestServerModelReader();
        queryStringWithPathVarAndInternalsRequestServerModelReader = new $$QueryStringWithPathVarAndInternalsRequestServerModelReader();
        httpBodyWithInternalsRequestServerModelReader = new $$HttpBodyWithInternalsRequestServerModelReader();
        headersOnlyRequestServerModelReader = new $$HeadersOnlyRequestServerModelReader();
        queryStringWithInternalsAndHeadersRequestServerModelReader = new $$QueryStringWithInternalsAndHeadersRequestServerModelReader();
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
                        new WithUrlPathVariablesRequestMappingRule(
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
                        new WithUrlPathVariablesRequestMappingRule(
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
                        new WithUrlPathVariablesRequestMappingRule(
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
                        new WithUrlPathVariablesRequestMappingRule(
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
                        new WithUrlPathVariablesRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/consume12/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                false
                        ),

                        new WithUrlPathVariablesRequestMappingRule(
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
                        new WithUrlPathVariablesRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/consume15/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                false
                        ),

                        new WithUrlPathVariablesRequestMappingRule(
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
                        new WithUrlPathVariablesRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/consume16/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                false
                        ),

                        new WithUrlPathVariablesRequestMappingRule(
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
                        new WithUrlPathVariablesRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/consume33/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                false
                        ),

                        new WithUrlPathVariablesRequestMappingRule(
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
                        new WithUrlPathVariablesRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/consume36/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                false
                        ),

                        new WithUrlPathVariablesRequestMappingRule(
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
                        new WithUrlPathVariablesRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/consume37/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                false
                        ),

                        new WithUrlPathVariablesRequestMappingRule(
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
                        new WithUrlPathVariablesRequestMappingRule(
                                "GET",
                                new UrlSegments(
                                        "/consume38/?/?/?/?/?/?/?/?/?/?/?/?/?/?",
                                        List.of("a", "b", "c", "d", "e", "f", "g", "j", "h", "i", "j", "k", "l", "m")
                                ),
                                false
                        ),

                        new WithUrlPathVariablesRequestMappingRule(
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
                        new WithUrlPathVariablesRequestMappingRule(
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
                        new WithUrlPathVariablesRequestMappingRule(
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
                        new WithUrlPathVariablesRequestMappingRule(
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
                        new WithUrlPathVariablesRequestMappingRule(
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
        final HttpBodyRequest req = httpBodyRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeHttpBodyWithHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                            final HttpRequest request) {
        final HttpBodyWithHeadersRequest req = httpBodyWithHeadersRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeHttpBodyWithPathVarRequest(final PathVariableMapping pathVariableMapping,
                                                                            final HttpRequest request) {
        final HttpBodyWithPathVarRequest req = httpBodyWithPathVarRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeHttpBodyWithInternalsRequest(final PathVariableMapping pathVariableMapping,
                                                                              final HttpRequest request) {
        final HttpBodyWithInternalsRequest req = httpBodyWithInternalsRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeHttpBodyWithInternalsAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                        final HttpRequest request) {
        final HttpBodyWithInternalsAndHeadersRequest req = httpBodyWithInternalsAndHeadersRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeHttpBodyWithPathVarAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                      final HttpRequest request) {
        final HttpBodyWithPathVarAndHeadersRequest req = httpBodyWithPathVarAndHeadersRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeHttpBodyWithPathVarAndInternalsRequest(final PathVariableMapping pathVariableMapping,
                                                                                        final HttpRequest request) {
        final HttpBodyWithPathVarAndInternalsRequest req = httpBodyWithPathVarAndInternalsRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeHttpBodyWithPathVarAndInternalsAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                                  final HttpRequest request) {
        final HttpBodyWithPathVarAndInternalsAndHeadersRequest req = httpBodyWithPathVarAndInternalsAndHeadersRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeInternalsOnlyRequest(final PathVariableMapping pathVariableMapping,
                                                                      final HttpRequest request) {
        final InternalsOnlyRequest req = internalsOnlyRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumePathVarOnlyRequest(final PathVariableMapping pathVariableMapping,
                                                                    final HttpRequest request) {
        final PathVarOnlyRequest req = pathVarOnlyRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeHeadersOnlyRequest(final PathVariableMapping pathVariableMapping,
                                                                    final HttpRequest request) {
        final HeadersOnlyRequest req = headersOnlyRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeInternalsAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                            final HttpRequest request) {
        final InternalsAndHeadersRequest req = internalsAndHeadersRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumePathVarAndInternalsRequest(final PathVariableMapping pathVariableMapping,
                                                                            final HttpRequest request) {
        final PathVarAndInternalsRequest req = pathVarAndInternalsRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumePathVarAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                          final HttpRequest request) {
        final PathVarAndHeadersRequest req = pathVarAndHeadersRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryOrHttpBodyRequest(final PathVariableMapping pathVariableMapping,
                                                                        final HttpRequest request) {
        final QueryOrHttpBodyRequest req = queryOrHttpBodyRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryOrHttpBodyWithHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                   final HttpRequest request) {
        final QueryOrHttpBodyWithHeadersRequest req = queryOrHttpBodyWithHeadersRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryOrHttpBodyWithPathVarRequest(final PathVariableMapping pathVariableMapping,
                                                                                   final HttpRequest request) {
        final QueryOrHttpBodyWithPathVarRequest req = queryOrHttpBodyWithPathVarRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryOrHttpBodyWithInternalsRequest(final PathVariableMapping pathVariableMapping,
                                                                                     final HttpRequest request) {
        final QueryOrHttpBodyWithInternalsRequest req = queryOrHttpBodyWithInternalsRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryOrHttpBodyWithInternalsAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                               final HttpRequest request) {
        final QueryOrHttpBodyWithInternalsAndHeadersRequest req = queryOrHttpBodyWithInternalsAndHeadersRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryOrHttpBodyWithPathVarAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                             final HttpRequest request) {
        final QueryOrHttpBodyWithPathVarAndHeadersRequest req = queryOrHttpBodyWithPathVarAndHeadersRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryOrHttpBodyWithPathVarAndInternalsRequest(final PathVariableMapping pathVariableMapping,
                                                                                               final HttpRequest request) {
        final QueryOrHttpBodyWithPathVarAndInternalsRequest req = queryOrHttpBodyWithPathVarAndInternalsRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryOrHttpBodyWithPathVarAndInternalsAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                                         final HttpRequest request) {
        final QueryOrHttpBodyWithPathVarAndInternalsAndHeadersRequest req = queryOrHttpBodyWithPathVarAndInternalsAndHeadersRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryStringRequest(final PathVariableMapping pathVariableMapping,
                                                                    final HttpRequest request) {
        final QueryStringRequest req = queryStringRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryStringWithHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                               final HttpRequest request) {
        final QueryStringWithHeadersRequest req = queryStringWithHeadersRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryStringWithPathVarRequest(final PathVariableMapping pathVariableMapping,
                                                                               final HttpRequest request) {
        final QueryStringWithPathVarRequest req = queryStringWithPathVarRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryStringWithInternalsRequest(final PathVariableMapping pathVariableMapping,
                                                                                 final HttpRequest request) {
        final QueryStringWithInternalsRequest req = queryStringWithInternalsRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryStringWithInternalsAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                           final HttpRequest request) {
        final QueryStringWithInternalsAndHeadersRequest req = queryStringWithInternalsAndHeadersRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryStringWithPathVarAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                         final HttpRequest request) {
        final QueryStringWithPathVarAndHeadersRequest req = queryStringWithPathVarAndHeadersRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryStringWithPathVarAndInternalsRequest(final PathVariableMapping pathVariableMapping,
                                                                                           final HttpRequest request) {
        final QueryStringWithPathVarAndInternalsRequest req = queryStringWithPathVarAndInternalsRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeQueryStringWithPathVarAndInternalsAndHeadersRequest(final PathVariableMapping pathVariableMapping,
                                                                                                     final HttpRequest request) {
        final QueryStringWithPathVarAndInternalsAndHeadersRequest req = queryStringWithPathVarAndInternalsAndHeadersRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
        final HttpHeaders headers = HttpHeaders.of();
        restController.consume(req);
        return CompletableFuture.completedStage(buildResponse(200, headers));
    }

    private CompletionStage<HttpResponse> consumeWithoutAnyFieldsRequest(final PathVariableMapping pathVariableMapping,
                                                                         final HttpRequest request) {
        final WithoutAnyFieldsRequest req = withoutAnyFieldsRequestServerModelReader.read(pathVariableMapping, request, request.isContentPresent());
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
