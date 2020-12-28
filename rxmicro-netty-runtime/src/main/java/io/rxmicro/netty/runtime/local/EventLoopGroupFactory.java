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

package io.rxmicro.netty.runtime.local;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.kqueue.KQueueSocketChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.JdkLoggerFactory;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.netty.runtime.NettyRuntimeConfig;
import io.rxmicro.netty.runtime.internal.EventLoopGroupFactoryImpl;

import java.util.List;
import java.util.Optional;

import static io.rxmicro.common.util.Environments.isCurrentOsLinux;
import static io.rxmicro.common.util.Environments.isCurrentOsMac;
import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.netty.runtime.local.NettyTransports.getEPollNativeUnavailabilityCause;
import static io.rxmicro.netty.runtime.local.NettyTransports.getKQueueNativeUnavailabilityCause;
import static io.rxmicro.netty.runtime.local.NettyTransports.isEPollNativeAdded;
import static io.rxmicro.netty.runtime.local.NettyTransports.isEPollNativeAvailable;
import static io.rxmicro.netty.runtime.local.NettyTransports.isKQueueNativeAdded;
import static io.rxmicro.netty.runtime.local.NettyTransports.isKQueueNativeAvailable;

/**
 * @author nedis
 * @since 0.8
 */
public abstract class EventLoopGroupFactory {

    static {
        InternalLoggerFactory.setDefaultFactory(JdkLoggerFactory.INSTANCE);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(EventLoopGroupFactory.class);

    private static EventLoopGroupFactory eventLoopGroupFactoryInstance;

    protected final NettyRuntimeConfig nettyRuntimeConfig;

    protected final NettyTransport nettyTransport;

    public static void init() {
        // do nothing. All customization at the static section
    }

    public static EventLoopGroupFactory getEventLoopGroupFactory() {
        if (eventLoopGroupFactoryInstance == null) {
            eventLoopGroupFactoryInstance = new EventLoopGroupFactoryImpl();
        }
        return eventLoopGroupFactoryInstance;
    }

    public static void clearEventLoopGroupFactory() {
        if (eventLoopGroupFactoryInstance != null) {
            eventLoopGroupFactoryInstance.shutdownAll();
            eventLoopGroupFactoryInstance = null;
        }
    }

    protected EventLoopGroupFactory() {
        nettyRuntimeConfig = getConfig(NettyRuntimeConfig.class);
        nettyTransport = resolveNettyTransport();
    }

    public abstract Optional<EventLoopGroup> getSharedAcceptorEventLoopGroup();

    public final EventLoopGroup getRequiredAcceptorEventLoopGroup() {
        return getSharedAcceptorEventLoopGroup().orElseGet(this::createSharedAcceptorEventLoopGroup);
    }

    public abstract Optional<EventLoopGroup> getSharedWorkerEventLoopGroup();

    public final EventLoopGroup getRequiredWorkerEventLoopGroup(final String threadNameQualifier) {
        return getSharedWorkerEventLoopGroup().orElseGet(() -> createWorkerEventLoopGroup(threadNameQualifier));
    }

    public final String getCurrentNettyTransport() {
        return nettyTransport.name();
    }

    public final Class<? extends SocketChannel> getSocketChannelClass() {
        if (nettyTransport == NettyTransport.EPOLL) {
            return EpollSocketChannel.class;
        } else if (nettyTransport == NettyTransport.KQUEUE) {
            return KQueueSocketChannel.class;
        } else {
            return NioSocketChannel.class;
        }
    }

    public final Class<? extends ServerSocketChannel> getServerSocketChannelClass() {
        if (nettyTransport == NettyTransport.EPOLL) {
            return EpollServerSocketChannel.class;
        } else if (nettyTransport == NettyTransport.KQUEUE) {
            return KQueueServerSocketChannel.class;
        } else {
            return NioServerSocketChannel.class;
        }
    }

    public abstract List<Future<?>> shutdownGracefully();

    protected abstract EventLoopGroup createSharedAcceptorEventLoopGroup();

    protected abstract EventLoopGroup createWorkerEventLoopGroup(String threadNameQualifier);

    protected abstract void shutdownAll();

    private NettyTransport resolveNettyTransport() {
        if (isCurrentOsLinux() && isEPollNativeAdded()) {
            if (isEPollNativeAvailable()) {
                return NettyTransport.EPOLL;
            } else {
                final Throwable cause = getEPollNativeUnavailabilityCause();
                LOGGER.warn(cause, "EPoll native added but not available, because ?", cause.getMessage());
                return NettyTransport.NETTY;
            }
        } else if (isCurrentOsMac() && isKQueueNativeAdded()) {
            if (isKQueueNativeAvailable()) {
                return NettyTransport.KQUEUE;
            } else {
                final Throwable cause = getKQueueNativeUnavailabilityCause();
                LOGGER.warn(cause, "KQueue native added but not available, because ?", cause.getMessage());
                return NettyTransport.NETTY;
            }
        } else {
            return NettyTransport.NETTY;
        }
    }

    /**
     * @author nedis
     * @since 0.8
     */
    protected enum NettyTransport {

        NETTY,

        EPOLL,

        KQUEUE
    }
}
