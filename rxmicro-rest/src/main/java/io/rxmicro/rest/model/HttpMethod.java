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

package io.rxmicro.rest.model;

/**
 * All Supported HTTP methods.
 *
 * @author nedis
 * @see io.rxmicro.rest.method.DELETE
 * @see io.rxmicro.rest.method.GET
 * @see io.rxmicro.rest.method.HEAD
 * @see io.rxmicro.rest.method.OPTIONS
 * @see io.rxmicro.rest.method.PATCH
 * @see io.rxmicro.rest.method.POST
 * @see io.rxmicro.rest.method.PUT
 * @see io.rxmicro.rest.method.HttpMethods
 * @since 0.1
 */
public enum HttpMethod {

    /**
     * HTTP GET method.
     */
    GET,

    /**
     * HTTP POST method.
     */
    POST,

    /**
     * HTTP PUT method.
     */
    PUT,

    /**
     * HTTP DELETE method.
     */
    DELETE,

    /**
     * HTTP PATCH method.
     */
    PATCH,

    /**
     * HTTP OPTIONS method.
     */
    OPTIONS,

    /**
     * HTTP HEAD method.
     */
    HEAD
}
