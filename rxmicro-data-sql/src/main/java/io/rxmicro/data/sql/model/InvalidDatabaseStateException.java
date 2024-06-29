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

package io.rxmicro.data.sql.model;

import io.rxmicro.common.RxMicroException;
import io.rxmicro.common.util.Formats;

/**
 * Indicates that current database state is invalid.
 *
 * <p>
 * Required {@code message} contains the details of failure.
 *
 * @author nedis
 * @since 0.7
 */
public final class InvalidDatabaseStateException extends RxMicroException {

    private static final long serialVersionUID = 7410727509107181319L;

    /**
     * Creates a new {@link InvalidDatabaseStateException} instance with error message.
     *
     * <p>
     * <i>(FYI: This constructor uses {@link Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param message the error message template
     * @param args    the error message template arguments
     */
    public InvalidDatabaseStateException(final String message,
                                         final Object... args) {
        super(message, args);
    }
}
