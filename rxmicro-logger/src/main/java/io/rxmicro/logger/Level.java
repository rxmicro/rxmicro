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
 * Supported {@link Logger} levels.
 *
 * @author nedis
 * @see LoggerFactory
 * @see Logger
 * @since 0.1
 */
public enum Level {

    /**
     * Logger is disabled.
     *
     * @see java.util.logging.Level#OFF
     */
    OFF,

    /**
     * Error level.
     *
     * @see java.util.logging.Level#SEVERE
     */
    ERROR,

    /**
     * Warning level.
     *
     * @see java.util.logging.Level#WARNING
     */
    WARN,

    /**
     * Info level.
     *
     * @see java.util.logging.Level#INFO
     */
    INFO,

    /**
     * Debug level.
     *
     * @see java.util.logging.Level#FINE
     */
    DEBUG,

    /**
     * Trace level.
     *
     * @see java.util.logging.Level#FINEST
     */
    TRACE,

    /**
     * All levels are enabled.
     *
     * @see java.util.logging.Level#ALL
     */
    ALL
}
