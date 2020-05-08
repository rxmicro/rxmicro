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

import io.rxmicro.http.client.HttpClient;
import io.rxmicro.http.client.HttpClientFactory;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.mockito.httpclient.HttpRequestMock;
import io.rxmicro.test.mockito.httpclient.HttpResponseMock;
import io.rxmicro.test.mockito.httpclient.internal.model.ResponseModel;

import java.util.Set;

import static io.rxmicro.rest.model.HttpMethod.DELETE;
import static io.rxmicro.rest.model.HttpMethod.GET;
import static io.rxmicro.rest.model.HttpMethod.HEAD;
import static io.rxmicro.rest.model.HttpMethod.OPTIONS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.MockUtil.isMock;

/**
 * @author nedis
 * @since 0.1
 */
public final class InternalHttpClientMockFactory {

    private final static HttpClientMethodMocker HTTP_CLIENT_METHOD_MOCKER = new HttpClientMethodMocker();

    public HttpClient prepare(final HttpClientFactory httpClientFactory,
                              final HttpRequestMock httpRequestMock,
                              final Throwable throwable,
                              final boolean logMockParams) {
        return prepare(
                httpClientFactory,
                httpRequestMock,
                new ResponseModel(throwable),
                logMockParams
        );
    }

    public HttpClient prepare(final HttpClientFactory httpClientFactory,
                              final HttpRequestMock httpRequestMock,
                              final HttpResponseMock httpResponseMock,
                              final boolean logMockParams) {
        return prepare(
                httpClientFactory,
                httpRequestMock,
                new ResponseModel(httpResponseMock),
                logMockParams
        );
    }

    private HttpClient prepare(final HttpClientFactory httpClientFactory,
                               final HttpRequestMock httpRequestMock,
                               final ResponseModel responseModel,
                               final boolean logMockParams) {
        validate(httpClientFactory, httpRequestMock);
        final HttpClient client = mock(HttpClient.class);
        HTTP_CLIENT_METHOD_MOCKER.mock(httpRequestMock, client, responseModel, logMockParams);
        when(httpClientFactory.create(any(), any(), any(), any())).thenReturn(client);
        return client;
    }

    private void validate(final HttpClientFactory httpClientFactory,
                          final HttpRequestMock httpRequestMock) {
        if (!isMock(httpClientFactory)) {
            throw new InvalidTestConfigException(
                    "'httpClientFactory' must be a Mockito mock!"
            );
        }
        if (httpRequestMock.isBodyPresent()) {
            if (httpRequestMock.getQueryParameters().isPresent() &&
                    !httpRequestMock.getQueryParameters().get().getEntries().isEmpty()) {
                throw new InvalidTestConfigException(
                        "Query parameters with body not supported. " +
                                "Remove query parameters!"
                );
            }
            if (httpRequestMock.getMethod().isPresent() &&
                    Set.of(GET, DELETE, HEAD, OPTIONS).contains(httpRequestMock.getMethod().get())) {
                throw new InvalidTestConfigException(
                        "'?' HTTP method does not support HTTP body",
                        httpRequestMock.getMethod().get()
                );
            }
        }
    }
}
