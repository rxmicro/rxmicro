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
 * <p>
 * Logger is an integral component of any software system.
 * <p>
 * The RxMicro framework provides the {@code rxmicro.logger } module for logging important events during the work of microservices.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @link https://docs.oracle.com/en/java/javase/11/docs/api/java.logging/java/util/logging/package-summary.html
 * @see Formats
 * @since 0.1
 */
public interface Logger {

    /**
     * Is the logger instance enabled for the {@code TRACE} level?
     *
     * @return {@code true} if this Logger is enabled for the {@code TRACE} level
     */
    boolean isTraceEnabled();

    /**
     * Log the message at the {@code TRACE} level.
     *
     * @param msg the message string to be logged
     */
    void trace(String msg);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and argument.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the argument
     */
    default void trace(final String format,
                       final Object arg1) {
        trace(format, new Object[]{arg1});
    }

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void trace(final String format,
                       final Object arg1,
                       final Object arg2) {
        trace(format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void trace(final String format,
                       final Object arg1,
                       final Object arg2,
                       final Object arg3) {
        trace(format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void trace(final String format,
                       final Object arg1,
                       final Object arg2,
                       final Object arg3,
                       final Object arg4) {
        trace(format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void trace(final String format,
                       final Object arg1,
                       final Object arg2,
                       final Object arg3,
                       final Object arg4,
                       final Object arg5) {
        trace(format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arguments the arguments
     */
    void trace(String format, Object... arguments);

    /**
     * Log the message at the {@code TRACE} level according to the specified format and argument.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the argument
     */
    default void trace(final String format,
                       final Supplier<?> arg1) {
        trace(format, new Object[]{arg1});
    }

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void trace(final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2) {
        trace(format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void trace(final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2,
                       final Supplier<?> arg3) {
        trace(format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void trace(final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2,
                       final Supplier<?> arg3,
                       final Supplier<?> arg4) {
        trace(format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void trace(final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2,
                       final Supplier<?> arg3,
                       final Supplier<?> arg4,
                       final Supplier<?> arg5) {
        trace(format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param suppliers the argument suppliers
     */
    void trace(String format, Supplier<?>... suppliers);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the argument
     */
    default void trace(final Throwable throwable,
                       final String format,
                       final Object arg1) {
        trace(throwable, format, new Object[]{arg1});
    }

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void trace(final Throwable throwable,
                       final String format,
                       final Object arg1,
                       final Object arg2) {
        trace(throwable, format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void trace(final Throwable throwable,
                       final String format,
                       final Object arg1,
                       final Object arg2,
                       final Object arg3) {
        trace(throwable, format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void trace(final Throwable throwable,
                       final String format,
                       final Object arg1,
                       final Object arg2,
                       final Object arg3,
                       final Object arg4) {
        trace(throwable, format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void trace(final Throwable throwable,
                       final String format,
                       final Object arg1,
                       final Object arg2,
                       final Object arg3,
                       final Object arg4,
                       final Object arg5) {
        trace(throwable, format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arguments the arguments
     */
    void trace(Throwable throwable, String format, Object... arguments);

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and argument.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the argument
     */
    default void trace(final Throwable throwable,
                       final String format,
                       final Supplier<?> arg1) {
        trace(throwable, format, new Object[]{arg1});
    }

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void trace(final Throwable throwable,
                       final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2) {
        trace(throwable, format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void trace(final Throwable throwable,
                       final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2,
                       final Supplier<?> arg3) {
        trace(throwable, format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void trace(final Throwable throwable,
                       final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2,
                       final Supplier<?> arg3,
                       final Supplier<?> arg4) {
        trace(throwable, format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void trace(final Throwable throwable,
                       final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2,
                       final Supplier<?> arg3,
                       final Supplier<?> arg4,
                       final Supplier<?> arg5) {
        trace(throwable, format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message with the throwable at the {@code TRACE} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param suppliers the suppliers
     */
    void trace(Throwable throwable, String format, Supplier<?>... suppliers);

    // -------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Is the logger instance enabled for the {@code DEBUG} level?
     *
     * @return {@code true} if this Logger is enabled for the {@code DEBUG} level
     */
    boolean isDebugEnabled();

    /**
     * Log the message at the {@code DEBUG} level.
     *
     * @param msg the message string to be logged
     */
    void debug(String msg);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and argument.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the argument
     */
    default void debug(final String format,
                       final Object arg1) {
        debug(format, new Object[]{arg1});
    }

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void debug(final String format,
                       final Object arg1,
                       final Object arg2) {
        debug(format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void debug(final String format,
                       final Object arg1,
                       final Object arg2,
                       final Object arg3) {
        debug(format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void debug(final String format,
                       final Object arg1,
                       final Object arg2,
                       final Object arg3,
                       final Object arg4) {
        debug(format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void debug(final String format,
                       final Object arg1,
                       final Object arg2,
                       final Object arg3,
                       final Object arg4,
                       final Object arg5) {
        debug(format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arguments the arguments
     */
    void debug(String format, Object... arguments);

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and argument.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the argument
     */
    default void debug(final String format,
                       final Supplier<?> arg1) {
        debug(format, new Object[]{arg1});
    }

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void debug(final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2) {
        debug(format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void debug(final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2,
                       final Supplier<?> arg3) {
        debug(format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void debug(final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2,
                       final Supplier<?> arg3,
                       final Supplier<?> arg4) {
        debug(format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void debug(final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2,
                       final Supplier<?> arg3,
                       final Supplier<?> arg4,
                       final Supplier<?> arg5) {
        debug(format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param suppliers the argument suppliers
     */
    void debug(String format, Supplier<?>... suppliers);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the argument
     */
    default void debug(final Throwable throwable,
                       final String format,
                       final Object arg1) {
        debug(throwable, format, new Object[]{arg1});
    }

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void debug(final Throwable throwable,
                       final String format,
                       final Object arg1,
                       final Object arg2) {
        debug(throwable, format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void debug(final Throwable throwable,
                       final String format,
                       final Object arg1,
                       final Object arg2,
                       final Object arg3) {
        debug(throwable, format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void debug(final Throwable throwable,
                       final String format,
                       final Object arg1,
                       final Object arg2,
                       final Object arg3,
                       final Object arg4) {
        debug(throwable, format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void debug(final Throwable throwable,
                       final String format,
                       final Object arg1,
                       final Object arg2,
                       final Object arg3,
                       final Object arg4,
                       final Object arg5) {
        debug(throwable, format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arguments the arguments
     */
    void debug(Throwable throwable, String format, Object... arguments);

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and argument.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the argument
     */
    default void debug(final Throwable throwable,
                       final String format,
                       final Supplier<?> arg1) {
        debug(throwable, format, new Object[]{arg1});
    }

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void debug(final Throwable throwable,
                       final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2) {
        debug(throwable, format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void debug(final Throwable throwable,
                       final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2,
                       final Supplier<?> arg3) {
        debug(throwable, format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void debug(final Throwable throwable,
                       final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2,
                       final Supplier<?> arg3,
                       final Supplier<?> arg4) {
        debug(throwable, format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void debug(final Throwable throwable,
                       final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2,
                       final Supplier<?> arg3,
                       final Supplier<?> arg4,
                       final Supplier<?> arg5) {
        debug(throwable, format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message with the throwable at the {@code DEBUG} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param suppliers the suppliers
     */
    void debug(Throwable throwable, String format, Supplier<?>... suppliers);

    // -------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Is the logger instance enabled for the {@code INFO} level?
     *
     * @return {@code true} if this Logger is enabled for the {@code INFO} level
     */
    boolean isInfoEnabled();

    /**
     * Log the message at the {@code INFO} level.
     *
     * @param msg the message string to be logged
     */
    void info(String msg);

    /**
     * Log the message at the {@code INFO} level according to the specified format and argument.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the argument
     */
    default void info(final String format,
                      final Object arg1) {
        info(format, new Object[]{arg1});
    }

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void info(final String format,
                      final Object arg1,
                      final Object arg2) {
        info(format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void info(final String format,
                      final Object arg1,
                      final Object arg2,
                      final Object arg3) {
        info(format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void info(final String format,
                      final Object arg1,
                      final Object arg2,
                      final Object arg3,
                      final Object arg4) {
        info(format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void info(final String format,
                      final Object arg1,
                      final Object arg2,
                      final Object arg3,
                      final Object arg4,
                      final Object arg5) {
        info(format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arguments the arguments
     */
    void info(String format, Object... arguments);

    /**
     * Log the message at the {@code INFO} level according to the specified format and argument.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the argument
     */
    default void info(final String format,
                      final Supplier<?> arg1) {
        info(format, new Object[]{arg1});
    }

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void info(final String format,
                      final Supplier<?> arg1,
                      final Supplier<?> arg2) {
        info(format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void info(final String format,
                      final Supplier<?> arg1,
                      final Supplier<?> arg2,
                      final Supplier<?> arg3) {
        info(format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void info(final String format,
                      final Supplier<?> arg1,
                      final Supplier<?> arg2,
                      final Supplier<?> arg3,
                      final Supplier<?> arg4) {
        info(format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void info(final String format,
                      final Supplier<?> arg1,
                      final Supplier<?> arg2,
                      final Supplier<?> arg3,
                      final Supplier<?> arg4,
                      final Supplier<?> arg5) {
        info(format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param suppliers the argument suppliers
     */
    void info(String format, Supplier<?>... suppliers);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the argument
     */
    default void info(final Throwable throwable,
                      final String format,
                      final Object arg1) {
        info(throwable, format, new Object[]{arg1});
    }

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void info(final Throwable throwable,
                      final String format,
                      final Object arg1,
                      final Object arg2) {
        info(throwable, format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void info(final Throwable throwable,
                      final String format,
                      final Object arg1,
                      final Object arg2,
                      final Object arg3) {
        info(throwable, format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void info(final Throwable throwable,
                      final String format,
                      final Object arg1,
                      final Object arg2,
                      final Object arg3,
                      final Object arg4) {
        info(throwable, format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void info(final Throwable throwable,
                      final String format,
                      final Object arg1,
                      final Object arg2,
                      final Object arg3,
                      final Object arg4,
                      final Object arg5) {
        info(throwable, format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arguments the arguments
     */
    void info(Throwable throwable, String format, Object... arguments);

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and argument.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the argument
     */
    default void info(final Throwable throwable,
                      final String format,
                      final Supplier<?> arg1) {
        info(throwable, format, new Object[]{arg1});
    }

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void info(final Throwable throwable,
                      final String format,
                      final Supplier<?> arg1,
                      final Supplier<?> arg2) {
        info(throwable, format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void info(final Throwable throwable,
                      final String format,
                      final Supplier<?> arg1,
                      final Supplier<?> arg2,
                      final Supplier<?> arg3) {
        info(throwable, format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void info(final Throwable throwable,
                      final String format,
                      final Supplier<?> arg1,
                      final Supplier<?> arg2,
                      final Supplier<?> arg3,
                      final Supplier<?> arg4) {
        info(throwable, format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void info(final Throwable throwable,
                      final String format,
                      final Supplier<?> arg1,
                      final Supplier<?> arg2,
                      final Supplier<?> arg3,
                      final Supplier<?> arg4,
                      final Supplier<?> arg5) {
        info(throwable, format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message with the throwable at the {@code INFO} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param suppliers the suppliers
     */
    void info(Throwable throwable, String format, Supplier<?>... suppliers);

    // -------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Is the logger instance enabled for the {@code WARN} level?
     *
     * @return {@code true} if this Logger is enabled for the {@code WARN} level
     */
    boolean isWarnEnabled();

    /**
     * Log the message at the {@code WARN} level.
     *
     * @param msg the message string to be logged
     */
    void warn(String msg);

    /**
     * Log the message at the {@code WARN} level according to the specified format and argument.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the argument
     */
    default void warn(final String format,
                      final Object arg1) {
        warn(format, new Object[]{arg1});
    }

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void warn(final String format,
                      final Object arg1,
                      final Object arg2) {
        warn(format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void warn(final String format,
                      final Object arg1,
                      final Object arg2,
                      final Object arg3) {
        warn(format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void warn(final String format,
                      final Object arg1,
                      final Object arg2,
                      final Object arg3,
                      final Object arg4) {
        warn(format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void warn(final String format,
                      final Object arg1,
                      final Object arg2,
                      final Object arg3,
                      final Object arg4,
                      final Object arg5) {
        warn(format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arguments the arguments
     */
    void warn(String format, Object... arguments);

    /**
     * Log the message at the {@code WARN} level according to the specified format and argument.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the argument
     */
    default void warn(final String format,
                      final Supplier<?> arg1) {
        warn(format, new Object[]{arg1});
    }

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void warn(final String format,
                      final Supplier<?> arg1,
                      final Supplier<?> arg2) {
        warn(format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void warn(final String format,
                      final Supplier<?> arg1,
                      final Supplier<?> arg2,
                      final Supplier<?> arg3) {
        warn(format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void warn(final String format,
                      final Supplier<?> arg1,
                      final Supplier<?> arg2,
                      final Supplier<?> arg3,
                      final Supplier<?> arg4) {
        warn(format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void warn(final String format,
                      final Supplier<?> arg1,
                      final Supplier<?> arg2,
                      final Supplier<?> arg3,
                      final Supplier<?> arg4,
                      final Supplier<?> arg5) {
        warn(format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param suppliers the argument suppliers
     */
    void warn(String format, Supplier<?>... suppliers);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the argument
     */
    default void warn(final Throwable throwable,
                      final String format,
                      final Object arg1) {
        warn(throwable, format, new Object[]{arg1});
    }

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void warn(final Throwable throwable,
                      final String format,
                      final Object arg1,
                      final Object arg2) {
        warn(throwable, format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void warn(final Throwable throwable,
                      final String format,
                      final Object arg1,
                      final Object arg2,
                      final Object arg3) {
        warn(throwable, format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void warn(final Throwable throwable,
                      final String format,
                      final Object arg1,
                      final Object arg2,
                      final Object arg3,
                      final Object arg4) {
        warn(throwable, format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void warn(final Throwable throwable,
                      final String format,
                      final Object arg1,
                      final Object arg2,
                      final Object arg3,
                      final Object arg4,
                      final Object arg5) {
        warn(throwable, format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arguments the arguments
     */
    void warn(Throwable throwable, String format, Object... arguments);

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and argument.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the argument
     */
    default void warn(final Throwable throwable,
                      final String format,
                      final Supplier<?> arg1) {
        warn(throwable, format, new Object[]{arg1});
    }

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void warn(final Throwable throwable,
                      final String format,
                      final Supplier<?> arg1,
                      final Supplier<?> arg2) {
        warn(throwable, format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void warn(final Throwable throwable,
                      final String format,
                      final Supplier<?> arg1,
                      final Supplier<?> arg2,
                      final Supplier<?> arg3) {
        warn(throwable, format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void warn(final Throwable throwable,
                      final String format,
                      final Supplier<?> arg1,
                      final Supplier<?> arg2,
                      final Supplier<?> arg3,
                      final Supplier<?> arg4) {
        warn(throwable, format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void warn(final Throwable throwable,
                      final String format,
                      final Supplier<?> arg1,
                      final Supplier<?> arg2,
                      final Supplier<?> arg3,
                      final Supplier<?> arg4,
                      final Supplier<?> arg5) {
        warn(throwable, format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message with the throwable at the {@code WARN} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param suppliers the suppliers
     */
    void warn(Throwable throwable, String format, Supplier<?>... suppliers);

    // -------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Is the logger instance enabled for the {@code ERROR} level?
     *
     * @return {@code true} if this Logger is enabled for the {@code ERROR} level
     */
    boolean isErrorEnabled();

    /**
     * Log the message at the {@code ERROR} level.
     *
     * @param msg the message string to be logged
     */
    void error(String msg);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and argument.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the argument
     */
    default void error(final String format,
                       final Object arg1) {
        error(format, new Object[]{arg1});
    }

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void error(final String format,
                       final Object arg1,
                       final Object arg2) {
        error(format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void error(final String format,
                       final Object arg1,
                       final Object arg2,
                       final Object arg3) {
        error(format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void error(final String format,
                       final Object arg1,
                       final Object arg2,
                       final Object arg3,
                       final Object arg4) {
        error(format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void error(final String format,
                       final Object arg1,
                       final Object arg2,
                       final Object arg3,
                       final Object arg4,
                       final Object arg5) {
        error(format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arguments the arguments
     */
    void error(String format, Object... arguments);

    /**
     * Log the message at the {@code ERROR} level according to the specified format and argument.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the argument
     */
    default void error(final String format,
                       final Supplier<?> arg1) {
        error(format, new Object[]{arg1});
    }

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void error(final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2) {
        error(format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void error(final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2,
                       final Supplier<?> arg3) {
        error(format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void error(final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2,
                       final Supplier<?> arg3,
                       final Supplier<?> arg4) {
        error(format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void error(final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2,
                       final Supplier<?> arg3,
                       final Supplier<?> arg4,
                       final Supplier<?> arg5) {
        error(format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param format the format string
     * @param suppliers the argument suppliers
     */
    void error(String format, Supplier<?>... suppliers);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the argument
     */
    default void error(final Throwable throwable,
                       final String format,
                       final Object arg1) {
        error(throwable, format, new Object[]{arg1});
    }

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void error(final Throwable throwable,
                       final String format,
                       final Object arg1,
                       final Object arg2) {
        error(throwable, format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void error(final Throwable throwable,
                       final String format,
                       final Object arg1,
                       final Object arg2,
                       final Object arg3) {
        error(throwable, format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void error(final Throwable throwable,
                       final String format,
                       final Object arg1,
                       final Object arg2,
                       final Object arg3,
                       final Object arg4) {
        error(throwable, format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void error(final Throwable throwable,
                       final String format,
                       final Object arg1,
                       final Object arg2,
                       final Object arg3,
                       final Object arg4,
                       final Object arg5) {
        error(throwable, format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arguments the arguments
     */
    void error(Throwable throwable, String format, Object... arguments);

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and argument.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the argument
     */
    default void error(final Throwable throwable,
                       final String format,
                       final Supplier<?> arg1) {
        error(throwable, format, new Object[]{arg1});
    }

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    default void error(final Throwable throwable,
                       final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2) {
        error(throwable, format, new Object[]{arg1, arg2});
    }

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     */
    default void error(final Throwable throwable,
                       final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2,
                       final Supplier<?> arg3) {
        error(throwable, format, new Object[]{arg1, arg2, arg3});
    }

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     */
    default void error(final Throwable throwable,
                       final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2,
                       final Supplier<?> arg3,
                       final Supplier<?> arg4) {
        error(throwable, format, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param arg3 the third argument
     * @param arg4 the forth argument
     * @param arg5 the fifth argument
     */
    default void error(final Throwable throwable,
                       final String format,
                       final Supplier<?> arg1,
                       final Supplier<?> arg2,
                       final Supplier<?> arg3,
                       final Supplier<?> arg4,
                       final Supplier<?> arg5) {
        error(throwable, format, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * Log the message with the throwable at the {@code ERROR} level according to the specified format and arguments.
     * <p>
     * <i>(This method uses {@link Formats#format(String, Object...)} method to format logger message.)</i>
     *
     * @param throwable the throwable
     * @param format the format string
     * @param suppliers the suppliers
     */
    void error(Throwable throwable, String format, Supplier<?>... suppliers);
}
