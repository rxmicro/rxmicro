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

package io.rxmicro.annotation.processor.rest.server.model;

import io.rxmicro.rest.model.HttpMethod;

import java.util.Objects;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class HttpHealthCheck implements Comparable<HttpHealthCheck> {

    private final HttpMethod method;

    private final String endpoint;

    public HttpHealthCheck(final HttpMethod method,
                           final String endpoint) {
        this.method = require(method);
        this.endpoint = require(endpoint);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getEndpoint() {
        return endpoint;
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, endpoint);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final HttpHealthCheck that = (HttpHealthCheck) other;
        return method.equals(that.method) &&
                endpoint.equals(that.endpoint);
    }

    @Override
    public int compareTo(final HttpHealthCheck other) {
        final int result = endpoint.compareTo(other.endpoint);
        if (result == 0) {
            return method.compareTo(other.method);
        } else {
            return result;
        }
    }
}
