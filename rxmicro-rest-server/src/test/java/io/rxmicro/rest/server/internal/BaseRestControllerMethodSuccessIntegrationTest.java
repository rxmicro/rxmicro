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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.rxmicro.http.HttpStandardHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN;
import static io.rxmicro.http.HttpStandardHeaderNames.ORIGIN;
import static java.util.concurrent.CompletableFuture.completedStage;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
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
final class BaseRestControllerMethodSuccessIntegrationTest extends AbstractBaseRestControllerMethodTest {

    @Mock
    private PathVariableMapping pathVariableMapping;

    @Mock
    private HttpRequest request;

    @Mock
    private HttpHeaders httpHeaders;

    @Mock
    private HttpResponse httpResponse;

    @Test
    @Order(1)
    void Should_invoke_successful_if_cors_is_not_possible() {
        when(request.getHeaders()).thenReturn(httpHeaders);

        final BaseRestControllerMethod method = build(
                "",
                false,
                (pvm, req) -> completedStage(httpResponse));
        final HttpResponse actualResponse = method.call(pathVariableMapping, request).toCompletableFuture().join();

        assertSame(httpResponse, actualResponse);
        verify(httpResponse, never()).setHeader(anyString(), anyString());
    }

    @Test
    @Order(2)
    void Should_invoke_successful_if_cors_is_possible_but_Origin_header_is_not_set() {
        when(request.getHeaders()).thenReturn(httpHeaders);

        final BaseRestControllerMethod method = build(
                "",
                true,
                (pvm, req) -> completedStage(httpResponse));
        final HttpResponse actualResponse = method.call(pathVariableMapping, request).toCompletableFuture().join();

        assertSame(httpResponse, actualResponse);
        verify(httpResponse, never()).setHeader(anyString(), anyString());
    }

    @Test
    @Order(3)
    void Should_invoke_successful_if_cors_is_possible_and_Origin_header_is_set() {
        when(request.getHeaders()).thenReturn(httpHeaders);
        when(httpHeaders.getValue(ORIGIN)).thenReturn("origin");

        final BaseRestControllerMethod method = build(
                "",
                true,
                (pvm, req) -> completedStage(httpResponse));
        final HttpResponse actualResponse = method.call(pathVariableMapping, request).toCompletableFuture().join();

        assertSame(httpResponse, actualResponse);
        verify(httpResponse).setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "origin");
    }
}
