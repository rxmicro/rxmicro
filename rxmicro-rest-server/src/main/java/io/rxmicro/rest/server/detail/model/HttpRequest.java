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

package io.rxmicro.rest.server.detail.model;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;

import java.net.SocketAddress;

/**
 * Represents a HTTP request model.
 * <p>
 * Used by generated code that was created by {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @since 0.1
 */
public interface HttpRequest {

    SocketAddress getRemoteAddress();

    String getRequestId();

    String getMethod();

    String getUri();

    default boolean isQueryStringPresent() {
        return getQueryString().isEmpty();
    }

    String getQueryString();

    HttpVersion getVersion();

    HttpHeaders getHeaders();

    boolean isContentPresent();

    byte[] getContent();
}
