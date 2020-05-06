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

package io.rxmicro.rest.server.local.component.impl;

import io.rxmicro.rest.server.local.component.RequestIdGenerator;

/**
 * Request id generator used for test environment only.
 *
 * By default, it is automatically activated for test environment.
 * Returns predefined value: {@code TestRequestId}. See {@link TestRequestIdGenerator#TEST_REQUEST_ID}
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class TestRequestIdGenerator implements RequestIdGenerator {

    private static final String TEST_REQUEST_ID = "TestRequestId";

    @Override
    public String getNextId() {
        return TEST_REQUEST_ID;
    }
}
