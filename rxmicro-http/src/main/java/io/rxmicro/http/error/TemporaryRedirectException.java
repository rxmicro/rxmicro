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

package io.rxmicro.http.error;

import io.rxmicro.common.util.Formats;

import static io.rxmicro.http.HttpStatuses.TEMPORARY_REDIRECT_307;

/**
 * A class that signals the need to perform Temporary Redirect ({@code 307}).
 *
 * @author nedis
 * @since 0.1
 */
public final class TemporaryRedirectException extends RedirectException {

    /**
     * Status code for the all instances of the current exception type.
     */
    public static final int STATUS_CODE = TEMPORARY_REDIRECT_307;

    private static final long serialVersionUID = -2268993570678368244L;

    /**
     * Creates a Temporary Redirect instance with new URL path.
     *
     * <p>
     * For all child classes which extend the HttpErrorException class, when creating an exception instance the stack trace is not filled,
     * as this information is redundant.
     *
     * <p>
     * (<i>This behavior is achieved by using the {@link RuntimeException#RuntimeException(String, Throwable, boolean, boolean)}.</i>)
     *
     * @param location the HTTP {@code Location} header value
     */
    public TemporaryRedirectException(final String location) {
        super(STATUS_CODE, location);
    }

    /**
     * Creates a Temporary Redirect instance with new URL path.
     *
     * <p>
     * For all child classes which extend the HttpErrorException class, when creating an exception instance the stack trace is not filled,
     * as this information is redundant.
     *
     * <p>
     * (<i>This behavior is achieved by using the {@link RuntimeException#RuntimeException(String, Throwable, boolean, boolean)}.</i>)
     *
     * <p>
     * This constructor uses {@link Formats#format(String, Object...)} method to format `Location` header
     *
     * @param location the HTTP {@code Location} header value template
     * @param args the HTTP {@code Location} header value template arguments
     */
    public TemporaryRedirectException(final String location,
                                      final Object... args) {
        super(STATUS_CODE, location, args);
    }
}
