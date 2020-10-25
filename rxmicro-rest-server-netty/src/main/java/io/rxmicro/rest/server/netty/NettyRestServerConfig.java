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

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.config.Config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

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
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
public final class NettyRestServerConfig extends Config {

    /**
     * Default backlog size.
     */
    public static final int DEFAULT_BACKLOG_SIZE = 128;

    /**
     * Default aggregator content length in bytes.
     */
    public static final int DEFAULT_AGGREGATOR_CONTENT_LENGTH_IN_BYTES = 64 * 1024;

    private final Map<ChannelOption<?>, Object> serverOptions = new LinkedHashMap<>(
            Map.of(ChannelOption.SO_BACKLOG, DEFAULT_BACKLOG_SIZE)
    );

    private final Map<ChannelOption<?>, Object> clientOptions = new LinkedHashMap<>();

    private final List<Supplier<ChannelHandler>> handlerSuppliers = new ArrayList<>(List.of(
            HttpServerCodec::new,
            () -> new HttpObjectAggregator(DEFAULT_AGGREGATOR_CONTENT_LENGTH_IN_BYTES, true)
    ));

    private NettyTransport transport = NettyTransport.AUTO;

    private NettyChannelIdType channelIdType = PredefinedNettyChannelIdType.SHORT;

    /**
     * Adds server channel option.
     *
     * @param option option name
     * @param value  option value
     * @param <T>    option type
     * @return the reference to this {@link NettyRestServerConfig} instance
     * @see ChannelOption
     */
    @BuilderMethod
    public <T> NettyRestServerConfig serverOption(final ChannelOption<T> option, final T value) {
        serverOptions.put(require(option), require(value));
        return this;
    }

    /**
     * Adds client channel option.
     *
     * @param option option name
     * @param value  option value
     * @param <T>    option type
     * @return the reference to this {@link NettyRestServerConfig} instance
     * @see ChannelOption
     */
    @BuilderMethod
    public <T> NettyRestServerConfig clientOption(final ChannelOption<T> option, final T value) {
        clientOptions.put(require(option), require(value));
        return this;
    }

    /**
     * Adds channel handler supplier to the last position of channel handlers.
     *
     * @param channelHandlerSupplier channel handler supplier
     * @return the reference to this {@link NettyRestServerConfig} instance
     */
    @BuilderMethod
    public NettyRestServerConfig addLast(final Supplier<ChannelHandler> channelHandlerSupplier) {
        handlerSuppliers.add(require(channelHandlerSupplier));
        return this;
    }

    /**
     * Resets channel handlers, i.e. removes all configured channel handlers.
     *
     * @return the reference to this {@link NettyRestServerConfig} instance
     */
    @BuilderMethod
    public NettyRestServerConfig resetChannelHandlers() {
        handlerSuppliers.clear();
        return this;
    }

    /**
     * Returns configured server options.
     *
     * @return configured server options
     */
    public Map<ChannelOption<?>, Object> getServerOptions() {
        return serverOptions;
    }

    /**
     * Returns configured client options.
     *
     * @return configured client options
     */
    public Map<ChannelOption<?>, Object> getClientOptions() {
        return clientOptions;
    }

    /**
     * Returns configured {@link ChannelHandler} suppliers.
     *
     * @return configured {@link ChannelHandler} suppliers
     */
    public List<Supplier<ChannelHandler>> getHandlerSuppliers() {
        return handlerSuppliers;
    }

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
                "serverOptions=" + serverOptions +
                ", clientOptions=" + clientOptions +
                ", handlerSuppliers=" + handlerSuppliers +
                ", transport=" + transport +
                '}';
    }
}
