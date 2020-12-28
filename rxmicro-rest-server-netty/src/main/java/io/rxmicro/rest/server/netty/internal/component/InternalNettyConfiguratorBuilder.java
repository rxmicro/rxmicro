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
import io.rxmicro.rest.server.netty.NettyRestServerConfig;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.rest.server.netty.NettyRestServerConfig.DEFAULT_BACKLOG_SIZE;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.7.2
 */
public class InternalNettyConfiguratorBuilder {

    /**
     * The channel handler name for {@link SharableNettyRequestHandler}.
     */
    protected static final String NETTY_RX_MICRO_REQUEST_HANDLER_NAME = "nettyRxMicroRequestHandler";

    /**
     * Server options.
     */
    protected final Map<ChannelOption<?>, Object> serverOptions = new LinkedHashMap<>(
            Map.of(ChannelOption.SO_BACKLOG, DEFAULT_BACKLOG_SIZE)
    );

    /**
     * Client options.
     */
    protected final Map<ChannelOption<?>, Object> clientOptions = new LinkedHashMap<>();

    /**
     * Handler suppliers.
     */
    protected final List<Map.Entry<String, Supplier<ChannelHandler>>> handlerSuppliers = new ArrayList<>(List.of(
            entry("HttpServerCodec", this::createHttpServerCodec),
            entry("HttpObjectAggregator", this::createHttpObjectAggregator)
    ));

    private HttpServerCodec createHttpServerCodec() {
        final NettyRestServerConfig config = getConfig(NettyRestServerConfig.class);
        return new HttpServerCodec(
                config.getMaxHttpRequestInitialLineLength(),
                config.getMaxHttpRequestHeaderSize(),
                config.getMaxHttpRequestChunkSize(),
                config.isValidateHttpRequestHeaders(),
                config.getInitialHttpRequestBufferSize(),
                config.isAllowDuplicateHttpRequestContentLengths()
        );
    }

    private HttpObjectAggregator createHttpObjectAggregator() {
        final NettyRestServerConfig config = getConfig(NettyRestServerConfig.class);
        return new HttpObjectAggregator(
                config.getMaxHttpRequestContentLength(),
                config.isCloseOnHttpRequestContentExpectationFailed()
        );
    }
}
