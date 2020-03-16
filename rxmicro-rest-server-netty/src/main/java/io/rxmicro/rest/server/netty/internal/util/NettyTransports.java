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

import io.netty.channel.MultithreadEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @link https://netty.io/wiki/native-transports.html
 * @since 0.1
 */
@SuppressWarnings("unchecked")
final class NettyTransports {

    // E_POLL
    private static final String E_POLL_PACKAGE = "io.netty.channel.epoll.";

    private static final String E_POLL_EVENT_LOOP_GROUP_CLASS = E_POLL_PACKAGE + "EpollEventLoopGroup";

    private static final String E_POLL_SERVER_SOCKET_CHANNEL_CLASS = E_POLL_PACKAGE + "EpollServerSocketChannel";

    // K_QUEUE
    private static final String K_QUEUE_PACKAGE = "io.netty.channel.kqueue.";

    private static final String K_QUEUE_EVENT_LOOP_GROUP_CLASS = K_QUEUE_PACKAGE + "KQueueEventLoopGroup";

    private static final String K_QUEUE_SERVER_SOCKET_CHANNEL_CLASS = K_QUEUE_PACKAGE + "KQueueServerSocketChannel";

    private static final Map<String, Class<?>> CLASSES_CACHE = new HashMap<>();

    static boolean isEPollNativeAdded() {
        try {
            getClass(E_POLL_EVENT_LOOP_GROUP_CLASS);
            return true;
        } catch (final ClassNotFoundException e) {
            return false;
        }
    }

    static Class<? extends MultithreadEventLoopGroup> getEPollEventLoopGroupClass() throws ClassNotFoundException {
        return (Class<? extends MultithreadEventLoopGroup>) getClass(E_POLL_EVENT_LOOP_GROUP_CLASS);
    }

    static Class<? extends ServerSocketChannel> getEPollServerSocketChannelClass() throws ClassNotFoundException {
        return (Class<? extends ServerSocketChannel>) getClass(E_POLL_SERVER_SOCKET_CHANNEL_CLASS);
    }

    static boolean isKQueueNativeAdded() {
        try {
            getClass(K_QUEUE_EVENT_LOOP_GROUP_CLASS);
            return true;
        } catch (final ClassNotFoundException e) {
            return false;
        }
    }

    static Class<? extends MultithreadEventLoopGroup> getKQueueEventLoopGroupClass() throws ClassNotFoundException {
        return (Class<? extends MultithreadEventLoopGroup>) getClass(K_QUEUE_EVENT_LOOP_GROUP_CLASS);
    }

    static Class<? extends ServerSocketChannel> getKQueueServerSocketChannelClass() throws ClassNotFoundException {
        return (Class<? extends ServerSocketChannel>) getClass(K_QUEUE_SERVER_SOCKET_CHANNEL_CLASS);
    }

    private static Class<?> getClass(final String className) throws ClassNotFoundException {
        Class<?> cl = CLASSES_CACHE.get(className);
        if (cl == null) {
            cl = Class.forName(className);
            CLASSES_CACHE.put(className, cl);
        }
        return cl;
    }
}
