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

package io.rxmicro.logger;

/**
 * Defines sources for logger configuration.
 *
 * <p>
 * The RxMicro framework supports the following source of configurations:
 * <ul>
 *     <li>Properties files</li>
 *     <li>Classpath properties resources</li>
 *     <li>Environment variables</li>
 *     <li>Java system properties</li>
 * </ul>
 *
 * @author nedis
 * @since 0.9
 */
public enum LoggerConfigSource {

    /**
     * Default configuration.
     *
     * <pre>
     * <code>
     * .level=INFO
     * handlers=io.rxmicro.logger.jul.SystemConsoleHandler
     * </code>
     * </pre>
     */
    DEFAULT,

    /**
     * Config from {@code classpath:jul.properties}.
     */
    CLASS_PATH_RESOURCE,

    /**
     * Config from {@code classpath:jul.test.properties}.
     */
    TEST_CLASS_PATH_RESOURCE,

    /**
     * Config from {@code $HOME/jul.properties}.
     */
    FILE_AT_THE_HOME_DIR,

    /**
     * Config from {@code ./jul.properties}.
     */
    FILE_AT_THE_CURRENT_DIR,

    /**
     * Config from {@code $HOME/.rxmicro/jul.properties}.
     */
    FILE_AT_THE_RXMICRO_CONFIG_DIR,

    /**
     * Config from env variables: {@code export logger..level=DEBUG}.
     */
    ENVIRONMENT_VARIABLES,

    /**
     * Config from Java system properties: {@code java ... -Dlogger..level=DEBUG ...}.
     */
    JAVA_SYSTEM_PROPERTIES
}
