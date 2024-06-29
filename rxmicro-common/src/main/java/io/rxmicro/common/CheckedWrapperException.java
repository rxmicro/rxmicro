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

/**
 * Wrapper for checked exceptions.
 *
 * @author nedis
 * @see io.rxmicro.common.util.Exceptions
 * @since 0.1
 */
public final class CheckedWrapperException extends RxMicroException {

    private static final long serialVersionUID = 930053263723131097L;

    /**
     * Creates a new {@link CheckedWrapperException} instance with error message and cause.
     *
     * <p>
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).
     * @param message the error message template
     * @param args    the error message template arguments
     */
    public CheckedWrapperException(final Exception cause,
                                   final String message,
                                   final Object... args) {
        super(ensureCheckedException(cause), message, args);
    }

    /**
     * Creates a new {@link CheckedWrapperException} instance with cause only.
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public CheckedWrapperException(final Exception cause) {
        super(ensureCheckedException(cause));
    }

    private static Exception ensureCheckedException(final Exception cause) {
        if (cause instanceof RuntimeException) {
            throw (RuntimeException) cause;
        }
        return cause;
    }

    /**
     * Returns {@code true} if current instance contains a cause one of the specified exception class.
     *
     * @param exceptionClass the specified exception class
     * @return {@code true} if current instance contains a cause one of the specified exception class
     */
    public boolean isCause(final Class<? extends Exception> exceptionClass) {
        return exceptionClass.isAssignableFrom(getCause().getClass());
    }
}
