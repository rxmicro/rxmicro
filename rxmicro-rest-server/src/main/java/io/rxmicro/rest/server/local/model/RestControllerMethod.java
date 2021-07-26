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

package io.rxmicro.rest.server.local.model;

import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.internal.BaseRestControllerMethod;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.1
 */
public final class RestControllerMethod extends BaseRestControllerMethod {

    private final String methodName;

    private final List<Class<?>> paramTypes;

    private final BiFunction<PathVariableMapping, HttpRequest, CompletionStage<HttpResponse>> function;

    public RestControllerMethod(final Registration registration, final AbstractRestController restController) {
        super(registration.getParentUrl(), restController, registration.isCorsRequestPossible());
        this.methodName = require(registration.getMethodName());
        this.paramTypes = unmodifiableList(registration.getParamTypes());
        this.function = require(registration.getMethod());
    }

    @Override
    public AbstractRestController getRestController() {
        return (AbstractRestController) super.getRestController();
    }

    @Override
    protected CompletionStage<HttpResponse> invoke(final PathVariableMapping pathVariableMapping,
                                                   final HttpRequest request) {
        return function.apply(pathVariableMapping, request);
    }

    public String getShortName() {
        return format(
                "?.?(?)",
                getRestController().getRestControllerClass().getSimpleName(),
                methodName,
                paramTypes.stream().map(Class::getSimpleName).collect(joining(", "))
        );
    }

    public String getFullName() {
        return format(
                "?.?(?)",
                getRestController().getRestControllerClass().getName(),
                methodName,
                paramTypes.stream().map(Class::getName).collect(joining(", "))
        );
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
