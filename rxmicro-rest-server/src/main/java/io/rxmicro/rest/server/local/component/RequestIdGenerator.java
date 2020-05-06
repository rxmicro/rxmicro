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

package io.rxmicro.rest.server.local.component;

/**
 * Base interface for generating of unique HTTP request.
 *
 * To track an HTTP request when using a microservice architecture, the RxMicro framework provides an HTTP request identification feature.
 * If the current request is identified, the provided unique id is used during the life-cycle of the current request.
 * If the request is not identified, RxMicro generates a unique id, which is further used in the life-cycle of the current request.
 *
 * To store the request id, the `Request-Id` (See {@link io.rxmicro.http.HttpHeaders#REQUEST_ID}) HTTP additional header is used.
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public interface RequestIdGenerator {

    /**
     * Returns the next unique identifier of HTTP request
     *
     * @return the next unique identifier of HTTP request
     */
    String getNextId();
}
