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

package io.rxmicro.logger.internal;

import io.rxmicro.common.util.Reflections;
import io.rxmicro.logger.impl.LoggerImplProvider;
import io.rxmicro.logger.internal.jul.JULLoggerImplProvider;

/**
 * @author nedis
 * @since 0.7.3
 */
public final class LoggerImplProviderFactoryHelper {

    public static LoggerImplProvider createAndSetupLoggerImplProvider(final Class<? extends LoggerImplProvider> loggerImplProviderClass) {
        final LoggerImplProvider provider = instantiate(loggerImplProviderClass);
        try {
            provider.setup();
        } catch (final Throwable throwable) {
            System.err.println("Can't setup logger impl factory: " + throwable.getMessage());
            throwable.printStackTrace();
        }
        return provider;
    }

    private static LoggerImplProvider instantiate(final Class<? extends LoggerImplProvider> loggerImplProviderClass) {
        if (loggerImplProviderClass == JULLoggerImplProvider.class) {
            return new JULLoggerImplProvider();
        } else {
            return Reflections.instantiate(loggerImplProviderClass);
        }
    }

    private LoggerImplProviderFactoryHelper() {
    }
}
