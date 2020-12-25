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

package io.rxmicro.rest.server.netty;

import io.netty.channel.ChannelOption;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.config.Config;
import io.rxmicro.config.SingletonConfigClass;

import static io.rxmicro.common.util.Requires.require;

/**
 * Allows customizing netty REST server options.
 *
 * @author nedis
 * @see NettyTransport
 * @see NettyChannelIdType
 * @see ChannelOption
 * @see HttpServerCodec
 * @see HttpObjectAggregator
 * @see NettyRestServerConfigCustomizer
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
@SingletonConfigClass
public final class NettyRestServerConfig extends Config {

    private NettyTransport transport = NettyTransport.AUTO;

    private NettyChannelIdType channelIdType = PredefinedNettyChannelIdType.SHORT;

    /**
     * Returns current {@link NettyTransport}.
     *
     * @return current {@link NettyTransport}
     */
    public NettyTransport getTransport() {
        return transport;
    }

    /**
     * Sets which {@link NettyTransport} must be used.
     * If required {@link NettyTransport} couldn't be used, {@link NettyTransport#NETTY} will be set automatically.
     *
     * @param transport {@link NettyTransport} which must be used
     * @return the reference to this {@link NettyRestServerConfig} instance
     */
    @BuilderMethod
    public NettyRestServerConfig setTransport(final NettyTransport transport) {
        this.transport = require(transport);
        return this;
    }

    /**
     * Returns the channel id type.
     *
     * @return the channel id type
     * @since 0.3
     */
    public NettyChannelIdType getChannelIdType() {
        return channelIdType;
    }

    /**
     * Sets channelIdType.
     *
     * @since 0.3
     * @param channelIdType {@link NettyChannelIdType} which must be used
     * @return the reference to this {@link NettyRestServerConfig} instance
     */
    @BuilderMethod
    public NettyRestServerConfig setChannelIdType(final NettyChannelIdType channelIdType) {
        this.channelIdType = require(channelIdType);
        return this;
    }

    @Override
    public String toString() {
        return "NettyRestServerConfig{" +
                "transport=" + transport +
                ", channelIdType=" + channelIdType +
                '}';
    }
}
