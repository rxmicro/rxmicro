/*
 * Copyright (c) 2020 https://rxmicro.io
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

/**
 * Allows configuring a netty transport. <p>
 * See
 * <a href="https://netty.io/wiki/native-transports.html">
 *     https://netty.io/wiki/native-transports.html
 * </a>
 *
 * @author nedis
 * @since 0.1
 * @see NettyRestServerConfig
 */
public enum NettyTransport {

    /**
     * Auto detect transport.
     * <p>
     * <ul>
     *     <li>If current platform is linux and netty-transport-native-epoll dependency added, then {@link #EPOLL} will be used.</li>
     *     <li>If current platform is osx and netty-transport-native-kqueue dependency added, then {@link #KQUEUE} will be used.</li>
     *     <li>Otherwise cross platform java implementation ({@link #NETTY}) will be used</li>
     * </ul>
     */
    AUTO,

    /**
     * Cross platform java implementation.
     */
    NETTY,

    /**
     * Tries to use netty-transport-native-epoll implementation. <p>
     * See
     * <a href="https://netty.io/wiki/native-transports.html#using-the-linux-native-transport">
     *     https://netty.io/wiki/native-transports.html#using-the-linux-native-transport
     * </a>
     * <p>
     * If native implementation couldn't be used, switches to the cross platform java implementation: {@link #NETTY}
     */
    EPOLL,

    /**
     * Tries to use netty-transport-native-kqueue implementation. <p>
     * See
     * <a href="https://netty.io/wiki/native-transports.html#using-the-macosbsd-native-transport">
     *     https://netty.io/wiki/native-transports.html#using-the-macosbsd-native-transport
     * </a>
     * <p>
     * If native implementation couldn't be used, switches to the cross platform java implementation: {@link #NETTY}
     */
    KQUEUE
}
