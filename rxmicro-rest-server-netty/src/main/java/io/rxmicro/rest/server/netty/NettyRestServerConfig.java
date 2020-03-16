/*
 * Copyright (c) 2020 https://rxmicro.io
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

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.rxmicro.config.Config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static io.rxmicro.common.util.Requires.require;

/**
 * Allows to customize netty HTTP server
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class NettyRestServerConfig extends Config {

    private final Map<ChannelOption<?>, Object> serverOptions = new LinkedHashMap<>(
            Map.of(ChannelOption.SO_BACKLOG, 128)
    );

    private final Map<ChannelOption<?>, Object> clientOptions = new LinkedHashMap<>();

    private final List<Supplier<ChannelHandler>> handlerSuppliers = new ArrayList<>(List.of(
            HttpServerCodec::new,
            () -> new HttpObjectAggregator(64 * 1024, true)
    ));

    private NettyTransport transport = NettyTransport.AUTO;

    /**
     * Adds server channel option
     *
     * @param option option name
     * @param value  option value
     * @param <T>    option type
     * @return A reference to this {@code NettyServerConfig}
     * @see ChannelOption
     */
    public <T> NettyRestServerConfig serverOption(final ChannelOption<T> option, final T value) {
        serverOptions.put(require(option), require(value));
        return this;
    }

    /**
     * Adds client channel option
     *
     * @param option option name
     * @param value  option value
     * @param <T>    option type
     * @return A reference to this {@code NettyServerConfig}
     * @see ChannelOption
     */
    public <T> NettyRestServerConfig clientOption(final ChannelOption<T> option, final T value) {
        clientOptions.put(require(option), require(value));
        return this;
    }

    /**
     * Adds channel handler supplier to the last position of channel handlers
     *
     * @param channelHandlerSupplier channel handler supplier
     * @return A reference to this {@code NettyServerConfig}
     */
    public NettyRestServerConfig addLast(final Supplier<ChannelHandler> channelHandlerSupplier) {
        handlerSuppliers.add(require(channelHandlerSupplier));
        return this;
    }

    /**
     * Resets channel handlers
     *
     * @return A reference to this {@code NettyServerConfig}
     */
    public NettyRestServerConfig resetChannelHandlers() {
        handlerSuppliers.clear();
        return this;
    }

    public Map<ChannelOption<?>, Object> getServerOptions() {
        return serverOptions;
    }

    public Map<ChannelOption<?>, Object> getClientOptions() {
        return clientOptions;
    }

    public List<Supplier<ChannelHandler>> getHandlerSuppliers() {
        return handlerSuppliers;
    }

    public NettyTransport getTransport() {
        return transport;
    }

    /**
     * Sets which {@code NettyTransport} must be used.
     * If required {@code NettyTransport} couldn't be used, {@code NettyTransport.NETTY} will be set automatically
     *
     * @param transport {@code NettyTransport} which must be used
     * @return A reference to this {@code NettyServerConfig}
     */
    public NettyRestServerConfig setTransport(final NettyTransport transport) {
        this.transport = require(transport);
        return this;
    }

    @Override
    public String toString() {
        return "NettyRestServerConfig{" +
                "serverOptions=" + serverOptions +
                ", clientOptions=" + clientOptions +
                ", handlerSuppliers=" + handlerSuppliers +
                ", transport=" + transport +
                '}';
    }
}
