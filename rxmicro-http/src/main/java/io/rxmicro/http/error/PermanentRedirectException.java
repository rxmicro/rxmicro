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

import static io.rxmicro.http.HttpStatuses.PERMANENT_REDIRECT_308;

/**
 * A class that signals the need to perform Permanent Redirect ({@code 308}).
 *
 * @author nedis
 * @since 0.1
 */
public final class PermanentRedirectException extends RedirectException {

    /**
     * Status code for the all instances of the current exception type.
     */
    public static final int STATUS_CODE = PERMANENT_REDIRECT_308;

    /**
     * Creates a Permanent Redirect instance with new URL path.
     *
     * <p>
     * For all child classes which extend the HttpErrorException class, when creating an exception instance the stack trace is not filled,
     * as this information is redundant.
     *
     * <p>
     * (<i>This behavior is achieved by using the {@link RuntimeException#RuntimeException(String, Throwable, boolean, boolean)}.</i>)
     *
     * @param location the HTTP {@code Location} header value
     * @throws NullPointerException if the location is {@code null}
     */
    public PermanentRedirectException(final String location) {
        super(STATUS_CODE, location);
    }

    /**
     * Creates a Permanent Redirect instance with new URL path.
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
     * @throws NullPointerException if the location is {@code null}
     * @throws IllegalArgumentException if detected a redundant placeholder or missing argument
     */
    public PermanentRedirectException(final String location,
                                      final Object... args) {
        super(STATUS_CODE, location, args);
    }
}
