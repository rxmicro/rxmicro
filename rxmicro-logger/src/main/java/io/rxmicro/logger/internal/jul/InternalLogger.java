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

package io.rxmicro.logger.internal.jul;

import io.rxmicro.logger.internal.jul.config.adapter.RxMicroLogRecord;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;

/**
 * @author nedis
 * @since 0.7
 */
public final class InternalLogger {

    private static final Logger LOGGER = Logger.getGlobal();

    public static void logInternal(final Level level,
                                   final String msg) {
        LOGGER.log(new RxMicroLogRecord(GLOBAL_LOGGER_NAME, level, msg));
    }

    private InternalLogger() {
    }
}
