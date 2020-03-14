/*
 * Copyright (c) 2020. http://rxmicro.io
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

import io.rxmicro.config.ConfigException;
import io.rxmicro.http.ProtocolSchema;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.rest.server.RequestIdGeneratorType;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.ServerInstance;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;
import io.rxmicro.rest.server.local.component.RequestHandler;
import io.rxmicro.rest.server.local.component.RequestIdGenerator;
import io.rxmicro.rest.server.local.component.ServerFactory;
import io.rxmicro.rest.server.local.component.impl.FasterButUnSafeRequestIdGenerator;
import io.rxmicro.rest.server.local.component.impl.SafeButSlowerRequestIdGenerator;
import io.rxmicro.rest.server.local.component.impl.TestRequestIdGenerator;
import io.rxmicro.rest.server.netty.NettyRestServerConfig;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.CountDownLatch;

import static io.rxmicro.common.Constants.RX_MICRO_FRAMEWORK_NAME;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.rest.server.RequestIdGeneratorType.FASTER_BUT_UNSAFE;
import static io.rxmicro.rest.server.RequestIdGeneratorType.FOR_TESTS_ONLY;
import static io.rxmicro.rest.server.RequestIdGeneratorType.SAFE_BUT_SLOWER;
import static io.rxmicro.rest.server.netty.internal.util.NettyTransportFactory.getServerSocketChannelClass;
import static io.rxmicro.rest.server.netty.internal.util.NettyTransportFactory.newEventLoopGroup;
import static io.rxmicro.runtime.local.Instances.getImplementation;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class NettyServerFactory implements ServerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerFactory.class);

    private final Map<RequestIdGeneratorType, RequestIdGenerator> requestIdGeneratorMap = Map.of(
            SAFE_BUT_SLOWER, new SafeButSlowerRequestIdGenerator(),
            FASTER_BUT_UNSAFE, new FasterButUnSafeRequestIdGenerator(),
            FOR_TESTS_ONLY, new TestRequestIdGenerator()
    );

    private final HttpResponseBuilder responseBuilder =
            new NettyHttpResponseBuilder();

    private final HttpErrorResponseBodyBuilder responseContentBuilder =
            getImplementation(HttpErrorResponseBodyBuilder.class, true, ServiceLoader::load);

    @Override
    public ServerInstance startNewServer(final RequestHandler requestHandler) {
        try {
            final HttpServerConfig httpServerConfig = getConfig(HttpServerConfig.class);
            if (httpServerConfig.getSchema() != ProtocolSchema.http) {
                throw new ConfigException("Only http schema supported now");
            }
            final RestServerConfig restServerConfig = getConfig(RestServerConfig.class);
            final RequestIdGenerator requestIdGenerator = requestIdGeneratorMap.get(restServerConfig.getGeneratorType());
            final NettyRestServerConfig nettyRestServerConfig =
                    getConfig(NettyRestServerConfig.class).addLast(() ->
                            new NettyRequestHandler(
                                    requestHandler,
                                    requestIdGenerator,
                                    responseBuilder,
                                    responseContentBuilder,
                                    restServerConfig.isReturnGeneratedRequestId()));
            return start(httpServerConfig, nettyRestServerConfig);
        } catch (final ClassNotFoundException e) {
            throw new ConfigException("Required class not found: " + e.getMessage());
        }
    }

    private ServerInstance start(final HttpServerConfig httpServerConfig,
                                 final NettyRestServerConfig nettyRestServerConfig)
            throws ClassNotFoundException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Thread thread = new Thread(
                new NettyServer(
                        httpServerConfig,
                        nettyRestServerConfig,
                        getServerSocketChannelClass(nettyRestServerConfig),
                        newEventLoopGroup(nettyRestServerConfig),
                        newEventLoopGroup(nettyRestServerConfig),
                        latch),
                format("?-NettyServerManager", RX_MICRO_FRAMEWORK_NAME)
        );
        thread.start();
        try {
            latch.await();
        } catch (final InterruptedException e) {
            LOGGER.error(e, "Waiting for started failed");
        }
        return new ServerInstanceImpl(thread);
    }

    /**
     * @author nedis
     * @link http://rxmicro.io
     * @since 0.1
     */
    private static final class ServerInstanceImpl implements ServerInstance {

        private final Thread thread;

        private ServerInstanceImpl(final Thread thread) {
            this.thread = require(thread);
        }

        @Override
        public void shutdown() {
            thread.interrupt();
        }

        @Override
        public void shutdownAndWait() throws InterruptedException {
            shutdown();
            thread.join();
        }
    }
}
