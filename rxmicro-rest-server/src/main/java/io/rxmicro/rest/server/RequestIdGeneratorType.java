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

/**
 * Defines supported HTTP request id generator types.
 *
 * @author nedis
 * @see RestServerConfig
 * @since 0.1
 */
public interface RequestIdGeneratorType {

    /**
     * Returns the request id generator instance
     *
     * @return the request id generator instance
     * @see RequestIdGenerator
     * @see PredefinedRequestIdGeneratorType
     */
    RequestIdGenerator getRequestIdGenerator();
}
