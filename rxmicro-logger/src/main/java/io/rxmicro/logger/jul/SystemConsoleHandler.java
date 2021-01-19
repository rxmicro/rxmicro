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

package io.rxmicro.logger.jul;

import java.io.UnsupportedEncodingException;
import java.util.function.Predicate;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

import static io.rxmicro.logger.internal.jul.InternalLoggerHelper.logInternal;
import static io.rxmicro.logger.internal.jul.SystemConsoleHandlerHelper.getConfiguredComponent;
import static io.rxmicro.logger.internal.jul.SystemConsoleHandlerHelper.getConfiguredErrLevelForPredicate;
import static io.rxmicro.logger.internal.jul.SystemConsoleHandlerHelper.getConfiguredLevel;
import static io.rxmicro.logger.internal.jul.SystemConsoleHandlerHelper.getConfiguredStream;
import static io.rxmicro.logger.internal.jul.SystemConsoleHandlerHelper.getPropertyValue;

/**
 * This {@link java.util.logging.Handler} publishes log records to {@link System#out} or {@link System#err}.
 *
 * <p>
 * {@link SystemConsoleHandler} supports the following streams:
 * <ul>
 *     <li>
 *         {@value #AUTO} - this stream means that log messages with {@code TRACE}, {@code DEBUG}, {@code INFO} and {@code WARN} levels
 *         are forwarded to the standard output stream ({@link System#out}). And log messages with {@code ERROR} levels are forwarded to
 *         the standard error stream ({@link System#err}).
 *
 *         {@link SystemConsoleHandler} component uses the {@link SystemConsoleHandler}.{@code errStreamFor} option to detect the log level
 *         that must be forwarded to the standard error stream ({@link System#err}).
 *     </li>
 *     <li>
 *         {@value #STD_OUT} - this stream means that all log messages are forwarded to the standard output stream ({@link System#out}).
 *     </li>
 *     <li>
 *         {@value #STD_ERR} - this stream means that all log messages are forwarded to the standard error stream ({@link System#err}).
 *     </li>
 * </ul>
 *
 * <p>
 * By default the {@value #AUTO} stream is used.
 *
 * <p>
 * By default the {@link PatternFormatter} with {@value PatternFormatter#DEFAULT_PATTERN} pattern is used to generate messages.
 *
 * <p>
 * <b>Configuration:</b>
 * By default each {@link SystemConsoleHandler} is initialized using the following {@link LogManager} configuration properties.
 * If properties are not defined (or have invalid values) then the specified default values are used.
 * <ul>
 *      <li>
 *          {@code io.rxmicro.logger.jul.SystemConsoleHandler.stream} specifies the output stream for the {@code Handler} (defaults to {@value #AUTO}).
 *      </li>
 *      <li>
 *          {@code io.rxmicro.logger.jul.SystemConsoleHandler.errStreamFor} specifies the log levels that must be forwarded to the standard error stream
 *          ({@link System#err}) (defaults to {@code ERROR}).
 *          This option is used only with {@code io.rxmicro.logger.jul.SystemConsoleHandler.stream} = {@value #AUTO}!
 *          For several values use {@code ,} separator, for example:
 *          {@code io.rxmicro.logger.jul.SystemConsoleHandler.errStreamFor=ERROR, WARN}!
 *          The {@link SystemConsoleHandler}.{@code errStreamFor} supports all logging levels from the following sets:
 *          <ul>
 *              <li>
 *                  The RxMicro log level set:
 *                  {@code OFF}, {@code ERROR}, {@code WARN}, {@code INFO}, {@code DEBUG}, {@code TRACE}, {@code ALL}
 *              </li>
 *              <li>
 *                  The java util logging set:
 *                  {@code OFF}, {@code SEVERE}, {@code WARNING}, {@code INFO},
 *                  {@code CONFIG}, {@code FINE}, {@code FINER}, {@code FINEST}, {@code ALL}
 *              </li>
 *          </ul>
 *          This option makes it possible to use unsupported but widely used in other logging frameworks logging levels
 *          for the {@code java.logging} logger.
 *      </li>
 *      <li>
 *          {@code io.rxmicro.logger.jul.SystemConsoleHandler.level} specifies the default level for the {@code Handler}
 *          (defaults to {@link Level#INFO}).
 *      </li>
 *      <li>
 *          {@code io.rxmicro.logger.jul.SystemConsoleHandler.filter} specifies the name of a {@link Filter} class to use
 *          (defaults to no {@link Filter}).
 *      </li>
 *      <li>
 *          {@code io.rxmicro.logger.jul.SystemConsoleHandler.formatter} specifies the name of a {@link Formatter} class to use
 *          (defaults to {@link PatternFormatter} with {@value PatternFormatter#DEFAULT_PATTERN} pattern).
 *      </li>
 *      <li>
 *          {@code io.rxmicro.logger.jul.SystemConsoleHandler.encoding} the name of the character set encoding to use
 *          (defaults to the default platform encoding).
 *      </li>
 * </ul>
 *
 * @author nedis
 * @since 0.1
 */
public final class SystemConsoleHandler extends Handler {

    /**
     * The standard output stream.
     *
     * @see System#out
     */
    public static final String STD_OUT = "stdout";

    /**
     * The standard error stream.
     *
     * @see System#err
     */
    public static final String STD_ERR = "stderr";

    /**
     * Auto output stream.
     *
     * <p>
     * Log messages with 'TRACE', 'DEBUG', 'INFO' and 'WARN' levels are forwarded to the standard output stream ({@link System#out}).
     * Log messages with 'ERROR' levels are forwarded to the standard error stream ({@link System#err}).
     *
     * @see System#out
     * @see System#err
     */
    public static final String AUTO = "auto";

    private final Handler handler;

    /**
     * Creates an instance of {@link SystemConsoleHandler} class.
     */
    public SystemConsoleHandler() {
        final LogManager manager = LogManager.getLogManager();
        final String stream = getConfiguredStream(manager);
        final PatternFormatter formatter = new PatternFormatter();
        if (stream.equals(STD_ERR)) {
            handler = new StreamHandler(System.err, formatter);
        } else if (stream.equals(STD_OUT)) {
            handler = new StreamHandler(System.out, formatter);
        } else {
            handler = new AutoStreamHandler(manager, formatter);
        }
        initStreamHandler(manager, handler);
    }

    private void initStreamHandler(final LogManager manager,
                                   final Handler streamHandler) {
        getConfiguredLevel(manager).ifPresent(streamHandler::setLevel);
        getPropertyValue(manager, "encoding").ifPresent(encoding -> {
            try {
                streamHandler.setEncoding(encoding);
            } catch (final UnsupportedEncodingException exception) {
                logInternal(
                        Level.WARNING,
                        "Unsupported value: '?' for 'encoding' parameter: ?! This parameter is ignored!",
                        getEncoding(), exception.getMessage()
                );
            }
        });
        getConfiguredComponent(manager, "filter", Filter.class).ifPresent(streamHandler::setFilter);
        getConfiguredComponent(manager, "formatter", Formatter.class).ifPresent(streamHandler::setFormatter);
    }

    @Override
    public void publish(final LogRecord record) {
        handler.publish(record);
        handler.flush();
    }

    @Override
    public void flush() {
        handler.flush();
    }

    @Override
    public void close() {
        flush();
    }

    /**
     * @author nedis
     * @since 0.9
     */
    private static final class AutoStreamHandler extends Handler {

        private final StreamHandler sysOutStreamHandler;

        private final StreamHandler sysErrStreamHandler;

        private final Predicate<Level> errStreamForPredicate;

        private AutoStreamHandler(final LogManager manager,
                                  final Formatter formatter) {
            sysOutStreamHandler = new StreamHandler(System.out, formatter);
            sysErrStreamHandler = new StreamHandler(System.err, formatter);
            errStreamForPredicate = getConfiguredErrLevelForPredicate(manager);
        }

        @Override
        public void publish(final LogRecord record) {
            if (errStreamForPredicate.test(record.getLevel())) {
                sysErrStreamHandler.publish(record);
                sysErrStreamHandler.flush();
            } else {
                sysOutStreamHandler.publish(record);
                sysOutStreamHandler.flush();
            }
        }

        @Override
        public void setEncoding(final String encoding) throws UnsupportedEncodingException {
            sysErrStreamHandler.setEncoding(encoding);
            sysOutStreamHandler.setEncoding(encoding);
        }

        @Override
        public void setFilter(final Filter newFilter) {
            sysErrStreamHandler.setFilter(newFilter);
            sysOutStreamHandler.setFilter(newFilter);
        }

        @Override
        public void setLevel(final Level newLevel) {
            sysErrStreamHandler.setLevel(newLevel);
            sysOutStreamHandler.setLevel(newLevel);
        }

        @Override
        public void setFormatter(final Formatter newFormatter) {
            sysErrStreamHandler.setFormatter(newFormatter);
            sysOutStreamHandler.setFormatter(newFormatter);
        }

        @Override
        public void flush() {
            sysErrStreamHandler.flush();
            sysOutStreamHandler.flush();
        }

        @Override
        public void close() {
            flush();
        }
    }
}
