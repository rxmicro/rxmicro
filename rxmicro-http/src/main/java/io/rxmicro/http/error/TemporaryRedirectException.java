/*
 * Copyright (c) 2020. http://rxmicro.io
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

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class TemporaryRedirectException extends RedirectException {

    public static final int STATUS_CODE = 307;

    public TemporaryRedirectException(final String location) {
        super(STATUS_CODE, location);
    }

    /**
     * This constructor uses {@link Formats#format(String, Object...) Formats.format} to format `Location` header
     */
    public TemporaryRedirectException(final String location,
                                      final Object... args) {
        super(STATUS_CODE, location, args);
    }
}
