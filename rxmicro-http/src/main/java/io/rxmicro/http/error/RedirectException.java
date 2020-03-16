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

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class RedirectException extends HttpErrorException {

    private final String location;

    private final boolean absolute;

    RedirectException(final int status,
                      final String location) {
        super(status);
        this.absolute = location.startsWith("http://") ||
                location.startsWith("https://") ||
                location.startsWith("//");
        if (this.absolute) {
            this.location = location;
        } else {
            this.location = location.startsWith("/") ? location : "/" + location;
        }
    }

    /**
     * This constructor uses {@link Formats#format(String, Object...) Formats.format} to format `Location` header
     */
    RedirectException(final int status,
                      final String location,
                      final Object... args) {
        this(status, format(location, args));
    }

    public boolean isAbsolute() {
        return absolute;
    }

    public final String getLocation() {
        return location;
    }
}
