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
 * {@link Logger} constants.
 *
 * @author nedis
 * @see LoggerFactory
 * @see Logger
 * @since 0.1
 */
public final class Constants {

    /**
     * The prefix for environment variable or java system properties that
     * must be interpret by the RxMicro framework as configuration for logger module.
     *
     * <p>
     * For example:
     * {@code export logger.io.rxmicro.rest.server.level=TRACE}
     * equals to
     * {@code classpath:/jul.properties} resource with the following content:
     * <br>
     * {@code io.rxmicro.rest.server.level=TRACE}
     */
    public static final String LOGGER_VARIABLE_PREFIX = "logger.";

    /**
     * Predefined logger setting that hides the displaying the RxMicro logger configuration properties during start up.
     *
     * <p>
     * This variable has {@code boolean} type.
     * By default the current logger configuration is displayed:
     * <pre>
     * [INFO] global : Using java.util.logging with the following config:
     *
     *   .level=INFO
     *   handlers=io.rxmicro.logger.internal.jul.config.adapter.SystemOutConsoleHandler
     *   io.rxmicro.logger.internal.jul.config.adapter.SystemOutConsoleHandler.level=ALL
     *
     * </pre>
     */
    public static final String CONFIGURATION_PROPERTIES_HIDE = "configuration.properties.hide";

    /**
     * This option configures which type of class name will be used as logger name: simple or full class name.
     *
     * <p>
     * This variable has {@link TrimMode} type.
     *
     * <p>
     *  By default the RxMicro framework uses {@link TrimMode#WITHOUT_TRIM} mode
     */
    public static final String CONFIGURATION_LOGGER_CLASS_NAME_TRIM_MODE = "configuration.logger.class.name.trim.mode";

    /**
     * Configures supported trim modes for logger names.
     */
    public enum TrimMode {

        /**
         * Full class name will be used for all logger names.
         */
        WITHOUT_TRIM,

        /**
         * If class is located at the {@code io.rxmicro.*} package or at any package that belongs to the RxMicro framework dependencies,
         * simple class name will be used,
         * otherwise full class name will be used
         */
        FRAMEWORK_CLASSES_ONLY,

        /**
         * Simple class names will be used for all logger names.
         */
        ALL_CLASSES
    }

    private Constants() {
    }
}
