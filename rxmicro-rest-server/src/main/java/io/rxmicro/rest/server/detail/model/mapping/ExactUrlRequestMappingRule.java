/*
 * Copyright 2019 https://rxmicro.io
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

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class ExactUrlRequestMappingRule extends AbstractRequestMappingRule {

    private final String uri;

    public ExactUrlRequestMappingRule(final String method,
                                      final String uri,
                                      final boolean httpBody) {
        super(method, httpBody);
        this.uri = require(uri);
    }

    public ExactUrlRequestMappingRule(final String method,
                                      final String uri,
                                      final boolean httpBody,
                                      final String versionHeaderValue) {
        super(method, httpBody, versionHeaderValue);
        this.uri = require(uri);
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + uri.hashCode();
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
        if (!super.equals(other)) {
            return false;
        }
        final ExactUrlRequestMappingRule that = (ExactUrlRequestMappingRule) other;
        return uri.equals(that.uri);
    }
}
