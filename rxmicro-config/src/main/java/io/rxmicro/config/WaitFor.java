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

package io.rxmicro.config;

import io.rxmicro.config.internal.waitfor.WaitForService;

import static io.rxmicro.config.internal.waitfor.WaitForServiceFactory.createWaitForService;

/**
 * Example of usage:
 *
 * public static void main(final String[] args) {
 * new WaitFor(args).waitFor();
 * startRestServer(MicroService.class);
 * }
 *
 * -----------------------------------------------------------------------------------------------------------------------------------------
 * Command line arguments:
 * java -p lib:. -m my.module/my_package.Launcher wait-for --type=tcp-socket --timeout=30 localhost:12017
 * java -p lib:. -m my.module/my_package.Launcher wait-for localhost:12017
 *
 * -----------------------------------------------------------------------------------------------------------------------------------------
 * Using java system properties:
 *
 * SYS_PROP="-DWAIT_FOR=--type=tcp-socket --timeout=30 localhost:12017"
 * java -p lib:. "$SYS_PROP" -m my.module/my_package.Launcher
 *
 * SYS_PROP="-DWAIT_FOR=localhost:12017"
 * java -p lib:. "$SYS_PROP" -m my.module/my_package.Launcher
 * -----------------------------------------------------------------------------------------------------------------------------------------
 * Using environment variables:
 *
 * export WAIT_FOR="--type=tcp-socket --timeout=30 localhost:12017"
 * java -p lib:. -m my.module/my_package.Launcher
 *
 * export WAIT_FOR=localhost:12017
 * java -p lib:. -m my.module/my_package.Launcher
 * -----------------------------------------------------------------------------------------------------------------------------------------
 *
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.3
 */
public final class WaitFor {

    /**
     * For configuration WaitForService using environment variables or Java system properties, use `WAIT_FOR` name.
     */
    public static final String WAIT_FOR_ENV_VAR_OF_JAVA_SYS_PROP_NAME = "WAIT_FOR";

    /**
     * For configuration WaitForService using command line arguments, use `wait-for` name.
     */
    public static final String WAIT_FOR_COMMAND_LINE_ARG = "wait-for";

    /**
     * Wait for type parameter
     */
    public static final String WAIT_FOR_TYPE_PARAM_NAME = "type";

    /**
     * Default wait for type: tcp-socket
     */
    public static final String WAIT_FOR_TCP_SOCKET_TYPE_NAME = "tcp-socket";

    /**
     * Wait for timeout.
     *
     * Integer value means timeout in seconds, i.e. `--timeout=5` equals to `--timeout=5 seconds`
     *
     * Other units can be used as string using Duration format:
     *
     * `--timeout=PT15M` equals to `--timeout=15 minutes`
     * `--timeout=P2D` equals to `--timeout=2 days`
     *
     * See for detail: {@link java.time.Duration#parse(CharSequence)}
     */
    public static final String WAIT_FOR_TIMEOUT = "timeout";

    /**
     * Default timeout value
     */
    public static final String WAIT_FOR_TIMEOUT_DEFAULT_VALUE_IN_SECONDS = "30";

    private final WaitForService waitForService;

    public WaitFor(final String[] commandLineArgs) {
        waitForService = createWaitForService(commandLineArgs).orElse(null);
    }

    public void start() {
        if (waitForService != null) {
            waitForService.start();
        }
    }
}
