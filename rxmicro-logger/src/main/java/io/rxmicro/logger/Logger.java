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

import io.rxmicro.common.util.Formats;

import java.util.function.Supplier;

/**
 * The {@link Logger} interface is an abstraction over the real logger.
 *
 * <p>
 * Logger is an integral component of any software system.
 *
 * <p>
 * The RxMicro framework provides the {@code rxmicro.logger } module for logging important events during the work of microservices.
 *
 * @author nedis
 * @see Formats
 * @see LoggerFactory
 * @see Level
 * @see LoggerImplProviderFactory
 * @since 0.1
 */
public interface Logger {

    /**
     * Return the name of this Logger instance.
     *
     * @return the name of this Logger instance
     */
    String getName();

    /**
     * Returns {@code true} if the logger instance enabled for the {@code TRACE} level.
     *
     * @return {@code true} if this Logger is enabled for the {@code TRACE} level
     */
    boolean isTraceEnabled();

    /**
     * Log the message at the {@code TRACE} level.
     *
     * <p>
     * To build an instance of {@link LoggerEvent} use {@link LoggerEventBuilder#build()} builder method.
     * To create a new instance of {@link LoggerEventBuilder} use {@link LoggerFactory#newLoggerEventBuilder()} factory method.
     *
     * @param loggerEvent the logger event instance with custom log data
     * @see LoggerFactory#newLoggerEventBuilder()
     * @see LoggerEventBuilder
     * @see LoggerEvent
     */
    void trace(LoggerEvent loggerEvent);

    /**
     * Log the message at the {@code TRACE} level.
     *
     * @param msg the message string to be logged
     */
    void trace(String msg);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the argument
     */
    void trace(String format,
               Object arg1);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void trace(String format,
               Object arg1,
               Object arg2);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     */
    void trace(String format,
               Object arg1,
               Object arg2,
               Object arg3);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     */
    void trace(String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     * @param arg5   the fifth argument
     */
    void trace(String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4,
               Object arg5);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format    the format string
     * @param arguments the arguments
     */
    void trace(String format,
               Object... arguments);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the argument
     */
    void trace(String format,
               Supplier<?> arg1);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void trace(String format,
               Supplier<?> arg1,
               Supplier<?> arg2);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     */
    void trace(String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     */
    void trace(String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     * @param arg5   the fifth argument
     */
    void trace(String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4,
               Supplier<?> arg5);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format    the format string
     * @param suppliers the argument suppliers
     */
    void trace(String format,
               Supplier<?>... suppliers);

    /**
     * Log the message with the throwable at the {@code TRACE} level.
     *
     * @param throwable the throwable
     * @param msg       the message string to be logged
     */
    void trace(Throwable throwable,
               String msg);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the argument
     */
    void trace(Throwable throwable,
               String format,
               Object arg1);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     */
    void trace(Throwable throwable,
               String format,
               Object arg1,
               Object arg2);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     */
    void trace(Throwable throwable,
               String format,
               Object arg1,
               Object arg2,
               Object arg3);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     */
    void trace(Throwable throwable,
               String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     * @param arg5      the fifth argument
     */
    void trace(Throwable throwable,
               String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4,
               Object arg5);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arguments the arguments
     */
    void trace(Throwable throwable,
               String format,
               Object... arguments);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the argument
     */
    void trace(Throwable throwable,
               String format,
               Supplier<?> arg1);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     */
    void trace(Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     */
    void trace(Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     */
    void trace(Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     * @param arg5      the fifth argument
     */
    void trace(Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4,
               Supplier<?> arg5);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param suppliers the suppliers
     */
    void trace(Throwable throwable,
               String format,
               Supplier<?>... suppliers);

    /**
     * Log the message at the {@code TRACE} level.
     *
     * @param requestIdSupplier the request id supplier.
     * @param msg               the message string to be logged
     */
    void trace(RequestIdSupplier requestIdSupplier,
               String msg);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               String format,
               Object arg1);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               String format,
               Object arg1,
               Object arg2);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               String format,
               Object arg1,
               Object arg2,
               Object arg3);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4,
               Object arg5);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arguments         the arguments
     */
    void trace(RequestIdSupplier requestIdSupplier,
               String format,
               Object... arguments);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               String format,
               Supplier<?> arg1);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4,
               Supplier<?> arg5);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param suppliers         the argument suppliers
     */
    void trace(RequestIdSupplier requestIdSupplier,
               String format,
               Supplier<?>... suppliers);

    /**
     * Log the message with the throwable at the {@code TRACE} level.
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param msg               the message string to be logged
     */
    void trace(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String msg);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Object arg1);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Object arg1,
               Object arg2);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Object arg1,
               Object arg2,
               Object arg3);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4,
               Object arg5);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arguments         the arguments
     */
    void trace(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Object... arguments);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Supplier<?> arg1);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void trace(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4,
               Supplier<?> arg5);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param suppliers         the suppliers
     */
    void trace(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Supplier<?>... suppliers);

    // -------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Returns {@code true} if the logger instance enabled for the {@code DEBUG} level.
     *
     * @return {@code true} if this Logger is enabled for the {@code DEBUG} level
     */
    boolean isDebugEnabled();

    /**
     * Log the message at the {@code DEBUG} level.
     *
     * <p>
     * To build an instance of {@link LoggerEvent} use {@link LoggerEventBuilder#build()} builder method.
     * To create a new instance of {@link LoggerEventBuilder} use {@link LoggerFactory#newLoggerEventBuilder()} factory method.
     *
     * @param loggerEvent the logger event instance with custom log data
     * @see LoggerFactory#newLoggerEventBuilder()
     * @see LoggerEventBuilder
     * @see LoggerEvent
     */
    void debug(LoggerEvent loggerEvent);

    /**
     * Log the message at the {@code DEBUG} level.
     *
     * @param msg the message string to be logged
     */
    void debug(String msg);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the argument
     */
    void debug(String format,
               Object arg1);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void debug(String format,
               Object arg1,
               Object arg2);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     */
    void debug(String format,
               Object arg1,
               Object arg2,
               Object arg3);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     */
    void debug(String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     * @param arg5   the fifth argument
     */
    void debug(String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4,
               Object arg5);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format    the format string
     * @param arguments the arguments
     */
    void debug(String format,
               Object... arguments);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the argument
     */
    void debug(String format,
               Supplier<?> arg1);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void debug(String format,
               Supplier<?> arg1,
               Supplier<?> arg2);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     */
    void debug(String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     */
    void debug(String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     * @param arg5   the fifth argument
     */
    void debug(String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4,
               Supplier<?> arg5);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format    the format string
     * @param suppliers the argument suppliers
     */
    void debug(String format,
               Supplier<?>... suppliers);

    /**
     * Log the message with the throwable at the {@code DEBUG} level.
     *
     * @param throwable the throwable
     * @param msg       the message string to be logged
     */
    void debug(Throwable throwable,
               String msg);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the argument
     */
    void debug(Throwable throwable,
               String format,
               Object arg1);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     */
    void debug(Throwable throwable,
               String format,
               Object arg1,
               Object arg2);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     */
    void debug(Throwable throwable,
               String format,
               Object arg1,
               Object arg2,
               Object arg3);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     */
    void debug(Throwable throwable,
               String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     * @param arg5      the fifth argument
     */
    void debug(Throwable throwable,
               String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4,
               Object arg5);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arguments the arguments
     */
    void debug(Throwable throwable,
               String format,
               Object... arguments);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the argument
     */
    void debug(Throwable throwable,
               String format,
               Supplier<?> arg1);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     */
    void debug(Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     */
    void debug(Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     */
    void debug(Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     * @param arg5      the fifth argument
     */
    void debug(Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4,
               Supplier<?> arg5);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param suppliers the suppliers
     */
    void debug(Throwable throwable,
               String format,
               Supplier<?>... suppliers);

    /**
     * Log the message at the {@code DEBUG} level.
     *
     * @param requestIdSupplier the request id supplier.
     * @param msg               the message string to be logged
     */
    void debug(RequestIdSupplier requestIdSupplier,
               String msg);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               String format,
               Object arg1);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               String format,
               Object arg1,
               Object arg2);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               String format,
               Object arg1,
               Object arg2,
               Object arg3);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4,
               Object arg5);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arguments         the arguments
     */
    void debug(RequestIdSupplier requestIdSupplier,
               String format,
               Object... arguments);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               String format,
               Supplier<?> arg1);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4,
               Supplier<?> arg5);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param suppliers         the argument suppliers
     */
    void debug(RequestIdSupplier requestIdSupplier,
               String format,
               Supplier<?>... suppliers);

    /**
     * Log the message with the throwable at the {@code DEBUG} level.
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable.
     * @param msg               the message string to be logged
     */
    void debug(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String msg);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Object arg1);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Object arg1,
               Object arg2);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Object arg1,
               Object arg2,
               Object arg3);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4,
               Object arg5);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arguments         the arguments
     */
    void debug(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Object... arguments);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Supplier<?> arg1);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void debug(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4,
               Supplier<?> arg5);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param suppliers         the suppliers
     */
    void debug(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Supplier<?>... suppliers);

    // -------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Returns {@code true} if the logger instance enabled for the {@code INFO} level.
     *
     * @return {@code true} if this Logger is enabled for the {@code INFO} level
     */
    boolean isInfoEnabled();

    /**
     * Log the message at the {@code INFO} level.
     *
     * <p>
     * To build an instance of {@link LoggerEvent} use {@link LoggerEventBuilder#build()} builder method.
     * To create a new instance of {@link LoggerEventBuilder} use {@link LoggerFactory#newLoggerEventBuilder()} factory method.
     *
     * @param loggerEvent the logger event instance with custom log data
     * @see LoggerFactory#newLoggerEventBuilder()
     * @see LoggerEventBuilder
     * @see LoggerEvent
     */
    void info(LoggerEvent loggerEvent);

    /**
     * Log the message at the {@code INFO} level.
     *
     * @param msg the message string to be logged
     */
    void info(String msg);

    /**
     * Log the message at the {@code INFO} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the argument
     */
    void info(String format,
              Object arg1);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void info(String format,
              Object arg1,
              Object arg2);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     */
    void info(String format,
              Object arg1,
              Object arg2,
              Object arg3);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     */
    void info(String format,
              Object arg1,
              Object arg2,
              Object arg3,
              Object arg4);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     * @param arg5   the fifth argument
     */
    void info(String format,
              Object arg1,
              Object arg2,
              Object arg3,
              Object arg4,
              Object arg5);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format    the format string
     * @param arguments the arguments
     */
    void info(String format,
              Object... arguments);

    /**
     * Log the message at the {@code INFO} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the argument
     */
    void info(String format,
              Supplier<?> arg1);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void info(String format,
              Supplier<?> arg1,
              Supplier<?> arg2);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     */
    void info(String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     */
    void info(String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3,
              Supplier<?> arg4);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     * @param arg5   the fifth argument
     */
    void info(String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3,
              Supplier<?> arg4,
              Supplier<?> arg5);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format    the format string
     * @param suppliers the argument suppliers
     */
    void info(String format,
              Supplier<?>... suppliers);

    /**
     * Log the message with the throwable at the {@code INFO} level.
     *
     * @param throwable the throwable
     * @param msg       the message string to be logged
     */
    void info(Throwable throwable,
              String msg);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the argument
     */
    void info(Throwable throwable,
              String format,
              Object arg1);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     */
    void info(Throwable throwable,
              String format,
              Object arg1,
              Object arg2);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     */
    void info(Throwable throwable,
              String format,
              Object arg1,
              Object arg2,
              Object arg3);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     */
    void info(Throwable throwable,
              String format,
              Object arg1,
              Object arg2,
              Object arg3,
              Object arg4);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     * @param arg5      the fifth argument
     */
    void info(Throwable throwable,
              String format,
              Object arg1,
              Object arg2,
              Object arg3,
              Object arg4,
              Object arg5);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arguments the arguments
     */
    void info(Throwable throwable,
              String format,
              Object... arguments);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the argument
     */
    void info(Throwable throwable,
              String format,
              Supplier<?> arg1);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     */
    void info(Throwable throwable,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     */
    void info(Throwable throwable,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     */
    void info(Throwable throwable,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3,
              Supplier<?> arg4);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     * @param arg5      the fifth argument
     */
    void info(Throwable throwable,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3,
              Supplier<?> arg4,
              Supplier<?> arg5);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param suppliers the suppliers
     */
    void info(Throwable throwable,
              String format,
              Supplier<?>... suppliers);

    /**
     * Log the message at the {@code INFO} level.
     *
     * @param requestIdSupplier the request id supplier.
     * @param msg               the message string to be logged
     */
    void info(RequestIdSupplier requestIdSupplier,
              String msg);

    /**
     * Log the message at the {@code INFO} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              String format,
              Object arg1);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              String format,
              Object arg1,
              Object arg2);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              String format,
              Object arg1,
              Object arg2,
              Object arg3);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              String format,
              Object arg1,
              Object arg2,
              Object arg3,
              Object arg4);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              String format,
              Object arg1,
              Object arg2,
              Object arg3,
              Object arg4,
              Object arg5);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arguments         the arguments
     */
    void info(RequestIdSupplier requestIdSupplier,
              String format,
              Object... arguments);

    /**
     * Log the message at the {@code INFO} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              String format,
              Supplier<?> arg1);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3,
              Supplier<?> arg4);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3,
              Supplier<?> arg4,
              Supplier<?> arg5);

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param suppliers         the argument suppliers
     */
    void info(RequestIdSupplier requestIdSupplier,
              String format,
              Supplier<?>... suppliers);

    /**
     * Log the message with the throwable at the {@code INFO} level.
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param msg               the message string to be logged
     */
    void info(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String msg);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Object arg1);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Object arg1,
              Object arg2);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Object arg1,
              Object arg2,
              Object arg3);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Object arg1,
              Object arg2,
              Object arg3,
              Object arg4);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Object arg1,
              Object arg2,
              Object arg3,
              Object arg4,
              Object arg5);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arguments         the arguments
     */
    void info(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Object... arguments);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Supplier<?> arg1);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3,
              Supplier<?> arg4);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void info(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3,
              Supplier<?> arg4,
              Supplier<?> arg5);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param suppliers         the suppliers
     */
    void info(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Supplier<?>... suppliers);

    // -------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Returns {@code true} if the logger instance enabled for the {@code WARN} level.
     *
     * @return {@code true} if this Logger is enabled for the {@code WARN} level
     */
    boolean isWarnEnabled();

    /**
     * Log the message at the {@code WARN} level.
     *
     * <p>
     * To build an instance of {@link LoggerEvent} use {@link LoggerEventBuilder#build()} builder method.
     * To create a new instance of {@link LoggerEventBuilder} use {@link LoggerFactory#newLoggerEventBuilder()} factory method.
     *
     * @param loggerEvent the logger event instance with custom log data
     * @see LoggerFactory#newLoggerEventBuilder()
     * @see LoggerEventBuilder
     * @see LoggerEvent
     */
    void warn(LoggerEvent loggerEvent);

    /**
     * Log the message at the {@code WARN} level.
     *
     * @param msg the message string to be logged
     */
    void warn(String msg);

    /**
     * Log the message at the {@code WARN} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the argument
     */
    void warn(String format,
              Object arg1);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void warn(String format,
              Object arg1,
              Object arg2);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     */
    void warn(String format,
              Object arg1,
              Object arg2,
              Object arg3);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     */
    void warn(String format,
              Object arg1,
              Object arg2,
              Object arg3,
              Object arg4);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     * @param arg5   the fifth argument
     */
    void warn(String format,
              Object arg1,
              Object arg2,
              Object arg3,
              Object arg4,
              Object arg5);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format    the format string
     * @param arguments the arguments
     */
    void warn(String format,
              Object... arguments);

    /**
     * Log the message at the {@code WARN} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the argument
     */
    void warn(String format,
              Supplier<?> arg1);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void warn(String format,
              Supplier<?> arg1,
              Supplier<?> arg2);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     */
    void warn(String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     */
    void warn(String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3,
              Supplier<?> arg4);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     * @param arg5   the fifth argument
     */
    void warn(String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3,
              Supplier<?> arg4,
              Supplier<?> arg5);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format    the format string
     * @param suppliers the argument suppliers
     */
    void warn(String format,
              Supplier<?>... suppliers);

    /**
     * Log the message with the throwable at the {@code WARN} level.
     *
     * @param throwable the throwable
     * @param msg       the message string to be logged
     */
    void warn(Throwable throwable,
              String msg);


    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the argument
     */
    void warn(Throwable throwable,
              String format,
              Object arg1);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     */
    void warn(Throwable throwable,
              String format,
              Object arg1,
              Object arg2);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     */
    void warn(Throwable throwable,
              String format,
              Object arg1,
              Object arg2,
              Object arg3);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     */
    void warn(Throwable throwable,
              String format,
              Object arg1,
              Object arg2,
              Object arg3,
              Object arg4);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     * @param arg5      the fifth argument
     */
    void warn(Throwable throwable,
              String format,
              Object arg1,
              Object arg2,
              Object arg3,
              Object arg4,
              Object arg5);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arguments the arguments
     */
    void warn(Throwable throwable,
              String format,
              Object... arguments);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the argument
     */
    void warn(Throwable throwable,
              String format,
              Supplier<?> arg1);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     */
    void warn(Throwable throwable,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     */
    void warn(Throwable throwable,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     */
    void warn(Throwable throwable,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3,
              Supplier<?> arg4);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     * @param arg5      the fifth argument
     */
    void warn(Throwable throwable,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3,
              Supplier<?> arg4,
              Supplier<?> arg5);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param suppliers the suppliers
     */
    void warn(Throwable throwable,
              String format,
              Supplier<?>... suppliers);

    /**
     * Log the message at the {@code WARN} level.
     *
     * @param requestIdSupplier the request id supplier.
     * @param msg               the message string to be logged
     */
    void warn(RequestIdSupplier requestIdSupplier,
              String msg);

    /**
     * Log the message at the {@code WARN} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              String format,
              Object arg1);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              String format,
              Object arg1,
              Object arg2);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              String format,
              Object arg1,
              Object arg2,
              Object arg3);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              String format,
              Object arg1,
              Object arg2,
              Object arg3,
              Object arg4);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              String format,
              Object arg1,
              Object arg2,
              Object arg3,
              Object arg4,
              Object arg5);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arguments         the arguments
     */
    void warn(RequestIdSupplier requestIdSupplier,
              String format,
              Object... arguments);

    /**
     * Log the message at the {@code WARN} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              String format,
              Supplier<?> arg1);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3,
              Supplier<?> arg4);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3,
              Supplier<?> arg4,
              Supplier<?> arg5);

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param suppliers         the argument suppliers
     */
    void warn(RequestIdSupplier requestIdSupplier,
              String format,
              Supplier<?>... suppliers);

    /**
     * Log the message with the throwable at the {@code WARN} level.
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param msg               the message string to be logged
     */
    void warn(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String msg);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Object arg1);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Object arg1,
              Object arg2);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Object arg1,
              Object arg2,
              Object arg3);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Object arg1,
              Object arg2,
              Object arg3,
              Object arg4);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Object arg1,
              Object arg2,
              Object arg3,
              Object arg4,
              Object arg5);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arguments         the arguments
     */
    void warn(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Object... arguments);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Supplier<?> arg1);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3,
              Supplier<?> arg4);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void warn(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Supplier<?> arg1,
              Supplier<?> arg2,
              Supplier<?> arg3,
              Supplier<?> arg4,
              Supplier<?> arg5);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param suppliers         the suppliers
     */
    void warn(RequestIdSupplier requestIdSupplier,
              Throwable throwable,
              String format,
              Supplier<?>... suppliers);

    // -------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Returns {@code true} if the logger instance enabled for the {@code ERROR} level.
     *
     * @return {@code true} if this Logger is enabled for the {@code ERROR} level
     */
    boolean isErrorEnabled();

    /**
     * Log the message at the {@code ERROR} level.
     *
     * <p>
     * To build an instance of {@link LoggerEvent} use {@link LoggerEventBuilder#build()} builder method.
     * To create a new instance of {@link LoggerEventBuilder} use {@link LoggerFactory#newLoggerEventBuilder()} factory method.
     *
     * @param loggerEvent the logger event instance with custom log data
     * @see LoggerFactory#newLoggerEventBuilder()
     * @see LoggerEventBuilder
     * @see LoggerEvent
     */
    void error(LoggerEvent loggerEvent);

    /**
     * Log the message at the {@code ERROR} level.
     *
     * @param msg the message string to be logged
     */
    void error(String msg);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the argument
     */
    void error(String format,
               Object arg1);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void error(String format,
               Object arg1,
               Object arg2);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     */
    void error(String format,
               Object arg1,
               Object arg2,
               Object arg3);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     */
    void error(String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     * @param arg5   the fifth argument
     */
    void error(String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4,
               Object arg5);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format    the format string
     * @param arguments the arguments
     */
    void error(String format,
               Object... arguments);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the argument
     */
    void error(String format,
               Supplier<?> arg1);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void error(String format,
               Supplier<?> arg1,
               Supplier<?> arg2);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     */
    void error(String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     */
    void error(String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @param arg3   the third argument
     * @param arg4   the forth argument
     * @param arg5   the fifth argument
     */
    void error(String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4,
               Supplier<?> arg5);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format    the format string
     * @param suppliers the argument suppliers
     */
    void error(String format,
               Supplier<?>... suppliers);

    /**
     * Log the message with the throwable at the {@code ERROR} level.
     *
     * @param throwable the throwable
     * @param msg       the message string to be logged
     */
    void error(Throwable throwable,
               String msg);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the argument
     */
    void error(Throwable throwable,
               String format,
               Object arg1);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     */
    void error(Throwable throwable,
               String format,
               Object arg1,
               Object arg2);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     */
    void error(Throwable throwable,
               String format,
               Object arg1,
               Object arg2,
               Object arg3);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     */
    void error(Throwable throwable,
               String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     * @param arg5      the fifth argument
     */
    void error(Throwable throwable,
               String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4,
               Object arg5);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arguments the arguments
     */
    void error(Throwable throwable,
               String format,
               Object... arguments);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the argument
     */
    void error(Throwable throwable,
               String format,
               Supplier<?> arg1);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     */
    void error(Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     */
    void error(Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     */
    void error(Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param arg1      the first argument
     * @param arg2      the second argument
     * @param arg3      the third argument
     * @param arg4      the forth argument
     * @param arg5      the fifth argument
     */
    void error(Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4,
               Supplier<?> arg5);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format    the format string
     * @param suppliers the suppliers
     */
    void error(Throwable throwable,
               String format,
               Supplier<?>... suppliers);

    /**
     * Log the message at the {@code ERROR} level.
     *
     * @param requestIdSupplier the request id supplier.
     * @param msg               the message string to be logged
     */
    void error(RequestIdSupplier requestIdSupplier,
               String msg);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               String format,
               Object arg1);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               String format,
               Object arg1,
               Object arg2);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               String format,
               Object arg1,
               Object arg2,
               Object arg3);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4,
               Object arg5);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arguments         the arguments
     */
    void error(RequestIdSupplier requestIdSupplier,
               String format,
               Object... arguments);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               String format,
               Supplier<?> arg1);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4,
               Supplier<?> arg5);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param format            the format string
     * @param suppliers         the argument suppliers
     */
    void error(RequestIdSupplier requestIdSupplier,
               String format,
               Supplier<?>... suppliers);

    /**
     * Log the message with the throwable at the {@code ERROR} level.
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param msg               the message string to be logged
     */
    void error(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String msg);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Object arg1);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Object arg1,
               Object arg2);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Object arg1,
               Object arg2,
               Object arg3);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Object arg1,
               Object arg2,
               Object arg3,
               Object arg4,
               Object arg5);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arguments         the arguments
     */
    void error(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Object... arguments);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and argument.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Supplier<?> arg1);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param arg1              the first argument
     * @param arg2              the second argument
     * @param arg3              the third argument
     * @param arg4              the forth argument
     * @param arg5              the fifth argument
     */
    void error(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Supplier<?> arg1,
               Supplier<?> arg2,
               Supplier<?> arg3,
               Supplier<?> arg4,
               Supplier<?> arg5);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     *
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param requestIdSupplier the request id supplier.
     * @param throwable         the throwable
     * @param format            the format string
     * @param suppliers         the suppliers
     */
    void error(RequestIdSupplier requestIdSupplier,
               Throwable throwable,
               String format,
               Supplier<?>... suppliers);
}
