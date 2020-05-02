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

package io.rxmicro.rest.server.netty.internal.util;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.rxmicro.rest.server.netty.NettyRestServerConfig;
import io.rxmicro.rest.server.netty.NettyTransport;

import static io.rxmicro.common.util.Environments.isCurrentOsLinux;
import static io.rxmicro.common.util.Environments.isCurrentOsMac;
import static io.rxmicro.rest.server.netty.NettyTransport.AUTO;
import static io.rxmicro.rest.server.netty.NettyTransport.EPOLL;
import static io.rxmicro.rest.server.netty.NettyTransport.KQUEUE;
import static io.rxmicro.rest.server.netty.NettyTransport.NETTY;
import static io.rxmicro.rest.server.netty.internal.util.NettyTransports.getEPollEventLoopGroupClass;
import static io.rxmicro.rest.server.netty.internal.util.NettyTransports.getEPollServerSocketChannelClass;
import static io.rxmicro.rest.server.netty.internal.util.NettyTransports.getKQueueEventLoopGroupClass;
import static io.rxmicro.rest.server.netty.internal.util.NettyTransports.getKQueueServerSocketChannelClass;
import static io.rxmicro.rest.server.netty.internal.util.NettyTransports.isEPollNativeAdded;
import static io.rxmicro.rest.server.netty.internal.util.NettyTransports.isKQueueNativeAdded;
import static io.rxmicro.runtime.local.Instances.instantiate;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class NettyTransportFactory {

    public static NettyTransport getCurrentNettyTransport(final NettyRestServerConfig config) {
        if (config.getTransport() == AUTO) {
            if (isCurrentOsLinux() && isEPollNativeAdded()) {
                return EPOLL;
            } else if (isCurrentOsMac() && isKQueueNativeAdded()) {
                return KQUEUE;
            } else {
                return NETTY;
            }
        } else {
            return config.getTransport();
        }
    }

    public static EventLoopGroup newEventLoopGroup(final NettyRestServerConfig config)
            throws ClassNotFoundException {
        if (config.getTransport() == NETTY) {
            return new NioEventLoopGroup();
        } else if (config.getTransport() == EPOLL) {
            return instantiate(getEPollEventLoopGroupClass());
        } else if (config.getTransport() == KQUEUE) {
            return instantiate(getKQueueEventLoopGroupClass());
        } else {
            if (isCurrentOsLinux() && isEPollNativeAdded()) {
                return instantiate(getEPollEventLoopGroupClass());
            } else if (isCurrentOsMac() && isKQueueNativeAdded()) {
                return instantiate(getKQueueEventLoopGroupClass());
            } else {
                return new NioEventLoopGroup();
            }
        }
    }

    public static Class<? extends ServerSocketChannel> getServerSocketChannelClass(final NettyRestServerConfig config)
            throws ClassNotFoundException {
        if (config.getTransport() == NETTY) {
            return NioServerSocketChannel.class;
        } else if (config.getTransport() == EPOLL) {
            return getEPollServerSocketChannelClass();
        } else if (config.getTransport() == KQUEUE) {
            return getKQueueServerSocketChannelClass();
        } else {
            if (isCurrentOsLinux() && isEPollNativeAdded()) {
                return getEPollServerSocketChannelClass();
            } else if (isCurrentOsMac() && isKQueueNativeAdded()) {
                return getKQueueServerSocketChannelClass();
            } else {
                return NioServerSocketChannel.class;
            }
        }
    }

    private NettyTransportFactory(){
    }
}
