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

package io.rxmicro.rest.client.netty.internal;

import io.rxmicro.rest.client.netty.NettyClientConfiguratorBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author nedis
 * @since 0.8
 */
public class NettyCustomizer {

    protected static final Map<String, NettyClientConfiguratorBuilder> NETTY_CLIENT_CONFIGURATOR_BUILDERS = new ConcurrentHashMap<>();

    protected NettyCustomizer() {
        // This is basic class designed for extension only.
    }
}
