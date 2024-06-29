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
import static io.rxmicro.http.HttpStatuses.BAD_REQUEST_400;
import static io.rxmicro.http.HttpStatuses.INTERNAL_SERVER_ERROR_500;
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
 * @since 0.1
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@SuppressWarnings({"PMD.ProperLogger", "PMD.AvoidDuplicateLiterals"})
final class BaseRestControllerMethodHttpCallFailedExceptionIntegrationTest extends AbstractBaseRestControllerMethodTest {

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
    @ArgumentsSource(ThrowServerErrorHttpCallFailedExceptionArgumentsProvider.class)
    @Order(1)
    void Should_return_all_HttpCallFailedException_data_with_body(
            final BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>> func,
            final HttpCallFailedException httpCallFailedException,
            final String expectedHttpResponsePackage) {
        when(request.getHeaders()).thenReturn(httpHeaders);
        when(httpErrorResponseBodyBuilder.build(httpResponseBuilder, httpCallFailedException)).thenReturn(httpResponse);
        when(httpErrorResponseBodyBuilder.isRxMicroError(httpCallFailedException)).thenReturn(true);

        final BaseRestControllerMethod method = build(
                "",
                false,
                func);
        final HttpResponse actualResponse = method.call(pathVariableMapping, request).toCompletableFuture().join();

        assertSame(httpResponse, actualResponse);
        verify(httpResponse, never()).setHeader(eq(ACCESS_CONTROL_ALLOW_ORIGIN), anyString());
        verify(logger).error(
                request,
                "Http call failed: ?",
                format(expectedHttpResponsePackage)
        );
    }

    @ParameterizedTest
    @ArgumentsSource(ThrowServerErrorHttpCallFailedExceptionWithBodyArgumentsProvider.class)
    @Order(3)
    void Should_return_Internal_Server_error(
            final BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>> func) {
        when(request.getHeaders()).thenReturn(httpHeaders);
        when(httpErrorResponseBodyBuilder.build(httpResponseBuilder, 500, "Internal Server Error")).thenReturn(httpResponse);
        when(restServerConfig.isHideInternalErrorMessage()).thenReturn(true);

        final BaseRestControllerMethod method = build(
                "",
                false,
                func);
        final HttpResponse actualResponse = method.call(pathVariableMapping, request).toCompletableFuture().join();

        assertSame(httpResponse, actualResponse);
        verify(httpResponse, never()).setHeader(eq(ACCESS_CONTROL_ALLOW_ORIGIN), anyString());
        verify(logger).error(
                request,
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
        when(httpErrorResponseBodyBuilder.build(
                httpResponseBuilder,
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
                request,
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
        when(httpErrorResponseBodyBuilder.build(
                httpResponseBuilder,
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
     * @since 0.1
     */
    private static final class ThrowServerErrorHttpCallFailedExceptionWithBodyArgumentsProvider implements ArgumentsProvider {

        private static final HttpCallFailedException HTTP_CALL_FAILED_EXCEPTION =
                new HttpCallFailedException(INTERNAL_SERVER_ERROR_500, HTTP_1_1, of("Header1", "value1"), null, "<body>") {

                    private static final long serialVersionUID = -8963833503627193002L;
                };

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
            return Stream.of(
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw HTTP_CALL_FAILED_EXCEPTION;
                            },
                            HTTP_CALL_FAILED_EXCEPTION,
                            "500 HTTP/1.1\nHeader1: value1\n\n<body>"
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw HTTP_CALL_FAILED_EXCEPTION;
                            },
                            HTTP_CALL_FAILED_EXCEPTION,
                            "500 HTTP/1.1\nHeader1: value1\n\n<body>"
                    )
            );
        }

    }

    /**
     * @author nedis
     * @since 0.1
     */
    private static final class ThrowServerErrorHttpCallFailedExceptionWithoutBodyArgumentsProvider implements ArgumentsProvider {

        private static final HttpCallFailedException HTTP_CALL_FAILED_EXCEPTION =
                new HttpCallFailedException(INTERNAL_SERVER_ERROR_500, HTTP_1_1, of("Header1", "value1"), null, "") {

                    private static final long serialVersionUID = -8555570983424729378L;
                };

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
            return Stream.of(
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw HTTP_CALL_FAILED_EXCEPTION;
                            },
                            HTTP_CALL_FAILED_EXCEPTION,
                            "500 HTTP/1.1\nHeader1: value1\n\n"
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw HTTP_CALL_FAILED_EXCEPTION;
                            },
                            HTTP_CALL_FAILED_EXCEPTION,
                            "500 HTTP/1.1\nHeader1: value1\n\n"
                    )
            );
        }
    }

    /**
     * @author nedis
     * @since 0.1
     */
    private static final class ThrowServerErrorHttpCallFailedExceptionArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
            return Stream.concat(
                    new ThrowServerErrorHttpCallFailedExceptionWithBodyArgumentsProvider().provideArguments(extensionContext),
                    new ThrowServerErrorHttpCallFailedExceptionWithoutBodyArgumentsProvider().provideArguments(extensionContext)
            );
        }
    }

    /**
     * @author nedis
     * @since 0.1
     */
    private static final class ThrowClientErrorHttpCallFailedExceptionWithBodyArgumentsProvider implements ArgumentsProvider {

        private static final HttpCallFailedException HTTP_CALL_FAILED_EXCEPTION =
                new HttpCallFailedException(BAD_REQUEST_400, HTTP_1_1, of("Header1", "value1"), null, "<body>") {

                    private static final long serialVersionUID = 3468094529079669977L;
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
