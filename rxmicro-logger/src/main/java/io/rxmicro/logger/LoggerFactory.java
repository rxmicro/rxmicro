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
import io.rxmicro.logger.impl.LoggerImplFactory;

import static io.rxmicro.logger.impl.LoggerImplFactoryProvider.getLoggerImplFactory;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class LoggerFactory {

    private static final LoggerImplFactory LOGGER_IMPL_FACTORY;

    static {
        StartTimeStampHelper.init();
        LOGGER_IMPL_FACTORY = getLoggerImplFactory();
        try {
            LOGGER_IMPL_FACTORY.setup();
        } catch (final Throwable throwable) {
            System.err.println("Can't setup logger impl factory: " + throwable.getMessage());
            throwable.printStackTrace();
        }
    }

    public static Logger getLogger(final Class<?> className) {
        return LOGGER_IMPL_FACTORY.getLogger(className.getName());
    }

    public static Logger getLogger(final String name) {
        return LOGGER_IMPL_FACTORY.getLogger(name);
    }

    private LoggerFactory() {
    }
}
