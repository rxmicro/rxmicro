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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.netty.channel.ChannelOption.SO_KEEPALIVE;
import static io.rxmicro.rest.client.netty.internal.NettyClientConfiguratorBuilderImpl.PROCESSED_NAMESPACES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nedis
 * @since 0.8
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class NettyClientConfiguratorBuilderImplTest {

    private static final String TEST_NAMESPACE = "test";

    private static final String ERROR_MESSAGE = "Netty configurator already built! " +
            "Any customizations must be done before building of the netty configurator!";

    @Test
    @Order(1)
    void constructor_should_throw_IllegalStateException() {
        PROCESSED_NAMESPACES.add(TEST_NAMESPACE);
        final IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> new NettyClientConfiguratorBuilderImpl(TEST_NAMESPACE));
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    @Order(2)
    void setClientOption_should_throw_IllegalStateException() {
        final NettyClientConfiguratorBuilderImpl builder = new NettyClientConfiguratorBuilderImpl(TEST_NAMESPACE);
        PROCESSED_NAMESPACES.add(TEST_NAMESPACE);
        final IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> builder.setClientOption(SO_KEEPALIVE, false));
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    @Order(3)
    void getHttpResponseDecoderSpec_should_throw_IllegalStateException() {
        final NettyClientConfiguratorBuilderImpl builder = new NettyClientConfiguratorBuilderImpl(TEST_NAMESPACE);
        PROCESSED_NAMESPACES.add(TEST_NAMESPACE);
        final IllegalStateException exception =
                assertThrows(IllegalStateException.class, builder::getHttpResponseDecoderSpec);
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    @AfterEach
    void afterEach() {
        PROCESSED_NAMESPACES.clear();
    }
}