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

package io.rxmicro.config;

import io.rxmicro.common.RxMicroException;
import io.rxmicro.common.util.Formats;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class ConfigException extends RxMicroException {

    /**
     * This constructor uses {@link Formats#format(String, Object...) Formats.format} to format error message
     */
    public ConfigException(final String message,
                           final Object... args) {
        super(message, args);
    }
}
