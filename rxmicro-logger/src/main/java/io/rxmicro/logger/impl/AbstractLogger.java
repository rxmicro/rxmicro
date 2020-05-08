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

import java.util.Arrays;
import java.util.function.Supplier;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * Abstract logger implementation that delegates the log events to `log` methods
 *
 * @author nedis
 * @since 0.1
 * @see io.rxmicro.logger.LoggerFactory
 */
public abstract class AbstractLogger implements Logger {

    protected abstract boolean isLevelEnabled(Level level);

    protected abstract void log(Level level, String message);

    protected abstract void log(Level level, String message, Throwable throwable);

    @Override
    public final boolean isTraceEnabled() {
        return isLevelEnabled(Level.TRACE);
    }

    @Override
    public void trace(final String msg) {
        if (isLevelEnabled(Level.TRACE)) {
            log(Level.TRACE, msg);
        }
    }

    @Override
    public final void trace(final String msg,
                            final Object arg1) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String msg,
                            final Object arg1,
                            final Object arg2) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String format,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(format, arg1, arg2, arg3);
            log(Level.TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String format,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(format, arg1, arg2, arg3, arg4);
            log(Level.TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String format,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4,
                            final Object arg5) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(format, arg1, arg2, arg3, arg4, arg5);
            log(Level.TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String format,
                            final Object... arguments) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(format, arguments);
            log(Level.TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String msg,
                            final Supplier<?> arg1) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4,
                            final Supplier<?> arg5) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final String msg,
                            final Supplier<?>... suppliers) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(Level.TRACE, finalMessage);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Object arg1) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.TRACE, finalMessage, throwable);
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
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Object... arguments) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(Level.TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.TRACE, finalMessage, throwable);
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
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final void trace(final Throwable throwable,
                            final String msg,
                            final Supplier<?>... suppliers) {
        if (isLevelEnabled(Level.TRACE)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(Level.TRACE, finalMessage, throwable);
        }
    }

    @Override
    public final boolean isDebugEnabled() {
        return isLevelEnabled(Level.DEBUG);
    }

    @Override
    public final void debug(final String msg) {
        if (isLevelEnabled(Level.DEBUG)) {
            log(Level.DEBUG, msg);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Object arg1) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Object arg1,
                            final Object arg2) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4,
                            final Object arg5) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Object... arguments) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(Level.DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Supplier<?> arg1) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4,
                            final Supplier<?> arg5) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final String msg,
                            final Supplier<?>... suppliers) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(Level.DEBUG, finalMessage);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Object arg1) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.DEBUG, finalMessage, throwable);
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
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Object... arguments) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(Level.DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.DEBUG, finalMessage, throwable);
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
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final void debug(final Throwable throwable,
                            final String msg,
                            final Supplier<?>... suppliers) {
        if (isLevelEnabled(Level.DEBUG)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(Level.DEBUG, finalMessage, throwable);
        }
    }

    @Override
    public final boolean isInfoEnabled() {
        return isLevelEnabled(Level.INFO);
    }

    @Override
    public final void info(final String msg) {
        if (isLevelEnabled(Level.INFO)) {
            log(Level.INFO, msg);
        }
    }

    @Override
    public final void info(final String msg,
                           final Object arg1) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Object arg1,
                           final Object arg2) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4,
                           final Object arg5) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Object... arguments) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(Level.INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Supplier<?> arg1) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4,
                           final Supplier<?> arg5) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.INFO, finalMessage);
        }
    }

    @Override
    public final void info(final String msg,
                           final Supplier<?>... suppliers) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(Level.INFO, finalMessage);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Object arg1) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.INFO, finalMessage, throwable);
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
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Object... arguments) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(Level.INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.INFO, finalMessage, throwable);
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
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.INFO, finalMessage, throwable);
        }
    }

    @Override
    public final void info(final Throwable throwable,
                           final String msg,
                           final Supplier<?>... suppliers) {
        if (isLevelEnabled(Level.INFO)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(Level.INFO, finalMessage, throwable);
        }
    }

    @Override
    public final boolean isWarnEnabled() {
        return isLevelEnabled(Level.WARN);
    }

    @Override
    public final void warn(final String msg) {
        if (isLevelEnabled(Level.WARN)) {
            log(Level.WARN, msg);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Object arg1) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Object arg1,
                           final Object arg2) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4,
                           final Object arg5) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Object... arguments) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(Level.WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Supplier<?> arg1) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4,
                           final Supplier<?> arg5) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final String msg,
                           final Supplier<?>... suppliers) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(Level.WARN, finalMessage);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Object arg1) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Object arg1,
                           final Object arg2,
                           final Object arg3,
                           final Object arg4) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.WARN, finalMessage, throwable);
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
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Object... arguments) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(Level.WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Supplier<?> arg1,
                           final Supplier<?> arg2,
                           final Supplier<?> arg3,
                           final Supplier<?> arg4) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.WARN, finalMessage, throwable);
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
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.WARN, finalMessage, throwable);
        }
    }

    @Override
    public final void warn(final Throwable throwable,
                           final String msg,
                           final Supplier<?>... suppliers) {
        if (isLevelEnabled(Level.WARN)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(Level.WARN, finalMessage, throwable);
        }
    }

    @Override
    public final boolean isErrorEnabled() {
        return isLevelEnabled(Level.ERROR);
    }

    @Override
    public final void error(final String msg) {
        if (isLevelEnabled(Level.ERROR)) {
            log(Level.ERROR, msg);
        }
    }

    @Override
    public final void error(final String msg,
                            final Object arg1) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Object arg1,
                            final Object arg2) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4,
                            final Object arg5) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Object... arguments) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(Level.ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Supplier<?> arg1) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4,
                            final Supplier<?> arg5) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final String msg,
                            final Supplier<?>... suppliers) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(Level.ERROR, finalMessage);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Object arg1) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Object arg1,
                            final Object arg2,
                            final Object arg3,
                            final Object arg4) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.ERROR, finalMessage, throwable);
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
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Object... arguments) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arguments);
            log(Level.ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1);
            log(Level.ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2);
            log(Level.ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3);
            log(Level.ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Supplier<?> arg1,
                            final Supplier<?> arg2,
                            final Supplier<?> arg3,
                            final Supplier<?> arg4) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4);
            log(Level.ERROR, finalMessage, throwable);
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
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, arg1, arg2, arg3, arg4, arg5);
            log(Level.ERROR, finalMessage, throwable);
        }
    }

    @Override
    public final void error(final Throwable throwable,
                            final String msg,
                            final Supplier<?>... suppliers) {
        if (isLevelEnabled(Level.ERROR)) {
            final String finalMessage = getFinalMessage(msg, suppliers);
            log(Level.ERROR, finalMessage, throwable);
        }
    }

    private String getFinalMessage(final String msg,
                                   final Object... arguments) {
        return arguments.length == 0 ? require(msg) : format(msg, arguments);
    }

    private String getFinalMessage(final String msg,
                                   final Supplier<?>... suppliers) {
        return suppliers.length == 0 ? require(msg) : format(msg, Arrays.stream(suppliers).map(Supplier::get).toArray());
    }
}
