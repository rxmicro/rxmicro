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
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.rest.server.netty.NettyRestServerConfig;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import static io.rxmicro.common.local.StartTimeStampHelper.START_TIME_STAMP;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.rest.server.netty.internal.util.NettyTransportFactory.getCurrentNettyTransport;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
final class NettyServer implements Runnable {

    private static final AttributeKey<Long> CHANNEL_TTL = AttributeKey.valueOf("CHANNEL_TTL");

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
            final ServerBootstrap b = new ServerBootstrap()
                    .group(serverGroup, workerGroup)
                    .channel(serverSocketChannelClass)
                    .childHandler(new RxMicroChannelInitializer(nettyRestServerConfig));
            nettyRestServerConfig.getServerOptions().forEach((o, v) -> b.option((ChannelOption<Object>) o, v));
            nettyRestServerConfig.getClientOptions().forEach((o, v) -> b.childOption((ChannelOption<Object>) o, v));

            final ChannelFuture f = b.bind(httpServerConfig.getHost(), httpServerConfig.getPort()).sync();
            logStartedMessage();
            latch.countDown();
            f.channel().closeFuture().sync();
        } catch (final InterruptedException e) {
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
                    () -> (System.nanoTime() - START_TIME_STAMP) / 1_000_000
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

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.3
     */
    private static final class RxMicroChannelInitializer extends ChannelInitializer<SocketChannel> {

        private final NettyRestServerConfig nettyRestServerConfig;

        public RxMicroChannelInitializer(final NettyRestServerConfig nettyRestServerConfig) {
            this.nettyRestServerConfig = require(nettyRestServerConfig);
        }

        @Override
        protected void initChannel(final SocketChannel ch) {
            if (LOGGER.isTraceEnabled()) {
                ch.attr(CHANNEL_TTL).set(System.nanoTime());
                LOGGER.trace(
                        "Client connection created: Channel=?, IP=?",
                        ch.id().asShortText(), ch.remoteAddress()
                );
            }
            nettyRestServerConfig.getHandlerSuppliers().forEach(s -> ch.pipeline().addLast(s.get()));
            ch.closeFuture().addListener(future -> {
                        if (LOGGER.isTraceEnabled()) {
                            LOGGER.trace(
                                    "Client connection closed: Channel=?, IP=?, TTL=?",
                                    ch.id().asShortText(),
                                    ch.remoteAddress(),
                                    format(Duration.ofNanos(System.nanoTime() - ch.attr(CHANNEL_TTL).get()))
                            );
                        }
                    }
            );
        }
    }
}
