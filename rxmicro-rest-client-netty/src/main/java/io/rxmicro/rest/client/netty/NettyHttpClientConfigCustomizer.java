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

package io.rxmicro.rest.client.netty;

import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.netty.internal.NettyClientConfiguratorBuilderImpl;
import io.rxmicro.rest.client.netty.internal.NettyCustomizer;

import static io.rxmicro.config.Config.getDefaultNameSpace;

/**
 * Allows configuring the application specific configs for netty HTTP client.
 *
 * @author nedis
 * @since 0.8
 */
public final class NettyHttpClientConfigCustomizer extends NettyCustomizer {

    /**
     * Returns the {@link NettyClientConfiguratorBuilder} instance to configure http client for the specified namespace.
     *
     * @param namespace the specified namespace.
     * @return the {@link NettyClientConfiguratorBuilder} instance
     */
    public static NettyClientConfiguratorBuilder getNettyClientConfiguratorBuilder(final String namespace) {
        return NETTY_CLIENT_CONFIGURATOR_BUILDERS.computeIfAbsent(namespace, v -> new NettyClientConfiguratorBuilderImpl());
    }

    /**
     * Returns the {@link NettyClientConfiguratorBuilder} instance to configure http client for the default namespace.
     *
     * @return the {@link NettyClientConfiguratorBuilder} instance
     */
    public static NettyClientConfiguratorBuilder getDefaultNettyClientConfiguratorBuilder() {
        return getNettyClientConfiguratorBuilder(getDefaultNameSpace(RestClientConfig.class));
    }

    private NettyHttpClientConfigCustomizer() {
    }
}
