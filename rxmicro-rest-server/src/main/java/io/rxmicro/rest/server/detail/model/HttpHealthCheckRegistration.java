/*
 * Copyright (c) 2020. http://rxmicro.io
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

import java.util.Objects;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class HttpHealthCheckRegistration {

    private final String method;

    private final String endpoint;

    public HttpHealthCheckRegistration(final String method,
                                       final String endpoint) {
        this.method = require(method);
        this.endpoint = require(endpoint);
    }

    public String getMethod() {
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
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final HttpHealthCheckRegistration that = (HttpHealthCheckRegistration) o;
        return method.equals(that.method) &&
                endpoint.equals(that.endpoint);
    }
}
