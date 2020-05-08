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

package io.rxmicro.http.client;

import java.util.function.Function;

/**
 * Basic interface for low-level converting of HTTP body for HTTP client
 *
 * @author nedis
 * @since 0.1
 */
public interface HttpClientContentConverter {

    /**
     * Returns the request content (body) converter
     *
     * @return the request content (body) converter
     */
    Function<Object, byte[]> getRequestContentConverter();

    /**
     * Returns the response content (body) converter
     *
     * @return the response content (body) converter
     */
    Function<byte[], Object> getResponseContentConverter();

    /**
     * Returns the supported content type
     *
     * @return the supported content type
     */
    String getContentType();
}
