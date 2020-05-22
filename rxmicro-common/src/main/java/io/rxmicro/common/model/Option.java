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

package io.rxmicro.common.model;

/**
 * Three state logic class.
 *
 * @author nedis
 * @see Boolean
 * @since 0.1
 */
public enum Option {

    /**
     * Option is enabled.
     */
    ENABLED,

    /**
     * Option is disabled.
     */
    DISABLED,

    /**
     * Option state must be defined using external functions.
     */
    AUTO;

    /**
     * Converts the {@link Option} to the {@code boolean} type.
     *
     * @param autoValue the external value for {@link #AUTO} state of the current {@link Option}
     * @return the {@code boolean} representation of the current {@link Option}
     */
    public boolean toBoolean(final boolean autoValue) {
        if (this == ENABLED) {
            return true;
        } else if (this == DISABLED) {
            return false;
        } else {
            return autoValue;
        }
    }
}
