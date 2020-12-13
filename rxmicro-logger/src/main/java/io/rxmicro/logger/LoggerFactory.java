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

import io.rxmicro.common.local.StartTimeStampHelper;
import io.rxmicro.logger.impl.LoggerImplProvider;

import static io.rxmicro.logger.LoggerImplProviderFactory.getLoggerImplFactory;

/**
 * Factory that must be used to get a {@link Logger} instance.
 *
 * @author nedis
 * @see Logger
 * @see Level
 * @see LoggerImplProvider
 * @see LoggerImplProviderFactory
 * @since 0.1
 */
public final class LoggerFactory {

    private static final LoggerImplProvider LOGGER_IMPL_PROVIDER;

    static {
        StartTimeStampHelper.init();
        LOGGER_IMPL_PROVIDER = getLoggerImplFactory();
    }

    /**
     * Returns the {@link Logger} instance by class name.
     *
     * @param className the class name
     * @return the {@link Logger} instance
     */
    public static Logger getLogger(final Class<?> className) {
        return LOGGER_IMPL_PROVIDER.getLogger(className);
    }

    /**
     * Returns the {@link Logger} instance by name.
     *
     * @param name the name
     * @return the {@link Logger} instance
     */
    public static Logger getLogger(final String name) {
        return LOGGER_IMPL_PROVIDER.getLogger(name);
    }

    private LoggerFactory() {
    }
}
