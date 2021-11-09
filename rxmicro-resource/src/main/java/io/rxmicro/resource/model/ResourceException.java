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

package io.rxmicro.resource.model;

import io.rxmicro.common.RxMicroException;
import io.rxmicro.common.util.Formats;

/**
 * Signals that an error with file resource has occurred.
 *
 * @author nedis
 * @since 0.1
 */
public final class ResourceException extends RxMicroException {

    private static final long serialVersionUID = 3339709000198947089L;

    /**
     * Creates a {@link ResourceException} instance.
     *
     * <p>
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param message the error message template
     * @param args the error message template arguments
     */
    public ResourceException(final String message,
                             final Object... args) {
        super(message, args);
    }

    /**
     * Creates a {@link ResourceException} instance.
     *
     * <p>
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     * @param message the error message template
     * @param args the error message template arguments
     */
    public ResourceException(final Throwable cause,
                             final String message,
                             final Object... args) {
        super(cause, message, args);
    }
}
