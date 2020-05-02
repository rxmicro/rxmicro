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

import io.rxmicro.common.RxMicroException;
import io.rxmicro.common.local.StartTimeStampHelper;
import io.rxmicro.logger.internal.jul.JULLoggerImplProvider;

import static io.rxmicro.common.util.Requires.require;
import static java.util.Objects.requireNonNullElseGet;

/**
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

    public static LoggerImplProvider getLoggerImplFactory() {
        init = true;
        return requireNonNullElseGet(impl, JULLoggerImplProvider::new);
    }

    public static void setLoggerImplFactory(final LoggerImplProvider impl) {
        if (init) {
            throw new RxMicroException("LoggerImplFactory instance already created");
        }
        LoggerImplProviderFactory.impl = require(impl);
    }

    private LoggerImplProviderFactory(){
    }
}
