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

package io.rxmicro.http.error;

import io.rxmicro.common.RxMicroException;
import io.rxmicro.common.util.Formats;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public class HttpErrorException extends RxMicroException {

    private final int statusCode;

    public HttpErrorException(final int statusCode,
                              final String message) {
        super(message, null, false, false);
        this.statusCode = statusCode;
    }

    /**
     * This constructor uses {@link Formats#format(String, Object...) Formats.format} to format error message
     */
    public HttpErrorException(final int statusCode,
                              final String message,
                              final Object... args) {
        super(message, null, false, false, args);
        this.statusCode = statusCode;
    }

    public HttpErrorException(final int statusCode) {
        super(null, null, false, false);
        this.statusCode = statusCode;
    }

    public final int getStatusCode() {
        return statusCode;
    }

    public final boolean isInformationalCode() {
        return statusCode >= 100 && statusCode < 199;
    }

    public final boolean isSuccessCode() {
        return statusCode >= 200 && statusCode < 299;
    }

    public final boolean isRedirectCode() {
        return statusCode >= 300 && statusCode < 399;
    }

    public final boolean isClientErrorCode() {
        return statusCode >= 400 && statusCode < 499;
    }

    public final boolean isServerErrorCode() {
        return statusCode >= 500 && statusCode < 599;
    }
}
