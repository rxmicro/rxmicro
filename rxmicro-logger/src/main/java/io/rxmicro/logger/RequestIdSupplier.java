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

package io.rxmicro.logger;

/**
 * The request id supplier.
 *
 * @author nedis
 * @since 0.7
 */
public interface RequestIdSupplier {

    /**
     * Log message must contain {@value #UNDEFINED_REQUEST_ID} string if request id is is null.
     */
    String UNDEFINED_REQUEST_ID = "null";

    /**
     * Log message must contain {@value #UNSUPPORTED_REQUEST_ID_FEATURE} string if request id is not supported by logger framework.
     */
    String UNSUPPORTED_REQUEST_ID_FEATURE = "unsupported-request-id-feature";

    /**
     * Returns the request id.
     *
     * @return the request id
     */
    String getRequestId();
}
