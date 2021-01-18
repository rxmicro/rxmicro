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
import io.rxmicro.logger.RequestIdSupplier;

import java.net.SocketAddress;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * <p>
 * Represents a low level model for HTTP request and can be used to work with internal data.
 *
 * <p>
 * Read more:
 * <a href="https://docs.rxmicro.io/latest/user-guide/rest-controller.html#rest-controller-internals-basic-section">
 *     https://docs.rxmicro.io/latest/user-guide/rest-controller.html#rest-controller-internals-basic-section
 * </a>
 *
 * @author nedis
 * @since 0.1
 */
public interface HttpRequest extends RequestIdSupplier {

    /**
     * Returns the remote address for the connected HTTP client.
     *
     * @return the remote address for the connected HTTP client
     */
    SocketAddress getRemoteAddress();

    /**
     * Returns the <code>{@value io.rxmicro.http.HttpStandardHeaderNames#REQUEST_ID}</code> HTTP header.
     *
     * @return the <code>{@value io.rxmicro.http.HttpStandardHeaderNames#REQUEST_ID}</code> HTTP header
     * @see io.rxmicro.http.HttpStandardHeaderNames#REQUEST_ID
     * @see RequestIdSupplier
     */
    @Override
    String getRequestId();

    /**
     * Returns the HTTP method.
     *
     * @return the HTTP method
     */
    String getMethod();

    /**
     * Returns the request URI
     *
     * @return the request URI
     */
    String getUri();

    /**
     * Returns {@code true} if the current HTTP request contains a query string
     *
     * @return {@code true} if the current HTTP request contains a query string
     */
    default boolean isQueryStringPresent() {
        return !getQueryString().isEmpty();
    }

    /**
     * Returns the query string for the current HTTP request.
     *
     * @return the query string for the current HTTP request or empty string if the query string not found
     */
    String getQueryString();

    /**
     * Returns the {@link HttpVersion} for the current HTTP request.
     *
     * @return the {@link HttpVersion} for the current HTTP request
     */
    HttpVersion getVersion();

    /**
     * Returns the {@link HttpHeaders} for the current HTTP request.
     *
     * @return the {@link HttpHeaders} for the current HTTP request
     */
    HttpHeaders getHeaders();

    /**
     * Returns {@code true} if the current HTTP request contains a HTTP body.
     *
     * @return {@code true} if the current HTTP request contains a HTTP body
     */
    boolean isContentPresent();

    /**
     * Returns the HTTP body as byte array for the current HTTP request.
     *
     * @return the HTTP body as byte array for the current HTTP request
     */
    byte[] getContent();
}
