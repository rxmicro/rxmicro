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

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.JdkLoggerFactory;
import io.rxmicro.config.ConfigException;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.rest.server.ServerInstance;
import io.rxmicro.rest.server.detail.component.HttpResponseBuilder;
import io.rxmicro.rest.server.local.component.HttpErrorResponseBodyBuilder;
import io.rxmicro.rest.server.local.component.RequestHandler;
import io.rxmicro.rest.server.local.component.ServerFactory;

import java.util.ServiceLoader;
import java.util.concurrent.CountDownLatch;

import static io.rxmicro.common.CommonConstants.RX_MICRO_FRAMEWORK_NAME;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.runtime.local.Implementations.getImplementation;

/**
 * @author nedis
 * @since 0.1
 */
public final class NettyServerFactory implements ServerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerFactory.class);

    private final HttpResponseBuilder responseBuilder =
            new NettyHttpResponseBuilder();

    private final HttpErrorResponseBodyBuilder responseContentBuilder =
            getImplementation(HttpErrorResponseBodyBuilder.class, true, ServiceLoader::load);

    @Override
    public ServerInstance startNewServer(final RequestHandler requestHandler) {
        try {
            initNettyFactories();
            final SharableNettyRequestHandler sharableNettyRequestHandler = new SharableNettyRequestHandler(
                    requestHandler,
                    responseBuilder,
                    responseContentBuilder
            );
            return start(sharableNettyRequestHandler);
        } catch (final ClassNotFoundException ex) {
            throw new ConfigException("Required class not found: " + ex.getMessage());
        }
    }

    private void initNettyFactories() {
        InternalLoggerFactory.setDefaultFactory(JdkLoggerFactory.INSTANCE);
    }

    private ServerInstance start(final SharableNettyRequestHandler sharableNettyRequestHandler)
            throws ClassNotFoundException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Thread thread = new Thread(
                new NettyServer(sharableNettyRequestHandler, latch),
                format("?-NettyServerController", RX_MICRO_FRAMEWORK_NAME)
        );
        thread.start();
        try {
            latch.await();
        } catch (final InterruptedException ex) {
            LOGGER.error(ex, "Waiting for started failed");
        }
        return new ServerInstanceImpl(thread);
    }

    /**
     * @author nedis
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
