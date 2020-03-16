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
 * @author nedis
 * @link https://rxmicro.io
 * @link https://docs.oracle.com/en/java/javase/11/docs/api/java.logging/java/util/logging/package-summary.html
 * @see Formats
 * @since 0.1
 */
public interface Logger {

    boolean isTraceEnabled();

    void trace(String msg);

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void trace(String msg, Object arg1) {
        trace(msg, new Object[]{arg1});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void trace(String msg, Object arg1, Object arg2) {
        trace(msg, new Object[]{arg1, arg2});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void trace(String msg, Object arg1, Object arg2, Object arg3) {
        trace(msg, new Object[]{arg1, arg2, arg3});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void trace(String msg, Object arg1, Object arg2, Object arg3, Object arg4) {
        trace(msg, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void trace(String msg, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        trace(msg, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    void trace(String msg, Object... arguments);

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void trace(String msg, Supplier<?> arg1) {
        trace(msg, new Object[]{arg1});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void trace(String msg, Supplier<?> arg1, Supplier<?> arg2) {
        trace(msg, new Object[]{arg1, arg2});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void trace(String msg, Supplier<?> arg1, Supplier<?> arg2, Supplier<?> arg3) {
        trace(msg, new Object[]{arg1, arg2, arg3});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void trace(String msg, Supplier<?> arg1, Supplier<?> arg2, Supplier<?> arg3, Supplier<?> arg4) {
        trace(msg, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void trace(String msg, Supplier<?> arg1, Supplier<?> arg2, Supplier<?> arg3, Supplier<?> arg4, Supplier<?> arg5) {
        trace(msg, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    void trace(String msg, Supplier<?>... suppliers);

    boolean isDebugEnabled();

    void debug(String msg);

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void debug(String msg, Object arg1) {
        debug(msg, new Object[]{arg1});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void debug(String msg, Object arg1, Object arg2) {
        debug(msg, new Object[]{arg1, arg2});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void debug(String msg, Object arg1, Object arg2, Object arg3) {
        debug(msg, new Object[]{arg1, arg2, arg3});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void debug(String msg, Object arg1, Object arg2, Object arg3, Object arg4) {
        debug(msg, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void debug(String msg, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        debug(msg, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    void debug(String msg, Object... arguments);

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void debug(String msg, Supplier<?> arg1) {
        debug(msg, new Object[]{arg1});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void debug(String msg, Supplier<?> arg1, Supplier<?> arg2) {
        debug(msg, new Object[]{arg1, arg2});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void debug(String msg, Supplier<?> arg1, Supplier<?> arg2, Supplier<?> arg3) {
        debug(msg, new Object[]{arg1, arg2, arg3});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void debug(String msg, Supplier<?> arg1, Supplier<?> arg2, Supplier<?> arg3, Supplier<?> arg4) {
        debug(msg, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void debug(String msg, Supplier<?> arg1, Supplier<?> arg2, Supplier<?> arg3, Supplier<?> arg4, Supplier<?> arg5) {
        debug(msg, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    void debug(String msg, Supplier<?>... suppliers);

    boolean isInfoEnabled();

    void info(String msg);

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void info(String msg, Object arg1) {
        info(msg, new Object[]{arg1});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void info(String msg, Object arg1, Object arg2) {
        info(msg, new Object[]{arg1, arg2});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void info(String msg, Object arg1, Object arg2, Object arg3) {
        info(msg, new Object[]{arg1, arg2, arg3});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void info(String msg, Object arg1, Object arg2, Object arg3, Object arg4) {
        info(msg, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void info(String msg, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        info(msg, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    void info(String msg, Object... arguments);

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void info(String msg, Supplier<?> arg1) {
        info(msg, new Object[]{arg1});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void info(String msg, Supplier<?> arg1, Supplier<?> arg2) {
        info(msg, new Object[]{arg1, arg2});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void info(String msg, Supplier<?> arg1, Supplier<?> arg2, Supplier<?> arg3) {
        info(msg, new Object[]{arg1, arg2, arg3});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void info(String msg, Supplier<?> arg1, Supplier<?> arg2, Supplier<?> arg3, Supplier<?> arg4) {
        info(msg, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void info(String msg, Supplier<?> arg1, Supplier<?> arg2, Supplier<?> arg3, Supplier<?> arg4, Supplier<?> arg5) {
        info(msg, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    void info(String msg, Supplier<?>... suppliers);

    boolean isWarnEnabled();

    void warn(String msg);

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void warn(String msg, Object arg1) {
        warn(msg, new Object[]{arg1});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void warn(String msg, Object arg1, Object arg2) {
        warn(msg, new Object[]{arg1, arg2});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void warn(String msg, Object arg1, Object arg2, Object arg3) {
        warn(msg, new Object[]{arg1, arg2, arg3});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void warn(String msg, Object arg1, Object arg2, Object arg3, Object arg4) {
        warn(msg, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void warn(String msg, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        warn(msg, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    void warn(String msg, Object... arguments);

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void warn(String msg, Supplier<?> arg1) {
        warn(msg, new Object[]{arg1});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void warn(String msg, Supplier<?> arg1, Supplier<?> arg2) {
        warn(msg, new Object[]{arg1, arg2});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void warn(String msg, Supplier<?> arg1, Supplier<?> arg2, Supplier<?> arg3) {
        warn(msg, new Object[]{arg1, arg2, arg3});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void warn(String msg, Supplier<?> arg1, Supplier<?> arg2, Supplier<?> arg3, Supplier<?> arg4) {
        warn(msg, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void warn(String msg, Supplier<?> arg1, Supplier<?> arg2, Supplier<?> arg3, Supplier<?> arg4, Supplier<?> arg5) {
        warn(msg, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    void warn(String msg, Supplier<?>... suppliers);

    boolean isErrorEnabled();

    void error(String msg);

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(String msg, Object arg1) {
        error(msg, new Object[]{arg1});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(String msg, Object arg1, Object arg2) {
        error(msg, new Object[]{arg1, arg2});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(String msg, Object arg1, Object arg2, Object arg3) {
        error(msg, new Object[]{arg1, arg2, arg3});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(String msg, Object arg1, Object arg2, Object arg3, Object arg4) {
        error(msg, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(String msg, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        error(msg, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(String msg, Supplier<?> arg1) {
        error(msg, new Object[]{arg1});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(String msg, Supplier<?> arg1, Supplier<?> arg2) {
        error(msg, new Object[]{arg1, arg2});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(String msg, Supplier<?> arg1, Supplier<?> arg2, Supplier<?> arg3) {
        error(msg, new Object[]{arg1, arg2, arg3});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(String msg, Supplier<?> arg1, Supplier<?> arg2, Supplier<?> arg3, Supplier<?> arg4) {
        error(msg, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(String msg, Supplier<?> arg1, Supplier<?> arg2, Supplier<?> arg3, Supplier<?> arg4, Supplier<?> arg5) {
        error(msg, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    void error(String msg, Object... arguments);

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    void error(String msg, Supplier<?>... suppliers);

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(Throwable throwable, String msg, Object arg1) {
        error(throwable, msg, new Object[]{arg1});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(Throwable throwable, String msg, Object arg1, Object arg2) {
        error(throwable, msg, new Object[]{arg1, arg2});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(Throwable throwable, String msg, Object arg1, Object arg2, Object arg3) {
        error(throwable, msg, new Object[]{arg1, arg2, arg3});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(Throwable throwable, String msg, Object arg1, Object arg2, Object arg3, Object arg4) {
        error(throwable, msg, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(Throwable throwable, String msg, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        error(throwable, msg, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    void error(Throwable throwable, String msg, Object... arguments);

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(Throwable throwable, String msg, Supplier<?> arg1) {
        error(throwable, msg, new Object[]{arg1});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(Throwable throwable, String msg, Supplier<?> arg1, Supplier<?> arg2) {
        error(throwable, msg, new Object[]{arg1, arg2});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(Throwable throwable, String msg, Supplier<?> arg1, Supplier<?> arg2, Supplier<?> arg3) {
        error(throwable, msg, new Object[]{arg1, arg2, arg3});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(Throwable throwable, String msg, Supplier<?> arg1, Supplier<?> arg2, Supplier<?> arg3, Supplier<?> arg4) {
        error(throwable, msg, new Object[]{arg1, arg2, arg3, arg4});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    default void error(Throwable throwable, String msg, Supplier<?> arg1, Supplier<?> arg2, Supplier<?> arg3, Supplier<?> arg4, Supplier<?> arg5) {
        error(throwable, msg, new Object[]{arg1, arg2, arg3, arg4, arg5});
    }

    /**
     * This method uses {@link Formats#format(String, Object...) Formats.format} to format logger message
     */
    void error(Throwable throwable, String msg, Supplier<?>... suppliers);
}
