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

package io.rxmicro.rest.server.netty.internal.component;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.rxmicro.rest.server.netty.NettyConfiguratorBuilder;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author nedis
 * @since 0.7.2
 */
public final class NettyConfiguratorController {

    private static final NettyConfiguratorController INSTANCE = new NettyConfiguratorController();

    private NettyConfigurator nettyConfigurator;

    private NettyConfiguratorBuilder nettyConfiguratorBuilder;

    public static NettyConfiguratorController getNettyConfiguratorController() {
        return INSTANCE;
    }

    public NettyConfigurator getNettyConfigurator() {
        if (nettyConfigurator == null) {
            nettyConfigurator = new NettyConfigurator(
                    getNettyConfiguratorBuilder().serverOptions,
                    getNettyConfiguratorBuilder().clientOptions,
                    getNettyConfiguratorBuilder().handlerSuppliers
            );
            // clear previous builder
            nettyConfiguratorBuilder = null;
        }
        return nettyConfigurator;
    }

    public NettyConfiguratorBuilder getNettyConfiguratorBuilder() {
        validateState();
        if (nettyConfiguratorBuilder == null) {
            nettyConfiguratorBuilder = new NettyConfiguratorBuilder();
        }
        return nettyConfiguratorBuilder;
    }

    public void validateState() {
        if (nettyConfigurator != null) {
            throw new IllegalStateException("Netty configurator already built! " +
                    "Any customizations must be done before building of the netty configurator!");
        }
    }

    public void reset() {
        nettyConfigurator = null;
        nettyConfiguratorBuilder = null;
    }

    /**
     * @author nedis
     * @since 0.7.2
     */
    static final class NettyConfigurator {

        private final Map<ChannelOption<?>, Object> serverOptions;

        private final Map<ChannelOption<?>, Object> clientOptions;

        private final List<Map.Entry<String, Supplier<ChannelHandler>>> handlerSuppliers;

        NettyConfigurator(final Map<ChannelOption<?>, Object> serverOptions,
                          final Map<ChannelOption<?>, Object> clientOptions,
                          final List<Map.Entry<String, Supplier<ChannelHandler>>> handlerSuppliers) {
            this.serverOptions = serverOptions;
            this.clientOptions = clientOptions;
            this.handlerSuppliers = handlerSuppliers;
        }

        Map<ChannelOption<?>, Object> getServerOptions() {
            return serverOptions;
        }

        Map<ChannelOption<?>, Object> getClientOptions() {
            return clientOptions;
        }

        List<Map.Entry<String, Supplier<ChannelHandler>>> getHandlerSuppliers() {
            return handlerSuppliers;
        }
    }
}
