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

package io.rxmicro.json;

import io.rxmicro.common.RxMicroException;

/**
 * Signals that json syntax error detected.
 *
 * @author nedis
 * @see JsonTypes
 * @see JsonHelper
 * @see JsonNumber
 * @since 0.1
 */
public final class JsonException extends RxMicroException {

    private static final long serialVersionUID = -680991406865212816L;

    /**
     * Creates a new {@link JsonException} instance with the specified error message.
     *
     * @param message the error message
     */
    public JsonException(final String message) {
        super(message);
    }

    /**
     * Creates a new {@link JsonException} instance with the specified error message template and arguments.
     *
     * <p>
     * <i>(FYI: This constructor uses {@link io.rxmicro.common.util.Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param message the error message template
     * @param args    the error message template arguments
     */
    public JsonException(final String message,
                         final Object... args) {
        super(message, args);
    }

    /**
     * Creates a new {@link JsonException} instance with error message and cause.
     *
     * <p>
     * <i>(FYI: This constructor uses {@link io.rxmicro.common.util.Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).
     * @param message the error message template
     * @param args    the error message template arguments
     */
    public JsonException(final Throwable cause,
                         final String message,
                         final Object... args) {
        super(cause, message, args);
    }
}
