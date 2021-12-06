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

import io.rxmicro.common.local.StartTimeStampHelper;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.rest.server.local.model.RestControllerRegistrationFilter;

import java.util.Scanner;
import java.util.Set;

import static io.rxmicro.common.util.Formats.formatSize;
import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.rest.server.local.component.RestServerLauncher.launchWithFilter;
import static io.rxmicro.rest.server.local.model.RestControllerRegistrationFilter.createFilter;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Utility class that helps to start a HTTP server with REST controllers.
 *
 * <p>
 * <strong>To run netty successfully it is necessary to add the following arguments:</strong>
 * <ul>
 *     <li>{@code --add-opens=java.base/jdk.internal.misc=io.netty.common}</li>
 *     <li>{@code --add-opens=java.base/java.nio=io.netty.common}</li>
 *     <li>{@code -Dio.netty.tryReflectionSetAccessible=true}</li>
 * </ul>
 *
 * @author nedis
 * @since 0.1
 */
public final class RxMicroRestServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RxMicroRestServer.class);

    static {
        StartTimeStampHelper.init();
    }

    /**
     * Starts all REST controllers that are located inside the {@code rootPackage} on HTTP server.
     *
     * <p>
     * <i>Alias for {@link RxMicroRestServer#startRESTServer(String)} method.</i>
     *
     * @param rootPackage root package
     * @return the {@link ServerInstance} to manage the started HTTP server
     */
    public static ServerInstance startRestServer(final String rootPackage) {
        return start(createFilter(rootPackage));
    }

    /**
     * Starts only one REST controller on HTTP server.
     *
     * <p>
     * <i>Alias for {@link RxMicroRestServer#startRESTServer(Class)} method.</i>
     *
     * @param restControllerClass REST controller class to start
     * @return the {@link ServerInstance} to manage the started HTTP server
     */
    public static ServerInstance startRestServer(final Class<?> restControllerClass) {
        return start(createFilter(restControllerClass));
    }

    /**
     * Starts a set of REST controllers on HTTP server.
     *
     * <p>
     * <i>Alias for {@link RxMicroRestServer#startRESTServer(Set)} method.</i>
     *
     * @param restControllerClasses a REST controllers set to start
     * @return the {@link ServerInstance} to manage the started HTTP server
     */
    public static ServerInstance startRestServer(final Set<Class<?>> restControllerClasses) {
        return start(createFilter(restControllerClasses));
    }

    /**
     * Starts an array of REST controllers on HTTP server.
     *
     * <p>
     * <i>Alias for {@link RxMicroRestServer#startRESTServer(Class, Class[])} method.</i>
     *
     * @param restControllerClass a first instance of REST controller to start
     * @param restControllerClasses a REST controllers arrays to start
     * @return the {@link ServerInstance} to manage the started HTTP server
     */
    public static ServerInstance startRestServer(final Class<?> restControllerClass,
                                                 final Class<?>... restControllerClasses) {
        return start(createFilter(restControllerClass, restControllerClasses));
    }

    /**
     * Starts all REST controllers that are located inside the {@code rootPackage} on HTTP server.
     *
     * <p>
     * <i>Alias for {@link RxMicroRestServer#startRestServer(String)} method.</i>
     *
     * @param rootPackage root package
     * @return the {@link ServerInstance} to manage the started HTTP server
     */
    public static ServerInstance startRESTServer(final String rootPackage) {
        return start(createFilter(rootPackage));
    }

    /**
     * Starts only one REST controller on HTTP server.
     *
     * <p>
     * <i>Alias for {@link RxMicroRestServer#startRestServer(Class)} method.</i>
     *
     * @param restControllerClass REST controller class to start
     * @return the {@link ServerInstance} to manage the started HTTP server
     */
    public static ServerInstance startRESTServer(final Class<?> restControllerClass) {
        return start(createFilter(restControllerClass));
    }

    /**
     * Starts a set of REST controllers on HTTP server.
     *
     * <p>
     * <i>Alias for {@link RxMicroRestServer#startRestServer(Set)} method.</i>
     *
     * @param restControllerClasses a REST controllers set to start
     * @return the {@link ServerInstance} to manage the started HTTP server
     */
    public static ServerInstance startRESTServer(final Set<Class<?>> restControllerClasses) {
        return start(createFilter(restControllerClasses));
    }

    /**
     * Starts an array of REST controllers on HTTP server.
     *
     * <p>
     * <i>Alias for {@link RxMicroRestServer#startRestServer(Class, Class[])} method.</i>
     *
     * @param restControllerClass a first instance of REST controller to start
     * @param restControllerClasses a REST controllers arrays to start
     * @return the {@link ServerInstance} to manage the started HTTP server
     */
    public static ServerInstance startRESTServer(final Class<?> restControllerClass,
                                                 final Class<?>... restControllerClasses) {
        return start(createFilter(restControllerClass, restControllerClasses));
    }

    /**
     * Starts all REST controllers that are located inside the {@code rootPackage} on HTTP server.
     *
     * <p>
     * <i>Alias for {@link RxMicroRestServer#startRESTServerInteractive(String)} method.</i>
     *
     * <p>
     * The RxMicro framework creates a thread that binds to the terminal and waits for exit command.
     * Exit command list is defined at {@link ForExitCommandWaiter#exitCommands}.
     *
     * @param rootPackage root package
     */
    public static void startRestServerInteractive(final String rootPackage) {
        new ForExitCommandWaiter(start(createFilter(rootPackage))).waitForQuit();
    }

    /**
     * Starts only one REST controller on HTTP server.
     *
     * <p>
     * <i>Alias for {@link RxMicroRestServer#startRESTServerInteractive(Class)} method.</i>
     *
     * <p>
     * The RxMicro framework creates a thread that binds to the terminal and waits for exit command.
     * Exit command list is defined at {@link ForExitCommandWaiter#exitCommands}.
     *
     * @param restControllerClass REST controller class to start
     */
    public static void startRestServerInteractive(final Class<?> restControllerClass) {
        new ForExitCommandWaiter(start(createFilter(restControllerClass))).waitForQuit();
    }

    /**
     * Starts a set of REST controllers on HTTP server.
     *
     * <p>
     * <i>Alias for {@link RxMicroRestServer#startRESTServerInteractive(Set)} method.</i>
     *
     * <p>
     * The RxMicro framework creates a thread that binds to the terminal and waits for exit command.
     * Exit command list is defined at {@link ForExitCommandWaiter#exitCommands}.
     *
     * @param restControllerClasses a REST controllers set to start
     */
    public static void startRestServerInteractive(final Set<Class<?>> restControllerClasses) {
        new ForExitCommandWaiter(start(createFilter(restControllerClasses))).waitForQuit();
    }

    /**
     * Starts an array of REST controllers on HTTP server.
     *
     * <p>
     * <i>Alias for {@link RxMicroRestServer#startRESTServerInteractive(Class, Class[])} method.</i>
     *
     * <p>
     * The RxMicro framework creates a thread that binds to the terminal and waits for exit command.
     * Exit command list is defined at {@link ForExitCommandWaiter#exitCommands}.
     *
     * @param restControllerClass a first instance of REST controller to start
     * @param restControllerClasses a REST controllers arrays to start
     */
    public static void startRestServerInteractive(final Class<?> restControllerClass,
                                                  final Class<?>... restControllerClasses) {
        new ForExitCommandWaiter(start(createFilter(restControllerClass, restControllerClasses))).waitForQuit();
    }

    /**
     * Starts all REST controllers that are located inside the {@code rootPackage} on HTTP server.
     *
     * <p>
     * <i>Alias for {@link RxMicroRestServer#startRestServerInteractive(String)} method.</i>
     *
     * <p>
     * The RxMicro framework creates a thread that binds to the terminal and waits for exit command.
     * Exit command list is defined at {@link ForExitCommandWaiter#exitCommands}.
     *
     * @param rootPackage root package
     */
    public static void startRESTServerInteractive(final String rootPackage) {
        new ForExitCommandWaiter(start(createFilter(rootPackage))).waitForQuit();
    }

    /**
     * Starts only one REST controller on HTTP server.
     *
     * <p>
     * <i>Alias for {@link RxMicroRestServer#startRestServerInteractive(Class)} method.</i>
     *
     * <p>
     * The RxMicro framework creates a thread that binds to the terminal and waits for exit command.
     * Exit command list is defined at {@link ForExitCommandWaiter#exitCommands}.
     *
     * @param restControllerClass REST controller class to start
     */
    public static void startRESTServerInteractive(final Class<?> restControllerClass) {
        new ForExitCommandWaiter(start(createFilter(restControllerClass))).waitForQuit();
    }

    /**
     * Starts a set of REST controllers on HTTP server.
     *
     * <p>
     * <i>Alias for {@link RxMicroRestServer#startRestServerInteractive(Set)} method.</i>
     *
     * <p>
     * The RxMicro framework creates a thread that binds to the terminal and waits for exit command.
     * Exit command list is defined at {@link ForExitCommandWaiter#exitCommands}.
     *
     * @param restControllerClasses a REST controllers set to start
     */
    public static void startRESTServerInteractive(final Set<Class<?>> restControllerClasses) {
        new ForExitCommandWaiter(start(createFilter(restControllerClasses))).waitForQuit();
    }

    /**
     * Starts an array of REST controllers on HTTP server.
     *
     * <p>
     * <i>Alias for {@link RxMicroRestServer#startRestServerInteractive(Class, Class[])} method.</i>
     *
     * <p>
     * The RxMicro framework creates a thread that binds to the terminal and waits for exit command.
     * Exit command list is defined at {@link ForExitCommandWaiter#exitCommands}.
     *
     * @param restControllerClass a first instance of REST controller to start
     * @param restControllerClasses a REST controllers arrays to start
     */
    public static void startRESTServerInteractive(final Class<?> restControllerClass,
                                                  final Class<?>... restControllerClasses) {
        new ForExitCommandWaiter(start(createFilter(restControllerClass, restControllerClasses))).waitForQuit();
    }

    private static ServerInstance start(final RestControllerRegistrationFilter filter) {
        LOGGER.debug("Received start server request");
        final ServerInstance serverInstance = launchWithFilter(filter).getServerInstance();
        printCurrentEnvironment();
        return serverInstance;
    }

    private static void printCurrentEnvironment() {
        if (getConfig(RestServerConfig.class).isShowRuntimeEnv() && LOGGER.isInfoEnabled()) {
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

    private RxMicroRestServer() {
    }

    /**
     * For exit commands waiter.
     *
     * @author nedis
     * @since 0.1
     */
    private static final class ForExitCommandWaiter {

        private final Set<String> exitCommands = Set.of(
                "q",
                "e",
                "quit",
                "exit",
                "close",
                "shutdown"
        );

        private final ServerInstance serverInstance;

        private ForExitCommandWaiter(final ServerInstance serverInstance) {
            this.serverInstance = serverInstance;
        }

        private void waitForQuit() {
            while (true) {
                final String cmd = new Scanner(System.in, UTF_8).nextLine();
                if (exitCommands.contains(cmd)) {
                    try {
                        serverInstance.shutdownAndWait();
                    } catch (final InterruptedException ex) {
                        LOGGER.warn(ex, "Shutdown interrupted: ?", ex.getMessage());
                    }
                    return;
                }
            }
        }
    }
}
