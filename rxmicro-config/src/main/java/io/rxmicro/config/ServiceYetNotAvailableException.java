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

package io.rxmicro.config;

import io.rxmicro.common.RxMicroException;

/**
 * Indicates that a service is not available yet.
 *
 * @author nedis
 * @since 0.7.3
 */
public final class ServiceYetNotAvailableException extends RxMicroException {

    private static final long serialVersionUID = -9146228228195897479L;

    /**
     * Default constructor to build {@link ServiceYetNotAvailableException} instance.
     *
     * <p>
     * <i>(FYI: This constructor uses {@link io.rxmicro.common.util.Formats#format(String, Object...)} method to format error message.)</i>
     *
     * @param message the error message template
     * @param args the message template arguments
     */
    public ServiceYetNotAvailableException(final String message,
                                           final Object... args) {
        super(message, args);
    }
}
