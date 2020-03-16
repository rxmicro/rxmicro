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

package io.rxmicro.annotation.processor.common.model;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public enum ModelFieldType {

    REST_SERVER_REQUEST,

    REST_SERVER_RESPONSE,

    REST_CLIENT_REQUEST,

    REST_CLIENT_RESPONSE,

    DATA_METHOD_PARAMETER,

    DATA_METHOD_RESULT,

    UNDEFINED;

    public boolean isRequest() {
        return this == REST_SERVER_REQUEST || this == REST_CLIENT_REQUEST;
    }

    public boolean isResponse() {
        return this == REST_SERVER_RESPONSE || this == REST_CLIENT_RESPONSE;
    }
}
