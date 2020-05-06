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

package io.rxmicro.logger.impl;

import io.rxmicro.common.InvalidStateException;
import io.rxmicro.common.local.StartTimeStampHelper;
import io.rxmicro.logger.internal.jul.JULLoggerImplProvider;

import static io.rxmicro.common.util.Requires.require;
import static java.util.Objects.requireNonNullElseGet;

/**
 * Factory class that can be used for retrieving the {@link LoggerImplProvider} instance.
 * <p>
 * Developer must use the {@link io.rxmicro.logger.LoggerFactory} utility class instead this one to get an instance
 * of the {@link io.rxmicro.logger.Logger} for his (her) production code.
 * <p>
 * {@link LoggerImplProviderFactory} can be used by developer only for testing purposes.
 * For example to create a {@link io.rxmicro.logger.Logger} mock.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class LoggerImplProviderFactory {

    private static LoggerImplProvider impl;

    private static boolean init;

    static {
        StartTimeStampHelper.init();
    }

    /**
     * Returns the {@link LoggerImplProvider} instance that configured by default
     *
     * @return the {@link LoggerImplProvider} instance that configured by default
     */
    public static LoggerImplProvider getLoggerImplFactory() {
        init = true;
        return requireNonNullElseGet(impl, JULLoggerImplProvider::new);
    }

    /**
     * Sets the {@link LoggerImplProvider} instance.
     * <p>
     * This method is useful for testing purposes, because it allows replacing the default {@link LoggerImplProvider} by the mock.
     *
     * @param impl the {@link LoggerImplProvider} instance
     */
    public static void setLoggerImplFactory(final LoggerImplProvider impl) {
        if (init) {
            throw new InvalidStateException("LoggerImplFactory instance already created");
        }
        LoggerImplProviderFactory.impl = require(impl);
    }

    private LoggerImplProviderFactory() {
    }
}
