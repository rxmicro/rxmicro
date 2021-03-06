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
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.netty.runtime.NettyRuntimeConfig;
import io.rxmicro.rest.server.RestServerConfig;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.rest.server.netty.internal.component.InternalNettyConfiguratorBuilder.NETTY_RX_MICRO_REQUEST_HANDLER_NAME;
import static io.rxmicro.rest.server.netty.internal.component.NettyConfiguratorController.getNettyConfiguratorController;
import static io.rxmicro.rest.server.netty.internal.util.HealthCheckTools.isHealthCheckToolAddress;

/**
 * @author nedis
 * @since 0.7
 */
final class NettyClientConnectionController extends ChannelInitializer<SocketChannel> {

    private static final AttributeKey<Long> CHANNEL_TTL = AttributeKey.valueOf("CHANNEL_TTL");

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientConnectionController.class);

    private final RestServerConfig restServerConfig;

    private final NettyRuntimeConfig nettyRuntimeConfig;

    private final SharableNettyRequestHandler sharableNettyRequestHandler;

    private final List<Map.Entry<String, Supplier<ChannelHandler>>> handlerSuppliers;

    NettyClientConnectionController(final SharableNettyRequestHandler sharableNettyRequestHandler) {
        this.restServerConfig = getConfig(RestServerConfig.class);
        this.nettyRuntimeConfig = getConfig(NettyRuntimeConfig.class);
        this.sharableNettyRequestHandler = sharableNettyRequestHandler;
        this.handlerSuppliers = getNettyConfiguratorController().getNettyConfigurator().getHandlerSuppliers();
    }

    @Override
    protected void initChannel(final SocketChannel ch) {
        final boolean traceConnection = LOGGER.isTraceEnabled() && !isHealthCheckToolAddress(restServerConfig, ch);
        if (traceConnection) {
            ch.attr(CHANNEL_TTL).set(System.nanoTime());
            LOGGER.trace(
                    "Client connection created: Channel=?, Socket=?",
                    nettyRuntimeConfig.getChannelIdType().getId(ch.id()), ch.remoteAddress()
            );
        }
        final ChannelPipeline pipeline = ch.pipeline();
        for (final Map.Entry<String, Supplier<ChannelHandler>> entry : handlerSuppliers) {
            pipeline.addLast(entry.getKey(), entry.getValue().get());
        }
        pipeline.addLast(NETTY_RX_MICRO_REQUEST_HANDLER_NAME, sharableNettyRequestHandler);
        ch.closeFuture().addListener(future -> {
                    if (traceConnection) {
                        LOGGER.trace(
                                "Client connection closed: Channel=?, Socket=?, TTL=?",
                                nettyRuntimeConfig.getChannelIdType().getId(ch.id()),
                                ch.remoteAddress(),
                                format(Duration.ofNanos(System.nanoTime() - ch.attr(CHANNEL_TTL).get()))
                        );
                    }
                }
        );
    }
}
