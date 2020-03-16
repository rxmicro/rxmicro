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

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public class RxMicroException extends RuntimeException {

    public RxMicroException(final String message) {
        super(require(message));
    }

    /**
     * This constructor uses {@link Formats#format(String, Object...) Formats.format} to format error message
     */
    public RxMicroException(final String message,
                            final Object... args) {
        super(format(message, args));
    }

    public RxMicroException(final String message,
                            final Throwable throwable) {
        super(require(message), require(throwable));
    }

    /**
     * This constructor uses {@link Formats#format(String, Object...) Formats.format} to format error message
     */
    public RxMicroException(final String message,
                            final Throwable throwable,
                            final Object... args) {
        super(format(message, args), throwable);
    }

    public RxMicroException(final Throwable throwable) {
        super(require(throwable));
    }

    protected RxMicroException(final String message,
                               final boolean enableSuppression,
                               final boolean writableStackTrace) {
        super(message, null, enableSuppression, writableStackTrace);
    }

    /**
     * This constructor uses {@link Formats#format(String, Object...) Formats.format} to format error message
     */
    protected RxMicroException(final String message,
                               final boolean enableSuppression,
                               final boolean writableStackTrace,
                               final Object... args) {
        super(format(message, args), null, enableSuppression, writableStackTrace);
    }

    protected RxMicroException(final String message,
                               final Throwable cause,
                               final boolean enableSuppression,
                               final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * This constructor uses {@link Formats#format(String, Object...) Formats.format} to format error message
     */
    protected RxMicroException(final String message,
                               final Throwable cause,
                               final boolean enableSuppression,
                               final boolean writableStackTrace,
                               final Object... args) {
        super(format(message, args), cause, enableSuppression, writableStackTrace);
    }
}
