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

package io.rxmicro.test.mockito.httpclient;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.client.HttpClient;
import io.rxmicro.http.client.HttpClientFactory;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.mockito.httpclient.internal.InternalHttpClientMockFactory;

import java.util.Optional;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class HttpClientMockFactory {

    private static final InternalHttpClientMockFactory MOCK_FACTORY = new InternalHttpClientMockFactory();

    private static HttpClient httpClientMock;

    public static HttpClient getPreparedHttpClientMock() {
        return Optional.ofNullable(httpClientMock).orElseThrow(() -> {
            throw new InvalidTestConfigException("Use 'prepareHttpClientMock' method before 'getPreparedHttpClientMock'!");
        });
    }

    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock) {
        return httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                new HttpResponseMock.Builder()
                        .build(),
                false
        );
    }

    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final boolean logMockParams) {
        return httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                new HttpResponseMock.Builder()
                        .build(),
                logMockParams
        );
    }

    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final int statusCode) {
        return httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                new HttpResponseMock.Builder()
                        .setStatus(statusCode)
                        .build(),
                false
        );
    }

    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final int statusCode,
                                                   final boolean logMockParams) {
        return httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                new HttpResponseMock.Builder()
                        .setStatus(statusCode)
                        .build(),
                logMockParams
        );
    }

    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final HttpHeaders responseHeaders) {
        return httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                new HttpResponseMock.Builder()
                        .setHeaders(responseHeaders)
                        .build(),
                false
        );
    }

    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final HttpHeaders responseHeaders,
                                                   final boolean logMockParams) {
        return httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                new HttpResponseMock.Builder()
                        .setHeaders(responseHeaders)
                        .build(),
                logMockParams
        );
    }

    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final Object responseBody) {
        return httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                new HttpResponseMock.Builder()
                        .setBody(responseBody)
                        .build(),
                false
        );
    }

    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final Object responseBody,
                                                   final boolean logMockParams) {
        return httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                new HttpResponseMock.Builder()
                        .setBody(responseBody)
                        .build(),
                logMockParams
        );
    }

    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final HttpResponseMock httpResponseMock) {
        return httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                httpResponseMock,
                false
        );
    }

    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final HttpResponseMock httpResponseMock,
                                                   final boolean logMockParams) {
        return httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                httpResponseMock,
                logMockParams
        );
    }

    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final Throwable throwable) {
        return httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                throwable,
                false
        );
    }

    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final Throwable throwable,
                                                   final boolean logMockParams) {
        return httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                throwable,
                logMockParams
        );
    }

    private HttpClientMockFactory() {
    }
}
