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

package io.rxmicro.config;

import io.rxmicro.config.internal.waitfor.WaitForService;

import static io.rxmicro.config.internal.waitfor.WaitForServiceFactory.createWaitForService;

/**
 * Allows suspending current thread until configured service is up.
 *
 * <p>
 * Example of usage:
 * <pre><code>
 * public static void main(final String[] args) {
 *      new WaitFor(args).waitFor();
 *      startRestServer(MicroService.class);
 * }
 * </code></pre>
 *
 * <p>
 * <strong>Command line arguments:</strong>
 *
 * <p>
 * <ul>
 *     <li>{@code java -p lib:. -m my.module/my_package.Launcher wait-for --type=tcp-socket --timeout=30 localhost:12017}</li>
 *     <li>{@code java -p lib:. -m my.module/my_package.Launcher wait-for localhost:12017 }</li>
 * </ul>
 *
 * <p>
 * <strong>Using java system properties:</strong>
 *
 * <p>
 * <ul>
 *     <li>{@code SYS_PROP="-DWAIT_FOR=--type=tcp-socket --timeout=30 localhost:12017"}</li>
 *     <li>{@code java -p lib:. "$SYS_PROP" -m my.module/my_package.Launcher }</li>
 *     <li></li>
 *     <li>{@code SYS_PROP="-DWAIT_FOR=localhost:12017" }</li>
 *     <li>{@code java -p lib:. "$SYS_PROP" -m my.module/my_package.Launcher }</li>
 * </ul>
 *
 * <p>
 * <strong>Using environment variables:</strong>
 *
 * <p>
 * <ul>
 *     <li>{@code export WAIT_FOR="--type=tcp-socket --timeout=30 localhost:12017"}</li>
 *     <li>{@code java -p lib:. -m my.module/my_package.Launcher}</li>
 *     <li></li>
 *     <li>{@code export WAIT_FOR=localhost:12017}</li>
 *     <li>{@code java -p lib:. -m my.module/my_package.Launcher}</li>
 * </ul>
 *
 * @author nedis
 * @see java.net.Socket
 * @since 0.3
 */
public final class WaitFor {

    /**
     * For configuration WaitForService using environment variables or Java system properties, use {@code `WAIT_FOR`} name.
     */
    public static final String WAIT_FOR_ENV_VAR_OR_JAVA_SYS_PROP_NAME = "WAIT_FOR";

    /**
     * For configuration WaitForService using command line arguments, use {@code `wait-for`} name.
     */
    public static final String WAIT_FOR_COMMAND_LINE_ARG = "wait-for";

    /**
     * Wait for type parameter.
     */
    public static final String WAIT_FOR_TYPE_PARAM_NAME = "type";

    /**
     * Wait for delimiter character.
     */
    public static final String WAIT_FOR_DELIMITER = "&";

    /**
     * Wait for param prefix.
     */
    public static final String WAIT_FOR_PARAM_PREFIX = "--";

    /**
     * Default wait for type: {@code tcp-socket}.
     */
    public static final String WAIT_FOR_TCP_SOCKET_TYPE_NAME = "tcp-socket";

    /**
     * Wait for timeout.
     *
     * <p>
     * Integer value means timeout in seconds, i.e. {@code `--timeout=5`} equals to {@code `--timeout=5 seconds`}
     *
     * <p>
     * Other units can be used as string using Duration format:
     * <ul>
     *     <li>{@code `--timeout=PT15M`} equals to {@code `--timeout=15 minutes`}</li>
     *     <li>{@code `--timeout=P2D`} equals to {@code `--timeout=2 days`}</li>
     * </ul>
     *
     * <p>
     * See for detail: {@link java.time.Duration#parse(CharSequence)}
     */
    public static final String WAIT_FOR_TIMEOUT = "timeout";

    /**
     * Default timeout value.
     */
    public static final String WAIT_FOR_TIMEOUT_DEFAULT_VALUE_IN_SECONDS = "10";

    private final WaitForService waitForService;

    /**
     * Creates a WaitFor instance.
     *
     * @param commandLineArgs command line arguments
     * @throws ConfigException if the provided arguments are not valid
     */
    public WaitFor(final String commandLineArgs) {
        if (commandLineArgs.indexOf(' ') == -1) {
            waitForService = createWaitForService(commandLineArgs).orElse(null);
        } else {
            waitForService = createWaitForService(commandLineArgs.split(" ")).orElse(null);
        }
    }

    /**
     * Creates a WaitFor instance.
     *
     * @param commandLineArgs command line arguments
     * @throws ConfigException if the provided arguments are not valid
     */
    public WaitFor(final String... commandLineArgs) {
        waitForService = createWaitForService(commandLineArgs).orElse(null);
    }

    /**
     * Starts the suspending the current thread until configured service is up.
     *
     * <p>
     * If current environment does not enable {@link WaitFor} this method does nothing.
     *
     * @throws ServiceYetNotAvailableException if configured service is not up.
     */
    public void start() {
        if (waitForService != null) {
            waitForService.start();
        }
    }
}
