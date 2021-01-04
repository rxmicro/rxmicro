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

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.rxmicro.http.ProtocolSchema;
import io.rxmicro.rest.client.LeasingStrategy;
import io.rxmicro.rest.client.RestClientConfig;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.util.Map;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
import static io.rxmicro.common.CommonConstants.RX_MICRO_FRAMEWORK_NAME;
import static io.rxmicro.netty.runtime.local.EventLoopGroupFactory.getEventLoopGroupFactory;
import static io.rxmicro.rest.client.netty.internal.NettyClientConfiguratorBuilderImpl.PROCESSED_NAMESPACES;
import static io.rxmicro.rest.client.netty.internal.NettyCustomizer.NETTY_CLIENT_CONFIGURATOR_BUILDERS;

/**
 * @author nedis
 * @since 0.8
 */
final class NettyHttpClientBuilder {

    private static final String DEFAULT_CONNECTION_POOL_NAME = RX_MICRO_FRAMEWORK_NAME + "-netty-http-pool";

    private static final String DEFAULT_WORKER_THREAD_QUALIFIER = "http-client";

    private final RestClientConfig config;

    private final String namespace;

    NettyHttpClientBuilder(final RestClientConfig config,
                           final String namespace) {
        this.config = config;
        this.namespace = namespace;
    }

    public HttpClient build() {
        final ConnectionProvider connectionProvider = buildConnectionProvider();
        HttpClient client = HttpClient.create(connectionProvider)
                .host(config.getHost())
                .port(config.getPort())
                .followRedirect(config.isFollowRedirects())
                .protocol(HttpProtocol.HTTP11)
                .runOn(getEventLoopGroupFactory().getRequiredWorkerEventLoopGroup(DEFAULT_WORKER_THREAD_QUALIFIER));
        client = customizeClient(client);
        if (config.getSchema() == ProtocolSchema.HTTPS) {
            final SslContextBuilder sslContextBuilder = SslContextBuilder.forClient();
            client = client.secure(spec -> spec.sslContext(sslContextBuilder));
        }
        if (!config.getConnectTimeout().isZero()) {
            client = client.option(CONNECT_TIMEOUT_MILLIS, (int) config.getConnectTimeout().toMillis());
        }
        if (!config.getRequestTimeout().isZero()) {
            client = client.responseTimeout(config.getRequestTimeout());
        }
        PROCESSED_NAMESPACES.add(namespace);
        return client;
    }

    private ConnectionProvider buildConnectionProvider() {
        final ConnectionProvider.Builder builder = ConnectionProvider.builder(DEFAULT_CONNECTION_POOL_NAME)
                .evictInBackground(config.getEvictionInterval())
                .maxConnections(config.getMaxConnections())
                .pendingAcquireTimeout(config.getPendingAcquireTimeout());

        config.getPendingAcquireMaxCount().ifPresent(builder::pendingAcquireMaxCount);
        config.getMaxIdleTime().ifPresent(builder::maxIdleTime);
        config.getMaxLifeTime().ifPresent(builder::maxLifeTime);

        if (config.getLeasingStrategy() == LeasingStrategy.FIFO) {
            builder.fifo();
        } else {
            // if (config.getLeasingStrategy() == LeasingStrategy.LIFO)
            builder.lifo();
        }
        return builder.build();
    }

    @SuppressWarnings("unchecked")
    private HttpClient customizeClient(final HttpClient client) {
        final NettyClientConfiguratorBuilderImpl builder =
                (NettyClientConfiguratorBuilderImpl) NETTY_CLIENT_CONFIGURATOR_BUILDERS.remove(namespace);
        if (builder == null || builder.clientOptions.isEmpty() && builder.httpResponseDecoderSpec == null) {
            return client;
        } else {
            HttpClient newClient = client;
            for (final Map.Entry<ChannelOption<?>, Object> entry : builder.clientOptions.entrySet()) {
                newClient = newClient.option((ChannelOption<Object>) entry.getKey(), entry.getValue());
            }
            if (builder.httpResponseDecoderSpec != null) {
                newClient = newClient.httpResponseDecoder(spec -> builder.httpResponseDecoderSpec);
            }
            return newClient;
        }
    }
}
