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
import io.rxmicro.rest.server.netty.internal.component.InternalNettyRestServerConfigCustomizer;

import java.util.function.Supplier;

import static io.rxmicro.common.util.Requires.require;

/**
 * Allows configuring the application specific configs for netty REST server.
 *
 * <ul>
 *     <li>{@link NettyRestServerConfig} must be used for environment specific configs.</li>
 *     <li>{@link NettyRestServerConfigCustomizer} must be used for application specific configs.</li>
 * </ul>
 *
 * @author nedis
 * @see NettyRestServerConfig
 * @see HttpServerCodec
 * @see HttpObjectAggregator
 * @since 0.7.2
 */
public final class NettyRestServerConfigCustomizer extends InternalNettyRestServerConfigCustomizer {

    /**
     * Adds server channel option.
     *
     * @param option option name
     * @param value  option value
     * @param <T>    option type
     * @return the previous value associated with {@code option}, or {@code null} if there was no mapping for {@code option}.
     * @see ChannelOption
     * @see java.util.Map
     * @throws IllegalStateException if netty server already built
     * @throws NullPointerException if {@code option} or {@code value} is {@code null}
     */
    @SuppressWarnings("unchecked")
    public static <T> T setServerOption(final ChannelOption<T> option, final T value) {
        validateState();
        return (T) serverOptions.put(require(option), require(value));
    }

    /**
     * Adds client channel option.
     *
     * @param option option name
     * @param value  option value
     * @param <T>    option type
     * @return the previous value associated with {@code option}, or {@code null} if there was no mapping for {@code option}.
     * @see ChannelOption
     * @see java.util.Map
     * @throws IllegalStateException if netty server already built
     * @throws NullPointerException if {@code option} or {@code value} is {@code null}
     */
    @SuppressWarnings("unchecked")
    public static <T> T setClientOption(final ChannelOption<T> option, final T value) {
        validateState();
        return (T) clientOptions.put(require(option), require(value));
    }

    /**
     * Resets channel handlers, i.e. removes all configured channel handlers.
     *
     * @throws IllegalStateException if netty server already built
     */
    public static void resetChannelHandlers() {
        validateState();
        handlerSuppliers.clear();
    }

    /**
     * Adds channel handler supplier to the last position of channel handlers.
     *
     * @param channelHandlerSupplier channel handler supplier
     * @throws IllegalStateException if netty server already built
     * @throws NullPointerException if {@code channelHandlerSupplier} is {@code null}
     */
    public static void addChannelHandlerSupplierToLastPosition(final Supplier<ChannelHandler> channelHandlerSupplier) {
        validateState();
        handlerSuppliers.add(require(channelHandlerSupplier));
    }

    private NettyRestServerConfigCustomizer() {
    }
}
