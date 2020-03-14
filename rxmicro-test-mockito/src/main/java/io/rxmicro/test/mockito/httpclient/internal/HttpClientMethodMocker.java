/*
 * Copyright (c) 2020. http://rxmicro.io
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

import io.rxmicro.common.ImpossibleException;
import io.rxmicro.http.client.HttpClient;
import io.rxmicro.http.client.HttpClientContentConverter;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.mockito.httpclient.HttpRequestMock;
import io.rxmicro.test.mockito.httpclient.internal.matchers.BodyArgumentMatcher;
import io.rxmicro.test.mockito.httpclient.internal.matchers.HeadersArgumentMatcher;
import io.rxmicro.test.mockito.httpclient.internal.matchers.UrlPathArgumentMatcher;
import io.rxmicro.test.mockito.httpclient.internal.model.ResponseModel;
import org.mockito.invocation.InvocationOnMock;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;

import static io.rxmicro.http.QueryParams.joinPath;
import static io.rxmicro.test.mockito.httpclient.internal.AnyValues.ANY_BODY;
import static io.rxmicro.test.mockito.httpclient.internal.AnyValues.ANY_HEADERS;
import static io.rxmicro.test.mockito.httpclient.internal.AnyValues.ANY_METHOD;
import static io.rxmicro.test.mockito.httpclient.internal.AnyValues.ANY_URL_PATH;
import static io.rxmicro.test.mockito.internal.CommonMatchers.any;
import static io.rxmicro.test.mockito.internal.CommonMatchers.anyList;
import static io.rxmicro.test.mockito.internal.CommonMatchers.anyString;
import static io.rxmicro.test.mockito.internal.CommonMatchers.equal;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.concurrent.CompletableFuture.failedFuture;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class HttpClientMethodMocker {

    private final HttpClientMockLogger mockLogger = new HttpClientMockLogger();

    public void mock(final HttpRequestMock httpRequestMock,
                     final HttpClient client,
                     final ResponseModel responseModel,
                     final boolean logMockParams) {
        if (httpRequestMock.isAny()) {
            lenient()
                    .when(client.sendAsync(
                            mockMethod(httpRequestMock),
                            mockPath(httpRequestMock),
                            mockHeaders(httpRequestMock))
                    )
                    .thenAnswer(invocation -> createCompletableFuture(responseModel, invocation));
            lenient()
                    .when(client.sendAsync(
                            mockMethod(httpRequestMock),
                            mockPath(httpRequestMock),
                            mockHeaders(httpRequestMock),
                            mockBody(httpRequestMock))
                    )
                    .thenAnswer(invocation -> createCompletableFuture(responseModel, invocation));
        } else if (httpRequestMock.isBodyPresent()) {
            when(client.sendAsync(
                    mockMethod(httpRequestMock),
                    mockPath(httpRequestMock),
                    mockHeaders(httpRequestMock),
                    mockBody(httpRequestMock))
            ).thenAnswer(invocation -> createCompletableFuture(responseModel, invocation));
        } else {
            when(client.sendAsync(
                    mockMethod(httpRequestMock),
                    mockPath(httpRequestMock),
                    mockHeaders(httpRequestMock))
            ).thenAnswer(invocation -> createCompletableFuture(responseModel, invocation));
        }
        if (logMockParams) {
            mockLogger.log(httpRequestMock, responseModel);
        }
    }

    private Object createCompletableFuture(final ResponseModel responseModel,
                                           final InvocationOnMock invocation) {
        return responseModel.getHttpResponseMock()
                .map(r -> {
                    if (r.isReturnRequestBody()) {
                        final Object requestBody = invocation.getArgument(3);
                        final Object responseBody = getResponseBody(requestBody);
                        return completedFuture(r.getClientHttpResponse(responseBody));
                    } else {
                        return completedFuture(r.getClientHttpResponse());
                    }
                })
                .orElseGet(() -> failedFuture(responseModel.getThrowable().orElseThrow()));
    }

    private Object getResponseBody(final Object requestBody) {
        final HttpClientContentConverter httpClientContentConverter =
                ServiceLoader.load(HttpClientContentConverter.class)
                        .findFirst()
                        .orElseThrow(() -> {
                            throw new ImpossibleException(
                                    "Implementation of '?' not found for test environment!",
                                    HttpClientContentConverter.class.getName()
                            );
                        });
        final byte[] content = httpClientContentConverter.getRequestContentConverter().apply(requestBody);
        return httpClientContentConverter.getResponseContentConverter().apply(content);
    }

    private String mockMethod(final HttpRequestMock httpRequestMock) {
        if (httpRequestMock.getMethod().isPresent()) {
            return equal(httpRequestMock.getMethod().get().name());
        } else {
            return anyString(ANY_METHOD);
        }
    }

    private String mockPath(final HttpRequestMock httpRequestMock) {
        if (httpRequestMock.getPath().isPresent() && httpRequestMock.getQueryParameters().isPresent()) {
            final String path = joinPath(httpRequestMock.getPath().get(), httpRequestMock.getQueryParameters().get());
            return equal(new UrlPathArgumentMatcher(path), path);
        } else if (httpRequestMock.getPath().isPresent()) {
            final String path = httpRequestMock.getPath().get();
            return equal(new UrlPathArgumentMatcher(path), path);
        } else if (httpRequestMock.getQueryParameters().isPresent()) {
            throw new InvalidTestConfigException("Set 'URL Path'!");
        } else {
            return anyString(ANY_URL_PATH);
        }
    }

    private List<Map.Entry<String, String>> mockHeaders(final HttpRequestMock httpRequestMock) {
        if (httpRequestMock.getHeaders().isPresent()) {
            final List<Map.Entry<String, String>> headers = httpRequestMock.getHeaders().get().getEntries();
            return equal(new HeadersArgumentMatcher(headers), headers);
        } else {
            return anyList(ANY_HEADERS);
        }
    }

    private Object mockBody(final HttpRequestMock httpRequestMock) {
        if (httpRequestMock.isBodyPresent()) {
            final Optional<Object> bodyOptional = httpRequestMock.getBody();
            if (bodyOptional.isPresent()) {
                final Object value = bodyOptional.get();
                return equal(new BodyArgumentMatcher(value), value);
            } else {
                return any(Object.class, ANY_BODY);
            }
        } else if (httpRequestMock.isAny()) {
            return any(Object.class, ANY_BODY);
        } else {
            return null;
        }
    }
}
