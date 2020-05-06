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

package io.rxmicro.data;

import io.rxmicro.common.RxMicroException;
import io.rxmicro.common.util.Formats;

/**
 * The basic exception type for modules that work with dynamic data repositories.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class DataException extends RxMicroException {

    /**
     * Creates a data error with error message.
     * <p>
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     * <p>
     * @param message error message or error message template
     * @param args error message template arguments
     * @throws NullPointerException if {@code message} is {@code null}
     */
    protected DataException(final String message,
                            final Object... args) {
        super(message, args);
    }

    /**
     * Creates a data error with error message.
     * <p>
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     * @param message error message or error message template
     * @param args error message template arguments
     * @throws NullPointerException if {@code message} or {@code cause} is {@code null}
     */
    protected DataException(final Throwable cause,
                            final String message,
                            final Object... args) {
        super(cause, message, args);
    }
}
