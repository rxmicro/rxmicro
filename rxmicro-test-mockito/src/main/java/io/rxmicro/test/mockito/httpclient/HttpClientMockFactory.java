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

package io.rxmicro.test.mockito.httpclient;

import io.rxmicro.config.Secrets;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.client.HttpClient;
import io.rxmicro.http.client.HttpClientConfig;
import io.rxmicro.http.client.HttpClientContentConverter;
import io.rxmicro.http.client.HttpClientFactory;
import io.rxmicro.test.local.InvalidTestConfigException;
import io.rxmicro.test.mockito.httpclient.internal.InternalHttpClientMockFactory;

import java.util.Optional;

/**
 * Helper class with useful static methods that help to configure a {@link HttpClient} mock.
 * <p>
 * This class must be used for testing purposes
 *
 * @author nedis
 * @since 0.1
 */
public final class HttpClientMockFactory {

    private static final InternalHttpClientMockFactory MOCK_FACTORY = new InternalHttpClientMockFactory();

    private static HttpClient httpClientMock;

    /**
     * Returns the last prepared mock of the {@link HttpClient}
     *
     * @return the last prepared mock of the {@link HttpClient}
     * @throws InvalidTestConfigException if the last prepared mock not defined
     */
    public static HttpClient getPreparedHttpClientMock() {
        return Optional.ofNullable(httpClientMock).orElseThrow(() -> {
            throw new InvalidTestConfigException("Use 'prepareHttpClientMock' method before 'getPreparedHttpClientMock'!");
        });
    }

    /**
     * Prepares a new instance of the {@link HttpClient} mock.
     * This mock will be returned by the specified {@link HttpClientFactory} if the RxMicro framework will invoke
     * the {@link HttpClientFactory#create(Class, HttpClientConfig, Secrets, HttpClientContentConverter)} method.
     * The empty HTTP response with {@code 200} status code and without additional HTTP headers will be returned
     * if the RxMicro framework will pass the specified {@link HttpRequestMock} to this mock.
     * <p>
     * (<i>This method requires that {@link HttpClientFactory} will be a mock!</i>)
     *
     * @param httpClientFactory the specified HTTP client factory
     * @param httpRequestMock the specified HTTP request mock
     * @return the prepared instance of the {@link HttpClient} mock
     * @throws InvalidTestConfigException if the specified parameters are invalid
     */
    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock) {
        httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                new HttpResponseMock.Builder()
                        .build(),
                false
        );
        return httpClientMock;
    }

    /**
     * Prepares a new instance of the {@link HttpClient} mock.
     * This mock will be returned by the specified {@link HttpClientFactory} if the RxMicro framework will invoke
     * the {@link HttpClientFactory#create(Class, HttpClientConfig, Secrets, HttpClientContentConverter)} method.
     * The empty HTTP response with {@code 200} status code and without additional HTTP headers will be returned
     * if the RxMicro framework will pass the specified {@link HttpRequestMock} to this mock.
     * <p>
     * (<i>This method requires that {@link HttpClientFactory} will be a mock!</i>)
     *
     * @param httpClientFactory the specified HTTP client factory
     * @param httpRequestMock the specified HTTP request mock
     * @param logMockParams whether or not the logging of mock parameters is enabled or disabled
     * @return the prepared instance of the {@link HttpClient} mock
     * @throws InvalidTestConfigException if the specified parameters are invalid
     */
    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final boolean logMockParams) {
        httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                new HttpResponseMock.Builder()
                        .build(),
                logMockParams
        );
        return httpClientMock;
    }

    /**
     * Prepares a new instance of the {@link HttpClient} mock.
     * This mock will be returned by the specified {@link HttpClientFactory} if the RxMicro framework will invoke
     * the {@link HttpClientFactory#create(Class, HttpClientConfig, Secrets, HttpClientContentConverter)} method.
     * The empty HTTP response with the specified status code and without additional HTTP headers will be returned
     * if the RxMicro framework will pass the specified {@link HttpRequestMock} to this mock.
     * <p>
     * (<i>This method requires that {@link HttpClientFactory} will be a mock!</i>)
     *
     * @param httpClientFactory the specified HTTP client factory
     * @param httpRequestMock the specified HTTP request mock
     * @param statusCode the specified status code for the returning HTTP response
     * @return the prepared instance of the {@link HttpClient} mock
     * @throws InvalidTestConfigException if the specified parameters are invalid
     */
    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final int statusCode) {
        httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                new HttpResponseMock.Builder()
                        .setStatus(statusCode)
                        .build(),
                false
        );
        return httpClientMock;
    }

    /**
     * Prepares a new instance of the {@link HttpClient} mock.
     * This mock will be returned by the specified {@link HttpClientFactory} if the RxMicro framework will invoke
     * the {@link HttpClientFactory#create(Class, HttpClientConfig, Secrets, HttpClientContentConverter)} method.
     * The empty HTTP response with the specified status code and without additional HTTP headers will be returned
     * if the RxMicro framework will pass the specified {@link HttpRequestMock} to this mock.
     * <p>
     * (<i>This method requires that {@link HttpClientFactory} will be a mock!</i>)
     *
     * @param httpClientFactory the specified HTTP client factory
     * @param httpRequestMock the specified HTTP request mock
     * @param statusCode the specified status code for the returning HTTP response
     * @param logMockParams whether or not the logging of mock parameters is enabled or disabled
     * @return the prepared instance of the {@link HttpClient} mock
     * @throws InvalidTestConfigException if the specified parameters are invalid
     */
    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final int statusCode,
                                                   final boolean logMockParams) {
        httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                new HttpResponseMock.Builder()
                        .setStatus(statusCode)
                        .build(),
                logMockParams
        );
        return httpClientMock;
    }

    /**
     * Prepares a new instance of the {@link HttpClient} mock.
     * This mock will be returned by the specified {@link HttpClientFactory} if the RxMicro framework will invoke
     * the {@link HttpClientFactory#create(Class, HttpClientConfig, Secrets, HttpClientContentConverter)} method.
     * The empty HTTP response with with {@code 200} status code and specified HTTP headers will be returned
     * if the RxMicro framework will pass the specified {@link HttpRequestMock} to this mock.
     * <p>
     * (<i>This method requires that {@link HttpClientFactory} will be a mock!</i>)
     *
     * @param httpClientFactory the specified HTTP client factory
     * @param httpRequestMock the specified HTTP request mock
     * @param responseHeaders the specified HTTP headers for the returning HTTP response
     * @return the prepared instance of the {@link HttpClient} mock
     * @throws InvalidTestConfigException if the specified parameters are invalid
     */
    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final HttpHeaders responseHeaders) {
        httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                new HttpResponseMock.Builder()
                        .setHeaders(responseHeaders)
                        .build(),
                false
        );
        return httpClientMock;
    }

    /**
     * Prepares a new instance of the {@link HttpClient} mock.
     * This mock will be returned by the specified {@link HttpClientFactory} if the RxMicro framework will invoke
     * the {@link HttpClientFactory#create(Class, HttpClientConfig, Secrets, HttpClientContentConverter)} method.
     * The empty HTTP response with with {@code 200} status code and specified HTTP headers will be returned
     * if the RxMicro framework will pass the specified {@link HttpRequestMock} to this mock.
     * <p>
     * (<i>This method requires that {@link HttpClientFactory} will be a mock!</i>)
     *
     * @param httpClientFactory the specified HTTP client factory
     * @param httpRequestMock the specified HTTP request mock
     * @param responseHeaders the specified HTTP headers for the returning HTTP response
     * @param logMockParams whether or not the logging of mock parameters is enabled or disabled
     * @return the prepared instance of the {@link HttpClient} mock
     * @throws InvalidTestConfigException if the specified parameters are invalid
     */
    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final HttpHeaders responseHeaders,
                                                   final boolean logMockParams) {
        httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                new HttpResponseMock.Builder()
                        .setHeaders(responseHeaders)
                        .build(),
                logMockParams
        );
        return httpClientMock;
    }

    /**
     * Prepares a new instance of the {@link HttpClient} mock.
     * This mock will be returned by the specified {@link HttpClientFactory} if the RxMicro framework will invoke
     * the {@link HttpClientFactory#create(Class, HttpClientConfig, Secrets, HttpClientContentConverter)} method.
     * The HTTP response with with {@code 200} status code, without additional HTTP headers and specified HTTP body will be returned
     * if the RxMicro framework will pass the specified {@link HttpRequestMock} to this mock.
     * <p>
     * (<i>This method requires that {@link HttpClientFactory} will be a mock!</i>)
     *
     * @param httpClientFactory the specified HTTP client factory
     * @param httpRequestMock the specified HTTP request mock
     * @param responseBody the specified HTTP body for the returning HTTP response
     * @return the prepared instance of the {@link HttpClient} mock
     * @throws InvalidTestConfigException if the specified parameters are invalid
     */
    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final Object responseBody) {
        httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                new HttpResponseMock.Builder()
                        .setBody(responseBody)
                        .build(),
                false
        );
        return httpClientMock;
    }

    /**
     * Prepares a new instance of the {@link HttpClient} mock.
     * This mock will be returned by the specified {@link HttpClientFactory} if the RxMicro framework will invoke
     * the {@link HttpClientFactory#create(Class, HttpClientConfig, Secrets, HttpClientContentConverter)} method.
     * The HTTP response with with {@code 200} status code, without additional HTTP headers and specified HTTP body will be returned
     * if the RxMicro framework will pass the specified {@link HttpRequestMock} to this mock.
     * <p>
     * (<i>This method requires that {@link HttpClientFactory} will be a mock!</i>)
     *
     * @param httpClientFactory the specified HTTP client factory
     * @param httpRequestMock the specified HTTP request mock
     * @param responseBody the specified HTTP body for the returning HTTP response
     * @param logMockParams whether or not the logging of mock parameters is enabled or disabled
     * @return the prepared instance of the {@link HttpClient} mock
     * @throws InvalidTestConfigException if the specified parameters are invalid
     */
    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final Object responseBody,
                                                   final boolean logMockParams) {
        httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                new HttpResponseMock.Builder()
                        .setBody(responseBody)
                        .build(),
                logMockParams
        );
        return httpClientMock;
    }

    /**
     * Prepares a new instance of the {@link HttpClient} mock.
     * This mock will be returned by the specified {@link HttpClientFactory} if the RxMicro framework will invoke
     * the {@link HttpClientFactory#create(Class, HttpClientConfig, Secrets, HttpClientContentConverter)} method.
     * The specified HTTP response mock will be returned
     * if the RxMicro framework will pass the specified {@link HttpRequestMock} to this mock.
     * <p>
     * (<i>This method requires that {@link HttpClientFactory} will be a mock!</i>)
     *
     * @param httpClientFactory the specified HTTP client factory
     * @param httpRequestMock the specified HTTP request mock
     * @param httpResponseMock the specified HTTP response mock
     * @return the prepared instance of the {@link HttpClient} mock
     * @throws InvalidTestConfigException if the specified parameters are invalid
     */
    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final HttpResponseMock httpResponseMock) {
        httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                httpResponseMock,
                false
        );
        return httpClientMock;
    }

    /**
     * Prepares a new instance of the {@link HttpClient} mock.
     * This mock will be returned by the specified {@link HttpClientFactory} if the RxMicro framework will invoke
     * the {@link HttpClientFactory#create(Class, HttpClientConfig, Secrets, HttpClientContentConverter)} method.
     * The specified HTTP response mock will be returned
     * if the RxMicro framework will pass the specified {@link HttpRequestMock} to this mock.
     * <p>
     * (<i>This method requires that {@link HttpClientFactory} will be a mock!</i>)
     *
     * @param httpClientFactory the specified HTTP client factory
     * @param httpRequestMock the specified HTTP request mock
     * @param httpResponseMock the specified HTTP response mock
     * @param logMockParams whether or not the logging of mock parameters is enabled or disabled
     * @return the prepared instance of the {@link HttpClient} mock
     * @throws InvalidTestConfigException if the specified parameters are invalid
     */
    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final HttpResponseMock httpResponseMock,
                                                   final boolean logMockParams) {
        httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                httpResponseMock,
                logMockParams
        );
        return httpClientMock;
    }

    /**
     * Prepares a new instance of the {@link HttpClient} mock.
     * This mock will be returned by the specified {@link HttpClientFactory} if the RxMicro framework will invoke
     * the {@link HttpClientFactory#create(Class, HttpClientConfig, Secrets, HttpClientContentConverter)} method.
     * The error signal with the specified {@link Throwable} will be returned
     * if the RxMicro framework will pass the specified {@link HttpRequestMock} to this mock.
     * <p>
     * (<i>This method requires that {@link HttpClientFactory} will be a mock!</i>)
     *
     * @param httpClientFactory the specified HTTP client factory
     * @param httpRequestMock the specified HTTP request mock
     * @param throwable the specified throwable
     * @return the prepared instance of the {@link HttpClient} mock
     * @throws InvalidTestConfigException if the specified parameters are invalid
     */
    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final Throwable throwable) {
        httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                throwable,
                false
        );
        return httpClientMock;
    }

    /**
     * Prepares a new instance of the {@link HttpClient} mock.
     * This mock will be returned by the specified {@link HttpClientFactory} if the RxMicro framework will invoke
     * the {@link HttpClientFactory#create(Class, HttpClientConfig, Secrets, HttpClientContentConverter)} method.
     * The error signal with the specified {@link Throwable} will be returned
     * if the RxMicro framework will pass the specified {@link HttpRequestMock} to this mock.
     * <p>
     * (<i>This method requires that {@link HttpClientFactory} will be a mock!</i>)
     *
     * @param httpClientFactory the specified HTTP client factory
     * @param httpRequestMock the specified HTTP request mock
     * @param throwable the specified throwable
     * @param logMockParams whether or not the logging of mock parameters is enabled or disabled
     * @return the prepared instance of the {@link HttpClient} mock
     * @throws InvalidTestConfigException if the specified parameters are invalid
     */
    public static HttpClient prepareHttpClientMock(final HttpClientFactory httpClientFactory,
                                                   final HttpRequestMock httpRequestMock,
                                                   final Throwable throwable,
                                                   final boolean logMockParams) {
        httpClientMock = MOCK_FACTORY.prepare(
                httpClientFactory,
                httpRequestMock,
                throwable,
                logMockParams
        );
        return httpClientMock;
    }

    private HttpClientMockFactory() {
    }
}
