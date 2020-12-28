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

package io.rxmicro.netty.runtime.internal;

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;

/**
 * @author nedis
 * @since 0.8
 */
public final class SharedUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    public static final SharedUncaughtExceptionHandler SHARED_UNCAUGHT_EXCEPTION_HANDLER = new SharedUncaughtExceptionHandler();

    private static final Logger LOGGER = LoggerFactory.getLogger(SharedUncaughtExceptionHandler.class);

    private SharedUncaughtExceptionHandler() {
    }

    @Override
    public void uncaughtException(final Thread thread,
                                  final Throwable throwable) {
        LOGGER.error(LoggerFactory.newLoggerEventBuilder()
                .setThread(thread)
                .setMessage("'?' thread throws an uncaught throwable: ?", thread, throwable.getMessage())
                .setThrowable(throwable)
                .build());
    }
}
