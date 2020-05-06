/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.common;

import io.rxmicro.common.util.Formats;

/**
 * Indicates that an exception cannot be thrown.
 * <p>
 * For example:
 * <pre>
 * final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
 * try (OutputStream out = byteArrayOutputStream) {
 *      // do something
 * } catch (final IOException e) {
 *     throw new ImpossibleException(e, "Writer uses the byte array, so IO exception is impossible!");
 * }
 * </pre>
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class ImpossibleException extends RxMicroException {

    /**
     * Creates a new {@link ImpossibleException} instance with error message.
     * <p>
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param message error message template
     * @param args error message template arguments
     * @throws NullPointerException if {@code message} is {@code null}
     */
    public ImpossibleException(final String message,
                               final Object... args) {
        super(message, args);
    }

    /**
     * Creates a new {@link ImpossibleException} instance with error message and cause.
     * <p>
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     * @param message error message template
     * @param args error message template arguments
     * @throws NullPointerException if {@code message} or {@code cause} is {@code null}
     */
    public ImpossibleException(final Throwable cause,
                               final String message,
                               final Object... args) {
        super(cause, message, args);
    }
}
