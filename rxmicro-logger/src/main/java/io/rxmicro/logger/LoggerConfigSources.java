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

import java.util.List;
import java.util.logging.Level;

import static io.rxmicro.logger.LoggerConfigSource.CLASS_PATH_RESOURCE;
import static io.rxmicro.logger.LoggerConfigSource.DEFAULT;
import static io.rxmicro.logger.LoggerConfigSource.TEST_CLASS_PATH_RESOURCE;
import static io.rxmicro.logger.LoggerImplProviderFactory.isLoggerFactoryInitialized;
import static io.rxmicro.logger.internal.jul.InternalLoggerHelper.logInternal;

/**
 * Allows configuring order of logger config sources.
 *
 * @author nedis
 * @since 0.9
 */
public final class LoggerConfigSources {

    // Default logger config source order.
    private static List<LoggerConfigSource> currentLoggerConfigSources = List.of(
            DEFAULT,
            CLASS_PATH_RESOURCE,
            TEST_CLASS_PATH_RESOURCE
    );

    /**
     * Sets custom logger config source order.
     *
     * <p>
     * This method can be used to disable/enable any supported logger config source as well!
     *
     * @param loggerConfigSources custom logger config source order.
     * @throws IllegalArgumentException if {@code loggerConfigSources} is empty array!
     */
    public static void setLoggerConfigSources(final LoggerConfigSource... loggerConfigSources) {
        if (loggerConfigSources.length == 0) {
            throw new IllegalArgumentException("At least one logger config source must be set!");
        }
        if (isLoggerFactoryInitialized()) {
            logInternal(
                    Level.SEVERE,
                    "Logger factory already initialized, so this config will be ignored! " +
                            "To enable this config invoke ?.resetLoggerImplFactory()!",
                    LoggerImplProviderFactory.class.getSimpleName()
            );
        }
        currentLoggerConfigSources = List.of(loggerConfigSources);
    }

    /**
     * Returns the configured logger config sources.
     *
     * @return the configured logger config sources.
     */
    public static List<LoggerConfigSource> getLoggerConfigSources() {
        return currentLoggerConfigSources;
    }

    private LoggerConfigSources() {
    }
}
