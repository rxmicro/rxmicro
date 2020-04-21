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

package io.rxmicro.rest.server;

import io.rxmicro.common.local.StartTimeStamp;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.rest.server.local.model.RestControllerRegistrationFilter;

import java.util.Scanner;
import java.util.Set;

import static io.rxmicro.common.util.Formats.formatSize;
import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.rest.server.local.component.RestServerLauncher.launchWithFilter;
import static io.rxmicro.rest.server.local.model.RestControllerRegistrationFilter.createFilter;

/**
 * To run netty successfully it is necessary to add:
 * --add-opens=java.base/jdk.internal.misc=io.netty.common
 * --add-opens=java.base/java.nio=io.netty.common
 * -Dio.netty.tryReflectionSetAccessible=true
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class RxMicro {

    private static final Logger LOGGER = LoggerFactory.getLogger(RxMicro.class);

    static {
        StartTimeStamp.init();
    }

    public static void startRestServerInteractive(final String rootPackage) {
        new InteractiveWrapper(start(createFilter(rootPackage))).waitForQuit();
    }

    public static void startRestServerInteractive(final Class<?> restControllerClass) {
        new InteractiveWrapper(start(createFilter(restControllerClass))).waitForQuit();
    }

    public static void startRestServerInteractive(final Set<Class<?>> restControllerClasses) {
        new InteractiveWrapper(start(createFilter(restControllerClasses))).waitForQuit();
    }

    public static void startRestServerInteractive(final Class<?> restControllerClass,
                                                  final Class<?>... restControllerClasses) {
        new InteractiveWrapper(start(createFilter(restControllerClass, restControllerClasses))).waitForQuit();
    }

    public static ServerInstance startRestServer(final Class<?> restControllerClass) {
        return start(createFilter(restControllerClass));
    }

    public static ServerInstance startRESTServer(final Class<?> restControllerClass) {
        return start(createFilter(restControllerClass));
    }

    public static ServerInstance startRestServer(final Set<Class<?>> restControllerClasses) {
        return start(createFilter(restControllerClasses));
    }

    public static ServerInstance startRESTServer(final Set<Class<?>> restControllerClasses) {
        return start(createFilter(restControllerClasses));
    }

    public static ServerInstance startRestServer(final Class<?> restControllerClass,
                                                 final Class<?>... restControllerClasses) {
        return start(createFilter(restControllerClass, restControllerClasses));
    }

    public static ServerInstance startRESTServer(final Class<?> restControllerClass,
                                                 final Class<?>... restControllerClasses) {
        return start(createFilter(restControllerClass, restControllerClasses));
    }

    public static ServerInstance startRestServer(final String rootPackage) {
        return start(createFilter(rootPackage));
    }

    public static ServerInstance startRESTServer(final String rootPackage) {
        return start(createFilter(rootPackage));
    }

    private static ServerInstance start(final RestControllerRegistrationFilter filter) {
        LOGGER.debug("Received start server request");
        final ServerInstance serverInstance = launchWithFilter(filter).serverInstance();
        printCurrentEnvironment();
        return serverInstance;
    }

    private static void printCurrentEnvironment() {
        if (getConfig(RestServerConfig.class).isShowRuntimeEnv()) {
            final Runtime runtime = Runtime.getRuntime();
            final long totalMemory = runtime.totalMemory();
            final long freeMemory = runtime.freeMemory();
            LOGGER.info("----------------------------------------------------------------------------------");
            LOGGER.info("CPU cores: ?", runtime.availableProcessors());
            LOGGER.info("Total RAM: ?", formatSize(totalMemory));
            LOGGER.info("Free  RAM: ?", formatSize(freeMemory));
            LOGGER.info("Used  RAM: ?", formatSize(totalMemory - freeMemory));
            LOGGER.info("Max   RAM: ?", formatSize(runtime.maxMemory()));
            LOGGER.info("----------------------------------------------------------------------------------");
        }
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    private static final class InteractiveWrapper {

        private final ServerInstance serverInstance;

        private InteractiveWrapper(final ServerInstance serverInstance) {
            this.serverInstance = serverInstance;
        }

        private void waitForQuit() {
            final Set<String> exitCommands = Set.of(
                    "q",
                    "e",
                    "quit",
                    "exit",
                    "close",
                    "shutdown"
            );
            while (true) {
                final String cmd = new Scanner(System.in).nextLine();
                if (exitCommands.contains(cmd)) {
                    try {
                        serverInstance.shutdownAndWait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
        }
    }
}
