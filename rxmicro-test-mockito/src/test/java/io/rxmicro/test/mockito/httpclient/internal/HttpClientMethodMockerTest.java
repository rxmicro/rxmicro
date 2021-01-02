/*
 * Copyright (c) 2020. https://rxmicro.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.rxmicro.test.mockito.httpclient.internal;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.client.detail.HttpClient;
import io.rxmicro.rest.client.detail.HttpResponse;
import io.rxmicro.rest.model.HttpMethod;
import io.rxmicro.test.mockito.httpclient.HttpRequestMock;
import io.rxmicro.test.mockito.httpclient.HttpResponseMock;
import io.rxmicro.test.mockito.httpclient.internal.model.ResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 *
 * @since 0.1
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
final class HttpClientMethodMockerTest {

    private final HttpClientMethodMocker httpClientMethodMocker = new HttpClientMethodMocker();

    @Mock
    private HttpClient client;

    @Mock
    private HttpResponse expected;

    @Mock
    private HttpResponseMock httpResponseMock;

    static Stream<Function<HttpClient, CompletableFuture<HttpResponse>>> provider() {
        return Stream.of(
                c -> c.sendAsync("GET", "/", HttpHeaders.of().getEntries()),
                c -> c.sendAsync("HEAD", "/head", HttpHeaders.of("H1", "V1").getEntries()),
                c -> c.sendAsync("DELETE", "/", HttpHeaders.of("H1", "V1").getEntries()),
                c -> c.sendAsync("OPTIONS", "/a/b/c", HttpHeaders.of("H1", "V1", "H1", "V2").getEntries()),
                c -> c.sendAsync("POST", "/", HttpHeaders.of().getEntries(), "<HTTP-BODY>"),
                c -> c.sendAsync("POST", "/head", HttpHeaders.of("H1", "V1").getEntries(), "<HTTP-BODY>"),
                c -> c.sendAsync("PATCH", "/", HttpHeaders.of("H1", "V1").getEntries(), "<HTTP-BODY>"),
                c -> c.sendAsync("PUT", "/a/b/c", HttpHeaders.of("H1", "V1", "H1", "V2").getEntries(), "<HTTP-BODY>")
        );
    }

    @BeforeEach
    void beforeEach() {
        when(httpResponseMock.getClientHttpResponse()).thenReturn(expected);
    }

    @ParameterizedTest
    @MethodSource("provider")
    @Order(1)
    void Should_mock_any_request(final Function<HttpClient, CompletableFuture<HttpResponse>> function) {
        final HttpRequestMock httpRequestMock = new HttpRequestMock.Builder()
                .setAnyRequest()
                .build();

        httpClientMethodMocker.mock(httpRequestMock, client, new ResponseModel(httpResponseMock), false);

        assertSame(
                expected,
                function.apply(client).join()
        );
    }

    @Test
    @Order(2)
    void Should_mock_exact_request_without_body() {
        final HttpRequestMock httpRequestMock = new HttpRequestMock.Builder()
                .setMethod(HttpMethod.GET)
                .setPath("/get")
                .setHeaders(HttpHeaders.of("H1", "V1"))
                .build();

        httpClientMethodMocker.mock(httpRequestMock, client, new ResponseModel(httpResponseMock), false);

        assertSame(
                expected,
                client.sendAsync("GET", "/get", HttpHeaders.of("H1", "V1").getEntries()).join()
        );
    }

    @Test
    @Order(3)
    void Should_mock_exact_request_with_query_params() {
        final HttpRequestMock httpRequestMock = new HttpRequestMock.Builder()
                .setMethod(HttpMethod.GET)
                .setPath("/get")
                .setQueryParameters(QueryParams.of("p1", "v1"))
                .setHeaders(HttpHeaders.of("H1", "V1"))
                .build();

        httpClientMethodMocker.mock(httpRequestMock, client, new ResponseModel(httpResponseMock), false);

        assertSame(
                expected,
                client.sendAsync("GET", "/get?p1=v1", HttpHeaders.of("H1", "V1").getEntries()).join()
        );
    }

    @Test
    @Order(4)
    void Should_mock_exact_request_with_body() {
        final HttpRequestMock httpRequestMock = new HttpRequestMock.Builder()
                .setMethod(HttpMethod.POST)
                .setPath("/post")
                .setHeaders(HttpHeaders.of("H1", "V1"))
                .setBody("<HTTP-BODY>")
                .build();

        httpClientMethodMocker.mock(httpRequestMock, client, new ResponseModel(httpResponseMock), false);

        assertSame(
                expected,
                client.sendAsync("POST", "/post", HttpHeaders.of("H1", "V1").getEntries(), "<HTTP-BODY>").join()
        );
    }
}
