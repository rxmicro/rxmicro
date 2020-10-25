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

/**
 * Defines the static HTTP response headers that must be returned by HTTP server for all HTTP responses.
 *
 * <p>
 * {@code `static`} term means that a header value does not depend on any HTTP request parameter or any config setting.
 *
 * @author nedis
 * @see PredefinedStaticResponseHeader
 * @since 0.3
 */
public interface StaticResponseHeader {

    /**
     * Returns the static HTTP header name.
     *
     * @return the static HTTP header name
     */
    String getName();

    /**
     * Returns the static HTTP header value.
     *
     * @return the static HTTP header value
     */
    String getValue();
}
