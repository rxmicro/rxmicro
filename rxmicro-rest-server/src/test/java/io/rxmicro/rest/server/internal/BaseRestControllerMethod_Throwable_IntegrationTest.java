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
import io.rxmicro.http.error.InternalHttpErrorException;
import io.rxmicro.logger.Logger;
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

import static io.rxmicro.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static java.util.concurrent.CompletableFuture.failedStage;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
final class BaseRestControllerMethod_Throwable_IntegrationTest extends AbstractBaseRestControllerMethodTest {

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
    @ArgumentsSource(ThrowAnyExceptionArgumentsProvider.class)
    @Order(1)
    void Should_return_InternalServerError_response_with_body(
            final BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>> func,
            final boolean hideInternalErrorMessage,
            final String expectedMessage,
            final Throwable throwable) {
        when(request.getHeaders()).thenReturn(httpHeaders);
        when(httpResponseBuilder.build()).thenReturn(httpResponse);
        when(httpErrorResponseBodyBuilder.build(any(), anyInt(), anyString())).thenReturn(httpResponse);
        when(restServerConfig.isHideInternalErrorMessage()).thenReturn(hideInternalErrorMessage);

        final BaseRestControllerMethod method = build(
                "",
                false,
                func);
        //setFieldValue(ThrowableHttpResponseBuilder.class, "LOGGER", logger);
        final HttpResponse actualResponse = method.call(pathVariableMapping, request).toCompletableFuture().join();

        assertSame(httpResponse, actualResponse);
        verify(httpErrorResponseBodyBuilder).build(httpResponse, InternalHttpErrorException.STATUS_CODE, expectedMessage);
        verify(httpResponse, never()).setHeader(eq(ACCESS_CONTROL_ALLOW_ORIGIN), anyString());
        verify(logger).error(throwable, "Internal server error: ?", throwable.getMessage());
    }

    @ParameterizedTest
    @ArgumentsSource(ThrowAnyExceptionArgumentsProvider.class)
    @Order(2)
    void Should_return_InternalServerError_response_without_body(
            final BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>> func,
            final boolean hideInternalErrorMessage,
            final String expectedMessage,
            final Throwable throwable) {
        when(request.getHeaders()).thenReturn(httpHeaders);
        when(httpResponseBuilder.build()).thenReturn(httpResponse);
        when(httpErrorResponseBodyBuilder.build(any(), anyInt(), anyString())).thenReturn(httpResponse);
        when(restServerConfig.isHideInternalErrorMessage()).thenReturn(hideInternalErrorMessage);

        final BaseRestControllerMethod method = build(
                "",
                false,
                func);
        //setFieldValue(ThrowableHttpResponseBuilder.class, "LOGGER", logger);
        final HttpResponse actualResponse = method.call(pathVariableMapping, request).toCompletableFuture().join();

        assertSame(httpResponse, actualResponse);
        verify(httpResponse, never()).setHeader(eq(ACCESS_CONTROL_ALLOW_ORIGIN), anyString());
        verify(logger).error(throwable, "Internal server error: ?", throwable.getMessage());
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     */
    @SuppressWarnings("CodeBlock2Expr")
    private static class ThrowAnyExceptionArgumentsProvider implements ArgumentsProvider {

        private static final NullPointerException NULL_POINTER_EXCEPTION = new NullPointerException();

        private static final IllegalArgumentException ILLEGAL_ARGUMENT_EXCEPTION = new IllegalArgumentException("Invalid argument");

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
            return Stream.of(
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw NULL_POINTER_EXCEPTION;
                            },
                            false,
                            "NullPointerException",
                            NULL_POINTER_EXCEPTION
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                return failedStage(NULL_POINTER_EXCEPTION);
                            },
                            false,
                            "NullPointerException",
                            NULL_POINTER_EXCEPTION
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw ILLEGAL_ARGUMENT_EXCEPTION;
                            },
                            false,
                            "Invalid argument",
                            ILLEGAL_ARGUMENT_EXCEPTION
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                return failedStage(ILLEGAL_ARGUMENT_EXCEPTION);
                            },
                            false,
                            "Invalid argument",
                            ILLEGAL_ARGUMENT_EXCEPTION
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw NULL_POINTER_EXCEPTION;
                            },
                            true,
                            "Internal Server Error",
                            NULL_POINTER_EXCEPTION
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                return failedStage(NULL_POINTER_EXCEPTION);
                            },
                            true,
                            "Internal Server Error",
                            NULL_POINTER_EXCEPTION
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw ILLEGAL_ARGUMENT_EXCEPTION;
                            },
                            true,
                            "Internal Server Error",
                            ILLEGAL_ARGUMENT_EXCEPTION
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                return failedStage(ILLEGAL_ARGUMENT_EXCEPTION);
                            },
                            true,
                            "Internal Server Error",
                            ILLEGAL_ARGUMENT_EXCEPTION
                    )
            );
        }
    }
}