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
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.HttpResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.http.HttpStandardHeaderNames.ACCEPT;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_LENGTH;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_TYPE;
import static io.rxmicro.http.HttpStandardHeaderNames.HOST;
import static io.rxmicro.http.HttpStandardHeaderNames.USER_AGENT;
import static io.rxmicro.rest.client.netty.internal.NettyHttpClient.DEFAULT_USER_AGENT;
import static io.rxmicro.rest.client.netty.internal.TestFactory.TEST_CONTENT_DATA;
import static io.rxmicro.rest.client.netty.internal.TestFactory.TEST_CONTENT_LENGTH;
import static io.rxmicro.rest.client.netty.internal.TestFactory.TEST_CONTENT_TYPE;
import static io.rxmicro.rest.client.netty.internal.TestFactory.TestHttpClientContentConverter;
import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author nedis
 * @since 0.8
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SuppressWarnings({
        "PMD.FieldDeclarationsShouldBeAtStartOfClass",
        "PMD.AvoidDuplicateLiterals"
})
final class NettyHttpClientSimpleIntegrationTest {

    private static final int HTTP_PORT = 8085;

    private static final String TEST_HOST = "localhost:" + HTTP_PORT;

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

    private final RestClientConfig restClientConfig = new RestClientConfig()
            .setPort(HTTP_PORT);

    private final NettyHttpClient nettyHttpClient = new NettyHttpClient(
            NettyHttpClient.class,
            restClientConfig.getNameSpace(),
            restClientConfig,
            Secrets.getDefaultInstance(),
            new TestHttpClientContentConverter()
    );

    @Test
    @Order(1)
    void Should_send_without_body_and_without_custom_headers_successfully() {
        final StubMapping stubMapping = WIRE_MOCK_SERVER.stubFor(
                get(urlEqualTo("/test"))
                        .withHeader(ACCEPT, equalTo(TEST_CONTENT_TYPE))
                        .withHeader(HOST, equalTo(TEST_HOST))
                        .withHeader(USER_AGENT, equalTo(DEFAULT_USER_AGENT))
                        .withHeader(CONTENT_LENGTH, equalTo("0"))
                        .willReturn(aResponse()
                                .withHeader(CONTENT_TYPE, TEST_CONTENT_TYPE)
                                .withHeader(CONTENT_LENGTH, TEST_CONTENT_LENGTH)
                                .withBody(TEST_CONTENT_DATA))
        );
        try {
            final HttpResponse response = nettyHttpClient.sendAsync("GET", "/test", List.of()).join();

            assertEquals(200, response.getStatusCode());
            assertEquals(TEST_CONTENT_TYPE, response.getHeaders().getValue(CONTENT_TYPE));
            assertEquals(TEST_CONTENT_LENGTH, response.getHeaders().getValue(CONTENT_LENGTH));
            assertEquals(HttpVersion.HTTP_1_1, response.getVersion());
            assertEquals(TEST_CONTENT_DATA, response.getBody());
        } finally {
            WIRE_MOCK_SERVER.removeStub(stubMapping);
        }
    }

    @Test
    @Order(2)
    void Should_send_with_body_and_without_custom_headers_successfully() {
        final StubMapping stubMapping = WIRE_MOCK_SERVER.stubFor(
                post(urlEqualTo("/test"))
                        .withHeader(ACCEPT, equalTo(TEST_CONTENT_TYPE))
                        .withHeader(HOST, equalTo(TEST_HOST))
                        .withHeader(USER_AGENT, equalTo(DEFAULT_USER_AGENT))
                        .withHeader(CONTENT_LENGTH, equalTo(TEST_CONTENT_LENGTH))
                        .withRequestBody(equalTo(TEST_CONTENT_DATA))
                        .willReturn(aResponse()
                                .withStatus(201))
        );
        try {
            final HttpResponse response = nettyHttpClient.sendAsync("POST", "/test", List.of(), TEST_CONTENT_DATA).join();

            assertEquals(201, response.getStatusCode());
            assertEquals(HttpVersion.HTTP_1_1, response.getVersion());
            assertTrue(response.isBodyEmpty(), () -> "Response body not empty: " + response.getBody());
        } finally {
            WIRE_MOCK_SERVER.removeStub(stubMapping);
        }
    }

    @Test
    @Order(3)
    void Should_send_without_body_and_with_custom_headers_successfully() {
        final StubMapping stubMapping = WIRE_MOCK_SERVER.stubFor(
                get(urlEqualTo("/test"))
                        .withHeader(ACCEPT, equalTo("custom/type"))
                        .withHeader(HOST, equalTo(TEST_HOST))
                        .withHeader(USER_AGENT, equalTo("custom-user-agent"))
                        .withHeader(CONTENT_LENGTH, equalTo("0"))
                        .willReturn(aResponse()
                                .withHeader(CONTENT_TYPE, TEST_CONTENT_TYPE)
                                .withHeader(CONTENT_LENGTH, TEST_CONTENT_LENGTH)
                                .withBody(TEST_CONTENT_DATA))
        );
        try {
            final HttpHeaders headers = HttpHeaders.of(
                    ACCEPT, "custom/type",
                    USER_AGENT, "custom-user-agent"
            );
            final HttpResponse response = nettyHttpClient.sendAsync("GET", "/test", headers.getEntries()).join();

            assertEquals(200, response.getStatusCode());
            assertEquals(TEST_CONTENT_TYPE, response.getHeaders().getValue(CONTENT_TYPE));
            assertEquals(TEST_CONTENT_LENGTH, response.getHeaders().getValue(CONTENT_LENGTH));
            assertEquals(HttpVersion.HTTP_1_1, response.getVersion());
            assertEquals(TEST_CONTENT_DATA, response.getBody());
        } finally {
            WIRE_MOCK_SERVER.removeStub(stubMapping);
        }
    }

    @Test
    @Order(4)
    void Should_send_with_body_and_with_custom_headers_successfully() {
        final StubMapping stubMapping = WIRE_MOCK_SERVER.stubFor(
                post(urlEqualTo("/test"))
                        .withHeader(ACCEPT, equalTo(TEST_CONTENT_TYPE))
                        .withHeader(HOST, equalTo(TEST_HOST))
                        .withHeader(USER_AGENT, equalTo(DEFAULT_USER_AGENT))
                        .withHeader(CONTENT_LENGTH, equalTo(TEST_CONTENT_LENGTH))
                        .withHeader("custom-name", equalTo("custom-value"))
                        .withRequestBody(equalTo(TEST_CONTENT_DATA))
                        .willReturn(aResponse()
                                .withStatus(201))
        );
        try {
            final HttpHeaders headers = HttpHeaders.of(
                    "custom-name", "custom-value"
            );
            final HttpResponse response = nettyHttpClient.sendAsync("POST", "/test", headers.getEntries(), TEST_CONTENT_DATA).join();

            assertEquals(201, response.getStatusCode());
            assertEquals(HttpVersion.HTTP_1_1, response.getVersion());
            assertTrue(response.isBodyEmpty(), () -> "Response body not empty: " + response.getBody());
        } finally {
            WIRE_MOCK_SERVER.removeStub(stubMapping);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Connection",
            "connection",
            "CONNECTION",
            "Content-Length",
            "content-length",
            "CONTENT-LENGTH",
            "Host",
            "host",
            "HOST"
    })
    @Order(5)
    void Should_throw_IllegalArgumentException_if_restricted_header_detected(final String header) {
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () ->
                        nettyHttpClient.sendAsync("GET", "/", List.of(entry(header, "value"))));
        assertEquals(format("Restricted header name: '?'! Remove this header!", header), exception.getMessage());
    }

    @AfterEach
    void afterEach() {
        new NettyHttpClientConfigurationResetController().resetConfiguration();
        nettyHttpClient.release();
    }
}