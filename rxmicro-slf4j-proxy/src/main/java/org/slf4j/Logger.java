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

package org.slf4j;

/**
 * Unfortunately some db drivers removed support of JUL,
 * so the RxMicro framework requires a org.slf4j proxy to enable logging without slf4j-api:
 * <a href="http://www.slf4j.org">http://www.slf4j.org</a>
 *
 * <p>
 * Read more:
 * <a href="https://github.com/mongodb/mongo-java-driver/commit/6a163f715fe08ed8d39acac3d11c896ae547df73">
 *     https://github.com/mongodb/mongo-java-driver/commit/6a163f715fe08ed8d39acac3d11c896ae547df73
 * </a>
 *
 * <p>
 * The org.slf4j.Logger interface is the main user entry point of SLF4J API.
 * It is expected that logging takes place through concrete implementations
 * of this interface.
 *
 * @author nedis
 * @see <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>
 * @since 0.3
 */
public interface Logger {

    /**
     * Case insensitive String constant used to retrieve the name of the root logger.
     */
    String ROOT_LOGGER_NAME = "ROOT";

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Return the name of this <code>Logger</code> instance.
     *
     * @return name of this logger instance
     */
    String getName();

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Is the logger instance enabled for the {@code TRACE} level?
     *
     * @return {@code true} if this Logger is enabled for the {@code TRACE} level, {@code false} otherwise.
     */
    boolean isTraceEnabled();

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Similar to {@link #isTraceEnabled()} method except that the
     * marker data is also taken into account.
     *
     * @param marker The marker data to take into consideration
     * @return {@code true} if this Logger is enabled for the {@code TRACE} level, {@code false} otherwise.
     */
    boolean isTraceEnabled(Marker marker);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code TRACE} level.
     *
     * @param msg the message string to be logged
     */
    void trace(String msg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code TRACE} level according to the specified format and argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the {@code TRACE} level.
     *
     * @param format the format string
     * @param arg    the argument
     */
    void trace(String format,
               Object arg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code TRACE} level according to the specified format and argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the {@code TRACE} level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void trace(String format,
               Object arg1,
               Object arg2);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code TRACE} level according to the specified format and argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the {@code TRACE} level.
     *
     * @param format    the format string
     * @param arguments the arguments
     */
    void trace(String format,
               Object... arguments);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log an exception (throwable) at the {@code TRACE} level with an accompanying message.
     *
     * @param msg       the message accompanying the exception
     * @param throwable the throwable to log
     */
    void trace(String msg,
               Throwable throwable);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message with the specific Marker at the {@code TRACE} level.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message string to be logged
     */
    void trace(Marker marker,
               String msg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #trace(String, Object)} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    void trace(Marker marker,
               String format,
               Object arg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #trace(String, Object, Object)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void trace(Marker marker,
               String format,
               Object arg1,
               Object arg2);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #trace(String, Object...)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker   the marker data specific to this log statement
     * @param format   the format string
     * @param argArray an array of arguments
     */
    void trace(Marker marker,
               String format,
               Object... argArray);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #trace(String, Throwable)} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param msg       the message accompanying the exception
     * @param throwable the exception (throwable) to log
     */
    void trace(Marker marker,
               String msg,
               Throwable throwable);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Is the logger instance enabled for the {@code DEBUG} level?
     *
     * @return {@code true} if this Logger is enabled for the {@code DEBUG} level, {@code false} otherwise.
     */
    boolean isDebugEnabled();

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Similar to {@link #isDebugEnabled()} method except that the
     * marker data is also taken into account.
     *
     * @param marker The marker data to take into consideration
     * @return {@code true} if this Logger is enabled for the {@code DEBUG} level, {@code false} otherwise.
     */
    boolean isDebugEnabled(Marker marker);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code DEBUG} level.
     *
     * @param msg the message string to be logged
     */
    void debug(String msg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code DEBUG} level according to the specified format and argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the {@code DEBUG} level.
     *
     * @param format the format string
     * @param arg    the argument
     */
    void debug(String format,
               Object arg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code DEBUG} level according to the specified format and argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the {@code DEBUG} level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void debug(String format,
               Object arg1,
               Object arg2);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code DEBUG} level according to the specified format and argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the {@code DEBUG} level.
     *
     * @param format    the format string
     * @param arguments the arguments
     */
    void debug(String format,
               Object... arguments);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log an exception (throwable) at the {@code DEBUG} level with an accompanying message.
     *
     * @param msg       the message accompanying the exception
     * @param throwable the throwable to log
     */
    void debug(String msg,
               Throwable throwable);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message with the specific Marker at the {@code DEBUG} level.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message string to be logged
     */
    void debug(Marker marker,
               String msg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #debug(String, Object)} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    void debug(Marker marker,
               String format,
               Object arg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #debug(String, Object, Object)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void debug(Marker marker,
               String format,
               Object arg1,
               Object arg2);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #debug(String, Object...)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker   the marker data specific to this log statement
     * @param format   the format string
     * @param argArray an array of arguments
     */
    void debug(Marker marker,
               String format,
               Object... argArray);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #debug(String, Throwable)} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param msg       the message accompanying the exception
     * @param throwable the exception (throwable) to log
     */
    void debug(Marker marker,
               String msg,
               Throwable throwable);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Is the logger instance enabled for the {@code INFO} level?
     *
     * @return {@code true} if this Logger is enabled for the {@code INFO} level, {@code false} otherwise.
     */
    boolean isInfoEnabled();

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Similar to {@link #isInfoEnabled()} method except that the
     * marker data is also taken into account.
     *
     * @param marker The marker data to take into consideration
     * @return {@code true} if this Logger is enabled for the {@code INFO} level, {@code false} otherwise.
     */
    boolean isInfoEnabled(Marker marker);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code INFO} level.
     *
     * @param msg the message string to be logged
     */
    void info(String msg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code INFO} level according to the specified format and argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the {@code INFO} level.
     *
     * @param format the format string
     * @param arg    the argument
     */
    void info(String format,
              Object arg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code INFO} level according to the specified format and argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the {@code INFO} level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void info(String format,
              Object arg1,
              Object arg2);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code INFO} level according to the specified format and argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the {@code INFO} level.
     *
     * @param format    the format string
     * @param arguments the arguments
     */
    void info(String format,
              Object... arguments);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log an exception (throwable) at the {@code INFO} level with an accompanying message.
     *
     * @param msg       the message accompanying the exception
     * @param throwable the throwable to log
     */
    void info(String msg,
              Throwable throwable);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message with the specific Marker at the {@code INFO} level.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message string to be logged
     */
    void info(Marker marker,
              String msg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #info(String, Object)} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    void info(Marker marker,
              String format,
              Object arg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #info(String, Object, Object)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void info(Marker marker,
              String format,
              Object arg1,
              Object arg2);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #info(String, Object...)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker   the marker data specific to this log statement
     * @param format   the format string
     * @param argArray an array of arguments
     */
    void info(Marker marker,
              String format,
              Object... argArray);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #info(String, Throwable)} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param msg       the message accompanying the exception
     * @param throwable the exception (throwable) to log
     */
    void info(Marker marker,
              String msg,
              Throwable throwable);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Is the logger instance enabled for the {@code WARN} level?
     *
     * @return {@code true} if this Logger is enabled for the {@code WARN} level, {@code false} otherwise.
     */
    boolean isWarnEnabled();

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Similar to {@link #isWarnEnabled()} method except that the
     * marker data is also taken into account.
     *
     * @param marker The marker data to take into consideration
     * @return {@code true} if this Logger is enabled for the {@code WARN} level, {@code false} otherwise.
     */
    boolean isWarnEnabled(Marker marker);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code WARN} level.
     *
     * @param msg the message string to be logged
     */
    void warn(String msg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code WARN} level according to the specified format and argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the {@code WARN} level.
     *
     * @param format the format string
     * @param arg    the argument
     */
    void warn(String format,
              Object arg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code WARN} level according to the specified format and argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the {@code WARN} level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void warn(String format,
              Object arg1,
              Object arg2);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code WARN} level according to the specified format and argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the {@code WARN} level.
     *
     * @param format    the format string
     * @param arguments the arguments
     */
    void warn(String format,
              Object... arguments);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log an exception (throwable) at the {@code WARN} level with an accompanying message.
     *
     * @param msg       the message accompanying the exception
     * @param throwable the throwable to log
     */
    void warn(String msg,
              Throwable throwable);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message with the specific Marker at the {@code WARN} level.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message string to be logged
     */
    void warn(Marker marker,
              String msg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #warn(String, Object)} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    void warn(Marker marker,
              String format,
              Object arg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #warn(String, Object, Object)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void warn(Marker marker,
              String format,
              Object arg1,
              Object arg2);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #warn(String, Object...)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker   the marker data specific to this log statement
     * @param format   the format string
     * @param argArray an array of arguments
     */
    void warn(Marker marker,
              String format,
              Object... argArray);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #warn(String, Throwable)} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param msg       the message accompanying the exception
     * @param throwable the exception (throwable) to log
     */
    void warn(Marker marker,
              String msg,
              Throwable throwable);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Is the logger instance enabled for the {@code ERROR} level?
     *
     * @return {@code true} if this Logger is enabled for the {@code ERROR} level, {@code false} otherwise.
     */
    boolean isErrorEnabled();

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Similar to {@link #isErrorEnabled()} method except that the
     * marker data is also taken into account.
     *
     * @param marker The marker data to take into consideration
     * @return {@code true} if this Logger is enabled for the {@code ERROR} level, {@code false} otherwise.
     */
    boolean isErrorEnabled(Marker marker);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code ERROR} level.
     *
     * @param msg the message string to be logged
     */
    void error(String msg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code ERROR} level according to the specified format and argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the {@code ERROR} level.
     *
     * @param format the format string
     * @param arg    the argument
     */
    void error(String format,
               Object arg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code ERROR} level according to the specified format and argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the {@code ERROR} level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void error(String format,
               Object arg1,
               Object arg2);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message at the {@code ERROR} level according to the specified format and argument.
     *
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the {@code ERROR} level.
     *
     * @param format    the format string
     * @param arguments the arguments
     */
    void error(String format,
               Object... arguments);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log an exception (throwable) at the {@code ERROR} level with an accompanying message.
     *
     * @param msg       the message accompanying the exception
     * @param throwable the throwable to log
     */
    void error(String msg,
               Throwable throwable);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * Log a message with the specific Marker at the {@code ERROR} level.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message string to be logged
     */
    void error(Marker marker,
               String msg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #error(String, Object)} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    void error(Marker marker,
               String format,
               Object arg);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #error(String, Object, Object)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    void error(Marker marker,
               String format,
               Object arg1,
               Object arg2);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #error(String, Object...)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker   the marker data specific to this log statement
     * @param format   the format string
     * @param argArray an array of arguments
     */
    void error(Marker marker,
               String format,
               Object... argArray);

    /**
     * See <a href="http://www.slf4j.org/apidocs/org/slf4j/Logger.html">http://www.slf4j.org/apidocs/org/slf4j/Logger.html</a>.
     *
     * <p>
     * This method is similar to {@link #error(String, Throwable)} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param msg       the message accompanying the exception
     * @param throwable the exception (throwable) to log
     */
    void error(Marker marker,
               String msg,
               Throwable throwable);
}
