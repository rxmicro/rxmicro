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
import io.rxmicro.rest.client.LeasingStrategy;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.HttpResponse;
import io.rxmicro.rest.client.netty.NettyClientConfiguratorBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.Duration;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.netty.channel.ChannelOption.SO_KEEPALIVE;
import static io.rxmicro.rest.client.netty.NettyHttpClientConfigCustomizer.getDefaultNettyClientConfiguratorBuilder;
import static io.rxmicro.rest.client.netty.internal.DummyFactory.StubHttpClientContentConverter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author nedis
 * @since 0.8
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SuppressWarnings("PMD.FieldDeclarationsShouldBeAtStartOfClass")
final class NettyHttpClientCustomizationIntegrationTest {

    private static final int HTTP_PORT = 8086;

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

    private final RestClientConfig restClientConfig = new RestClientConfig("rest-client")
            .setPort(HTTP_PORT);

    @Test
    @Order(1)
    void Should_support_client_options_customization() {
        final NettyClientConfiguratorBuilder builder = getDefaultNettyClientConfiguratorBuilder();
        builder.setClientOption(SO_KEEPALIVE, false);

        verifyResponse();
    }

    @Test
    @Order(2)
    void Should_support_httpResponseDecoderSpec_customization() {
        final NettyClientConfiguratorBuilder builder = getDefaultNettyClientConfiguratorBuilder();
        builder.getHttpResponseDecoderSpec().validateHeaders(false);
        builder.getHttpResponseDecoderSpec().failOnMissingResponse(false);

        verifyResponse();
    }

    @Test
    @Order(3)
    void Should_support_client_options_and_httpResponseDecoderSpec_customization() {
        final NettyClientConfiguratorBuilder builder = getDefaultNettyClientConfiguratorBuilder();
        builder.setClientOption(SO_KEEPALIVE, false);
        builder.getHttpResponseDecoderSpec().validateHeaders(false);
        builder.getHttpResponseDecoderSpec().failOnMissingResponse(false);

        verifyResponse();
    }

    private void verifyResponse() {
        final StubMapping stubMapping = WIRE_MOCK_SERVER.stubFor(
                get(urlEqualTo("/test"))
                        .willReturn(aResponse()
                                .withStatus(208))
        );
        NettyHttpClient nettyHttpClient = null;
        try {
            restClientConfig
                    .setConnectTimeout(Duration.ZERO)
                    .setRequestTimeout(Duration.ZERO)
                    .setLeasingStrategy(LeasingStrategy.LIFO);
            nettyHttpClient = new NettyHttpClient(
                    NettyHttpClient.class,
                    restClientConfig.getNameSpace(),
                    restClientConfig,
                    Secrets.getDefaultInstance(),
                    new StubHttpClientContentConverter()
            );

            final HttpResponse response = nettyHttpClient.sendAsync("GET", "/test", List.of()).join();

            assertEquals(208, response.getStatusCode());
            assertTrue(response.isBodyEmpty(), () -> "Response body not empty: " + response.getBody());
        } finally {
            if (nettyHttpClient != null) {
                nettyHttpClient.release();
            }
            WIRE_MOCK_SERVER.removeStub(stubMapping);
        }
    }

    @AfterEach
    void afterEach() {
        new NettyHttpClientConfigurationResetController().resetConfiguration();
    }
}