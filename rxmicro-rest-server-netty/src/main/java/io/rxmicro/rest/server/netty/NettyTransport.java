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
 * Allows to configure netty transport.
 * See https://netty.io/wiki/native-transports.html
 *
 * @author nedis
 * @link https://rxmicro.io
 * @link https://netty.io/wiki/native-transports.html
 * @since 0.1
 */
public enum NettyTransport {

    /**
     * Auto detect transport.
     * <p>
     * If current platform is linux and netty-transport-native-epoll dependency added, then E_POLL will be used
     * If current platform is osx and netty-transport-native-kqueue dependency added, then K_QUEUE will be used
     * Otherwise cross platform java implementation will be used
     */
    AUTO,

    /**
     * Cross platform java implementation
     */
    NETTY,

    /**
     * Try to use netty-transport-native-epoll implementation.
     * (See https://netty.io/wiki/native-transports.html#using-the-linux-native-transport)
     * If native implementation couldn't be used, switch to the cross platform java implementation
     *
     * @link https://netty.io/wiki/native-transports.html#using-the-linux-native-transport
     */
    EPOLL,

    /**
     * Try to use netty-transport-native-kqueue implementation.
     * (See https://netty.io/wiki/native-transports.html#using-the-macosbsd-native-transport)
     * If native implementation couldn't be used, switch to the cross platform java implementation
     *
     * @link https://netty.io/wiki/native-transports.html#using-the-macosbsd-native-transport
     */
    KQUEUE
}
