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

import io.rxmicro.common.InvalidStateException;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.error.HttpErrorException;
import io.rxmicro.http.error.PermanentRedirectException;
import io.rxmicro.http.error.RedirectException;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.http.HttpStandardHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN;
import static io.rxmicro.http.HttpStandardHeaderNames.LOCATION;
import static io.rxmicro.http.HttpStatuses.TEMPORARY_REDIRECT_307;
import static java.util.concurrent.CompletableFuture.failedStage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
final class BaseRestControllerMethodRedirectIntegrationTest extends AbstractBaseRestControllerMethodTest {

    @Mock
    private PathVariableMapping pathVariableMapping;

    @Mock
    private HttpRequest request;

    @Mock
    private HttpHeaders httpHeaders;

    @Mock
    private HttpResponse httpResponse;

    @ParameterizedTest
    @ArgumentsSource(ThrowRedirectExceptionArgumentsProvider.class)
    @Order(1)
    void Should_return_redirect_response(
            final BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>> func,
            final String parentUrl,
            final String expectedRedirectPath) {
        when(request.getHeaders()).thenReturn(httpHeaders);
        when(httpResponseBuilder.build()).thenReturn(httpResponse);

        final BaseRestControllerMethod method = build(
                parentUrl,
                false,
                func);
        final HttpResponse actualResponse = method.call(pathVariableMapping, request).toCompletableFuture().join();

        assertSame(httpResponse, actualResponse);
        verify(httpResponse).setStatus(PermanentRedirectException.STATUS_CODE);
        verify(httpResponse).setHeader(LOCATION, expectedRedirectPath);
        verify(httpResponse, never()).setHeader(eq(ACCESS_CONTROL_ALLOW_ORIGIN), anyString());
    }

    @Test
    @Order(2)
    void Should_throw_Exception_if_User_exception_class_does_not_extend_RedirectException() {
        when(request.getHeaders()).thenReturn(httpHeaders);

        final CompletionException exception = assertThrows(CompletionException.class, () -> {
            final BaseRestControllerMethod method = build(
                    "",
                    false,
                    (pv, req) -> failedStage(new UserRedirectException()));
            method.call(pathVariableMapping, request).toCompletableFuture().join();
        });
        assertEquals(InvalidStateException.class, exception.getCause().getClass());
        assertEquals(
                format("Class '?' must extend '?' class!",
                        UserRedirectException.class.getName(),
                        RedirectException.class.getName()),
                exception.getCause().getMessage());
    }

    /**
     * @author nedis
     *
     */
    @SuppressWarnings({"CodeBlock2Expr", "PMD.AvoidDuplicateLiterals"})
    private static class ThrowRedirectExceptionArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
            return Stream.of(
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw new PermanentRedirectException("/new-location");
                            },
                            "",
                            "/new-location"
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                return failedStage(new PermanentRedirectException("/new-location"));
                            },
                            "",
                            "/new-location"
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw new PermanentRedirectException("new-location");
                            },
                            "",
                            "/new-location"
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                return failedStage(new PermanentRedirectException("new-location"));
                            },
                            "",
                            "/new-location"
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw new PermanentRedirectException("/new-location");
                            },
                            "/parent",
                            "/parent/new-location"
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                return failedStage(new PermanentRedirectException("/new-location"));
                            },
                            "/parent",
                            "/parent/new-location"
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw new PermanentRedirectException("new-location");
                            },
                            "/parent",
                            "/parent/new-location"
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                return failedStage(new PermanentRedirectException("new-location"));
                            },
                            "/parent",
                            "/parent/new-location"
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw new PermanentRedirectException("/parent/new-location");
                            },
                            "/parent",
                            "/parent/new-location"
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                return failedStage(new PermanentRedirectException("/parent/new-location"));
                            },
                            "/parent",
                            "/parent/new-location"
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw new PermanentRedirectException("https://rxmicro.io");
                            },
                            "",
                            "https://rxmicro.io"
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                return failedStage(new PermanentRedirectException("https://rxmicro.io"));
                            },
                            "",
                            "https://rxmicro.io"
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                throw new PermanentRedirectException("https://rxmicro.io");
                            },
                            "/parent",
                            "https://rxmicro.io"
                    ),
                    arguments(
                            (BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>>) (pathVariableMapping, httpRequest) -> {
                                return failedStage(new PermanentRedirectException("https://rxmicro.io"));
                            },
                            "/parent",
                            "https://rxmicro.io"
                    )
            );
        }
    }

    /**
     * @author nedis
     *
     */
    private static final class UserRedirectException extends HttpErrorException {

        private static final long serialVersionUID = 7175277337376519437L;

        private UserRedirectException() {
            super(TEMPORARY_REDIRECT_307);
        }
    }
}
