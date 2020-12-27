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

import io.rxmicro.logger.Level;
import io.rxmicro.logger.LoggerEvent;
import io.rxmicro.logger.RequestIdSupplier;
import io.rxmicro.logger.impl.AbstractLogger;
import io.rxmicro.logger.internal.jul.config.adapter.RxMicroLogRecord;

import static io.rxmicro.logger.internal.jul.LevelMappings.getJulLevel;

/**
 * Read more:
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.logging/java/util/logging/package-summary.html
 *
 * @author nedis
 * @since 0.1
 */
final class JULLogger extends AbstractLogger {

    private final java.util.logging.Logger logger;

    private final String name;

    JULLogger(final String name) {
        this.logger = java.util.logging.Logger.getLogger(name);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    protected boolean isLevelEnabled(final Level level) {
        return logger.isLoggable(getJulLevel(level));
    }

    @Override
    protected void log(final Level level,
                       final LoggerEvent loggerEvent) {
        final JULLoggerEvent event = (JULLoggerEvent) loggerEvent;
        final String message = event.message != null ? event.message : "null";
        final RxMicroLogRecord record;
        if (event.requestIdSupplier != null) {
            record = new RxMicroLogRecord(event.requestIdSupplier, name, getJulLevel(level), message);
        } else {
            record = new RxMicroLogRecord(name, getJulLevel(level), message);
        }
        if (event.isStackFramePresent()) {
            record.setStackFrame(event.sourceClassName, event.sourceMethodName, event.sourceFileName, event.sourceLineNumber);
        }
        // See LogRecord#MIN_SEQUENTIAL_THREAD_ID and LogRecord#defaultThreadID()
        if (event.threadId != 0 && event.threadId < Integer.MAX_VALUE / 2) {
            record.setThreadID((int) event.threadId);
        }
        if (event.threadName != null) {
            record.setThreadName(event.threadName);
        }
        if (event.throwable != null) {
            record.setThrown(event.throwable);
        }
        logger.log(record);
    }

    @Override
    protected void log(final Level level,
                       final String message) {
        logger.log(new RxMicroLogRecord(name, getJulLevel(level), message));
    }

    @Override
    protected void log(final Level level,
                       final String message,
                       final Throwable throwable) {
        logger.log(new RxMicroLogRecord(name, getJulLevel(level), message, throwable));
    }

    @Override
    protected void log(final RequestIdSupplier requestIdSupplier,
                       final Level level,
                       final String message) {
        logger.log(new RxMicroLogRecord(requestIdSupplier, name, getJulLevel(level), message));
    }

    @Override
    protected void log(final RequestIdSupplier requestIdSupplier,
                       final Level level,
                       final String message,
                       final Throwable throwable) {
        logger.log(new RxMicroLogRecord(requestIdSupplier, name, getJulLevel(level), message, throwable));
    }
}
