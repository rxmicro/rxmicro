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

import io.rxmicro.logger.Level;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerEvent;
import io.rxmicro.logger.RequestIdSupplier;

import java.util.Arrays;
import java.util.function.Supplier;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.logger.Level.DEBUG;
import static io.rxmicro.logger.Level.ERROR;
import static io.rxmicro.logger.Level.INFO;
import static io.rxmicro.logger.Level.TRACE;
import static io.rxmicro.logger.Level.WARN;

/**
 * Abstract logger implementation that delegates the log events to `log` methods.
 *
 * @author nedis
 * @see io.rxmicro.logger.LoggerFactory
 * @since 0.1
 */
public abstract class AbstractLogger implements Logger {

    /**
     * Returns {@code true} if the specified level is enabled.
     *
     * @param level the specified level.
     * @return {@code true} if the specified level is enabled.
     */
    protected abstract boolean isLevelEnabled(Level level);

    /**
     * Logs the specified {@code loggerEvent} with the specified level.
     *
     * @param level       the specified level.
     * @param loggerEvent the specified logger event.
     * @throws ClassCastException if provided {@code loggerEvent} is not valid
     */
    protected abstract void log(Level level,
                                LoggerEvent loggerEvent);

    /**
     * Logs the specified message with the specified level.
     *
     * @param level   the specified level.
     * @param message the specified message.
     */
    protected abstract void log(Level level,
                                String message);

    /**
     * Logs the specified message with the specified level and prints stacktrace for the specified throwable.
     *
     * @param level     the specified level.
     * @param message   the specified message.
     * @param throwable the specified throwable.
     */
    protected abstract void log(Level level,
                                String message,
                                Throwable throwable);

    /**
     * Logs the specified message with the specified level.
     *
     * @param requestIdSupplier the request id supplier.
     * @param level             the specified level.
     * @param message           the specified message.
     */
    protected abstract void log(RequestIdSupplier requestIdSupplier,
                                Level level,
                                String message);

    /**
     * Logs the specified message with the specified level and prints stacktrace for the specified throwable.
     *
     * @param requestIdSupplier the request id supplier.
     * @param level             the specified level.
     * @param message           the specified message.
     * @param throwable         the specified throwable.
     */
    protected abstract void log(RequestIdSupplier requestIdSupplier,
                                Level level,
                                String message,
                                Throwable throwable);

    @Override
    public final boolean isTraceEnabled() {
        return isLevelEnabled(TRACE);
    }

    @Override
    public final void trace(final LoggerEvent loggerEvent) {
        if (isLevelEnabled(TRACE)) {
            log(TRACE, loggerEvent);
        }
    }

    @Override
    public final void trace(final String msg) {
        if (isLevelEnabled(TRACE)) {
            log(TRACE, msg);
        }
    }

    @Override
    public final void trace(final String msg,
                            final Object arg1) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String msg,
                            final Object arg1,
                            final Object arg2) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String format,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(format, arg1, arg2, arg3);
            log(TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String format,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(format, arg1, arg2, arg3, arg4);
            log(TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String format,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4,
                            final Object arg5) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(format, arg1, arg2, arg3, arg4, arg5);
            log(TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String format,
                            final Object... arguments) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(format, arguments);
            log(TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String msg,
                            final Supplier<?> arg1) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4,
                            final Supplier<?> arg5) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String msg,
                            final Supplier<?>... suppliers) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg) {
        if (isLevelEnabled(TRACE)) {
            log(TRACE, msg, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Object arg1) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4,
                            final Object arg5) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Object... arguments) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4,
                            final Supplier<?> arg5) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Supplier<?>... suppliers) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final String msg) {
        if (isLevelEnabled(TRACE)) {
            log(requestIdSupplier, TRACE, msg);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Object arg1) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Object arg1,
                            final Object arg2) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4,
                            final Object arg5) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Object... arguments) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(requestIdSupplier, TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Supplier<?> arg1) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4,
                            final Supplier<?> arg5) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Supplier<?>... suppliers) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(requestIdSupplier, TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg) {
        if (isLevelEnabled(TRACE)) {
            log(requestIdSupplier, TRACE, msg, throwable);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Object arg1) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4,
                            final Object arg5) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Object... arguments) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(requestIdSupplier, TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4,
                            final Supplier<?> arg5) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Supplier<?>... suppliers) {
        if (isLevelEnabled(TRACE)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(requestIdSupplier, TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final boolean isDebugEnabled() {
        return isLevelEnabled(DEBUG);
    }

    @Override
    public final void debug(final LoggerEvent loggerEvent) {
        if (isLevelEnabled(DEBUG)) {
            log(DEBUG, loggerEvent);
        }
    }

    @Override
    public final void debug(final String msg) {
        if (isLevelEnabled(DEBUG)) {
            log(DEBUG, msg);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Object arg1) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Object arg1,
                            final Object arg2) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4,
                            final Object arg5) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Object... arguments) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Supplier<?> arg1) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4,
                            final Supplier<?> arg5) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Supplier<?>... suppliers) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg) {
        if (isLevelEnabled(DEBUG)) {
            log(DEBUG, msg, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Object arg1) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4,
                            final Object arg5) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Object... arguments) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4,
                            final Supplier<?> arg5) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Supplier<?>... suppliers) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final String msg) {
        if (isLevelEnabled(DEBUG)) {
            log(requestIdSupplier, DEBUG, msg);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Object arg1) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Object arg1,
                            final Object arg2) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4,
                            final Object arg5) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Object... arguments) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(requestIdSupplier, DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Supplier<?> arg1) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4,
                            final Supplier<?> arg5) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Supplier<?>... suppliers) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(requestIdSupplier, DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg) {
        if (isLevelEnabled(DEBUG)) {
            log(requestIdSupplier, DEBUG, msg, throwable);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Object arg1) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4,
                            final Object arg5) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Object... arguments) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(requestIdSupplier, DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4,
                            final Supplier<?> arg5) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Supplier<?>... suppliers) {
        if (isLevelEnabled(DEBUG)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(requestIdSupplier, DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final boolean isInfoEnabled() {
        return isLevelEnabled(INFO);
    }

    @Override
    public final void info(final LoggerEvent loggerEvent) {
        if (isLevelEnabled(INFO)) {
            log(INFO, loggerEvent);
        }
    }

    @Override
    public final void info(final String msg) {
        if (isLevelEnabled(INFO)) {
            log(INFO, msg);
        }
    }

    @Override
    public final void info(final String msg,
                           final Object arg1) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Object arg1,
                           final Object arg2) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4,
                           final Object arg5) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Object... arguments) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Supplier<?> arg1) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4,
                           final Supplier<?> arg5) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Supplier<?>... suppliers) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(INFO, finalMessage);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg) {
        if (isLevelEnabled(INFO)) {
            log(INFO, msg, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Object arg1) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4,
                           final Object arg5) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Object... arguments) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4,
                           final Supplier<?> arg5) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Supplier<?>... suppliers) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final String msg) {
        if (isLevelEnabled(INFO)) {
            log(requestIdSupplier, INFO, msg);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Object arg1) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, INFO, finalMessage);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Object arg1,
                           final Object arg2) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, INFO, finalMessage);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, INFO, finalMessage);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, INFO, finalMessage);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4,
                           final Object arg5) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, INFO, finalMessage);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Object... arguments) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(requestIdSupplier, INFO, finalMessage);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Supplier<?> arg1) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, INFO, finalMessage);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, INFO, finalMessage);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, INFO, finalMessage);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, INFO, finalMessage);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4,
                           final Supplier<?> arg5) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, INFO, finalMessage);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Supplier<?>... suppliers) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(requestIdSupplier, INFO, finalMessage);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg) {
        if (isLevelEnabled(INFO)) {
            log(requestIdSupplier, INFO, msg, throwable);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Object arg1) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4,
                           final Object arg5) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Object... arguments) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(requestIdSupplier, INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4,
                           final Supplier<?> arg5) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Supplier<?>... suppliers) {
        if (isLevelEnabled(INFO)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(requestIdSupplier, INFO, finalMessage, throwable);
        }
    }

    @Override
    public final boolean isWarnEnabled() {
        return isLevelEnabled(WARN);
    }

    @Override
    public final void warn(final LoggerEvent loggerEvent) {
        if (isLevelEnabled(WARN)) {
            log(WARN, loggerEvent);
        }
    }

    @Override
    public final void warn(final String msg) {
        if (isLevelEnabled(WARN)) {
            log(WARN, msg);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Object arg1) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Object arg1,
                           final Object arg2) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4,
                           final Object arg5) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Object... arguments) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Supplier<?> arg1) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4,
                           final Supplier<?> arg5) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Supplier<?>... suppliers) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg) {
        if (isLevelEnabled(WARN)) {
            log(WARN, msg, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Object arg1) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4,
                           final Object arg5) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Object... arguments) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4,
                           final Supplier<?> arg5) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Supplier<?>... suppliers) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final String msg) {
        if (isLevelEnabled(WARN)) {
            log(requestIdSupplier, WARN, msg);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Object arg1) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Object arg1,
                           final Object arg2) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4,
                           final Object arg5) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Object... arguments) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(requestIdSupplier, WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Supplier<?> arg1) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4,
                           final Supplier<?> arg5) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final String msg,
                           final Supplier<?>... suppliers) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(requestIdSupplier, WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg) {
        if (isLevelEnabled(WARN)) {
            log(requestIdSupplier, WARN, msg, throwable);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Object arg1) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4,
                           final Object arg5) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Object... arguments) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(requestIdSupplier, WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4,
                           final Supplier<?> arg5) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final RequestIdSupplier requestIdSupplier,
                           final Throwable throwable,
                           final String msg,
                           final Supplier<?>... suppliers) {
        if (isLevelEnabled(WARN)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(requestIdSupplier, WARN, finalMessage, throwable);
        }
    }

    @Override
    public final boolean isErrorEnabled() {
        return isLevelEnabled(ERROR);
    }

    @Override
    public final void error(final LoggerEvent loggerEvent) {
        if (isLevelEnabled(ERROR)) {
            log(ERROR, loggerEvent);
        }
    }

    @Override
    public final void error(final String msg) {
        if (isLevelEnabled(ERROR)) {
            log(ERROR, msg);
        }
    }

    @Override
    public final void error(final String msg,
                            final Object arg1) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Object arg1,
                            final Object arg2) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4,
                            final Object arg5) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Object... arguments) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Supplier<?> arg1) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4,
                            final Supplier<?> arg5) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Supplier<?>... suppliers) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg) {
        if (isLevelEnabled(ERROR)) {
            log(ERROR, msg, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Object arg1) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4,
                            final Object arg5) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Object... arguments) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4,
                            final Supplier<?> arg5) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Supplier<?>... suppliers) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final String msg) {
        if (isLevelEnabled(ERROR)) {
            log(requestIdSupplier, ERROR, msg);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Object arg1) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Object arg1,
                            final Object arg2) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4,
                            final Object arg5) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Object... arguments) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(requestIdSupplier, ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Supplier<?> arg1) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4,
                            final Supplier<?> arg5) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final String msg,
                            final Supplier<?>... suppliers) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(requestIdSupplier, ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg) {
        if (isLevelEnabled(ERROR)) {
            log(requestIdSupplier, ERROR, msg, throwable);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Object arg1) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4,
                            final Object arg5) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Object... arguments) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(requestIdSupplier, ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(requestIdSupplier, ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(requestIdSupplier, ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(requestIdSupplier, ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(requestIdSupplier, ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4,
                            final Supplier<?> arg5) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(requestIdSupplier, ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final RequestIdSupplier requestIdSupplier,
                            final Throwable throwable,
                            final String msg,
                            final Supplier<?>... suppliers) {
        if (isLevelEnabled(ERROR)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(requestIdSupplier, ERROR, finalMessage, throwable);
        }
    }

    private String getFinalMessage(final String msg,
                                   final Object... arguments) {
        return format(msg, arguments);
    }

    private String getFinalMessage(final String msg,
                                   final Supplier<?>... suppliers) {
        return format(msg, Arrays.stream(suppliers).map(Supplier::get).toArray());
    }
}
