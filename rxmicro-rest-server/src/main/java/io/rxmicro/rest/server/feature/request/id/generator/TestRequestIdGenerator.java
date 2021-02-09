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

package io.rxmicro.rest.server.feature.request.id.generator;

import io.rxmicro.rest.server.feature.RequestIdGenerator;

/**
 * This request id generator always returns the {@value #TEST_REQUEST_ID} value as request id!
 *
 * <p>
 * The RxMicro team recommends to use this request id generator for testing purposes only!
 *
 * @author nedis
 * @since 0.10
 */
public final class TestRequestIdGenerator implements RequestIdGenerator {

    /**
     * The const value for the generated request id.
     */
    public static final String TEST_REQUEST_ID = "test_request_id";

    @Override
    public String getNextId() {
        return TEST_REQUEST_ID;
    }
}
