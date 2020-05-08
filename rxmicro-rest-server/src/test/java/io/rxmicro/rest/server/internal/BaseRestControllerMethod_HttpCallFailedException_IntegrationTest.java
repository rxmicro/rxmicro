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

package io.rxmicro.rest.server.internal;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.logger.Logger;
import io.rxmicro.rest.model.HttpCallFailedException;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.http.HttpHeaders.of;
import static io.rxmicro.http.HttpStandardHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN;
import static io.rxmicro.http.HttpVersion.HTTP_1_1;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 *
 * @since 0.1
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
final class BaseRestControllerMethod_HttpCallFailedException_IntegrationTest extends AbstractBaseRestControllerMethodTest {

    @Mock
    private PathVariableMapping pathVariableMapping;

    @Mock
    private HttpRequest request;

    @Mock
    private HttpHeaders httpHeaders;

    @Mock
    private HttpResponse httpResponse;

    @Mock
    private Logger logger;

    @BeforeEach
    void beforeEach() {
        setLoggerMock(logger);
    }

    @ParameterizedTest
    @ArgumentsSource(ThrowServerErrorHttpCallFailedExceptionWithBodyArgumentsProvider.class)
    @Order(1)
    void Should_return_all_HttpCallFailedException_data_with_body(
            final BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>> func,
            final HttpCallFailedException httpCallFailedException) {
        when(request.getHeaders()).thenReturn(httpHeaders);
        when(httpResponseBuilder.build(false)).thenReturn(httpResponse);
        when(httpErrorResponseBodyBuilder.build(httpResponse, httpCallFailedException)).thenReturn(httpResponse);
        when(httpErrorResponseBodyBuilder.isRxMicroError(httpCallFailedException)).thenReturn(true);

        final BaseRestControllerMethod method = build(
                "",
                false,
                func);
        final HttpResponse actualResponse = method.call(pathVariableMapping, request).toCompletableFuture().join();

        assertSame(httpResponse, actualResponse);
        verify(httpResponse, never()).setHeader(eq(ACCESS_CONTROL_ALLOW_ORIGIN), anyString());
        verify(logger).error(
                "Http call failed: ?",
                format("500 HTTP/1.1\nHeader1: value1\n\n<body>")
        );
    }

    @ParameterizedTest
    @ArgumentsSource(ThrowServerErrorHttpCallFailedExceptionWithoutBodyArgumentsProvider.class)
    @Order(2)
    void Should_return_all_HttpCallFailedException_data_without_body(
            final BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>> func,
            final HttpCallFailedException httpCallFailedException) {
        when(request.getHeaders()).thenReturn(httpHeaders);
        when(httpResponseBuilder.build(false)).thenReturn(httpResponse);
        when(httpErrorResponseBodyBuilder.build(httpResponse, httpCallFailedException)).thenReturn(httpResponse);
        when(httpErrorResponseBodyBuilder.isRxMicroError(httpCallFailedException)).thenReturn(true);

        final BaseRestControllerMethod method = build(
                "",
                false,
                func);
        final HttpResponse actualResponse = method.call(pathVariableMapping, request).toCompletableFuture().join();

        assertSame(httpResponse, actualResponse);
        verify(httpResponse, never()).setHeader(eq(ACCESS_CONTROL_ALLOW_ORIGIN), anyString());
        verify(logger).error(
                "Http call failed: ?",
                format("500 HTTP/1.1\nHeader1: value1\n\n")
        );
    }

    @ParameterizedTest
    @ArgumentsSource(ThrowServerErrorHttpCallFailedExceptionWithBodyArgumentsProvider.class)
    @Order(3)
    void Should_return_Internal_Server_error(
            final BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>> func) {
        when(request.getHeaders()).thenReturn(httpHeaders);
        when(httpResponseBuilder.build()).thenReturn(httpResponse);
        when(httpErrorResponseBodyBuilder.build(httpResponse, 500, "Internal Server Error")).thenReturn(httpResponse);
        when(restServerConfig.isHideInternalErrorMessage()).thenReturn(true);

        final BaseRestControllerMethod method = build(
                "",
                false,
                func);
        final HttpResponse actualResponse = method.call(pathVariableMapping, request).toCompletableFuture().join();

        assertSame(httpResponse, actualResponse);
        verify(httpResponse, never()).setHeader(eq(ACCESS_CONTROL_ALLOW_ORIGIN), anyString());
        verify(logger).error(
                "Http call failed: ?",
                format("500 HTTP/1.1\nHeader1: value1\n\n<body>")
        );
    }

    @ParameterizedTest
    @ArgumentsSource(ThrowServerErrorHttpCallFailedExceptionWithBodyArgumentsProvider.class)
    @Order(4)
    void Should_return_Server_Error_message_for_not_RxMicro_errors(
            final BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>> func) {
        when(request.getHeaders()).thenReturn(httpHeaders);
        when(httpResponseBuilder.build()).thenReturn(httpResponse);
        when(httpErrorResponseBodyBuilder.build(
                httpResponse,
                500,
                format("500 HTTP/1.1\nHeader1: value1\n\n<body>"))
        ).thenReturn(httpResponse);

        final BaseRestControllerMethod method = build(
                "",
                false,
                func);
        final HttpResponse actualResponse = method.call(pathVariableMapping, request).toCompletableFuture().join();

        assertSame(httpResponse, actualResponse);
        verify(httpResponse, never()).setHeader(eq(ACCESS_CONTROL_ALLOW_ORIGIN), anyString());
        verify(logger).error(
                "Http call failed: ?",
                format("500 HTTP/1.1\nHeader1: value1\n\n<body>")
        );
    }

    @ParameterizedTest
    @ArgumentsSource(ThrowClientErrorHttpCallFailedExceptionWithBodyArgumentsProvider.class)
    @Order(5)
    void Should_return_Client_Error_message_for_not_RxMicro_errors(
            final BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>> func) {
        when(request.getHeaders()).thenReturn(httpHeaders);
        when(httpResponseBuilder.build()).thenReturn(httpResponse);
        when(httpErrorResponseBodyBuilder.build(
                httpResponse,
                400,
                format("400 HTTP/1.1\nHeader1: value1\n\n<body>"))
        ).thenReturn(httpResponse);

        final BaseRestControllerMethod method = build(
                "",
                false,
                func);
        final HttpResponse actualResponse = method.call(pathVariableMapping, request).toCompletableFuture().join();

        assertSame(httpResponse, actualResponse);
        verify(httpResponse, never()).setHeader(eq(ACCESS_CONTROL_ALLOW_ORIGIN), anyString());
    }

    /**
     * @author nedis
     *
     */
    private static class ThrowServerErrorHttpCallFailedExceptionWithBodyArgumentsProvider implements ArgumentsProvider {

        private static final HttpCallFailedException HTTP_CALL_FAILED_EXCEPTION =
                new HttpCallFailedException(500, HTTP_1_1, of("Header1", "value1"), null, "<body>") {

                };

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
            return Stream.of(
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw HTTP_CALL_FAILED_EXCEPTION;
                            },
                            HTTP_CALL_FAILED_EXCEPTION
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw HTTP_CALL_FAILED_EXCEPTION;
                            },
                            HTTP_CALL_FAILED_EXCEPTION
                    )
            );
        }

    }

    /**
     * @author nedis
     *
     */
    private static class ThrowServerErrorHttpCallFailedExceptionWithoutBodyArgumentsProvider implements ArgumentsProvider {

        private static final HttpCallFailedException HTTP_CALL_FAILED_EXCEPTION =
                new HttpCallFailedException(500, HTTP_1_1, of("Header1", "value1"), null, "") {

                };

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
            return Stream.of(
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw HTTP_CALL_FAILED_EXCEPTION;
                            },
                            HTTP_CALL_FAILED_EXCEPTION
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw HTTP_CALL_FAILED_EXCEPTION;
                            },
                            HTTP_CALL_FAILED_EXCEPTION
                    )
            );
        }
    }

    /**
     * @author nedis
     *
     */
    private static class ThrowClientErrorHttpCallFailedExceptionWithBodyArgumentsProvider implements ArgumentsProvider {

        private static final HttpCallFailedException HTTP_CALL_FAILED_EXCEPTION =
                new HttpCallFailedException(400, HTTP_1_1, of("Header1", "value1"), null, "<body>") {

                };

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
            return Stream.of(
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw HTTP_CALL_FAILED_EXCEPTION;
                            },
                            HTTP_CALL_FAILED_EXCEPTION
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw HTTP_CALL_FAILED_EXCEPTION;
                            },
                            HTTP_CALL_FAILED_EXCEPTION
                    )
            );
        }
    }
}