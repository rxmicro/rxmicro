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
 * Signals that the environment or microservice is not in an appropriate state for the requested operation.
 *
 * @author nedis
 * @since 0.1
 */
public final class InvalidStateException extends RxMicroException {

    private static final long serialVersionUID = -152576916638467253L;

    /**
     * Creates a new {@link InvalidStateException} instance with error message.
     *
     * @param message the error message
     */
    public InvalidStateException(final String message) {
        super(message);
    }

    /**
     * Creates a new {@link InvalidStateException} instance with error message.
     *
     * <p>
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param message the error message template
     * @param args    the error message template arguments
     */
    public InvalidStateException(final String message,
                                 final Object... args) {
        super(message, args);
    }
}
