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

package io.rxmicro.test.local.util;

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Safes {

    private static final Logger LOGGER = LoggerFactory.getLogger(Safes.class);

    public static <T> void safeInvoke(final T instance,
                                      final Consumer<T> consumer) {
        if (instance != null) {
            try {
                consumer.accept(instance);
            } catch (final Throwable throwable) {
                LOGGER.warn(throwable, "Can't safe invoke: ?", throwable.getMessage());
            }
        }
    }

    private Safes() {
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    public interface Consumer<T> {

        void accept(T item) throws Throwable;
    }
}
