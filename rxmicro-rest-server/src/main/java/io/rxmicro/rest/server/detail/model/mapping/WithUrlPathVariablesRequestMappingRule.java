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

import io.rxmicro.rest.model.UrlSegments;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.PathMatcherResult;
import io.rxmicro.rest.server.local.component.PathMatcher;

import java.util.List;
import java.util.Optional;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.http.HttpStandardHeaderNames.API_VERSION;
import static io.rxmicro.rest.server.detail.model.PathMatcherResult.NO_MATCH;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public final class WithUrlPathVariablesRequestMappingRule extends AbstractRequestMappingRule {

    private final UrlSegments urlSegments;

    public WithUrlPathVariablesRequestMappingRule(final String method,
                                                  final UrlSegments urlSegments,
                                                  final boolean httpBody,
                                                  final String versionHeader) {
        super(method, httpBody, versionHeader);
        this.urlSegments = require(urlSegments);
    }

    public WithUrlPathVariablesRequestMappingRule(final String method,
                                                  final UrlSegments urlSegments,
                                                  final boolean httpBody) {
        super(method, httpBody);
        this.urlSegments = require(urlSegments);
    }

    public List<String> getVariables() {
        return urlSegments.getVariables();
    }

    @Override
    public String getUri() {
        return urlSegments.getOriginalUrl();
    }

    public PathMatcherResult match(final HttpRequest request) {
        if (hasHttpBody() != request.isContentPresent()) {
            return NO_MATCH;
        } else if (!getMethod().equals(request.getMethod())) {
            return NO_MATCH;
        } else {
            final Optional<String> expectedVersionHeaderOptional = getVersionHeaderValue();
            if (expectedVersionHeaderOptional.isPresent()) {
                final String actualVersionHeader = request.getHeaders().getValue(API_VERSION);
                if (actualVersionHeader == null) {
                    return NO_MATCH;
                } else if (!expectedVersionHeaderOptional.get().equals(actualVersionHeader)) {
                    return NO_MATCH;
                }
            }
            return new PathMatcher(urlSegments).matches(request.getUri());
        }
    }
}
