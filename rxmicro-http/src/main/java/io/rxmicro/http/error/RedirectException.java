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

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Strings.startsWith;

/**
 * A base class to inform the client about the need to perform redirect.
 *
 * <p>
 * Instead of using a base class, it is recommended to use one of the following child ones:
 * <ul>
 *     <li>{@link TemporaryRedirectException}</li>
 *     <li>{@link PermanentRedirectException}</li>
 * </ul>
 *
 * @author nedis
 * @since 0.1
 */
public abstract class RedirectException extends HttpErrorException {

    private static final long serialVersionUID = -2055706966189346269L;

    private final String location;

    private final boolean absolute;

    /**
     * Creates a {@link RedirectException} instance with the specified status and expected redirect location.
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
     * @param status   the specified redirect status code
     * @param location the HTTP {@code Location} header value
     */
    RedirectException(final int status,
                      final String location) {
        super(status);
        this.absolute = location.startsWith("http://") ||
                location.startsWith("https://") ||
                location.startsWith("//");
        if (this.absolute) {
            this.location = location;
        } else {
            this.location = startsWith(location, '/') ? location : "/" + location;
        }
    }

    /**
     * Creates a {@link RedirectException} instance with the specified status and expected redirect location.
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
     * @param status   the specified redirect status code
     * @param location the HTTP {@code Location} header value template
     * @param args     the HTTP {@code Location} header value template arguments
     */
    RedirectException(final int status,
                      final String location,
                      final Object... args) {
        this(status, format(location, args));
    }

    /**
     * Returns {@code true} if a new redirect location is absolute URL path.
     *
     * @return {@code true} if a new redirect location is absolute URL path.
     */
    public boolean isAbsolute() {
        return absolute;
    }

    /**
     * Returns the new redirect location.
     *
     * @return the new redirect location
     */
    public final String getLocation() {
        return location;
    }
}
