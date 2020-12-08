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

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.rest.server.netty.NettyRestServerConfig;

import java.util.concurrent.CountDownLatch;

import static io.rxmicro.common.CommonConstants.NANOS_IN_1_MILLIS;
import static io.rxmicro.common.local.StartTimeStampHelper.START_TIME_STAMP;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.rest.server.netty.internal.component.InternalNettyRestServerConfigCustomizer.getClientOptions;
import static io.rxmicro.rest.server.netty.internal.component.InternalNettyRestServerConfigCustomizer.getServerOptions;
import static io.rxmicro.rest.server.netty.internal.util.NettyTransportFactory.getCurrentNettyTransport;

/**
 * @author nedis
 * @since 0.1
 */
final class NettyServer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    private final HttpServerConfig httpServerConfig;

    private final NettyRestServerConfig nettyRestServerConfig;

    private final Class<? extends ServerSocketChannel> serverSocketChannelClass;

    private final EventLoopGroup serverGroup;

    private final EventLoopGroup workerGroup;

    private final CountDownLatch latch;

    NettyServer(final HttpServerConfig httpServerConfig,
                final NettyRestServerConfig nettyRestServerConfig,
                final Class<? extends ServerSocketChannel> serverSocketChannelClass,
                final EventLoopGroup serverGroup,
                final EventLoopGroup workerGroup,
                final CountDownLatch latch) {
        this.httpServerConfig = httpServerConfig;
        this.nettyRestServerConfig = require(nettyRestServerConfig);
        this.serverSocketChannelClass = require(serverSocketChannelClass);
        this.serverGroup = require(serverGroup);
        this.workerGroup = require(workerGroup);
        this.latch = latch;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        try {
            final ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(serverGroup, workerGroup)
                    .channel(serverSocketChannelClass)
                    .childHandler(new NettyClientConnectionController(nettyRestServerConfig));
            getServerOptions().forEach((o, v) -> bootstrap.option((ChannelOption<Object>) o, v));
            getClientOptions().forEach((o, v) -> bootstrap.childOption((ChannelOption<Object>) o, v));

            final ChannelFuture channelFuture = bootstrap.bind(httpServerConfig.getHost(), httpServerConfig.getPort()).sync();
            logStartedMessage();
            latch.countDown();
            channelFuture.channel().closeFuture().sync();
        } catch (final InterruptedException ignore) {
            LOGGER.info("Retrieved shutdown request ...");
        } finally {
            final Future<?> workerGroupStopFuture = workerGroup.shutdownGracefully();
            final Future<?> serverGroupStopFuture = serverGroup.shutdownGracefully()
                    .addListener(f -> LOGGER.info("Server stopped"));

            workerGroupStopFuture.awaitUninterruptibly();
            serverGroupStopFuture.awaitUninterruptibly();
        }
    }

    private void logStartedMessage() {
        if (httpServerConfig.isStartTimeTrackerEnabled()) {
            LOGGER.info(
                    "Server started at ?:? using ? transport in ? millis.",
                    httpServerConfig::getHost,
                    httpServerConfig::getPort,
                    () -> getCurrentNettyTransport(nettyRestServerConfig),
                    () -> (System.nanoTime() - START_TIME_STAMP) / NANOS_IN_1_MILLIS
            );
        } else {
            LOGGER.info(
                    "Server started at ?:? using ? transport.",
                    httpServerConfig::getHost,
                    httpServerConfig::getPort,
                    () -> getCurrentNettyTransport(nettyRestServerConfig)
            );
        }
    }
}
