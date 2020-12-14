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

package io.rxmicro.logger.impl;

import io.rxmicro.logger.Logger;

/**
 * Basic interface for the all supported {@link Logger} implementations.
 *
 * @author nedis
 * @see Logger
 * @see io.rxmicro.logger.LoggerFactory
 * @since 0.1
 */
public interface LoggerImplProvider {

    /**
     * Sets up the current provider.
     *
     * @throws Throwable if setup failed.
     */
    void setup() throws Throwable;

    /**
     * Returns the {@link Logger} instance by the specified name.
     *
     * @param name the specified name
     * @return the {@link Logger} instance
     */
    Logger getLogger(String name);

    /**
     * Returns the {@link Logger} instance by the specified class name.
     *
     * @param clazz the specified class name
     * @return the {@link Logger} instance
     */
    default Logger getLogger(final Class<?> clazz) {
        return getLogger(clazz.getName());
    }
}
