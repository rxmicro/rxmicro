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
import io.rxmicro.netty.runtime.local.EventLoopGroupFactory;
import io.rxmicro.rest.server.HttpServerConfig;

import java.util.concurrent.CountDownLatch;

import static io.rxmicro.common.CommonConstants.NANOS_IN_1_MILLIS;
import static io.rxmicro.common.local.StartTimeStampHelper.START_TIME_STAMP;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.netty.runtime.local.EventLoopGroupFactory.getEventLoopGroupFactory;
import static io.rxmicro.rest.server.netty.internal.component.NettyConfiguratorController.getNettyConfiguratorController;

/**
 * @author nedis
 * @since 0.1
 */
final class NettyServer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    private static final String DEFAULT_WORKER_THREAD_QUALIFIER = "http-server";

    private final HttpServerConfig httpServerConfig;

    private final SharableNettyRequestHandler sharableNettyRequestHandler;

    private final Class<? extends ServerSocketChannel> serverSocketChannelClass;

    private final EventLoopGroup acceptorGroup;

    private final EventLoopGroup workerGroup;

    private final String currentNettyTransport;

    private final CountDownLatch latch;

    NettyServer(final SharableNettyRequestHandler sharableNettyRequestHandler,
                final CountDownLatch latch) {
        this.httpServerConfig = getConfig(HttpServerConfig.class);
        this.sharableNettyRequestHandler = require(sharableNettyRequestHandler);
        final EventLoopGroupFactory eventLoopGroupFactory = getEventLoopGroupFactory();
        this.currentNettyTransport = eventLoopGroupFactory.getCurrentNettyTransport();
        this.serverSocketChannelClass = eventLoopGroupFactory.getServerSocketChannelClass();
        this.acceptorGroup = eventLoopGroupFactory.getRequiredAcceptorEventLoopGroup();
        this.workerGroup = eventLoopGroupFactory.getRequiredWorkerEventLoopGroup(DEFAULT_WORKER_THREAD_QUALIFIER);
        this.latch = latch;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        try {
            final ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(acceptorGroup, workerGroup)
                    .channel(serverSocketChannelClass)
                    .childHandler(
                            new NettyClientConnectionController(
                                    sharableNettyRequestHandler
                            )
                    );
            final NettyConfiguratorController.NettyConfigurator nettyConfigurator = getNettyConfiguratorController().getNettyConfigurator();
            nettyConfigurator.getServerOptions().forEach((o, v) -> bootstrap.option((ChannelOption<Object>) o, v));
            nettyConfigurator.getClientOptions().forEach((o, v) -> bootstrap.childOption((ChannelOption<Object>) o, v));

            final ChannelFuture channelFuture = bootstrap.bind(httpServerConfig.getHost(), httpServerConfig.getPort()).sync();
            logStartedMessage();
            latch.countDown();
            channelFuture.channel().closeFuture().sync();
        } catch (final InterruptedException ignore) {
            LOGGER.info("Retrieved shutdown request ...");
        } finally {
            final Future<?> workerGroupStopFuture = workerGroup.shutdownGracefully();
            final Future<?> serverGroupStopFuture = acceptorGroup.shutdownGracefully()
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
                    () -> currentNettyTransport,
                    () -> (System.nanoTime() - START_TIME_STAMP) / NANOS_IN_1_MILLIS
            );
        } else {
            LOGGER.info(
                    "Server started at ?:? using ? transport.",
                    httpServerConfig::getHost,
                    httpServerConfig::getPort,
                    () -> currentNettyTransport
            );
        }
    }
}
