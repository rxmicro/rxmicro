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

package io.rxmicro.rest.client.netty.internal;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import io.rxmicro.config.Configs;
import io.rxmicro.config.Secrets;
import io.rxmicro.logger.Level;
import io.rxmicro.logger.RequestIdSupplier;
import io.rxmicro.logger.impl.LoggerImplProvider;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.netty.internal.TestFactory.TestAbstractLogger;
import io.rxmicro.rest.client.netty.internal.TestFactory.TestHttpClientContentConverter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_LENGTH;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_TYPE;
import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static io.rxmicro.logger.Level.DEBUG;
import static io.rxmicro.logger.Level.TRACE;
import static io.rxmicro.logger.LoggerImplProviderFactory.resetLoggerImplFactory;
import static io.rxmicro.logger.LoggerImplProviderFactory.setLoggerImplFactory;
import static io.rxmicro.rest.client.netty.internal.TestFactory.TEST_CONTENT_DATA;
import static io.rxmicro.rest.client.netty.internal.TestFactory.TEST_CONTENT_LENGTH;
import static io.rxmicro.rest.client.netty.internal.TestFactory.TEST_CONTENT_TYPE;
import static java.util.Map.entry;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @since 0.8
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@SuppressWarnings({
        "PMD.FieldDeclarationsShouldBeAtStartOfClass",
        "PMD.ProperLogger"
})
final class NettyHttpClientLoggerIntegrationTest {

    private static final int HTTP_PORT = 8087;

    private static final WireMockServer WIRE_MOCK_SERVER = new WireMockServer(
            wireMockConfig()
                    .port(HTTP_PORT)
    );

    @BeforeAll
    static void beforeAll() {
        new Configs.Builder().build();
        WIRE_MOCK_SERVER.start();
    }

    @AfterAll
    static void afterAll() {
        WIRE_MOCK_SERVER.stop();
    }

    private final Map<Level, Boolean> levelEnabledMap = new HashMap<>();

    private final TestAbstractLogger logger = spy(new TestAbstractLogger(levelEnabledMap));

    @Mock
    private LoggerImplProvider loggerImplProvider;

    private NettyHttpClient nettyHttpClient;

    @BeforeEach
    void beforeEach() {
        when(loggerImplProvider.getLogger(any(Class.class))).thenReturn(logger);
        setLoggerImplFactory(loggerImplProvider);

        final RestClientConfig restClientConfig = new RestClientConfig()
                .setPort(HTTP_PORT)
                .setRequestTimeout(Duration.ZERO)
                .setConnectTimeout(Duration.ZERO);
        nettyHttpClient = new NettyHttpClient(
                NettyHttpClientLoggerIntegrationTest.class,
                restClientConfig.getNameSpace(),
                restClientConfig,
                Secrets.getDefaultInstance(),
                new TestHttpClientContentConverter()
        );
    }

    @Test
    @Order(1)
    void Should_trace_get_request() {
        levelEnabledMap.put(TRACE, true);

        invokeGet();

        verify(logger, times(2)).log(any(RequestIdSupplier.class), eq(TRACE), anyString());
    }

    @Test
    @Order(2)
    void Should_debug_get_request() {
        levelEnabledMap.put(DEBUG, true);

        invokeGet();

        verify(logger, times(2)).log(any(RequestIdSupplier.class), eq(DEBUG), anyString());
    }

    @Test
    @Order(3)
    void Should_trace_post_request() {
        levelEnabledMap.put(TRACE, true);

        invokePost();

        verify(logger, times(2)).log(any(RequestIdSupplier.class), eq(TRACE), anyString());
    }

    @Test
    @Order(4)
    void Should_debug_post_request() {
        levelEnabledMap.put(DEBUG, true);

        invokePost();

        verify(logger, times(2)).log(any(RequestIdSupplier.class), eq(DEBUG), anyString());
    }

    private void invokeGet() {
        final StubMapping stubMapping = WIRE_MOCK_SERVER.stubFor(
                get(urlEqualTo("/test"))
                        .willReturn(aResponse()
                                .withHeader(CONTENT_TYPE, TEST_CONTENT_TYPE)
                                .withHeader(CONTENT_LENGTH, TEST_CONTENT_LENGTH)
                                .withBody(TEST_CONTENT_DATA))
        );
        try {
            nettyHttpClient.sendAsync("GET", "/test", List.of(entry(REQUEST_ID, "TestRequestId"))).join();
        } finally {
            WIRE_MOCK_SERVER.removeStub(stubMapping);
        }
    }

    private void invokePost() {
        final StubMapping stubMapping = WIRE_MOCK_SERVER.stubFor(
                post(urlEqualTo("/test"))
                        .willReturn(aResponse()
                                .withStatus(208))
        );
        try {
            nettyHttpClient.sendAsync("POST", "/test", List.of(), TEST_CONTENT_DATA).join();
        } finally {
            WIRE_MOCK_SERVER.removeStub(stubMapping);
        }
    }

    @AfterEach
    void afterEach() {
        resetLoggerImplFactory();
        new NettyHttpClientConfigurationResetController().resetConfiguration();
        nettyHttpClient.release();
    }
}