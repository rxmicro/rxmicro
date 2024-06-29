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

package io.rxmicro.validation;

import io.rxmicro.common.RxMicroException;
import io.rxmicro.common.util.Formats;

/**
 * A class signaling that some model component is invalid.
 *
 * @author nedis
 * @since 0.12
 */
public final class ConstraintViolationException extends RxMicroException {

    /**
     * Creates a validation error with error message.
     *
     * <p>
     * FYI: When creating an exception instance the stack trace is not filled, as this information is redundant.
     * Usually, error message contains all required info to fix this error.
     *
     * <p>
     * (<i>This behavior is achieved by using the {@link RuntimeException#RuntimeException(String, Throwable, boolean, boolean)}.</i>)
     *
     * @param message the error message
     */
    public ConstraintViolationException(final String message) {
        super(true, false, message);
    }

    /**
     * Creates a validation error with error message.
     *
     * <p>
     * FYI: When creating an exception instance the stack trace is not filled, as this information is redundant.
     * Usually, error message contains all required info to fix this error.
     *
     * <p>
     * (<i>This behavior is achieved by using the {@link RuntimeException#RuntimeException(String, Throwable, boolean, boolean)}.</i>)
     *
     * <p>
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param message the error message template
     * @param args the error message template argument
     */
    public ConstraintViolationException(final String message, final Object... args) {
        super(true, false, message, args);
    }
}
