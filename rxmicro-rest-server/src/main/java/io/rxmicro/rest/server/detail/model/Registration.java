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

import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.model.mapping.RequestMappingRule;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * @author nedis
 * @hidden
 * @since 0.1
 */
public final class Registration {

    private final String parentUrl;

    private final String methodName;

    private final List<Class<?>> paramTypes;

    private final BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>> method;

    private final boolean corsRequestPossible;

    private final List<RequestMappingRule> requestMappingRules;

    public Registration(final String parentUrl,
                        final String methodName,
                        final List<Class<?>> paramTypes,
                        final BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>> method,
                        final boolean corsRequestPossible,
                        final RequestMappingRule... requestMappingRules) {
        this.parentUrl = parentUrl;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.method = method;
        this.corsRequestPossible = corsRequestPossible;
        this.requestMappingRules = List.of(requestMappingRules);
    }

    public Registration(final String parentUrl,
                        final String methodName,
                        final List<Class<?>> paramTypes,
                        final BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>> method,
                        final boolean corsRequestPossible,
                        final List<RequestMappingRule> requestMappingRules) {
        this.parentUrl = parentUrl;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.method = method;
        this.corsRequestPossible = corsRequestPossible;
        this.requestMappingRules = List.copyOf(requestMappingRules);
    }

    public String getMethodName() {
        return methodName;
    }

    public List<Class<?>> getParamTypes() {
        return paramTypes;
    }

    public BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>> getMethod() {
        return method;
    }

    public boolean isCorsRequestPossible() {
        return corsRequestPossible;
    }

    public List<RequestMappingRule> getRequestMappingRules() {
        return requestMappingRules;
    }

    public String getParentUrl() {
        return parentUrl;
    }
}
