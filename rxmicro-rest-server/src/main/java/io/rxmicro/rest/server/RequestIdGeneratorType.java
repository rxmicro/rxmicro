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

package io.rxmicro.rest.server;

import java.util.UUID;

/**
 * Defines supported HTTP request id generator types.
 *
 * @author nedis
 * @see RestServerConfig
 * @since 0.1
 */
public enum RequestIdGeneratorType {

    /**
     * Generates unique IDs only within the framework of starting one JVM.
     *
     * <p>
     * Uses a random increment counter.
     * (<i><strong>Used by default</strong></i>).
     */
    FASTER_BUT_UNSAFE,

    /**
     * Generates unique IDs independently of starting the JVM.
     *
     * <p>
     * As a unique value uses the {@link UUID#randomUUID()}.
     */
    SAFE_BUT_SLOWER,

    /**
     * Recommended for test environment only.
     *
     * <p>
     * <i><strong>By default, it is automatically activated for test environment.</strong></i>
     */
    FOR_TESTS_ONLY
}
