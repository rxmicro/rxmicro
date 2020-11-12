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

package io.rxmicro.test.local;

import io.rxmicro.common.RxMicroException;
import io.rxmicro.common.util.Formats;

/**
 * Signals that current test configuration contains error(s) that must be fixed before the launch of tests
 *
 * @author nedis
 * @since 0.1
 */
public final class InvalidTestConfigException extends RxMicroException {

    /**
     * Creates a {@link InvalidTestConfigException} instance
     *
     * @param message the error message
     * @throws NullPointerException if the error message is {@code null}
     */
    public InvalidTestConfigException(final String message) {
        super(message);
    }

    /**
     * Creates a {@link InvalidTestConfigException} instance
     * <p>
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param message the error message template
     * @param args the error message template arguments
     * @throws NullPointerException if the error message template is {@code null}
     * @throws IllegalArgumentException if detected a redundant placeholder or missing argument
     */
    public InvalidTestConfigException(final String message,
                                      final Object... args) {
        super(false, false, message, args);
    }

    /**
     * Creates a {@link InvalidTestConfigException} instance
     * <p>
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     * @param message the error message template
     * @param args the error message template arguments
     * @throws NullPointerException if the error message template is {@code null}
     * @throws IllegalArgumentException if detected a redundant placeholder or missing argument
     */
    public InvalidTestConfigException(final Throwable cause,
                                      final String message,
                                      final Object... args) {
        super(cause, false, false, message, args);
    }
}
