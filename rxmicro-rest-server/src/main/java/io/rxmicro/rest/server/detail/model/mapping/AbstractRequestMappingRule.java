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

package io.rxmicro.rest.server.detail.model.mapping;

import java.util.Objects;
import java.util.Optional;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.http.HttpStandardHeaderNames.API_VERSION;

/**
 * Used by generated code that was created by {@code RxMicro Annotation Processor}
 *
 * @author nedis
 * @since 0.1
 */
abstract class AbstractRequestMappingRule implements RequestMappingRule {

    private final String method;

    private final boolean httpBody;

    private final String versionHeaderValue;

    AbstractRequestMappingRule(final String method,
                               final boolean httpBody) {
        this.method = require(method);
        this.httpBody = httpBody;
        this.versionHeaderValue = null;
    }

    AbstractRequestMappingRule(final String method,
                               final boolean httpBody,
                               final String versionHeaderValue) {
        this.method = require(method);
        this.httpBody = httpBody;
        this.versionHeaderValue = require(versionHeaderValue);
    }

    @Override
    public final String getMethod() {
        return method;
    }

    @Override
    public final Optional<String> getVersionHeaderValue() {
        return Optional.ofNullable(versionHeaderValue);
    }

    @Override
    public final boolean hasHttpBody() {
        return httpBody;
    }

    @Override
    public int hashCode() {
        int result = method.hashCode();
        result = 31 * result + (httpBody ? 1 : 0);
        result = 31 * result + (versionHeaderValue != null ? versionHeaderValue.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final AbstractRequestMappingRule that = (AbstractRequestMappingRule) other;
        if (httpBody != that.httpBody) {
            return false;
        }
        if (!method.equals(that.method)) {
            return false;
        }
        return Objects.equals(versionHeaderValue, that.versionHeaderValue);
    }

    @Override
    public final String toString() {
        final StringBuilder stringBuilder = new StringBuilder("\"");
        stringBuilder.append(method).append(" '").append(getUri()).append("' ");
        if (versionHeaderValue != null) {
            stringBuilder.append(API_VERSION).append(": ").append(versionHeaderValue).append(' ');
        }
        if (httpBody) {
            stringBuilder.append("<with-body>");
        }

        return stringBuilder.toString();
    }
}
