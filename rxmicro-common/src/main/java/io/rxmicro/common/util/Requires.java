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

package io.rxmicro.common.util;

import static io.rxmicro.common.util.Formats.format;

/**
 * TODO
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Requires {

    /**
     *
     *
     * @param instance
     * @param <T>
     * @return
     */
    public static <T> T require(final T instance) {
        //assert instance != null;
        if (instance == null) {
            throw new NullPointerException();
        }
        return instance;
    }

    /**
     *
     *
     * @param instance
     * @param message
     * @param args
     * @param <T>
     * @return
     */
    public static <T> T require(final T instance,
                                final String message,
                                final Object... args) {
        //assert instance != null : format(message, args);
        if (instance == null) {
            throw new NullPointerException(format(message, args));
        }
        return instance;
    }

    private Requires() {
    }
}
