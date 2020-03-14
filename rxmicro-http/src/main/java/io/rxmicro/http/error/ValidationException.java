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

package io.rxmicro.http.error;

import io.rxmicro.common.util.Formats;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Strings.capitalize;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class ValidationException extends HttpErrorException {

    public static final int STATUS_CODE = 400;

    public ValidationException(final String message) {
        super(STATUS_CODE, capitalize(message));
    }

    /**
     * This constructor uses {@link Formats#format(String, Object...) Formats.format} to format error message
     */
    public ValidationException(final String message,
                               final Object... args) {
        super(STATUS_CODE, capitalize(format(message, args)));
    }
}
