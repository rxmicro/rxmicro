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

import io.rxmicro.rest.server.feature.RequestIdGenerator;
import io.rxmicro.rest.server.local.component.impl.FasterButUnSafeRequestIdGenerator;
import io.rxmicro.rest.server.local.component.impl.SafeButSlowerRequestIdGenerator;
import io.rxmicro.rest.server.local.component.impl.TestRequestIdGenerator;

/**
 * Defines supported HTTP request id generator types.
 *
 * @author nedis
 * @see RestServerConfig
 * @since 0.7
 */
public enum PredefinedRequestIdGeneratorType implements RequestIdGeneratorType{

    /**
     * Generates unique IDs only within the framework of starting one JVM.
     *
     * <p>
     * Uses a random increment counter.
     * (<i><strong>Used by default</strong></i>).
     */
    FASTER_BUT_UNSAFE(new FasterButUnSafeRequestIdGenerator()),

    /**
     * Generates unique IDs independently of starting the JVM.
     *
     * <p>
     * As a unique value uses the {@link java.util.UUID#randomUUID()}.
     */
    SAFE_BUT_SLOWER(new SafeButSlowerRequestIdGenerator()),

    /**
     * Recommended for test environment only.
     *
     * <p>
     * <i><strong>By default, it is automatically activated for test environment.</strong></i>
     */
    FOR_TESTS_ONLY(new TestRequestIdGenerator());

    private final RequestIdGenerator requestIdGenerator;

    PredefinedRequestIdGeneratorType(final RequestIdGenerator requestIdGenerator) {
        this.requestIdGenerator = requestIdGenerator;
    }

    @Override
    public RequestIdGenerator getRequestIdGenerator() {
        return requestIdGenerator;
    }
}
