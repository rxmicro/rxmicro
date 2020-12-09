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
import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.rest.server.netty.internal.component.InternalNettyConfiguratorBuilder;

import java.util.Map;
import java.util.function.Supplier;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.rest.server.netty.internal.component.NettyConfiguratorController.getNettyConfiguratorController;
import static java.util.Map.entry;

/**
 * Allows configuring the application specific configs for netty HTTP server.
 *
 * <p>
 * To get an instance of {@link NettyConfiguratorBuilder} type use
 * {@link NettyRestServerConfigCustomizer#getCurrentNettyConfiguratorBuilder()} static method.
 *
 * <p>
 * The default instance of {@link NettyConfiguratorBuilder} type contains the following predefined configuration:
 * <ul>
 *     <li>
 *         {@code serverOptions} contains:
 *          <ul>
 *              <li>{@link ChannelOption#SO_BACKLOG} = {@value InternalNettyConfiguratorBuilder#DEFAULT_BACKLOG_SIZE}</li>
 *          </ul>
 *     </li>
 *     <li>
 *         {@code handlerSuppliers} contains the following {@link ChannelHandler}:
 *         <ul>
 *             <li>{@link io.netty.handler.codec.http.HttpServerCodec} with default settings</li>
 *             <li>{@link io.netty.handler.codec.http.HttpObjectAggregator} with {@code maxContentLength} =
 *                  {@value InternalNettyConfiguratorBuilder#DEFAULT_AGGREGATOR_CONTENT_LENGTH_IN_BYTES} bytes and
 *                  {@code closeOnExpectationFailed} = {@code true}</li>
 *         </ul>
 *     </li>
 * </ul>
 *
 * @author nedis
 * @see NettyRestServerConfigCustomizer
 * @since 0.7.2
 */
public final class NettyConfiguratorBuilder extends InternalNettyConfiguratorBuilder {

    /**
     * Sets (adds or replaces) server channel option.
     *
     * @param option option name
     * @param value  option value
     * @param <T>    option type
     * @return the reference to this {@link NettyConfiguratorBuilder} instance
     * @see ChannelOption
     * @see java.util.Map
     * @throws IllegalStateException if Netty configurator already built
     * @throws NullPointerException if {@code option} or {@code value} is {@code null}
     */
    @BuilderMethod
    public <T> NettyConfiguratorBuilder setServerOption(final ChannelOption<T> option,
                                                        final T value) {
        getNettyConfiguratorController().validateState();
        serverOptions.put(require(option), require(value));
        return this;
    }

    /**
     * Sets (adds or replaces) client channel option.
     *
     * @param option option name
     * @param value  option value
     * @param <T>    option type
     * @return the reference to this {@link NettyConfiguratorBuilder} instance
     * @see ChannelOption
     * @see java.util.Map
     * @throws IllegalStateException if Netty configurator already built
     * @throws NullPointerException if {@code option} or {@code value} is {@code null}
     */
    @BuilderMethod
    public <T> NettyConfiguratorBuilder setClientOption(final ChannelOption<T> option,
                                                        final T value) {
        getNettyConfiguratorController().validateState();
        clientOptions.put(require(option), require(value));
        return this;
    }

    /**
     * Resets channel handlers, i.e. removes all configured channel handlers.
     *
     * @return the reference to this {@link NettyConfiguratorBuilder} instance
     * @throws IllegalStateException if Netty configurator already built
     */
    @BuilderMethod
    public NettyConfiguratorBuilder resetChannelHandlers() {
        getNettyConfiguratorController().validateState();
        handlerSuppliers.clear();
        return this;
    }

    /**
     * Adds channel handler supplier to the last position of channel handlers.
     *
     * <p>
     * The {@code name} parameter must be any unique string.
     * The RxMicro team recommends to use {@link Class#getSimpleName()} of added {@link ChannelHandler} class as {@code name} parameter.
     * If your pipeline must contain two different instance of the same {@link ChannelHandler} class provide any unique names, otherwise
     * {@link IllegalArgumentException} will be thrown.
     *
     * @param name                   the name of the handler to append
     * @param channelHandlerSupplier the channel handler supplier
     * @return the reference to this {@link NettyConfiguratorBuilder} instance
     * @see io.netty.channel.ChannelPipeline#addLast(String, ChannelHandler)
     * @throws IllegalStateException if Netty configurator already built
     * @throws NullPointerException  if {@code name} or {@code channelHandlerSupplier} is {@code null}
     * @throws IllegalArgumentException if there's an entry with the same name already in the pipeline
     */
    @BuilderMethod
    public NettyConfiguratorBuilder addChannelHandlerSupplierToLastPosition(final String name,
                                                                            final Supplier<ChannelHandler> channelHandlerSupplier) {
        getNettyConfiguratorController().validateState();
        for (final Map.Entry<String, Supplier<ChannelHandler>> entry : handlerSuppliers) {
            if (entry.getKey().equals(name) || NETTY_RX_MICRO_REQUEST_HANDLER_NAME.equals(name)) {
                throw new IllegalArgumentException(format("Channel handler with name='?' already defined!", name));
            }
        }
        handlerSuppliers.add(entry(name, channelHandlerSupplier));
        return this;
    }
}
