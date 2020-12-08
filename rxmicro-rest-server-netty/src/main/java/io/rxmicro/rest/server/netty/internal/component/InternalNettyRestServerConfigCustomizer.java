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
import io.netty.channel.ChannelOption;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static io.rxmicro.rest.server.netty.local.NettyRestServerConfigCustomizerState.setNettyRestServerConfigCustomizerState;
import static io.rxmicro.rest.server.netty.local.NettyRestServerConfigCustomizerState.validateNettyRestServerConfigCustomizerState;

/**
 * @author nedis
 * @since 0.7.2
 */
public class InternalNettyRestServerConfigCustomizer {

    /**
     * Default backlog size.
     */
    public static final int DEFAULT_BACKLOG_SIZE = 128;

    /**
     * Default aggregator content length in bytes.
     */
    public static final int DEFAULT_AGGREGATOR_CONTENT_LENGTH_IN_BYTES = 64 * 1024;

    protected static final Map<ChannelOption<?>, Object> SERVER_OPTIONS = new LinkedHashMap<>(
            Map.of(ChannelOption.SO_BACKLOG, DEFAULT_BACKLOG_SIZE)
    );

    protected static final Map<ChannelOption<?>, Object> CLIENT_OPTIONS = new LinkedHashMap<>();

    protected static final List<Supplier<ChannelHandler>> HANDLER_SUPPLIERS = new ArrayList<>(List.of(
            HttpServerCodec::new,
            () -> new HttpObjectAggregator(DEFAULT_AGGREGATOR_CONTENT_LENGTH_IN_BYTES, true)
    ));

    protected static void validateState() {
        validateNettyRestServerConfigCustomizerState();
    }

    static Map<ChannelOption<?>, Object> getServerOptions() {
        setNettyRestServerConfigCustomizerState(true);
        return SERVER_OPTIONS;
    }

    static Map<ChannelOption<?>, Object> getClientOptions() {
        setNettyRestServerConfigCustomizerState(true);
        return CLIENT_OPTIONS;
    }

    static List<Supplier<ChannelHandler>> getHandlerSuppliers() {
        setNettyRestServerConfigCustomizerState(true);
        return HANDLER_SUPPLIERS;
    }

    protected InternalNettyRestServerConfigCustomizer() {
        // This is basic class designed for extension only.
    }
}
