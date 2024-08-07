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

package io.rxmicro.common;

import io.rxmicro.common.util.Formats;

import static io.rxmicro.common.util.Formats.format;

/**
 * Basic exception class for all RxMicro modules.
 *
 * <p>
 * This class adds the string formatting support using the {@link Formats#format(String, Object...)} method.
 *
 * @author nedis
 * @see Formats#format(String, Object...)
 * @since 0.1
 */
public class RxMicroException extends RuntimeException {

    private static final long serialVersionUID = -6702503405202825623L;

    /**
     * Creates a new {@link RxMicroException} instance with error message.
     *
     * @param message the error message
     */
    protected RxMicroException(final String message) {
        super(message);
    }

    /**
     * Creates a new {@link RxMicroException} instance with error message.
     *
     * <p>
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param message the error message template
     * @param args    the error message template arguments
     */
    protected RxMicroException(final String message,
                               final Object... args) {
        super(format(message, args));
    }

    /**
     * Creates a new {@link RxMicroException} instance with error message and cause.
     *
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).
     * @param message the error message
     */
    protected RxMicroException(final Throwable cause,
                               final String message) {
        super(message, cause);
    }

    /**
     * Creates a new {@link RxMicroException} instance with error message and cause.
     *
     * <p>
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).
     * @param message the error message template
     * @param args    the error message template arguments
     */
    protected RxMicroException(final Throwable cause,
                               final String message,
                               final Object... args) {
        super(format(message, args), cause);
    }

    /**
     * Creates a new {@link RxMicroException} instance with cause only.
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    protected RxMicroException(final Throwable cause) {
        super(cause);
    }

    /**
     * Creates a {@link RxMicroException} instance with the specified suppression enabled or disabled
     * and writable stack trace enabled or disabled.
     *
     * <p>
     * <i>This constructor must be used from child classes only!</i>
     *
     * @param enableSuppression  whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    protected RxMicroException(final boolean enableSuppression,
                               final boolean writableStackTrace) {
        super(null, null, enableSuppression, writableStackTrace);
    }

    /**
     * Creates a {@link RxMicroException} instance with the specified error message, suppression enabled or disabled
     * and writable stack trace enabled or disabled.
     *
     * <p>
     * <i>This constructor must be used from child classes only!</i>
     *
     * @param enableSuppression  whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     * @param message            the error message
     */
    protected RxMicroException(final boolean enableSuppression,
                               final boolean writableStackTrace,
                               final String message) {
        super(message, null, enableSuppression, writableStackTrace);
    }

    /**
     * Creates a {@link RxMicroException} instance with the specified error message, suppression enabled or disabled
     * and writable stack trace enabled or disabled.
     *
     * <p>
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * <p>
     * <i>This constructor must be used from child classes only!</i>
     *
     * @param enableSuppression  whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     * @param message            the error message template
     * @param args               the error message template arguments
     */
    protected RxMicroException(final boolean enableSuppression,
                               final boolean writableStackTrace,
                               final String message,
                               final Object... args) {
        super(format(message, args), null, enableSuppression, writableStackTrace);
    }

    /**
     * Creates a {@link RxMicroException} instance with the specified error message, cause, suppression enabled or disabled
     * and writable stack trace enabled or disabled.
     *
     * <p>
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * <p>
     * <i>This constructor must be used from child classes only!</i>
     *
     * @param cause              the cause (which is saved for later retrieval by the {@link #getCause()} method).
     * @param enableSuppression  whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     * @param message            the error message
     */
    protected RxMicroException(final Throwable cause,
                               final boolean enableSuppression,
                               final boolean writableStackTrace,
                               final String message) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Creates a {@link RxMicroException} instance with the specified error message, cause, suppression enabled or disabled
     * and writable stack trace enabled or disabled.
     *
     * <p>
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * <p>
     * <i>This constructor must be used from child classes only!</i>
     *
     * @param cause              the cause (which is saved for later retrieval by the {@link #getCause()} method).
     * @param enableSuppression  whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     * @param message            the error message template
     * @param args               the error message template arguments
     */
    protected RxMicroException(final Throwable cause,
                               final boolean enableSuppression,
                               final boolean writableStackTrace,
                               final String message,
                               final Object... args) {
        super(format(message, args), cause, enableSuppression, writableStackTrace);
    }
}
