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

import io.rxmicro.config.Configs;
import io.rxmicro.config.Secrets;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.detail.HttpClient;
import io.rxmicro.rest.client.detail.HttpResponse;
import io.rxmicro.rest.client.netty.internal.TestFactory.TestHttpClientContentConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_LENGTH;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author nedis
 * @since 0.8
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SuppressWarnings("PMD.FieldDeclarationsShouldBeAtStartOfClass")
final class NettyHttpClientSSHIntegrationTest {

    @BeforeAll
    static void beforeAll() {
        new Configs.Builder().build();
    }

    private final RestClientConfig restClientConfig = new RestClientConfig()
            .setConnectionString("https://rxmicro.io");

    private final HttpClient httpClient = new NettyHttpClientFactory()
            .create(
                    NettyHttpClient.class,
                    restClientConfig.getNameSpace(),
                    restClientConfig,
                    Secrets.getDefaultInstance(),
                    new TestHttpClientContentConverter()
            );

    @Test
    @Order(1)
    void Should_send_successfully() {
        final HttpResponse response = httpClient.sendAsync("GET", "/mock-api/say-hello.json", List.of()).join();

        assertEquals(200, response.getStatusCode());
        assertEquals("application/json; charset=utf-8", response.getHeaders().getValue(CONTENT_TYPE));
        assertEquals("26", response.getHeaders().getValue(CONTENT_LENGTH));
        assertEquals(HttpVersion.HTTP_1_1, response.getVersion());
        assertEquals("{\"message\":\"Hello World!\"}", response.getBody());
        assertEquals(26, response.getBodyAsBytes().length);
        assertFalse(response.isBodyEmpty());
    }

    @AfterEach
    void afterEach() {
        new NettyHttpClientConfigurationResetController().resetConfiguration();
        httpClient.release();
    }
}