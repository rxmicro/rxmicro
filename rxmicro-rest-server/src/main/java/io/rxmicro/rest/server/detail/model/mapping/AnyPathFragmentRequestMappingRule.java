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

import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.mapping.resource.UrlPathMatchTemplate;

/**
 * @author nedis
 * @since 0.8
 */
public final class AnyPathFragmentRequestMappingRule extends AbstractRequestMappingRule {

    private final UrlPathMatchTemplate urlPathMatchTemplate;

    public AnyPathFragmentRequestMappingRule(final String method,
                                             final UrlPathMatchTemplate urlPathMatchTemplate,
                                             final boolean httpBody) {
        super(method, httpBody);
        this.urlPathMatchTemplate = urlPathMatchTemplate;
    }

    @Override
    public String getUri() {
        return urlPathMatchTemplate.getUrlTemplate();
    }

    public boolean match(final HttpRequest request) {
        if (hasHttpBody() != request.isContentPresent()) {
            return false;
        } else if (getMethod().equals(request.getMethod())) {
            return urlPathMatchTemplate.match(request);
        } else {
            return false;
        }
    }
}
