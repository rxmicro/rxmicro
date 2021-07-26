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

package io.rxmicro.rest.server.internal.component.impl;

import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.mapping.RequestMappingRule;
import io.rxmicro.rest.server.internal.component.RequestMappingKeyBuilder;

import static io.rxmicro.http.HttpStandardHeaderNames.API_VERSION;

/**
 * Example: {@code GET /hello-world v1.0}
 * Example: {@code POST /hello-world v1.0 <with-body>}
 *
 * @author nedis
 * @since 0.1
 */
public final class RequestMappingKeyBuilderImpl implements RequestMappingKeyBuilder {

    private static final String WITH_BODY = "<with-body>";

    private static final int DEFAULT_REQUEST_MAPPING_KEY_BUILDER_CAPACITY = 50;

    private static final String START_QUOTE_DELIMITER = " '";

    private static final String END_QUOTE_DELIMITER = "' ";

    @Override
    public String build(final HttpRequest request) {
        final StringBuilder keyBuilder = new StringBuilder(DEFAULT_REQUEST_MAPPING_KEY_BUILDER_CAPACITY)
                .append(request.getMethod()).append(START_QUOTE_DELIMITER)
                .append(request.getUri()).append(END_QUOTE_DELIMITER);
        final String versionHeader = request.getHeaders().getValue(API_VERSION);
        if (versionHeader != null) {
            keyBuilder.append(API_VERSION).append("='").append(versionHeader).append(END_QUOTE_DELIMITER);
        }
        if (request.isContentPresent()) {
            keyBuilder.append(WITH_BODY);
        }
        return keyBuilder.toString();
    }

    @Override
    public String build(final RequestMappingRule requestMappingRule) {
        final StringBuilder keyBuilder = new StringBuilder(DEFAULT_REQUEST_MAPPING_KEY_BUILDER_CAPACITY)
                .append(requestMappingRule.getMethod()).append(START_QUOTE_DELIMITER)
                .append(requestMappingRule.getUri()).append(END_QUOTE_DELIMITER);
        requestMappingRule.getVersionHeaderValue().ifPresent(versionHeader ->
                keyBuilder.append(API_VERSION).append("='").append(versionHeader).append(END_QUOTE_DELIMITER));
        if (requestMappingRule.hasHttpBody()) {
            keyBuilder.append(WITH_BODY);
        }
        return keyBuilder.toString();
    }
}
