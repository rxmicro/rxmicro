/*
 * Copyright 2019 http://rxmicro.io
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

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class CheckedWrapperException extends RxMicroException {

    /**
     * This constructor uses {@link Formats#format(String, Object...) Formats.format} to format error message
     */
    public CheckedWrapperException(final String message,
                                   final Throwable throwable,
                                   final Object... args) {
        super(message, require(throwable), args);
    }

    public CheckedWrapperException(final Throwable throwable) {
        super(require(throwable));
    }

    public boolean isCause(final Class<? extends Throwable> throwableClass) {
        return throwableClass.isAssignableFrom(getCause().getClass());
    }
}
