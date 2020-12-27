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

import static io.rxmicro.common.util.Formats.format;

/**
 * Represents a logger event builder instance that should be used for building a {@link LoggerEvent} instance.
 *
 * @author nedis
 * @see LoggerFactory#newLoggerEventBuilder()
 * @see LoggerEventBuilder
 * @see Logger#trace(LoggerEvent)
 * @see Logger#debug(LoggerEvent)
 * @see Logger#info(LoggerEvent)
 * @see Logger#warn(LoggerEvent)
 * @see Logger#error(LoggerEvent)
 * @since 0.8
 */
public interface LoggerEventBuilder {

    /**
     * Sets a {@link RequestIdSupplier} instance.
     *
     * @param requestIdSupplier the {@link RequestIdSupplier} instance.
     * @return the reference to this  {@link LoggerEventBuilder} instance.
     * @throws NullPointerException if the specified {@code requestIdSupplier} is {@code null}.
     */
    LoggerEventBuilder setRequestIdSupplier(RequestIdSupplier requestIdSupplier);

    /**
     * Sets source class name, method name, file name and line number.
     *
     * @param sourceClassName the class name.
     * @param sourceMethodName the method name.
     * @param sourceFileName the file name.
     * @param sourceLineNumber the line number.
     * @return the reference to this  {@link LoggerEventBuilder} instance.
     * @throws NullPointerException if the specified {@code sourceClassName} or {@code sourceMethodName} or {@code sourceFileName} is
     *                              {@code null}.
     * @throws IllegalArgumentException if specified {@code sourceLineNumber} is invalid.
     */
    LoggerEventBuilder setStackFrame(String sourceClassName,
                                     String sourceMethodName,
                                     String sourceFileName,
                                     int sourceLineNumber);

    /**
     * Sets a source class name.
     *
     * @param sourceClassName the class name.
     * @return the reference to this  {@link LoggerEventBuilder} instance.
     * @throws NullPointerException if the specified {@code sourceClassName} is {@code null}.
     */
    LoggerEventBuilder setSourceClassName(String sourceClassName);

    /**
     * Sets a source method name.
     *
     * @param sourceMethodName the method name.
     * @return the reference to this  {@link LoggerEventBuilder} instance.
     * @throws NullPointerException if the specified {@code sourceMethodName} is {@code null}.
     */
    LoggerEventBuilder setSourceMethodName(String sourceMethodName);

    /**
     * Sets a source file name.
     *
     * @param sourceFileName the file name.
     * @return the reference to this  {@link LoggerEventBuilder} instance.
     * @throws NullPointerException if the specified {@code sourceFileName} is {@code null}.
     */
    LoggerEventBuilder setSourceFileName(String sourceFileName);

    /**
     * Sets a source line number.
     *
     * @param sourceLineNumber the line number.
     * @return the reference to this  {@link LoggerEventBuilder} instance.
     * @throws IllegalArgumentException if specified {@code sourceLineNumber} is invalid.
     */
    LoggerEventBuilder setSourceLineNumber(int sourceLineNumber);

    /**
     * Sets a logged message.
     *
     * @param message the logged message.
     * @return the reference to this  {@link LoggerEventBuilder} instance.
     * @throws NullPointerException if the specified {@code message} is {@code null}.
     */
    LoggerEventBuilder setMessage(String message);

    /**
     * Sets a logged message.
     *
     * <p>
     * <i>(FYI: This constructor uses {@link io.rxmicro.common.util.Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param template the message template.
     * @param args the message template arguments
     * @return the reference to this  {@link LoggerEventBuilder} instance.
     * @throws NullPointerException if the specified {@code template} is {@code null}.
     * @throws IllegalArgumentException if detected a redundant placeholder or missing argument
     */
    default LoggerEventBuilder setMessage(final String template, final Object... args) {
        return setMessage(format(template, args));
    }

    /**
     * Sets a thread id.
     *
     * @param threadId the thread id.
     * @return the reference to this  {@link LoggerEventBuilder} instance.
     * @throws IllegalArgumentException if specified {@code threadId} is invalid.
     */
    LoggerEventBuilder setThreadId(long threadId);

    /**
     * Sets a thread name.
     *
     * @param threadName the thread name.
     * @return the reference to this  {@link LoggerEventBuilder} instance.
     * @throws NullPointerException if the specified {@code threadName} is {@code null}.
     */
    LoggerEventBuilder setThreadName(String threadName);

    /**
     * Sets id and name of the specified thread.
     *
     * @param thread the specified thread.
     * @return the reference to this  {@link LoggerEventBuilder} instance.
     * @throws NullPointerException if the specified {@code thread} is {@code null}.
     */
    default LoggerEventBuilder setThread(final Thread thread) {
        return setThreadId(thread.getId())
                .setThreadName(thread.getName());
    }

    /**
     * Sets a {@link Throwable} instance.
     *
     * @param throwable the throwable instance.
     * @return the reference to this  {@link LoggerEventBuilder} instance.
     * @throws NullPointerException if the specified {@code throwable} is {@code null}.
     */
    LoggerEventBuilder setThrowable(Throwable throwable);

    /**
     * Returns a new instance of logger event.
     *
     * @return a new instance of logger event.
     * @see Logger#trace(LoggerEvent)
     * @see Logger#debug(LoggerEvent)
     * @see Logger#info(LoggerEvent)
     * @see Logger#warn(LoggerEvent)
     * @see Logger#error(LoggerEvent)
     */
    LoggerEvent build();
}
