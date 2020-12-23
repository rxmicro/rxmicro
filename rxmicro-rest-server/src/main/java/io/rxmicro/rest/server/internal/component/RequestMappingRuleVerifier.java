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

package io.rxmicro.rest.server.internal.component;

import io.rxmicro.config.ConfigException;
import io.rxmicro.rest.server.detail.model.mapping.RequestMappingRule;
import io.rxmicro.rest.server.local.model.RestControllerMethod;

import java.util.HashMap;
import java.util.Map;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.8
 */
public final class RequestMappingRuleVerifier {

    private final RequestMappingKeyBuilder requestMappingKeyBuilder;

    private final Map<String, RestControllerMethod> map = new HashMap<>();

    public RequestMappingRuleVerifier(final RequestMappingKeyBuilder requestMappingKeyBuilder) {
        this.requestMappingKeyBuilder = require(requestMappingKeyBuilder);
    }

    public void verifyThatRequestMappingRuleIsUnique(final RequestMappingRule requestMappingRule,
                                                     final RestControllerMethod restControllerMethod) {
        final String key = requestMappingKeyBuilder.build(requestMappingRule);
        final RestControllerMethod oldRestControllerMethod = map.put(key, restControllerMethod);
        if (oldRestControllerMethod != null) {
            throw new ConfigException(
                    "Request mapping '?' not unique! " +
                            "Found the following rest controller methods that bound to this request mapping:\n  ?\n  ?",
                    key,
                    oldRestControllerMethod,
                    restControllerMethod
            );
        }
    }

    public void reset() {
        map.clear();
    }
}
